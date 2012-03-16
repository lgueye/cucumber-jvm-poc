package org.diveintojee.poc.cucumberjvm.domain.search;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class SearchClauseTest {

  @Test
  public void fromStringShouldReturnNullWithEmptyInput() throws Exception {

    // Variable
    String queryString;
    SearchClause result;

    // Given
    queryString = null;

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNull(result);

    // Given
    queryString = "";

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNull(result);

  }

  @Test
  public void fromStringShouldSucceed() throws Exception {

    // Variable
    String queryString;
    SearchClause result;
    String expectedField = "title";
    SearchOperator expectedOperator = SearchOperator.EXACT_MATCH;
    String input = "php";
    String expectedValue = "php";

    // Given
    queryString = expectedField.concat(expectedOperator.getToken()).concat(input);

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNotNull(result);
    assertEquals(expectedField, result.getField());
    assertEquals(expectedOperator, result.getOperator());
    assertEquals(expectedValue, result.getValue());

  }

  @Test
  public void fromStringShouldReturnSingleValuedClauseWhenNoOperatorWasFound() throws Exception {

    // Variable
    String queryString;
    SearchClause result;
    String expectedValue = "php";

    // Given
    queryString = expectedValue;

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNotNull(result);
    assertNull(result.getField());
    assertNull(result.getOperator());
    assertEquals(expectedValue, result.getValue());

  }

  @Test
  public void fromStringShouldReturnExactMatchClause() throws Exception {

    // Variable
    String queryString;
    SearchClause result;
    String expectedField = "title";
    SearchOperator expectedOperator = SearchOperator.EXACT_MATCH;
    String expectedValue = "php";

    // Given
    queryString = expectedField.concat(expectedOperator.getToken()).concat(expectedValue);

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNotNull(result);
    assertEquals(expectedField, result.getField());
    assertEquals(expectedOperator, result.getOperator());
    assertEquals(expectedValue, result.getValue());

  }

  @Test
  public void fromStringShouldReturnFullTextClause() throws Exception {

    // Variable
    String queryString;
    SearchClause result;
    String expectedField = "title";
    SearchOperator expectedOperator = SearchOperator.EXACT_MATCH;
    String input = "php";
    String expectedValue = "php";

    // Given
    queryString = expectedField.concat(expectedOperator.getToken()).concat(input);

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNotNull(result);
    assertEquals(expectedField, result.getField());
    assertEquals(expectedOperator, result.getOperator());
    assertEquals(expectedValue, result.getValue());

  }

  @Test
  public void fromStringShouldReturnNullWithIllegalGrammar() throws Exception {

    // Variable
    String queryString;
    SearchClause result;
    String expectedField;
    SearchOperator expectedOperator;
    String expectedValue;

    // Given
    queryString = null;

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNull(result);

    // Given
    queryString = "";

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNull(result);

    // Given
    expectedField = null;
    expectedOperator = SearchOperator.FULL_TEXT;
    expectedValue = "php";
    queryString = expectedOperator.getToken().concat(expectedValue);

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNull(result);

    // Given
    expectedField = "title";
    expectedOperator = SearchOperator.FULL_TEXT;
    expectedValue = null;
    queryString = expectedField.concat(expectedOperator.getToken());

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNull(result);

    // Given
    expectedField = null;
    expectedOperator = SearchOperator.EXACT_MATCH;
    expectedValue = "php";
    queryString = expectedOperator.getToken().concat(expectedValue);

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNull(result);

    // Given
    expectedField = "title";
    expectedOperator = SearchOperator.EXACT_MATCH;
    expectedValue = null;
    queryString = expectedField.concat(expectedOperator.getToken());

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNull(result);

  }

  @Test
  public void fromStringShouldNotAddWildCardsWhenAtLeastOneFound() {

    // Variables
    String queryString;
    SearchClause result;
    String expectedField = "title";
    SearchOperator expectedOperator = SearchOperator.EXACT_MATCH;
    String input;
    String expectedValue;

    // Given
    input = "php*";
    expectedValue = input;
    queryString = expectedField.concat(expectedOperator.getToken()).concat(input);

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNotNull(result);
    assertEquals(expectedField, result.getField());
    assertEquals(expectedOperator, result.getOperator());
    assertEquals(expectedValue, result.getValue());

    // Given
    input = "*php";
    expectedValue = input;
    queryString = expectedField.concat(expectedOperator.getToken()).concat(input);

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNotNull(result);
    assertEquals(expectedField, result.getField());
    assertEquals(expectedOperator, result.getOperator());
    assertEquals(expectedValue, result.getValue());

    // Given
    input = "ph*p";
    expectedValue = input;
    queryString = expectedField.concat(expectedOperator.getToken()).concat(input);

    // When
    result = SearchClause.fromString(queryString);

    // Then
    assertNotNull(result);
    assertEquals(expectedField, result.getField());
    assertEquals(expectedOperator, result.getOperator());
    assertEquals(expectedValue, result.getValue());
  }

}
