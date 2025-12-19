package de.tklein.tklab.openproject.mcp.security;

import java.util.function.Supplier;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@EnableCaching
public class OpenProjectTokenValidationCache {

  @Cacheable(cacheNames = {"auth-validation"}, key = "#key")
  public ValidatedOpenProjectUser cachedCheckToken(String key,
      Supplier<ValidatedOpenProjectUser> supplier) {
    log.trace(() -> String.format("Checking token for uncached key '%s'", key));
    var user = supplier.get();
    log.trace(() -> String.format("Cache user '%s' for key '%s'", user.name(), key));
    return user;
  }

}
