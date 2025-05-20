/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <V>
 * @param <R>
 */
@Deprecated
abstract class OldComposedCallable2<V, R> implements Callable<R> {
    
    @Deprecated
    public static interface Settings<E, T> {

        public ExecutorService executorService();

        default public ExecutorServiceStrategyV1 executorServiceStrategy() {
            return ExecutorServiceStrategyV1.forSubmit();
        }

        public Iterable<Callable<E>> callables();
    
        public int maxErrors();
        
        public ExecutorServiceShutdown.Settings shutdownSettings();
    
        public TimeValueUnit completionTimeout();

        default public Predicate<BasicComposedResult<E>> throwFailuresCondition() {
            return null;
        }
    
        public Function<BasicComposedResult<E>, T> mapResult();
        
    }
    
    /**
     * 
     * @return 
     */
    abstract protected Settings<V, R> settings();
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    @Override
    public R call() throws Exception {
        final Settings settings = checkNotNull(settings(), "Settings");
        final Callable<R> c = new AbstractComposedCallableV1BeforeStructConcurrency<V, R>() {
            @Override
            protected ExecutorService executorService() {
                return settings.executorService();
            }

            @Override
            protected ExecutorServiceStrategyV1 executorServiceStrategy() {
                return settings.executorServiceStrategy();
            }

            @Override
            protected Iterable<Callable<V>> callables() {
                return settings.callables();
            }

            @Override
            protected ExecutorServiceShutdown.Settings shutdownSettings() {
                return settings.shutdownSettings();
            }

            @Override
            protected int maxErrors() {
                return settings.maxErrors();
            }
            
            @Override
            protected TimeValueUnit completionTimeout() {
                return settings.completionTimeout();
            }

            @Override
            protected Predicate<BasicComposedResult<V>> failOnCondition() {
                return settings.throwFailuresCondition();
            }

            @Override
            protected Function<BasicComposedResult<V>, R> mapResult() {
                return settings.mapResult();
            }
        };
        return c.call();
    }
    
}
