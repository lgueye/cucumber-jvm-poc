package org.diveintojee.poc.cucumberjvm.business;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.diveintojee.poc.cucumberjvm.domain.NotFoundException;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.diveintojee.poc.cucumberjvm.persistence.search.JsonByteArrayToClassifiedConverter;
import org.diveintojee.poc.cucumberjvm.persistence.search.SearchEngine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 15/02/12 Time: 17:15
 */
@RunWith(MockitoJUnitRunner.class)
public class FacadeImplTest {

  @Mock
  private EntityManager entityManager;

  @Mock
  private SearchEngine searchEngine;

  @Mock
  private JsonByteArrayToClassifiedConverter jsonByteArrayToClassifiedConverter;

  @InjectMocks
  private Facade underTest = new FacadeImpl();

  @Test
  public final void createClassifiedShouldSucceed() {

    Classified classified;

    // Given
    classified = mock(Classified.class);

    // When
    underTest.createClassified(classified);

    // Then
    verify(entityManager).persist(classified);
    verify(classified).getId();
    verifyNoMoreInteractions(entityManager, classified);

  }

  @Test(expected = NotFoundException.class)
  public final void getClassifiedShouldThrowNotFoundException() {

    Long id = 7L;
    Classified classified;

    // Given
    classified = null;
    when(entityManager.find(Classified.class, id)).thenReturn(classified);

    // When
    underTest.getClassified(id);

    // Then
    verify(entityManager).find(Classified.class, id);
    verifyNoMoreInteractions(entityManager);

  }

  @Test
  public final void getClassifiedShouldSucceed() {

    Long id = 7L;
    Classified classified;

    // Given
    classified = mock(Classified.class);
    when(entityManager.find(Classified.class, id)).thenReturn(classified);

    // When
    Classified result = underTest.getClassified(id);

    // Then
    assertSame(classified, result);
    verify(entityManager).find(Classified.class, id);
    verifyNoMoreInteractions(entityManager);
    verifyZeroInteractions(classified);

  }

  @Test(expected = NotFoundException.class)
  public final void deleteClassifiedShouldThrowNotFoundException() {

    Long id = 7L;
    Classified classified;

    // Given
    classified = null;
    when(entityManager.find(Classified.class, id)).thenReturn(classified);

    // When
    underTest.deleteClassified(id);

    // Then
    verify(entityManager).find(Classified.class, id);
    verifyNoMoreInteractions(entityManager);

  }

  @Test
  public final void deleteClassifiedShouldSucceed() {

    Long id = 7L;
    Classified classified;

    // Given
    classified = mock(Classified.class);
    when(entityManager.find(Classified.class, id)).thenReturn(classified);

    // When
    underTest.deleteClassified(id);

    // Then
    verify(entityManager).find(Classified.class, id);
    verify(entityManager).remove(classified);
    verifyNoMoreInteractions(entityManager);
    verifyZeroInteractions(classified);

  }

  @Test
  public final void updateClassifiedShouldSucceed() {

    Classified classified;

    // Given
    classified = mock(Classified.class);

    // When
    underTest.updateClassified(classified);

    // Then
    verify(entityManager).merge(classified);
    verifyNoMoreInteractions(entityManager);
    verifyZeroInteractions(classified);

  }

  @Test
  public final void deleteAllClassifiedsShouldSucceed() {

    Query query;
    List classifieds;

    // Given
    query = mock(Query.class);
    classifieds = Arrays.asList(mock(Classified.class), mock(Classified.class));
    when(entityManager.createQuery("from Classified")).thenReturn(query);
    when(query.getResultList()).thenReturn(classifieds);

    // When
    underTest.deleteAllClassifieds();

    // Then
    verify(entityManager).createQuery("from Classified");
    verify(query).getResultList();
    verify(entityManager, times(classifieds.size())).remove(any(Classified.class));
    verifyNoMoreInteractions(entityManager, query);
  }

  @Test
  public final void searchShouldSucceed() throws UnsupportedEncodingException {

    // Variables
    SearchQuery searchQuery;

    // Given
    searchQuery = mock(SearchQuery.class);

    // When
    underTest.search(searchQuery);

    verify(searchEngine).search(searchQuery);

  }

  @Test
  public final void clearIndexShouldSucceed() throws UnsupportedEncodingException {

    // When
    underTest.clearIndex();

    verify(searchEngine).clearIndex();

  }

  @Test
  public final void indexAllClassifiedsShouldSucceed() throws UnsupportedEncodingException {

    Query query;
    List classifieds;

    // Given
    query = mock(Query.class);
    classifieds = Arrays.asList(mock(Classified.class), mock(Classified.class));
    when(entityManager.createQuery("from Classified")).thenReturn(query);
    when(query.getResultList()).thenReturn(classifieds);

    // When
    underTest.indexAllClassifieds();

    // Then
    verify(entityManager).createQuery("from Classified");
    verify(query).getResultList();
    verify(searchEngine).bulkReIndex(classifieds);
    verifyNoMoreInteractions(entityManager, query, searchEngine);

  }

}
