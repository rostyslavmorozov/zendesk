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

package io.cdap.plugin.zendesk.source.common.config;

import com.google.common.base.Strings;
import io.cdap.cdap.api.annotation.Description;
import io.cdap.cdap.api.annotation.Macro;
import io.cdap.cdap.api.annotation.Name;
import io.cdap.cdap.etl.api.FailureCollector;
import io.cdap.plugin.common.IdUtils;
import io.cdap.plugin.common.ReferencePluginConfig;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Base configuration for Zendesk Batch plugins.
 */
public class BaseZendeskSourceConfig extends ReferencePluginConfig {

  public static final String PROPERTY_ADMIN_EMAIL = "adminEmail";
  public static final String PROPERTY_API_TOKEN = "apiToken";
  public static final String PROPERTY_SUBDOMAINS = "subdomains";
  public static final String PROPERTY_OBJECTS_TO_PULL = "objectsToPull";

  @Name(PROPERTY_ADMIN_EMAIL)
  @Description("Zendesk admin email.")
  @Macro
  private String adminEmail;

  @Name(PROPERTY_API_TOKEN)
  @Description("Zendesk API token.")
  @Macro
  private String apiToken;

  @Name(PROPERTY_SUBDOMAINS)
  @Description("Zendesk Subdomains to read objects from.")
  @Macro
  private String subdomains;

  @Name(PROPERTY_OBJECTS_TO_PULL)
  @Description("Objects to pull from Zendesk API.")
  private String objectsToPull;

  public BaseZendeskSourceConfig(String referenceName,
                                 String adminEmail,
                                 String apiToken,
                                 String subdomains,
                                 String objectsToPull) {
    super(referenceName);
    this.adminEmail = adminEmail;
    this.apiToken = apiToken;
    this.subdomains = subdomains;
    this.objectsToPull = objectsToPull;
  }

  public String getAdminEmail() {
    return adminEmail;
  }

  public String getApiToken() {
    return apiToken;
  }

  public Set<String> getSubdomains() {
    return getList(subdomains);
  }

  public Set<String> getObjectsToPull() {
    return getList(objectsToPull);
  }

  public void validate(FailureCollector collector) {
    IdUtils.validateReferenceName(referenceName, collector);
    if (!EmailValidator.getInstance().isValid(adminEmail)) {
      collector.addFailure(String.format("Email '%s' is invalid.", adminEmail), null)
        .withConfigProperty(PROPERTY_ADMIN_EMAIL);
    }
  }

  protected Set<String> getList(String value) {
    return Strings.isNullOrEmpty(value)
      ? Collections.emptySet()
      : Stream.of(value.split(","))
      .map(String::trim)
      .filter(name -> !name.isEmpty())
      .collect(Collectors.toSet());
  }
}
