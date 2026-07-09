package de.tklein.tklab.openproject.mcp.tools;

import de.tklein.tklab.openproject.mcp.dto.UserDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import lombok.extern.log4j.Log4j2;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpTool.McpAnnotations;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Log4j2
@Validated
@Component
public class UserTools {

  private final OpenProjectApiClient openProjectApiClient;

  public UserTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(
      description = "Returns the OpenProject user authenticated by the current API token (name + API href). Use 'assigneeId=\"me\"' in workPackageList / workPackageAssign to reference this same user without resolving an ID first.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public UserDto currentUser() {
    return openProjectApiClient.root();
  }

}
