package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;


/**
 * User: lgueye Date: 16/02/12 Time: 11:43
 */
@Component
@Path("/classified")
public class ClassifiedResource {

  @Autowired
  private Facade facade;

  @Context
  UriInfo uriInfo;

  @GET
  @Path("{id}")
  @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
  public Response get(@PathParam(value = "id") final Long id) throws Throwable {

    final Classified classified = this.facade.getClassified(id);

    return Response.ok(classified).build();

  }

  @DELETE
  @Path("{id}")
  public Response delete(@PathParam(value = "id") final Long id) throws Throwable {

    this.facade.deleteClassified(id);

    return Response.ok().build();

  }

  @PUT
  @Path("{id}")
  @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
  public Response update(@PathParam(value = "id") final Long id, final Classified classified)
      throws Throwable {

    classified.setId(id);

    this.facade.updateClassified(classified);

    return Response.ok().build();

  }

}
