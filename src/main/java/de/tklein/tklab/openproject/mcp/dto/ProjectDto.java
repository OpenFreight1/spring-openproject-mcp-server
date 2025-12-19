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

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Data representation of a Project in OpenProject.")
public class ProjectDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical ID of the project.")
  private Integer id;

  @JsonProperty(required = true)
  @JsonPropertyDescription("The unique string identifier of the project (e.g. 'my-project').")
  private String identifier;

  @JsonProperty(required = true)
  @JsonPropertyDescription("The name of the project.")
  private String name;

  @JsonProperty
  @JsonPropertyDescription("Whether the project is currently active.")
  private Boolean active;

  @JsonProperty
  @JsonPropertyDescription("Whether the project is publicly visible.")
  private Boolean isPublic;

  @JsonProperty
  @JsonPropertyDescription("The current status of the project.")
  private String status;

  @JsonProperty
  @JsonPropertyDescription("A detailed description of the project.")
  private String description;

  @JsonProperty
  @JsonPropertyDescription("The date and time when the project was created.")
  private OffsetDateTime createdAt;

  @JsonProperty
  @JsonPropertyDescription("The date and time when the project was last updated.")
  private OffsetDateTime updatedAt;

  @JsonProperty
  @JsonPropertyDescription("The API link to this project resource.")
  private String href;
}
