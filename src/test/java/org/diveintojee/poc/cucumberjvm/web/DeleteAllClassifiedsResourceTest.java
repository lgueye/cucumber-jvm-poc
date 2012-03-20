package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * User: lgueye Date: 17/02/12 Time: 16:24
 */
@RunWith(MockitoJUnitRunner.class)
public class DeleteAllClassifiedsResourceTest {

  @Mock
  private Facade facade;

  @InjectMocks
  private DeleteAllClassifiedsResource underTest;

  @Test
  public final void deleteShouldSucceed() throws Throwable {

    // When
    Response response = underTest.execute();

    // Then
    assertTrue(response.getStatus() == HttpServletResponse.SC_OK);
    assertNull(response.getEntity());
    verify(facade).deleteAllClassifieds();
    verifyNoMoreInteractions(facade);

  }

}
