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

import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import com.github.rholder.retry.WaitStrategies;
import com.google.common.base.Strings;
import io.cdap.plugin.zendesk.source.batch.BaseZendeskBatchSourceConfig;
import io.cdap.plugin.zendesk.source.common.ObjectType;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * A class which contains utilities to build http specific resources.
 */
public class HttpUtil {

  public static CloseableHttpClient createHttpClient(BaseZendeskBatchSourceConfig config) {
    HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
    Long connectTimeoutMillis = TimeUnit.SECONDS.toMillis(config.getConnectTimeout());
    Long readTimeoutMillis = TimeUnit.SECONDS.toMillis(config.getReadTimeout());
    RequestConfig.Builder requestBuilder = RequestConfig.custom();
    requestBuilder.setSocketTimeout(readTimeoutMillis.intValue());
    requestBuilder.setConnectTimeout(connectTimeoutMillis.intValue());
    requestBuilder.setConnectionRequestTimeout(connectTimeoutMillis.intValue());
    httpClientBuilder.setDefaultRequestConfig(requestBuilder.build());
    return httpClientBuilder.build();
  }

  public static HttpClientContext createHttpContext(BaseZendeskBatchSourceConfig config, String url) {
    String adminEmail = config.getAdminEmail();
    String apiToken = config.getApiToken();
    URI uri = URI.create(url);
    CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    AuthCache authCache = new BasicAuthCache();
    if (!Strings.isNullOrEmpty(adminEmail) && !Strings.isNullOrEmpty(apiToken)
      && !Strings.isNullOrEmpty(url)) {
      HttpHost targetHost = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
      AuthScope authScope = new AuthScope(targetHost);
      credentialsProvider.setCredentials(
        authScope,
        new UsernamePasswordCredentials(adminEmail + "/token", apiToken));

      authCache.put(targetHost, new BasicScheme());
    }
    HttpClientContext context = HttpClientContext.create();
    context.setCredentialsProvider(credentialsProvider);
    context.setAuthCache(authCache);
    return context;
  }

  public static String createFirstPageUrl(BaseZendeskBatchSourceConfig config,
                                          ObjectType objectType, String subdomain) {
    List<String> additionalParams = new ArrayList<>();
    if (objectType.isBatch()) {
      long epochSecond = getEpochSecond(config.getStartDate());
      additionalParams.add(String.format("start_time=%s", epochSecond));
    }
    if (objectType == ObjectType.SATISFACTION_RATINGS) {
      if (!StringUtils.isBlank(config.getStartDate())) {
        long epochSecond = getEpochSecond(config.getStartDate());
        additionalParams.add(String.format("start_time=%s", epochSecond));
      }
      if (!StringUtils.isBlank(config.getEndDate())) {
        long epochSecond = getEpochSecond(config.getEndDate());
        additionalParams.add(String.format("end_time=%s", epochSecond));
      }
      if (!StringUtils.isBlank(config.getSatisfactionRatingsScore())) {
        additionalParams.add(String.format("score=%s", config.getSatisfactionRatingsScore()));
      }
    }
    String baseUrl = String.format(config.getZendeskBaseUrl(), subdomain, objectType.getApiEndpoint());
    if (!additionalParams.isEmpty()) {
      String additionalParamsString = String.join("&", additionalParams);
      baseUrl = String.format(baseUrl.contains("?") ? "%s&%s" : "%s?%s", baseUrl, additionalParamsString);
    }
    return baseUrl;
  }

  public static Retryer<Map<String, Object>> buildRetryer(BaseZendeskBatchSourceConfig config) {
    return RetryerBuilder.<Map<String, Object>>newBuilder()
      .retryIfExceptionOfType(RateLimitException.class)
      .withWaitStrategy(WaitStrategies.join(
        WaitStrategies.exponentialWait(config.getMaxRetryWait(), TimeUnit.SECONDS),
        WaitStrategies.randomWait(config.getMaxRetryJitterWait(), TimeUnit.MILLISECONDS)))
      .withStopStrategy(StopStrategies.stopAfterAttempt(config.getMaxRetryCount()))
      .build();
  }

  private static long getEpochSecond(String aDate) {
    ZonedDateTime zonedDateTime = ZonedDateTime.parse(aDate, DateTimeFormatter.ISO_DATE_TIME);
    return zonedDateTime.toEpochSecond();
  }
}
