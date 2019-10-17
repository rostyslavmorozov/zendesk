/*
 * Copyright © 2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.plugin.zendesk.source.batch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.format.StructuredRecordStringConverter;
import io.cdap.plugin.zendesk.source.batch.http.CommentsPagedIterator;
import io.cdap.plugin.zendesk.source.batch.http.PagedIterator;
import io.cdap.plugin.zendesk.source.common.ObjectType;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;

import static io.cdap.plugin.zendesk.source.batch.util.ZendeskBatchSourceConstants.PROPERTY_CONFIG_JSON;

/**
 * RecordReader implementation, which reads object from Zendesk using
 * zendesk-java-client.
 */
public class ZendeskRecordReader extends RecordReader<NullWritable, StructuredRecord> {

  private static final Gson GSON = new GsonBuilder().create();

  private final String subdomain;
  private final ObjectType objectType;
  private final Schema schema;

  private Iterator<String> pagedIterator;

  public ZendeskRecordReader(String subdomain, ObjectType objectType, Schema schema) {
    this.subdomain = subdomain;
    this.objectType = objectType;
    this.schema = schema;
  }

  @Override
  public void initialize(InputSplit split,
                         TaskAttemptContext taskAttemptContext) throws IOException, InterruptedException {
    Configuration conf = taskAttemptContext.getConfiguration();
    String configJson = conf.get(PROPERTY_CONFIG_JSON);
    BaseZendeskBatchSourceConfig config = GSON.fromJson(configJson, BaseZendeskBatchSourceConfig.class);
    pagedIterator = createIterator(config);
  }

  @Override
  public boolean nextKeyValue() throws IOException, InterruptedException {
    return pagedIterator.hasNext();
  }

  @Override
  public NullWritable getCurrentKey() throws IOException, InterruptedException {
    return NullWritable.get();
  }

  @Override
  public StructuredRecord getCurrentValue() throws IOException, InterruptedException {
    String next = pagedIterator.next();
    StructuredRecord record = StructuredRecordStringConverter.fromJsonString(next, schema);
    StructuredRecord.Builder builder = StructuredRecord.builder(schema);
    for (Schema.Field field : schema.getFields()) {
      builder.set(field.getName(), record.get(field.getName()));
    }
    return builder.build();
  }

  @Override
  public float getProgress() throws IOException, InterruptedException {
    return 0;
  }

  @Override
  public void close() throws IOException {
    if (pagedIterator != null) {
      ((Closeable) pagedIterator).close();
    }
  }

  private Iterator<String> createIterator(BaseZendeskBatchSourceConfig config) {
    if (objectType == ObjectType.ARTICLE_COMMENTS || objectType == ObjectType.POST_COMMENTS) {
      return new CommentsPagedIterator(
        new PagedIterator(config, ObjectType.USERS_SIMPLE, subdomain),
        config, objectType, subdomain);
    }
    if (objectType == ObjectType.REQUESTS_COMMENTS) {
      return new CommentsPagedIterator(
        new PagedIterator(config, ObjectType.REQUESTS, subdomain),
        config, objectType, subdomain);
    }
    return new PagedIterator(config, objectType, subdomain);
  }
}
