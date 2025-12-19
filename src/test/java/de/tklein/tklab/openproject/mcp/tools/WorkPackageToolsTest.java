package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.modelcontextprotocol.spec.McpSchema.GetPromptResult;
import io.modelcontextprotocol.spec.McpSchema.PromptMessage;
import io.modelcontextprotocol.spec.McpSchema.Role;
import io.modelcontextprotocol.spec.McpSchema.TextContent;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WorkPackageToolsTest {

  @Autowired
  private WorkPackageTools workPackageTools;

  @Test
  void workPackageList_validationExceptions() {
    assertThrows(ConstraintViolationException.class, () -> workPackageTools.workPackageList(null));
  }

  @Test
  void workPackageShow_validationExceptions() {
    assertThrows(ConstraintViolationException.class, () -> workPackageTools.workPackageShow(null));
  }

  @Test
  void workPackageCreate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageCreate(null, null));
  }

  @Test
  void workPackageUpdate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> workPackageTools.workPackageUpdate(null, null));
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
