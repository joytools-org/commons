/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import com.google.common.collect.Range;
import java.time.Instant;

/**
 *
 * @author AndreaR
 */
public interface TimeRange extends ElapsedTime, RemainingTime {
    
    /**
     * 
     * @return 
     */
    @Override
    default Range<Long> toLongRange() {
        return Range.closedOpen(startTimeMillis(), stopTimeMillis());
    }

    /**
     * 
     * @return 
     */
    @Override
    default Range<Instant> toInstantRange() {
        return Range.closedOpen(startInstant(), stopInstant());
    }
    
}
