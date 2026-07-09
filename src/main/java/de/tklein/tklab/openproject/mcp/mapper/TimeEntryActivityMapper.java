package de.tklein.tklab.openproject.mcp.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.TimeEntryActivityDto;
import org.mapstruct.Mapper;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Mapper
public interface TimeEntryActivityMapper {

  default TimeEntryActivityDto toDto(JsonNode node) {
    if (node == null) {
      return null;
    }

    TimeEntryActivityDto dto = new TimeEntryActivityDto();
    dto.setId(node.path("id").asInt());
    dto.setName(node.path("name").asText(null));
    dto.setIsDefault(node.path("default").isMissingNode() ? null : node.path("default").asBoolean());
    dto.setHref(node.path("_links").path("self").path("href").asText(null));
    return dto;
  }
}
