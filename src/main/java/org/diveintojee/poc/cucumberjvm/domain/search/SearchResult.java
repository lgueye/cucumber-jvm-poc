package org.diveintojee.poc.cucumberjvm.domain.search;

import org.diveintojee.poc.cucumberjvm.domain.Classified;

import java.util.List;


/**
 * User: lgueye Date: 07/03/12 Time: 12:00
 */
public class SearchResult {

  private SearchQuery query;
  private List<Classified> items;
  private int totalItems;

  private void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public SearchQuery getQuery() {
    return query;
  }

  private void setQuery(SearchQuery query) {
    this.query = query;
  }

  public List<Classified> getItems() {
    return items;
  }

  private void setItems(List<Classified> items) {
    this.items = items;
  }

  public SearchResult(SearchQuery query, List<Classified> items, int totalItems) {
    setItems(items);
    setQuery(query);
    setTotalItems(totalItems);
  }

  public int getTotalItems() {
    return totalItems;
  }

  private int countPages() {
    int
        itemsPerPage =
        (getQuery() == null) ? 0 : getQuery()
            .getItemsPerPage();
    if (itemsPerPage <= 0) {
      return 0;
    }
    int totalItems = getTotalItems();
    int moduloResult = totalItems % itemsPerPage;
    int divideResult = totalItems / itemsPerPage;
    return moduloResult == 0 ? divideResult : divideResult + 1;

  }

  public int getPageIndex() {
    if (countPages() <= 0) {
      return -1;
    }
    final int userRequestedPageIndex = getQuery().getPageIndex();
    if (userRequestedPageIndex < getMin() || userRequestedPageIndex > getMax()) {
      return 0;
    }
    return userRequestedPageIndex;
  }

  public int getFirstPageIndex() {
    if (getPageIndex() == -1) {
      return -1;
    }
    return 0;
  }

  public int getLastPageIndex() {
    if (getPageIndex() == -1) {
      return -1;
    }
    return countPages() - 1;
  }

  public int getPreviousPageIndex() {

    final int pageIndex = getPageIndex();
    if (pageIndex < getMin()) {
      return -1;
    }
    return pageIndex - 1;
  }

  public int getNextPageIndex() {

    final int pageIndex = getPageIndex();
    if (pageIndex == -1 || pageIndex >= getMax()) {
      return -1;
    }
    return pageIndex + 1;
  }

  private int getMin() {
    return 0;
  }

  private int getMax() {
    return Math.max(0, countPages() - 1);
  }
}
