/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.time;

import java.util.Date;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author AndreaR
 */
public class DateTimeUtils extends DateUtils {
    
    /**
     * 
     */
    public DateTimeUtils() {
    }

    /**
     * 
     * @param inst1
     * @param inst2
     * @return 
     */
    public static boolean isSameDay(final Instant inst1, final Instant inst2) {
        if (inst1 == inst2) {
            return true;
        }
        if (inst1 == null || inst2 == null) {
            return false;
        }
        final ZoneId zoneId = ZoneId.systemDefault();
        final LocalDate date1 = inst1.atZone(zoneId).toLocalDate();
        final LocalDate date2 = inst2.atZone(zoneId).toLocalDate();
        return date1.equals(date2);        
    }
    
    /**
     * 
     * @param inst1
     * @param inst2
     * @return 
     */
    public static boolean isSameDay(final TimeValue<?> inst1, final TimeValue<?> inst2) {
        if (inst1 == inst2) {
            return true;
        }
        return isSameDay(inst1.toInstant(), inst2.toInstant());
    }
    
    /**
     * 
     * @param date
     * @return 
     */
    public static Date truncateToDay(final Date date) {
        return CalendarField.DAY_OF_MONTH.truncate(date);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public static java.sql.Date truncateToDay(final java.sql.Date date) {
        return CalendarField.DAY_OF_MONTH.truncate(date);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public static Timestamp truncateToDay(final Timestamp date) {
        return CalendarField.DAY_OF_MONTH.truncate(date);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public static TimeValue<Instant> truncateToDay(final TimeValue<?> date) {
        if (date.isEmpty()) {
            return TimeValues.empty();
        }
        return TimeValues.of(truncateToDay(date.toInstant()));
    }

    /**
     * 
     * @param inst
     * @return 
     */
    public static Instant truncateToDay(final Instant inst) {
        if (inst == null) {
            return null;
        }
        final ZoneId def = ZoneId.systemDefault();
        final ZonedDateTime zdt = inst.atZone(def);
        final LocalDate ld = zdt.toLocalDate();
        final LocalTime lt = LocalTime.of(0, 0);
        return LocalDateTime.of(ld, lt).atZone(def).toInstant();
    }

    /**
     * 
     * @param date
     * @return 
     */
    public static Date truncateToSeconds(final Date date) {
        return CalendarField.SECOND.truncate(date);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public static java.sql.Date truncateToSeconds(final java.sql.Date date) {
        return CalendarField.SECOND.truncate(date);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public static Timestamp truncateToSeconds(final Timestamp date) {
        return CalendarField.SECOND.truncate(date);
    }

    /**
     * 
     * @param inst
     * @return 
     */
    public static Instant truncateToSeconds(final Instant inst) {
        if (inst == null) {
            return null;
        }
        return inst.atZone(ZoneId.systemDefault()).withNano(0).toInstant();
    }

    /**
     * 
     * @param date
     * @return 
     */
    public static TimeValue<Instant> truncateToSeconds(final TimeValue<?> date) {
        if (date.isEmpty()) {
            return TimeValues.empty();
        }
        return TimeValues.of(truncateToSeconds(date.toInstant()));
    }

    /**
     * 
     * @param input
     * @param targetClass
     * @return 
     */
    protected static IllegalArgumentException newIllegalArgumentException(final Object input,
            final Class targetClass) {
        return new IllegalArgumentException(
                "Class " + input.getClass() + " cannot be converted to " + 
                        targetClass == null ?
                        "a generic date or time object" :
                        targetClass.getName());
    }
    
    /**
     * 
     * @param year
     * @param month
     * @param dayOfMonth
     * @param hourOfDay
     * @param minute
     * @param second
     * @return 
     */
    public static boolean maybeDateTime(final int year,
            final int month,
            final int dayOfMonth,
            final int hourOfDay,
            final int minute,
            final int second) {
        return maybeDate(year, month, dayOfMonth) && 
                maybeTime(hourOfDay, minute, second);
    }

    /**
     * 
     */
    private final static int[] MAX_DAYS_PER_MONTH = { 
        31, // January
        29, // February
        31, // March
        30, // April
        31, // May
        30, // June
        31, // July
        31, // August
        30, // September
        31, // October
        30, // November
        31  // December
    };
    
    /**
     * 
     * @param year
     * @param month
     * @param day 
     * @return  
     */
    public static boolean maybeDate(final int year,
            final int month,
            final int day) {
        if (year < 1000 || year >= 10000
                || month < 1 || month > 12
                || day < 1 || day > MAX_DAYS_PER_MONTH[month - 1]) {
            return false;
        }
        try {
            LocalDate.of(year, month, month);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
    
    /**
     * 
     * @param hour
     * @param minute
     * @param second 
     * @return  
     */
    public static boolean maybeTime(final int hour,
            final int minute,
            final int second) {
        if (hour < 0 || hour >= 24 
                || minute < 0 || minute >= 60
                || second < 0 || second >= 60) {
            return false;
        }
        try {
            LocalTime.of(hour, minute, second);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
    
}
