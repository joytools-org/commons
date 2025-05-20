/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import java.time.Duration;
import java.util.function.LongConsumer;

/**
 *
 * @author AndreaR
 */
public class ElapsedTimeAccumulator implements AutoCloseable, ElapsedMillis, ElapsedNanos {

    private final static String ALREADY_STARTED = "Already started";
    
    private final static String NOT_STARTED = "Not started";
    
    /**
     * 
     * @param started
     * @param accumulator 
     */
    protected ElapsedTimeAccumulator(final boolean started, final LongConsumer accumulator) {
        this.started = started;
        this.accumulator = accumulator;
        if (started) {
            initialNanoTime = System.nanoTime();
        }
    }
    
    /**
     * 
     * @return 
     */
    public static ElapsedTimeAccumulator create() {
        return new ElapsedTimeAccumulator(false, null);
    }

    /**
     * 
     * @param accumulator
     * @return 
     */
    public static ElapsedTimeAccumulator create(final LongConsumer accumulator) {
        return new ElapsedTimeAccumulator(false, accumulator);
    }

    /**
     * 
     * @return 
     */
    public static ElapsedTimeAccumulator createStarted() {
        return new ElapsedTimeAccumulator(true, null);
    }
            
    /**
     * 
     * @param accumulator
     * @return 
     */
    public static ElapsedTimeAccumulator createStarted(final LongConsumer accumulator) {
        return new ElapsedTimeAccumulator(true, accumulator);
    }
    
    /**
     * 
     * @return 
     */
    public Duration duration() {
        return Duration.ofNanos(elapsedNanos());
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public long elapsedNanos() {
        if (!started) {
            throw new IllegalStateException(NOT_STARTED);
        }
        return System.nanoTime() - initialNanoTime;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public long elapsedMillis() {
        return elapsedNanos() / 1_000_000;
    }

    /**
     * 
     * @return 
     */
    public long totalNanos() {
        return totalNanos;
    }

    /**
     * 
     * @return 
     */
    public long totalMillis() {
        return totalNanos / 1_000_000;
    }

    /**
     * 
     * @return 
     */
    public boolean isStarted() {
        return started;
    }
    
    /**
     * 
     * @return 
     */
    public ElapsedTimeAccumulator start() {
        if (started) {
            throw new IllegalStateException(ALREADY_STARTED);
        }
        initialNanoTime = System.nanoTime();
        started = true;
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public ElapsedTimeAccumulator stop() {
        if (!started) {
            throw new IllegalStateException(NOT_STARTED);
        }
        final long elapsedNanos = System.nanoTime() - initialNanoTime;
        started = false;
        totalNanos += elapsedNanos;
        if (accumulator != null) {
            accumulator.accept(elapsedNanos);
        }
        return this;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return String.valueOf(totalNanos);
    }
    
    /**
     * 
     */
    @Override
    public void close() {
        if (started) {
            stop();
        }
    }
    
    private long initialNanoTime = 0;

    private long totalNanos = 0;
    
    private final LongConsumer accumulator;
    
    private boolean started = false;
            
}
