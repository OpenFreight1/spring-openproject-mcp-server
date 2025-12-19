{
  "subject": "${subject?json_string}",
  "scheduleManually": true,
  "startDate": <#if startDate??>"${startDate}"<#else>null</#if>,
  "dueDate": <#if dueDate??>"${dueDate}"<#else>null</#if>,
  "estimatedTime": <#if estimatedTime??>"${estimatedTime}"<#else>null</#if>,
  "storyPoints": <#if storyPoints??>${storyPoints}<#else>null</#if>,
  "duration": null,
  "ignoreNonWorkingDays": false,
  "percentageDone": null,
  "_links": {
    "category": {
      "href": null
    },
    "type": {
      "href": "/api/v3/types/${typeId}"
    }<#if priorityId??>,
    "priority": {
      "href": "/api/v3/priorities/${priorityId}"
    }</#if>,
    "project": {
      "href": "/api/v3/projects/${projectId}"
    },
    "projectPhase": {
      "href": null,
      "title": null
    },
    "projectPhaseDefinition": {
      "href": null,
      "title": null
    },
    "status": {
      "href": "/api/v3/statuses/1"
    },
    "responsible": {
      "href": null
    },
    "assignee": {
      "href": null
    },
    "version": {
      "href": null
    },
    "parent": {
      "href": null,
      "title": null
    },
    "budget": {
      "href": null
    },
    "self": {
      "href": null
    },
    "attachments": []
  },
  "description": {
    "raw": "${description!?json_string}"
  }
}
