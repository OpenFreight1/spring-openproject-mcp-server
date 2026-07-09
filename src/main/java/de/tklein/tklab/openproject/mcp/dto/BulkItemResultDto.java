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
@JsonClassDescription("The result of one item in a bulk work package create/update call.")
public class BulkItemResultDto {

  @JsonProperty
  @JsonPropertyDescription("The index of this item in the input list (0-based).")
  private Integer index;

  @JsonProperty
  @JsonPropertyDescription("The numerical ID of the created/updated work package, or null if this item failed.")
  private Integer workPackageId;

  @JsonProperty
  @JsonPropertyDescription("Whether this item succeeded.")
  private boolean success;

  @JsonProperty
  @JsonPropertyDescription("The error message if this item failed, otherwise null.")
  private String error;

}
