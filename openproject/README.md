# OpenAPI v3 Openproject

The first approach was to generate the API, which is obsolete now.
Instead, JSONNode → DTO using MapStruct Mappers and Freemarker templates for JSON payload are implemented.

- code generation using maven Plugin ```org.openapitools.openapi-generator-maven-plugin```
    - creates package ```de.tklein.tklab.openproject.mcp.openproject.v3.*```

## References
- https://github.com/opf/openproject/blob/dev/docs/api/apiv3/openapi-spec.yml Spec in repo, contains relative file links
- https://pro.t-klein.de/api/docs API documentation UI
- https://pro.t-klein.de/api/v3/spec.json contains external references cannot be used
- https://pro.t-klein.de/api/v3/spec.yml <-- use this

## Issues:
- Spec needs to be fixed, see [spec_original.yml](spec_original.yml) vs. [spec.yml](spec.yml)
  - needs ```<skipValidateSpec>true</skipValidateSpec>``` in pom