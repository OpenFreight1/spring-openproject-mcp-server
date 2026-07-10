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
@JsonClassDescription("Data representation of a Wiki Page in OpenProject. Note: the OpenProject API "
    + "only exposes the page's id/title, not its content — there is no endpoint to read or write "
    + "wiki text via the API.")
public class WikiPageDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical Id of the wiki page.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The wiki page's title.")
  private String title;

  @JsonProperty
  @JsonPropertyDescription("The API link to this wiki page resource.")
  private String href;

}
