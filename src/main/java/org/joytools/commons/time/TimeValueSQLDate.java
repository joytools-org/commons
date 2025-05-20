/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.sql.Date;

/**
 *
 * @author AndreaR
 */
abstract class TimeValueSQLDate extends AbstractNonEmptyTimeValue<Date> {

    public static TimeValueSQLDate of(final Date date) {
        final long timeMillis = date.getTime();
        return new TimeValueSQLDate(){
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public Date toSQLDate() {
                return date;
            }

            @Override
            public Date get() {
                return date;
            }
        };
    }
    
}
