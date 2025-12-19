{
  "type": "${relationType?json_string}",
  "description": <#if description?? && description?has_content>"${description?json_string}"<#else>null</#if>,
  "_links": {
    "to": {
      "href": "${toHref?json_string}"
    }
  }
}
