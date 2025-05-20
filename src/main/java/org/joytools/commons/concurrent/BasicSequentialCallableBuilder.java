/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang3.builder.Builder;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public class BasicSequentialCallableBuilder<V> implements Builder<Callable<BasicComposedResult<V>>> {

    protected BasicSequentialCallableBuilder() {
    }

    public BasicSequentialCallableBuilder<V> add(final Callable<? extends V> task) {
        Objects.requireNonNull(task, "Task"); 
        this.tasks = this.tasks.append((Callable<V>)task);
        return this;
    }

    // public <C extends Callable<? extends E>> SeqCallableBuilder<E> addAll(final Iterable<? extends Callable<E>> tasks) {
    public <C extends Callable<? extends V>> BasicSequentialCallableBuilder<V> addAll(final Iterable<? extends Callable<? extends V>> tasks) {
        Objects.requireNonNull(tasks, "Tasks");
        this.tasks = this.tasks.appendAll((Iterable<Callable<V>>)tasks);
        return this;
    }

    public <C extends Callable<? extends V>> BasicSequentialCallableBuilder<V> addAll(final C... tasks) {
        Objects.requireNonNull(tasks, "Tasks");
        for (final C c : tasks) {
            this.tasks = this.tasks.append((Callable<V>)c);
        }
        return this;
    }

    public BasicSequentialCallableBuilder<V> throwPolicy(final Predicate<ComposedResult<Integer, V>> condition) {
        this.throwPolicy = Objects.requireNonNull(condition, "Throw Policy Condition");
        this.throwPolicyMapper = null;
        return this;
    }

    public BasicSequentialCallableBuilder<V> throwPolicy(final Predicate<ComposedResult<Integer, V>> condition,
            final Function<ComposedResult<Integer, V>, Exception> mapper) {
        throwPolicy(condition);
        this.throwPolicyMapper = Objects.requireNonNull(mapper, "Throw Policy Mapper");
        return this;
    }

    public BasicSequentialCallableBuilder<V> throwNoException() {
        return throwPolicy(ThrowPolicies.alwaysFalse());
    }

    public BasicSequentialCallableBuilder<V> throwFirstException() {
        return throwPolicy(ThrowPolicies.isAnyFailed(), ThrowPolicies.firstException());
    }

    public BasicSequentialCallableBuilder<V> throwAllExceptions() {
        return throwPolicy(ThrowPolicies.isAnyFailed(), ThrowPolicies.allExceptions());
    }

    public BasicSequentialCallableBuilder<V> shutdownOnSuccess() {
        return shutdownPolicy(ShutdownPolicies.onSuccess());
    }

    public BasicSequentialCallableBuilder<V> shutdownOnSuccess(final int count) {
        return shutdownPolicy(ShutdownPolicies.onSuccess(count));
    }

    public BasicSequentialCallableBuilder<V> shutdownOnCompletion() {
        return shutdownPolicy(ShutdownPolicies.onCompletion());
    }

    public BasicSequentialCallableBuilder<V> shutdownOnCompletion(final int count) {
        return shutdownPolicy(ShutdownPolicies.onCompletion(count));
    }

    public BasicSequentialCallableBuilder<V> shutdownOnFailure() {
        return shutdownPolicy(ShutdownPolicies.onFailure());
    }

    public BasicSequentialCallableBuilder<V> shutdownOnFailure(final int count) {
        return shutdownPolicy(ShutdownPolicies.onFailure(count));
    }

    public BasicSequentialCallableBuilder<V> shutdownPolicy(final Predicate<ComposedResult<Integer, V>> shutdownPolicy) {
        this.shutdownPolicy = Objects.requireNonNull(shutdownPolicy, "Shutdown Policy");
        return this;
    }

    public BasicSequentialCallableBuilder<V> sumbitOrder() {
        return completionOrder(false);
    }

    public BasicSequentialCallableBuilder<V> completionOrder() {
        return completionOrder(true);
    }

    public BasicSequentialCallableBuilder<V> completionOrder(final boolean completionOrder) {
        this.completionOrder = completionOrder;
        return this;
    }

    public BasicSequentialCallableBuilder<V> timeout(final TimeValueUnit timeout) {
        this.timeout = timeout;
        return this;
    }

    public BasicSequentialCallableBuilder<V> timeout(final Duration timeout) {
        this.timeout = timeout == null ? null : TimeValueUnits.of(timeout);
        return this;
    }

    public BasicSequentialCallableBuilder<V> shutdownTimeout(final TimeValueUnit shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
        return this;
    }

    public BasicSequentialCallableBuilder<V> shutdownTimeout(final Duration shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout == null ? null : TimeValueUnits.of(shutdownTimeout);
        return this;
    }

    protected String prefix() {
        final String s = new java.sql.Timestamp(System.currentTimeMillis()).toString();
        return StringUtils.rightPad(s, 23, "0") + " ";
    }
    
    @Override
    public Callable<BasicComposedResult<V>> build() {
        final BasicComposedCallableSettings<V> settings = new BasicComposedCallableSettings<V>() {
            @Override
            public Seq<Callable<V>> tasks() {
                return tasks;
            }

            @Override
            public Integer maxThreads() {
                return 0;
            }

            @Override
            public boolean completionOrder() {
                return completionOrder;
            }

            @Override
            public TimeValueUnit timeout() {
                return timeout;
            }

            @Override
            public Predicate<ComposedResult<Integer, V>> shutdownPolicy() {
                return shutdownPolicy;
            }

            @Override
            public Predicate<ComposedResult<Integer, V>> throwPolicy() {
                return throwPolicy;
            }

            @Override
            public Function<ComposedResult<Integer, V>, Exception> throwPolicyMapper() {
                return throwPolicyMapper;
            }

            @Override
            public TimeValueUnit shutdownTimeout() {
                return shutdownTimeout;
            }
        };
        // return Callables.forComposition(settings);
        return new BasicComposedCallable<V>() {
            @Override
            protected BasicComposedCallableSettings<V> settings() {
                return settings;
            }
        };
    }

    private Seq<Callable<V>> tasks = Vector.empty();

    private TimeValueUnit timeout;
    
    private TimeValueUnit shutdownTimeout;

    private Predicate<ComposedResult<Integer, V>> shutdownPolicy = ShutdownPolicies.onCompletion();
    
    private Predicate<ComposedResult<Integer, V>> throwPolicy = ThrowPolicies.isAnyFailed();

    private Function<ComposedResult<Integer, V>, Exception> throwPolicyMapper = null;

    private boolean completionOrder = false;
    
}
