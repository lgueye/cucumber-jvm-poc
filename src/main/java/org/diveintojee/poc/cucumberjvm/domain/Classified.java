package org.diveintojee.poc.cucumberjvm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: lgueye Date: 15/02/12 Time: 11:42
 */
@Entity
@Table(name = Classified.TABLE_NAME)
@XmlRootElement
public class Classified {

  public Classified() {
    setStatus(Status.draft);
  }

  public static final String TABLE_NAME = "classified";

  public static final int CONSTRAINT_TITLE_MAX_SIZE = 50;
  public static final int CONSTRAINT_DESCRIPTION_MAX_SIZE = 1000;
  public static final int CONSTRAINT_EMAIL_MAX_SIZE = 50;
  public static final int CONSTRAINT_REFERENCE_MAX_SIZE = 100;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @NotNull(message = "{classified.price.required}")
  private Float price;

  @Column(unique = true)
  @NotEmpty(message = "{classified.email.required}")
  @Size(max = Classified.CONSTRAINT_EMAIL_MAX_SIZE, message = "{classified.email.max.size}")
  @Email(message = "{classified.email.valid.format.required}")
  private String email;

  @Column(unique = true)
  @NotEmpty(message = "{classified.reference.required}")
  @Size(max = Classified.CONSTRAINT_REFERENCE_MAX_SIZE, message = "{classified.reference.max.size}")
  private String reference;

  @NotEmpty(message = "{classified.title.required}")
  @Size(max = Classified.CONSTRAINT_TITLE_MAX_SIZE, message = "{classified.title.max.size}")
  private String title;

  @Size(max = Classified.CONSTRAINT_DESCRIPTION_MAX_SIZE,
        message = "{classified.description.max.size}")
  private String description;

  @Valid
  @NotNull(message = "{classified.location.required}")
  private Location location;

  @NotNull(message = "{classified.status.required}")
  private Status status;

  @Temporal(TemporalType.TIMESTAMP)
  private Date created;
  @Temporal(TemporalType.TIMESTAMP)
  private Date updated;
  @Temporal(TemporalType.TIMESTAMP)
  private Date expires;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  public Date getExpires() {
    return expires;
  }

  public void setExpires(Date expires) {
    this.expires = expires;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public String getReference() {
    return reference;
  }

  public void setReference(String reference) {
    this.reference = reference;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Classified)) {
      return false;
    }

    Classified that = (Classified) o;

    if (!id.equals(that.id)) {
      return false;
    }

    return true;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
        append("id", id).
        append("reference", reference).
        append("status", status).
        append("created", created).
        append("updated", updated).
        append("expires", expires).
        append("title", title).
        append("description", description).
        append("price", price).
        append("email", email).
        append("location", location).
        toString();
  }
}
