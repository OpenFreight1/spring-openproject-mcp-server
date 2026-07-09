package de.tklein.tklab.openproject.mcp.openproject.client;

import static de.tklein.tklab.openproject.mcp.util.PatchMap.Nullable.ALLOW_NULL_VALUES;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.tklein.tklab.openproject.mcp.dto.CategoryDto;
import de.tklein.tklab.openproject.mcp.dto.PriorityDto;
import de.tklein.tklab.openproject.mcp.dto.ProjectDto;
import de.tklein.tklab.openproject.mcp.dto.RelationDto;
import de.tklein.tklab.openproject.mcp.dto.StatusDto;
import de.tklein.tklab.openproject.mcp.dto.TimeEntryActivityDto;
import de.tklein.tklab.openproject.mcp.dto.TimeEntryDto;
import de.tklein.tklab.openproject.mcp.dto.TypeDto;
import de.tklein.tklab.openproject.mcp.dto.UserDto;
import de.tklein.tklab.openproject.mcp.dto.VersionDto;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageCreateDto;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageDto;
import de.tklein.tklab.openproject.mcp.dto.WorkPackageUpdateDto;
import de.tklein.tklab.openproject.mcp.mapper.CategoryMapper;
import de.tklein.tklab.openproject.mcp.mapper.PriorityMapper;
import de.tklein.tklab.openproject.mcp.mapper.ProjectMapper;
import de.tklein.tklab.openproject.mcp.mapper.RelationMapper;
import de.tklein.tklab.openproject.mcp.mapper.StatusMapper;
import de.tklein.tklab.openproject.mcp.mapper.TimeEntryActivityMapper;
import de.tklein.tklab.openproject.mcp.mapper.TimeEntryMapper;
import de.tklein.tklab.openproject.mcp.mapper.TypeMapper;
import de.tklein.tklab.openproject.mcp.mapper.UserMapper;
import de.tklein.tklab.openproject.mcp.mapper.VersionMapper;
import de.tklein.tklab.openproject.mcp.mapper.WorkPackageMapper;
import de.tklein.tklab.openproject.mcp.tools.RelationTools.RelationType;
import de.tklein.tklab.openproject.mcp.util.PatchMap;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

