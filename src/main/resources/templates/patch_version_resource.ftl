{
  "_type": "Version"
<#if name??>
  ,"name": "${name?json_string}"
</#if>
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
