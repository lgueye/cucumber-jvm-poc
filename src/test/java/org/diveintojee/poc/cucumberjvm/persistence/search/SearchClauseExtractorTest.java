package org.diveintojee.poc.cucumberjvm.persistence.search;

import org.diveintojee.poc.cucumberjvm.domain.search.SearchClause;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SearchClauseExtractorTest {

  private SearchClauseExtractor underTest;

  @Before
  public void before() {
    underTest = new SearchClauseExtractor();
  }


  @Test
  public void extractSearchClausesShouldReturnNull() {
    // Given
    String queryString;
    List<SearchClause> clauses;

    // When
    queryString = null;
    clauses = underTest.fromQueryString(queryString);

    // Then
    assertNull(clauses);

    // When
    queryString = "";
    clauses = underTest.fromQueryString(queryString);

    // Then
    assertNull(clauses);
  }

  @Test
  public void extractSearchClausesShouldReturnClauses() {
    // Given
    String firstClauseAsString = "status:draft";
    String secondClauseAsString = "email:michael@page.com";
    String
        queryString =
        firstClauseAsString.concat(SearchEngine.SEARCH_CLAUSES_SEPARATOR)
            .concat(SearchEngine.SEARCH_CLAUSES_SEPARATOR).concat(secondClauseAsString);

    // When
    List<SearchClause> clauses = underTest.fromQueryString(queryString);

    // Then
    SearchClause firstClause = SearchClause.fromString(firstClauseAsString);
    SearchClause secondClause = SearchClause.fromString(secondClauseAsString);
    assertEquals(Arrays.asList(firstClause, secondClause), clauses);

  }

  @Test
  public void extractSearchClausesShouldReturnEmptyList() {
    // Given
    String queryString;
    List<SearchClause> clauses;

    // When
    queryString = " :php AND php: ";
    clauses = underTest.fromQueryString(queryString);

    // Then
    assertEquals(0, clauses.size());
  }
}
