package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.hibernate.event.spi.PreUpdateEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

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
public class PreUpdateEventListenerTest {

  @Mock
  private Validator validator;

  @InjectMocks
  private PreUpdateEventListener underTest = new PreUpdateEventListener();

  @Test
  public void onPreUpdateShouldIgnoreValidationIfEventSourceIsNotAClassified() throws Exception {

    // Variables
    PreUpdateEvent event;
    String entity;

    // Given
    event = mock(PreUpdateEvent.class);
    entity = "anything but a classified";
    when(event.getEntity()).thenReturn(entity);

    // When
    underTest.onPreUpdate(event);

    // Then
    verifyZeroInteractions(validator);

  }

  @Test
  public void onPreUpdateShouldSetUpdatedDate() throws Exception {
    // Variables
    PreUpdateEvent event;
    Classified entity;
    Set<ConstraintViolation<Classified>> violations;

    // Given
    event = mock(PreUpdateEvent.class);
    entity = mock(Classified.class);
    violations = null;
    when(event.getEntity()).thenReturn(entity);
    when(validator.validate(entity)).thenReturn(violations);

    // When
    underTest.onPreUpdate(event);

    // Then
    verify(entity).setUpdated(any(Date.class));
    verify(validator).validate(entity);
    verifyNoMoreInteractions(validator, entity);
  }

}
