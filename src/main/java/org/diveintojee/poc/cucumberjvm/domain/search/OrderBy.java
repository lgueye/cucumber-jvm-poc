package org.diveintojee.poc.cucumberjvm.domain.search;

import com.google.common.base.Objects;
import com.google.common.base.Splitter;

import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;

public class OrderBy {

  private static final String DEFAULT_FIELD = "created";
  public static final OrderBy DEFAULT = new OrderBy(DEFAULT_FIELD, SortOrder.DESCENDING);

  private final String field;
  private final SortOrder direction;

  public OrderBy(String field, SortOrder direction) {
    this.field = field;
    this.direction = direction;
  }

  public static OrderBy parse(String orderByAsString) {

    if (StringUtils.isEmpty(orderByAsString)) {
      return null;
    }

    if (StringUtils.isNotEmpty(orderByAsString)
        && orderByAsString.indexOf(SearchOperator.EXACT_MATCH.getToken()) < 0) {
      return new OrderBy(orderByAsString, SortOrder.DESCENDING);
    }

    Iterable<String>
        iterable =
        Splitter.on(SearchOperator.EXACT_MATCH.getToken()).omitEmptyStrings().trimResults().split(
            orderByAsString);

    Iterator<String> iterator = iterable.iterator();

    if (!iterator.hasNext()) {
      return null;
    }

    String field = iterator.next();

    if (!iterator.hasNext()) {
      return null;
    }

    String order = iterator.next();

    return new OrderBy(field, SortOrder.fromString(order.toLowerCase()));

  }

  public String getField() {
    return field;
  }

  public SortOrder getDirection() {
    return direction;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    OrderBy orderBy = (OrderBy) o;
    return Objects.equal(field, orderBy.field)
           && Objects.equal(direction, orderBy.direction);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(field, direction);
  }

  /**
   * @return a string representation of the object.
   */
  @Override
  public String toString() {
    return new StringBuilder().append(getField()).append(SearchOperator.EXACT_MATCH.getToken())
        .append(getDirection().getValue()).toString();
  }
}
