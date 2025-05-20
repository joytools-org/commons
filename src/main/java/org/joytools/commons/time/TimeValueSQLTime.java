/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.sql.Time;

/**
 *
 * @author AndreaR
 */
abstract class TimeValueSQLTime extends AbstractNonEmptyTimeValue<Time> {

    public static TimeValueSQLTime of(final Time time) {
        final long timeMillis = time.getTime();
        return new TimeValueSQLTime(){
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public Time toSQLTime() {
                return time;
            }

            @Override
            public Time get() {
                return time;
            }
        };
    }
    
}
