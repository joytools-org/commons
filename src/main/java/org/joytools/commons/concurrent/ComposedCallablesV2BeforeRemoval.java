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
// import org.joytools.commons.collections.StreamIterables;
/**
 *
 * @author AndreaR
 */
@Deprecated
public class ComposedCallablesV2BeforeRemoval {
    
    /**
     * 
     */
    public ComposedCallablesV2BeforeRemoval() {
    }
    
    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param maxThreads
     * @param timeout
     * @param callables
     * @return 
     */
    /*
    public static <T> ComposedCallable<T> of(final int maxThreads, 
            final TimeValueUnit timeout, 
            final Iterable<Callable<T>> callables) {
        return new ComposedCallable(maxThreads, timeout, callables);
    } */
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> of(final int maxThreads, 
            final TimeValueUnit timeout, 
            final I callables) {
        return new ComposedCallableV1BeforeStructConcurrency(maxThreads, timeout, callables);
    } 

    /**
     * 
     * @param <T>
     * @param <C>
     * @param maxThreads
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_of(final int maxThreads, 
            final TimeValueUnit timeout, 
            final C... callables) {
        return new ComposedCallableV1BeforeStructConcurrency(maxThreads, timeout, IterableAccessors.from(callables));
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param es
     * @param timeout
     * @param callables
     * @return 
     */
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> of(final ExecutorService es, 
            final TimeValueUnit timeout, 
            final I callables) {
        return new ComposedCallableV1BeforeStructConcurrency(es, timeout, callables);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param es
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_of(final ExecutorService es, 
            final TimeValueUnit timeout, 
            final C... callables) {
        return new ComposedCallableV1BeforeStructConcurrency(es, timeout, IterableAccessors.from(callables));
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param maxThreads
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> old_forFixedThreadPool(final int maxThreads, 
            final TimeValueUnit timeout, 
            final I callables) {
        checkArgument(maxThreads > 0, MAXTHREADS_MUST_BE_GT0);
        return of(maxThreads, timeout, callables);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param maxThreads
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_forFixedThreadPool(final int maxThreads, 
            final TimeValueUnit timeout, 
            final C... callables) {
        checkArgument(maxThreads > 0, MAXTHREADS_MUST_BE_GT0);
        return of(maxThreads, timeout, IterableAccessors.from(callables));
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param maxThreads
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> old_forFixedThreadPool(final int maxThreads, 
            final I callables) {
        checkArgument(maxThreads > 0, MAXTHREADS_MUST_BE_GT0);
        return of(maxThreads, null, callables);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param maxThreads
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_forFixedThreadPool(final int maxThreads, 
            final C... callables) {
        checkArgument(maxThreads > 0, MAXTHREADS_MUST_BE_GT0);
        return of(maxThreads, null, IterableAccessors.from(callables));
    }
    
    /**
     * 
     */
    private static final String MAXTHREADS_MUST_BE_GT0 = "Max number of threads must be greater than 0";

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param es
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> old_forExecutorService(final ExecutorService es, 
            final TimeValueUnit timeout, 
            final I callables) {
        return of(es, timeout, callables);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param es
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_forExecutorService(final ExecutorService es, 
            final TimeValueUnit timeout, 
            final C... callables) {
        return of(es, timeout, IterableAccessors.from(callables));
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param es
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> old_forExecutorService(final ExecutorService es, 
            final I callables) {
        return of(es, null, callables);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param es
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_forExecutorService(final ExecutorService es, 
            final C... callables) {
        return of(es, null, IterableAccessors.from(callables));
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> old_forCachedThreadPool(final TimeValueUnit timeout, 
            final I callables) {
        return of(0, timeout, callables);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_forCachedThreadPool(final TimeValueUnit timeout, 
            final C... callables) {
        return of(0, timeout, IterableAccessors.from(callables));
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> old_forSingleThread(final TimeValueUnit timeout, 
            final I callables) {
        return of(1, timeout == null ? TimeValueUnits.of() : timeout, callables);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param timeout
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_forSingleThread(final TimeValueUnit timeout, 
            final C... callables) {
        return of(1, timeout == null ? TimeValueUnits.of() : timeout, IterableAccessors.from(callables));
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>, I extends Iterable<C>> ComposedCallableV1BeforeStructConcurrency<T> old_forCurrentThread(final I callables) {
        return of(1, null, callables);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> ComposedCallableV1BeforeStructConcurrency<T> old_forCurrentThread(final C... callables) {
        return of(1, null, IterableAccessors.from(callables));
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param <I>
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>, I extends Iterable<C>> Callable<BasicComposedResult<T>> old_forSequential(final I callables) {
        return old_forCurrentThread(callables)
                .maxErrors(0)
                .failOnCondition(sr -> sr.isAnyFailed());
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param callables
     * @return 
     */
    @Deprecated
    public static <T, C extends Callable<T>> Callable<BasicComposedResult<T>> old_forSequential(final Callable<T>... callables) {
        return old_forCurrentThread(callables)
                .maxErrors(0)
                .failOnCondition(sr -> sr.isAnyFailed());
    }
    
}
