package org.diveintojee.poc.cucumberjvm.persistence.search;


import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.search.OrderBy;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchResult;
import org.diveintojee.poc.cucumberjvm.domain.search.SortOrder;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.elasticsearch.search.sort.SortOrder.ASC;
import static org.elasticsearch.search.sort.SortOrder.DESC;

@Component
public class SearchEngineImpl implements SearchEngine {

  public static final int ELASTIC_SEARCH_FIRST_RESULT_INDEX = 0;

  @Autowired
  private Client searchClient;

  @Autowired
  private ClassifiedToJsonByteArrayConverter classifiedToJsonByteArrayConverter;

  @Autowired
  private JsonByteArrayToClassifiedConverter jsonByteArrayToClassifiedConverter;

  @Autowired
  private QueryBuilderResolver queryBuilderResolver;

  private static final Logger LOGGER = LoggerFactory.getLogger(SearchEngineImpl.class);

  @Override
  public SearchResult search(final SearchQuery query) {

    SearchRequestBuilder searchRequestBuilder = convertSearchQueryToRequestBuilder(query);

    SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

    SearchHits hits = searchResponse.getHits();

    SearchHit[] searchHitArray = hits.getHits();

    final int totalHits = (int) searchResponse.getHits().getTotalHits();

    if (ArrayUtils.isEmpty(searchHitArray)) {

      return new SearchResult(query, Collections.<Classified>emptyList(), totalHits);

    }

    List<Classified> results = new ArrayList<Classified>(searchHitArray.length);

    for (SearchHit hit : searchHitArray) {

      Classified classified = jsonByteArrayToClassifiedConverter.convert(hit.source());

      results.add(classified);

    }

    return new SearchResult(query, results, totalHits);

  }

  @Override
  public void index(final Classified classified, final Long id) {

    Preconditions.checkArgument(id != null, "identifier required");

    Preconditions.checkArgument(classified != null, "classified required");

    searchClient.prepareIndex(Indices.CLASSIFIED, Indices.Types.CLASSIFIED, id.toString())
        .setSource(classifiedToJsonByteArrayConverter.convert(classified))
        .setRefresh(true)
        .execute().actionGet();

  }

  SearchRequestBuilder convertSearchQueryToRequestBuilder(final SearchQuery query) {

    SearchRequestBuilder searchRequestBuilder =
        searchClient
            .prepareSearch(Indices.CLASSIFIED)
            .setTypes(Indices.Types.CLASSIFIED);

    if (query != null) {

      final int itemsPerPage = query.getItemsPerPage();
      searchRequestBuilder.setSize(itemsPerPage);

      final int from = Math.max(query.getPageIndex() * itemsPerPage,
                                ELASTIC_SEARCH_FIRST_RESULT_INDEX);
      searchRequestBuilder.setFrom(from);

      QueryBuilder queryBuilder = queryBuilderResolver.resolveQueryBuilder(query);

      searchRequestBuilder.setQuery(queryBuilder);

      applySort(new ArrayList<OrderBy>(query.getSort()), searchRequestBuilder);

    }

    return searchRequestBuilder;

  }

  void applySort(final List<OrderBy> orderByList, final SearchRequestBuilder searchRequestBuilder) {

    if (CollectionUtils.isNotEmpty(orderByList)) {

      for (OrderBy orderBy : orderByList) {

        searchRequestBuilder.addSort(orderBy.getField(),
                                     orderBy.getDirection() == SortOrder.ASCENDING ? ASC : DESC);

      }

    }

    if (orderSpecificationDoesntContainDefaultSortField(orderByList)) {
      applyDefaultSort(searchRequestBuilder);
    }

  }

  void applyDefaultSort(final SearchRequestBuilder searchRequestBuilder) {
    searchRequestBuilder.addSort(OrderBy.DEFAULT.getField(), DESC);
  }

  boolean orderSpecificationDoesntContainDefaultSortField(final List<OrderBy> orderByList) {

    if (CollectionUtils.isEmpty(orderByList)) {
      return true;
    }

    Collection<String> orderFields = Collections2.transform(orderByList, GetField.INSTANCE);

    return (CollectionUtils.isEmpty(orderFields) || !orderFields
        .contains(OrderBy.DEFAULT.getField()));

  }

  @Override
  public void clearIndex() {

    if (searchClient.admin().indices().prepareExists(Indices.CLASSIFIED).execute().actionGet()
        .exists()) {

      searchClient.prepareDeleteByQuery(Indices.CLASSIFIED)
          .setQuery(QueryBuilders.matchAllQuery())
          .execute().actionGet();

    }

  }

  @Override
  public void removeFromIndex(final Long id) {

    searchClient.prepareDelete(Indices.CLASSIFIED, Indices.Types.CLASSIFIED, id.toString())
        .setRefresh(true).execute().actionGet();

  }

  @Override
  public void bulkReIndex(final List<Classified> classifieds) {

    if (CollectionUtils.isEmpty(classifieds)) {
      return;
    }

    BulkRequestBuilder bulkRequest = searchClient.prepareBulk();

    for (Classified classified : classifieds) {

      Long id = classified.getId();

      bulkRequest.add(searchClient.prepareDelete(Indices.CLASSIFIED,
                                                 Indices.Types.CLASSIFIED,
                                                 id.toString()));

      bulkRequest.add(searchClient.prepareIndex(Indices.CLASSIFIED,
                                                Indices.Types.CLASSIFIED,
                                                id.toString()) //
                          .setSource(classifiedToJsonByteArrayConverter.convert(classified)));

    }

    BulkResponse bulkResponse = bulkRequest.setRefresh(true).execute().actionGet();

    LOGGER.info("Bulk reindex done --------------------------------");
    LOGGER.info("Bulk reindex took {} ms", bulkResponse.tookInMillis());

    if (bulkResponse.hasFailures()) {
      LOGGER.error("Bulk reindex failures report --------------------------------");
      LOGGER.error(bulkResponse.buildFailureMessage());
      LOGGER.error("End failures report --------------------------------");
    }
  }

  private static enum GetField implements Function<OrderBy, String> {

    INSTANCE;

    @Override
    public String apply(OrderBy input) {

      if (input == null) {
        return null;
      }

      return input.getField();

    }

  }

}
