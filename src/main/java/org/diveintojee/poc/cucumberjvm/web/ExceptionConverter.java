package org.diveintojee.poc.cucumberjvm.web;

import org.apache.commons.lang3.StringUtils;
import org.diveintojee.poc.cucumberjvm.domain.NotFoundException;
import org.diveintojee.poc.cucumberjvm.domain.ResponseError;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


/**
 * User: lgueye Date: 16/02/12 Time: 18:56
 */
@Component
public class ExceptionConverter {

  public static final List<String> SUPPORTED_MEDIA_TYPES = Arrays
      .asList(MediaType.APPLICATION_JSON,
              MediaType.APPLICATION_XML);

  public static final String DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON;

  /**
   * @param th
   * @return
   */
  public int resolveHttpStatus(final Throwable th) {
//    if (th != null) {
//      th.printStackTrace();
//    }
    if (th == null) {
      return HttpServletResponse.SC_OK;
    }

    if (th instanceof NotFoundException) {
      return HttpServletResponse.SC_NOT_FOUND;
    }

    if (th instanceof IllegalArgumentException || th instanceof ValidationException) {
      return HttpServletResponse.SC_BAD_REQUEST;
    }

    if (th instanceof IllegalStateException) {
      return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
    }

    if (th instanceof WebApplicationException
        && ((WebApplicationException) th).getResponse() != null) {
      return ((WebApplicationException) th).getResponse().getStatus();
    }

    return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

  }

  /**
   * @param request
   * @param th
   * @return
   */
  public String resolveMesage(final HttpServletRequest request, final Throwable th) {
    if (th == null && request == null) {
      return StringUtils.EMPTY;
    }

    if (th == null) {
      return StringUtils.EMPTY;
    }

    if (request == null) {
      return th.getMessage();
    }

    if (th instanceof ConstraintViolationException) {

      final
      ConstraintViolationException
          constraintViolationException =
          (ConstraintViolationException) th;

      final
      Set<ConstraintViolation<?>>
          violations = constraintViolationException.getConstraintViolations();

      final ConstraintViolation<?> violation = violations.iterator().next();

      return violation.getMessage();

    }

    return th.getMessage();

  }

  public ResponseError toResponseError(final Throwable th, final HttpServletRequest request) {
    final String message = resolveMesage(request, th);
    final int httpStatus = resolveHttpStatus(th);
    return new ResponseError(message, httpStatus);
  }
}
