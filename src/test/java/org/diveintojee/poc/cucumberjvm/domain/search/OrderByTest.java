package org.diveintojee.poc.cucumberjvm.domain.search;

import org.junit.Test;

import static org.diveintojee.poc.cucumberjvm.domain.search.SortOrder.ASCENDING;
import static org.diveintojee.poc.cucumberjvm.domain.search.SortOrder.DESCENDING;
import static org.junit.Assert.assertEquals;

/**
 * User: lgueye Date: 07/03/12 Time: 18:34
 */
public class OrderByTest {

  @Test
  public void parseWithoutSortOder() {
    // Given ...
    String value = "title";

    // When ...
    OrderBy orderBy = OrderBy.parse(value);

    // Then ...
    assertEquals("title", orderBy.getField());
    assertEquals(SortOrder.DESCENDING, orderBy.getDirection());
  }

  @Test
  public void parseWithSortOrder() {
    // Given ...
    String value = "title:desc";

    // When ...
    OrderBy orderBy = OrderBy.parse(value);

    // Then ...
    assertEquals("title", orderBy.getField());
    assertEquals(DESCENDING, orderBy.getDirection());
  }

  @Test
  public void parseWithSortOrderIgnoreCase() {
    // Given ...
    String value = "title:DeSC";

    // When ...
    OrderBy orderBy = OrderBy.parse(value);

    // Then ...
    assertEquals("title", orderBy.getField());
    assertEquals(DESCENDING, orderBy.getDirection());
  }

}
