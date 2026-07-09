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

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Data representation of a Time Entry Activity (e.g. 'Development', 'Management') in OpenProject.")
public class TimeEntryActivityDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical Id of the time entry activity.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The name of the activity (e.g., 'Development', 'Management').")
  private String name;

  @JsonProperty
  @JsonPropertyDescription("Whether this is the default activity applied when none is specified.")
  private Boolean isDefault;

  @JsonProperty
  @JsonPropertyDescription("The API link to this time entry activity resource.")
  private String href;

}
