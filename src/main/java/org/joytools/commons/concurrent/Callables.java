/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import static io.vavr.API.Left;
import static io.vavr.API.Right;
import io.vavr.control.Either;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;
import java.util.function.Supplier;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.lang.StringUtils;

public class Callables {
    
    /**
     * 
     * @param <T>
     * @param <C>
     * @param type
     * @return 
     */
    public static <T, C extends Callable<T>> BasicParallelCallableBuilder<T> parallel(final Class<T> type) {
        Objects.requireNonNull(type, "Type");
        return new BasicParallelCallableBuilder<>();
    }

    /**
     * 
     * @param <T>
     * @param tasks
     * @return 
     */
    public static <T> BasicParallelCallableBuilder<T> parallel(final Iterable<? extends Callable<? extends T>> tasks) {
        final BasicParallelCallableBuilder<T> builder = new BasicParallelCallableBuilder<>();
        return builder.addAll(tasks);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param tasks
     * @return 
     */
    public static <T, C extends Callable<? extends T>> BasicParallelCallableBuilder<T> parallel(final C... tasks) {
        final BasicParallelCallableBuilder<T> builder = new BasicParallelCallableBuilder<>();
        return builder.addAll(tasks);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param type
     * @return 
     */
    public static <T, C extends Callable<T>> BasicSequentialCallableBuilder<T> sequential(final Class<T> type) {
        Objects.requireNonNull(type, "Type");
        final BasicSequentialCallableBuilder<T> builder = new BasicSequentialCallableBuilder<>();
        return builder;
    }

    /**
     * 
     * @param <T>
     * @param tasks
     * @return 
     */
    public static <T> BasicSequentialCallableBuilder<T> sequential(final Iterable<? extends Callable<? extends T>> tasks) {
        final BasicSequentialCallableBuilder<T> builder = new BasicSequentialCallableBuilder<>();
        return builder.addAll(tasks);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param tasks
     * @return 
     */
    public static <T, C extends Callable<? extends T>> BasicSequentialCallableBuilder<T> sequential(final C... tasks) {
        final BasicSequentialCallableBuilder<T> builder = new BasicSequentialCallableBuilder<>();
        return builder.addAll(tasks);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    @Deprecated
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
     * @param <V>
     * @param callable
     * @param delay
     * @return 
     */
    public static <V> Callable<V> delayBefore(final TimeValueUnit delay, final Callable<V> callable) {
        final Duration d = delay == null ? null : delay.toDuration();
        return delayBefore(d, callable);
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @param maxMillis
     * @return 
     */
    public static <V> Callable<V> randomDelayBefore(final long maxMillis, final Callable<V> callable) {
        return randomDelayBefore(0, maxMillis, callable);
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @param stopMillis
     * @return 
     */
    public static <V> Callable<V> randomDelayAfter(final Callable<V> callable, final long stopMillis) {
        return randomDelayAfter(callable, 0, stopMillis);
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @param startMillis
     * @param stopMillis
     * @return 
     */
    public static <V> Callable<V> randomDelayAfter(final Callable<V> callable, final long startMillis, final long stopMillis) {
        final Duration delay = Duration.ofMillis(RandomUtils.nextLong(startMillis, stopMillis));
        return delayAfter(callable, delay);
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @param startMillis
     * @param stopMillis
     * @return 
     */
    public static <V> Callable<V> randomDelayBefore(final long startMillis, final long stopMillis, final Callable<V> callable) {
        final Duration delay = Duration.ofMillis(RandomUtils.nextLong(startMillis, stopMillis));
        return delayBefore(delay, callable);
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @param delay
     * @return 
     */
    public static <V> Callable<V> delayBefore(final Duration delay, final Callable<V> callable) {
        Objects.requireNonNull(callable, "Callable");
        long millis;
        if (delay == null || (millis = delay.toMillis()) == 0) {
            return callable;
        }
        return () -> {
            Thread.sleep(millis);
            return callable.call();
        };
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @param delay
     * @return 
     */
    public static <V> Callable<V> delayAfter(final Callable<V> callable, final TimeValueUnit delay) {
        final Duration d = delay == null ? null : delay.toDuration();
        return delayAfter(callable, d);
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @param delay
     * @return 
     */
    public static <V> Callable<V> delayAfter(final Callable<V> callable, final Duration delay) {
        Objects.requireNonNull(callable, "Callable");
        long millis;
        if (delay == null || (millis = delay.toMillis()) == 0) {
            return callable;
        }
        return () -> {
            final V result = callable.call();
            Thread.sleep(millis);
            return result;
        };
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @return 
     */
    public static <V> Callable<Either<Exception, V>> tryCatchInterrupt(final Callable<V> callable) {
        Objects.requireNonNull(callable, "Callable");
        return () -> {
            try {
                return Right(callable.call());
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
                throw e;
            } catch (final Exception e) {
                return Left(e);
            }
        };
    }    
    
    /**
     * 
     * @param <V>
     * @param callable
     * @param action
     * @param defValue
     * @return 
     */
    public static <V> Callable<V> tryCatch(final Callable<V> callable,
            final FailableConsumer<Exception, ? extends Exception> action,
            final V defValue) {
        Objects.requireNonNull(callable, "Callable");
        return () -> {
            try {
                return callable.call();
            } catch (final Exception e) {
                if (action != null) {
                    action.accept(e);
                }
                return defValue;
            }
        };
    }    

    /**
     * 
     * @param <V>
     * @param callable
     * @return 
     */
    @Deprecated
    public static <V> Runnable toRunnableV1(final Callable<V> callable) {
        Objects.requireNonNull(callable, "Callable");
        return () -> {
            try {
                callable.call();
            } catch (final Exception t) {
                ExceptionUtils.wrapAndThrow(t);
            }
        };
    }
    
}