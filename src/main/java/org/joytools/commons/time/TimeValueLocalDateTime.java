/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author AndreaR
 */
abstract class TimeValueLocalDateTime extends AbstractNonEmptyTimeValue<LocalDateTime> {

    public static TimeValueLocalDateTime of(final LocalDateTime value) {
        final Instant instant = value.atZone(ZoneId.systemDefault()).toInstant();
        final long timeMillis = instant.toEpochMilli();
        return new TimeValueLocalDateTime(){
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public Instant toInstant() {
                return instant;
            }

            @Override
            public LocalDateTime toLocalDateTime() {
                return value;
            }

            @Override
            public LocalDateTime get() {
                return value;
            }
        };
    }
    

}
