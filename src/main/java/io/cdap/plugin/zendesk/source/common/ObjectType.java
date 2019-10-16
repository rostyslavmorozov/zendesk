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

package io.cdap.plugin.zendesk.source.common;

import io.cdap.cdap.api.data.schema.Schema;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.zendesk.source.common.config.BaseZendeskSourceConfig;

import java.util.Arrays;

import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_GROUPS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_ORGANIZATIONS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_SATISFACTION_RATINGS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_TAGS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_TICKETS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_TICKET_COMMENTS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_TICKET_FIELDS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_TICKET_METRICS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_TICKET_METRIC_EVENTS;
import static io.cdap.plugin.zendesk.source.common.ObjectTypeSchemaConstants.SCHEMA_USERS;

/**
 * Supported Zendesk objects with schema
 */
public enum ObjectType {
  TICKET_COMMENTS(
    "Ticket Comments", "ticket_events", "child_events",
    "incremental/ticket_events.json?include=comment_events",
    true, SCHEMA_TICKET_COMMENTS),
  GROUPS(
    "Groups", "groups", "groups.json",
    false, SCHEMA_GROUPS),
  ORGANIZATIONS(
    "Organizations", "organizations", "incremental/organizations.json",
    true, SCHEMA_ORGANIZATIONS),
  SATISFACTION_RATINGS(
    "Satisfaction Ratings", "satisfaction_ratings", "satisfaction_ratings.json",
    false, SCHEMA_SATISFACTION_RATINGS),
  TAGS(
    "Tags", "tags", "tags.json",
    false, SCHEMA_TAGS),
  TICKET_FIELDS(
    "Ticket Fields", "ticket_fields", "ticket_fields.json",
    false, SCHEMA_TICKET_FIELDS),
  TICKET_METRICS(
    "Ticket Metrics", "ticket_metrics", "ticket_metrics.json",
    false, SCHEMA_TICKET_METRICS),
  TICKET_METRIC_EVENTS(
    "Ticket Metric Events", "ticket_metric_events", "incremental/ticket_metric_events.json",
    true, SCHEMA_TICKET_METRIC_EVENTS),
  TICKETS(
    "Tickets", "tickets", "incremental/tickets.json",
    true, SCHEMA_TICKETS),
  USERS(
    "Users", "users", "incremental/users.json",
    true, SCHEMA_USERS);

  private static final String CLASS_NAME = ObjectType.class.getName();

  private final String objectName;
  private final String responseKey;
  private final String childKey;
  private final String apiEndpoint;
  private final boolean batch;
  private final Schema objectSchema;

  ObjectType(String objectName,
             String responseKey,
             String apiEndpoint,
             boolean batch,
             Schema objectSchema) {
    this(objectName, responseKey, null,
         apiEndpoint, batch, objectSchema);
  }

  ObjectType(String objectName,
             String responseKey,
             String childKey,
             String apiEndpoint,
             boolean batch,
             Schema objectSchema) {
    this.objectName = objectName;
    this.responseKey = responseKey;
    this.childKey = childKey;
    this.apiEndpoint = apiEndpoint;
    this.batch = batch;
    this.objectSchema = objectSchema;
  }

  public String getObjectName() {
    return objectName;
  }

  public String getResponseKey() {
    return responseKey;
  }

  public String getChildKey() {
    return childKey;
  }

  public String getApiEndpoint() {
    return apiEndpoint;
  }

  public boolean isBatch() {
    return batch;
  }

  public Schema getObjectSchema() {
    return objectSchema;
  }

  public static ObjectType fromString(String value) {
    return Arrays.stream(ObjectType.values())
      .filter(fields -> fields.getObjectName().equals(value))
      .findFirst()
      .orElseThrow(() -> new IllegalStateException(String.format("'%s' is invalid %s", value, CLASS_NAME)));
  }

  public static ObjectType fromString(String value, FailureCollector collector) {
    return Arrays.stream(ObjectType.values())
      .filter(objectType -> objectType.getObjectName().equals(value))
      .findFirst()
      .orElseThrow(() -> {
        collector.addFailure(String.format("Unsupported schema for object %s. ", value),
                             "Ensure 'Objects to Pull' is selected.")
          .withConfigProperty(BaseZendeskSourceConfig.PROPERTY_OBJECTS_TO_PULL);
        return collector.getOrThrowException();
      });
  }
}
