{
  "lockVersion": ${lockVersion},
  "_links": {
    "parent": {
      "href": <#if parentHref?? && parentHref?has_content>"${parentHref}"<#else>null</#if>
    }
  }
}