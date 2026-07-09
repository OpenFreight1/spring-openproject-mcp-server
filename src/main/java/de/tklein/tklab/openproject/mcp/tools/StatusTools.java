package de.tklein.tklab.openproject.mcp.tools;

import de.tklein.tklab.openproject.mcp.dto.StatusDto;
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
public class StatusTools {

  private final OpenProjectApiClient openProjectApiClient;

  public StatusTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(
      description = "List existing work package statuses (e.g. 'New', 'In progress', 'Closed'). The status id is used by 'workPackageChangeStatus'.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<StatusDto> statusList() {
    return openProjectApiClient.statusList();
  }

}
