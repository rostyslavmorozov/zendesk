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

import io.cdap.cdap.etl.api.validation.CauseAttributes;
import io.cdap.cdap.etl.api.validation.ValidationFailure;
import io.cdap.cdap.etl.mock.validation.MockFailureCollector;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class BaseZendeskBatchSourceConfigTest {

  private static final String MOCK_STAGE = "mockStage";

  @Test
  public void testGetZendeskBaseUrl() {
    String expected = "http://test.com/api/v2";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      expected);

    String actual = config.getZendeskBaseUrl();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testGetZendeskBaseUrlNull() {
    String expected = "https://%s.zendesk.com/api/v2/%s";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      null);

    String actual = config.getZendeskBaseUrl();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testGetZendeskBaseUrlEmpty() {
    String expected = "https://%s.zendesk.com/api/v2/%s";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "");

    String actual = config.getZendeskBaseUrl();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testValidate() {
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "https://%s.zendesk.com/api/v2/%s");

    MockFailureCollector collector = new MockFailureCollector(MOCK_STAGE);
    config.validate(collector);
    Assert.assertTrue(collector.getValidationFailures().isEmpty());
  }

  @Test
  public void testValidateEmptyDates() {
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Groups",
      "",
      "",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "https://%s.zendesk.com/api/v2/%s");

    MockFailureCollector collector = new MockFailureCollector(MOCK_STAGE);
    config.validate(collector);
    Assert.assertTrue(collector.getValidationFailures().isEmpty());
  }

  @Test
  public void testValidateBatchObject() {
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Organizations",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "https://%s.zendesk.com/api/v2/%s");

    MockFailureCollector collector = new MockFailureCollector(MOCK_STAGE);
    config.validate(collector);
    Assert.assertTrue(collector.getValidationFailures().isEmpty());
  }

  @Test
  public void testValidateStartDateForBatchObject() {
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Organizations",
      "",
      "",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "https://%s.zendesk.com/api/v2/%s");

    MockFailureCollector collector = new MockFailureCollector(MOCK_STAGE);
    config.validate(collector);

    Assert.assertEquals(1, collector.getValidationFailures().size());
    List<ValidationFailure.Cause> causeList = collector.getValidationFailures().get(0).getCauses();
    Assert.assertEquals(1, causeList.size());
    Assert.assertEquals(BaseZendeskBatchSourceConfig.PROPERTY_START_DATE, collector.getValidationFailures().get(0)
      .getCauses().get(0).getAttribute(CauseAttributes.STAGE_CONFIG));
  }

  @Test
  public void testInvalidStartDate() {
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Groups",
      "invalid",
      "",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "https://%s.zendesk.com/api/v2/%s");

    MockFailureCollector collector = new MockFailureCollector(MOCK_STAGE);
    config.validate(collector);

    Assert.assertEquals(1, collector.getValidationFailures().size());
    List<ValidationFailure.Cause> causeList = collector.getValidationFailures().get(0).getCauses();
    Assert.assertEquals(1, causeList.size());
    Assert.assertEquals(BaseZendeskBatchSourceConfig.PROPERTY_START_DATE, collector.getValidationFailures().get(0)
      .getCauses().get(0).getAttribute(CauseAttributes.STAGE_CONFIG));
  }

  @Test
  public void testInvalidEndDate() {
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      "subdomain",
      "Groups",
      "",
      "invalid",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "https://%s.zendesk.com/api/v2/%s");

    MockFailureCollector collector = new MockFailureCollector(MOCK_STAGE);
    config.validate(collector);

    Assert.assertEquals(1, collector.getValidationFailures().size());
    List<ValidationFailure.Cause> causeList = collector.getValidationFailures().get(0).getCauses();
    Assert.assertEquals(1, causeList.size());
    Assert.assertEquals(BaseZendeskBatchSourceConfig.PROPERTY_END_DATE, collector.getValidationFailures().get(0)
      .getCauses().get(0).getAttribute(CauseAttributes.STAGE_CONFIG));
  }
}
