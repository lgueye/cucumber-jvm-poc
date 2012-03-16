package org.diveintojee.poc.cucumberjvm.business;

import org.apache.commons.lang3.RandomStringUtils;
import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Location;
import org.diveintojee.poc.cucumberjvm.domain.Status;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;


import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * User: lgueye Date: 15/02/12 Time: 17:15
 */
public class ValidationsOnCreateIT extends BaseValidations {

  private Classified classified;

  private static final int OPERATION = BaseValidations.CREATE_OPERATION;

  @Before
  public final void before() {
    classified = TestUtils.validClassified();
  }

  @Test
  public void shouldNotPersistWithWrongPrice() {

    // Variables
    String errorMessage;
    String propertyPath;
    Float offendingData;

    // Given
    errorMessage = "Le prix est requis";
    propertyPath = "price";
    offendingData = null;
    classified.setPrice(offendingData);

    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

  @Test
  public void shouldNotPersistWithWrongStatus() {

    // Variables
    String errorMessage;
    String propertyPath;
    Status offendingData;

    // Given
    errorMessage = "Le statut est requis";
    propertyPath = "status";
    offendingData = null;
    classified.setStatus(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

  @Test
  public void shouldNotPersistWithWrongTitle() {

    // Variables
    String errorMessage;
    String propertyPath;
    String offendingData;

    // Given
    errorMessage = "Le titre est requis";
    propertyPath = "title";
    offendingData = null;
    classified.setTitle(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "Le titre est requis";
    propertyPath = "title";
    offendingData = EMPTY;
    classified.setTitle(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "Taille maximale du titre : " + Classified.CONSTRAINT_TITLE_MAX_SIZE;
    propertyPath = "title";
    offendingData = classified.getTitle() + "x";
    classified.setTitle(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

  @Test
  public void shouldNotPersistWithWrongEmail() {

    // Variables
    String errorMessage;
    String propertyPath;
    String offendingData;

    // Given
    errorMessage = "L'email est requis";
    propertyPath = "email";
    offendingData = null;
    classified.setEmail(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "L'email est requis";
    propertyPath = "email";
    offendingData = EMPTY;
    classified.setEmail(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "Taille maximale de l'email : " + Classified.CONSTRAINT_EMAIL_MAX_SIZE;
    propertyPath = "email";
    offendingData =
        new StringBuilder()
            .append(RandomStringUtils.random(Classified.CONSTRAINT_EMAIL_MAX_SIZE, TestUtils.CHARS))
            .append("@foo.com").toString();
    classified.setEmail(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "Un format valide (prenom.nom@domain.com) est requis";
    propertyPath = "email";
    offendingData =
        new StringBuilder()
            .append(RandomStringUtils.random(Classified.CONSTRAINT_EMAIL_MAX_SIZE, TestUtils.CHARS))
            .toString();
    classified.setEmail(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

  @Test
  public void shouldNotPersistWithWrongDescription() {

    // Variables
    String errorMessage;
    String propertyPath;
    String offendingData;

    // Given
    errorMessage =
        "Taille maximale de la description : " + Classified.CONSTRAINT_DESCRIPTION_MAX_SIZE;
    propertyPath = "title";
    offendingData = classified.getTitle() + "x";
    classified.setDescription(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

  @Test
  public void shouldNotPersistWithWrongReference() {

    // Variables
    String errorMessage;
    String propertyPath;
    String offendingData;

    // Given
    errorMessage = "La reference est requise";
    propertyPath = "reference";
    offendingData = null;
    classified.setReference(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "La reference est requise";
    propertyPath = "reference";
    offendingData = EMPTY;
    classified.setReference(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "Taille maximale de la reference : " + Classified.CONSTRAINT_REFERENCE_MAX_SIZE;
    propertyPath = "reference";
    offendingData =
        "REF-"
        + RandomStringUtils.random(Classified.CONSTRAINT_REFERENCE_MAX_SIZE - 4, TestUtils.CHARS)
        + "x";
    classified.setReference(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

  @Test
  public void shouldNotPersistWithWrongStreetAddress() {

    // Variables
    String errorMessage;
    String propertyPath;
    String offendingData;

    // Given
    errorMessage = "L'adresse postale est requise";
    propertyPath = "location.streetAddress";
    offendingData = null;
    classified.getLocation().setStreetAddress(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "L'adresse postale est requise";
    propertyPath = "location.streetAddress";
    offendingData = EMPTY;
    classified.getLocation().setStreetAddress(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage =
        "Taille maximale de l'adresse postale : " + Location.CONSTRAINT_STREET_ADDRESS_MAX_SIZE;
    propertyPath = "location.streetAddress";
    offendingData = classified.getLocation().getStreetAddress() + "x";
    classified.getLocation().setStreetAddress(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

  @Test
  public void shouldNotPersistWithWrongPostalCode() {

    // Variables
    String errorMessage;
    String propertyPath;
    String offendingData;

    // Given
    errorMessage = "Le code postal est requis";
    propertyPath = "location.postalCode";
    offendingData = null;
    classified.getLocation().setPostalCode(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "Le code postal est requis";
    propertyPath = "location.postalCode";
    offendingData = EMPTY;
    classified.getLocation().setPostalCode(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "Taille maximale du code postal : " + Location.CONSTRAINT_POSTAL_CODE_MAX_SIZE;
    propertyPath = "location.postalCode";
    offendingData = classified.getLocation().getPostalCode() + "x";
    classified.getLocation().setPostalCode(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

  @Test
  public void shouldNotPersistWithWrongCountryCode() {

    // Variables
    String errorMessage;
    String propertyPath;
    String offendingData;

    // Given
    errorMessage = "Le code pays est requis";
    propertyPath = "location.countryCode";
    offendingData = null;
    classified.getLocation().setCountryCode(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    propertyPath = "location.countryCode";
    offendingData = EMPTY;
    classified.getLocation().setCountryCode(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList("Le code pays est requis",
                                               "Taille exacte du code pays : "
                                               + Location.CONSTRAINT_COUNTRY_CODE_MAX_SIZE)),
             propertyPath, Locale.FRENCH);
    // Given
    errorMessage = "Taille exacte du code pays : " + Location.CONSTRAINT_COUNTRY_CODE_MAX_SIZE;
    propertyPath = "location.countryCode";
    offendingData = classified.getLocation().getCountryCode() + "x";
    classified.getLocation().setCountryCode(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }


  @Test
  public void shouldNotPersistWithWrongCity() {

    // Variables
    String errorMessage;
    String propertyPath;
    String offendingData;

    // Given
    errorMessage = "La ville est requise";
    propertyPath = "location.city";
    offendingData = null;
    classified.getLocation().setCity(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "La ville est requise";
    propertyPath = "location.city";
    offendingData = EMPTY;
    classified.getLocation().setCity(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

    // Given
    errorMessage = "Taille exacte de la ville : " + Location.CONSTRAINT_CITY_MAX_SIZE;
    propertyPath = "location.city";
    offendingData = classified.getLocation().getCity() + "x";
    classified.getLocation().setCity(offendingData);

    // When
    validate(classified, OPERATION,
             new HashSet<String>(Arrays.asList(errorMessage)), propertyPath, Locale.FRENCH);

  }

}
