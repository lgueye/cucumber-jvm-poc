package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 12/03/12 Time: 13:45
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchRepresentationBuilderTest {

  @Test
  public void zeroPagesShouldSetUndefinedPaginationData() {

    // Variables
    String queryString;
    int pageIndex;
    int itemsPerPage;
    int totalItems;
    Set<String> sort;
    SearchQuery query;
    List<Classified> items;
    SearchResult searchResult;
    UriInfo uriInfo;

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 2;
    itemsPerPage = 3;
    totalItems = 0;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);
    items = null;
    uriInfo = mock(UriInfo.class);
    searchResult = new SearchResult(query, items, totalItems);
    SearchRepresentationBuilder.fromResults(searchResult, uriInfo);

    // Then
    assertEquals(-1, searchResult.getFirstPageIndex());
    assertEquals(-1, searchResult.getPreviousPageIndex());
    assertEquals(-1, searchResult.getPageIndex());
    assertEquals(-1, searchResult.getNextPageIndex());
    assertEquals(-1, searchResult.getLastPageIndex());

  }

  @Test
  public void onePageShouldSetUndefinedPreviousAndNext() {

    // Variables
    String queryString;
    int pageIndex;
    int itemsPerPage;
    Set<String> sort;
    SearchQuery query;
    List<Classified> items;
    int totalItems;
    SearchResult searchResult;
    UriInfo uriInfo;
    UriBuilder uriBuilder;
    URI uri;

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 2;
    itemsPerPage = 2;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);
    items = null;
    uriInfo = mock(UriInfo.class);
    uriBuilder = mock(UriBuilder.class);
    when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("q", query.getQuery())).thenReturn(uriBuilder);
    when(uriBuilder.queryParam(eq("sort"), any(String.class))).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("pageIndex", pageIndex)).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("itemsPerPage", itemsPerPage)).thenReturn(uriBuilder);
    uri = URI.create("?q=" + queryString);
    when(uriBuilder.build()).thenReturn(uri);
    totalItems = 1;
    searchResult = new SearchResult(query, items, totalItems);
    SearchRepresentationBuilder.fromResults(searchResult, uriInfo);

    // Then
    assertEquals(0, searchResult.getFirstPageIndex());
    // Irrelevant, points to nothing
    assertEquals(-1, searchResult.getPreviousPageIndex());
    assertEquals(0, searchResult.getPageIndex());
    // Irrelevant, points to nothing
    assertEquals(-1, searchResult.getNextPageIndex());
    assertEquals(0, searchResult.getLastPageIndex());
  }

  @Test
  public void twoPagesShouldSetUndefinedPreviousWhenFirstPageWasAsked() {

    // Variables
    String queryString;
    int pageIndex;
    int itemsPerPage;
    Set<String> sort;
    SearchQuery query;
    List<Classified> items;
    int totalItems;
    SearchResult searchResult;
    UriInfo uriInfo;
    UriBuilder uriBuilder;
    URI uri;

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 0;
    itemsPerPage = 2;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);
    items = Arrays.asList(new Classified(), new Classified(), new Classified());
    uriInfo = mock(UriInfo.class);
    uriBuilder = mock(UriBuilder.class);
    when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("q", query.getQuery())).thenReturn(uriBuilder);
    when(uriBuilder.queryParam(eq("sort"), any(String.class))).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("pageIndex", pageIndex)).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("itemsPerPage", itemsPerPage)).thenReturn(uriBuilder);
    uri = URI.create("?q=" + queryString);
    when(uriBuilder.build()).thenReturn(uri);
    totalItems = 3;
    searchResult = new SearchResult(query, items, totalItems);
    SearchRepresentationBuilder.fromResults(searchResult, uriInfo);

    // Then
    assertEquals(0, searchResult.getFirstPageIndex());
    // Irrelevant, points to nothing
    assertEquals(-1, searchResult.getPreviousPageIndex());
    assertEquals(0, searchResult.getPageIndex());
    assertEquals(1, searchResult.getNextPageIndex());
    assertEquals(1, searchResult.getLastPageIndex());
  }

  @Test
  public void twoPagesShouldSetUndefinedPreviousWhenSecondPageWasAsked() {

    // Variables
    String queryString;
    int pageIndex;
    int itemsPerPage;
    Set<String> sort;
    SearchQuery query;
    List<Classified> items;
    int totalItems;
    SearchResult searchResult;
    UriInfo uriInfo;
    UriBuilder uriBuilder;
    URI uri;

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 1;
    itemsPerPage = 2;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);
    items = Arrays.asList(new Classified(), new Classified(), new Classified());
    uriInfo = mock(UriInfo.class);
    uriBuilder = mock(UriBuilder.class);
    when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("q", query.getQuery())).thenReturn(uriBuilder);
    when(uriBuilder.queryParam(eq("sort"), any(String.class))).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("pageIndex", pageIndex)).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("itemsPerPage", itemsPerPage)).thenReturn(uriBuilder);
    uri = URI.create("?q=" + queryString);
    when(uriBuilder.build()).thenReturn(uri);
    totalItems = 3;
    searchResult = new SearchResult(query, items, totalItems);
    SearchRepresentationBuilder.fromResults(searchResult, uriInfo);

    // Then
    assertEquals(0, searchResult.getFirstPageIndex());
    assertEquals(0, searchResult.getPreviousPageIndex());
    assertEquals(1, searchResult.getPageIndex());
    // Irrelevant, points to nothing
    assertEquals(-1, searchResult.getNextPageIndex());
    assertEquals(1, searchResult.getLastPageIndex());
  }

  @Test
  public void fivePagesShouldSetAllPaginationIndicesWhenThirdPageWasAsked() {

    // Variables
    String queryString;
    int pageIndex;
    int itemsPerPage;
    Set<String> sort;
    SearchQuery query;
    List<Classified> items;
    int totalItems;
    SearchResult searchResult;
    UriInfo uriInfo;
    UriBuilder uriBuilder;
    URI uri;

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 2;
    itemsPerPage = 2;
    sort = new HashSet<String>(asList("id:asc"));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);
    items =
        Arrays.asList(new Classified(), new Classified(), new Classified(), new Classified(),
                      new Classified(), new Classified(), new Classified(), new Classified(),
                      new Classified());
    uriInfo = mock(UriInfo.class);
    uriBuilder = mock(UriBuilder.class);
    when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("q", query.getQuery())).thenReturn(uriBuilder);
    when(uriBuilder.queryParam(eq("sort"), any(String.class))).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("pageIndex", pageIndex)).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("itemsPerPage", itemsPerPage)).thenReturn(uriBuilder);
    uri = URI.create("?q=" + queryString);
    when(uriBuilder.build()).thenReturn(uri);
    totalItems = 9;
    searchResult = new SearchResult(query, items, totalItems);
    SearchRepresentationBuilder.fromResults(searchResult, uriInfo);

    // Then
    assertEquals(0, searchResult.getFirstPageIndex());
    assertEquals(1, searchResult.getPreviousPageIndex());
    assertEquals(2, searchResult.getPageIndex());
    assertEquals(3, searchResult.getNextPageIndex());
    assertEquals(4, searchResult.getLastPageIndex());
  }

  @Test
  public void buildLinksLinkShouldSucceed() {
    // Variables
    String queryString;
    int pageIndex;
    int itemsPerPage;
    Set<String> sort;
    SearchQuery query;
    List<Classified> items;
    int totalItems;
    SearchResult searchResult;
    UriInfo uriInfo;
    UriBuilder uriBuilder;
    URI uri;

    // Given ...
    queryString = "location.postalCode:92*";
    pageIndex = 2;
    itemsPerPage = 2;
    final String sortValue = "id:asc";
    sort = new HashSet<String>(asList(sortValue));
    query = new SearchQuery(queryString, pageIndex, itemsPerPage, sort);
    items =
        Arrays.asList(new Classified(), new Classified(), new Classified(), new Classified(),
                      new Classified(), new Classified(), new Classified(), new Classified(),
                      new Classified());
    uriInfo = mock(UriInfo.class);
    uriBuilder = mock(UriBuilder.class);
    when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("q", query.getQuery())).thenReturn(uriBuilder);
    when(uriBuilder.queryParam(eq("sort"), any(String.class))).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("pageIndex", pageIndex)).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("itemsPerPage", itemsPerPage)).thenReturn(uriBuilder);
    uri = URI.create("?q=" + queryString);
    when(uriBuilder.build()).thenReturn(uri);
    totalItems = 9;
    searchResult = new SearchResult(query, items, totalItems);
    SearchRepresentationBuilder.fromResults(searchResult, uriInfo);
    // When
    verify(uriBuilder, times(5)).queryParam("q", query.getQuery());
    verify(uriBuilder, times(5)).queryParam("sort", sortValue);
    verify(uriBuilder, times(1)).queryParam("pageIndex", searchResult.getFirstPageIndex());
    verify(uriBuilder, times(1)).queryParam("pageIndex", searchResult.getPreviousPageIndex());
    verify(uriBuilder, times(1)).queryParam("pageIndex", searchResult.getPageIndex());
    verify(uriBuilder, times(1)).queryParam("pageIndex",
                                            searchResult.getNextPageIndex());
    verify(uriBuilder, times(1)).queryParam("pageIndex", searchResult.getLastPageIndex());
    verify(uriBuilder, times(5)).queryParam("itemsPerPage", itemsPerPage);

  }

}
