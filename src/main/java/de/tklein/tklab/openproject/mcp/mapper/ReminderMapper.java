package de.tklein.tklab.openproject.mcp.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.ReminderDto;
import de.tklein.tklab.openproject.mcp.util.Utils;
import java.time.OffsetDateTime;
import org.mapstruct.Mapper;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Mapper
public interface ReminderMapper {

  default ReminderDto toDto(JsonNode node) {
    if (node == null) {
      return null;
    }

    ReminderDto dto = new ReminderDto();
    dto.setId(node.path("id").asInt());
    dto.setNote(node.path("note").asText(null));

    String remindAt = node.path("remindAt").asText(null);
    if (remindAt != null) {
      dto.setRemindAt(OffsetDateTime.parse(remindAt));
    }

    dto.setWorkPackageId(Utils.hrefToId(node.path("_links").path("remindable").path("href").asText(null)));
    dto.setHref(node.path("_links").path("self").path("href").asText(null));
    return dto;
  }
}
