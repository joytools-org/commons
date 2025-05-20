/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Instant;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author AndreaR
 */
abstract class TimeValueCalendar extends AbstractNonEmptyTimeValue<Calendar> {

    public static TimeValueCalendar of(final Calendar cal) {
        Objects.requireNonNull(cal, "Calendar");
        return new TimeValueCalendar(){
            @Override
            public long timeMillis() {
                return cal.getTimeInMillis();
            }

            @Override
            public Calendar toCalendar() {
                return (Calendar)cal.clone();
            }

            @Override
            public Calendar get() {
                return (Calendar)cal.clone();
            }
        };
    }
    
}
