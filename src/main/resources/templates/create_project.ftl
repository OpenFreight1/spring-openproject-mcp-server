{
  "name": "${name?json_string}",
  <#if identifier?? && identifier?has_content>"identifier": "${identifier?json_string}",</#if>
  "description": {
    "raw": <#if description?? && description?has_content>"${description?json_string}"<#else>null</#if>
  }
}
