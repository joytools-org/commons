/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import com.google.common.collect.Range;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author AndreaR
 */
public interface RemainingTime extends RemainingMillis {
    
    /**
     * 
     * @return 
     */
    @Override
    public long remainingMillis();
    
    /**
     * 
     * @return 
     */
    default Duration remainingDuration() {
        return Duration.ofMillis(remainingMillis());
    }
    
    /**
     * 
     * @return 
     */
    default TimeValueUnit remainingTimeValueUnit() {
        return TimeValueUnits.of(remainingMillis(), TimeUnit.MILLISECONDS);
    }

    /**
     * 
     * @return 
     */
    public long stopTimeMillis();
    
    /**
     * 
     * @return 
     */
    public Instant stopInstant();
    
    /**
     * 
     * @return 
     */
    default boolean isAfter() {
        return System.currentTimeMillis() >= stopTimeMillis();
    }

    /**
     * 
     * @return 
     */
    default Range<Long> toLongRange() {
        return Range.lessThan(stopTimeMillis());
    }

    /**
     * 
     * @return 
     */
    default Range<Instant> toInstantRange() {
        return Range.lessThan(stopInstant());
    }

}
