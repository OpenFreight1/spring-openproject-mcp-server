{
  "spentOn": "${spentOn}",
  "hours": "${hours?json_string}",
  "comment": {
    "raw": <#if comment?? && comment?has_content>"${comment?json_string}"<#else>null</#if>
  },
  "_links": {
    "workPackage": {
      "href": "/api/v3/work_packages/${workPackageId}"
    }<#if activityId??>,
    "activity": {
      "href": "/api/v3/time_entries/activities/${activityId}"
    }</#if>
  }
}
