package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.TestUtils;
import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * User: lgueye\n Date: 15/02/12\n Time: 13:59\n
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:cucumber-jvm-poc.xml"})
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ClassifiedPersistenceTestIT {

  @PersistenceContext(unitName = "cucumber-jvm-poc-persistence-unit")
  private EntityManager entityManager;

  @Test
  public void createClassifiedShouldSucceed() {

    // Variables
    Classified classified;
    // Given
    classified = TestUtils.validClassified();
    assertNull(classified.getId());

    // When
    entityManager.persist(classified);
    entityManager.flush();

    //Then
    assertNotNull(classified.getId());

  }

  @Test
  public void updateClassifiedShouldSucceed() {

    // Variables
    Classified classified;
    Classified persistedInstance;
    String newTitle;

    // Given
    classified = TestUtils.validClassified();
    entityManager.persist(classified);
    persistedInstance = entityManager.find(Classified.class, classified.getId());

    // When
    newTitle = "Test new valid title";

    persistedInstance.setTitle(newTitle);
    entityManager.persist(persistedInstance);
    entityManager.flush();

    //Then
    assertEquals(newTitle, classified.getTitle());
  }

  @Test
  public void deleteClassifiedShouldSucceed() {

    // Variables
    Classified classified;
    Long id;

    // Given
    classified = TestUtils.validClassified();
    entityManager.persist(classified);
    id = classified.getId();

    // When
    entityManager.remove(classified);
    entityManager.flush();

    //Then
    assertNull(entityManager.find(Classified.class, id));

  }
}
