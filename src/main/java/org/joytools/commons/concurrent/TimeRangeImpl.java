/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author AndreaR
 */
class TimeRangeImpl implements TimeRange {

    /**
     * 
     * @param startInstant
     * @param timeoutInstant 
     */
    TimeRangeImpl(final Instant startInstant, final Instant timeoutInstant) {
        this.startInstant = checkNotNull(startInstant, "Start Instant");
        this.endInstant = checkNotNull(timeoutInstant, "Timeout Instant");
        startTimeMillis = startInstant.toEpochMilli();
        endTimeMillis = timeoutInstant.toEpochMilli();
        checkArgument(startTimeMillis <= System.currentTimeMillis(), "Start time could not be set in the future");
        checkArgument(startTimeMillis <= endTimeMillis, "Timeout must be set after start time");
    }

    /**
     * 
     * @param startTimeMillis
     * @param timeoutTimeMillis 
     */
    protected TimeRangeImpl(final long startTimeMillis, final long timeoutTimeMillis) {
        this(Instant.ofEpochMilli(startTimeMillis), Instant.ofEpochMilli(timeoutTimeMillis));
        /*
        checkArgument(startTimeMillis <= System.currentTimeMillis(), "Start time could not be set in the future");
        checkArgument(startTimeMillis <= stopTimeMillis , "TimeRangeImpl must be after start time");
        this.startTimeMillis = startTimeMillis;
        this.stopTimeMillis = stopTimeMillis;
        this.startInstant = Instant.ofEpochMilli(startTimeMillis);
        this.stopInstant = Instant.ofEpochMilli(stopTimeMillis);
        */
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public long startTimeMillis() {
        return startTimeMillis;
    }

    /**
     * 
     * @return 
     */
    @Override
    public long stopTimeMillis() {
        return endTimeMillis;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Instant startInstant() {
        return startInstant;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Instant stopInstant() {
        return endInstant;
    }

    /**
     * 
     * @return 
     */
    @Override
    public long elapsedMillis() {
        return System.currentTimeMillis() - startTimeMillis;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Duration duration() {
        return Duration.ofMillis(elapsedMillis());
    }

    /**
     * 
     * @return 
     */
    @Override
    public long remainingMillis() {
        final long remaining = endTimeMillis - System.currentTimeMillis();
        return remaining < 0 ? 0 : remaining;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Duration remainingDuration() {
        return Duration.ofMillis(remainingMillis());
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isBefore() {
        return System.currentTimeMillis() < startTimeMillis;
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isAfter() {
        return System.currentTimeMillis() >= endTimeMillis;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder()
                .append("{startTimeMillis=").append(startTimeMillis())
                .append(";timeoutTimeMillis=").append(stopTimeMillis())
                .append(";elapsedMillis=").append(elapsedMillis())
                .append(";remainingMillis=").append(remainingMillis())
                .append(";isAfter=").append(isAfter()).append('}');
        return sb.toString();
    }
    
    /**
     * 
     */
    private final long startTimeMillis;

    /**
     * 
     */
    private final long endTimeMillis;
    
    /**
     * 
     */
    private final Instant startInstant;

    /**
     * 
     */
    private final Instant endInstant;

}

