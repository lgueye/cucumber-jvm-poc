package org.diveintojee.poc.cucumberjvm.persistence.search;

import com.google.common.base.Joiner;

import org.apache.commons.collections.CollectionUtils;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchClause;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component(QueryBuilderResolver.BEAN_ID)
public class QueryBuilderResolver {

  public static final String BEAN_ID = "QueryBuilderResolver";

  @Autowired
  private SearchClauseExtractor searchClauseExtractor;

  public QueryBuilder resolveQueryBuilder(SearchQuery query) {

    List<SearchClause> searchClauses =
        searchClauseExtractor.fromQueryString(query.getQuery());

    // No clause => return all results
    // Example ?q= or ?sort=....
    if (CollectionUtils.isEmpty(searchClauses)) {
      return QueryBuilders.matchAllQuery();
    }

    // One  clause => full text search on all fields
    // Example ?q=java
    if (CollectionUtils.size(searchClauses) == 1) {

      SearchClause searchClause = searchClauses.get(0);

      if (searchClause.getOperator() == null && searchClause.getField() == null) {
        return QueryBuilders.queryString(searchClause.getValue()).defaultField("_all");
      }

    }

    String queryString = Joiner.on(SearchEngine.SEARCH_CLAUSES_SEPARATOR).join(searchClauses);

    return QueryBuilders.queryString(queryString).analyzeWildcard(true);

  }
}
