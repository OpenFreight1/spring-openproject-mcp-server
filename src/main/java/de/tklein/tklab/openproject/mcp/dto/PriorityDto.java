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

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonClassDescription("Data representation of a Priority in OpenProject.")
public class PriorityDto {

  @JsonProperty
  @JsonPropertyDescription("The unique numerical Id of the priority.")
  private Integer id;

  @JsonProperty
  @JsonPropertyDescription("The name of the priority (e.g., 'Low', 'Normal').")
  private String name;

  @JsonProperty
  @JsonPropertyDescription("Ordering position of the priority.")
  private Integer position;

  @JsonProperty
  @JsonPropertyDescription("The API link to this priority resource.")
  private String href;

}
