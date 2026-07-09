{
  "name": "${name?json_string}",
  "_links": {
    "definingProject": {
      "href": "/api/v3/projects/${projectId}"
    }
  }
<#if description??>
  ,"description": { "raw": "${description?json_string}" }
</#if>
<#if startDate??>
  ,"startDate": "${startDate}"
</#if>
<#if endDate??>
  ,"endDate": "${endDate}"
</#if>
}
