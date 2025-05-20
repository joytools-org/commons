/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.time;

import java.util.Calendar;

/**
 *
 * @author AndreaR
 */
public enum CalendarField {
    
    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * era, e.g., AD or BC in the Julian calendar. This is a calendar-specific
     * value; see subclass documentation.
     *
     * @see GregorianCalendar#AD
     * @see GregorianCalendar#BC
     */
    ERA(Calendar.ERA),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * year. This is a calendar-specific value; see subclass documentation.
     */
    YEAR(Calendar.YEAR),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * month. This is a calendar-specific value. The first month of
     * the year in the Gregorian and Julian calendars is
     * <code>JANUARY</code> which is 0; the last depends on the number
     * of months in a year.
     *
     * @see #JANUARY
     * @see #FEBRUARY
     * @see #MARCH
     * @see #APRIL
     * @see #MAY
     * @see #JUNE
     * @see #JULY
     * @see #AUGUST
     * @see #SEPTEMBER
     * @see #OCTOBER
     * @see #NOVEMBER
     * @see #DECEMBER
     * @see #UNDECIMBER
     */
    MONTH(Calendar.MONTH),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * week number within the current year.  The first week of the year, as
     * defined by <code>getFirstDayOfWeek()</code> and
     * <code>getMinimalDaysInFirstWeek()</code>, has value 1.  Subclasses define
     * the value of <code>WEEK_OF_YEAR</code> for days before the first week of
     * the year.
     *
     * @see #getFirstDayOfWeek
     * @see #getMinimalDaysInFirstWeek
     */
    WEEK_OF_YEAR(Calendar.WEEK_OF_YEAR),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * week number within the current month.  The first week of the month, as
     * defined by <code>getFirstDayOfWeek()</code> and
     * <code>getMinimalDaysInFirstWeek()</code>, has value 1.  Subclasses define
     * the value of <code>WEEK_OF_MONTH</code> for days before the first week of
     * the month.
     *
     * @see #getFirstDayOfWeek
     * @see #getMinimalDaysInFirstWeek
     */
    WEEK_OF_MONTH(Calendar.WEEK_OF_MONTH),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * day of the month. This is a synonym for <code>DAY_OF_MONTH</code>.
     * The first day of the month has value 1.
     *
     * @see #DAY_OF_MONTH
     */
    DATE(Calendar.DATE),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * day of the month. This is a synonym for <code>DATE</code>.
     * The first day of the month has value 1.
     *
     * @see #DATE
     */
    DAY_OF_MONTH(Calendar.DAY_OF_MONTH),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the day
     * number within the current year.  The first day of the year has value 1.
     */
    DAY_OF_YEAR(Calendar.DAY_OF_YEAR),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the day
     * of the week.  This field takes values <code>SUNDAY</code>,
     * <code>MONDAY</code>, <code>TUESDAY</code>, <code>WEDNESDAY</code>,
     * <code>THURSDAY</code>, <code>FRIDAY</code>, and <code>SATURDAY</code>.
     *
     * @see #SUNDAY
     * @see #MONDAY
     * @see #TUESDAY
     * @see #WEDNESDAY
     * @see #THURSDAY
     * @see #FRIDAY
     * @see #SATURDAY
     */
    DAY_OF_WEEK(Calendar.DAY_OF_WEEK),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * ordinal number of the day of the week within the current month. Together
     * with the <code>DAY_OF_WEEK</code> field, this uniquely specifies a day
     * within a month.  Unlike <code>WEEK_OF_MONTH</code> and
     * <code>WEEK_OF_YEAR</code>, this field's value does <em>not</em> depend on
     * <code>getFirstDayOfWeek()</code> or
     * <code>getMinimalDaysInFirstWeek()</code>.  <code>DAY_OF_MONTH 1</code>
     * through <code>7</code> always correspond to <code>DAY_OF_WEEK_IN_MONTH
     * 1</code>; <code>8</code> through <code>14</code> correspond to
     * <code>DAY_OF_WEEK_IN_MONTH 2</code>, and so on.
     * <code>DAY_OF_WEEK_IN_MONTH 0</code> indicates the week before
     * <code>DAY_OF_WEEK_IN_MONTH 1</code>.  Negative values count back from the
     * end of the month, so the last Sunday of a month is specified as
     * <code>DAY_OF_WEEK = SUNDAY, DAY_OF_WEEK_IN_MONTH = -1</code>.  Because
     * negative values count backward they will usually be aligned differently
     * within the month than positive values.  For example, if a month has 31
     * days, <code>DAY_OF_WEEK_IN_MONTH -1</code> will overlap
     * <code>DAY_OF_WEEK_IN_MONTH 5</code> and the end of <code>4</code>.
     *
     * @see #DAY_OF_WEEK
     * @see #WEEK_OF_MONTH
     */
    DAY_OF_WEEK_IN_MONTH(Calendar.DAY_OF_WEEK_IN_MONTH),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating
     * whether the <code>HOUR</code> is before or after noon.
     * E.g., at 10:04:15.250 PM the <code>AM_PM</code> is <code>PM</code>.
     *
     * @see #AM
     * @see #PM
     * @see #HOUR
     */
    AM_PM(Calendar.AM_PM),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * hour of the morning or afternoon. <code>HOUR</code> is used for the
     * 12-hour clock (0 - 11). Noon and midnight are represented by 0, not by 12.
     * E.g., at 10:04:15.250 PM the <code>HOUR</code> is 10.
     *
     * @see #AM_PM
     * @see #HOUR_OF_DAY
     */
    HOUR(Calendar.HOUR),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * hour of the day. <code>HOUR_OF_DAY</code> is used for the 24-hour clock.
     * E.g., at 10:04:15.250 PM the <code>HOUR_OF_DAY</code> is 22.
     *
     * @see #HOUR
     */
    HOUR_OF_DAY(Calendar.HOUR_OF_DAY),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * minute within the hour.
     * E.g., at 10:04:15.250 PM the <code>MINUTE</code> is 4.
     */
    MINUTE(Calendar.MINUTE),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * second within the minute.
     * E.g., at 10:04:15.250 PM the <code>SECOND</code> is 15.
     */
    SECOND(Calendar.SECOND),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * millisecond within the second.
     * E.g., at 10:04:15.250 PM the <code>MILLISECOND</code> is 250.
     */
    MILLISECOND(Calendar.MILLISECOND),

