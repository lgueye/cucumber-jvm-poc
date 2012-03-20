package org.diveintojee.poc.cucumberjvm.web;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.search.Link;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * User: lgueye Date: 13/03/12 Time: 14:28
 */
@XmlRootElement
public class SearchRepresentation {

  private int totalItems;
  private List<Classified> items;
  private Map<String, Link> links;

  public int getTotalItems() {
    return totalItems;
  }

  public void setTotalItems(int totalItems) {
    this.totalItems = totalItems;
  }

  public List<Classified> getItems() {
    return items;
  }

  public void setItems(List<Classified> items) {
    this.items = items;
  }

  public Map<String, Link> getLinks() {
    return links;
  }

  public void setLinks(Map<String, Link> links) {
    this.links = links;
  }
}
