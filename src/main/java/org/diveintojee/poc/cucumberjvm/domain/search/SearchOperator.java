package org.diveintojee.poc.cucumberjvm.domain.search;

public enum SearchOperator {
  EXACT_MATCH(":"),
  FULL_TEXT(":");

  private final String token;

  private SearchOperator(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }

  @Override
  public String toString() {
    return token;
  }

}
