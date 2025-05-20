/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Instant;
import java.time.temporal.TemporalAccessor;

/**
 *
 * @author AndreaR
 */
abstract class TimeValueTemporalAccessor<T extends TemporalAccessor> extends AbstractNonEmptyTimeValue<T> {

    public static <T extends TemporalAccessor> TimeValueTemporalAccessor<T> of(final T ta) {
        final Instant instant = Instant.from(ta);
        final long timeMillis = instant.toEpochMilli();
        return new TimeValueTemporalAccessor(){
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public Instant toInstant() {
                return instant;
            }
            
            @Override
            public T get() {
                return ta;
            }
        };
    }

}
