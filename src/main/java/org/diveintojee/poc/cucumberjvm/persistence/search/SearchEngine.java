package org.diveintojee.poc.cucumberjvm.persistence.search;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchResult;
import org.elasticsearch.action.search.SearchResponse;

import java.util.List;


public interface SearchEngine {

    String SEARCH_CLAUSES_SEPARATOR = " AND ";

    SearchResult search(SearchQuery query);

    void index(Classified classified, Long id);

    void removeFromIndex(Long id);

    void clearIndex();

    void bulkReIndex(List<Classified> classifieds);

}
