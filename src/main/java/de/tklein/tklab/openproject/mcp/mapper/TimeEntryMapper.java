package de.tklein.tklab.openproject.mcp.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.TimeEntryDto;
import de.tklein.tklab.openproject.mcp.util.Utils;
import java.time.LocalDate;
import org.mapstruct.Mapper;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Mapper
public interface TimeEntryMapper {

  default TimeEntryDto toDto(JsonNode node) {
    if (node == null) {
      return null;
    }

    TimeEntryDto dto = new TimeEntryDto();
    dto.setId(node.path("id").asInt());
    dto.setHours(node.path("hours").asText(null));
    dto.setComment(node.path("comment").path("raw").asText(null));

    String spentOn = node.path("spentOn").asText(null);
    if (spentOn != null) {
      dto.setSpentOn(LocalDate.parse(spentOn));
    }

    JsonNode entityLink = node.path("_links").path("entity");
    if (entityLink.isMissingNode() || entityLink.isNull()) {
      entityLink = node.path("_links").path("workPackage");
    }
    dto.setWorkPackageId(Utils.hrefToId(entityLink.path("href").asText(null)));

    JsonNode activityLink = node.path("_links").path("activity");
    dto.setActivity(activityLink.path("title").asText(null));
    dto.setActivityId(Utils.hrefToId(activityLink.path("href").asText(null)));

    dto.setUser(node.path("_links").path("user").path("title").asText(null));
    dto.setHref(node.path("_links").path("self").path("href").asText(null));
    return dto;
  }
}
