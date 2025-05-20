/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <V>
 * @param <R>
 */
@Deprecated
abstract class AbstractComposedCallableV1BeforeStructConcurrency<V, R> implements Callable<R> {
    
    abstract protected ExecutorService executorService();

    abstract protected ExecutorServiceStrategyV1 executorServiceStrategy();
    /*
    protected ExecutorServiceStrategy executorServiceStrategy() {
        return ExecutorServiceStrategy.forSubmit();
    } */

    abstract protected Iterable<Callable<V>> callables();
    
    abstract protected ExecutorServiceShutdown.Settings shutdownSettings();
    
    abstract protected TimeValueUnit completionTimeout();

    abstract protected int maxErrors();
    /*
    protected int maxErrors() {
        return -1;
    } */
    
    abstract protected Predicate<BasicComposedResult<V>> failOnCondition();
    /*
    protected Predicate<FuturesCompletion<V>> throwFailuresCondition() {
        return null;
    } */
    
    abstract protected Function<BasicComposedResult<V>, R> mapResult();

    @Override
    public R call() throws Exception {
        boolean lenientShutdown = true;
        final ExecutorService es = checkNotNull(executorService(), "Executor Service");
        try {
            final ExecutorServiceStrategyV1 ess = checkNotNull(executorServiceStrategy(), "Executor Service Strategy");
            final Function<BasicComposedResult<V>, R> mr = checkNotNull(mapResult(), "Map Result Function");
            final BasicComposedResult<V> result = ess
                    .invokeOrSubmit(es, callables(), completionTimeout(), maxErrors())
                    .waitForCompletion(0, TimeUnit.MILLISECONDS)
                    .throwFailures(failOnCondition())
                    .result();
            final R value = mr.apply(result);
            lenientShutdown = false;
            return value;
        } finally {
            final ExecutorServiceShutdown.Settings settings = shutdownSettings();
            if (settings != null) {
                final ExecutorServiceShutdown ess = ExecutorServiceShutdown.of(es, settings);
                if (lenientShutdown) {
                    try {
                        ess.execute();
                    } catch (final Exception e) {}
                } else {
                    ess.execute();
                }
            }
        } 
    }
    
}
