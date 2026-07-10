package de.tklein.tklab.openproject.mcp.tools;

import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WikiPageToolsTest {

  @Autowired
  private WikiPageTools wikiPageTools;

  @Test
  void wikiPageShow_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> wikiPageTools.wikiPageShow(null));
  }

  @Test
  void wikiPageAttachments_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> wikiPageTools.wikiPageAttachments(null));
  }

  @Test
  void wikiPageUploadAttachment_validationExceptions() {
    assertThrows(ConstraintViolationException.class,
        () -> wikiPageTools.wikiPageUploadAttachment(null, null, null, null));
  }

}
