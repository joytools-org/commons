/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.control.Option;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author AndreaR
 */
public interface TimeValueUnit {

    /**
     * 
     * @return 
     */
    default long value() {
        return Long.MAX_VALUE;
    }
    
    /**
     * 
     * @return 
     */
    default TimeUnit unit() {
        return TimeUnit.MILLISECONDS;
    }

    /**
     * 
     * @return 
     */
    default long toDays() {
        if (isAbsent()) {
            return Long.MAX_VALUE;
        }
        return unit().toDays(value());
    }

    /**
     * 
     * @return 
     */
    default Duration toDuration() {
        if (isAbsent()) {
            return Duration.ofNanos(Long.MAX_VALUE);
        }
        return Duration.ofNanos(unit().toNanos(value()));
    }

    /**
     * 
     * @return 
     */
    default long toHours() {
        if (isAbsent()) {
            return Long.MAX_VALUE;
        }
        return unit().toHours(value());
    }

    /**
     * 
     * @return 
     */
    default long toMicros() {
        if (isAbsent()) {
            return Long.MAX_VALUE;
        }
        return unit().toMicros(value());
    }

    /**
     * 
     * @return 
     */
    default long toMillis() {
        if (isAbsent()) {
            return Long.MAX_VALUE;
        }
        return unit().toMillis(value());
    }

    /**
     * 
     * @return 
     */
    default long toMinutes() {
        if (isAbsent()) {
            return Long.MAX_VALUE;
        }
        return unit().toMinutes(value());
    }

    /**
     * 
     * @return 
     */
    default long toNanos() {
        if (isAbsent()) {
            return Long.MAX_VALUE;
        }
        return unit().toNanos(value());
    }

    /**
     * 
     * @return 
     */
    default long toSeconds() {
        if (isAbsent()) {
            return Long.MAX_VALUE;
        }
        return unit().toSeconds(value());
    }

    /**
     * 
     * @return 
     */
    default boolean isPresent() {
        final long v = value();
        return !(v < 0 || v == Long.MAX_VALUE);
    }
    
    /**
     * 
     * @return 
     */
    default boolean isAbsent() {
        final long v = value();
        return v < 0 || v == Long.MAX_VALUE;
    }

    /**
     * 
     * @return 
     */
    default Option<Duration> duration() {
        if (isPresent()) {
            return Some(Duration.ofNanos(unit().toNanos(value())));
        }
        return None();
    }
    
    /**
     * 
     * @return 
     */
    default Option<TimeValueUnit> toOption() {
        final long v = value();
        if (v < 0 || v == Long.MAX_VALUE) {
            return None();
        }
        return Some(this);
    }

    /**
     * 
     * @return 
     */
    default Optional<TimeValueUnit> toOptional() {
        final long v = value();
        if (v < 0 || v == Long.MAX_VALUE) {
            return Optional.empty();
        }
        return Optional.of(this);
    }
    
}
