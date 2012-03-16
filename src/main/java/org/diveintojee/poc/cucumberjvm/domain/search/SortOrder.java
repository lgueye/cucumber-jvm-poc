package org.diveintojee.poc.cucumberjvm.domain.search;

public enum SortOrder {
  ASCENDING("asc"),
  DESCENDING("desc");

  private final String value;

  private SortOrder(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static SortOrder fromString(String input) {
    for (SortOrder sortOrder : values()) {
      if (sortOrder.getValue().equals(input)) return sortOrder;
    }
    return null;
  }
}
