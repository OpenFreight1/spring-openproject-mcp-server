package de.tklein.tklab.openproject.mcp.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.VersionDto;
import org.mapstruct.Mapper;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Mapper
public interface VersionMapper {

  default VersionDto toDto(JsonNode node) {
    if (node == null) {
      return null;
    }

    VersionDto dto = new VersionDto();
    dto.setId(node.path("id").asInt());
    dto.setName(node.path("name").asText(null));
    dto.setStatus(node.path("status").asText(null));
    dto.setHref(node.path("_links").path("self").path("href").asText(null));
    return dto;
  }
}
