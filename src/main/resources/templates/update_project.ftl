{
  "_type": "Project"
<#if name??>
  ,"name": "${name?json_string}"
</#if>
<#if description??>
  ,"description": { "raw": "${description?json_string}" }
</#if>
}
