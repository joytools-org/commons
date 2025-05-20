/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.sandbox.util;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;
import org.joytools.commons.concurrent.TimeValueUnit;

/**
 *
 * @author AndreaR
 */
public class Thresholds {
    
    /**
     * 
     * @param <T>
     * @param count
     * @return 
     */
    public static <T> Predicate<T> eachCount(final long count) {
        if (count < 0 || count == Long.MAX_VALUE) {
            return (Predicate<T>)ALWAYS_FALSE;
        }
        if (count == 0) {
            return (Predicate<T>)ALWAYS_TRUE;
        }
        final AtomicLong counter = new AtomicLong();
        return t -> {
            if (counter.incrementAndGet() >= count) {
                counter.set(0);
                return true;
            }
            return false;
        };
    }

    /**
     * 
     * @param <T>
     * @param amountMillis
     * @return 
     */
    public static <T> Predicate<T> eachElapsed(final long amountMillis) {
        if (amountMillis < 0 || amountMillis == Long.MAX_VALUE) {
            return (Predicate<T>)ALWAYS_FALSE;
        }
        if (amountMillis == 0) {
            return (Predicate<T>)ALWAYS_TRUE;
        }
        final AtomicLong expiryTime = new AtomicLong(System.currentTimeMillis() + amountMillis);
        return t -> {
            final long now = System.currentTimeMillis();
            if (now < expiryTime.get()) {
                return false;
            }
            expiryTime.set(now + amountMillis);
            return true;
        };
    }
    
    /**
     * 
     * @param <T>
     * @param amount
     * @return 
     */
    public static <T> Predicate<T> eachElapsed(final TimeValueUnit amount) {
        Objects.requireNonNull(amount, "Amount");
        return eachElapsed(amount.toMillis());
    }

    private final static Predicate<?> ALWAYS_FALSE = t -> false;
    
    private final static Predicate<?> ALWAYS_TRUE = t -> true;

    /*
    public static LongPredicate eachElapsed(final long amountMillis) {
        if (amountMillis < 0 || amountMillis == Long.MAX_VALUE) {
            return ALWAYS_FALSE_LONG;
        }
        if (amountMillis == 0) {
            return ALWAYS_TRUE_LONG;
        }
        final AtomicLong expiryTime = new AtomicLong(System.currentTimeMillis() + amountMillis);
        return t -> {
            final long now = System.currentTimeMillis();
            if (now < expiryTime.get()) {
                return false;
            }
            expiryTime.set(now + amountMillis);
            return true;
        };
    }
    
    public static LongPredicate eachElapsed(final TimeValueUnit amount) {
        Objects.requireNonNull(amount, "Amount");
        return eachElapsed(amount.toMillis());
    }

    private final static LongPredicate ALWAYS_FALSE_LONG = t -> false;
    
    private final static LongPredicate ALWAYS_TRUE_LONG = t -> true;
    */

}
