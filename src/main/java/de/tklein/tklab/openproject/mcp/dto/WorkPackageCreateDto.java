package de.tklein.tklab.openproject.mcp.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
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
@JsonClassDescription("Data required to create a new work package in OpenProject.")
public class WorkPackageCreateDto {

  @JsonProperty(required = true)
  @JsonPropertyDescription("The subject or title of the work package.")
  @NotBlank(groups = OnCreate.class)
  private String subject;

  @JsonProperty
  @JsonPropertyDescription("Detailed description of the work package. Supports Markdown.")
  private String description;

  @JsonProperty(required = true)
  @JsonPropertyDescription("The numerical ID of the work package type e.g., 5 for 'Epic', 6 for 'User story'. Use tool 'list_project_types' to retrieve available types and IDs for the project.")
  @NotNull(groups = OnCreate.class)
  private Integer typeId;

  @JsonProperty
  @JsonPropertyDescription("Optional: The numerical ID of the priority (e.g., 8 for 'Normal') gathered from MCP tool 'priorities_list'. If omitted, OpenProject will apply its default priority.")
  private Integer priorityId;

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
  @JsonPropertyDescription("Story points of the work package (if type is 'User story').")
  @Positive
  private Integer storyPoints;

  public interface OnCreate {
    // constraint group constraints in subclasses
  }
}
