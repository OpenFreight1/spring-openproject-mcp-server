{
  "lockVersion": ${lockVersion},
  "_links": {
    "assignee": {
      "href": <#if assigneeHref?? && assigneeHref?has_content>"${assigneeHref}"<#else>null</#if>
    }
  }
}
