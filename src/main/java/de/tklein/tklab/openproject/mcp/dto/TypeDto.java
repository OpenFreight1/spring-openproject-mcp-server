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
@JsonClassDescription("Data representation of a Work Package Type in OpenProject.")
public class TypeDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical ID of the type.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The name of the type (e.g., 'Task', 'Bug', 'Phase').")
  private String name;

  @JsonProperty
  @JsonPropertyDescription("Indicates if this is the default type for new work packages.")
  private Boolean defaultType;

  @JsonProperty
  @JsonPropertyDescription("Indicates if this type represents a milestone.")
  private Boolean milestone;

  @JsonProperty
  @JsonPropertyDescription("The date and time when the type was created.")
  private OffsetDateTime createdAt;

  @JsonProperty
  @JsonPropertyDescription("The date and time when the type was last updated.")
  private OffsetDateTime updatedAt;

  @JsonProperty
  @JsonPropertyDescription("The API link to this type resource.")
  private String href;
}
