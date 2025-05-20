/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
abstract class AbstractComposedCallable {
 
    /**
     * 
     * @param <T>
     * @param callable
     * @return 
     */
    static <T> Future<T> toFuture(final Callable<T> callable) {
        final CompletableFuture<T> cf = new CompletableFuture();
        try {
            cf.complete(callable.call());
        } catch (final Exception ex) {
            cf.completeExceptionally(ex);
        }
        return cf;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    static <T> Future<T> newTimeoutFuture() {
        return new Future<T>() {
            @Override
            public boolean cancel(final boolean mayInterruptIfRunning) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isCancelled() {
                return true;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public T get() throws InterruptedException, ExecutionException {
                throw new UnsupportedOperationException();
            }

            @Override
            public T get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                throw new TimeoutException();
            }
        };
    }
    
    /**
     * 
     * @return 
     */
    static String tracePrefix() {
        final String s = new java.sql.Timestamp(System.currentTimeMillis()).toString();
        return StringUtils.rightPad(s, 23, "0") + " ";
    }
    
    /**
     * 
     * @param str 
     */
    static void trace(final Object str) {
    }
    
    
}
