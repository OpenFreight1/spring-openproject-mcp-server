package de.tklein.tklab.openproject.mcp.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.time.LocalDate;
import java.time.OffsetDateTime;
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
@JsonClassDescription("Data representation of a Work Package in OpenProject.")
public class WorkPackageDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical ID of the work package.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The version number for optimistic locking.")
  private Integer lockVersion;

  @JsonProperty
  @JsonPropertyDescription("The subject or title of the work package.")
  private String subject;

  @JsonProperty
  @JsonPropertyDescription("Detailed description of the work package. Supports Markdown.")
  private String description;

  @JsonProperty
  @JsonPropertyDescription("Story points of the work package (if type is 'User story').")
  private Integer storyPoints;

  @JsonProperty
  @JsonPropertyDescription("The start date of the work package in YYYY-MM-DD format.")
  private LocalDate startDate;

  @JsonProperty
  @JsonPropertyDescription("The due date of the work package in YYYY-MM-DD format.")
  private LocalDate dueDate;

  @JsonProperty
  @JsonPropertyDescription("Estimated time for the work package in ISO 8601 duration format (e.g., 'PT5H' for 5 hours).")
  private String estimatedTime;

  @JsonProperty
  @JsonPropertyDescription("The duration of the work package in ISO 8601 duration format.")
  private String duration;

  @JsonProperty
  @JsonPropertyDescription("The date and time when the work package was created.")
  private OffsetDateTime createdAt;

  @JsonProperty
  @JsonPropertyDescription("The date and time when the work package was last updated.")
  private OffsetDateTime updatedAt;

  @JsonProperty
  @JsonPropertyDescription("The type name of the work package (e.g. 'Task', 'Bug').")
  private String type;

  @JsonProperty
  @JsonPropertyDescription("The priority name of the work package (e.g. 'Low', 'Normal').")
  private String priority;

  @JsonProperty
  @JsonPropertyDescription("The API link to this work package resource.")
  private String href;
}
