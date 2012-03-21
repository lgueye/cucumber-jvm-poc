/*
 *
 */
package org.diveintojee.poc.cucumberjvm.steps;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.diveintojee.poc.cucumberjvm.domain.search.Link;

import java.net.URI;
import java.util.List;
import java.util.Map;

import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author louis.gueye@gmail.com
 */
public class PaginationStepDefinitions extends SharedExecutionContext {

  @When("^I ask for page \"([^\"]*)\"$")
  public void I_ask_for_page(int pageIndex) {
    SharedExecutionContext.pageIndex = pageIndex;
  }

  @When("^I ask \"([^\"]*)\" items per page$")
  public void I_ask_items_per_page(int itemsPerPage) {
    SharedExecutionContext.itemsPerPage = itemsPerPage;
  }

  @Then("^the representation should contain no \"([^\"]*)\" link$")
  public void the_representation_should_contain_no_link(String rel) {
    assertNotNull(SharedExecutionContext.results);
    final Map<String, Link> links = SharedExecutionContext.results.getLinks();
    if (links != null && StringUtils.isNotEmpty((CharSequence) links.get(rel))) {
      assertFalse("the representation should contain no " + rel + " link", false);
    }
  }

  @When("^I sort by \"([^\"]*)\" \"([^\"]*)\"$")
  public void I_sort_by(String direction, String field) {
    SharedExecutionContext.sort = field + ":" + (direction.startsWith("desc") ? "desc" : "asc");
  }

  @Then("^\"([^\"]*)\" link \"([^\"]*)\" should be \"([^\"]*)\"$")
  public void link_page_index_should_be(String rel, String field, String value) {
    assertNotNull(SharedExecutionContext.results);
    final Map<String, Link> links = SharedExecutionContext.results.getLinks();
    assertNotNull(links);
    Link link = links.get(rel);
    assertNotNull(link);
    String href = link.getHref();
    assertTrue(StringUtils.isNotEmpty(href));
    List<NameValuePair> params = URLEncodedUtils.parse(URI.create(href), "UTF-8");
    for (NameValuePair nameValuePair : params) {
      if (field.equals(nameValuePair.getName())) {
        assertEquals(value, nameValuePair.getValue());
        break;
      }
    }
  }

}
