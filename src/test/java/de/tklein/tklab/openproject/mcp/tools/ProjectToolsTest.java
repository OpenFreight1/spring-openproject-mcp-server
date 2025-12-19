package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.tklein.tklab.openproject.mcp.dto.ProjectDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProjectToolsTest {

  // mocked
  private final OpenProjectApiClient openProjectApiClient = mock(OpenProjectApiClient.class);

  @InjectMocks
  private ProjectTools projectTools;

  @Test
  void listProjects_delegatesToApiClient_andReturnsResult() {
    // Arrange
    List<ProjectDto> expected = List.of(
        new ProjectDto()
            .withId(1)
            .withName("Project 1")
            .withDescription("desc")
            .withHref("https://example.invalid/p/1"),
        new ProjectDto()
            .withId(2)
            .withIdentifier("proj-2")
            .withName("Project 2")
            .withDescription(null)
            .withHref("https://example.invalid/p/2")
    );
    when(openProjectApiClient.projectList()).thenReturn(expected);

    // Act
    List<ProjectDto> actual = projectTools.projectList();

    // Assert
    assertEquals(expected, actual);
    verify(openProjectApiClient).projectList();
  }

  @Test
  void generateProjectSummaryPrompt_buildsPromptWithProvidedProjectData() {
    // Arrange
    String projectId = "my-project";
    String projectName = "My Project";

    // Act
    McpSchema.GetPromptResult result = projectTools.generateProjectSummaryPrompt(projectId,
        projectName);

    // Assert
    assertNotNull(result);

    assertEquals("project summary", result.description());
    assertNotNull(result.messages());
    assertEquals(1, result.messages().size());

    McpSchema.PromptMessage message = result.messages().getFirst();
    assertEquals(McpSchema.Role.ASSISTANT, message.role());

    McpSchema.TextContent content = (McpSchema.TextContent) message.content();
    assertEquals(
        "Provide a detailed summary for project My Project (my-project).",
        content.text()
    );
  }

}
