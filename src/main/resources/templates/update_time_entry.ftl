{
  "_type": "TimeEntry"
<#if hours??>
  ,"hours": "${hours?json_string}"
</#if>
<#if spentOn??>
  ,"spentOn": "${spentOn}"
</#if>
<#if comment??>
  ,"comment": { "raw": "${comment?json_string}" }
</#if>
}
