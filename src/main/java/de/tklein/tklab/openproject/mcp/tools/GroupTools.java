package de.tklein.tklab.openproject.mcp.tools;

import de.tklein.tklab.openproject.mcp.dto.GroupDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import java.util.List;
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
public class GroupTools {

  private final OpenProjectApiClient openProjectApiClient;

  public GroupTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(
      description = "List visible OpenProject groups (of users).",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<GroupDto> groupList() {
    return openProjectApiClient.groupList();
  }

}
