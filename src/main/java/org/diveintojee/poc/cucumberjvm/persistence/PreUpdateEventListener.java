package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.hibernate.event.spi.PreUpdateEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


/**
 * User: lgueye Date: 16/02/12 Time: 15:44
 */
@Component
public class PreUpdateEventListener extends AbstractPreEventListener
    implements org.hibernate.event.spi.PreUpdateEventListener {

  /**
   * Return true if the operation should be vetoed
   */
  @Override
  public boolean onPreUpdate(PreUpdateEvent event) {

    Object eventEntity = event.getEntity();

    if (eventEntity instanceof Classified) {

      Classified classified = (Classified) eventEntity;

      Date updated = Calendar.getInstance().getTime();

      classified.setUpdated(updated);

      validate(classified);

    }

    return false;

  }

}
