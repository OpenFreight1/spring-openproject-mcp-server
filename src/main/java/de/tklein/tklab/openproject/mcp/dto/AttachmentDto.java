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
@JsonClassDescription("Data representation of a work package Attachment in OpenProject.")
public class AttachmentDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical Id of the attachment.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The uploaded file's name.")
  private String fileName;

  @JsonProperty
  @JsonPropertyDescription("The size of the uploaded file in bytes.")
  private Long fileSize;

  @JsonProperty
  @JsonPropertyDescription("The file's MIME content type.")
  private String contentType;

  @JsonProperty
  @JsonPropertyDescription("The API link to this attachment resource.")
  private String href;

}
