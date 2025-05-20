/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import org.joytools.commons.collections.IterableAccessors;

/**
 *
 * @author AndreaR
 */
@Deprecated
public class ComposedCallablesV1BeforeIterator {
    
    public ComposedCallablesV1BeforeIterator() {
    }
    
    public static <T> ComposedCallableV1BeforeStructConcurrency<T> of(final int maxThreads, final TimeValueUnit timeout, final Iterable<Callable<T>> callables) {
        return new ComposedCallableV1BeforeStructConcurrency(maxThreads, timeout, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> of(final int maxThreads, final TimeValueUnit timeout, final Callable<T>... callables) {
        return new ComposedCallableV1BeforeStructConcurrency(maxThreads, timeout, IterableAccessors.from(callables));
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> of(final ExecutorService es, final TimeValueUnit timeout, final Iterable<Callable<T>> callables) {
        return new ComposedCallableV1BeforeStructConcurrency(es, timeout, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> of(final ExecutorService es, final TimeValueUnit timeout, final Callable<T>... callables) {
        return new ComposedCallableV1BeforeStructConcurrency(es, timeout, IterableAccessors.from(callables));
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forFixedThreadPool(final int maxThreads, final TimeValueUnit timeout, final Iterable<Callable<T>> callables) {
        checkArgument(maxThreads > 0, MAXTHREADS_MUST_BE_GT0);
        return of(maxThreads, timeout, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forFixedThreadPool(final int maxThreads, final TimeValueUnit timeout, final Callable<T>... callables) {
        checkArgument(maxThreads > 0, MAXTHREADS_MUST_BE_GT0);
        return of(maxThreads, timeout, IterableAccessors.from(callables));
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forFixedThreadPool(final int maxThreads, final Iterable<Callable<T>> callables) {
        checkArgument(maxThreads > 0, MAXTHREADS_MUST_BE_GT0);
        return of(maxThreads, null, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forFixedThreadPool(final int maxThreads, final Callable<T>... callables) {
        checkArgument(maxThreads > 0, MAXTHREADS_MUST_BE_GT0);
        return of(maxThreads, null, IterableAccessors.from(callables));
    }
    
    private static final String MAXTHREADS_MUST_BE_GT0 = "Max number of threads must be greater than 0";

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forExecutorService(final ExecutorService es, final TimeValueUnit timeout, final Iterable<Callable<T>> callables) {
        return of(es, timeout, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forExecutorService(final ExecutorService es, final TimeValueUnit timeout, final Callable<T>... callables) {
        return of(es, timeout, IterableAccessors.from(callables));
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forExecutorService(final ExecutorService es, final Iterable<Callable<T>> callables) {
        return of(es, null, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forExecutorService(final ExecutorService es, final Callable<T>... callables) {
        return of(es, null, IterableAccessors.from(callables));
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forCachedThreadPool(final TimeValueUnit timeout, final Iterable<Callable<T>> callables) {
        return of(0, timeout, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forCachedThreadPool(final TimeValueUnit timeout, final Callable<T>... callables) {
        return of(0, timeout, IterableAccessors.from(callables));
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forSingleThread(final TimeValueUnit timeout, final Iterable<Callable<T>> callables) {
        return of(1, timeout == null ? TimeValueUnits.of() : timeout, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forSingleThread(final TimeValueUnit timeout, final Callable<T>... callables) {
        return of(1, timeout == null ? TimeValueUnits.of() : timeout, IterableAccessors.from(callables));
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forCurrentThread(final Iterable<Callable<T>> callables) {
        return of(1, null, callables);
    }

    public static <T> ComposedCallableV1BeforeStructConcurrency<T> forCurrentThread(final Callable<T>... callables) {
        return of(1, null, IterableAccessors.from(callables));
    }

    public static <T> Callable<BasicComposedResult<T>> forSequential(final Iterable<Callable<T>> callables) {
        return forCurrentThread(callables)
                .maxErrors(0)
                .failOnCondition(sr -> sr.isAnyFailed());
    }

    public static <T> Callable<BasicComposedResult<T>> forSequential(final Callable<T>... callables) {
        return forCurrentThread(callables)
                .maxErrors(0)
                .failOnCondition(sr -> sr.isAnyFailed());
    }
    
}
