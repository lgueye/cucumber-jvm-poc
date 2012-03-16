package org.diveintojee.poc.cucumberjvm.persistence.search;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component(ClassifiedToJsonByteArrayConverter.BEAN_ID)
public class ClassifiedToJsonByteArrayConverter implements Converter<Classified, byte[]> {

  public static final String BEAN_ID = "ClassifiedToJsonByteArrayConverter";

  @Autowired
  private ObjectMapper jsonMapper;

  public byte[] convert(Classified jobPosting) {

    if (jobPosting == null) {
      return null;
    }

    try {
      jsonMapper.getSerializationConfig().without(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS);
      String string = jsonMapper.writeValueAsString(jobPosting);
      //System.out.println("source as string = " + string);
      return string.getBytes("UTF-8");
    } catch (IOException ignored) {
      throw new IllegalStateException(ignored);
    }
  }

}
