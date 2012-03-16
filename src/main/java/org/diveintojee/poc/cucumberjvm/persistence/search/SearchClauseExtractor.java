package org.diveintojee.poc.cucumberjvm.persistence.search;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import org.apache.commons.lang3.StringUtils;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchClause;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Component(SearchClauseExtractor.BEAN_ID)
public class SearchClauseExtractor {

  public static final String BEAN_ID = "SearchClauseExtractor";

  public List<SearchClause> fromQueryString(String queryString) {

    if (StringUtils.isEmpty(queryString)) {
      return null;
    }

    List<String> clausesAsString =
        Arrays.asList(StringUtils.trim(queryString).split(SearchEngine.SEARCH_CLAUSES_SEPARATOR));

    Collection<SearchClause> searchClauses =
        Collections2.transform(clausesAsString, new Function<String, SearchClause>() {
          @Override
          public SearchClause apply(String input) {
            return SearchClause.fromString(input);
          }
        });

    return new ArrayList<SearchClause>(
        Collections2.filter(searchClauses, new Predicate<SearchClause>() {
          @Override
          public boolean apply(SearchClause input) {
            return input != null;
          }
        }));

  }

}
