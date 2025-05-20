/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import com.google.common.collect.Range;
import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author AndreaR
 */
public interface ElapsedTime extends ElapsedMillis {
    
    /**
     * 
     * @return 
     */
    default Duration duration() {
        return Duration.ofMillis(elapsedMillis());
    }
    
    /**
     * 
     * @return 
     */
    public long startTimeMillis();
    
    /**
     * 
     * @return 
     */
    public Instant startInstant();
    
    /**
     * 
     * @return 
     */
    default boolean isBefore() {
        return System.currentTimeMillis() < startTimeMillis();
    }
    
    /**
     * 
     * @return 
     */
    default Range<Long> toLongRange() {
        return Range.atLeast(startTimeMillis());
    }

    /**
     * 
     * @return 
     */
    default Range<Instant> toInstantRange() {
        return Range.atLeast(startInstant());
    }
    
}
