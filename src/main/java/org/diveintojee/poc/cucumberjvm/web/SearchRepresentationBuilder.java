package org.diveintojee.poc.cucumberjvm.web;

import org.apache.commons.collections.CollectionUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.search.Link;
import org.diveintojee.poc.cucumberjvm.domain.search.OrderBy;
import org.diveintojee.poc.cucumberjvm.domain.search.Relations;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;


/**
 * User: lgueye Date: 13/03/12 Time: 14:28
 */
public class SearchRepresentationBuilder {

  private static Map<String, Link> links(SearchResult searchResult, UriInfo uriInfo) {
    Map<String, Link> links = new HashMap<String, Link>();

    Link link = null;

    link = buildPaginationLink(Relations.first, searchResult, uriInfo);
    if (link != null) {
      links.put(Relations.first.toString(), link);
    }

    link = buildPaginationLink(Relations.previous, searchResult, uriInfo);
    if (link != null) {
      links.put(Relations.previous.toString(), link);
    }

    link = buildPaginationLink(Relations.self, searchResult, uriInfo);
    if (link != null) {
      links.put(Relations.self.toString(), link);
    }

    link = buildPaginationLink(Relations.next, searchResult, uriInfo);
    if (link != null) {
      links.put(Relations.next.toString(), link);
    }

    link = buildPaginationLink(Relations.last, searchResult, uriInfo);
    if (link != null) {
      links.put(Relations.last.toString(), link);
    }

    return links;

  }

  private static Link buildPaginationLink(Relations relation, SearchResult searchResult,
                                          UriInfo uriInfo) {
    int pageIndex = -1;

    switch (relation) {
      case first:
        pageIndex = searchResult.getFirstPageIndex();
        break;
      case previous:
        pageIndex = searchResult.getPreviousPageIndex();
        break;
      case self:
        pageIndex = searchResult.getPageIndex();
        break;
      case next:
        pageIndex = searchResult.getNextPageIndex();
        break;
      case last:
        pageIndex = searchResult.getLastPageIndex();
        break;
    }

    if (pageIndex == -1) {
      return null;
    }

    Link link = new Link();
    link.setRel(relation);
    UriBuilder
        builder =
        uriInfo.getBaseUriBuilder().queryParam("q", searchResult.getQuery().getQuery());
    if (CollectionUtils.isNotEmpty(searchResult.getQuery().getSort())) {
      for (OrderBy item : searchResult.getQuery().getSort()) {
        builder.queryParam("sort", item.toString());
      }
    } else {
      builder.queryParam("sort", OrderBy.DEFAULT.toString());
    }

    builder.queryParam("pageIndex", pageIndex);
    builder.queryParam("itemsPerPage", searchResult.getQuery().getItemsPerPage());
    String href = builder.build().toString();
    link.setHref(href);
    return link;
  }

  public static SearchRepresentation fromResults(SearchResult searchResult, UriInfo uriInfo) {
    int totalItems = searchResult.getTotalItems();
    List<Classified> items = searchResult.getItems();
    Map<String, Link> links = links(searchResult, uriInfo);

    final
    SearchRepresentation
        searchRepresentation =
        new SearchRepresentation();
    searchRepresentation.setItems(items);
    searchRepresentation.setLinks(links);
    searchRepresentation.setTotalItems(totalItems);
    return searchRepresentation;

  }

}
