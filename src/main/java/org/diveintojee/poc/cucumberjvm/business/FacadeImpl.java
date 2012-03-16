package org.diveintojee.poc.cucumberjvm.business;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.diveintojee.poc.cucumberjvm.domain.Facade;
import org.diveintojee.poc.cucumberjvm.domain.NotFoundException;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchQuery;
import org.diveintojee.poc.cucumberjvm.domain.search.SearchResult;
import org.diveintojee.poc.cucumberjvm.persistence.search.SearchEngine;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;


/**
 * User: lgueye Date: 15/02/12 Time: 17:24
 */
@Service
public class FacadeImpl implements Facade {

  @PersistenceContext(unitName = "webservice-bootstrap-persistence-unit")
  private EntityManager entityManager;

  @Autowired
  private SearchEngine searchEngine;

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Long createClassified(Classified classified) {

    entityManager.persist(classified);

    return classified.getId();

  }

  @Override
  @Transactional(readOnly = true)
  public Classified getClassified(Long id) {

    Classified classified = entityManager.find(Classified.class, id);

    if (classified == null) {
      throw new NotFoundException();
    }

    return classified;

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteClassified(Long id) {

    Classified classified = getClassified(id);

    entityManager.remove(classified);

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void updateClassified(Classified classified) {

    entityManager.merge(classified);

  }

  @Override
  @Transactional(readOnly = true)
  public List<Classified> getClassifieds() {

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Classified> query = criteriaBuilder.createQuery(Classified.class);

    query.from(Classified.class);

    return entityManager.createQuery(query).getResultList();

  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void deleteAllClassifieds() {

    Query query = entityManager.createQuery("from Classified");

    List<Classified> classifieds = query.getResultList();

    for (Classified classified : classifieds) {
      entityManager.remove(classified);
    }

    LoggerFactory.getLogger(FacadeImpl.class).info("{} Classifieds deleted", classifieds.size());

  }

  @Override
  public SearchResult search(SearchQuery searchQuery) {

    return searchEngine.search(searchQuery);

  }

  @Override
  public void clearIndex() {

    searchEngine.clearIndex();

  }

  @Override
  public void indexAllClassifieds() {

    Query query = entityManager.createQuery("from Classified");

    List<Classified> classifieds = query.getResultList();

    searchEngine.bulkReIndex(classifieds);

  }
}
