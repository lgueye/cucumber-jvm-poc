package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.diveintojee.poc.cucumberjvm.domain.search.OrderBy;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchResult;
import org.diveintojee.poc.cucumberjvm.domain.search.SortOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 17/02/12 Time: 16:24
 */
@RunWith(MockitoJUnitRunner.class)
public class ClassifiedsResourceTest {

  @Mock
  private Facade facade;

  @Mock
  private UriInfo uriInfo;

  @InjectMocks
  private ClassifiedsResource underTest;

  @Test
  public final void createShouldSucceed() throws Throwable {

    // Variable
    Classified classified;
    Long id;
    UriBuilder uriBuilder;
    URI uri;

    // Given
    classified = mock(Classified.class);
    id = 8L;
    uriBuilder = mock(UriBuilder.class);
    uri = URI.create("http://example.com");
    when(facade.createClassified(classified)).thenReturn(id);
    when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.path(ClassifiedResource.class)).thenReturn(uriBuilder);
    when(uriBuilder.path(String.valueOf(id))).thenReturn(uriBuilder);
    when(uriBuilder.build()).thenReturn(uri);

    // When
    Response response = underTest.create(classified);

    // Then
    assertSame(uri, response.getMetadata().getFirst("Location"));
    assertTrue(response.getStatus() == HttpServletResponse.SC_CREATED);
    assertNull(response.getEntity());

    verify(facade).createClassified(classified);
    verify(uriInfo).getBaseUriBuilder();
    verify(uriBuilder).path(ClassifiedResource.class);
    verify(uriBuilder).path(String.valueOf(id));
    verify(uriBuilder).build();
    verifyNoMoreInteractions(facade, uriInfo, uriBuilder, classified);

  }

  @Test
  @Ignore
  public final void getShouldSucceed() throws Throwable {

    // Variable
    List<Classified> results;
    String query;
    int itemsPerPage;
    int pageIndex;
    Set<String> sort;
    Set<OrderBy> expectedSort;
    UriBuilder uriBuilder;
    SearchResult searchResults;

    // Given
    Classified item0 = TestUtils.validClassified();
    item0.setCreated(Calendar.getInstance().getTime());
    Classified item1 = TestUtils.validClassified();
    item1.setCreated(Calendar.getInstance().getTime());
    results = Arrays.asList(item0, item1);
    query = "status:draft";
    itemsPerPage = 2;
    pageIndex = 1;
    sort = new HashSet<String>(Arrays.asList("created:desc", "status:asc"));
    expectedSort =
        new HashSet<OrderBy>(
            Arrays.asList(OrderBy.DEFAULT, new OrderBy("status", SortOrder.ASCENDING)));
    searchResults = mock(SearchResult.class);
    when(facade.search(Matchers.<SearchQuery>any(SearchQuery.class))).thenReturn(searchResults);
    uriInfo = mock(UriInfo.class);
    uriBuilder = mock(UriBuilder.class);
    when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("q", query)).thenReturn(uriBuilder);
    when(uriBuilder.queryParam(eq("sort"), any(String.class))).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("pageIndex", pageIndex)).thenReturn(uriBuilder);
    when(uriBuilder.queryParam("itemsPerPage", itemsPerPage)).thenReturn(uriBuilder);

    // When
    Response response = underTest.findByCriteria(query, itemsPerPage, pageIndex, sort);

    // Then
    verify(facade).search(Matchers.<SearchQuery>any(SearchQuery.class));
    verifyNoMoreInteractions(facade);
    verifyZeroInteractions(uriInfo);

    assertTrue(response.getStatus() == HttpServletResponse.SC_OK);
    SearchResult entity = (SearchResult) response.getEntity();
    assertNotNull(entity);
    List<Classified> items = entity.getItems();
    assertSame(results, items);

    SearchQuery responseQuery = entity.getQuery();
    assertSame(query, responseQuery.getQuery());
    assertSame(itemsPerPage, responseQuery.getItemsPerPage());
    assertSame(pageIndex, responseQuery.getPageIndex());
    assertEquals(expectedSort, responseQuery.getSort());


  }

}
