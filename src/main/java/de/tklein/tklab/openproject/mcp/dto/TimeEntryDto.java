package de.tklein.tklab.openproject.mcp.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;
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
@JsonClassDescription("Data representation of a logged Time Entry in OpenProject.")
public class TimeEntryDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical Id of the time entry.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The numerical ID of the work package this time was logged against.")
  private Integer workPackageId;

  @JsonProperty
  @JsonPropertyDescription("The time quantifying the expenditure, in ISO 8601 duration format (e.g., 'PT2H' for 2 hours).")
  private String hours;

  @JsonProperty
  @JsonPropertyDescription("The date the expenditure is booked for.")
  private LocalDate spentOn;

  @JsonProperty
  @JsonPropertyDescription("An optional comment describing the logged time.")
  private String comment;

  @JsonProperty
  @JsonPropertyDescription("The name of the time entry activity this entry is categorized as (e.g. 'Development').")
  private String activity;

  @JsonProperty
  @JsonPropertyDescription("The numerical ID of the time entry activity.")
  private Integer activityId;

  @JsonProperty
  @JsonPropertyDescription("The display name of the user this time entry is booked for.")
  private String user;

  @JsonProperty
  @JsonPropertyDescription("The API link to this time entry resource.")
  private String href;

}
