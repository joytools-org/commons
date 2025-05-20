/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Instant;
import java.util.Date;

/**
 *
 * @author AndreaR
 */
abstract class TimeValueDate extends AbstractNonEmptyTimeValue<Date> {

    public static TimeValueDate of(final Date date) {
        final long timeMillis = date.getTime();
        return new TimeValueDate(){
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public Date toDate() {
                return date;
            }

            @Override
            public Date get() {
                return date;
            }
        };
    }
    
}
