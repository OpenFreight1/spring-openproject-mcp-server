{
  "lockVersion": ${lockVersion},
  "_links": {
    <#if typeId??>
    "type": { "href": "/api/v3/types/${typeId}" }
    </#if>
    <#if typeId?? && priorityId??>,</#if>
    <#if priorityId??>
    "priority": { "href": "/api/v3/priorities/${priorityId}" }
    </#if>
  },
  "_meta": {
    "validateCustomFields": true
  }
<#if subject??>
  ,"subject": "${subject?json_string}"
</#if>
<#if description??>
  ,"description": { "raw": "${description?json_string}" }
</#if>
<#if startDate??>
  ,"startDate": "${startDate}"
</#if>
<#if dueDate??>
  ,"dueDate": "${dueDate}"
</#if>
<#if estimatedTime??>
  ,"estimatedTime": "${estimatedTime}"
</#if>
<#if storyPoints??>
  ,"storyPoints": ${storyPoints}
</#if>
}