/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author AndreaR
 */
public class Timeouts {
    
    /**
     * 
     * @param timeMillis
     * @param timeoutMillis
     * @return 
     */
    public static RemainingTime from(final long timeMillis, final long timeoutMillis) {
        long timeoutTimeMillis = Long.MAX_VALUE;
        try {
            timeoutTimeMillis = Math.addExact(timeMillis, timeoutMillis);
        } catch (final ArithmeticException ignore) { }
        return new TimeRangeImpl(timeMillis, timeoutTimeMillis);
    }

    /**
     * 
     * @param instant
     * @param timeoutMillis
     * @return 
     */
    public static RemainingTime from(final Instant instant, final long timeoutMillis) {
        checkNotNull(instant, "Start instant");         
        final Instant timeoutInstant = instant.plusMillis(timeoutMillis);
        return new TimeRangeImpl(instant, timeoutInstant);
    }

    /**
     * 
     * @param timeMillis
     * @param timeout
     * @return 
     */
    public static RemainingTime from(final long timeMillis, final TimeValueUnit timeout) {
        return from(timeMillis, timeout.toMillis());
    }
    
    /**
     * 
     * @param instant
     * @param timeout
     * @return 
     */
    public static RemainingTime from(final Instant instant, final TimeValueUnit timeout) {
        return from(instant, timeout.toMillis());
    }

    /**
     * 
     * @param timeMillis
     * @param value
     * @param unit
     * @return 
     */
    public static RemainingTime from(final long timeMillis, final long value, final TimeUnit unit) {
        return from(timeMillis, unit.toMillis(value));
    }

    /**
     * 
     * @param instant
     * @param value
     * @param unit
     * @return 
     */
    public static RemainingTime from(final Instant instant, final long value, final TimeUnit unit) {
        return from(instant, unit.toMillis(value));
    }

    /**
     * 
     * @param timeoutMillis
     * @return 
     */
    public static RemainingTime fromNow(final long timeoutMillis) {
        return from(System.currentTimeMillis(), timeoutMillis);
    }

    /**
     * 
     * @param timeout
     * @return 
     */
    public static RemainingTime fromNow(final Duration timeout) {
        return from(System.currentTimeMillis(), timeout.toMillis());
    }

    /**
     * 
     * @param timeout
     * @return 
     */
    public static RemainingTime fromNow(final TimeValueUnit timeout) {
        return from(System.currentTimeMillis(), timeout);
    }
    
    /**
     * 
     * @param value
     * @param unit
     * @return 
     */
    public static RemainingTime fromNow(final long value, final TimeUnit unit) {
        return from(System.currentTimeMillis(), value, unit);
    }

    /**
     * 
     * @return 
     */
    public static RemainingTime none() {
        return NONE;
    }

    /**
     * 
     * @param timeoutTimeMillis
     * @return 
     */
    public static RemainingTime at(final long timeoutTimeMillis) {
        return new TimeRangeImpl(System.currentTimeMillis(), timeoutTimeMillis);
    }

    /**
     * 
     * @param timeoutInstant
     * @return 
     */
    public static RemainingTime at(final Instant timeoutInstant) {
        return new TimeRangeImpl(Instant.now(), timeoutInstant);
    }

    /**
     * 
     */
    private final static Duration MAX_DURATION = Duration.ofMillis(Long.MAX_VALUE);
    
    /**
     * 
     */
    static class TimeoutNone extends TimeRangeImpl {
        
        TimeoutNone() {
            super(Long.MIN_VALUE, Long.MAX_VALUE);
        }
        
        @Override
        public long elapsedMillis() {
            return Long.MAX_VALUE;
        }
        
        @Override
        public Duration duration() {
            return MAX_DURATION;
        }

        @Override
        public long remainingMillis() {
            return Long.MAX_VALUE;
        }
        
        @Override
        public Duration remainingDuration() {
            return MAX_DURATION;
        }
        
    }
    
    /**
     * 
     */
    private final static TimeoutNone NONE = new TimeoutNone();
    
}
