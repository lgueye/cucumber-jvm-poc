package org.diveintojee.poc.cucumberjvm.domain;

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
    super();
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
  private void setMessage(String message) {
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
  private void setHttpStatus(int httpStatus) {
    this.httpStatus = httpStatus;
  }

}
