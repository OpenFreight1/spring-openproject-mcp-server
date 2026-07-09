package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Separate SpringBootTest class (real, validated proxy) from TimeEntryToolsTest (Mockito-based
 * delegation test for the no-arg timeEntryActivityList) since the two test styles don't mix
 * within one class.
 */
@SpringBootTest
class TimeEntryToolsValidationTest {

  @Autowired
  private TimeEntryTools timeEntryTools;

  @Test
  void timeEntryShow_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> timeEntryTools.timeEntryShow(null));
  }

  @Test
  void timeEntryUpdate_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> timeEntryTools.timeEntryUpdate(null, null, null, null));
  }

  @Test
  void timeEntryDelete_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> timeEntryTools.timeEntryDelete(null));
  }

}
