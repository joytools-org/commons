/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.time;

import io.vavr.Function1;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Objects;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TimeConverters {
   
    /**
     * Try to find the Time In Milllis of a given generic object
     *
     * @param value
     * @return the found time in millis if found, null otherwise
     */
    public static Long findTimeInMillisOrNullWithParse(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof TimeValue time) {
            return time.isEmpty() ? null : time.timeMillis();
        }
        if (value instanceof TimeMillis time) {
            return time.timeMillis();
        }
        if (value instanceof TemporalAccessor ta) {
            try {
                return Instant.from(ta).toEpochMilli();
            } catch (final DateTimeException de) {
                // Ignore
            }
        }
        if (value instanceof Clock clock) {
            return clock.millis();
        }
        if (value instanceof java.util.Date dt) {
            return dt.getTime();
        }
        if (value instanceof Calendar cal) {
            return cal.getTimeInMillis();
        }
        if (value instanceof CharSequence cs) {
            return parseTimeInMillisOrNull(cs);
        }
        return null;
    }
     
    /**
     * Instant
     * SQL Timestamp
     * SQL Date
     * SQL Time
     * 
     * @param cs
     * @return 
     */
    public static Long parseTimeInMillisOrNull(final CharSequence cs) {
        final String s = Objects.toString(cs, null);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        final TimeValue<?> tv = TimeValues.parseAllOrNull(cs);
        return tv == null || tv.isEmpty() ? null : tv.timeMillis();
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static Long parseTimeInMillisOrFail(final CharSequence cs) {
        final String s = Objects.toString(cs, null);
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        final Long l = parseTimeInMillisOrNull(cs);
        if (l == null) {
            throw new DateTimeException("Could parse to timeInMillis: " + cs);
        }
        return l;
    }

    /**
     * 
     * @param zoneId
     * @return 
     */
    public static ZoneId withSystemDefaultZoneId(final ZoneId zoneId) {
        if (zoneId == null) {
            return ZoneId.systemDefault();
        }
        return zoneId;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static Instant toInstant(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Instant inst) {
            return inst;
        }
        return TimeValues.findOrFailWithParseAll(value).toInstant();
        /*
        if (value instanceof TimeValue) {
            final Object v = TimeValue.class.cast(value).get();
            if (v instanceof Instant) {
                return Instant.class.cast(v);
            }
        }
        final Long timeMillis = findTimeInMillisOrParse(value);
        if (timeMillis == null) {
            throw new DateTimeException("Could not convert to Instant: " + value);
        }
        return Instant.ofEpochMilli(timeMillis);*/
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public static java.util.Date toDate(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.util.Date dt) {
            return dt;
        }
        return TimeValues.findOrFailWithParseAll(value).toDate();
        /*
        if (value instanceof TimeValue) {
            final Object v = TimeValue.class.cast(value).get();
            if (v instanceof java.util.Date) {
                return java.util.Date.class.cast(v);
            }
        }
        final Long timeMillis = findTimeInMillisOrParse(value);
        if (timeMillis == null) {
            throw new DateTimeException("Could not convert to Date: " + value);
        }
        return new java.util.Date(timeMillis); */
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static Calendar toCalendar(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar cal) {
            return cal;
        }
        return TimeValues.findOrFailWithParseAll(value).toCalendar();
        /*
        if (value instanceof TimeValue) {
            final Object v = TimeValue.class.cast(value).get();
            if (v instanceof Calendar) {
                return Calendar.class.cast(v);
            }
        }
        final Long timeMillis = findTimeInMillisOrParse(value);
        if (timeMillis == null) {
            throw new DateTimeException("Could not convert to Calendar: " + value);
        }
        final Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeMillis);
        return c; */
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Calendar> toCalendar() {
        return (Function1<T, Calendar>)TO_CALENDAR;
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, java.util.Date> toDate() {
        return (Function1<T, java.util.Date>)TO_DATE;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Instant> toInstant() {
        return (Function1<T, Instant>)TO_INSTANT;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, java.sql.Date> toSQLDate() {
        return (Function1<T, java.sql.Date>)TO_SQL_DATE;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, java.sql.Time> toSQLTime() {
        return (Function1<T, java.sql.Time>)TO_SQL_TIME;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, java.sql.Timestamp> toSQLTimestamp() {
        return (Function1<T, java.sql.Timestamp>)TO_SQL_TIMESTAMP;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static java.sql.Timestamp toSQLTimestamp(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Timestamp ts) {
            return ts;
        }
        return TimeValues.findOrFailWithParseAll(value).toSQLTimestamp();
        /*
        if (value instanceof TimeValue) {
            final Object v = TimeValue.class.cast(value).get();
            if (v instanceof java.sql.Timestamp) {
                return java.sql.Timestamp.class.cast(v);
            }
        }
        final Long timeMillis = findTimeInMillisOrParse(value);
        if (timeMillis == null) {
            throw new DateTimeException("Could not convert to SQL Timestamp: " + value);
        }
        return new java.sql.Timestamp(timeMillis);*/
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public static java.sql.Time toSQLTime(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Time t) {
            return t;
        }
        return TimeValues.findOrFailWithParseAll(value).toSQLTime();
        /*
        if (value instanceof TimeValue) {
            final Object v = TimeValue.class.cast(value).get();
            if (v instanceof java.sql.Time) {
                return java.sql.Time.class.cast(v);
            }
        }
        final Long timeMillis = findTimeInMillisOrParse(value);
        if (timeMillis == null) {
            throw new DateTimeException("Could not convert to SQL Time: " + value);
        }
        return new java.sql.Time(timeMillis);  */
    }
   
    /**
     * 
     * @param value
     * @return 
     */
    public static java.sql.Date toSQLDate(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof java.sql.Date dt) {
            return dt;
        }
        return TimeValues.findOrFailWithParseAll(value).toSQLDate();
        /*
        if (value instanceof TimeValue) {
            final Object v = TimeValue.class.cast(value).get();
            if (v instanceof java.sql.Date) {
                return java.sql.Date.class.cast(v);
            }
        }
        final Long timeMillis = findTimeInMillisOrParse(value);
        if (timeMillis == null) {
            throw new DateTimeException("Could not convert to SQL Date: " + value);
        }
        return new java.sql.Date(timeMillis); */
    }    
    
    /**
     * 
     * @param value
     * @return 
     */
    @Deprecated
    static boolean old_isEmpty(final Object value) {
        if (value == null) {
            return true;
        }
        if (value instanceof TimeValue) {
            return TimeValue.class.cast(value).isEmpty();
        }
        return false;
    }

    private final static Function1<Object, Calendar> TO_CALENDAR = x -> toCalendar(x);
    
    private final static Function1<Object, java.util.Date> TO_DATE = x -> toDate(x);

    private final static Function1<Object, Instant> TO_INSTANT = x -> toInstant(x);

    private final static Function1<Object, java.sql.Date> TO_SQL_DATE = x -> toSQLDate(x);

    private final static Function1<Object, java.sql.Time> TO_SQL_TIME = x -> toSQLTime(x);

    private final static Function1<Object, java.sql.Timestamp> TO_SQL_TIMESTAMP = x -> toSQLTimestamp(x);
    
}
