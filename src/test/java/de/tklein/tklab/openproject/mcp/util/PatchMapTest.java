package de.tklein.tklab.openproject.mcp.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.tklein.tklab.openproject.mcp.util.PatchMap.Nullable;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PatchMapTest {

  @Test
  void ofWithDropNullValues_filtersOutNullValues() {
    // WHEN
    Map<String, Object> result = PatchMap.of(Nullable.DROP_NULL_VALUES,
        "key1", "value1",
        "key2", null,
        "key3", "value3");

    // THEN
    assertThat(result).containsExactlyInAnyOrderEntriesOf(
        Map.of("key1", "value1", "key3", "value3"));
    assertTrue(result.containsKey("key1"));
    assertTrue(result.containsKey("key3"));
    assertFalse(result.containsKey("key2"));
    assertEquals("value1", result.get("key1"));
    assertEquals("value3", result.get("key3"));
  }

  @Test
  void ofWithAllowNullValues_includesNullValues() {
    // WHEN
    Map<String, Object> result = PatchMap.of(Nullable.ALLOW_NULL_VALUES,
        "key1", "value1",
        "key2", null,
        "key3", "value3");

    // THEN
    assertTrue(result.containsKey("key1"));
    assertTrue(result.containsKey("key2"));
    assertTrue(result.containsKey("key3"));
    assertEquals("value1", result.get("key1"));
    assertNull(result.get("key2"));
    assertEquals("value3", result.get("key3"));
  }

  @Test
  void ofWithEmptyArguments_returnsEmptyMap() {
    // WHEN
    Map<String, Object> result = PatchMap.of(Nullable.DROP_NULL_VALUES);

    // THEN
    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void ofWithOddNumberOfArguments_throwsIllegalArgumentException() {
    // WHEN & THEN
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
        () -> PatchMap.of(Nullable.DROP_NULL_VALUES, "key1", "value1", "key2"));

    assertEquals("Number of  arguments must be even", exception.getMessage());
  }

  @Test
  void ofWithNonStringKey_throwsClassCastException() {
    // WHEN & THEN
    assertThrows(ClassCastException.class,
        () -> PatchMap.of(Nullable.DROP_NULL_VALUES, 123, "value1"));
  }

  @Test
  void ofWithMixedTypes_values() {
    // WHEN
    Map<String, Object> result = PatchMap.of(Nullable.DROP_NULL_VALUES,
        "stringKey", "stringValue",
        "intKey", 42,
        "boolKey", true,
        "nullKey", null);

    // THEN
    assertThat(result).containsExactlyInAnyOrderEntriesOf(
        Map.of("stringKey", "stringValue", "intKey", 42, "boolKey", true));
    assertEquals("stringValue", result.get("stringKey"));
    assertEquals(42, result.get("intKey"));
    assertEquals(true, result.get("boolKey"));
  }

  @Test
  void ofWithDropNullValues_onlyFiltersNullValues() {
    // WHEN
    Map<String, Object> result = PatchMap.of(Nullable.DROP_NULL_VALUES,
        "key1", "",
        "key2", 0,
        "key3", false,
        "key4", null);

    // THEN
    assertThat(result).containsExactlyInAnyOrderEntriesOf(
        Map.of("key1", "", "key2", 0, "key3", false));
    assertEquals("", result.get("key1"));
    assertEquals(0, result.get("key2"));
    assertEquals(false, result.get("key3"));
  }
}
