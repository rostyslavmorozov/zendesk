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

package io.cdap.plugin.zendesk.source.batch.http;

import com.google.common.collect.ImmutableMap;
import io.cdap.plugin.zendesk.source.batch.BaseZendeskBatchSourceConfig;
import io.cdap.plugin.zendesk.source.common.ObjectType;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class PagedIteratorTest {

  @Test
  public void testGetNextPage() throws IOException {
    String expected = "expected_page";

    ObjectType objectType = ObjectType.GROUPS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(ImmutableMap.of("next_page", expected));
      Assert.assertEquals(expected, actual);
    }
  }

  @Test
  public void testGetNextPageNull() throws IOException {
    ObjectType objectType = ObjectType.GROUPS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(new HashMap<>());
      Assert.assertNull(actual);
    }
  }

  @Test
  public void testGetNextPageBatch() throws IOException {
    String expected = "expected_page";
    long endTime = TimeUnit.MILLISECONDS.toSeconds(
      System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(6));
    int count = 1000;

    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(ImmutableMap.of("next_page", expected,
                                                                "end_time", endTime,
                                                                "count", count));
      Assert.assertEquals(expected, actual);
    }
  }

  @Test
  public void testGetNextPageBatchNull() throws IOException {
    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(new HashMap<>());
      Assert.assertNull(actual);
    }
  }

  @Test
  public void testGetNextPageBatchEndTimeNull() throws IOException {
    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(ImmutableMap.of("next_page", "expected_page"));
      Assert.assertNull(actual);
    }
  }

  @Test
  public void testGetNextPageBatchEndTimeZero() throws IOException {
    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(ImmutableMap.of("next_page", "expected_page",
                                                                "end_time", 0));
      Assert.assertNull(actual);
    }
  }

  @Test
  public void testGetNextPageBatchEndTime1MinuteAgo() throws IOException {
    long endTime = TimeUnit.MILLISECONDS.toSeconds(
      System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(4));

    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(ImmutableMap.of("next_page", "expected_page",
                                                                "end_time", endTime));
      Assert.assertNull(actual);
    }
  }

  @Test
  public void testGetNextPageBatchCountNull() throws IOException {
    long endTime = TimeUnit.MILLISECONDS.toSeconds(
      System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(6));

    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(ImmutableMap.of("next_page", "expected_page",
                                                                "end_time", endTime));
      Assert.assertNull(actual);
    }
  }

  @Test
  public void testGetNextPageBatchCountZero() throws IOException {
    long endTime = TimeUnit.MILLISECONDS.toSeconds(
      System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(6));

    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(ImmutableMap.of("next_page", "expected_page",
                                                                "end_time", endTime,
                                                                "count", 0));
      Assert.assertNull(actual);
    }
  }

  @Test
  public void testGetNextPageBatchCountLessThan1000() throws IOException {
    long endTime = TimeUnit.MILLISECONDS.toSeconds(
      System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(6));
    int count = 999;

    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      String actual = pagedIterator.getNextPage(ImmutableMap.of("next_page", "expected_page",
                                                                "end_time", endTime,
                                                                "count", count));
      Assert.assertNull(actual);
    }
  }

  @Test
  public void testGetJsonValuesFromResponse() throws IOException {
    ImmutableMap<String, Object> response = ImmutableMap.of(
      "organizations", Arrays.asList(
        ImmutableMap.of("key", "val1"),
        ImmutableMap.of("key", "val2")));
    List<String> expected = new ArrayList<>();
    expected.add("{\"key\":\"val1\"}");
    expected.add("{\"key\":\"val2\"}");

    ObjectType objectType = ObjectType.ORGANIZATIONS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      Iterator<String> result = pagedIterator.getJsonValuesFromResponse(response);
      List<String> actual = new ArrayList<>();
      result.forEachRemaining(actual::add);

      Assert.assertEquals(expected, actual);
    }
  }

  @Test
  public void testGetJsonValuesFromResponseWithChildKey() throws IOException {
    ImmutableMap<String, Object> response = ImmutableMap.of(
      "ticket_events",
      Collections.singletonList(ImmutableMap.of(
        "child_events", Arrays.asList(
          ImmutableMap.of("key", "val1"),
          ImmutableMap.of("key", "val2")))));
    List<String> expected = new ArrayList<>();
    expected.add("{\"key\":\"val1\"}");
    expected.add("{\"key\":\"val2\"}");

    ObjectType objectType = ObjectType.TICKET_COMMENTS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain)) {
      Iterator<String> result = pagedIterator.getJsonValuesFromResponse(response);
      List<String> actual = new ArrayList<>();
      result.forEachRemaining(actual::add);

      Assert.assertEquals(expected, actual);
    }
  }

  @Test
  public void testHasNext() throws IOException {
    ImmutableMap<String, Object> response = ImmutableMap.of(
      "groups", Arrays.asList(
        ImmutableMap.of("key", "val1"),
        ImmutableMap.of("key", "val2")));

    ObjectType objectType = ObjectType.GROUPS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain) {
      @Override
      Map<String, Object> getResponseAsMap() {
        return response;
      }
    }) {
      boolean actual = pagedIterator.hasNext();
      Assert.assertTrue(actual);

      String next = pagedIterator.next();
      Assert.assertEquals("{\"key\":\"val1\"}", next);
    }
  }

  @Test
  public void testHasNextPagination() throws IOException {
    Map<String, Object> response = new HashMap<>();
    response.put("groups", Collections.singletonList(
      ImmutableMap.of("key", "val1")));
    response.put("next_page", "some_page");

    ObjectType objectType = ObjectType.GROUPS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain) {
      @Override
      Map<String, Object> getResponseAsMap() {
        return response;
      }
    }) {
      boolean actual = pagedIterator.hasNext();
      Assert.assertTrue(actual);

      String next = pagedIterator.next();
      Assert.assertEquals("{\"key\":\"val1\"}", next);

      response.put("groups", Collections.singletonList(
        ImmutableMap.of("key", "val2")));
      response.put("next_page", "null");

      actual = pagedIterator.hasNext();
      Assert.assertTrue(actual);

      next = pagedIterator.next();
      Assert.assertEquals("{\"key\":\"val2\"}", next);

      actual = pagedIterator.hasNext();
      Assert.assertFalse(actual);
    }
  }

  @Test
  public void testHasNextCurrentEmptyNextPageNull() throws IOException {
    ImmutableMap<String, Object> response = ImmutableMap.of(
      "groups", Collections.singletonList(
        ImmutableMap.of("key", "val1")));

    ObjectType objectType = ObjectType.GROUPS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain) {
      @Override
      Map<String, Object> getResponseAsMap() {
        return response;
      }
    }) {
      boolean actual = pagedIterator.hasNext();
      Assert.assertTrue(actual);

      String next = pagedIterator.next();
      Assert.assertEquals("{\"key\":\"val1\"}", next);

      actual = pagedIterator.hasNext();
      Assert.assertFalse(actual);
    }
  }

  @Test
  public void testHasNextCurrentEmptyNextPageNullString() throws IOException {
    ImmutableMap<String, Object> response = ImmutableMap.of(
      "groups", Collections.singletonList(
        ImmutableMap.of("key", "val1")),
      "next_page", "null");

    ObjectType objectType = ObjectType.GROUPS;
    String subdomain = "subdomain";
    BaseZendeskBatchSourceConfig config = new BaseZendeskBatchSourceConfig(
      "reference",
      "email@test.com",
      "apiToken",
      subdomain,
      "Groups",
      "2019-01-01T23:01:01Z",
      "2019-01-01T23:01:01Z",
      "satisfactionRatingsScore",
      20,
      240,
      100,
      300,
      300,
      "http://%s.localhosttestdomain/%s");

    try (PagedIterator pagedIterator = new PagedIterator(config, objectType, subdomain) {
      @Override
      Map<String, Object> getResponseAsMap() {
        return response;
      }
    }) {
      boolean actual = pagedIterator.hasNext();
      Assert.assertTrue(actual);

      String next = pagedIterator.next();
      Assert.assertEquals("{\"key\":\"val1\"}", next);

      actual = pagedIterator.hasNext();
      Assert.assertFalse(actual);
    }
  }
}
