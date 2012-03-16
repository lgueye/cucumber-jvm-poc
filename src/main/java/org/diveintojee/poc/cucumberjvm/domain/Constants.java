package org.diveintojee.poc.cucumberjvm.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * User: lgueye Date: 16/02/12 Time: 19:05
 */
public class Constants {

  public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
  public static final Set<Locale> SUPPORTED_LOCALES =
      new HashSet(Arrays.asList(Locale.FRENCH, Locale.ENGLISH));
}
