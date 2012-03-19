package org.diveintojee.poc.cucumberjvm.business;

import org.apache.commons.lang3.RandomStringUtils;
import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.diveintojee.poc.cucumberjvm.domain.Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolationException;


import static org.junit.Assert.fail;

/**
 * User: lgueye Date: 15/02/12 Time: 17:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cucumber-jvm-poc.xml"})
public class ClassifiedValidationTestIT {

  @Autowired
  private Facade facade;

  private Classified underTest;

  @Before
  public final void before() {
    underTest = TestUtils.validClassified();
  }

  /**
   * Validate "create classified use case"
   */
  @Test
  public void shouldValidateClassified() {

    shouldValidateTitleRequiredConstraint();

    shouldValidateTitleSizeConstraint();

    shouldValidateDescriptionSizeConstraint();

    shouldValidateEmailRequiredConstraint();

    shouldValidateEmailSizeConstraint();

    shouldValidateEmailValidFormatConstraint();

    shouldValidatePriceRequiredConstraint();

    shouldValidateLocationRequiredConstraint();

    shouldValidateStreetAddressRequiredConstraint();

    shouldValidateStreetAddressSizeConstraint();

    shouldValidateCityRequiredConstraint();

    shouldValidateCitySizeConstraint();

    shouldValidatePostalCodeRequiredConstraint();

    shouldValidatePostalCodeSizeConstraint();

    shouldValidateCountryCodeRequiredConstraint();

    shouldValidateCountryCodeSizeConstraint();

  }

  /**
   * Given : a valid classified valued with an invalid name<br/> When : one persists the above
   * classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateTitleRequiredConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final String wrongData = null;
    this.underTest.setTitle(wrongData);

    assertExpectedViolation(this.underTest, "{classified.title.required}", "title");

  }

  private void assertExpectedViolation(Classified classified, String errorCode, String errorPath) {
    // When
    try {
      facade.createClassified(classified);

      fail(ConstraintViolationException.class.getName() + " expected");

      // Then
    } catch (final ConstraintViolationException e) {
      TestUtils.assertViolationContainsTemplateAndPath(e, errorCode, errorPath);
    } catch (final Throwable th) {
      th.printStackTrace();
      fail(ConstraintViolationException.class.getName() + " expected, got class="
           + th.getClass().getName() + ", message=" + th.getLocalizedMessage() + ", cause="
           + th.getCause());
    }
  }

  /**
   * Given : a valid classified valued with an invalid name<br/> When : one persists the above
   * classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateTitleSizeConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final
    String
        wrongData =
        RandomStringUtils.random(Classified.CONSTRAINT_TITLE_MAX_SIZE + 1, TestUtils.CHARS);
    this.underTest.setTitle(wrongData);

    assertExpectedViolation(this.underTest, "{classified.title.max.size}", "title");

  }

  /**
   * Given : a valid classified valued with an invalid price<br/> When : one persists the above
   * classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidatePriceRequiredConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final Float wrongData = null;
    this.underTest.setPrice(wrongData);

    assertExpectedViolation(this.underTest, "{classified.price.required}", "price");

  }

  /**
   * Given : a valid classified valued with an invalid location<br/> When : one persists the above
   * classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateLocationRequiredConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final Location wrongData = null;
    this.underTest.setLocation(wrongData);

    assertExpectedViolation(this.underTest, "{classified.location.required}", "location");

  }

  /**
   * Given : a valid classified valued with an invalid street address<br/> When : one persists the
   * above classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateStreetAddressRequiredConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final String wrongData = null;
    this.underTest.getLocation().setStreetAddress(wrongData);

    assertExpectedViolation(this.underTest, "{location.streetAddress.required}",
                            "location.streetAddress");

  }

  /**
   * Given : a valid classified valued with an invalid street address<br/> When : one persists the
   * above classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateStreetAddressSizeConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final
    String
        wrongData =
        RandomStringUtils.random(Location.CONSTRAINT_STREET_ADDRESS_MAX_SIZE + 1, TestUtils.CHARS);
    this.underTest.getLocation().setStreetAddress(wrongData);

    assertExpectedViolation(this.underTest, "{location.streetAddress.max.size}",
                            "location.streetAddress");

  }

  /**
   * Given : a valid classified valued with an invalid city<br/> When : one persists the above
   * classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateCityRequiredConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final String wrongData = null;
    this.underTest.getLocation().setCity(wrongData);

    assertExpectedViolation(this.underTest, "{location.city.required}", "location.city");

  }

  /**
   * Given : a valid classified valued with an invalid city<br/> When : one persists the above
   * classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateCitySizeConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final
    String
        wrongData =
        RandomStringUtils.random(Location.CONSTRAINT_CITY_MAX_SIZE + 1, TestUtils.CHARS);
    this.underTest.getLocation().setCity(wrongData);

    assertExpectedViolation(this.underTest, "{location.city.max.size}", "location.city");

  }

  /**
   * Given : a valid classified valued with an invalid postal code<br/> When : one persists the
   * above classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidatePostalCodeRequiredConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final String wrongData = null;
    this.underTest.getLocation().setPostalCode(wrongData);

    assertExpectedViolation(this.underTest, "{location.postalCode.required}",
                            "location.postalCode");

  }

  /**
   * Given : a valid classified valued with an invalid postal code<br/> When : one persists the
   * above classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidatePostalCodeSizeConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final
    String
        wrongData =
        RandomStringUtils.random(Location.CONSTRAINT_POSTAL_CODE_MAX_SIZE + 1, TestUtils.CHARS);
    this.underTest.getLocation().setPostalCode(wrongData);

    assertExpectedViolation(this.underTest, "{location.postalCode.max.size}",
                            "location.postalCode");

  }

  /**
   * Given : a valid classified valued with an invalid country code<br/> When : one persists the
   * above classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateCountryCodeRequiredConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final String wrongData = null;
    this.underTest.getLocation().setCountryCode(wrongData);

    assertExpectedViolation(this.underTest, "{location.countryCode.required}",
                            "location.countryCode");

  }

  /**
   * Given : a valid classified valued with an invalid country code<br/> When : one persists the
   * above classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateCountryCodeSizeConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final
    String
        wrongData =
        RandomStringUtils.random(Location.CONSTRAINT_COUNTRY_CODE_MAX_SIZE + 1, TestUtils.CHARS);
    this.underTest.getLocation().setCountryCode(wrongData);

    assertExpectedViolation(this.underTest, "{location.countryCode.exact.size}",
                            "location.countryCode");

  }

  /**
   * Given : a valid classified valued with an invalid description<br/> When : one persists the
   * above classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateDescriptionSizeConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final
    String
        wrongData =
        RandomStringUtils.random(Classified.CONSTRAINT_DESCRIPTION_MAX_SIZE + 1, TestUtils.CHARS);
    this.underTest.setDescription(wrongData);

    assertExpectedViolation(this.underTest, "{classified.description.max.size}",
                            "description");

  }

  /**
   * Given : a valid classified valued with an invalid description<br/> When : one persists the
   * above classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateEmailSizeConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final
    String
        wrongData =
        RandomStringUtils.random(Classified.CONSTRAINT_EMAIL_MAX_SIZE + 1, TestUtils.CHARS)
        + "@foo.com";
    this.underTest.setEmail(wrongData);

    assertExpectedViolation(this.underTest, "{classified.email.max.size}",
                            "email");

  }

  /**
   * Given : a valid classified valued with an invalid email<br/> When : one persists the above
   * classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateEmailRequiredConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final String wrongData = null;
    this.underTest.setEmail(wrongData);

    assertExpectedViolation(this.underTest, "{classified.email.required}", "email");

  }

  /**
   * Given : a valid classified valued with an invalid email<br/> When : one persists the above
   * classified<br/> Then : system should throw a {@link ConstraintViolationException}<br/>
   */
  private void shouldValidateEmailValidFormatConstraint() {
    // Given
    this.underTest = TestUtils.validClassified();
    final String wrongData = "foo.bar";
    this.underTest.setEmail(wrongData);

    assertExpectedViolation(this.underTest, "{classified.email.valid.format.required}",
                            "email");

  }
}
