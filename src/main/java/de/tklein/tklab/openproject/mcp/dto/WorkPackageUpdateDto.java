package de.tklein.tklab.openproject.mcp.dto;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import jakarta.validation.constraints.NotNull;
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
@JsonClassDescription("Data required to update a work package in OpenProject.")
public class WorkPackageUpdateDto extends WorkPackageCreateDto {

  @JsonProperty(required = true)
  @JsonPropertyDescription("The latest 'lockVersion' property from the work-package to update.")
  @NotNull
  private Integer lockVersion;

  @JsonProperty // override from superclass
  @JsonPropertyDescription("The subject or title of the work package.")
  private String subject;

  @JsonProperty // override from superclass
  @JsonPropertyDescription("The numerical ID of the work package type e.g., 5 for 'Epic', 6 for 'User story'. Use tool 'list_project_types' to retrieve available types and IDs for the project.")
  private Integer typeId;

}
