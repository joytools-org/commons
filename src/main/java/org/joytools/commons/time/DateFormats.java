/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.time;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 *
 * @author AndreaR
 */
public class DateFormats {
    
    /**
     * yyyy-MM-dd
     * 
     * @return 
     */
    public static DateFormat isoDate() {
        return ISO_DATE_FORMAT;
    }

    /**
     * HH:mm:ss
     * 
     * @return 
     */
    public static DateFormat isoTime() {
        return ISO_TIME_FORMAT;
    }
    
    /**
     * yyyy-MM-dd HH:mm:ss
     * 
     * @return 
     */
    public static DateFormat isoDateTime() {
        return ISO_DATETIME_FORMAT;
    }

    /**
     * yyyyMMdd
     * 
     * @return 
     */
    public static DateFormat isoNumericDate() {
        return ISO_NUMERIC_DATE_FORMAT;
    }

    /**
     * HHmmss
     * 
     * @return 
     */
    public static DateFormat isoNumericTime() {
        return ISO_NUMERIC_TIME_FORMAT;
    }
    
    /**
     * 
     * @return 
     */
    public static DateFormat isoNumericDateTime() {
        return ISO_NUMERIC_DATETIME_FORMAT_STRICT;
    }

    /**
     * yyyyMMddHHmmss
     * 
     * @param lenient
     * @return 
     */
    public static DateFormat isoNumericDateTime(final boolean lenient) {
        return lenient ? ISO_NUMERIC_DATETIME_FORMAT_LENIENT : ISO_NUMERIC_DATETIME_FORMAT_STRICT;
    }
    
    /**
     * 
     * @return 
     */
    public static DateFormat isoNumericDateTimeLenient() {
        return ISO_NUMERIC_DATETIME_FORMAT_LENIENT;
    }

    private final static DateFormat ISO_DATE_FORMAT = strict("yyyy-MM-dd");
    
    private final static DateFormat ISO_TIME_FORMAT = strict("HH:mm:ss");

    private final static DateFormat ISO_DATETIME_FORMAT = strict("yyyy-MM-dd HH:mm:ss");

    private final static DateFormat ISO_NUMERIC_DATE_FORMAT = strict("yyyyMMdd");
    
    private final static DateFormat ISO_NUMERIC_TIME_FORMAT = strict("HHmmss");

    private final static DateFormat ISO_NUMERIC_DATETIME_FORMAT_STRICT = strict("yyyyMMddHHmmss");

    private final static DateFormat ISO_NUMERIC_DATETIME_FORMAT_LENIENT = lenient("yyyyMMddHHmmss");

    /**
     * 
     * @param pattern
     * @return 
     */
    public static DateFormat strict(final String pattern) {
        final SimpleDateFormat fmt = new SimpleDateFormat(pattern);
        fmt.setLenient(false);
        return fmt;
    }

    /**
     * 
     * @param pattern
     * @return 
     */
    public static DateFormat lenient(final String pattern) {
        final SimpleDateFormat fmt = new SimpleDateFormat(pattern);
        fmt.setLenient(true);
        return fmt;
    }

    /**
     * 
     * @param pattern
     * @param locale
     * @return 
     */
    public static DateFormat strict(final String pattern, final Locale locale) {
        final SimpleDateFormat fmt = new SimpleDateFormat(pattern, locale);
        fmt.setLenient(false);
        return fmt;
    }

    /**
     * 
     * @param pattern
     * @param symbols
     * @return 
     */
    public static DateFormat strict(final String pattern, final DateFormatSymbols symbols) {
        final SimpleDateFormat fmt = new SimpleDateFormat(pattern, symbols);
        fmt.setLenient(false);
        return fmt;
    }

}
