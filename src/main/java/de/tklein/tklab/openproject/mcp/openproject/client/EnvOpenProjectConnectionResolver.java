package de.tklein.tklab.openproject.mcp.openproject.client;

import java.net.URI;

/**
 * Für später (STDIO statt SSE): Credentials/URL aus ENV ziehen. Minimaler Switch wäre dann: diese
 * Bean aktivieren und den Header-Resolver deaktivieren.
 */
// @Component
public class EnvOpenProjectConnectionResolver implements OpenProjectConnectionResolver {

  @Override
  public OpenProjectConnection resolve() {
    String baseUrl = System.getenv("OPENPROJECT_BASE_URL");
    String token = System.getenv("OPENPROJECT_BEARER_TOKEN");
    return new OpenProjectConnection(URI.create(baseUrl), token);
  }
}