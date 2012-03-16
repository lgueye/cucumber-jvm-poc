/*
 *
 */
package org.diveintojee.poc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletResponse;

import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.ResponseError;
import org.diveintojee.poc.cucumberjvm.domain.Status;

import com.sun.jersey.api.client.ClientResponse;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

/**
 * @author louis.gueye@gmail.com
 */
public class CreateAdvertStepDefinitions {

    private final String user = null;
    private String language = null;
    private Classified advert;
    private ClientResponse clientResponse;

    @Given("^a valid advert$")
    public void a_valid_advert() {
        advert = TestUtils.validClassified();
    }

    @Given("^I am a customer$")
    public void I_am_a_customer() {}

    @Then("^I create the advert$")
    public void I_create_the_advert() {
        clientResponse = TestUtils.createAdvert(advert, "application/json", null, "en", false);
    }

    @When("^I load the advert as \"([^\"]*)\"$")
    public void I_load_the_advert_as(final String format) {
        clientResponse = TestUtils.loadAdvert(format);
    }

    @Given("^I set \"([^\"]*)\" to \"([^\"]*)\"$")
    public void I_set_to(final String field, final String value) {
        if ("title".equalsIgnoreCase(field)) {
            advert.setTitle(value);
        }
    }

    @When("^I try to create the advert$")
    public void I_try_to_create() {
        clientResponse = TestUtils.createAdvert(advert, "application/json", null, "en", false);
    }

    @Given("^I use \"([^\"]*)\" language$")
    public void i_use_language(final String language) {
        this.language = language;
    }

    @Then("^its status is \"([^\"]*)\"$")
    public void its_status_is(final Status status) {
        final Classified advert = clientResponse.getEntity(Classified.class);
        assertEquals(status, advert.getStatus());
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
