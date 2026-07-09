package de.tklein.tklab.openproject.mcp.tools;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.tklein.tklab.openproject.mcp.dto.WorkPackageCreateDto;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageUpdateDto;
import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Tests each method in WorkPackageTools. Constraint with inheritance in WorkPackageUpdateDto and
 * WorkPackageCreateDto might have unexpected results if Group 'OnCreate' is used incorrectly!
 */
@SpringBootTest
class WorkPackageToolsTest {

  @Autowired
  private WorkPackageTools workPackageTools;

  @Test
  void workPackageList_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageList(null, null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageList.projectId"
        );
  }

  @Test
  void workPackageShow_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageShow(null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageShow.workPackageId"
        );
  }

  @Test
  void workPackageCreate_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageCreate(null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageCreate.projectId",
            "workPackageCreate.workPackage"
        );
  }

  @Test
  void workPackageCreate_validationExceptions_WithWorkPackage() {
    var wp = new WorkPackageCreateDto();
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageCreate(123, wp));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageCreate.workPackage.subject",
            "workPackageCreate.workPackage.typeId"
        );
  }

  @Test
  void workPackageUpdate_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageUpdate(null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageUpdate.workPackageId",
            "workPackageUpdate.workPackage"
        );
  }

  @Test
  void workPackageUpdate_validationExceptions_WithWorkPackage() {
    var wp = new WorkPackageUpdateDto();
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageUpdate(123, wp));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageUpdate.workPackage.lockVersion"
            // and not subject or typeId from superclass annotation !!
        );
  }

  @Test
  void workPackageSetVersion_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageSetVersion(null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageSetVersion.workPackageId"
            // versionId is intentionally nullable (null = clear)
        );
  }

  @Test
  void workPackageSetCategory_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageSetCategory(null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageSetCategory.workPackageId"
            // categoryId is intentionally nullable (null = clear)
        );
  }

  @Test
  void workPackageLogTime_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageLogTime(null, null, null, null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageLogTime.workPackageId",
            "workPackageLogTime.hours"
            // comment/spentOn/activityId are optional
        );
  }

  @Test
  void workPackageTimeEntries_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageTimeEntries(null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageTimeEntries.workPackageId"
        );
  }

  @Test
  void workPackageAssign_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageAssign(null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageAssign.workPackageId"
            // userId is intentionally nullable (null = unassign)
        );
  }

  @Test
  void workPackageChangeStatus_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageChangeStatus(null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageChangeStatus.workPackageId",
            "workPackageChangeStatus.statusId"
        );
  }

  @Test
  void workPackageList_withOptionalFiltersOmitted_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageList(null, "me", null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageList.projectId"
            // assigneeId/statusId are optional filters, not required
        );
  }

  @Test
  void workPackageAddComment_validationExceptions() {
    var ex = assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageAddComment(null, null));
    assertThat(ex.getConstraintViolations())
        .extracting(v -> v.getPropertyPath().toString())
        .containsExactlyInAnyOrder(
            "workPackageAddComment.workPackageId",
            "workPackageAddComment.comment"
        );
  }

  @Test
  void workPackageUploadAttachment_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageUploadAttachment(null, null, null, null));
  }

  @Test
  void searchWorkPackages_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.searchWorkPackages(null, null, null, null));
  }

  @Test
  void workPackagesBulkCreate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackagesBulkCreate(null, null));
  }

  @Test
  void workPackagesBulkUpdate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackagesBulkUpdate(null));
  }

  @Test
  void workPackageAttachments_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageAttachments(null));
  }

  @Test
  void attachmentDelete_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.attachmentDelete(null));
  }

  @Test
  void workPackageWatchers_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageWatchers(null));
  }

  @Test
  void workPackageAddWatcher_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageAddWatcher(null, null));
  }

  @Test
  void workPackageRemoveWatcher_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageRemoveWatcher(null, null));
  }

  @Test
  void workPackageReminders_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageReminders(null));
  }

  @Test
  void workPackageAddReminder_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageAddReminder(null, null, null));
  }

  @Test
  void reminderDelete_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.reminderDelete(null));
  }

  @Test
  void safeEditWorkPackagePrompt_containsInputsAndInstructions() {
    GetPromptResult result = workPackageTools.safeEditWorkPackagePrompt("123", "do it");

    assertNotNull(result);
    assertEquals("OpenProject Work Package Safe Edit", result.description());
    assertNotNull(result.messages());
    assertEquals(1, result.messages().size());

    PromptMessage msg = result.messages().getFirst();
    assertEquals(Role.ASSISTANT, msg.role());
    assertInstanceOf(TextContent.class, msg.content());

    String prompt = ((TextContent) msg.content()).text();
    assertTrue(prompt.contains("workPackageId: 123"));
    assertTrue(prompt.contains("requestedChange: do it"));
    assertTrue(prompt.contains("Critical formatting rule (Markdown newlines)"));
    assertTrue(
        prompt.contains("represent line breaks using the literal two-character sequence /n"));
    assertTrue(prompt.contains("Safe-change workflow (Diff + explicit confirmation)"));
  }

}
