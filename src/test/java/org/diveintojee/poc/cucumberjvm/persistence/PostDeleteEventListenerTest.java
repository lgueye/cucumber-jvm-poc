package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.persistence.search.SearchEngine;
import org.hibernate.event.spi.PostDeleteEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 17/02/12 Time: 13:52
 */
@RunWith(MockitoJUnitRunner.class)
public class PostDeleteEventListenerTest {

  @Mock
  private SearchEngine searchEngine;

  @InjectMocks
  private PostDeleteEventListener underTest = new PostDeleteEventListener();

  @Test
  public void onPostDeleteShouldIgnoreIndexationIfEventSourceIsNotAClassified() throws Exception {

    // Variables
    PostDeleteEvent event;
    String entity;

    // Given
    event = mock(PostDeleteEvent.class);
    entity = "anything but a classified";
    when(event.getEntity()).thenReturn(entity);

    // When
    underTest.onPostDelete(event);

    // Then
    verifyZeroInteractions(searchEngine);

  }

}
