/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Instant;

/**
 *
 * @author AndreaR
 */
abstract class TimeValueLong extends AbstractNonEmptyTimeValue<Long> {

    public static TimeValueLong of(final long timeMillis) {
        final Instant instant = Instant.ofEpochMilli(timeMillis);
        return new TimeValueLong(){
            @Override
            public Instant toInstant() {
                return instant;
            }
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public Long get() {
                return timeMillis;
            }
        };
    }
    
}
