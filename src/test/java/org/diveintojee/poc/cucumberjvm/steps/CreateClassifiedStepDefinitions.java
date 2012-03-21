/*
 *
 */
package org.diveintojee.poc.cucumberjvm.steps;

import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.ResponseError;

import javax.servlet.http.HttpServletResponse;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author louis.gueye@gmail.com
 */
public class CreateClassifiedStepDefinitions extends SharedExecutionContext {

  @Given("^a valid classified$")
  public void a_valid_classified() {
    SharedExecutionContext.classified = TestUtils.validClassified();
  }

  @Given("^I am a customer$")
  public void I_am_a_customer() {
  }

  @When("^I load the classified as \"([^\"]*)\"$")
  public void I_load_the_classified_as(final String format) {
    String uri = SharedExecutionContext.clientResponse.getLocation().toString();
    SharedExecutionContext.clientResponse = TestUtils.loadClassified(uri, format);
  }

  @When("^I send \"([^\"]*)\"$")
  public void I_send(final String format) {
    SharedExecutionContext.requestFormat = format;
  }

  @Given("^I set \"([^\"]*)\" to \"([^\"]*)\"$")
  public void I_set_to(final String field, final String value) {
    if ("title".equalsIgnoreCase(field)) {
      SharedExecutionContext.classified.setTitle(value);
    }
  }

  @When("^I try to create the classified$")
  public void I_try_to_create() {
    SharedExecutionContext.clientResponse =
        TestUtils
            .createClassified(SharedExecutionContext.classified,
                              SharedExecutionContext.requestFormat,
                              null, SharedExecutionContext.language);
  }

  @Given("^I use \"([^\"]*)\" language$")
  public void i_use_language(final String language) {
    SharedExecutionContext.language = language;
  }

  @Then("^its status is \"([^\"]*)\"$")
  public void its_status_is(final String status) {
    final Classified classified = SharedExecutionContext.clientResponse.getEntity(Classified.class);
    assertEquals(status, classified.getStatus().toString());
  }

  @Then("^the creation fails$")
  public void the_creation_fails() {
    assertTrue(SharedExecutionContext.clientResponse.getStatus() != HttpServletResponse.SC_CREATED);
  }

  @Then("^the creation is successful$")
  public void the_creation_is_successful() {
    assertTrue(SharedExecutionContext.clientResponse.getStatus() == HttpServletResponse.SC_CREATED);
  }

  @Then("^the error message is \"([^\"]*)\"$")
  public void the_error_message_is(final String errorMessage) {
    final
    ResponseError
        responseError =
        SharedExecutionContext.clientResponse.getEntity(ResponseError.class);
    assertEquals(errorMessage, responseError.getMessage());
  }

}
