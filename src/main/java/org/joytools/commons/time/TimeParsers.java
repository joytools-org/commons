/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;
import java.util.function.Supplier;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.accessors.StringAccessors;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.concurrent.AtomicSuppliers;
import org.joytools.commons.lang.StringUtils;
import static org.joytools.commons.time.DateTimeUtils.maybeDate;
import static org.joytools.commons.time.DateTimeUtils.maybeTime;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
class TimeParsers {

    /**
     *
     * @param cs
     * @return
     */
    public static java.sql.Date parseISOSQLDate(final CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return null;
        }
        try {
            return java.sql.Date.valueOf(cs.toString());
        } catch (final DateTimeException dte) {
            throw dte;
        } catch (final Exception cause) {
            throw new DateTimeException(String.format("Could not parse to ISO Date: %s", cs), cause);
        }
    }

    /**
     *
     * @param s
     * @return
     */
    @Deprecated 
    protected static String tryISOInstant(final CharSequence s) {
        final String ts = s.toString().trim().replaceFirst(" ", "T") + "Z";
        return ts;
    }

    /**
     *
     * @param ts
     * @return
     */
    public static Instant parseISOInstant(final CharSequence ts) {
        if (StringUtils.isEmpty(ts)) {
            return null;
        }
        try {
            return Instant.parse(ts);
        } catch (final DateTimeException e) {
            throw e;
        } catch (final Exception cause) {
            throw new DateTimeException("Could not parse to Instant: " + ts, cause);
        }
    }

    @Deprecated
    public static Instant parseISOInstantV1(final CharSequence ts) {
        if (StringUtils.isEmpty(ts)) {
            return null;
        }
        Exception cause = null;
        // First attempt
        try {
            return Instant.parse(ts);
        } catch (final Exception e) {
            if (cause == null) {
                cause = e;
            }
        }
        // Second attempt
        if (false) try {
            // return Instant.parse(tryISOInstant(ts));
            return Instant.ofEpochMilli(java.sql.Timestamp.valueOf(ts.toString()).getTime());
        } catch (final Exception e) {
            if (cause == null) {
                cause = e;
            }
        }
        if (cause != null && cause instanceof DateTimeException dte) {
            throw dte;
        }
        // Nothing to do anymore
        throw new DateTimeException("Could not parse to Instant: " + ts, cause);
    }

    /**
     *
     * @param cs
     * @return
     */
    public static LocalDate parseISOLocalDate(final CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return null;
        }
        return LocalDate.parse(cs);
    }

    /**
     *
     * @param cs
     * @return
     */
    public static java.util.Date parseISODate(final CharSequence cs) {
        final Date dt = parseISOSQLDate(cs);
        if (dt == null) {
            return null;
        }
        return new java.util.Date(dt.getTime());
    }

    /**
     *
     * @param cs
     * @return
     */
    public static Time parseISOSQLTime(final CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return null;
        }
        try {
            return Time.valueOf(cs.toString());
        } catch (final DateTimeException dte) {
            throw dte;
        } catch (final Exception cause) {
            throw new DateTimeException("Could not parse to ISO Time: " + cs, cause);
        }
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static java.sql.Timestamp parseISOSQLTimestamp(final CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return null;
        }
        try {
            return java.sql.Timestamp.valueOf(cs.toString());
        } catch (final DateTimeException dte) {
            throw dte;
        } catch (final Exception cause) {
            throw new DateTimeException("Could not parse to ISO Timestamp: " + cs, cause);
        }
    }

    /**
     *
     * @param cs
     * @return
     */
    public static LocalTime parseISOLocalTime(final CharSequence cs) {
        if (StringUtils.isEmpty(cs)) {
            return null;
        }
        return LocalTime.parse(cs);
    }
    
    /**
     * 
     * @param ts
     * @param lenient
     * @return 
     */
    public static Calendar parseCalendar(final CharSequence ts,
            final boolean lenient) {
        final String s = ts == null ? null : StringUtils.trimToNull(ts.toString());
        if (s == null) {
            return null;
        }
        
        Exception cause = null;
        
        final String[] items = StringUtils.split(s, ' ');
        if (items.length >= 1 && items.length <= 2) {
            final String[] date = StringUtils.split(items[0], '-');
            if (date.length == 3) {
                final String[] timemillis = StringUtils.split(
                        items.length == 2 ? items[1] : "00:00", '.');
                if (timemillis.length >= 1 && timemillis.length <= 2) {
                    final String[] time = StringUtils.split(timemillis[0], ':');
                    if (time.length >= 2 && time.length <= 3) {
                        final String millis = timemillis.length == 2 ? timemillis[1] : "0";
                        try {
                            final int year = Integer.parseInt(date[0]);
                            final int month = Integer.parseInt(date[1]);
                            final int day = Integer.parseInt(date[2]);
                            if (!maybeDate(year, month, day)) {
                                throw new DateTimeException("Not a valid date: " + ts);
                            }
                            final int hour = Integer.parseInt(time[0]);
                            final int minute = Integer.parseInt(time[1]);
                            final int second = time.length < 3 ? 0: Integer.parseInt(time[2]);
                            if (!maybeTime(hour, minute, second)) {
                                throw new DateTimeException("Not a valid date: " + ts);
                            }
                            final int ms = Integer.parseInt(millis);
                            final Calendar c = Calendar.getInstance();
                            c.setLenient(false);
                            c.set(year, month - 1, day, hour, minute, second);
                            c.set(Calendar.MILLISECOND, ms);
                            c.getTimeInMillis();
                            return c;
                        } catch (final Exception e) {
                            cause = e;
                        }
                    }
                }
            }
        }
        if (lenient) {
            return null;
        } else {      
            if (cause != null && cause instanceof DateTimeException dte) {
                throw dte;
            }
            throw new DateTimeException("Not a valid date: " + ts, cause);
        }
    }
        
}
