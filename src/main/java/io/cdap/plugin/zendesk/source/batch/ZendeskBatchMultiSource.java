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

import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.annotation.Plugin;
import io.cdap.cdap.api.data.batch.Input;
import io.cdap.cdap.api.data.format.StructuredRecord;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.api.dataset.lib.KeyValue;
import io.cdap.cdap.etl.api.Emitter;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.cdap.etl.api.PipelineConfigurer;
import io.cdap.cdap.etl.api.action.SettableArguments;
import io.cdap.cdap.etl.api.batch.BatchSource;
import io.cdap.cdap.etl.api.batch.BatchSourceContext;
import org.apache.hadoop.io.NullWritable;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Plugin returns records from Zendesk API using provided by user multiple objects.
 */
@Plugin(type = BatchSource.PLUGIN_TYPE)
@Name(ZendeskBatchMultiSource.NAME)
@Description("Read data from Zendesk.")
public class ZendeskBatchMultiSource extends BatchSource<NullWritable, StructuredRecord, StructuredRecord> {

  public static final String NAME = "ZendeskBatchMultiSource";

  private static final String MULTI_SINK_PREFIX = "multisink.";

  private final ZendeskBatchSourceConfig config;

  public ZendeskBatchMultiSource(ZendeskBatchSourceConfig config) {
    this.config = config;
  }

  @Override
  public void configurePipeline(PipelineConfigurer pipelineConfigurer) {
    FailureCollector failureCollector = pipelineConfigurer.getStageConfigurer().getFailureCollector();
    validateConfiguration(failureCollector);

    pipelineConfigurer.getStageConfigurer().setOutputSchema(null);
  }

  @Override
  public void prepareRun(BatchSourceContext batchSourceContext) throws Exception {
    FailureCollector failureCollector = batchSourceContext.getFailureCollector();
    validateConfiguration(failureCollector);

    Map<String, Schema> schemas = config.getSchemas(failureCollector);
    Map<String, String> schemasStrings = schemas.entrySet()
      .stream()
      .collect(Collectors.toMap(
        Map.Entry::getKey,
        entry -> entry.getValue().toString()));

    // propagate schema for each object for multi sink plugin
    SettableArguments arguments = batchSourceContext.getArguments();
    schemasStrings.forEach(
      (objectName, objectSchema) -> arguments.set(
        MULTI_SINK_PREFIX + objectName.toLowerCase().replaceAll(" ", "_"),
        objectSchema));

    batchSourceContext.setInput(Input.of(config.referenceName, new ZendeskInputFormatProvider(
      config, config.getObjects(), schemasStrings)));
  }

  @Override
  public void transform(KeyValue<NullWritable, StructuredRecord> input,
                        Emitter<StructuredRecord> emitter) throws Exception {
    emitter.emit(input.getValue());
  }

  private void validateConfiguration(FailureCollector failureCollector) {
    config.validate(failureCollector);
    failureCollector.getOrThrowException();
  }
}
