package de.tklein.tklab.openproject.mcp.tools;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayWithSize;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.tklein.tklab.openproject.mcp.dto.RelationValuesDto;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RelationToolsTest {

  @Autowired
  private RelationTools relationTools;

  @Test
  void relationAllowedValues_ReturnsResults() {
    // WHEN
    RelationValuesDto result = relationTools.relationAllowedValues();

    // THEN
    assertNotNull(result);
    assertThat(result.allowedToValues(), arrayWithSize(6));
    assertThat(result.allowedToValues(), hasItemInArray("relates"));
  }

  @Test
  void relationAdd_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> relationTools.relationAdd(null, null, null, null));
  }

  @Test
  void relationDelete_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> relationTools.relationDelete(null));
  }

  @Test
  void relationAddParent_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> relationTools.relationAddParent(null, null));
  }

  @Test
  void relationList_validationExceptions() {
    assertThrows(ConstraintViolationException.class, () -> relationTools.relationList(null));
  }

  @Test
  void relationDeleteParent_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> relationTools.relationDeleteParent(null));
  }

}
