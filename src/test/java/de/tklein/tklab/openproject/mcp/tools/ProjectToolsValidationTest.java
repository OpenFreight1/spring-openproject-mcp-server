package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Separate SpringBootTest class (real, validated proxy) from ProjectToolsTest (Mockito-based
 * delegation test) since the two test styles don't mix within one class.
 */
@SpringBootTest
class ProjectToolsValidationTest {

  @Autowired
  private ProjectTools projectTools;

  @Test
  void projectShow_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> projectTools.projectShow(null));
  }

  @Test
  void projectCreate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> projectTools.projectCreate(null, null, null));
  }

  @Test
  void projectUpdate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> projectTools.projectUpdate(null, null, null));
  }

}
