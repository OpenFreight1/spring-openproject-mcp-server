package de.tklein.tklab.openproject.mcp.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class McpBearerAuthenticationFilter extends OncePerRequestFilter {

  private final OpenProjectTokenValidator tokenValidator;

  private final SecurityContextRepository securityContextRepository =
      new RequestAttributeSecurityContextRepository();

  public McpBearerAuthenticationFilter(OpenProjectTokenValidator tokenValidator) {
    this.tokenValidator = tokenValidator;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) {
    String path = request.getRequestURI();
    // MCP endpoint is /mcp (SSE + JSON-RPC over HTTP)
    return path == null || !(path.equals("/sse") || path.equals("/mcp") || path.startsWith(
        "/mcp/"));
  }

  @Override
  protected boolean shouldNotFilterAsyncDispatch() {
    // IMPORTANT: /mcp uses async dispatch (SSE). We must authenticate on async dispatch too.
    return false;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain
  ) throws ServletException, IOException {

    try {
      ValidatedOpenProjectUser user = tokenValidator.validateCurrentRequestOrThrow();

      var auth = new UsernamePasswordAuthenticationToken(
          user,
          null,
          List.of(new SimpleGrantedAuthority("ROLE_MCP_USER"))
      );
      SecurityContextHolder.getContext().setAuthentication(auth);

      // Persist context so Spring Security can restore it during async dispatch
      securityContextRepository.saveContext(SecurityContextHolder.getContext(), request, response);

      filterChain.doFilter(request, response);
    } catch (UnauthorizedException e) {
      SecurityContextHolder.clearContext();
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write("{\"error\":\"unauthorized\"}");
    }
  }
}