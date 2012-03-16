package org.diveintojee.poc.cucumberjvm.persistence;

import org.diveintojee.poc.cucumberjvm.domain.Classified;
import org.hibernate.event.spi.PreInsertEvent;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


/**
 * User: lgueye Date: 16/02/12 Time: 15:44
 */
@Component
public class PreInsertEventListener extends AbstractPreEventListener
    implements org.hibernate.event.spi.PreInsertEventListener {

  /**
   * Return true if the operation should be vetoed
   */
  @Override
  public boolean onPreInsert(PreInsertEvent event) {

    Object eventEntity = event.getEntity();

    if (eventEntity instanceof Classified) {

      Classified classified = (Classified) eventEntity;

      Date created = Calendar.getInstance().getTime();

      classified.setCreated(created);

      validate(classified);

    }

    return false;

  }

}
