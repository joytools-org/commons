/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import static com.google.common.base.Preconditions.checkNotNull;
import org.joytools.commons.collections.IterableAccessors;

/**
 *
 * @author AndreaR
 */
@Deprecated
public class ExecutorServiceInvokeV1<E> extends AbstractExecutorServiceInvokeSubmit<E> {
    
    /**
     * 
     * @param es 
     */
    protected ExecutorServiceInvokeV1(final ExecutorService es) {
        super(es);
    }
    
    /**
     * 
     * @param <T>
     * @param es
     * @return 
     */
    public static <T> ExecutorServiceInvokeV1<T> of(final ExecutorService es) {
        return new ExecutorServiceInvokeV1(es);
    }
    
    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    public ComposedFutureCompletion<E> invoke() throws InterruptedException {
        return ComposedFutureCompletion.of(executorService().invokeAll(callableList()));
    }

    /**
     * 
     * @param timeout
     * @return
     * @throws InterruptedException 
     */
    public ComposedFutureCompletion<E> invoke(final TimeValueUnit timeout) throws InterruptedException {
        if (timeout == null || timeout.isAbsent()) {
            return invoke();
        }
        return invoke(timeout.value(), timeout.unit());
    }

    /**
     * 
     * @param value
     * @param unit
     * @return
     * @throws InterruptedException 
     */
    public ComposedFutureCompletion<E> invoke(final long value, final TimeUnit unit) throws InterruptedException {
        return ComposedFutureCompletion.of(executorService().invokeAll(callableList(), value, unit));
    }

    /**
     * 
     * @param callable
     * @return 
     */
    public ExecutorServiceInvokeV1<E> add(final Callable<E> callable) {
        checkNotNull(callable, "Callable");
        add0(callable);
        return this;
    }

    /**
     * 
     * @param runnable
     * @return 
     */
    public ExecutorServiceInvokeV1<E> add(final Runnable runnable) {
        return add(runnable, null);
    }

    /**
     * 
     * @param runnable
     * @param result
     * @return 
     */
    public ExecutorServiceInvokeV1<E> add(final Runnable runnable, final E result) {
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
    public ExecutorServiceInvokeV1<E> addAll(final Callable<E>... callables) {
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
    public ExecutorServiceInvokeV1<E> addAll(final Iterable<Callable<E>> callables) {
        checkNotNull(callables, "Callables");
        callables.forEach(this::add0);
        return this;
    }

}
