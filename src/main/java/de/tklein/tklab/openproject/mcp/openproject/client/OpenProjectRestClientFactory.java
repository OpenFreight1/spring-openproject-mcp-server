package de.tklein.tklab.openproject.mcp.openproject.client;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class OpenProjectRestClientFactory {

  private final OpenProjectConnectionResolver connectionResolver;
  private final OpenProjectAuthHeaderFactory authHeaderFactory;
  private final RestClient.Builder restClientBuilder;

  public RestClient createClient() {
    var c = connectionResolver.resolve();
    var auth = authHeaderFactory.toOpenProjectBasicAuth(c.bearerToken());
    return restClientBuilder
        .clone()
        .baseUrl(c.baseUrl().toString())
        .defaultHeader("Authorization", auth)
        .build();
  }
}