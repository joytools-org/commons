/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-public.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.NoSuchElementException;

/**
 *
 * @author AndreaR
 */
class TimeValueEmpty<T> extends AbstractTimeValue<T> {

    /**
     * 
     */
    static final TimeValueEmpty INSTANCE = new TimeValueEmpty();
       
    /**
     * 
     * @return 
     */
    @Override
    public long timeMillis() {
        throw new NoSuchElementException();
    }

    /**
     * 
     * @return 
     */
    @Override
    public T get() {
        throw new NoSuchElementException();
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Instant toInstant() {
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Calendar toCalendar() {
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public java.util.Date toDate() {
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public java.sql.Date toSQLDate() {
        return null;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public java.sql.Time toSQLTime() {
        return null;
    }

    /**
     * 
     * @return 
     */
    @Override
    public java.sql.Timestamp toSQLTimestamp() {
        return null;
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return null;
    }

    @Override
    public LocalDateTime toLocalDateTime(final ZoneId zoneId) {
        return null;
    }

    @Override
    public LocalDateTime toLocalDateTime(final ZoneOffset offset) {
        return null;
    }

    public LocalDate toLocalDate() {
        return null;
    }

    public LocalDate toLocalDate(final ZoneId zoneId) {
        return null;
    }

    public LocalTime toLocalTime() {
        return null;
    }

    public LocalTime toLocalTime(final ZoneId zoneId) {
        return null;
    }

    public ZonedDateTime toZonedDateTime() {
        return null;
    }

    public ZonedDateTime toZonedDateTime(final ZoneId zoneId) {
        return null;
    }

    public OffsetTime toOffsetTime() {
        return null;
    }

    public OffsetTime toOffsetTime(final ZoneId zoneId) {
        return null;
    }

    public OffsetDateTime toOffsetDateTime() {
        return null;
    }

    public OffsetDateTime toOffsetDateTime(final ZoneId zoneId) {
        return null;
    }

}
