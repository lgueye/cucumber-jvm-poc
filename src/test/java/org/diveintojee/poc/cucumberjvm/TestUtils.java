package org.diveintojee.poc.cucumberjvm;

import com.sun.jersey.api.client.ClientResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Location;

import java.util.Random;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * User: lgueye\n Date: 15/02/12\n Time: 14:07\n
 */
public class TestUtils {

  public static final String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

  public static Classified validClassified() {
    Classified classified = new Classified();
    classified.setEmail(
        RandomStringUtils.random((Classified.CONSTRAINT_EMAIL_MAX_SIZE / 2) - 1, CHARS)
        + "@foo.com");
    classified.setPrice(new Random(1000).nextFloat());
    classified.setReference(
        "REF-" + RandomStringUtils.random(Classified.CONSTRAINT_REFERENCE_MAX_SIZE - 4, CHARS));
    classified.setTitle(RandomStringUtils.random(Classified.CONSTRAINT_TITLE_MAX_SIZE, CHARS));
    classified.setDescription(
        RandomStringUtils.random(Classified.CONSTRAINT_DESCRIPTION_MAX_SIZE, CHARS));
    classified.setLocation(TestUtils.validLocation());
    return classified;
  }

  /**
   * @return
   */
  public static Location validLocation() {
    final Location address = new Location();
    address.setCity(RandomStringUtils.random(Location.CONSTRAINT_CITY_MAX_SIZE, TestUtils.CHARS));
    address.setCountryCode("fr");
    address.setPostalCode(
        RandomStringUtils.random(Location.CONSTRAINT_POSTAL_CODE_MAX_SIZE, TestUtils.CHARS));
    address.setStreetAddress(RandomStringUtils
                                 .random(Location.CONSTRAINT_STREET_ADDRESS_MAX_SIZE,
                                         TestUtils.CHARS));
    return address;
  }

  public static void assertViolationContainsTemplateAndPath(final ConstraintViolationException e,
                                                            final String errorCode,
                                                            final String propertyPath) {
    assertNotNull(e.getConstraintViolations());
//    for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
//      System.out.println("constraintViolation.getMessageTemplate() = " + constraintViolation.getMessageTemplate());
//      System.out.println("constraintViolation.getPropertyPath()() = " + constraintViolation.getPropertyPath());
//
//    }
    assertEquals(1, CollectionUtils.size(e.getConstraintViolations()));

    final ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
    assertEquals(errorCode, violation.getMessageTemplate());
    assertEquals(propertyPath, violation.getPropertyPath().toString());
  }


  public static ClientResponse createAdvert(Classified advert, String requestFormat, String responseFormat, String en, boolean b) {
    return null;  //To change body of created methods use File | Settings | File Templates.
  }

  public static ClientResponse loadAdvert(String format) {
    return null;  //To change body of created methods use File | Settings | File Templates.
  }
}
