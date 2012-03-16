package org.diveintojee.poc.cucumberjvm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

/**
 * User: lgueye\n Date: 15/02/12\n Time: 11:46\n
 */
@Embeddable
public class Location {

  public static final int CONSTRAINT_POSTAL_CODE_MAX_SIZE = 10;
  public static final int CONSTRAINT_COUNTRY_CODE_MAX_SIZE = 2;
  public static final int CONSTRAINT_CITY_MAX_SIZE = 50;
  public static final int CONSTRAINT_STREET_ADDRESS_MAX_SIZE = 50;

  @Column(name = "location_street_address")
  @NotEmpty(message = "{location.streetAddress.required}")
  @Size(max = Location.CONSTRAINT_STREET_ADDRESS_MAX_SIZE,
        message = "{location.streetAddress.max.size}")
  private String streetAddress;

  @Column(name = "location_city")
  @NotEmpty(message = "{location.city.required}")
  @Size(max = Location.CONSTRAINT_CITY_MAX_SIZE, message = "{location.city.max.size}")
  private String city;

  @Column(name = "location_postal_code")
  @NotEmpty(message = "{location.postalCode.required}")
  @Size(max = Location.CONSTRAINT_POSTAL_CODE_MAX_SIZE, message = "{location.postalCode.max.size}")
  private String postalCode;

  @Column(name = "location_country_code")
  @NotEmpty(message = "{location.countryCode.required}")
  @Size(max = Location.CONSTRAINT_COUNTRY_CODE_MAX_SIZE,
        min = Location.CONSTRAINT_COUNTRY_CODE_MAX_SIZE,
        message = "{location.countryCode.exact.size}")
  private String countryCode;

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this).
        append("streetAddress", streetAddress).
        append("city", city).
        append("postalCode", postalCode).
        append("countryCode", countryCode).
        toString();
  }
}
