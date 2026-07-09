package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VersionToolsTest {

  @Autowired
  private VersionTools versionTools;

  @Test
  void versionList_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> versionTools.versionList(null));
  }

}
