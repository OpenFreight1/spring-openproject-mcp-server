package de.tklein.tklab.openproject.mcp.mapper;

import static org.apache.commons.lang3.StringUtils.trimToNull;

import com.fasterxml.jackson.databind.JsonNode;
import de.tklein.tklab.openproject.mcp.dto.UserDto;
import de.tklein.tklab.openproject.mcp.util.Utils;
import jakarta.annotation.Nonnull;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

  /**
   * Maps the OpenProject API root response (/api/v3) to a UserDto: _links.user.href -> href
   * _links.user.title -> name
   */
  default UserDto fromRoot(@Nonnull JsonNode root) {
    JsonNode userNode = root.path("_links").path("user");

    String href = trimToNull(userNode.path("href").asText(null));
    UserDto dto = new UserDto();
    dto.setHref(href);
    dto.setName(trimToNull(userNode.path("title").asText(null)));
    dto.setId(Utils.hrefToId(href));
    return dto;
  }

  /**
   * Maps a User/Group/PlaceholderUser resource (top-level id/name, e.g. from /api/v3/users,
   * /api/v3/groups, or a work package's watchers collection) to a UserDto.
   */
  default UserDto fromPrincipalResource(@Nonnull JsonNode node) {
    UserDto dto = new UserDto();
    dto.setId(node.path("id").isMissingNode() ? null : node.path("id").asInt());
    dto.setName(trimToNull(node.path("name").asText(null)));
    dto.setHref(trimToNull(node.path("_links").path("self").path("href").asText(null)));
    return dto;
  }
}

