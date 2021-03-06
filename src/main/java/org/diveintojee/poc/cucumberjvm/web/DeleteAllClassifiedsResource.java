package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


/**
 * User: lgueye Date: 06/03/12 Time: 18:56
 */
@Component
@Path("/debug/delete_all_classifieds")
public class DeleteAllClassifiedsResource {

  @Autowired
  private Facade facade;

  @POST
  public Response execute() {
    facade.deleteAllClassifieds();
    return Response.ok().build();
  }
}
