/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <V>
 */
@Deprecated
public class ComposedCallableV1BeforeStructConcurrency<V> extends AbstractComposedCallableV1BeforeStructConcurrency<V, BasicComposedResult<V>> {

    /**
     * 
     * @param maxThreads
     * @param timeout
     * @param callables 
     */
    public ComposedCallableV1BeforeStructConcurrency(final int maxThreads, final TimeValueUnit timeout, final Iterable<Callable<V>> callables) {
        this.maxThreads = maxThreads;
        this.timeout = timeout;
        this.callables = checkNotNull(callables, "Callables");
    }
    
    /**
     * 
     * @param providedExecutorService
     * @param timeout
     * @param callables 
     */
    public ComposedCallableV1BeforeStructConcurrency(final ExecutorService providedExecutorService, final TimeValueUnit timeout, final Iterable<Callable<V>> callables) {
        this.providedExecutorService = checkNotNull(providedExecutorService, "Executor Service");
        this.maxThreads = -1;
        this.timeout = timeout;
        this.callables = checkNotNull(callables, "Callables");
    }

    /**
     * 
     * @param condition
     * @return 
     */
    public ComposedCallableV1BeforeStructConcurrency<V> failOnCondition(final Predicate<BasicComposedResult<V>> condition) {
        this.condition = condition;
        return this;
    }
    
    /**
     * 
     * @param delay
     * @return 
     */
    public ComposedCallableV1BeforeStructConcurrency<V> delay(final TimeValueUnit delay) {
        this.delay = delay;
        return this;
    }

    /**
     * 
     * @param maxErrors
     * @return 
     */
    public ComposedCallableV1BeforeStructConcurrency<V> maxErrors(final int maxErrors) {
        this.maxErrors = maxErrors;
        return this;
    }

    private final int maxThreads;
    
    private final TimeValueUnit timeout;
    
    private ExecutorService providedExecutorService;
    
    private final Iterable<Callable<V>> callables;
    
    private final ExecutorServiceShutdown.Settings shutdownSettings = new ExecutorServiceShutdown.Settings() {};

    private int maxErrors = -1;

    private TimeValueUnit delay;

    private Predicate<BasicComposedResult<V>> condition;
    
    /**
     * 
     * @return 
     */
    @Override
    protected ExecutorService executorService() {
        if (providedExecutorService != null) {
            return providedExecutorService;
        }
        if (maxThreads <= 0) {
            return Executors.newCachedThreadPool();
        } else
        if (maxThreads == 1) {
            if (timeout == null || timeout.isAbsent()) {
                return MoreExecutors.newDirectExecutorService();
            } else {
                return Executors.newSingleThreadExecutor();
            }
        } 
        else {
            return Executors.newFixedThreadPool(maxThreads);
        }
    }
    
    @Override
    protected ExecutorServiceStrategyV1 executorServiceStrategy() {
        if (delay == null) {
            return ExecutorServiceStrategyV1.forSubmit();
            // return ExecutorServiceStrategy.forInvoke();
        } else {
            return ExecutorServiceStrategyV1.forSubmit(delay);
        }
    }

    @Override
    protected Iterable<Callable<V>> callables() {
        return callables;
    }

    @Override
    protected ExecutorServiceShutdown.Settings shutdownSettings() {
        if (providedExecutorService != null) {
            return null;
        }
        return shutdownSettings;
    }

    @Override
    protected int maxErrors() {
        return maxErrors;
    }
    
    @Override
    protected TimeValueUnit completionTimeout() {
        return timeout;
    }

    @Override
    protected Function<BasicComposedResult<V>, BasicComposedResult<V>> mapResult() {
        // return r -> true;
        return Function.identity();
    }

    @Override
    protected Predicate<BasicComposedResult<V>> failOnCondition() {
        return condition;
    }
    
    @Deprecated
    public static ExecutorService old_newFixedThreadPool(final int maxThreads) {
        return new ThreadPoolExecutor(0, maxThreads,
                                      0L, TimeUnit.MILLISECONDS,
                                      new LinkedBlockingQueue<>());
    }
    
}
