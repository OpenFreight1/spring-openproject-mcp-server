package de.tklein.tklab.openproject.mcp.tools;

import de.tklein.tklab.openproject.mcp.dto.VersionDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import jakarta.validation.constraints.NotNull;
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
public class VersionTools {

  private final OpenProjectApiClient openProjectApiClient;

  public VersionTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(
      description = "List existing versions (e.g. sprints/milestones) for a project. The version id is used by 'workPackageSetVersion'.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<VersionDto> versionList(@NotNull Integer projectId) {
    return openProjectApiClient.versionList(projectId);
  }

}
