/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.time;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public final class StopWatch {
    
    public StopWatch() {
        sw = new org.apache.commons.lang3.time.StopWatch();
    }
    
    public StopWatch(final CharSequence message) {
        sw = new org.apache.commons.lang3.time.StopWatch(StringUtils.toString(message));
    }

    public static StopWatch create() {
        return new StopWatch();
    }
    
    public static StopWatch create(final CharSequence message) {
        return new StopWatch(message);
    }

    public static StopWatch createStarted() {
        return create().start();
    }
    
    public static StopWatch createStarted(final CharSequence message) {
        return create(message).start();
    }

    public StopWatch start() {
        sw.start();
        return this;
    }
    
    public StopWatch stop() {
        sw.stop();
        return this;
    }

    public StopWatch split() {
        sw.split();
        return this;
    }

    public StopWatch reset() {
        sw.reset();
        return this;
    }

    public StopWatch restart() {
        sw.reset();
        sw.start();
        return this;
    }
    
    public StopWatch resume() {
        sw.resume();
        return this;
    }

    public StopWatch suspend() {
        sw.suspend();
        return this;
    }

    public StopWatch unsplit() {
        sw.unsplit();
        return this;
    }

    public String formatTime() {
        return sw.formatTime();
    }

    public String formatSplitTime() {
        return sw.formatSplitTime();
    }

    public String formatTimeAndRestart() {
        final String s = sw.formatTime();
        restart();
        return s;
    }

    public String formatSplitTimeAndRestart() {
        final String s = sw.formatSplitTime();
        restart();
        return s;
    }

    public Duration getDuration() {
        return Duration.ofNanos(sw.getNanoTime());
    }

    public Duration getDurationAndRestart() {
        final Duration d = getDuration();
        restart();
        return d;
    }

    public long getSplitTime() {
        return sw.getSplitTime();
    }

    public long getSplitNanoTime() {
        return sw.getSplitNanoTime();
    }

    public Duration getSplitDuration() {
        return Duration.ofNanos(sw.getSplitNanoTime());
    }

    public long getTime() {
        return sw.getTime();
    }

    public long getTimeAndRestart() {
        final long time = getTime();
        restart();
        return time;
    }

    public long getTime(final TimeUnit unit) {
        return sw.getTime(unit);
    }

    public long getTimeAndRestart(final TimeUnit unit) {
        final long time = getTime(unit);
        restart();
        return time;
    }

    public String getMessage() {
        return sw.getMessage();
    }

    public long getNanoTime() {
        return sw.getNanoTime();
    }

    public long getNanoTimeAndRestart() {
        final long time = getNanoTime();
        restart();
        return time;
    }
    
    public long getStartTime() {
        return sw.getStartTime();
    }

    public long getStopTime() {
        return sw.getStopTime();
    }

    public TimeValue<Long> getStartTimeValue() {
        return TimeValues.of(sw.getStartTime());
    }

    public TimeValue<Long> getStopTimeValue() {
        return TimeValues.of(sw.getStopTime());
    }

    public boolean isStarted() {
        return sw.isStarted();
    }

    public boolean isStopped() {
        return sw.isStopped();
    }

    public boolean isSuspended() {
        return sw.isSuspended();
    }

    @Override
    public String toString() {
        return sw.toString();
    }

    public String toSplitString() {
        return sw.toSplitString();
    }
    
    private final org.apache.commons.lang3.time.StopWatch sw;
    
}
