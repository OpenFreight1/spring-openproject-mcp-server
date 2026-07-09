package de.tklein.tklab.openproject.mcp.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.StatusDto;
import org.mapstruct.Mapper;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Mapper
public interface StatusMapper {

  default StatusDto toDto(JsonNode node) {
    if (node == null) {
      return null;
    }

    StatusDto dto = new StatusDto();
    dto.setId(node.path("id").asInt());
    dto.setName(node.path("name").asText(null));
    dto.setIsClosed(node.path("isClosed").isMissingNode() ? null : node.path("isClosed").asBoolean());
    dto.setHref(node.path("_links").path("self").path("href").asText(null));
    return dto;
  }
}
