package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.persistence.search.SearchEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 08/03/12 Time: 13:38
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractPostEventListenerTest {

  @Mock
  private SearchEngine searchEngine;

  @InjectMocks
  AbstractPostEventListener underTest = new PostDeleteEventListener();

  @Test
  public void removeFromIndexShouldSucceed() throws Exception {
    Classified classified;
    Long id;

    // Given
    classified = mock(Classified.class);
    id = 8L;

    // When
    when(classified.getId()).thenReturn(id);
    underTest.removeFromIndex(classified);

    // Then
    verify(searchEngine).removeFromIndex(id);
    verify(classified).getId();
    verifyNoMoreInteractions(searchEngine, classified);
  }

  @Test
  public void indexShouldSucceed() throws Exception {
    Classified classified;
    Long id;

    // Given
    classified = mock(Classified.class);
    id = 8L;

    // When
    when(classified.getId()).thenReturn(id);
    underTest.index(classified);

    // Then
    verify(searchEngine).index(classified, id);
    verify(classified).getId();
    verifyNoMoreInteractions(searchEngine, classified);
  }

}
