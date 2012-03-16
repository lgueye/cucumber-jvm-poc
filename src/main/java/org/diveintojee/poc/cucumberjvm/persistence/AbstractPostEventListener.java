package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.persistence.search.SearchEngine;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * User: lgueye Date: 16/02/12 Time: 15:58
 */
public abstract class AbstractPostEventListener {

  @Autowired
  private SearchEngine searchEngine;

  protected <T> void index(final T eventEntity) {

    if (eventEntity == null) {
      return;
    }

    if (eventEntity instanceof Classified) {

      Classified classified = (Classified) eventEntity;

      Long id = classified.getId();

      searchEngine.index(classified, id);

    }

  }

  protected <T> void removeFromIndex(final T eventEntity) {

    if (eventEntity == null) {
      return;
    }

    if (eventEntity instanceof Classified) {

      Classified classified = (Classified) eventEntity;

      Long id = classified.getId();

      searchEngine.removeFromIndex(id);

    }

  }

}
