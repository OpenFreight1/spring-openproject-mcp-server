package de.tklein.tklab.openproject.mcp.openproject.client;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.stereotype.Component;

@Component
public class OpenProjectAuthHeaderFactory {

  public String toOpenProjectBasicAuth(String bearerToken) {
    if (bearerToken == null || bearerToken.isBlank()) {
      throw new IllegalArgumentException("bearerToken must not be blank");
    }
    String raw = "apikey:" + bearerToken;
    String b64 = Base64.getEncoder().encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    return "Basic " + b64;
  }
}