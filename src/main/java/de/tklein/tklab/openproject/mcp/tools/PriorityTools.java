package de.tklein.tklab.openproject.mcp.tools;

import de.tklein.tklab.openproject.mcp.dto.PriorityDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpTool.McpAnnotations;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Log4j2
@Validated
@Component
public class PriorityTools {

  private final OpenProjectApiClient openProjectApiClient;

  public PriorityTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(description = "List existing priorities. The priority id will be used in work packages.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<PriorityDto> priorityList() {
    return openProjectApiClient.priorityList();
  }

}