    /**
     * Field number for <code>get</code> and <code>set</code>
     * indicating the raw offset from GMT in milliseconds.
     * <p>
     * This field reflects the correct GMT offset value of the time
     * zone of this <code>Calendar</code> if the
     * <code>TimeZone</code> implementation subclass supports
     * historical GMT offset changes.
     */
    ZONE_OFFSET(Calendar.ZONE_OFFSET),

    /**
     * Field number for <code>get</code> and <code>set</code> indicating the
     * daylight saving offset in milliseconds.
     * <p>
     * This field reflects the correct daylight saving offset value of
     * the time zone of this <code>Calendar</code> if the
     * <code>TimeZone</code> implementation subclass supports
     * historical Daylight Saving Time schedule changes.
     */
    DST_OFFSET(Calendar.DST_OFFSET);
    
    /**
     * 
     * @param field 
     */
    CalendarField(final int field) {
        this.field = field;
    }

    /**
     * 
     * @param cal
     * @param amount 
     */
    public void addTo(final Calendar cal, int amount) {
        if (cal == null) {
            return;
        }
        cal.add(field, amount);
    }

    /**
     * 
     * @param cal
     * @param amount 
     * @return  
     */
    public Calendar add(final Calendar cal, int amount) {
        if (cal == null) {
            return null;
        }
        final Calendar c = (Calendar)cal.clone();
        c.add(field, amount);
        return c;
    }

    /**
     * 
     * @param date
     * @param amount
     * @return 
     */
    public java.util.Date add(final java.util.Date date, int amount) {
        if (date == null) {
            return null;
        }
        final Calendar c = DateTimeUtils.toCalendar(date);
        c.add(field, amount);
        return new java.util.Date(c.getTimeInMillis());
    }

