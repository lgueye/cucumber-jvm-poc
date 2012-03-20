package org.diveintojee.poc.cucumberjvm.domain.search;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;

import org.apache.commons.lang3.StringUtils;

public class SearchClause {

  private String field;
  private SearchOperator operator;
  private String value;

  public SearchClause(String field, SearchOperator operator, String value) {
    this.field = field;
    this.operator = operator;
    this.value = value;
  }

  public String getField() {
    return field;
  }

  public SearchOperator getOperator() {
    return operator;
  }

  public String getValue() {
    return value;
  }

  public static SearchClause fromString(final String queryString) {

    if (StringUtils.isEmpty(queryString)
        || queryString.startsWith(SearchOperator.EXACT_MATCH.getToken()) || queryString
        .endsWith(SearchOperator.EXACT_MATCH.getToken())
        ) {
      return null;
    }

    int exactMatchOperatorIndex = queryString.indexOf(SearchOperator.EXACT_MATCH.getToken());

    if (exactMatchOperatorIndex < 0) {
      return new SearchClause(null, null, queryString);
    }

    String[] clause = queryString.split(SearchOperator.EXACT_MATCH.getToken());
    String field = clause[0];
    String value = clause[1];

    if (StringUtils.isEmpty(value)) {
      return null;
    }

    return new SearchClause(StringUtils.trim(field), SearchOperator.EXACT_MATCH,
                            StringUtils.trim(value));

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SearchClause)) {
      return false;
    }

    SearchClause that = (SearchClause) o;

    return Objects.equal(field, that.field)
           && Objects.equal(operator, that.operator)
           && Objects.equal(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(field, operator, value);
  }

  @Override
  public String toString() {
    return Joiner.on("").skipNulls().join(field, operator, value);
  }
}
