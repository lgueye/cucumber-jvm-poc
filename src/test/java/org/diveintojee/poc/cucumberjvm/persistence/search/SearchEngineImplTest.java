package org.diveintojee.poc.cucumberjvm.persistence.search;

import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.search.OrderBy;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.admin.indices.exists.IndicesExistsResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.deletebyquery.DeleteByQueryResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.AdminClient;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.action.admin.indices.exists.IndicesExistsRequestBuilder;
import org.elasticsearch.client.action.bulk.BulkRequestBuilder;
import org.elasticsearch.client.action.delete.DeleteRequestBuilder;
import org.elasticsearch.client.action.deletebyquery.DeleteByQueryRequestBuilder;
import org.elasticsearch.client.action.index.IndexRequestBuilder;
import org.elasticsearch.client.action.search.SearchRequestBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


import static org.diveintojee.poc.cucumberjvm.domain.search.SortOrder.ASCENDING;
import static org.diveintojee.poc.cucumberjvm.domain.search.SortOrder.DESCENDING;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchEngineImplTest {

  @Mock
  private Client searchClient;

  @Mock
  private QueryBuilderResolver queryBuilderResolver;

  @Mock
  private ClassifiedToJsonByteArrayConverter jsonBinaryConverter;

  @InjectMocks
  private SearchEngineImpl underTest = new SearchEngineImpl();

  @Test(expected = IllegalArgumentException.class)
  public void indexShouldThrowIllagalArgumentExceptionWithNullContent() {

    Classified Classified;
    Long id;

    // Given
    Classified = null;
    id = 4L;

    // When
    underTest.index(Classified, id);

  }

  @Test(expected = IllegalArgumentException.class)
  public void indexShouldThrowIllagalArgumentExceptionWithNullId() {

    Classified Classified;
    Long id;

    // Given
    Classified = new Classified();
    id = null;

    // When
    underTest.index(Classified, id);

  }

  @Test
  public void indexShouldIndexFullContent() {

    // Given
    Classified Classified = TestUtils.validClassified();
    Long identifier = 5L;
    IndexRequestBuilder builder = mock(IndexRequestBuilder.class);
    byte[] source = "{foo:bar}".getBytes();
    @SuppressWarnings("unchecked")
    ListenableActionFuture<IndexResponse> future = mock(ListenableActionFuture.class);

    // When
    when(searchClient.prepareIndex(Indices.CLASSIFIED,
                                   Indices.Types.CLASSIFIED,
                                   identifier.toString()))
        .thenReturn(builder);
    when(jsonBinaryConverter.convert(Classified))
        .thenReturn(source);
    when(builder.setSource(source))
        .thenReturn(builder);
    when(builder.execute())
        .thenReturn(future);
    when(builder.setRefresh(true))
        .thenReturn(builder);
    underTest.index(Classified, identifier);

    // Then
    verify(searchClient)
        .prepareIndex(Indices.CLASSIFIED,
                      Indices.Types.CLASSIFIED,
                      identifier.toString());
    verify(jsonBinaryConverter).convert(Classified);
    verify(builder).setSource(source);
    verify(builder).execute();
    verify(builder).setRefresh(true);
    verifyNoMoreInteractions(searchClient, builder, jsonBinaryConverter);

  }

  @Test
  public void clearIndexShouldSucceed() {

    // Given
    AdminClient adminClient = mock(AdminClient.class);
    IndicesAdminClient indicesAdminClient = mock(IndicesAdminClient.class);
    IndicesExistsRequestBuilder
        indicesExistsRequestBuilder =
        mock(IndicesExistsRequestBuilder.class);
    IndicesExistsResponse indicesExistsResponse = mock(IndicesExistsResponse.class);
    @SuppressWarnings("unchecked")
    ListenableActionFuture<IndicesExistsResponse> indicesExistsResponseListenableActionFuture =
        mock(ListenableActionFuture.class);

    DeleteByQueryRequestBuilder deleteByQueryRequestBuilder =
        mock(DeleteByQueryRequestBuilder.class);
    @SuppressWarnings("unchecked")
    ListenableActionFuture<DeleteByQueryResponse> deleteByQueryResponseListenableActionFuture =
        mock(ListenableActionFuture.class);

    // When
    when(searchClient.admin())
        .thenReturn(adminClient);
    when(adminClient.indices())
        .thenReturn(indicesAdminClient);
    when(indicesAdminClient.prepareExists(Indices.CLASSIFIED))
        .thenReturn(indicesExistsRequestBuilder);
    when(indicesExistsRequestBuilder.execute())
        .thenReturn(indicesExistsResponseListenableActionFuture);
    when(indicesExistsResponseListenableActionFuture.actionGet())
        .thenReturn(indicesExistsResponse);
    when(indicesExistsResponse.exists())
        .thenReturn(true);

    when(searchClient.prepareDeleteByQuery(Indices.CLASSIFIED))
        .thenReturn(deleteByQueryRequestBuilder);
    when(deleteByQueryRequestBuilder.setQuery(Mockito.<QueryBuilder>any()))
        .thenReturn(deleteByQueryRequestBuilder);
    when(deleteByQueryRequestBuilder.execute())
        .thenReturn(deleteByQueryResponseListenableActionFuture);

    underTest.clearIndex();

    // Then
    verify(searchClient).admin();
    verify(adminClient).indices();
    verify(searchClient).prepareDeleteByQuery(Indices.CLASSIFIED);
    verify(indicesAdminClient).prepareExists(Indices.CLASSIFIED);
    verify(indicesExistsRequestBuilder).execute();
    verify(indicesExistsResponseListenableActionFuture).actionGet();
    verify(indicesExistsResponse).exists();
    verify(deleteByQueryRequestBuilder).setQuery(Matchers.<QueryBuilder>any());
    verify(deleteByQueryRequestBuilder).execute();
    verify(deleteByQueryResponseListenableActionFuture).actionGet();
    verifyNoMoreInteractions(searchClient, adminClient, indicesAdminClient,
                             indicesExistsRequestBuilder, indicesExistsResponse,
                             indicesExistsResponseListenableActionFuture,
                             deleteByQueryRequestBuilder,
                             deleteByQueryResponseListenableActionFuture);

  }

  @Test
  public void removeFromIndexShouldSucceed() {

    // Given
    Long id = 5L;
    DeleteRequestBuilder deleteRequestBuilder = mock(DeleteRequestBuilder.class);
    @SuppressWarnings("unchecked")
    ListenableActionFuture<DeleteResponse> deleteResponseListenableActionFuture =
        mock(ListenableActionFuture.class);

    // When
    when(searchClient.prepareDelete(Indices.CLASSIFIED,
                                    Indices.Types.CLASSIFIED,
                                    id.toString()))
        .thenReturn(deleteRequestBuilder);
    when(deleteRequestBuilder.setRefresh(true))
        .thenReturn(deleteRequestBuilder);
    when(deleteRequestBuilder.execute())
        .thenReturn(deleteResponseListenableActionFuture);
    underTest.removeFromIndex(id);

    // Then
    verify(searchClient)
        .prepareDelete(Indices.CLASSIFIED,
                       Indices.Types.CLASSIFIED,
                       id.toString());
    verify(deleteRequestBuilder).setRefresh(true);
    verify(deleteRequestBuilder).execute();
    verify(deleteResponseListenableActionFuture).actionGet();

    verifyNoMoreInteractions(searchClient, deleteRequestBuilder,
                             deleteResponseListenableActionFuture);

  }

  @Test
  public void applySortShouldApplyDefaultSort() {

    // Variables
    List<OrderBy> orderByList;
    SearchRequestBuilder searchRequestBuilder;

    // Given
    orderByList = null;
    searchRequestBuilder = mock(SearchRequestBuilder.class);

    // When
    underTest.applySort(orderByList, searchRequestBuilder);

    // Then
    verify(searchRequestBuilder).addSort(OrderBy.DEFAULT.getField(), SortOrder.DESC);
    verifyNoMoreInteractions(searchRequestBuilder);

  }

  @Test
  public void applySortShouldNotApplyDefaultSortWhenDefaultFieldAlreadySpecified() {

    // Variables
    List<OrderBy> orderByList;
    SearchRequestBuilder searchRequestBuilder;

    // Given
    orderByList = Arrays.asList(new OrderBy(OrderBy.DEFAULT.getField(),
                                            ASCENDING));
    searchRequestBuilder = mock(SearchRequestBuilder.class);

    // When
    underTest.applySort(orderByList, searchRequestBuilder);

    // Then
    verify(searchRequestBuilder).addSort(OrderBy.DEFAULT.getField(), SortOrder.ASC);
    verifyNoMoreInteractions(searchRequestBuilder);

  }

  @Test
  public void applySortShouldApplyDefaultSortInAdditionToOtherSortsWhenSortsAreSpecified() {

    // Variables
    List<OrderBy> orderByList;
    SearchRequestBuilder searchRequestBuilder;

    // Given
    OrderBy firstOrderSpecification =
        new OrderBy("status", ASCENDING);
    OrderBy secondOrderSpecification =
        new OrderBy("created", DESCENDING);
    OrderBy thirdOrderSpecification =
        new OrderBy("id", DESCENDING);
    orderByList = Arrays.asList(firstOrderSpecification, secondOrderSpecification,
                                thirdOrderSpecification);
    searchRequestBuilder = mock(SearchRequestBuilder.class);

    // When
    underTest.applySort(orderByList, searchRequestBuilder);

    // Then
    verify(searchRequestBuilder).addSort("status", SortOrder.ASC);
    verify(searchRequestBuilder).addSort("created", SortOrder.DESC);
    verify(searchRequestBuilder).addSort("id", SortOrder.DESC);
    verify(searchRequestBuilder).addSort(OrderBy.DEFAULT.getField(), SortOrder.DESC);
    verifyNoMoreInteractions(searchRequestBuilder);

  }

  @Test
  public final void bulkReIndexShouldIgnoreEmptyCollection() {

    // Variables
    List<Classified> Classifieds;

    // Given
    Classifieds = null;

    // When
    underTest.bulkReIndex(Classifieds);

    // Then
    verifyZeroInteractions(searchClient, jsonBinaryConverter);

    // Given
    Classifieds = Collections.emptyList();

    // When
    underTest.bulkReIndex(Classifieds);

    // Then
    verifyZeroInteractions(searchClient, jsonBinaryConverter);

  }

  @Test
  public final void bulkReIndexShouldPrintFailureMessage() {

    // Variables
    Long id = 5L;
    List<Classified> Classifieds;
    Classified Classified;
    BulkRequestBuilder bulkRequestBuilder;
    DeleteRequestBuilder deleteRequestBuilder;
    byte[] byteArray;
    IndexRequestBuilder indexRequestBuilder;
    ListenableActionFuture<BulkResponse> bulkResponseListenableActionFuture;
    BulkResponse bulkResponse;

    // Given
    Classified = mock(Classified.class);
    Classifieds = new ArrayList<Classified>();
    Classifieds.add(Classified);
    bulkRequestBuilder = mock(BulkRequestBuilder.class);
    deleteRequestBuilder = mock(DeleteRequestBuilder.class);
    byteArray = "{}".getBytes();
    indexRequestBuilder = mock(IndexRequestBuilder.class);
    bulkResponseListenableActionFuture = mock(ListenableActionFuture.class);
    bulkResponse = mock(BulkResponse.class);

    // When
    when(searchClient.prepareBulk())
        .thenReturn(bulkRequestBuilder);
    when(Classified.getId())
        .thenReturn(id);
    when(searchClient.prepareDelete(Indices.CLASSIFIED,
                                    Indices.Types.CLASSIFIED,
                                    id.toString()))
        .thenReturn(deleteRequestBuilder);
    when(jsonBinaryConverter.convert(Classified))
        .thenReturn(byteArray);
    when(searchClient.prepareIndex(Indices.CLASSIFIED,
                                   Indices.Types.CLASSIFIED,
                                   id.toString()))
        .thenReturn(indexRequestBuilder);
    when(indexRequestBuilder.setSource(byteArray))
        .thenReturn(indexRequestBuilder);
    when(bulkRequestBuilder.setRefresh(true))
        .thenReturn(bulkRequestBuilder);
    when(bulkRequestBuilder.execute())
        .thenReturn(bulkResponseListenableActionFuture);
    when(bulkResponseListenableActionFuture.actionGet())
        .thenReturn(bulkResponse);
    when(bulkResponse.hasFailures())
        .thenReturn(true);
    underTest.bulkReIndex(Classifieds);

    // Then
    verify(searchClient).prepareBulk();
    verify(Classified).getId();
    verify(searchClient)
        .prepareDelete(Indices.CLASSIFIED,
                       Indices.Types.CLASSIFIED,
                       id.toString());
    verify(bulkRequestBuilder).add(deleteRequestBuilder);
    verify(jsonBinaryConverter).convert(Classified);
    verify(searchClient)
        .prepareIndex(Indices.CLASSIFIED,
                      Indices.Types.CLASSIFIED,
                      id.toString());
    verify(indexRequestBuilder).setSource(byteArray);
    verify(bulkRequestBuilder).add(indexRequestBuilder);
    verify(bulkRequestBuilder).setRefresh(true);
    verify(bulkRequestBuilder).execute();
    verify(bulkResponseListenableActionFuture).actionGet();
    verify(bulkResponse).tookInMillis();
    verify(bulkResponse).hasFailures();
    verify(bulkResponse).buildFailureMessage();
    verifyNoMoreInteractions(Classified, bulkRequestBuilder, deleteRequestBuilder,
                             indexRequestBuilder, bulkResponseListenableActionFuture, bulkResponse);
  }

  @Test
  public final void bulkReIndexShouldSucceed() {

    // Variables
    Long id = 5L;
    List<Classified> Classifieds;
    Classified Classified;
    BulkRequestBuilder bulkRequestBuilder;
    DeleteRequestBuilder deleteRequestBuilder;
    byte[] byteArray;
    IndexRequestBuilder indexRequestBuilder;
    ListenableActionFuture<BulkResponse> bulkResponseListenableActionFuture;
    BulkResponse bulkResponse;

    // Given
    Classified = mock(Classified.class);
    Classifieds = new ArrayList<Classified>();
    Classifieds.add(Classified);
    bulkRequestBuilder = mock(BulkRequestBuilder.class);
    deleteRequestBuilder = mock(DeleteRequestBuilder.class);
    byteArray = "{}".getBytes();
    indexRequestBuilder = mock(IndexRequestBuilder.class);
    bulkResponseListenableActionFuture = mock(ListenableActionFuture.class);
    bulkResponse = mock(BulkResponse.class);

    // When
    when(searchClient.prepareBulk())
        .thenReturn(bulkRequestBuilder);
    when(Classified.getId())
        .thenReturn(id);
    when(searchClient.prepareDelete(Indices.CLASSIFIED,
                                    Indices.Types.CLASSIFIED,
                                    id.toString()))
        .thenReturn(deleteRequestBuilder);
    when(jsonBinaryConverter.convert(Classified))
        .thenReturn(byteArray);
    when(searchClient.prepareIndex(Indices.CLASSIFIED,
                                   Indices.Types.CLASSIFIED,
                                   id.toString()))
        .thenReturn(indexRequestBuilder);
    when(indexRequestBuilder.setSource(byteArray))
        .thenReturn(indexRequestBuilder);
    when(bulkRequestBuilder.setRefresh(true))
        .thenReturn(bulkRequestBuilder);
    when(bulkRequestBuilder.execute())
        .thenReturn(bulkResponseListenableActionFuture);
    when(bulkResponseListenableActionFuture.actionGet())
        .thenReturn(bulkResponse);
    when(bulkResponse.hasFailures())
        .thenReturn(false);
    underTest.bulkReIndex(Classifieds);

    // Then
    verify(searchClient).prepareBulk();
    verify(Classified).getId();
    verify(searchClient)
        .prepareDelete(Indices.CLASSIFIED,
                       Indices.Types.CLASSIFIED,
                       id.toString());
    verify(bulkRequestBuilder).add(deleteRequestBuilder);
    verify(jsonBinaryConverter).convert(Classified);
    verify(searchClient)
        .prepareIndex(Indices.CLASSIFIED,
                      Indices.Types.CLASSIFIED,
                      id.toString());
    verify(indexRequestBuilder).setSource(byteArray);
    verify(bulkRequestBuilder).add(indexRequestBuilder);
    verify(bulkRequestBuilder).setRefresh(true);
    verify(bulkRequestBuilder).execute();
    verify(bulkResponseListenableActionFuture).actionGet();
    verify(bulkResponse).tookInMillis();
    verify(bulkResponse).hasFailures();
    verifyNoMoreInteractions(Classified, bulkRequestBuilder, deleteRequestBuilder,
                             indexRequestBuilder, bulkResponseListenableActionFuture, bulkResponse);

  }

  @Test
  public void shouldConfigureSearchRequest() throws Exception {
    // Given
    SearchQuery query = new SearchQuery(null, 1, 50, null);

    SearchRequestBuilder mockBuilder = mock(SearchRequestBuilder.class);
    when(searchClient.prepareSearch(Indices.CLASSIFIED))
        .thenReturn(mockBuilder);
    when(mockBuilder.setTypes(Indices.Types.CLASSIFIED))
        .thenReturn(mockBuilder);

    QueryBuilder mockQueryBuilder = mock(QueryBuilder.class);
    when(queryBuilderResolver.resolveQueryBuilder(query))
        .thenReturn(mockQueryBuilder);

    // When
    SearchRequestBuilder builder = underTest.convertSearchQueryToRequestBuilder(query);

    // Then
    assertSame(mockBuilder, builder);

    verify(mockBuilder)
        .setQuery(mockQueryBuilder);
    verify(mockBuilder)
        .setFrom(Math.max(query.getPageIndex() * query.getItemsPerPage(),
                          SearchEngineImpl.ELASTIC_SEARCH_FIRST_RESULT_INDEX));
    verify(mockBuilder)
        .setSize(query.getItemsPerPage());
  }

/*
  @Test
  public final void searchShouldSucceed() throws UnsupportedEncodingException {

    // Variables
    SearchQuery searchQuery;
    SearchResponse searchResponse;
    SearchHits hits;
    SearchHit[] searchHitArray;
    SearchHit hit0;
    SearchHit hit1;
    Classified classified0;
    Classified classified1;

    // Given
    searchQuery = mock(SearchQuery.class);
    searchResponse = mock(SearchResponse.class);
    hits = mock(SearchHits.class);
    hit0 = mock(SearchHit.class);
    hit1 = mock(SearchHit.class);
    searchHitArray = new SearchHit[] {hit0, hit1};
    final byte[] source0 = "hit0".getBytes("utf-8");
    final byte[] source1 = "hit1".getBytes("utf-8");
    classified0 = mock(Classified.class);
    classified1 = mock(Classified.class);

    when(underTest.search(searchQuery)).thenReturn(searchResponse);
    when(underTest.search(searchQuery)).thenReturn(searchResponse);
    when(underTest.search(searchQuery)).thenReturn(searchResponse);
    when(searchResponse.getHits()).thenReturn(hits);
    when(hits.getHits()).thenReturn(searchHitArray);
    when(hit0.source()).thenReturn(source0);
    when(hit1.source()).thenReturn(source1);
    when(jsonByteArrayToClassifiedConverter.convert(source0)).thenReturn(classified0);
    when(jsonByteArrayToClassifiedConverter.convert(source1)).thenReturn(classified1);

    // When
    underTest.search(searchQuery);

    verify(searchEngine).search(searchQuery);
    verify(searchResponse).getHits();
    verify(hits).getHits();
    verify(jsonByteArrayToClassifiedConverter).convert(source0);
    verify(jsonByteArrayToClassifiedConverter).convert(source1);

  }
  */
}
