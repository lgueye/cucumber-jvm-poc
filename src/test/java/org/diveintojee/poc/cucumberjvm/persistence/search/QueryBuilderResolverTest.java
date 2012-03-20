package org.diveintojee.poc.cucumberjvm.persistence.search;

import org.diveintojee.poc.cucumberjvm.domain.search.SearchClause;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.when;

/**
 * Created by IntelliJ IDEA. User: lgueye Date: 24/01/12 Time: 11:35 To change this template use
 * File | Settings | File Templates.
 */
@RunWith(MockitoJUnitRunner.class)
public class QueryBuilderResolverTest {

  @Mock
  private SearchClauseExtractor searchClauseExtractor;

  @InjectMocks
  private QueryBuilderResolver underTest = new QueryBuilderResolver();

  @Test
  public void resolveQueryBuilderShouldResolveToAMatchAllQueryBuilder() {

    SearchQuery query;
    QueryBuilder queryBuilder;

    // Given
    query = new SearchQuery();

    // When
    when(searchClauseExtractor.fromQueryString(query.getQuery())).thenReturn(null);
    queryBuilder = underTest.resolveQueryBuilder(query);

    // Then
    assertTrue((queryBuilder instanceof MatchAllQueryBuilder));
  }

  @Test
  public void resolveQueryBuilderShouldResolveToAQueryStringOnAllFieldsQueryBuilder() {
    SearchQuery query;
    QueryBuilder queryBuilder;
    List<SearchClause> searchClauses;
    SearchClause searchClause;

    // Given
    query = new SearchQuery("php");
    searchClauses = new ArrayList<SearchClause>();
    searchClause = SearchClause.fromString(query.getQuery());
    searchClauses.add(searchClause);

    // When
    when(searchClauseExtractor.fromQueryString(query.getQuery())).thenReturn(searchClauses);
    queryBuilder = underTest.resolveQueryBuilder(query);

    // Then
    assertTrue((queryBuilder instanceof QueryStringQueryBuilder));
    String builderAsString = queryBuilder.toString();
    assertTrue(builderAsString.contains("\"default_field\" : \"_all\""));
    assertTrue(builderAsString.contains("\"query\" : \"php\""));
  }

  @Test
  public void resolveQueryBuilderShouldResolveToAQueryStringForExactMatchSearch() {
    SearchQuery query;
    QueryBuilder queryBuilder;
    List<SearchClause> searchClauses;
    SearchClause searchClause;

    // Given
    query = new SearchQuery("status:draft");
    searchClauses = new ArrayList<SearchClause>();
    searchClause = SearchClause.fromString(query.getQuery());
    searchClauses.add(searchClause);

    // When
    when(searchClauseExtractor.fromQueryString(query.getQuery())).thenReturn(searchClauses);
    queryBuilder = underTest.resolveQueryBuilder(query);

    // Then
    assertTrue((queryBuilder instanceof QueryStringQueryBuilder));
    String builderAsString = queryBuilder.toString();
    assertThat(builderAsString, containsString("query_string"));
    assertThat(builderAsString, containsString("\"query\" : \"status:draft\""));
  }

  @Test
  public void resolveQueryBuilderShouldResolveToAQueryStringOnPreciseField() {
    SearchQuery query;
    QueryBuilder queryBuilder;
    List<SearchClause> searchClauses;
    SearchClause searchClause;

    // Given
    query = new SearchQuery("title:*php*");
    searchClauses = new ArrayList<SearchClause>();
    searchClause = SearchClause.fromString(query.getQuery());
    searchClauses.add(searchClause);

    // When
    when(searchClauseExtractor.fromQueryString(query.getQuery())).thenReturn(searchClauses);
    queryBuilder = underTest.resolveQueryBuilder(query);

    // Then
    assertTrue((queryBuilder instanceof QueryStringQueryBuilder));
    String builderAsString = queryBuilder.toString();
    //System.out.println("builderAsString = " + builderAsString);
    assertTrue(builderAsString.contains("query_string"));
    assertTrue(builderAsString.contains("\"query\" : \"title:*php*\""));
  }

}
