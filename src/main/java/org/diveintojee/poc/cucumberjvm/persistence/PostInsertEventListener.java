package org.diveintojee.poc.cucumberjvm.persistence;

import org.hibernate.event.spi.PostInsertEvent;
import org.springframework.stereotype.Component;


@Component(PostInsertEventListener.BEAN_ID)
public class PostInsertEventListener extends AbstractPostEventListener
    implements org.hibernate.event.spi.PostInsertEventListener {

  public static final String BEAN_ID = "PostInsertEventListener";

  @Override
  public void onPostInsert(PostInsertEvent event) {

    if (event == null) {
      return;
    }

    index(event.getEntity());

  }

  private static final long serialVersionUID = -1628412495951772353L;
}
