package org.diveintojee.poc.cucumberjvm.domain.search;

import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * User: lgueye Date: 07/03/12 Time: 12:01
 */
public class SearchQuery {

  public static final int DEFAULT_ITEMS_PER_PAGE = 2;
  public static final int DEFAULT_PAGE_INDEX = 0;

  private String query;
  private int pageIndex;
  private int itemsPerPage;
  private Set<OrderBy> sort;

  public SearchQuery() {
    setItemsPerPage(DEFAULT_ITEMS_PER_PAGE);
    setSort(new HashSet<OrderBy>(Arrays.asList(OrderBy.DEFAULT)));
    setPageIndex(DEFAULT_PAGE_INDEX);
  }

  public SearchQuery(String query) {
    this();
    setQuery(query);
  }

  private Set<OrderBy> fromStringSet(Set<String> input) {

    if (CollectionUtils.isEmpty(input)) {
      return null;
    }

    Set<OrderBy> orderBys = new HashSet<OrderBy>(input.size());

    for (String orderByAsString : input) {

      OrderBy orderBy = OrderBy.parse(orderByAsString);

      if (orderBy != null) {
        orderBys.add(orderBy);
      }

    }

    return orderBys;

  }

  public SearchQuery(String query, int pageIndex, int itemsPerPage, Set<String> sort) {

    if (pageIndex < 0) {
      setPageIndex(DEFAULT_PAGE_INDEX);
    } else {
      setPageIndex(pageIndex);
    }
    if (itemsPerPage <= 0) {
      setItemsPerPage(DEFAULT_ITEMS_PER_PAGE);
    } else {
      setItemsPerPage(itemsPerPage);
    }
    if (CollectionUtils.isEmpty(sort)) {
      setSort(new HashSet<OrderBy>(Arrays.asList(OrderBy.DEFAULT)));
    } else {
      setSort(fromStringSet(sort));
    }

    setQuery(query);

  }

  public String getQuery() {
    return query;
  }

  private void setQuery(String query) {
    this.query = query;
  }

  public int getPageIndex() {
    return pageIndex;
  }

  private void setPageIndex(int pageIndex) {
    this.pageIndex = pageIndex;
  }

  public int getItemsPerPage() {
    return itemsPerPage;
  }

  private void setItemsPerPage(int itemsPerPage) {
    this.itemsPerPage = itemsPerPage;
  }

  public Set<OrderBy> getSort() {
    return sort;
  }

  private void setSort(Set<OrderBy> sort) {
    this.sort = sort;
  }
}
