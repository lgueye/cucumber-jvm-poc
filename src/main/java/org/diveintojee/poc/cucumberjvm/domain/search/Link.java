package org.diveintojee.poc.cucumberjvm.domain.search;

/**
 * User: lgueye Date: 12/03/12 Time: 16:34
 */
public class Link {

  private String href;
  private Relations rel;

  public String getHref() {
    return href;
  }

  public void setHref(String href) {
    this.href = href;
  }

  public Relations getRel() {
    return rel;
  }

  public void setRel(Relations rel) {
    this.rel = rel;
  }
}
