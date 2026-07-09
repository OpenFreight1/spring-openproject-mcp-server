package de.tklein.tklab.openproject.mcp.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import de.tklein.tklab.openproject.mcp.dto.VersionDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
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

  @McpTool(
      description = "Fetches a version by id.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public VersionDto versionShow(@NotNull Integer versionId) {
    return openProjectApiClient.versionShow(versionId);
  }

  @McpTool(description = "Creates a new version (e.g. sprint/milestone) in a project.")
  public Integer versionCreate(@NotNull Integer projectId, @NotNull String name,
      @JsonPropertyDescription("Optional version description.") @JsonProperty String description,
      @JsonPropertyDescription("Optional start date, YYYY-MM-DD.") @JsonProperty LocalDate startDate,
      @JsonPropertyDescription("Optional end date, YYYY-MM-DD.") @JsonProperty LocalDate endDate) {
    return openProjectApiClient.versionCreate(projectId, name, description, startDate, endDate);
  }

  @McpTool(description = "Updates a version's name, description and/or dates. Omitted fields are left unchanged.")
  public boolean versionUpdate(@NotNull Integer versionId,
      @JsonPropertyDescription("New name, or omit to leave unchanged.") @JsonProperty String name,
      @JsonPropertyDescription("New description, or omit to leave unchanged.") @JsonProperty String description,
      @JsonPropertyDescription("New start date, YYYY-MM-DD, or omit to leave unchanged.") @JsonProperty LocalDate startDate,
      @JsonPropertyDescription("New end date, YYYY-MM-DD, or omit to leave unchanged.") @JsonProperty LocalDate endDate) {
    return openProjectApiClient.versionUpdate(versionId, name, description, startDate, endDate);
  }

  @McpTool(description = "Deletes a version. Work packages assigned to it are unassigned from it.")
  public boolean versionDelete(@NotNull Integer versionId) {
    return openProjectApiClient.versionDelete(versionId);
  }

}
