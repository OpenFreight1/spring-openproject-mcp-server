package de.tklein.tklab.openproject.mcp.util;

import java.time.Duration;

public class Utils {

  private Utils() {
  }

  public static void sleep(Duration duration) {
    try {
      Thread.sleep(duration);
    } catch (InterruptedException _) {
      Thread.currentThread().interrupt();
    }
  }

  /**
   * Extracts the trailing numerical ID from an OpenProject API href, e.g. "/api/v3/users/5" -&gt;
   * 5. Returns null for blank/missing hrefs (e.g. an unset assignee).
   */
  public static Integer hrefToId(String href) {
    if (href == null || href.isBlank()) {
      return null;
    }
    String idPart = href.substring(href.lastIndexOf('/') + 1);
    try {
      return Integer.valueOf(idPart);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
