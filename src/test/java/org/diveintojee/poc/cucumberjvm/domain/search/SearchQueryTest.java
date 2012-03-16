package org.diveintojee.poc.cucumberjvm.domain.search;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class SearchQueryTest {

  @Test
  public void shouldUseDefaultValues() {

    // Variables
    SearchQuery query;
    String queryString;
    int pageIndex;
    int itemsPerPage;
    Set<String> sort;

    // Given ...
    query = new SearchQuery();

    // Then ...
    assertEquals(SearchQuery.DEFAULT_ITEMS_PER_PAGE, query.getItemsPerPage());
    assertEquals(SearchQuery.DEFAULT_PAGE_INDEX, query.getPageIndex());
    assertEquals(new HashSet<OrderBy>(asList(OrderBy.DEFAULT)), query.getSort());

    // Given ...
    queryString = "location.postalCode:92*";
    query = new SearchQuery(queryString);

    // Then ...
    assertEquals(SearchQuery.DEFAULT_ITEMS_PER_PAGE, query.getItemsPerPage());
    assertEquals(SearchQuery.DEFAULT_PAGE_INDEX, query.getPageIndex());
    assertEquals(new HashSet<OrderBy>(asList(OrderBy.DEFAULT)), query.getSort());

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = -1;
    itemsPerPage = 5;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);

    // Then ...
    assertEquals(SearchQuery.DEFAULT_PAGE_INDEX, query.getPageIndex());

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 0;
    itemsPerPage = 5;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);

    // Then ...
    assertEquals(SearchQuery.DEFAULT_PAGE_INDEX, query.getPageIndex());

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 2;
    itemsPerPage = -1;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);

    // Then ...
    assertEquals(SearchQuery.DEFAULT_ITEMS_PER_PAGE, query.getItemsPerPage());

    // Given ...
    queryString = null;
    pageIndex = 2;
    itemsPerPage = 0;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);

    // Then ...
    assertEquals(SearchQuery.DEFAULT_ITEMS_PER_PAGE, query.getItemsPerPage());

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 2;
    itemsPerPage = 0;
    sort = null;
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);

    // Then ...
    assertEquals(new HashSet<OrderBy>(asList(OrderBy.DEFAULT)), query.getSort());

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 2;
    itemsPerPage = 0;
    sort = new HashSet<String>();
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);

    // Then ...
    assertEquals(new HashSet<OrderBy>(asList(OrderBy.DEFAULT)), query.getSort());

  }

  @Test
  public void shouldNotUseDefaultValues() {

    // Variables
    SearchQuery query;
    String queryString;
    int pageIndex;
    int itemsPerPage;
    Set<String> sort;

    // Given ...
    queryString = "location.postalCode:92*";
    itemsPerPage = 5;
    pageIndex = 2;
    sort = new HashSet<String>(asList("id:asc"));
    Set<OrderBy> expectedSort = new HashSet<OrderBy>(asList(new OrderBy("id", SortOrder.ASCENDING)));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);

    // Then ...
    assertEquals(queryString, query.getQuery());
    assertEquals(pageIndex, query.getPageIndex());
    assertEquals(itemsPerPage, query.getItemsPerPage());
    assertEquals(expectedSort, query.getSort());

  }

}
