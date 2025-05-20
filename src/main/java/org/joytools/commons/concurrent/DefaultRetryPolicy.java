/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public class DefaultRetryPolicy<V> implements Function<Callable<V>, Callable<V>> {

    /**
     * 
     */
    public static final IntFunction<Duration> DELAY_POLICY_NONE = x -> Duration.ofMillis(0);

    /**
     * 
     */
    public static final IntFunction<Duration> DELAY_POLICY_5_SECONDS_FIXED = x -> Duration.ofMillis(5000);

    /**
     * 
     */
    public static final IntFunction<Duration> DELAY_POLICY_5_SECONDS_EXPONENTIAL = x -> Duration.ofMillis(x * 5000);
            
    /**
     * 
     * @param test
     * @param maxRetries 
     * @param retryDelay 
     */
    public DefaultRetryPolicy(
            final Predicate<? super Exception> test, 
            final int maxRetries, 
            final IntFunction<Duration> retryDelay) {
        this.test = Objects.requireNonNull(test, "Predicate");
        this.maxRetries = maxRetries;
        this.retryDelayPolicy = Objects.requireNonNull(retryDelay, "Retry Delay Function");
    }
    
    private final Predicate<? super Exception> test;
    
    private final IntFunction<Duration> retryDelayPolicy;

    private final int maxRetries;

    /**
     * 
     * @param callable
     * @return 
     */
    @Override
    public Callable<V> apply(final Callable<V> callable) {
        Objects.requireNonNull(callable, "Callable");
        return () -> {
            int retryNum = 0;
            while (true) {
                try {
                    return callable.call();
                } catch (final Exception ex) {
                    if (test.test(ex) && (++retryNum <= maxRetries || maxRetries < 0)) {
                        Thread.sleep(retryDelayPolicy.apply(retryNum).toMillis());
                    } else {
                        throw ex;
                    }
                }
            } 
        };
    }
    
}
