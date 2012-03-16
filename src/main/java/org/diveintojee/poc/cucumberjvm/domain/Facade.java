package org.diveintojee.poc.cucumberjvm.domain;

import java.util.List;

import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchResult;


/**
 * User: lgueye Date: 15/02/12 Time: 17:22
 */
public interface Facade {

  Long createClassified(Classified classified);

  Classified getClassified(Long id);

  void deleteClassified(Long id);

  void updateClassified(Classified classified);

  List<Classified> getClassifieds();

  void deleteAllClassifieds();

  SearchResult search(SearchQuery searchQuery);

  void clearIndex();

  void indexAllClassifieds();
}
