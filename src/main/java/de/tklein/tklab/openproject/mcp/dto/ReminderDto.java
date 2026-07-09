package de.tklein.tklab.openproject.mcp.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.OffsetDateTime;
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
@JsonClassDescription("Data representation of a Reminder on a work package in OpenProject.")
public class ReminderDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical Id of the reminder.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The numerical ID of the work package this reminder is set on.")
  private Integer workPackageId;

  @JsonProperty
  @JsonPropertyDescription("The date and time the reminder is set to fire.")
  private OffsetDateTime remindAt;

  @JsonProperty
  @JsonPropertyDescription("An optional note for the reminder.")
  private String note;

  @JsonProperty
  @JsonPropertyDescription("The API link to this reminder resource.")
  private String href;

}
