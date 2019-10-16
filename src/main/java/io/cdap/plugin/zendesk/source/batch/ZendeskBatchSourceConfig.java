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

import com.google.common.base.Strings;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.zendesk.source.common.ObjectType;

import java.io.IOException;
import javax.annotation.Nullable;

/**
 * This class {@link ZendeskBatchSourceConfig} provides all the configuration required for
 * configuring the {@link ZendeskBatchSource} plugin.
 */
public class ZendeskBatchSourceConfig extends BaseZendeskBatchSourceConfig {

  public static final String PROPERTY_SCHEMA = "schema";

  @Name(PROPERTY_SCHEMA)
  @Nullable
  @Description("Output schema for the source.")
  private String schema;

  public ZendeskBatchSourceConfig(String referenceName,
                                  String adminEmail,
                                  String apiToken,
                                  String subdomains,
                                  String objectsToPull,
                                  @Nullable String startDate,
                                  @Nullable String endDate,
                                  @Nullable String satisfactionRatingsScore,
                                  Integer maxRetryCount,
                                  Integer maxRetryWait,
                                  Integer maxRetryJitterWait,
                                  Integer connectTimeout,
                                  Integer readTimeout,
                                  @Nullable String zendeskBaseUrl,
                                  @Nullable String schema) {
    super(referenceName, adminEmail, apiToken, subdomains, objectsToPull,
          startDate, endDate, satisfactionRatingsScore, maxRetryCount, maxRetryWait,
          maxRetryJitterWait, connectTimeout, readTimeout, zendeskBaseUrl);
    this.schema = schema;
  }

  public Schema getSchema(FailureCollector collector) {
    if (Strings.isNullOrEmpty(schema)) {
      String object = getObjectsToPull().iterator().next();
      return ObjectType.fromString(object, collector).getObjectSchema();
    }
    try {
      return Schema.parseJson(schema);
    } catch (IOException e) {
      collector.addFailure(String.format("Unable to parse output schema: %s", schema),
                           "Ensure 'Schema' is specified.")
        .withConfigProperty(PROPERTY_SCHEMA);
      throw collector.getOrThrowException();
    }
  }
}
