package org.diveintojee.poc.cucumberjvm.business;

import org.apache.commons.collections.CollectionUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
public abstract class BaseValidations {

  @Autowired
  private Facade underTest;
  public static final int CREATE_OPERATION = 1;
  public static final int UPDATE_OPERATION = 2;


  protected void validate(Classified offendingClassified, int operation,
                          Set<String> expectedMessages,
                          String expectedPropertyPath, Locale locale) {

    LocaleContextHolder.setLocale(locale);

    try {
      switch (operation) {
        case CREATE_OPERATION:
          underTest.createClassified(offendingClassified);
          break;
        case UPDATE_OPERATION:
          underTest.updateClassified(offendingClassified);
          break;
        default:
          throw new IllegalArgumentException("Operation " + operation + " not supported");
      }
    } catch (ConstraintViolationException e) {

      // Then
      Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

      assertEquals(CollectionUtils.size(expectedMessages), CollectionUtils.size(violations));

      Set<String> messages = new HashSet<String>(violations.size());

      for (ConstraintViolation<?> constraintViolation : violations) {

        assertEquals(expectedPropertyPath, constraintViolation.getPropertyPath().toString());

        messages.add(constraintViolation.getMessage());

      }

      assertEquals(expectedMessages, messages);

    }


  }

}
