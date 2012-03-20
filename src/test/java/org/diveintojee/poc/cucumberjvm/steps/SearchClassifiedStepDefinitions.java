/*
 *
 */
package org.diveintojee.poc.cucumberjvm.steps;

import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.web.SearchRepresentation;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import cucumber.annotation.Before;
import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

import static org.junit.Assert.assertEquals;

/**
 * @author louis.gueye@gmail.com
 */
public class SearchClassifiedStepDefinitions {

  private SearchRepresentation results;

  @Before
  public void before() {
    TestUtils.cleanRepository();
  }

  @Given("^the repository:$")
  public void the_repository(List<List<String>> table) {
    TestUtils.setupRepository(table);
  }

  @When("^I search classifieds for which \"([^\"]*)\" is \"([^\"]*)\"$")
  public void I_search_classifieds_for_which_is(String field, String value)
      throws UnsupportedEncodingException {
    boolean exact = true;
    results = TestUtils.findClassifiedsByCriteria(field, value, exact);
  }

  @Then("^I get \"([^\"]*)\" items$")
  public void I_get_items(int itemsCount) {
    assertEquals(itemsCount, results.getTotalItems());
  }

  @Then("^the items are:$")
  public void the_items_are(List<List<String>> table)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    TestUtils.diffTable(table, results.getItems());
  }

  @When("^I search classifieds for which \"([^\"]*)\" contains \"([^\"]*)\"$")
  public void I_search_classifieds_for_which_contains(String field, String value)
      throws UnsupportedEncodingException {
    boolean exact = false;
    results = TestUtils.findClassifiedsByCriteria(field, value, exact);
  }

}
