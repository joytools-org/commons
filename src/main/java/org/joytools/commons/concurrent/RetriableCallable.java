/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 */
public class RetriableCallable<V> implements Callable<V> {

    /**
     * 
     * @param callable 
     */
    protected RetriableCallable(final Callable<V> callable) {
        this.callable = checkNotNull(callable, "Callable");
    }
    
    /**
     * 
     * @param <T>
     * @param callable
     * @return 
     */
    public static <T> RetriableCallable<T> of(final Callable<T> callable) {
        return new RetriableCallable(callable);
    }
    
    /**
     * 
     * @param delay
     * @return 
     */
    public RetriableCallable<V> delay(final TimeValueUnit delay) {
        this.delay = delay;
        return this;
    }
    
    /**
     * 
     * @param attempts
     * @return 
     */
    public RetriableCallable<V> attempts(final int attempts) {
        this.attempts = attempts;
        return this;
    }

    /**
     * 
     * @param map
     * @return 
     */
    public RetriableCallable<V> map(final BiFunction<Callable<V>, Integer, Callable<V>> map) {
        this.map = map;
        return this;
    }

    /**
     * 
     * @param filter
     * @return 
     */
    public RetriableCallable<V> filter(final Predicate<Throwable> filter) {
        this.filter = filter;
        return this;
    }

    private BiFunction<Callable<V>, Integer, Callable<V>> map;

    private TimeValueUnit delay;

    private Predicate<Throwable> filter;

    private int attempts = 0;
    
    private final Callable<V> callable;

    @Override
    public V call() throws Exception {
        if (attempts <= 0) {
            return callable.call();
        }
        int attempt = 0;
        Callable<V> c = callable;
        while (true) {
            try {
                return c.call();
            } catch (final Exception e) {
                if (++attempt > attempts || filter != null && !filter.test(e)) {
                    throw e;
                }
                if (delay != null) {
                    Thread.sleep(delay.toMillis());
                }
                if (map != null) {
                    c = map.apply(callable, attempt);
                    if (c == null) {
                        throw new IllegalStateException("Operation aborted since the map function returned null");
                    }
                }
            }
        }
    }
    
}
