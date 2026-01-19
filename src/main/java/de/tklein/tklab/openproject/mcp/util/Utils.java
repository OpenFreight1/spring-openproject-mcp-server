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
}
