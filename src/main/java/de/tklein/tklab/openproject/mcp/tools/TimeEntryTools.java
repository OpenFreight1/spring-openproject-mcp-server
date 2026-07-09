package de.tklein.tklab.openproject.mcp.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import de.tklein.tklab.openproject.mcp.dto.TimeEntryActivityDto;
import de.tklein.tklab.openproject.mcp.dto.TimeEntryDto;
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
public class TimeEntryTools {

  private final OpenProjectApiClient openProjectApiClient;

  public TimeEntryTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(
      description = "List existing time entry activities (e.g. 'Development', 'Management'). The activity id can optionally be passed to 'workPackageLogTime'.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<TimeEntryActivityDto> timeEntryActivityList() {
    return openProjectApiClient.timeEntryActivityList();
  }

  @McpTool(
      description = "Fetches a time entry by id.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public TimeEntryDto timeEntryShow(@NotNull Integer timeEntryId) {
    return openProjectApiClient.timeEntryShow(timeEntryId);
  }

  @McpTool(description = "Updates a logged time entry's hours, comment and/or date. Omitted fields are left unchanged.")
  public boolean timeEntryUpdate(@NotNull Integer timeEntryId,
      @JsonPropertyDescription("New time in ISO 8601 duration format, or omit to leave unchanged.") @JsonProperty String hours,
      @JsonPropertyDescription("New comment, or omit to leave unchanged.") @JsonProperty String comment,
      @JsonPropertyDescription("New date spent, YYYY-MM-DD, or omit to leave unchanged.") @JsonProperty LocalDate spentOn) {
    return openProjectApiClient.timeEntryUpdate(timeEntryId, hours, comment, spentOn);
  }

  @McpTool(description = "Deletes a logged time entry.")
  public boolean timeEntryDelete(@NotNull Integer timeEntryId) {
    return openProjectApiClient.timeEntryDelete(timeEntryId);
  }

}
