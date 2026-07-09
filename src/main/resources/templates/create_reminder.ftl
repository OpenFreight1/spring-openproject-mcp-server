{
  "remindAt": "${remindAt?json_string}",
  "note": <#if note?? && note?has_content>"${note?json_string}"<#else>null</#if>
}
