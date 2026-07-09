package de.tklein.tklab.openproject.mcp.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
@JsonClassDescription("One item of a bulk work package update: the target work package id plus its update payload.")
public class WorkPackageBulkUpdateItemDto {

  @JsonProperty(required = true)
  @JsonPropertyDescription("The numerical ID of the work package to update.")
  @NotNull
  private Integer workPackageId;

  @JsonProperty(required = true)
  @JsonPropertyDescription("The update payload, same shape as workPackageUpdate's 'workPackage' argument.")
  @NotNull
  @Valid
  private WorkPackageUpdateDto workPackage;

}
