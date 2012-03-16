package org.diveintojee.poc.cucumberjvm.persistence;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;


/**
 * User: lgueye Date: 16/02/12 Time: 15:58
 */
public abstract class AbstractPreEventListener {

  @Autowired
  @Qualifier("validator")
  private Validator validator;

  protected <T> void validate(final T type) {

    if (type == null) {
      throw new IllegalArgumentException("Illegal call to validate : type is required");
    }

    final Set<ConstraintViolation<T>> constraintViolations = this.validator.validate(type);

    if (CollectionUtils.isEmpty(constraintViolations)) {
      return;
    }

    throw new ConstraintViolationException(
        new HashSet<ConstraintViolation<?>>(constraintViolations));

  }

}
