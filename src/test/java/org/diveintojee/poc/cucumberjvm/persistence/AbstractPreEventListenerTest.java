package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;


import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: lgueye Date: 17/02/12 Time: 11:54
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractPreEventListenerTest {

  @Mock
  private Validator validator;

  @InjectMocks
  private AbstractPreEventListener underTest = new PreInsertEventListener();

  @Test(expected = IllegalArgumentException.class)
  public final void validateShouldThrowIllegalArgumentException() {

    // Variables
    Classified classified;

    // Given

    classified = null;
    // When
    underTest.validate(classified);

  }

  @Test(expected = ConstraintViolationException.class)
  public final void validateShouldThrowConstraintViolationException() {

    // Variables
    Classified classified;
    Set<ConstraintViolation<Classified>> violations;

    // Given
    classified = mock(Classified.class);
    violations = new HashSet<ConstraintViolation<Classified>>();
    violations.add(mock(ConstraintViolation.class));
    when(validator.validate(classified)).thenReturn(violations);

    // When
    underTest.validate(classified);

    // Then
    verify(validator).validate(classified);
  }

  @Test
  public final void validateShouldNotThrowConstraintViolationException() {

    // Variables
    Classified classified;
    Set<ConstraintViolation<Classified>> violations;

    // Given
    classified = mock(Classified.class);
    violations = null;
    when(validator.validate(classified)).thenReturn(violations);

    // When
    underTest.validate(classified);

    // Then
    verify(validator).validate(classified);

    // Given
    classified = mock(Classified.class);
    violations = new HashSet<ConstraintViolation<Classified>>();
    when(validator.validate(classified)).thenReturn(violations);

    // When
    underTest.validate(classified);

    // Then
    verify(validator).validate(classified);
  }

}
