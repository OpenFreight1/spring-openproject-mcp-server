package de.tklein.tklab.openproject.mcp.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.WikiPageDto;
import org.mapstruct.Mapper;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Mapper
public interface WikiPageMapper {

  default WikiPageDto toDto(JsonNode node) {
    if (node == null) {
      return null;
    }

    WikiPageDto dto = new WikiPageDto();
    dto.setId(node.path("id").asInt());
    dto.setTitle(node.path("title").asText(null));
    dto.setHref(node.path("_links").path("self").path("href").asText(null));
    return dto;
  }
}
