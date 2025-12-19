# spring-openproject-mcp-server

## Open project compatibility
Tested against OpenProject 14,15,16,17-rc

## SSE vs Streamable

spring.ai.mcp.server.protocol=SSE
spring.ai.mcp.server.protocol=STREAMABLE

Spring creates one MCP server bean with one transport:

- SSE protocol → exposes:
  - GET /sse for server → client streaming
  - POST /mcp/message for client → server communication
- STREAMABLE protocol → exposes a single bidirectional endpoint (default /mcp) based on the new MCP streamable HTTP spec.

Because the transport defines the wiring, endpoints, and message routing, Spring cannot bind two protocols simultaneously.

## Integration Tests
```bash
mvn test -Dopenproject.container.tag=16 -Dopenproject.container.port=18080
```
Mind to remove volumes between tests 

## TODO
- MCP OpenProject with OTEL
- use results after patch json
- add docker container build
- add kubernetes deployment+service (helm?)
- create GitLab CI (GitHub)

## FAQ
- Why wasn't a generated OpenAPI client used, but JSONNode value mapping with MapStruct?
  - The first approach was using a generated OpenAPI client, but there were many issues:
    - code generation with org.openapitools:openapi-generator-maven-plugin wasn't totally clean and needed lots of manual corrections.
    - client is very strict, and would fail minimal spec changes
    - Spec lacks values, e.g. 'storyPoints' and manually enhancing generated code is not applicable
    - compatibility to wider range of versions can better be realized with direct value mapping and integration tests against multiple versions of OpenProject
  
