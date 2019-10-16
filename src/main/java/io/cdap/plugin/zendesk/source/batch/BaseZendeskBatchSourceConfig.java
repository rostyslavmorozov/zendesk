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
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.zendesk.source.common.ObjectType;
import io.cdap.plugin.zendesk.source.common.config.BaseZendeskSourceConfig;
import org.apache.commons.lang3.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.annotation.Nullable;

/**
 * Base Zendesk Batch Source config. Contains common configuration properties and methods.
 */
public class BaseZendeskBatchSourceConfig extends BaseZendeskSourceConfig {

  public static final String PROPERTY_START_DATE = "startDate";
  public static final String PROPERTY_END_DATE = "endDate";
  public static final String PROPERTY_SATISFACTION_RATINGS_SCORE = "satisfactionRatingsScore";
  public static final String PROPERTY_MAX_RETRY_COUNT = "maxRetryCount";
  public static final String PROPERTY_MAX_RETRY_WAIT = "maxRetryWait";
  public static final String PROPERTY_MAX_RETRY_JITTER_WAIT = "maxRetryJitterWait";
  public static final String PROPERTY_CONNECT_TIMEOUT = "connectTimeout";
  public static final String PROPERTY_READ_TIMEOUT = "readTimeout";
  public static final String PROPERTY_URL = "zendeskBaseUrl";

  @Name(PROPERTY_START_DATE)
  @Description("Zendesk start_date filter. " +
    "Searching for items modified after or at start_date. Example: 2019-03-12T11:29:52Z")
  @Nullable
  @Macro
  private String startDate;

  @Name(PROPERTY_END_DATE)
  @Description("Zendesk end_date filter. " +
    "Searching for items modified before or at end_date. Example: 2019-03-12T11:29:52Z")
  @Nullable
  @Macro
  private String endDate;

  @Name(PROPERTY_SATISFACTION_RATINGS_SCORE)
  @Description("Score filter for Satisfaction Ratings object.")
  @Nullable
  @Macro
  private String satisfactionRatingsScore;

  @Name(PROPERTY_MAX_RETRY_COUNT)
  @Description("Maximum number of retries can take.")
  @Macro
  private Integer maxRetryCount;

  @Name(PROPERTY_MAX_RETRY_WAIT)
  @Description("Maximum time in seconds retries can take.")
  @Macro
  private Integer maxRetryWait;

  @Name(PROPERTY_MAX_RETRY_JITTER_WAIT)
  @Description("Maximum time in milliseconds added to retries.")
  @Macro
  private Integer maxRetryJitterWait;

  @Name(PROPERTY_CONNECT_TIMEOUT)
  @Description("Maximum time in seconds connection initialization is allowed to take.")
  @Macro
  private Integer connectTimeout;

  @Name(PROPERTY_READ_TIMEOUT)
  @Description("Maximum time in seconds fetching data from the server is allowed to take.")
  @Macro
  private Integer readTimeout;

  @Name(PROPERTY_URL)
  @Description("Zendesk base url.")
  @Nullable
  @Macro
  private String zendeskBaseUrl;

  public BaseZendeskBatchSourceConfig(String referenceName,
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
                                      @Nullable String zendeskBaseUrl) {
    super(referenceName, adminEmail, apiToken, subdomains, objectsToPull);
    this.startDate = startDate;
    this.endDate = endDate;
    this.satisfactionRatingsScore = satisfactionRatingsScore;
    this.maxRetryCount = maxRetryCount;
    this.maxRetryWait = maxRetryWait;
    this.maxRetryJitterWait = maxRetryJitterWait;
    this.connectTimeout = connectTimeout;
    this.readTimeout = readTimeout;
    this.zendeskBaseUrl = zendeskBaseUrl;
  }

  @Nullable
  public String getStartDate() {
    return startDate;
  }

  @Nullable
  public String getEndDate() {
    return endDate;
  }

  @Nullable
  public String getSatisfactionRatingsScore() {
    return satisfactionRatingsScore;
  }

  public Integer getMaxRetryCount() {
    return maxRetryCount;
  }

  public Integer getMaxRetryWait() {
    return maxRetryWait;
  }

  public Integer getMaxRetryJitterWait() {
    return maxRetryJitterWait;
  }

  public Integer getConnectTimeout() {
    return connectTimeout;
  }

  public Integer getReadTimeout() {
    return readTimeout;
  }

  public String getZendeskBaseUrl() {
    if (StringUtils.isBlank(zendeskBaseUrl)) {
      return "https://%s.zendesk.com/api/v2/%s";
    }
    return zendeskBaseUrl;
  }

  @Override
  public void validate(FailureCollector collector) {
    super.validate(collector);
    if (!containsMacro(PROPERTY_START_DATE)
      && !containsMacro(PROPERTY_OBJECTS_TO_PULL)) {
      try {
        boolean batchObjectSelected = getObjectsToPull().stream()
          .map(ObjectType::fromString)
          .anyMatch(ObjectType::isBatch);
        if (batchObjectSelected && StringUtils.isBlank(startDate)) {
          collector.addFailure(
            "Property 'Start Date' can't be empty.",
            "Ensure 'Start Date' is specified for objects: " +
              "Ticket Comments, Organizations, Ticket Metrics, Ticket Metric Events, " +
              "Tickets, Users")
            .withConfigProperty(PROPERTY_START_DATE);
        }
      } catch (IllegalStateException e) {
        collector.addFailure(e.getMessage(), null)
          .withConfigProperty(BaseZendeskSourceConfig.PROPERTY_OBJECTS_TO_PULL)
          .withStacktrace(e.getStackTrace());
      }
    }
    validateIntervalFilterProperty(PROPERTY_START_DATE, getStartDate(), collector);
    validateIntervalFilterProperty(PROPERTY_END_DATE, getEndDate(), collector);
  }

  private void validateIntervalFilterProperty(String propertyName, String datetime, FailureCollector collector) {
    if (containsMacro(propertyName)) {
      return;
    }
    if (StringUtils.isBlank(datetime)) {
      return;
    }
    try {
      ZonedDateTime.parse(datetime, DateTimeFormatter.ISO_DATE_TIME);
    } catch (DateTimeParseException e) {
      collector.addFailure(
        String.format("Invalid '%s' value: '%s'.", propertyName, datetime),
        "Value must be in Zendesk Formats. For example, 2019-01-01T23:01:01Z")
        .withConfigProperty(propertyName);
    }
  }
}
