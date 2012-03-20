package org.diveintojee.poc.cucumberjvm.persistence;

import org.hibernate.event.spi.PostDeleteEvent;
import org.springframework.stereotype.Component;

@Component(PostDeleteEventListener.BEAN_ID)
public class PostDeleteEventListener extends AbstractPostEventListener
    implements org.hibernate.event.spi.PostDeleteEventListener {

  public static final String BEAN_ID = "PostDeleteEventListener";

  @Override
  public void onPostDelete(PostDeleteEvent event) {

    if (event == null) {
      return;
    }

    removeFromIndex(event.getEntity());

  }

  private static final long serialVersionUID = -4148539712224089605L;
}
