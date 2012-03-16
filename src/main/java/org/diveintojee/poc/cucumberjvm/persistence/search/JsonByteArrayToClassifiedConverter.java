package org.diveintojee.poc.cucumberjvm.persistence.search;

import org.apache.commons.lang3.ArrayUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component(JsonByteArrayToClassifiedConverter.BEAN_ID)
public class JsonByteArrayToClassifiedConverter implements Converter<byte[], Classified> {

  public static final String BEAN_ID = "JsonByteArrayToClassifiedConverter";

  @Autowired
  private ObjectMapper jsonMapper;

  public Classified convert(byte[] jsonByteArray) {

    if (ArrayUtils.isEmpty(jsonByteArray)) {
      return null;
    }

    try {

      jsonMapper.getDeserializationConfig()
          .without(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES);

      return jsonMapper.readValue(jsonByteArray, Classified.class);

    } catch (IOException ignored) {
      throw new IllegalStateException(ignored);
    }
  }
}
