package de.tklein.tklab.openproject.mcp.tools;

import de.tklein.tklab.openproject.mcp.dto.ProjectDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import io.modelcontextprotocol.spec.McpSchema;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpTool.McpAnnotations;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Log4j2
@Validated
@Component
public class ProjectTools {

  private final OpenProjectApiClient openProjectApiClient;

  public ProjectTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(description = "Lists existing projects visible for the current user with most relevant properties.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<ProjectDto> projectList() {
    return openProjectApiClient.projectList();
  }

  // TODO
  @McpPrompt(name = "openproject.project.summary", description = "Generates a summary of project information")
  public McpSchema.GetPromptResult generateProjectSummaryPrompt(
      @McpArg(description = "Project identifier") String projectId,
      @McpArg(description = "Project name") String projectName) {

    String template = String.format("Provide a detailed summary for project %s (%s).", projectName,
        projectId);
    return new McpSchema.GetPromptResult("project summary", List.of(
        new McpSchema.PromptMessage(McpSchema.Role.ASSISTANT,
            new McpSchema.TextContent(template))));
  }

}
