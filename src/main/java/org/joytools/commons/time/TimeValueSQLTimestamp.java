/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.sql.Timestamp;

/**
 *
 * @author AndreaR
 */
abstract class TimeValueSQLTimestamp extends AbstractNonEmptyTimeValue<Timestamp> {

    public static TimeValueSQLTimestamp of(final Timestamp ts) {
        final long timeMillis = ts.getTime();
        return new TimeValueSQLTimestamp(){
            @Override
            public long timeMillis() {
                return timeMillis;
            }

            @Override
            public Timestamp toSQLTimestamp() {
                return ts;
            }

            @Override
            public Timestamp get() {
                return ts;
            }
        };
    }
    
}
