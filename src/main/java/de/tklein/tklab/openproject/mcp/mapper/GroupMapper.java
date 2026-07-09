package de.tklein.tklab.openproject.mcp.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.GroupDto;
import org.mapstruct.Mapper;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Mapper
public interface GroupMapper {

  default GroupDto toDto(JsonNode node) {
    if (node == null) {
      return null;
    }

    GroupDto dto = new GroupDto();
    dto.setId(node.path("id").asInt());
    dto.setName(node.path("name").asText(null));
    dto.setHref(node.path("_links").path("self").path("href").asText(null));
    return dto;
  }
}