    /**
     * 
     * @param date
     * @param amount
     * @return 
     */
    public java.sql.Date add(final java.sql.Date date, int amount) {
        if (date == null) {
            return null;
        }
        final Calendar c = DateTimeUtils.toCalendar(date);
        c.add(field, amount);
        return new java.sql.Date(c.getTimeInMillis());
    }

    /**
     * 
     * @param ts
     * @param amount
     * @return 
     */
    public java.sql.Timestamp add(final java.sql.Timestamp ts, int amount) {
        if (ts == null) {
            return null;
        }
        final Calendar c = DateTimeUtils.toCalendar(ts);
        c.add(field, amount);
        return new java.sql.Timestamp(c.getTimeInMillis());
    }

    /**
     * 
     * @param cal
     * @return 
     */
    public int get(final Calendar cal) {
        return cal.get(field);
    }
    
    /**
     * 
     * @param date
     * @return 
     */
    public int get(final java.util.Date date) {
        return DateTimeUtils.toCalendar(date).get(field);
    }
    
    /**
     * 
     * @param cal
     * @return 
     */
    public long getFragmentInDays(final Calendar cal) {
        return DateTimeUtils.getFragmentInDays(cal, field);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public long getFragmentInDays(final java.util.Date date) {
        return DateTimeUtils.getFragmentInDays(date, field);
    }

    /**
     * 
     * @param cal
     * @return 
     */
    public long getFragmentInHours(final Calendar cal) {
        return DateTimeUtils.getFragmentInHours(cal, field);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public long getFragmentInHours(final java.util.Date date) {
        return DateTimeUtils.getFragmentInHours(date, field);
    }

    /**
     * 
     * @param cal
     * @return 
     */
    public long getFragmentInMilliseconds(final Calendar cal) {
        return DateTimeUtils.getFragmentInMilliseconds(cal, field);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public long getFragmentInMilliseconds(final java.util.Date date) {
        return DateTimeUtils.getFragmentInMilliseconds(date, field);
    }

    /**
     * 
     * @param cal
     * @return 
     */
    public long getFragmentInMinutes(final Calendar cal) {
        return DateTimeUtils.getFragmentInMinutes(cal, field);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public long getFragmentInMinutes(final java.util.Date date) {
        return DateTimeUtils.getFragmentInMinutes(date, field);
    }

    /**
     * 
     * @param cal
     * @return 
     */
    public long getFragmentInSeconds(final Calendar cal) {
        return DateTimeUtils.getFragmentInSeconds(cal, field);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public long getFragmentInSeconds(final java.util.Date date) {
        return DateTimeUtils.getFragmentInSeconds(date, field);
    }

    /**
     * <p>Gets a date ceiling, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the date-time of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 14:00:00.000.  If this was passed with MONTH, it would
     * return 1 Apr 2002 0:00:00.000.</p>
     * 
     * @param cal
     * @return the different ceil date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     * @since 2.5
     */
    public Calendar ceiling(final Calendar cal) {
        if (cal == null) {
            return null;
        }
        return DateTimeUtils.ceiling(cal, field);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.util.Date ceiling(final java.util.Date date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = ceiling(DateTimeUtils.toCalendar(date));
        return new java.util.Date(cal.getTimeInMillis());
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.sql.Date ceiling(final java.sql.Date date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = ceiling(DateTimeUtils.toCalendar(date));
        return new java.sql.Date(cal.getTimeInMillis());
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.sql.Timestamp ceiling(final java.sql.Timestamp date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = ceiling(DateTimeUtils.toCalendar(date));
        return new java.sql.Timestamp(cal.getTimeInMillis());
    }

    /**
     * <p>Rounds a date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the date-time of 28 Mar 2002
     * 13:45:01.231, if this was passed with HOUR, it would return
     * 28 Mar 2002 14:00:00.000. If this was passed with MONTH, it
     * would return 1 April 2002 0:00:00.000.</p>
     * 
     * <p>For a date in a timezone that handles the change to daylight
     * saving time, rounding to Calendar.HOUR_OF_DAY will behave as follows.
     * Suppose daylight saving time begins at 02:00 on March 30. Rounding a 
     * date that crosses this time would produce the following values:
     * </p>
     * <ul>
     * <li>March 30, 2003 01:10 rounds to March 30, 2003 01:00</li>
     * <li>March 30, 2003 01:40 rounds to March 30, 2003 03:00</li>
     * <li>March 30, 2003 02:10 rounds to March 30, 2003 03:00</li>
     * <li>March 30, 2003 02:40 rounds to March 30, 2003 04:00</li>
     * </ul>
     * 
     * @param cal
     * @return the different rounded date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public Calendar round(final Calendar cal) {
        if (cal == null) {
            return null;
        }
        return DateTimeUtils.round(cal, field);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.util.Date round(final java.util.Date date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = round(DateTimeUtils.toCalendar(date));
        return new java.util.Date(cal.getTimeInMillis());
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.sql.Date round(final java.sql.Date date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = round(DateTimeUtils.toCalendar(date));
        return new java.sql.Date(cal.getTimeInMillis());
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.sql.Timestamp round(final java.sql.Timestamp date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = round(DateTimeUtils.toCalendar(date));
        return new java.sql.Timestamp(cal.getTimeInMillis());
    }

    /**
     * <p>Truncates a date, leaving the field specified as the most
     * significant field.</p>
     *
     * <p>For example, if you had the date-time of 28 Mar 2002
     * 13:45:01.231, if you passed with HOUR, it would return 28 Mar
     * 2002 13:00:00.000.  If this was passed with MONTH, it would
     * return 1 Mar 2002 0:00:00.000.</p>
     * 
     * @param cal
     * @return the different truncated date, not null
     * @throws IllegalArgumentException if the date is <code>null</code>
     * @throws ArithmeticException if the year is over 280 million
     */
    public Calendar truncate(final Calendar cal) {
        if (cal == null) {
            return null;
        }
        return DateTimeUtils.truncate(cal, field);
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.util.Date truncate(final java.util.Date date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = truncate(DateTimeUtils.toCalendar(date));
        return new java.util.Date(cal.getTimeInMillis());
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.sql.Date truncate(final java.sql.Date date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = truncate(DateTimeUtils.toCalendar(date));
        return new java.sql.Date(cal.getTimeInMillis());
    }

    /**
     * 
     * @param date
     * @return 
     */
    public java.sql.Timestamp truncate(final java.sql.Timestamp date) {
        if (date == null) {
            return null;
        }
        final Calendar cal = truncate(DateTimeUtils.toCalendar(date));
        return new java.sql.Timestamp(cal.getTimeInMillis());
    }

    /**
     * 
     * @param cal1
     * @param cal2
     * @return 
     */
    public boolean truncatedEquals(final Calendar cal1, final Calendar cal2) {
        return DateTimeUtils.truncatedEquals(cal1, cal2, field);
    }
    
    /**
     * 
     * @param date1
     * @param date2
     * @return 
     */
    public boolean truncatedEquals(final java.util.Date date1, final java.util.Date date2) {
        return DateTimeUtils.truncatedEquals(date1, date2, field);
    }

    /**
     * 
     * @param cal1
     * @param cal2
     * @return 
     */
    public int truncatedCompareTo(final Calendar cal1, final Calendar cal2) {
        return DateTimeUtils.truncatedCompareTo(cal1, cal2, field);
    }
    
    /**
     * 
     * @param date1
     * @param date2
     * @return 
     */
    public int truncatedCompareTo(final java.util.Date date1, final java.util.Date date2) {
        return DateTimeUtils.truncatedCompareTo(date1, date2, field);
    }

    /**
     * 
     * @return 
     */
    public int field() {
        return field;
    }
    
    /**
     * 
     */
    private final int field;
    
}
