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
abstract class TimeValueInstant extends AbstractNonEmptyTimeValue<Instant> {

    public static TimeValueInstant of(final Instant instant) {
        final long timeMillis = instant.toEpochMilli();
        return new TimeValueInstant(){
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public Instant toInstant() {
                return instant;
            }

            @Override
            public Instant get() {
                return instant;
            }
        };
    }
    

}
