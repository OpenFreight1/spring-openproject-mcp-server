package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TypesToolsTest {

  @Autowired
  private TypesTools typesTools;

  @Test
  void typeList_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> typesTools.typeList(null));
  }

}
