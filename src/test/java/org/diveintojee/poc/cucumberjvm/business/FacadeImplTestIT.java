package org.diveintojee.poc.cucumberjvm.business;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.diveintojee.poc.cucumberjvm.domain.NotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * User: lgueye Date: 15/02/12 Time: 17:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cucumber-jvm-poc.xml"})
public class FacadeImplTestIT {

  @Autowired
  private Facade underTest;

  @Test
  public final void createClassifiedShouldSucceed() {

    // Variables
    Classified classified;
    String title;

    // Given
    classified = TestUtils.validClassified();

    // When
    Long id = underTest.createClassified(classified);

    // Then
    assertNotNull(id);

  }

  @Test(expected = NotFoundException.class)
  public final void deleteClassifiedShouldSucceed() {

    // Variables
    Classified classified;

    // Given
    classified = TestUtils.validClassified();
    Long id = underTest.createClassified(classified);
    // When
    underTest.deleteClassified(id);

    // Then
    underTest.getClassified(id);
  }

  @Test
  public final void updateClassifiedShouldSucceed() {

    // Variables
    Classified classified;
    Classified persistedInstance;

    // Given
    classified = TestUtils.validClassified();
    String initialTitle = classified.getTitle();
    Long id = underTest.createClassified(classified);
    assertNotNull(id);
    persistedInstance = underTest.getClassified(id);
    assertEquals(initialTitle, persistedInstance.getTitle());
    String newTitle = "new title";
    classified.setTitle(newTitle);

    // When
    underTest.updateClassified(classified);
    persistedInstance = underTest.getClassified(id);

    // Then
    assertEquals(newTitle, persistedInstance.getTitle());
  }

  @Test
  public final void getClassifiedsShouldSucceed() {

    // Variables
    Classified classified;

    // Given
    classified = TestUtils.validClassified();
    underTest.createClassified(classified);
    classified = TestUtils.validClassified();
    underTest.createClassified(classified);
    classified = TestUtils.validClassified();
    underTest.createClassified(classified);
    classified = TestUtils.validClassified();
    underTest.createClassified(classified);

    // When
    List<Classified> classifieds = underTest.getClassifieds();

    // Then
    assertTrue(classifieds.size() >= 3);
  }

  @Test
  public void validationShouldBeLocaleAware() {

    // Variables
    Classified classified;
    String errorMessage;
    String propertyPath;
    Set<ConstraintViolation<?>> violations;
    ConstraintViolation<?> violation;

    // Given
    classified = TestUtils.validClassified();
    LocaleContextHolder.setLocale(Locale.ENGLISH);
    errorMessage = "Title max length is " + Classified.CONSTRAINT_TITLE_MAX_SIZE;
    propertyPath = "title";
    classified.setTitle(
        RandomStringUtils.random(Classified.CONSTRAINT_TITLE_MAX_SIZE + 1, TestUtils.CHARS));

    // When
    try {
      underTest.createClassified(classified);
    } catch (ConstraintViolationException e) {
      // Then
      violations = e.getConstraintViolations();
      assertEquals(1, CollectionUtils.size(violations));
      violation = violations.iterator().next();
      assertEquals(errorMessage, violation.getMessage());
      assertEquals(propertyPath, violation.getPropertyPath().toString());

    }


  }

  @Test
  public final void deleteAllClassifiedsShouldSucceed() {

    // Variables
    Classified classified;
    List<Classified> classifieds;

    // Given
    classified = TestUtils.validClassified();
    underTest.createClassified(classified);
    classified = TestUtils.validClassified();
    underTest.createClassified(classified);
    classified = TestUtils.validClassified();
    underTest.createClassified(classified);
    classified = TestUtils.validClassified();
    underTest.createClassified(classified);

    // When
    classifieds = underTest.getClassifieds();

    // Then
    assertTrue(classifieds.size() >= 3);

    // When
    underTest.deleteAllClassifieds();
    classifieds = underTest.getClassifieds();

    // Then
    assertTrue(classifieds.size() == 0);
    
  }


}
