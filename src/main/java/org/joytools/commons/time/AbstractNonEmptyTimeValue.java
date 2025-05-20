/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-public abstract.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;

/**
 *
 * @author AndreaR
 */
abstract class AbstractNonEmptyTimeValue<T> extends AbstractTimeValue<T> {
    
    /**
     * 
     * @return 
     */
    @Override
    public final boolean isEmpty() {
        return false;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Instant toInstant() {
        return Instant.ofEpochMilli(timeMillis());
    }

    /**
     * 
     * @return 
     */
    @Override
    public Calendar toCalendar() {
        final Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timeMillis());
        return cal;
    }

    /**
     * 
     * @return 
     */
    @Override
    public java.util.Date toDate() {
        return new java.util.Date(timeMillis());
    }

    /**
     * 
     * @return 
     */
    @Override
    public java.sql.Date toSQLDate() {
        return new java.sql.Date(timeMillis());
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public java.sql.Time toSQLTime() {
        return new java.sql.Time(timeMillis());
    }

    /**
     * 
     * @return 
     */
    @Override
    public java.sql.Timestamp toSQLTimestamp() {
        return new java.sql.Timestamp(timeMillis());
    }

    /**
     * 
     * @return 
     */
    @Override
    public LocalDateTime toLocalDateTime() {
        return LocalDateTime.ofInstant(toInstant(), ZoneId.systemDefault());
    }

    /**
     * 
     * @param zoneId
     * @return 
     */
    @Override
    public final LocalDateTime toLocalDateTime(final ZoneId zoneId) {
        return toLocalDateTime().atZone(zoneId).toLocalDateTime();
    }

    /**
     * 
     * @param zoneId
     * @return 
     */
    @Override
    public final LocalDateTime toLocalDateTime(final ZoneOffset zoneId) {
        return toLocalDateTime().atOffset(zoneId).toLocalDateTime();
    }

    /*
    public final LocalDate toLocalDate() {
        return LocalDate.from(toInstant());
    }

    public final LocalDate toLocalDate(final ZoneId zoneId) {
        return LocalDate.from(toInstant().atZone(zoneId));
    }

    public final LocalTime toLocalTime() {
        return LocalTime.from(toInstant());
    }

    public final LocalTime toLocalTime(final ZoneId zoneId) {
        return LocalTime.from(toInstant().atZone(zoneId));
    }


    public final LocalDateTime toLocalDateTime(final ZoneId zoneId) {
        return LocalDateTime.from(toInstant().atZone(zoneId));
    }

    public OffsetTime toOffsetTime() {
        return OffsetTime.ofInstant(toInstant(), ZoneId.systemDefault());
    }

    public OffsetTime toOffsetTime(final ZoneId zoneId) {
        return OffsetTime.ofInstant(toInstant(), zoneId);
    }

    public OffsetDateTime toOffsetDateTime() {
        return OffsetDateTime.ofInstant(toInstant(), ZoneId.systemDefault());
    }

    public OffsetDateTime toOffsetDateTime(final ZoneId zoneId) {
        return OffsetDateTime.ofInstant(toInstant(), ZoneId.systemDefault());
    }

    public final ZonedDateTime toZonedDateTime() {
        return ZonedDateTime.from(toInstant());
    }

    public final ZonedDateTime toZonedDateTime(final ZoneId zoneId) {
        return ZonedDateTime.from(toInstant().atZone(zoneId));
    }
    */
    
}
