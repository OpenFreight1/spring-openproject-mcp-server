{
  "lockVersion": ${lockVersion},
  "_links": {
    "category": {
      "href": <#if categoryHref?? && categoryHref?has_content>"${categoryHref}"<#else>null</#if>
    }
  }
}
