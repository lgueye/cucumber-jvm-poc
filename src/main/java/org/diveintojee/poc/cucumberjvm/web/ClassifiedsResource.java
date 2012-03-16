package org.diveintojee.poc.cucumberjvm.web;

import org.apache.commons.lang3.StringUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


/**
 * User: lgueye Date: 15/03/12 Time: 13:52
 */
@Component
@Path("/classifieds")
public class ClassifiedsResource {

  @Autowired
  private Facade facade;

  @Context
  UriInfo uriInfo;

  @POST
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response create(final Classified classified) throws Throwable {

    final Long id = this.facade.createClassified(classified);

    final
    URI
        uri =
        this.uriInfo.getBaseUriBuilder().path(ClassifiedResource.class).path(String.valueOf(id)).build();

    return Response.created(uri).build();

  }

  @GET
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response findByCriteria(@DefaultValue(StringUtils.EMPTY) @QueryParam("q") String query,
                                 @DefaultValue(StringUtils.EMPTY + SearchQuery.DEFAULT_ITEMS_PER_PAGE) @QueryParam("itemsPerPage") int itemsPerPage,
                                 @DefaultValue(StringUtils.EMPTY + SearchQuery.DEFAULT_PAGE_INDEX) @QueryParam("pageIndex") int pageIndex,
                                 @DefaultValue(StringUtils.EMPTY) @QueryParam("sort") Set<String> sort) throws Throwable {

    final SearchQuery searchQuery = new SearchQuery(query, pageIndex, itemsPerPage, sort);

    SearchResult searchResult = this.facade.search(searchQuery);

    SearchRepresentation
        searchRepresentation =
        SearchRepresentation.fromResults(searchResult, uriInfo);

    return Response.ok(searchRepresentation).build();

  }
}
