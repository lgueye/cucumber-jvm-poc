package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.domain.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 17/02/12 Time: 17:37
 */
@RunWith(MockitoJUnitRunner.class)
public class ExceptionConverterTest {

  private ExceptionConverter underTest = new ExceptionConverter();

  @Test
  public final void resolveStatusShouldReturn200WithNullInput() {

    // Variable
    Throwable throwable;

    // Given
    throwable = null;

    // When
    int status = underTest.resolveHttpStatus(throwable);

    // Then
    assertEquals(HttpServletResponse.SC_OK, status);

  }

  @Test
  public final void resolveStatusShouldReturn404WithNotFoundException() {

    // Variable
    Throwable throwable;

    // Given
    throwable = mock(NotFoundException.class);

    // When
    int status = underTest.resolveHttpStatus(throwable);

    // Then
    assertEquals(HttpServletResponse.SC_NOT_FOUND, status);

  }

  @Test
  public final void resolveStatusShouldReturn400WithIllegalArgumentException() {

    // Variable
    Throwable throwable;

    // Given
    throwable = mock(IllegalArgumentException.class);

    // When
    int status = underTest.resolveHttpStatus(throwable);

    // Then
    assertEquals(HttpServletResponse.SC_BAD_REQUEST, status);

  }

  @Test
  public final void resolveStatusShouldReturn400WithValidationException() {

    // Variable
    Throwable throwable;

    // Given
    throwable = mock(ValidationException.class);

    // When
    int status = underTest.resolveHttpStatus(throwable);

    // Then
    assertEquals(HttpServletResponse.SC_BAD_REQUEST, status);

  }

  @Test
  public final void resolveStatusShouldReturn500WithIllegalStateException() {

    // Variable
    Throwable throwable;

    // Given
    throwable = mock(IllegalStateException.class);

    // When
    int status = underTest.resolveHttpStatus(throwable);

    // Then
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, status);

  }

  @Test
  public final void resolveStatusShouldReturnResponseStatusWithWebApplicationException() {

    // Variable
    WebApplicationException throwable;
    Response response;
    int responseStatus;

    // Given
    response = mock(Response.class);
    throwable = mock(WebApplicationException.class);
    responseStatus = 5;
    when(throwable.getResponse()).thenReturn(response);
    when(response.getStatus()).thenReturn(responseStatus);

    // When
    int status = underTest.resolveHttpStatus(throwable);

    // Then
    assertEquals(responseStatus, status);

  }

  @Test
  public final void resolveStatusShouldReturn500WithOtherExceptions() {

    // Variable
    Throwable throwable;
    Response response;
    int responseStatus;

    // Given
    response = mock(Response.class);
    throwable = mock(NullPointerException.class);

    // When
    int status = underTest.resolveHttpStatus(throwable);

    // Then
    assertEquals(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, status);

  }

}
