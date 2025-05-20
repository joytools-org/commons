/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import static com.google.common.base.Preconditions.checkNotNull;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import org.joytools.commons.collections.IterableAccessors;

/**
 *
 * @author AndreaR
 */
public class ExecutorServiceSubmit<E> extends AbstractExecutorServiceInvokeSubmit<E> {
    
    /**
     * 
     * @param es 
     */
    protected ExecutorServiceSubmit(final ExecutorService es) {
        super(es);
    }
    
    /**
     * 
     * @param <T>
     * @param es
     * @return 
     */
    public static <T> ExecutorServiceSubmit<T> of(final ExecutorService es) {
        return new ExecutorServiceSubmit(es);
    }
    
    /**
     * 
     * @param maxErrors
     * @return 
     */
    public ExecutorServiceSubmit<E> maxErrors(final Integer maxErrors) {
        errorThreshold0(maxErrors);
        return this;
    }

    /**
     * 
     * @param delay
     * @return 
     */
    public ExecutorServiceSubmit<E> delay(final TimeValueUnit delay) {
        this.delay = delay;
        return this;
    }
    
    /**
     * 
     * @param value
     * @param unit
     * @return 
     */
    public ExecutorServiceSubmit<E> delay(final long value, final TimeUnit unit) {
        return delay(TimeValueUnits.of(value, unit));
    }
    
    /**
     * 
     * @return  
     */
    public ComposedFutureCompletion<E> submit() {
        final ExecutorService es = executorService();
        final List<Future<E>> futures = Collections.synchronizedList(new ArrayList());
        final AtomicInteger errorCount = new AtomicInteger();
        boolean first = true;
        final int maxErrors = errorThreshold();
        for (final Callable<E> c : callables()) {
            if (first) {
                first = false; 
            } else {
                if (delay != null) {
                    try {
                        sleep(delay.toMillis());
                    } catch (final InterruptedException ex) {
                        Thread.currentThread().interrupt();
                        throw new IllegalStateException("The execution has been interrupted", ex);
                    }
                }
            }
            if (Thread.currentThread().isInterrupted() || isAboveErrorThreshold(errorCount)) {
                break;
            }
            final Callable<E> wc = watchErrors(c, maxErrors, errorCount, futures, futures.size());
            final Future<E> f = es.submit(wc);
            futures.add(f);
        }
        // Synchronization no longer needed
        return ComposedFutureCompletion.of(futures);
    }

    /**
     * 
     * @param callable
     * @return 
     */
    public ExecutorServiceSubmit<E> add(final Callable<E> callable) {
        checkNotNull(callable, "Callable");
        add0(callable);
        return this;
    }

    /**
     * 
     * @param runnable
     * @return 
     */
    public ExecutorServiceSubmit<E> add(final Runnable runnable) {
        return add(runnable, null);
    }

    /**
     * 
     * @param runnable
     * @param result
     * @return 
     */
    public ExecutorServiceSubmit<E> add(final Runnable runnable, final E result) {
        checkNotNull(runnable, "Runnable");
        add0(() -> {
            runnable.run();
            return result;
        });
        return this;
    }

    /**
     * 
     * @param callables
     * @return 
     */
    public ExecutorServiceSubmit<E> addAll(final Callable<E>... callables) {
        checkNotNull(callables, "Callables");
        // addAll(StreamIterables.of(callables));
        addAll(IterableAccessors.from(callables));
        return this;
    }
    
    /**
     * 
     * @param callables
     * @return 
     */
    public ExecutorServiceSubmit<E> addAll(final Iterable<Callable<E>> callables) {
        checkNotNull(callables, "Callables");
        callables.forEach(this::add0);
        return this;
    }

    /**
     * 
     * @param callables
     * @return 
     */
    public ExecutorServiceSubmit<E> set(final Iterable<Callable<E>> callables) {
        set0(callables);
        return this;
    }

    /**
     * 
     */
    private TimeValueUnit delay = null;
    
}
