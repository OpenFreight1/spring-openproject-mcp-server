package de.tklein.tklab.openproject.mcp.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import de.tklein.tklab.openproject.mcp.dto.AttachmentDto;
import de.tklein.tklab.openproject.mcp.dto.BulkItemResultDto;
import de.tklein.tklab.openproject.mcp.dto.ReminderDto;
import de.tklein.tklab.openproject.mcp.dto.TimeEntryDto;
import de.tklein.tklab.openproject.mcp.dto.UserDto;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageBulkUpdateItemDto;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageCreateDto;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageCreateDto.OnCreate;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageDto;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageUpdateDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpPrompt;
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
public class WorkPackageTools {

  private final OpenProjectApiClient openProjectApiClient;

  public WorkPackageTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(
      description = "Queries the work-packages by projectId, optionally filtered by assignee and/or status.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<WorkPackageDto> workPackageList(@NotNull Integer projectId,
      @JsonPropertyDescription("Optional filter: numerical user id (see 'currentUser'/'workPackageShow'), or the literal string 'me' for the authenticated user's own work packages.") @JsonProperty String assigneeId,
      @JsonPropertyDescription("Optional filter: numerical status id from MCP tool 'statusList'.") @JsonProperty Integer statusId) {
    return openProjectApiClient.workPackageList(projectId, assigneeId, statusId);
  }

  @McpTool(
      description = "Gets the work-package by id 'wpId'.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public WorkPackageDto workPackageShow(@NotNull Integer workPackageId) {
    return openProjectApiClient.workPackageShow(workPackageId);
  }

  @McpTool(
      description = "Creates a new work-package for a project. Requires projectId and typeId (e.g. 1 for Task).")
  @Validated({OnCreate.class, Default.class})
  public Integer workPackageCreate(@NotNull Integer projectId,
      @Valid @NotNull WorkPackageCreateDto workPackage) {
    return openProjectApiClient.workPackageCreate(projectId, workPackage);
  }

  @McpTool(
      description = "Updates a work-package for a project. Requires projectId and typeId (e.g. 1 for Task).")
  public boolean workPackageUpdate(@NotNull Integer workPackageId,
      @Valid @NotNull WorkPackageUpdateDto workPackage) {
    return openProjectApiClient.workPackageUpdate(workPackageId, workPackage);
  }

  @McpTool(
      description = "Assigns a work package to a user, or unassigns it if userId is omitted/null. Use 'currentUser' to resolve your own id for self-assignment.")
  public boolean workPackageAssign(@NotNull Integer workPackageId,
      @JsonPropertyDescription("The numerical user id to assign, or omit/null to unassign.") @JsonProperty Integer userId) {
    return openProjectApiClient.workPackageAssign(workPackageId, userId);
  }

  @McpTool(
      description = "Changes a work package's status. Use 'statusList' to resolve status names to ids.")
  public boolean workPackageChangeStatus(@NotNull Integer workPackageId,
      @JsonPropertyDescription("The numerical status id from MCP tool 'statusList'.") @NotNull Integer statusId) {
    return openProjectApiClient.workPackageChangeStatus(workPackageId, statusId);
  }

  @McpTool(
      description = "Sets a work package's version (e.g. sprint/milestone), or clears it if versionId is omitted/null. Use 'versionList' to resolve names to ids.")
  public boolean workPackageSetVersion(@NotNull Integer workPackageId,
      @JsonPropertyDescription("The numerical version id from MCP tool 'versionList', or omit/null to clear.") @JsonProperty Integer versionId) {
    return openProjectApiClient.workPackageSetVersion(workPackageId, versionId);
  }

  @McpTool(
      description = "Sets a work package's category, or clears it if categoryId is omitted/null. Use 'categoryList' to resolve names to ids.")
  public boolean workPackageSetCategory(@NotNull Integer workPackageId,
      @JsonPropertyDescription("The numerical category id from MCP tool 'categoryList', or omit/null to clear.") @JsonProperty Integer categoryId) {
    return openProjectApiClient.workPackageSetCategory(workPackageId, categoryId);
  }

