package de.tklein.tklab.openproject.mcp.tools;

import static org.assertj.core.api.Assertions.assertThat;

import de.tklein.tklab.openproject.mcp.TestConstants;
import de.tklein.tklab.openproject.mcp.dto.UserDto;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectConnection;
import de.tklein.tklab.openproject.mcp.openproject.client.OpenProjectConnectionResolver;
import de.tklein.tklab.openproject.mcp.security.OpenProjectTokenValidationCache;
import de.tklein.tklab.openproject.mcp.security.OpenProjectTokenValidator;
import de.tklein.tklab.openproject.mcp.security.UnauthorizedException;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Tests authentication aspects around MCP methods: 'tools/list', 'prompts/list' and 'ping'.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
    "spring.ai.mcp.server.protocol=STREAMABLE"})
@Tag(TestConstants.TAG_INTEGRATION_TEST_LOCAL)
@Tag(TestConstants.TAG_SECURITY)
class McpSecurityIntegrationTest {

  static final String VALID_TOKEN = "correctToken";
  static final Map<String, String> VALID_AUTH_HEADER = makeAuthHeader(VALID_TOKEN);

  static Map<String, String> makeAuthHeader(String token) {
    return token == null ? Collections.emptyMap() : Map.of("Authorization", "Bearer " + token);
  }

  @TestConfiguration
  static class McpHeaderAwareTokenValidatorTestConfig {

    /**
     * Fake auth check. Mocks that OpenProject accepts for VALID_TOKEN otherwise rejects.
     */
    @Bean
    @Primary
    OpenProjectTokenValidator openProjectTokenValidatorStub(OpenProjectConnectionResolver resolver,
        OpenProjectTokenValidationCache cache) {
      return new OpenProjectTokenValidator(resolver, null, cache) {

        @Override
        protected UserDto fetchUserFromOpenProject() {
          OpenProjectConnection c = resolver.resolve();
          String token = (c == null) ? null : c.bearerToken();

          if (VALID_TOKEN.equals(token)) {
            return new UserDto(999, "/api/v3/users/999", "test-user");
          }
          throw new UnauthorizedException("Token rejected by OpenProject (test stub)");
        }
      };
    }
  }

  @LocalServerPort
  int port;

  @ParameterizedTest
  @CsvSource(value = {"correctToken,200", "wrongToken,401", ",401", "'',401"})
  void mcpInitializeAuth(String token, int expectedStatus) throws Exception {
    var headers = makeAuthHeader(token);
    HttpResponse<String> response = McpToolsHelper.initializeMcp(port, headers);
    assertThat(response.statusCode()).as("expect %d for header %s, Body=%s", expectedStatus, token,
        response.body()).isEqualTo(expectedStatus);
  }

  @ParameterizedTest
  @CsvSource(value = {"correctToken,404", "wrongToken,401", ",401", "'',401"})
  void mcpToolsRequestWithoutInitializeAuth(String token, int expectedStatus) throws Exception {
    var headers = makeAuthHeader(token);
    var result = McpToolsHelper.callToolsListRequest(port, "fakeSessionId", headers,
        expectedStatus);
    assertThat(result).isNull();
  }

  @ParameterizedTest
  @CsvSource(value = {"correctToken,200", "wrongToken,401", ",401", "'',401"})
  void mcpToolsRequestWithCorrectMCPSession(String token, int expectedStatus) throws Exception {
    // initialize with the correct token
    String sessionId = McpToolsHelper.initializeAndGetSessionId(port, VALID_AUTH_HEADER);
    assertThat(sessionId).isNotBlank();

    var headers = makeAuthHeader(token);
    McpToolsHelper.callToolsListRequest(port, sessionId, headers, expectedStatus);
  }

  @ParameterizedTest
  @CsvSource(value = {"correctToken,404", "wrongToken,401", ",401", "'',401"})
  void mcpPingRequestWithoutInitializeAuth(String token, int expectedStatus) throws Exception {
    var headers = makeAuthHeader(token);
    var result = McpToolsHelper.callPingRequest(port, "fakeSessionId", headers, expectedStatus);
    assertThat(result).isNull();
  }

  @ParameterizedTest
  @CsvSource(value = {"correctToken,200", "wrongToken,401", ",401", "'',401"})
  void mcpPromptsRequestWithCorrectMcpSession(String token, int expectedStatus) throws Exception {
    // initialize with the correct token
    String sessionId = McpToolsHelper.initializeAndGetSessionId(port, VALID_AUTH_HEADER);
    assertThat(sessionId).isNotBlank();

    var headers = makeAuthHeader(token);
    McpToolsHelper.callPromptsListRequest(port, sessionId, headers, expectedStatus);
  }

  @ParameterizedTest
  @CsvSource(value = {"correctToken,404", "wrongToken,401", ",401", "'',401"})
  void mcpPromptsRequestWithoutInitializeAuth(String token, int expectedStatus) throws Exception {
    var headers = makeAuthHeader(token);
    var result = McpToolsHelper.callPromptsListRequest(port, "fakeSessionId", headers,
        expectedStatus);
    assertThat(result).isNull();
  }

  @ParameterizedTest
  @CsvSource(value = {"correctToken,200", "wrongToken,401", ",401", "'',401"})
  void mcpPingRequestWithCorrectMcpSession(String token, int expectedStatus) throws Exception {
    // initialize with the correct token
    String sessionId = McpToolsHelper.initializeAndGetSessionId(port, VALID_AUTH_HEADER);
    assertThat(sessionId).isNotBlank();

    var headers = makeAuthHeader(token);
    McpToolsHelper.callPingRequest(port, sessionId, headers, expectedStatus);
  }
}