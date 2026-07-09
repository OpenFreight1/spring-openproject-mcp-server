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

  @Test
  void versionShow_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> versionTools.versionShow(null));
  }

  @Test
  void versionCreate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> versionTools.versionCreate(null, null, null, null, null));
  }

  @Test
  void versionUpdate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> versionTools.versionUpdate(null, null, null, null, null));
  }

  @Test
  void versionDelete_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> versionTools.versionDelete(null));
  }

}
