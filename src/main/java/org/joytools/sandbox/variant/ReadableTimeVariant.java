/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt get change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java get edit this template
 */
package org.joytools.sandbox.variant;

import org.joytools.commons.time.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface ReadableTimeVariant<T> extends ReadableVariant<T> {
    
    public long getTimeInMillis();
    
    default Instant getInstant() {
        return TimeConverters.toInstant().apply(this);
    }

    default Calendar getCalendar() {
        return TimeConverters.toCalendar().apply(this);
    }

    default java.util.Date getDate() {
        return TimeConverters.toDate().apply(this);
    }

    default java.sql.Date getSQLDate() {
        return TimeConverters.toSQLDate().apply(this);
    }
    
    default java.sql.Time getSQLTime() {
        return TimeConverters.toSQLTime().apply(this);
    }

    default java.sql.Timestamp getSQLTimestamp() {
        return TimeConverters.toSQLTimestamp().apply(this);
    }

    /*
    default LocalDate getLocalDate() {
        return TimeConverters.toLocalDate(this);
    }

    default LocalDate getLocalDate(final ZoneId zoneId) {
        return TimeConverters.toLocalDate(this, zoneId);
    }

    default LocalTime getLocalTime() {
        return TimeConverters.toLocalTime(this);
    }

    default LocalTime getLocalTime(final ZoneId zoneId) {
        return TimeConverters.toLocalTime(this, zoneId);
    }

    default LocalDateTime getLocalDateTime() {
        return TimeConverters.toLocalDateTime(this);
    }

    default LocalDateTime getLocalDateTime(final ZoneId zoneId) {
        return TimeConverters.toLocalDateTime(this, zoneId);
    }

    default ZonedDateTime getZonedDateTime() {
        return TimeConverters.toZonedDateTime(this);
    }

    default ZonedDateTime getZonedDateTime(final ZoneId zoneId) {
        return TimeConverters.toZonedDateTime(this, zoneId);
    } */

}
