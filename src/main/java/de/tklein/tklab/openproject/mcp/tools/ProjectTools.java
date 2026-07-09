package de.tklein.tklab.openproject.mcp.tools;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import de.tklein.tklab.openproject.mcp.dto.ProjectDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.validation.constraints.NotNull;
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

  @McpTool(description = "Fetches a project by id with its most relevant properties.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public ProjectDto projectShow(@NotNull Integer projectId) {
    return openProjectApiClient.projectShow(projectId);
  }

  @McpTool(description = "Creates a new project. The identifier is optional (OpenProject derives one from the name if omitted).")
  public Integer projectCreate(
      @JsonPropertyDescription("The project's name.") @NotNull String projectName,
      @JsonPropertyDescription("Optional unique string identifier (e.g. 'my-project'). Auto-derived from name if omitted.") @JsonProperty String identifier,
      @JsonPropertyDescription("Optional project description.") @JsonProperty String description) {
    return openProjectApiClient.projectCreate(projectName, identifier, description);
  }

  @McpTool(description = "Updates a project's name and/or description. Omitted fields are left unchanged.")
  public boolean projectUpdate(@NotNull Integer projectId,
      @JsonPropertyDescription("New project name, or omit to leave unchanged.") @JsonProperty String projectName,
      @JsonPropertyDescription("New project description, or omit to leave unchanged.") @JsonProperty String description) {
    return openProjectApiClient.projectUpdate(projectId, projectName, description);
  }

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
