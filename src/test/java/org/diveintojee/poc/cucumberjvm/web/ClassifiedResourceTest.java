package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 17/02/12 Time: 16:24
 */
@RunWith(MockitoJUnitRunner.class)
public class ClassifiedResourceTest {

  @Mock
  private Facade facade;

  @Mock
  private UriInfo uriInfo;

  @InjectMocks
  private ClassifiedResource underTest;

  @Test
  public final void getShouldSucceed() throws Throwable {

    // Variable
    Classified classified;
    Long id;

    // Given
    classified = mock(Classified.class);
    id = 8L;
    when(facade.getClassified(id)).thenReturn(classified);

    // When
    Response response = underTest.get(id);

    // Then
    assertTrue(response.getStatus() == HttpServletResponse.SC_OK);
    assertSame(response.getEntity(), classified);
    verify(facade).getClassified(id);
    verifyNoMoreInteractions(facade);
    verifyZeroInteractions(uriInfo, classified);

  }

  @Test
  public final void deleteShouldSucceed() throws Throwable {

    // Variable
    Long id;

    // Given
    id = 8L;

    // When
    Response response = underTest.delete(id);

    // Then
    assertTrue(response.getStatus() == HttpServletResponse.SC_OK);
    assertNull(response.getEntity());
    verify(facade).deleteClassified(id);
    verifyNoMoreInteractions(facade);
    verifyZeroInteractions(uriInfo);

  }

  @Test
  public final void updateShouldSucceed() throws Throwable {

    // Variable
    Long id;
    Classified classified;

    // Given
    id = 6L;
    classified = mock(Classified.class);

    // When
    Response response = underTest.update(id, classified);

    // Then
    assertTrue(response.getStatus() == HttpServletResponse.SC_OK);
    assertNull(response.getEntity());

    verify(classified).setId(id);
    verify(facade).updateClassified(classified);
    verifyNoMoreInteractions(facade);
    verifyZeroInteractions(uriInfo);

  }
}