  @McpTool(
      description = "Logs time spent on a work package. spentOn defaults to today if omitted. Use 'timeEntryActivityList' to resolve an optional activity id.")
  public Integer workPackageLogTime(@NotNull Integer workPackageId,
      @JsonPropertyDescription("Time spent in ISO 8601 duration format (e.g. 'PT2H30M' for 2.5 hours).") @NotNull String hours,
      @JsonPropertyDescription("Optional comment describing the logged time.") @JsonProperty String comment,
      @JsonPropertyDescription("The date the time was spent, in YYYY-MM-DD format. Defaults to today if omitted.") @JsonProperty LocalDate spentOn,
      @JsonPropertyDescription("Optional numerical time entry activity id from MCP tool 'timeEntryActivityList'.") @JsonProperty Integer activityId) {
    return openProjectApiClient.workPackageLogTime(workPackageId, hours, comment, spentOn,
        activityId);
  }

  @McpTool(
      description = "Lists the time entries logged against a work package.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<TimeEntryDto> workPackageTimeEntries(@NotNull Integer workPackageId) {
    return openProjectApiClient.timeEntryList(workPackageId);
  }

  @McpTool(
      description = "Adds a comment (activity) to a work package. Does not change the work package's description, status or any other field.")
  public Integer workPackageAddComment(@NotNull Integer workPackageId, @NotNull String comment) {
    return openProjectApiClient.workPackageAddComment(workPackageId, comment);
  }

  @McpTool(
      description = "Searches work packages by subject text (contains match), optionally scoped to a project and/or filtered by status/assignee.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<WorkPackageDto> searchWorkPackages(@NotNull String query,
      @JsonPropertyDescription("Optional project id to scope the search to.") @JsonProperty Integer projectId,
      @JsonPropertyDescription("Optional status id filter from MCP tool 'statusList'.") @JsonProperty Integer statusId,
      @JsonPropertyDescription("Optional assignee filter: numerical user id, or the literal string 'me'.") @JsonProperty String assigneeId) {
    return openProjectApiClient.searchWorkPackages(query, projectId, statusId, assigneeId);
  }

  @McpTool(
      description = "Creates multiple work packages in one call. Returns per-item results including any errors; one failed item does not abort the others.")
  @Validated({OnCreate.class, Default.class})
  public List<BulkItemResultDto> workPackagesBulkCreate(@NotNull Integer projectId,
      @Valid @NotNull List<WorkPackageCreateDto> workPackages) {
    return openProjectApiClient.workPackagesBulkCreate(projectId, workPackages);
  }

  @McpTool(
      description = "Updates multiple work packages in one call. Each item must include its own lockVersion. Returns per-item results including any errors; one failed item does not abort the others.")
  public List<BulkItemResultDto> workPackagesBulkUpdate(
      @Valid @NotNull List<WorkPackageBulkUpdateItemDto> items) {
    return openProjectApiClient.workPackagesBulkUpdate(items);
  }

  @McpTool(
      description = "Lists attachments on a work package.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<AttachmentDto> workPackageAttachments(@NotNull Integer workPackageId) {
    return openProjectApiClient.workPackageAttachmentList(workPackageId);
  }

  @McpTool(description = "Deletes an attachment by its id (see 'workPackageAttachments').")
  public boolean attachmentDelete(@NotNull Integer attachmentId) {
    return openProjectApiClient.attachmentDelete(attachmentId);
  }

  @McpTool(
      description = "Lists the watchers (users subscribed to notifications) on a work package.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<UserDto> workPackageWatchers(@NotNull Integer workPackageId) {
    return openProjectApiClient.workPackageWatcherList(workPackageId);
  }

  @McpTool(description = "Adds a user as a watcher on a work package. Use 'userList'/'currentUser' to resolve a user id.")
  public boolean workPackageAddWatcher(@NotNull Integer workPackageId, @NotNull Integer userId) {
    return openProjectApiClient.workPackageAddWatcher(workPackageId, userId);
  }

  @McpTool(description = "Removes a user from the watchers of a work package.")
  public boolean workPackageRemoveWatcher(@NotNull Integer workPackageId, @NotNull Integer userId) {
    return openProjectApiClient.workPackageRemoveWatcher(workPackageId, userId);
  }

