package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.persistence.search.SearchEngine;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PreUpdateEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 17/02/12 Time: 13:52
 */
@RunWith(MockitoJUnitRunner.class)
public class PostUpdateEventListenerTest {

  @Mock
  private SearchEngine searchEngine;

  @InjectMocks
  private PostUpdateEventListener underTest = new PostUpdateEventListener();

  @Test
  public void onPostUpdateShouldIgnoreIndexationIfEventSourceIsNotAClassified() throws Exception {

    // Variables
    PostUpdateEvent event;
    String entity;

    // Given
    event = mock(PostUpdateEvent.class);
    entity = "anything but a classified";
    when(event.getEntity()).thenReturn(entity);

    // When
    underTest.onPostUpdate(event);

    // Then
    verifyZeroInteractions(searchEngine);

  }

}