/*
 * Copyright (c) 2025 Thomas Klein
 * SPDX-License-Identifier: MIT
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class OpenProjectApiClient {

  public static final String WP_PREFIX = "/api/v3/work_packages/";

  private final OpenProjectRestOperations restOperations;
  private final OpenProjectTemplateRenderer templateRenderer;
  private final TypeMapper typeMapper;
  private final ProjectMapper projectMapper;
  private final WorkPackageMapper workPackageMapper;
  private final PriorityMapper priorityMapper;
  private final RelationMapper relationMapper;
  private final UserMapper userMapper;
  private final StatusMapper statusMapper;
  private final ObjectMapper objectMapper;
  private final TimeEntryMapper timeEntryMapper;
  private final TimeEntryActivityMapper timeEntryActivityMapper;
  private final VersionMapper versionMapper;
  private final CategoryMapper categoryMapper;

  public UserDto root() {
    var result = restOperations.getJson("/api/v3");
    return userMapper.fromRoot(result);
  }

  /**
   * @param assigneeId optional filter: numerical user id as string, or the pseudo-value "me"
   *                    (resolved by OpenProject to the authenticated user); null/blank = no filter
   * @param statusId   optional filter: numerical status id; null = no filter
   */
  public List<WorkPackageDto> workPackageList(@Nonnull Integer projectId, String assigneeId,
      Integer statusId) {
    var uriTemplate = "/api/v3/projects/{projectId}/work_packages?pageSize=1000";
    var filters = buildFilters(assigneeId, statusId);
    JsonNode result;
    if (filters == null) {
      result = restOperations.getJson(uriTemplate, projectId);
    } else {
      result = restOperations.getJson(uriTemplate + "&filters={filters}", projectId, filters);
    }
    return restOperations.mapEmbeddedElements(result, workPackageMapper::toDto);
  }

  private String buildFilters(String assigneeId, Integer statusId) {
    List<Map<String, Object>> filters = new ArrayList<>();
    if (assigneeId != null && !assigneeId.isBlank()) {
      filters.add(Map.of("assignee", Map.of("operator", "=", "values", List.of(assigneeId))));
    }
    if (statusId != null) {
      filters.add(Map.of("status", Map.of("operator", "=", "values", List.of(statusId.toString()))));
    }
    if (filters.isEmpty()) {
      return null;
    }
    try {
      return objectMapper.writeValueAsString(filters);
    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Failed to serialize work package filters", e);
    }
  }

  public WorkPackageDto workPackageShow(@Nonnull Integer workPackageId) {
    try {
      var result = restOperations.getJson("/api/v3/work_packages/{wpId}", workPackageId);
      return workPackageMapper.toDto(result);
    } catch (RestClientResponseException e) {
      if (e.getStatusCode().value() == 404) {
        return null;
      }
      throw e;
    }
  }

  public Integer workPackageCreate(Integer projectId, WorkPackageCreateDto workPackage) {
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "projectId", projectId,
        "typeId", workPackage.getTypeId(),
        "priorityId", workPackage.getPriorityId(),
        "subject", workPackage.getSubject(),
        "description", workPackage.getDescription(),
        "startDate", workPackage.getStartDate(),
        "dueDate", workPackage.getDueDate(),
        "estimatedTime", workPackage.getEstimatedTime(),
        "storyPoints", workPackage.getStoryPoints());
    var jsonBody = templateRenderer.render("create_work_package.ftl", model);
    var result = restOperations.postJson("/api/v3/projects/{projectId}/work_packages", jsonBody,
        projectId);
    return result.findValue("id").asInt();
  }

  public boolean workPackageUpdate(Integer workPackageId, WorkPackageUpdateDto workPackage) {
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "lockVersion", workPackage.getLockVersion(),
        "typeId", workPackage.getTypeId(),
        "priorityId", workPackage.getPriorityId(),
        "subject", workPackage.getSubject(),
        "description", workPackage.getDescription(),
        "startDate", workPackage.getStartDate(),
        "dueDate", workPackage.getDueDate(),
        "estimatedTime", workPackage.getEstimatedTime(),
        "storyPoints", workPackage.getStoryPoints());
    var jsonBody = templateRenderer.render("update_work_package.ftl", model);
    restOperations.patchJson("/api/v3/work_packages/{workPackageId}", jsonBody, workPackageId);
    return true;
  }

  public Integer workPackageAddComment(Integer workPackageId, String comment) {
    var model = PatchMap.of(ALLOW_NULL_VALUES, "comment", comment);
    var jsonBody = templateRenderer.render("create_comment.ftl", model);
    var result = restOperations.postJson("/api/v3/work_packages/{wpId}/activities", jsonBody,
        workPackageId);
    return result.findValue("id").asInt();
  }

  /**
   * @param userId the user id to assign, or null to unassign
   */
  public boolean workPackageAssign(@NotNull Integer workPackageId, Integer userId) {
    var wp = restOperations.getJson("/api/v3/work_packages/{wpId}", workPackageId);
    int lockVersion = wp.path("lockVersion").asInt();
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "lockVersion", lockVersion,
        "assigneeHref", userId == null ? null : "/api/v3/users/" + userId);
    var jsonBody = templateRenderer.render("update_assignee.ftl", model);
    restOperations.patchJson("/api/v3/work_packages/{workPackageId}", jsonBody, workPackageId);
    return true;
  }

  public boolean workPackageChangeStatus(@NotNull Integer workPackageId, @NotNull Integer statusId) {
    var wp = restOperations.getJson("/api/v3/work_packages/{wpId}", workPackageId);
    int lockVersion = wp.path("lockVersion").asInt();
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "lockVersion", lockVersion,
        "statusId", statusId);
    var jsonBody = templateRenderer.render("update_status.ftl", model);
    restOperations.patchJson("/api/v3/work_packages/{workPackageId}", jsonBody, workPackageId);
    return true;
  }

  public List<StatusDto> statusList() {
    var result = restOperations.getJson("/api/v3/statuses");
    return restOperations.mapEmbeddedElements(result, statusMapper::toDto);
  }

  public boolean workPackageSetVersion(@NotNull Integer workPackageId, Integer versionId) {
    var wp = restOperations.getJson("/api/v3/work_packages/{wpId}", workPackageId);
    int lockVersion = wp.path("lockVersion").asInt();
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "lockVersion", lockVersion,
        "versionHref", versionId == null ? null : "/api/v3/versions/" + versionId);
    var jsonBody = templateRenderer.render("update_version.ftl", model);
    restOperations.patchJson("/api/v3/work_packages/{workPackageId}", jsonBody, workPackageId);
    return true;
  }

  public List<VersionDto> versionList(@Nonnull Integer projectId) {
    var result = restOperations.getJson("/api/v3/projects/{projectId}/versions", projectId);
    return restOperations.mapEmbeddedElements(result, versionMapper::toDto);
  }

  public boolean workPackageSetCategory(@NotNull Integer workPackageId, Integer categoryId) {
    var wp = restOperations.getJson("/api/v3/work_packages/{wpId}", workPackageId);
    int lockVersion = wp.path("lockVersion").asInt();
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "lockVersion", lockVersion,
        "categoryHref", categoryId == null ? null : "/api/v3/categories/" + categoryId);
    var jsonBody = templateRenderer.render("update_category.ftl", model);
    restOperations.patchJson("/api/v3/work_packages/{workPackageId}", jsonBody, workPackageId);
    return true;
  }

  public List<CategoryDto> categoryList(@Nonnull Integer projectId) {
    var result = restOperations.getJson("/api/v3/projects/{projectId}/categories", projectId);
    return restOperations.mapEmbeddedElements(result, categoryMapper::toDto);
  }

  public Integer workPackageLogTime(@NotNull Integer workPackageId, @NotNull String hours,
      String comment, LocalDate spentOn, Integer activityId) {
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "workPackageId", workPackageId,
        "hours", hours,
        "comment", comment,
        "spentOn", spentOn == null ? LocalDate.now().toString() : spentOn.toString(),
        "activityId", activityId);
    var jsonBody = templateRenderer.render("create_time_entry.ftl", model);
    var result = restOperations.postJson("/api/v3/time_entries", jsonBody);
    return result.findValue("id").asInt();
  }

  public List<TimeEntryDto> timeEntryList(@Nonnull Integer workPackageId) {
    var filters = "[{\"workPackage\":{\"operator\":\"=\",\"values\":[\"" + workPackageId + "\"]}}]";
    var result = restOperations.getJson("/api/v3/time_entries?filters={filters}", filters);
    return restOperations.mapEmbeddedElements(result, timeEntryMapper::toDto);
  }

  public List<TimeEntryActivityDto> timeEntryActivityList() {
    var result = restOperations.getJson("/api/v3/time_entries/activities");
    return restOperations.mapEmbeddedElements(result, timeEntryActivityMapper::toDto);
  }

  public Integer workPackageUploadAttachment(Integer workPackageId, String fileName,
      byte[] fileContent, String fileContentType) {
    var body = restOperations.buildMultipartAttachmentBody(fileName, fileContent, fileContentType);
    var result = restOperations.postMultipart("/api/v3/work_packages/{wpId}/attachments", body,
        workPackageId);
    return result.findValue("id").asInt();
  }

  public List<TypeDto> typeList(@Nonnull Integer projectId) {
    var result = restOperations.getJson("/api/v3/projects/{projectId}/types", projectId);
    return restOperations.mapEmbeddedElements(result, typeMapper::toDto);
  }

  public List<PriorityDto> priorityList() {
    var result = restOperations.getJson("/api/v3/priorities");
    return restOperations.mapEmbeddedElements(result, priorityMapper::toDto);
  }

  public List<ProjectDto> projectList() {
    var result = restOperations.getJson("/api/v3/projects");
    return restOperations.mapEmbeddedElements(result, projectMapper::toDto);
  }

  public Collection<RelationDto> listRelationsForWorkPackage(@Nonnull Integer wpId) {
    var wp = restOperations.getJson("/api/v3/work_packages/{wpId}", wpId);
    if (wp == null || wp.isNull()) {
      return Collections.emptyList();
    }

    List<RelationDto> result = new ArrayList<>();

    // 1) echte Relations: _embedded.relations._embedded.elements[]
    JsonNode relationElements = wp.path("_embedded").path("relations").path("_embedded")
        .path("elements");

    if (relationElements.isArray()) {
      StreamSupport.stream(relationElements.spliterator(), false)
          .map(relationMapper::fromRelationElement)
          .filter(r -> r != null && (r.getToId() != null || r.getTo() != null || r.getId() != null))
          .forEach(result::add);
    }

    // 2) parent (derived)
    RelationDto parent = relationMapper.fromWpLink("parent", wp.path("_links").path("parent"));
    if (parent != null && (parent.getToId() != null || parent.getTo() != null)) {
      result.add(parent);
    }

    // 3) children[] (derived)
    JsonNode children = wp.path("_links").path("children");
    if (children.isArray()) {
      StreamSupport.stream(children.spliterator(), false)
          .map(n -> relationMapper.fromWpLink("children", n))
          .filter(r -> r != null && (r.getToId() != null || r.getTo() != null))
          .forEach(result::add);
    }

    // 4) ancestors[] (derived)
    JsonNode ancestors = wp.path("_links").path("ancestors");
    if (ancestors.isArray()) {
      StreamSupport.stream(ancestors.spliterator(), false)
          .map(n -> relationMapper.fromWpLink("ancestors", n))
          .filter(r -> r != null && (r.getToId() != null || r.getTo() != null))
          .forEach(result::add);
    }
    return result;
  }

  public boolean relationAdd(Integer workPackageId, Integer otherWorkPackageId, String description,
      RelationType relationType) {
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "relationType", relationType == null ? null : relationType.name(),
        "description", description,
        "toHref", WP_PREFIX + otherWorkPackageId);
    var jsonBody = templateRenderer.render("create_parent_relation.ftl", model);
    var result = restOperations.postJson("/api/v3/work_packages/{workPackageId}/relations",
        jsonBody, workPackageId);
    return result != null && result.hasNonNull("id") && result.hasNonNull("_type");
  }

  public boolean relationDelete(Integer relationId) {
    try {
      restOperations.delete("/api/v3/relations/{relationId}", relationId);
      return true;
    } catch (RestClientResponseException e) {
      log.warn("Failed to delete relation {}. status={}, body={}", relationId, e.getStatusCode(),
          e.getResponseBodyAsString());
      return false;
    }
  }

  public boolean relationAddParent(@NotNull Integer wpId, @NotNull Integer parentId) {
    var wp = restOperations.getJson("/api/v3/work_packages/{wpId}", wpId);
    int lockVersion = wp.path("lockVersion").asInt();
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "lockVersion", lockVersion,
        "parentHref", WP_PREFIX + parentId);
    var jsonBody = templateRenderer.render("update_parent_relation.ftl", model);
    restOperations.patchJson("/api/v3/work_packages/{workPackageId}", jsonBody, wpId);
    return true;
  }

  public boolean relationDeleteParent(Integer wpId) {
    var wp = restOperations.getJson("/api/v3/work_packages/{wpId}", wpId);
    int lockVersion = wp.path("lockVersion").asInt();
    var model = PatchMap.of(ALLOW_NULL_VALUES,
        "lockVersion", lockVersion,
        "parentHref", null);
    var jsonBody = templateRenderer.render("update_parent_relation.ftl", model);
    restOperations.patchJson("/api/v3/work_packages/{workPackageId}", jsonBody, wpId);
    return true;
  }
}