  @McpTool(
      description = "Lists the current user's active reminders on a work package.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<ReminderDto> workPackageReminders(@NotNull Integer workPackageId) {
    return openProjectApiClient.workPackageReminderList(workPackageId);
  }

  @McpTool(
      description = "Creates a reminder on a work package for the current user. A work package can have only one active reminder per user.")
  public Integer workPackageAddReminder(@NotNull Integer workPackageId,
      @JsonPropertyDescription("The date and time the reminder should fire, ISO 8601 (e.g. '2026-08-01T09:00:00Z').") @NotNull String remindAt,
      @JsonPropertyDescription("Optional note for the reminder.") @JsonProperty String note) {
    return openProjectApiClient.workPackageAddReminder(workPackageId, remindAt, note);
  }

  @McpTool(description = "Deletes a reminder by its id (see 'workPackageReminders').")
  public boolean reminderDelete(@NotNull Integer reminderId) {
    return openProjectApiClient.reminderDelete(reminderId);
  }

  @McpTool(
      description = "Uploads an attachment to a work package. Provide workPackageId, fileName and base64-encoded fileContent. Optionally provide fileContentType.")
  public Integer workPackageUploadAttachment(@NotNull Integer workPackageId,
      @NotNull String fileName,
      @NotNull String fileContentBase64, @NotNull String fileContentType) {

    byte[] bytes;
    try {
      bytes = Base64.getDecoder().decode(fileContentBase64);
    } catch (IllegalArgumentException _) {
      throw new IllegalArgumentException("'fileContentBase64' must be valid Base64");
    }
    return openProjectApiClient.workPackageUploadAttachment(workPackageId, fileName, bytes,
        fileContentType);
  }

  @McpPrompt(
      name = "openproject.workpackage.safe_edit",
      description = "Safe edit workflow for OpenProject Work Packages (diff + confirm, lockVersion handling, Markdown newline directive).")
  public GetPromptResult safeEditWorkPackagePrompt(
      @McpArg(name = "workPackageId", description = "ID of the OpenProject Work Package to modify.", required = true)
      String workPackageId,
      @McpArg(name = "requestedChange", description = "Natural language description of what should be changed.", required = true)
      String requestedChange
  ) {
    String prompt = """
        You are an MCP assistant connected to OpenProject. Your job is to propose and apply safe updates to Work Packages.
        
        Target Work Package:
        - workPackageId: %s
        
        User request:
        - requestedChange: %s
        
        ## Critical formatting rule (Markdown newlines)
        - When you output or construct Markdown for Work Package text/description fields, represent line breaks using the literal two-character sequence /n.
        - Do NOT use //n.
        - Only deviate from this rule if the user explicitly revokes this directive (they must clearly say so). If revoked, follow the user's new instruction exactly.
        
        ## Concurrency & locking (lockVersion)
        - For any work_package_update, you MUST include the property lockVersion from the latest known server state of that Work Package.
        - After performing an update, treat your local Work Package state as stale.
          - If further changes are needed, reload/refetch the Work Package first and use the newest lockVersion.
        
        ## Safe-change workflow (Diff + explicit confirmation)
        1. Understand the requested change.
        2. Fetch the current Work Package (or ensure you have the latest state).
        3. Prepare a patch proposal and show it as a unified diff (old vs new), including all affected fields.
           - If editing Markdown text, ensure newlines are expressed as /n in the proposed new text.
        4. Ask for explicit confirmation before calling work_package_update.
           - Accept only an unambiguous confirmation like: "Yes, apply", "Confirm", or "Proceed".
           - If the user requests modifications, revise the diff and ask again.
        5. On confirmation:
           - Execute work_package_update using the newest lockVersion.
        6. After update:
           - If you need to do anything else (additional edits, verification, follow-up changes), reload/refetch the Work Package first.
        
        ## Output requirements
        - Always present the diff BEFORE any update call.
        - Never apply updates without confirmation.
        - If there is a lockVersion conflict, reload/refetch, regenerate the diff, and re-request confirmation.
        """.formatted(workPackageId, requestedChange);

    return new GetPromptResult(
        "OpenProject Work Package Safe Edit",
        List.of(new PromptMessage(Role.ASSISTANT, new TextContent(prompt)))
    );
  }
}
