package org.diveintojee.poc.cucumberjvm.persistence;

import org.hibernate.ejb.HibernateEntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PreInsertEventListener;
import org.hibernate.event.spi.PreUpdateEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;

/**
 * User: lgueye Date: 16/02/12 Time: 15:39
 */
@Component
public class HibernateListenersConfigurer {

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Autowired
  private PreInsertEventListener preInsertEventListener;

  @Autowired
  private PreUpdateEventListener preUpdateEventListener;

  @Autowired
  private PostInsertEventListener postInsertEventListener;

  @Autowired
  private PostUpdateEventListener postUpdateEventListener;

  @Autowired
  private PostDeleteEventListener postDeleteEventListener;

  @PostConstruct
  public void registerListeners() {

    HibernateEntityManagerFactory
        hibernateEntityManagerFactory =
        (HibernateEntityManagerFactory) this.entityManagerFactory;

    SessionFactoryImpl
        sessionFactoryImpl =
        (SessionFactoryImpl) hibernateEntityManagerFactory.getSessionFactory();

    EventListenerRegistry
        registry =
        sessionFactoryImpl.getServiceRegistry().getService(EventListenerRegistry.class);

    registry.getEventListenerGroup(EventType.PRE_INSERT).prependListener(preInsertEventListener);
    registry.getEventListenerGroup(EventType.PRE_UPDATE).prependListener(preUpdateEventListener);
    
    registry.getEventListenerGroup(EventType.POST_INSERT).prependListener(postInsertEventListener);
    registry.getEventListenerGroup(EventType.POST_UPDATE).prependListener(postUpdateEventListener);
    registry.getEventListenerGroup(EventType.POST_DELETE).prependListener(postDeleteEventListener);
    
  }

}
