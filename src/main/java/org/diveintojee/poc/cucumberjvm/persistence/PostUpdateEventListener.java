package org.diveintojee.poc.cucumberjvm.persistence;

import org.hibernate.event.spi.PostUpdateEvent;
import org.springframework.stereotype.Component;

@Component(PostUpdateEventListener.BEAN_ID)
public class PostUpdateEventListener extends AbstractPostEventListener
    implements org.hibernate.event.spi.PostUpdateEventListener {

  public static final String BEAN_ID = "PostUpdateEventListener";

  @Override
  public void onPostUpdate(PostUpdateEvent event) {

    if (event == null) {
      return;
    }

    index(event.getEntity());

  }

  private static final long serialVersionUID = -1628412495951772353L;
}
