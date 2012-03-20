package org.diveintojee.poc.cucumberjvm.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * User: lgueye Date: 16/02/12 Time: 19:04
 */
@XmlRootElement
public class ResponseError {

  private String message;
  private int httpStatus;

  /**
   *
   */
  public ResponseError() {
  }

  /**
   * @param message
   * @param message
   * @param httpStatus
   */
  public ResponseError(String message, int httpStatus) {
    setMessage(message);
    setHttpStatus(httpStatus);
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return this.message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the httpStatus
   */
  public int getHttpStatus() {
    return this.httpStatus;
  }

  /**
   * @param httpStatus the httpStatus to set
   */
  public void setHttpStatus(int httpStatus) {
    this.httpStatus = httpStatus;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).
        append("message", message).
        append("httpStatus", httpStatus).
        toString();
  }
}
