package org.diveintojee.poc.cucumberjvm.persistence;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


import static org.junit.Assert.assertEquals;

/**
 * User: lgueye Date: 15/02/12 Time: 17:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cucumber-jvm-poc.xml"})
public class PreUpdateEventListenerTestIT {

  @Autowired
  private PreUpdateEventListener underTest;

  private Classified classified;

  @Before
  public final void before() {
    classified = TestUtils.validClassified();
  }

  @Test
  public void validationShouldBeLocaleAware() {

    // Variables
    String errorMessage;
    String propertyPath;
    Set<ConstraintViolation<Classified>> violations;
    ConstraintViolation<?> violation;

    // Given
    LocaleContextHolder.setLocale(Locale.ENGLISH);
    errorMessage = "Title max length is " + Classified.CONSTRAINT_TITLE_MAX_SIZE;
    propertyPath = "title";
    classified.setTitle(
        RandomStringUtils.random(Classified.CONSTRAINT_TITLE_MAX_SIZE + 1, TestUtils.CHARS));

    // When
    try {
      underTest.validate(classified);
    } catch (ConstraintViolationException e) {
      // Then
      violations = new HashSet<ConstraintViolation<Classified>>
          ((Collection) e.getConstraintViolations());
      assertEquals(1, CollectionUtils.size(violations));
      violation = violations.iterator().next();
      assertEquals(errorMessage, violation.getMessage());
      assertEquals(propertyPath, violation.getPropertyPath().toString());

    }


  }

}
