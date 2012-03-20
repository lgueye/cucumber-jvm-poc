package org.diveintojee.poc.cucumberjvm;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.client.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Location;
import org.diveintojee.poc.cucumberjvm.web.SearchRepresentation;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TestUtils {

  public static final String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
  private static final

  String
      baseEndPoint =
      ResourceBundle.getBundle("stories-context").getString("baseEndPoint");

  private static Client jerseyClient;

  static {
    DefaultClientConfig config = new DefaultClientConfig();
    config.getClasses().add(JacksonJsonProvider.class);
    config.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
    jerseyClient = Client.create(config);
    jerseyClient.addFilter(new LoggingFilter());
  }

  public static Classified validClassified() {
    Classified classified = new Classified();
    classified.setEmail(
        RandomStringUtils.random((Classified.CONSTRAINT_EMAIL_MAX_SIZE / 2) - 1, CHARS)
        + "@foo.com");
    classified.setPrice(new Random(10).nextFloat() * 1000);
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
    assertEquals(1, CollectionUtils.size(e.getConstraintViolations()));
    final ConstraintViolation<?> violation = e.getConstraintViolations().iterator().next();
    assertEquals(errorCode, violation.getMessageTemplate());
    assertEquals(propertyPath, violation.getPropertyPath().toString());
  }

  public static ClientResponse createAdvert(Classified advert, String requestFormat,
                                            String responseFormat, String language) {

    return jerseyClient.resource(baseEndPoint + "/classifieds").acceptLanguage(language)
        .type(requestFormat).accept(responseFormat).post(ClientResponse.class, advert);

  }

  public static ClientResponse loadAdvert(String uri, String responseFormat) {

    return jerseyClient.resource(uri).accept(responseFormat).get(ClientResponse.class);

  }

  public static void cleanRepository() {
    jerseyClient.resource(baseEndPoint + "/debug/delete_all_classifieds").post();
  }


  public static void setupRepository(List<List<String>> table) {
    // Omit headers, start at 1
    for (int i = 1; i < table.size(); i++) {
      List<String> row = table.get(i);
      Classified classified = new Classified();
      classified.setTitle(row.get(0));
      classified.setDescription(row.get(1));
      final String priceAsString = row.get(2);
      if (StringUtils.isNotEmpty(priceAsString)) {
        classified.setPrice(Float.valueOf(priceAsString));
      }
      classified.setReference(row.get(3));
      classified.setEmail(row.get(4));
      classified.setLocation(new Location());
      classified.getLocation().setStreetAddress(row.get(5));
      classified.getLocation().setCity(row.get(6));
      classified.getLocation().setPostalCode(row.get(7));
      classified.getLocation().setCountryCode(row.get(8));
      TestUtils.createAdvert(classified, "application/json", "application/json", "en");
    }
  }

  public static SearchRepresentation findClassifiedsByCriteria(String field, String value,
                                                               boolean exact)
      throws UnsupportedEncodingException {
    String key = "q";
    String val = field + ":" + value + (!(exact) ? "*" : "");
    String queryString = "q=" + URLEncoder.encode(val, "utf-8");
    return jerseyClient.resource(baseEndPoint + "/classifieds?" + queryString)
        .accept("application/xml").type("application/x-www-form-urlencoded").get(
            SearchRepresentation.class);
  }

  public static void diffTable(List<List<String>> table, List<Classified> results)
      throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
    List<String> headers = table.get(0);
    for (int i = 1; i < table.size(); i++) {
      List<String> expectedRow = table.get(i);
      Classified classified = results.get(i - 1);
      Map<String, Object> expectedRowAsMap = new HashMap<String, Object>(results.size());
      Map<String, Object> actualRowAsMap = new HashMap<String, Object>(results.size());
      for (int j = 0; j < headers.size(); j++) {
        expectedRowAsMap.put(headers.get(j), expectedRow.get(j));
        actualRowAsMap.put(headers.get(j), BeanUtils.getProperty(classified, headers.get(j)));
      }
      for (String key : actualRowAsMap.keySet()) {
        Object actualValue = actualRowAsMap.get(key);
        Object expectedValue = expectedRowAsMap.get(key);
        assertEquals(expectedValue, actualValue);
      }
    }
  }
}
