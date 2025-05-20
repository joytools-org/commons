/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.profile;

import java.time.Duration;

/**
 *
 * @author AndreaR
 */
public class TimeAccumulator implements AutoCloseable {
    
    /**
     * 
     */
    protected TimeAccumulator() {
    }
    
    /**
     * 
     * @param started 
     */ /*
    protected TimeAccumulator(final boolean started) {
        if (started) {
            start();
        }
    } */

    /**
     * 
     * @param startTimeNano 
     */ /*
    public TimeAccumulator(final long startTimeNano) {
        if (startTimeNano < 0) {
            throw new IllegalArgumentException("Starting time can not be negative");
        }
        this.startTimeNano = startTimeNano;
    } */

    /**
     * 
     * @return 
     */
    public static TimeAccumulator create() {
        return new TimeAccumulator();
    }
    
    /**
     * 
     * @return 
     */
    public static TimeAccumulator createStarted() {
        final TimeAccumulator acc = create();
        acc.start();
        return acc;
    }

    /**
     * 
     * @return 
     */ /*
    public static TimeAccumulator getStarted() {
        return new TimeAccumulator(true);
    } */
    
    /**
     * 
     * @return 
     */ /*
    public static TimeAccumulator get() {
        return new TimeAccumulator();
    } */

    /**
     * 
     * @return 
     */
    public boolean start() {
        if (startTimeNano < 0) {
            count++;
            startTimeNano = System.nanoTime();
            return true;
        }
        return false;
    }
    
    /**
     * 
     * @return 
     */
    public boolean stop() {
        if (startTimeNano < 0) {
            return false;
        }
        accumulatedTimeNano += System.nanoTime() - startTimeNano;
        startTimeNano = -1;
        return true;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isStarted() {
        return startTimeNano >= 0;
    }

    /**
     * 
     * @return 
     */
    public boolean isRunning() {
        return startTimeNano >= 0;
    }

    /**
     * 
     * @return 
     */
    public boolean isStopped() {
        return startTimeNano < 0;
    }
    
    /**
     * 
     * @return 
     */
    public Duration getDuration() {
        return Duration.ofNanos(accumulatedTimeNano);
    }
    
    /**
     * 
     * @return 
     */
    public long getDurationNanos() {
        return accumulatedTimeNano;
    }
    
    /**
     * 
     * @return 
     */
    public long getDurationMillis() {
        return accumulatedTimeNano / 10000000;
    }
    
    /**
     * 
     * @return 
     */
    public long getCount() {
        return count;
    }
    
    /**
     * 
     * @param nanoTime 
     */
    public void add(final long nanoTime) {
        if (nanoTime < 0) {
            throw new IllegalArgumentException("Added time must be greater or equal to 0");
        }
        accumulatedTimeNano += nanoTime;
    }
            
    /**
     * 
     * @param ta 
     */
    public void add(final TimeAccumulator ta) {
        if (ta != null) {
            accumulatedTimeNano += ta.accumulatedTimeNano;
        }
    }

    /**
     * 
     */
    private long accumulatedTimeNano = 0;
    
    /**
     * 
     */
    private long startTimeNano = -1;
    
    /**
     * 
     */
    private long count = 0;

    /**
     * 
     */
    @Override
    public void close() {
        stop();
    }
    
}
