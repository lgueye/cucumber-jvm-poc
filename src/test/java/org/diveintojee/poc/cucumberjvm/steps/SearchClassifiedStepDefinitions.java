/*
 *
 */
package org.diveintojee.poc.cucumberjvm.steps;

import org.diveintojee.poc.cucumberjvm.TestUtils;

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
public class SearchClassifiedStepDefinitions extends SharedExecutionContext {

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
    SharedExecutionContext.results =
        TestUtils.findClassifiedsByCriteria(field, value, exact, SharedExecutionContext.pageIndex,
                                            SharedExecutionContext.itemsPerPage,
                                            SharedExecutionContext.sort);
  }

  @Then("^I get \"([^\"]*)\" items$")
  public void I_get_items(int itemsCount) {
    assertEquals(itemsCount, SharedExecutionContext.results.getTotalItems());
  }

  @Then("^the items are:$")
  public void the_items_are(List<List<String>> table)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    TestUtils.diffTable(table, SharedExecutionContext.results.getItems());
  }

  @When("^I search classifieds for which \"([^\"]*)\" contains \"([^\"]*)\"$")
  public void I_search_classifieds_for_which_contains(String field, String value)
      throws UnsupportedEncodingException {
    boolean exact = false;
    SharedExecutionContext.results =
        TestUtils.findClassifiedsByCriteria(field, value, exact, SharedExecutionContext.pageIndex,
                                            SharedExecutionContext.itemsPerPage,
                                            SharedExecutionContext.sort);
  }

  @When("^I search all classifieds$")
  public void I_search_all_classifieds() throws UnsupportedEncodingException {
    boolean exact = false;
    final String field = null;
    final String value = null;
    SharedExecutionContext.results =
        TestUtils.findClassifiedsByCriteria(field, value, exact, SharedExecutionContext.pageIndex,
                                            SharedExecutionContext.itemsPerPage,
                                            SharedExecutionContext.sort);
  }

}
