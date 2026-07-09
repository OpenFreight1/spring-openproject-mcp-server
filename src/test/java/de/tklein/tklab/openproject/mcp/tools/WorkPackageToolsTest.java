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
        () -> workPackageTools.workPackageList(null));
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
