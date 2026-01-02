package de.tklein.tklab.openproject.mcp.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Data representation of the authenticated user extracted from the OpenProject API root (/api/v3).")
public class UserDto {

  @JsonProperty
  @JsonPropertyDescription("The API link to the current user resource (_links.user.href).")
  private String href;

  @JsonProperty
  @JsonPropertyDescription("The display name/title of the current user (_links.user.title).")
  private String name;
}
