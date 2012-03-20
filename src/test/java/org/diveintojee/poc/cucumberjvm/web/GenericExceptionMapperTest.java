package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.domain.ResponseError;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 17/02/12 Time: 15:29
 */
@RunWith(MockitoJUnitRunner.class)
public class GenericExceptionMapperTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private ExceptionConverter exceptionConverter;

  @InjectMocks
  private GenericExceptionMapper underTest = new GenericExceptionMapper();

  @Test
  public void toResponseShouldSetDefaultContentType() throws Exception {

    // Variable
    Throwable throwable;
    ResponseError error;
    String preferredResponseMediaType;

    // Given
    error = mock(ResponseError.class);
    throwable = mock(Throwable.class);
    preferredResponseMediaType = "application/exe";
    when(exceptionConverter.toResponseError(throwable, request)).thenReturn(error);
    when(request.getHeader("Accept")).thenReturn(preferredResponseMediaType);

    // When
    Response response = underTest.toResponse(throwable);

    //Then
    assertTrue(response.getMetadata().getFirst("Content-Type")
                   .equals(ExceptionConverter.DEFAULT_MEDIA_TYPE));
    int httpStatus = error.getHttpStatus();
    assertTrue(response.getStatus() == httpStatus);
    assertSame(response.getEntity(), error);
    verify(error, times(2)).getHttpStatus();
    verify(exceptionConverter).toResponseError(throwable, request);
    verify(request).getHeader("Accept");
    verifyNoMoreInteractions(request, exceptionConverter, throwable, error);

  }

  @Test
  public void toResponseShouldSucceed() throws Exception {

    // Variable
    Throwable throwable;
    ResponseError error;
    String preferredResponseMediaType;

    // Given
    error = mock(ResponseError.class);
    throwable = mock(Throwable.class);
    preferredResponseMediaType = "application/xml";
    when(exceptionConverter.toResponseError(throwable, request)).thenReturn(error);
    when(request.getHeader("Accept")).thenReturn(preferredResponseMediaType);

    // When
    Response response = underTest.toResponse(throwable);

    //Then
    int httpStatus = error.getHttpStatus();
    assertTrue(response.getStatus() == httpStatus);
    assertSame(response.getEntity(), error);
    verify(error, times(2)).getHttpStatus();
    verify(exceptionConverter).toResponseError(throwable, request);
    verify(request).getHeader("Accept");
    verifyNoMoreInteractions(request, exceptionConverter, throwable, error);

  }
}
