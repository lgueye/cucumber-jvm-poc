/*
 *
 */
package org.diveintojee.poc.cucumberjvm.steps;

import com.sun.jersey.api.client.ClientResponse;

import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.ResponseError;

import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletResponse;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author louis.gueye@gmail.com
 */
public class CreateAdvertStepDefinitions {

  private final String user = null;
  private String language = null;
  private Classified advert;
  private ClientResponse clientResponse;
  private String requestFormat;

  @Given("^a valid advert$")
  public void a_valid_advert() {
    advert = TestUtils.validClassified();
  }

  @Given("^I am a customer$")
  public void I_am_a_customer() {
  }

  @When("^I load the advert as \"([^\"]*)\"$")
  public void I_load_the_advert_as(final String format) {
    String uri = clientResponse.getLocation().toString();
    clientResponse = TestUtils.loadAdvert(uri, format);
  }

  @When("^I send \"([^\"]*)\"$")
  public void I_send(final String format) {
    this.requestFormat = format;
  }

  @Given("^I set \"([^\"]*)\" to \"([^\"]*)\"$")
  public void I_set_to(final String field, final String value) {
    if ("title".equalsIgnoreCase(field)) {
      advert.setTitle(value);
    }
  }

  @When("^I try to create the advert$")
  public void I_try_to_create() throws ExecutionException, InterruptedException {
    clientResponse = TestUtils.createAdvert(advert, requestFormat, null, this.language);
  }

  @Given("^I use \"([^\"]*)\" language$")
  public void i_use_language(final String language) {
    this.language = language;
  }

  @Then("^its status is \"([^\"]*)\"$")
  public void its_status_is(final String status) {
    final Classified advert = clientResponse.getEntity(Classified.class);
    assertEquals(status, advert.getStatus().toString());
  }

  @Then("^the creation fails$")
  public void the_creation_fails() {
    assertTrue(clientResponse.getStatus() != HttpServletResponse.SC_CREATED);
  }

  @Then("^the creation is successful$")
  public void the_creation_is_successful() {
    assertTrue(clientResponse.getStatus() == HttpServletResponse.SC_CREATED);
  }

  @Then("^the error message is \"([^\"]*)\"$")
  public void the_error_message_is(final String errorMessage) {
    final ResponseError responseError = clientResponse.getEntity(ResponseError.class);
    assertEquals(errorMessage, responseError.getMessage());
  }
}
