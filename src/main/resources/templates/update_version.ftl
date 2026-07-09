{
  "lockVersion": ${lockVersion},
  "_links": {
    "version": {
      "href": <#if versionHref?? && versionHref?has_content>"${versionHref}"<#else>null</#if>
    }
  }
}
