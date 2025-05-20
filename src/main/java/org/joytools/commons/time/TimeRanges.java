/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Duration;
import java.time.Instant;
import org.apache.commons.lang3.Range;

/**
 *
 * @author AndreaR
 */
public class TimeRanges {
    
    /**
     * 
     * @param <T>
     * @param range
     * @return 
     */
    public static <T> boolean isInstant(final Range<TimeValue<T>> range) {
        final long start = range.getMinimum().timeMillis();
        final long stop = range.getMaximum().timeMillis();
        return start == stop;
    }

    /**
     * 
     * @param <T>
     * @param range
     * @return 
     */
    public static <T> boolean isDuration(final Range<TimeValue<T>> range) {
        return !isInstant(range);
    }

    /**
     * 
     * @param startInstant
     * @param duration
     * @return 
     */
    public static Range<TimeValue<Instant>> nonEmpty(final Instant startInstant, final long duration) {
        final TimeValue<Instant> start = TimeValues.of(startInstant);
        final Instant stopInstant = startInstant.plusMillis(TimeRanges.nonEmpty(duration));
        return Range.of(start, TimeValues.of(stopInstant));        
    }

    /**
     * 
     * @param startInstant
     * @param stopInstant
     * @return 
     */
    public static Range<TimeValue<Instant>> nonEmpty(final Instant startInstant, final Instant stopInstant) {
        return TimeRanges.nonEmpty(startInstant, stopInstant.toEpochMilli() - startInstant.toEpochMilli());
    }
    
    /**
     * 
     * @param startInstant
     * @param duration
     * @return 
     */
    public static Range<TimeValue<Instant>> nonEmpty(final Instant startInstant, final Duration duration) {
        return TimeRanges.nonEmpty(startInstant, duration.toMillis());
    }

    /**
     * 
     * @param <T>
     * @param start
     * @param duration
     * @return 
     */
    public static <T> Range<TimeValue<Instant>> nonEmpty(final TimeValue<T> start, final long duration) {
        return TimeRanges.nonEmpty(start.toInstant(), duration);
    }

    /**
     * 
     * @param <T>
     * @param start
     * @param stop
     * @return 
     */
    public static <T> Range<TimeValue<Instant>> nonEmpty(final TimeValue<T> start, final TimeValue<T> stop) {
        return TimeRanges.nonEmpty(start.toInstant(), stop.timeMillis() - start.timeMillis());
    }

    /**
     * 
     * @param <T>
     * @param start
     * @param duration
     * @return 
     */
    public static <T> Range<TimeValue<Instant>> nonEmpty(final TimeValue<T> start, final Duration duration) {
        return TimeRanges.nonEmpty(start.toInstant(), duration);
    }

    /**
     * 
     * @param millis
     * @return 
     */
    public static long nonEmpty(final long millis) {
        return millis <= 0 ? 1 : millis;
    }

    /**
     * 
     */
    private final static Duration MIN_DURATION = Duration.ofMillis(1);
    
    /**
     * 
     * @param duration
     * @return 
     */
    public static Duration nonEmpty(final Duration duration) {
        final long millis = duration.toMillis();
        return millis <= 0 ? MIN_DURATION : duration;
    }

    /**
     * 
     * @param start
     * @return 
     */
    public static Range<TimeValue<Instant>> nonEmpty(final Instant start) {
        return nonEmpty(start, 1);
    }

    /**
     * 
     * @param <T>
     * @param start
     * @return 
     */
    public static <T> Range<TimeValue<Instant>> nonEmpty(final TimeValue<T> start) {
        return nonEmpty(start, 1);
    }

    /**
     * 
     * @param <T>
     * @param range
     * @return 
     */
    public static <T> long durationMillis(final Range<TimeValue<T>> range) {
        final long start = range.getMinimum().timeMillis();
        final long stop = range.getMaximum().timeMillis();
        return stop - start;
    }

    /**
     * 
     * @param <T>
     * @param range
     * @return 
     */
    public static <T> Duration duration(final Range<TimeValue<T>> range) {
        final long start = range.getMinimum().timeMillis();
        final long stop = range.getMaximum().timeMillis();
        return Duration.ofMillis(stop - start);
    }

}
