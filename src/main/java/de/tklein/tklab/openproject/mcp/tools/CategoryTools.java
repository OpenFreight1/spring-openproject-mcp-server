package de.tklein.tklab.openproject.mcp.tools;

import de.tklein.tklab.openproject.mcp.dto.CategoryDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectApiClient;
import jakarta.validation.constraints.NotNull;
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
public class CategoryTools {

  private final OpenProjectApiClient openProjectApiClient;

  public CategoryTools(OpenProjectApiClient openProjectApiClient) {
    this.openProjectApiClient = openProjectApiClient;
  }

  @McpTool(
      description = "List existing work package categories for a project. The category id is used by 'workPackageSetCategory'.",
      annotations = @McpAnnotations(readOnlyHint = true))
  public List<CategoryDto> categoryList(@NotNull Integer projectId) {
    return openProjectApiClient.categoryList(projectId);
  }

}
