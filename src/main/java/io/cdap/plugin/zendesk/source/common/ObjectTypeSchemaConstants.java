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

/**
 * Holds schemas for Zendesk objects
 */
public class ObjectTypeSchemaConstants {

  public static final Schema SCHEMA_TICKET_COMMENTS = Schema.recordOf(
    "ticket_comments",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("type", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("body", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("html_body", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("plain_body", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("public", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("author_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("attachments", Schema.nullableOf(Schema.arrayOf(Schema.nullableOf(Schema.recordOf(
      "attachment_object",
      Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("file_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("content_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("mapped_content_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("content_type", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("width", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("height", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("size", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("thumbnails", Schema.nullableOf(Schema.arrayOf(Schema.nullableOf(Schema.recordOf(
        "photo_object",
        Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("file_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("content_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("mapped_content_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("content_type", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("width", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("height", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("size", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("inline", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
        Schema.Field.of("deleted", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN)))
      ))))),
      Schema.Field.of("inline", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
      Schema.Field.of("deleted", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN)))
    ))))),
    Schema.Field.of("via", Schema.nullableOf(Schema.recordOf(
      "via_object",
      Schema.Field.of("channel", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("source", Schema.nullableOf(Schema.recordOf(
        "source_object",
        Schema.Field.of("from", Schema.nullableOf(Schema.recordOf(
          "from_object",
          Schema.Field.of("profile_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("subject", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("service_info", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("title", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("topic_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
          Schema.Field.of("topic_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
          Schema.Field.of("revision_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
          Schema.Field.of("supports_channelback", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("address", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("supports_clickthrough", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("original_recipients", Schema.nullableOf(Schema.arrayOf(
            Schema.nullableOf(Schema.of(Schema.Type.STRING))))),
          Schema.Field.of("formatted_phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("ticket_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
          Schema.Field.of("facebook_id", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("registered_integration_service_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("deleted", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("username", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
        ))),
        Schema.Field.of("to", Schema.nullableOf(Schema.recordOf(
          "to_object",
          Schema.Field.of("address", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("profile_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("formatted_phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("username", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("facebook_id", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
        ))),
        Schema.Field.of("rel", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
      )))
    ))),
    Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
  );

  public static final Schema SCHEMA_GROUPS = Schema.recordOf(
    "group",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("name", Schema.of(Schema.Type.STRING)),
    Schema.Field.of("deleted", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
  );

  public static final Schema SCHEMA_ORGANIZATIONS = Schema.recordOf(
    "organization",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("external_id", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("name", Schema.of(Schema.Type.STRING)),
    Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("domain_names", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.STRING))))),
    Schema.Field.of("details", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("notes", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("group_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("shared_tickets", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("shared_comments", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("tags", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.STRING))))),
    Schema.Field.of("organization_fields", Schema.nullableOf(Schema.mapOf(
      Schema.of(Schema.Type.STRING),
      Schema.of(Schema.Type.STRING))))
  );

  public static final Schema SCHEMA_SATISFACTION_RATINGS = Schema.recordOf(
    "satisfaction_ratings",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("assignee_id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("group_id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("requester_id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("ticket_id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("score", Schema.of(Schema.Type.STRING)),
    Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("comment", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("reason", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("reason_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("reason_code", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
  );

  public static final Schema SCHEMA_TAGS = Schema.recordOf(
    "tags",
    Schema.Field.of("name", Schema.of(Schema.Type.STRING)),
    Schema.Field.of("count", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
  );

  public static final Schema SCHEMA_TICKET_FIELDS = Schema.recordOf(
    "ticket_fields",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("type", Schema.of(Schema.Type.STRING)),
    Schema.Field.of("title", Schema.of(Schema.Type.STRING)),
    Schema.Field.of("raw_title", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("description", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("raw_description", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("position", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("active", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("required", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("collapsed_for_agents", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("regexp_for_validation", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("title_in_portal", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("raw_title_in_portal", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("visible_in_portal", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("editable_in_portal", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("required_in_portal", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("tag", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("system_field_options", Schema.nullableOf(Schema.arrayOf(Schema.nullableOf(Schema.recordOf(
      "system_field_option",
      Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("position", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("raw_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("value", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("default", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN)))
    ))))),
    Schema.Field.of("custom_field_options", Schema.nullableOf(Schema.arrayOf(Schema.nullableOf(Schema.recordOf(
      "custom_field_option",
      Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("position", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("raw_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("value", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("default", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN)))
    ))))),
    Schema.Field.of("sub_type_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("removable", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("agent_description", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
  );

  public static final Schema SCHEMA_TICKET_METRICS = Schema.recordOf(
    "ticket_metrics",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("ticket_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("group_stations", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("assignee_stations", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("reopens", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("replies", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("assignee_updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("requester_updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("status_updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("initially_assigned_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("assigned_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("solved_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("latest_comment_added_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("first_resolution_time_in_minutes", Schema.nullableOf(Schema.recordOf(
      "first_resolution_time_in_minute",
      Schema.Field.of("calendar", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("business", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
    ))),
    Schema.Field.of("reply_time_in_minutes", Schema.nullableOf(Schema.recordOf(
      "reply_time_in_minute",
      Schema.Field.of("calendar", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("business", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
    ))),
    Schema.Field.of("full_resolution_time_in_minutes", Schema.nullableOf(Schema.recordOf(
      "full_resolution_time_in_minute",
      Schema.Field.of("calendar", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("business", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
    ))),
    Schema.Field.of("agent_wait_time_in_minutes", Schema.nullableOf(Schema.recordOf(
      "agent_wait_time_in_minute",
      Schema.Field.of("calendar", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("business", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
    ))),
    Schema.Field.of("requester_wait_time_in_minutes", Schema.nullableOf(Schema.recordOf(
      "requester_wait_time_in_minute",
      Schema.Field.of("calendar", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("business", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
    ))),
    Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
  );

  public static final Schema SCHEMA_TICKET_METRIC_EVENTS = Schema.recordOf(
    "ticket_metric_events",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("ticket_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("metric", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("instance_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("type", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("time", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("sla", Schema.nullableOf(Schema.recordOf(
      "sla_object",
      Schema.Field.of("target", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("business_hours", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
      Schema.Field.of("policy", Schema.nullableOf(Schema.recordOf(
        "policy_object",
        Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("title", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("description", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
      )))
    ))),
    Schema.Field.of("status", Schema.nullableOf(Schema.recordOf(
      "status_object",
      Schema.Field.of("calendar", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("business", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
    ))),
    Schema.Field.of("deleted", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN)))
  );

  public static final Schema SCHEMA_TICKETS = Schema.recordOf(
    "tickets",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("external_id", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("type", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("subject", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("raw_subject", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("description", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("priority", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("status", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("recipient", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("requester_id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("submitter_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("assignee_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("organization_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("group_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("collaborator_ids", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.LONG))))),
    Schema.Field.of("collaborators", Schema.nullableOf(Schema.arrayOf(Schema.nullableOf(Schema.recordOf(
      "collaborator",
      Schema.Field.of("name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("email", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
    ))))),
    Schema.Field.of("email_cc_ids", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.LONG))))),
    Schema.Field.of("follower_ids", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.LONG))))),
    Schema.Field.of("forum_topic_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("problem_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("has_incidents", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("due_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("tags", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.STRING))))),
    Schema.Field.of("via", Schema.nullableOf(Schema.recordOf(
      "via_object",
      Schema.Field.of("channel", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("source", Schema.nullableOf(Schema.recordOf(
        "source_object",
        Schema.Field.of("from", Schema.nullableOf(Schema.recordOf(
          "from_object",
          Schema.Field.of("profile_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("subject", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("service_info", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("title", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("topic_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
          Schema.Field.of("topic_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
          Schema.Field.of("revision_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
          Schema.Field.of("supports_channelback", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("address", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("supports_clickthrough", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("original_recipients", Schema.nullableOf(Schema.arrayOf(
            Schema.nullableOf(Schema.of(Schema.Type.STRING))))),
          Schema.Field.of("formatted_phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("ticket_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
          Schema.Field.of("facebook_id", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("registered_integration_service_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("deleted", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("username", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
        ))),
        Schema.Field.of("to", Schema.nullableOf(Schema.recordOf(
          "to_object",
          Schema.Field.of("address", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("profile_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("formatted_phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("username", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
          Schema.Field.of("facebook_id", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
        ))),
        Schema.Field.of("rel", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
      )))
    ))),
    Schema.Field.of("custom_fields", Schema.nullableOf(Schema.arrayOf(Schema.nullableOf(Schema.recordOf(
      "custom_field",
      Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("value", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
    ))))),
    Schema.Field.of("satisfaction_rating", Schema.nullableOf(Schema.recordOf(
      "satisfaction_rating_object",
      Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("assignee_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("group_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("requester_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("ticket_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("score", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("comment", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("reason", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("reason_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("reason_code", Schema.nullableOf(Schema.of(Schema.Type.LONG)))
    ))),
    Schema.Field.of("sharing_agreement_ids", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.LONG))))),
    Schema.Field.of("followup_ids", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.LONG))))),
    Schema.Field.of("via_followup_source_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("macro_ids", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.LONG))))),
    Schema.Field.of("ticket_form_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("brand_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("allow_channelback", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("allow_attachments", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("is_public", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING)))
  );

  public static final Schema SCHEMA_USERS = Schema.recordOf(
    "tickets",
    Schema.Field.of("id", Schema.of(Schema.Type.LONG)),
    Schema.Field.of("email", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("name", Schema.of(Schema.Type.STRING)),
    Schema.Field.of("active", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("alias", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("chat_only", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("created_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("custom_role_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("role_type", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("details", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("external_id", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("last_login_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("locale", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("locale_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("moderator", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("notes", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("only_public_comments", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("organization_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("default_group_id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
    Schema.Field.of("phone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("shared_phone_number", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("photo", Schema.nullableOf(Schema.recordOf(
      "attachment_object",
      Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("file_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("content_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("mapped_content_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("content_type", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
      Schema.Field.of("width", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("height", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("size", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
      Schema.Field.of("thumbnails", Schema.nullableOf(Schema.arrayOf(Schema.nullableOf(Schema.recordOf(
        "photo_object",
        Schema.Field.of("id", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("file_name", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("content_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("mapped_content_url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("content_type", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
        Schema.Field.of("width", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("height", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("size", Schema.nullableOf(Schema.of(Schema.Type.LONG))),
        Schema.Field.of("inline", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
        Schema.Field.of("deleted", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN)))
      ))))),
      Schema.Field.of("inline", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
      Schema.Field.of("deleted", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN)))
    ))),
    Schema.Field.of("restricted_agent", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("role", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("shared", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("shared_agent", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("signature", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("suspended", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("tags", Schema.nullableOf(Schema.arrayOf(
      Schema.nullableOf(Schema.of(Schema.Type.STRING))))),
    Schema.Field.of("ticket_restriction", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("time_zone", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("two_factor_auth_enabled", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("updated_at", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("url", Schema.nullableOf(Schema.of(Schema.Type.STRING))),
    Schema.Field.of("user_fields", Schema.nullableOf(Schema.mapOf(
      Schema.of(Schema.Type.STRING),
      Schema.of(Schema.Type.STRING)))),
    Schema.Field.of("verified", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN))),
    Schema.Field.of("report_csv", Schema.nullableOf(Schema.of(Schema.Type.BOOLEAN)))
  );
}
