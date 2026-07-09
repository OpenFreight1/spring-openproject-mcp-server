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
@JsonClassDescription("Data representation of a project work package Category in OpenProject.")
public class CategoryDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical Id of the category.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The name of the category.")
  private String name;

  @JsonProperty
  @JsonPropertyDescription("The API link to this category resource.")
  private String href;

}
