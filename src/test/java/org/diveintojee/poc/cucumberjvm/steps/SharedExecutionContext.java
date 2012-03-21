/*
 *
 */
package org.diveintojee.poc.cucumberjvm.steps;

import com.sun.jersey.api.client.ClientResponse;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.web.SearchRepresentation;

import cucumber.annotation.Before;

/**
 * @author louis.gueye@gmail.com
 */
public class SharedExecutionContext {

  protected static String language;
  protected static Classified classified;
  protected static ClientResponse clientResponse;
  protected static String requestFormat;
  protected static SearchRepresentation results;
  protected static int pageIndex = -1;
  protected static int itemsPerPage = -1;
  protected static String sort;


  @Before
  public void before() {
    language = null;
    classified = null;
    clientResponse = null;
    requestFormat = null;
    results = null;
    pageIndex = -1;
    itemsPerPage = -1;
    sort = null;
  }


}
