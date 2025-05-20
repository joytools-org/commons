/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author AndreaR
 * @param <V>
 */
@FunctionalInterface
@Deprecated
public interface ExecutorServiceStrategyV1<V> {

    /**
     * 
     * @param es
     * @param callables
     * @param timeout
     * @param maxErrors
     * @return 
     * @throws java.lang.InterruptedException 
     */
    public ComposedFutureCompletion<V> invokeOrSubmit(
            final ExecutorService es,
            final Iterable<Callable<V>> callables,
            final TimeValueUnit timeout,
            final int maxErrors) throws InterruptedException;
       
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> ExecutorServiceStrategyV1<T> forInvoke() {
        return (final ExecutorService es, final Iterable<Callable<T>> callables, final TimeValueUnit timeout, final int maxErrors) -> {
            if (maxErrors >= 0 && maxErrors < Integer.MAX_VALUE) {
                throw new UnsupportedOperationException("Max errors not supported yet with invoke");
            }
            return ExecutorServiceInvokeV1.<T>of(es)
                    .addAll(callables)
                    .invoke(timeout);
        };
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> ExecutorServiceStrategyV1<T> forSubmit() {
        return forSubmit(null);
    }

    /**
     * 
     * @param <T>
     * @param delay
     * @return 
     */
    public static <T> ExecutorServiceStrategyV1<T> forSubmit(final TimeValueUnit delay) {
        return (final ExecutorService es, final Iterable<Callable<T>> callables, final TimeValueUnit timeout, final int maxErrors) -> {
            final RemainingTime t = timeout == null ? Timeouts.none() : Timeouts.fromNow(timeout);
            return ExecutorServiceSubmit.<T>of(es)
                    .set(callables)
                    .delay(delay)
                    .maxErrors(maxErrors)
                    .submit()
                    .waitForCompletion(t);
        };
    }
    
}
