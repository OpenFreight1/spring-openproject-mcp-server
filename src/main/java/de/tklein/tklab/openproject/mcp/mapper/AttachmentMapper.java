package de.tklein.tklab.openproject.mcp.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.AttachmentDto;
import org.mapstruct.Mapper;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Mapper
public interface AttachmentMapper {

  default AttachmentDto toDto(JsonNode node) {
    if (node == null) {
      return null;
    }

    AttachmentDto dto = new AttachmentDto();
    dto.setId(node.path("id").asInt());
    dto.setFileName(node.path("fileName").asText(null));
    dto.setFileSize(node.path("fileSize").isMissingNode() ? null : node.path("fileSize").asLong());
    dto.setContentType(node.path("contentType").asText(null));
    dto.setHref(node.path("_links").path("self").path("href").asText(null));
    return dto;
  }
}
