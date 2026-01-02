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
@JsonClassDescription("Data representation of a relation/parent/child/ancestor reference of a Work Package in OpenProject.")
public class RelationDto {

  @JsonProperty
  @JsonPropertyDescription("Unique id of the relation (always 'null' for derived types parent|children|ancestors).")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("Relation type (see possible values in MCP tool 'relations_list') or 'parent|children|ancestors' for derived types.")
  private String type;

  @JsonProperty
  @JsonPropertyDescription("Optional description of the relation.")
  private String description;

  @JsonProperty
  @JsonPropertyDescription("Target work package id.")
  private Integer toId;

  @JsonProperty
  @JsonPropertyDescription("Target work package title.")
  private String to;
}
