/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TimeValueUnits {
    
    /**
     * 
     * @return 
     */
    public static TimeValueUnit of() {
        return of(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
    }
    
    /**
     * 
     * @param str
     * @return 
     */
    public static TimeValueUnit of(final CharSequence str) {
        final String s = StringUtils.toNullableString(str);
        if (s == null) {
            return of();
        }
        final String ss[] = StringUtils.split(
                StringUtils.replaceRepeatedly(s, "  ", " "), ' ');
        if (ss.length == 2) {
            final long value = Long.parseLong(ss[0]);
            final String unitName1 = ss[1];
            final String unitName2 = unitName1 + "s";
            for (final TimeUnit unit : TimeUnit.values()) {
                if (StringUtils.equalsAnyIgnoreCase(unit.name(), 
                        unitName1, unitName2)) {
                    return of(value, unit);
                }
            }
        }
        throw new IllegalArgumentException("Invalid value for TimeValueUnit: " + str);
    }

    /**
     * 
     * @param duration
     * @return 
     */
    public static TimeValueUnit of(final Duration duration) {
        Objects.requireNonNull(duration, "Duration");
        return new TimeValueUnitImpl(duration.toNanos(), TimeUnit.NANOSECONDS);
    }

    /**
     * 
     * @param value
     * @param unit
     * @return 
     */
    public static TimeValueUnit of(final long value, final TimeUnit unit) {
        Objects.requireNonNull(unit, "TimeUnit");
        return new TimeValueUnitImpl(value, unit);
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValueUnit ofNanos(final long value) {
        return of(value, TimeUnit.NANOSECONDS);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValueUnit ofMillis(final long value) {
        return of(value, TimeUnit.MILLISECONDS);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static TimeValueUnit ofSeconds(final long value) {
        return of(value, TimeUnit.SECONDS);
    }
    
    /**
     * 
     * @return 
     */
    public static TimeValueUnit zero() {
        return ZERO_MILLIS;
    }

    /**
     * 
     * @return 
     */
    public static TimeValueUnit zeroMillis() {
        return ZERO_MILLIS;
    }
    
    /**
     * 
     */
    private final static TimeValueUnit ZERO_MILLIS = of(0, TimeUnit.MILLISECONDS);

}
