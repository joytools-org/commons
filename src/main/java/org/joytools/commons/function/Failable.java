/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.function;

import com.google.common.collect.AbstractIterator;
import io.vavr.Function1;
import io.vavr.Function2;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.apache.commons.lang3.function.FailableBiConsumer;
import org.apache.commons.lang3.function.FailableBiFunction;
import org.apache.commons.lang3.function.FailableBiPredicate;
import org.apache.commons.lang3.function.FailableBooleanSupplier;
import org.apache.commons.lang3.function.FailableCallable;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableDoubleBinaryOperator;
import org.apache.commons.lang3.function.FailableDoubleConsumer;
import org.apache.commons.lang3.function.FailableDoubleSupplier;
import org.apache.commons.lang3.function.FailableIntConsumer;
import org.apache.commons.lang3.function.FailableIntSupplier;
import org.apache.commons.lang3.function.FailableLongConsumer;
import org.apache.commons.lang3.function.FailableLongSupplier;
import org.apache.commons.lang3.function.FailablePredicate;
import org.apache.commons.lang3.function.FailableRunnable;
import org.apache.commons.lang3.function.FailableShortSupplier;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.stream.Streams;
import org.apache.commons.lang3.stream.Streams.FailableStream;
import org.apache.commons.lang3.function.FailableFunction;
import org.joytools.commons.util.FailableOpenable;
import org.joytools.commons.util.Openable;

/**
 * This class provides utility functions, and classes for working with the {@code java.util.function} package, or more
 * generally, with Java 8 lambdas. More specifically, it attempts to address the fact that lambdas are supposed not to
 * throw Exceptions, at least not checked Exceptions, AKA instances of {@link Exception}. This enforces the use of
 * constructs like:
 *
 * <pre>
 * Consumer&lt;java.lang.reflect.Method&gt; consumer = m -&gt; {
 *     try {
 *         m.invoke(o, args);
 *     } catch (Throwable t) {
 *         throw Failable.rethrow(t);
 *     }
 * };
 * </pre>
 *
 * <p>
 * By replacing a {@link java.util.function.Consumer Consumer&lt;O&gt;} with a {@link FailableConsumer
 * FailableConsumer&lt;O,? extends Throwable&gt;}, this can be written like follows:
 * </p>
 *
 * <pre>
 * Functions.accept((m) -&gt; m.invoke(o, args));
 * </pre>
 *
 * <p>
 * Obviously, the second version is much more concise and the spirit of Lambda expressions is met better than the second
 * version.
 * </p>
 *
 * @since 3.11
 */
public abstract class Failable {

    /**
     * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
     *
     * @param consumer the consumer to consume
     * @param object1 the first object to consume by {@code consumer}
     * @param object2 the second object to consume by {@code consumer}
     * @param <T> the type of the first argument the consumer accepts
     * @param <U> the type of the second argument the consumer accepts
     * @param <E> the type of checked exception the consumer may throw
     */
    public <T, U, E extends Throwable> void accept(final FailableBiConsumer<T, U, E> consumer, final T object1,
        final U object2) {
        Objects.requireNonNull(consumer, "Consumer");
        run(() -> consumer.accept(object1, object2));
    }

    /**
     * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
     *
     * @param consumer the consumer to consume
     * @param object the object to consume by {@code consumer}
     * @param <T> the type the consumer accepts
     * @param <E> the type of checked exception the consumer may throw
     */
    public <T, E extends Throwable> void accept(final FailableConsumer<T, E> consumer, final T object) {
        Objects.requireNonNull(consumer, "Consumer");
        run(() -> consumer.accept(object));
    }

    /**
     * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
     *
     * @param consumer the consumer to consume
     * @param value the value to consume by {@code consumer}
     * @param <E> the type of checked exception the consumer may throw
     */
    public <E extends Throwable> void accept(final FailableDoubleConsumer<E> consumer, final double value) {
        Objects.requireNonNull(consumer, "Consumer");
        run(() -> consumer.accept(value));
    }

    /**
     * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
     *
     * @param consumer the consumer to consume
     * @param value the value to consume by {@code consumer}
     * @param <E> the type of checked exception the consumer may throw
     */
    public <E extends Throwable> void accept(final FailableIntConsumer<E> consumer, final int value) {
        Objects.requireNonNull(consumer, "Consumer");
        run(() -> consumer.accept(value));
    }

    /**
     * Consumes a consumer and rethrows any exception as a {@link RuntimeException}.
     *
     * @param consumer the consumer to consume
     * @param value the value to consume by {@code consumer}
     * @param <E> the type of checked exception the consumer may throw
     */
    public <E extends Throwable> void accept(final FailableLongConsumer<E> consumer, final long value) {
        Objects.requireNonNull(consumer, "Consumer");
        run(() -> consumer.accept(value));
    }

    /**
     * Applies a function and rethrows any exception as a {@link RuntimeException}.
     *
     * @param function the function to apply
     * @param input1 the first input to apply {@code function} on
     * @param input2 the second input to apply {@code function} on
     * @param <T> the type of the first argument the function accepts
     * @param <U> the type of the second argument the function accepts
     * @param <R> the return type of the function
     * @param <E> the type of checked exception the function may throw
     * @return the value returned from the function
     */
    public <T, U, R, E extends Throwable> R apply(final FailableBiFunction<T, U, R, E> function, final T input1,
        final U input2) {
        Objects.requireNonNull(function, "Function");
        return get(() -> function.apply(input1, input2));
    }

    /**
     * Applies a function and rethrows any exception as a {@link RuntimeException}.
     *
     * @param function the function to apply
     * @param input the input to apply {@code function} on
     * @param <T> the type of the argument the function accepts
     * @param <R> the return type of the function
     * @param <E> the type of checked exception the function may throw
     * @return the value returned from the function
     */
    public <T, R, E extends Throwable> R apply(final FailableFunction<T, R, E> function, final T input) {
        Objects.requireNonNull(function, "Function");
        return get(() -> function.apply(input));
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param <E>
     * @param processor
     * @param input
     * @return 
     */
    public <T, R, E extends Exception> R process(final FailableProcessor<T, R, E> processor, final T input) {
        Objects.requireNonNull(processor, "Processor");
        return get(() -> processor.process(input));
    }

    /**
     * Applies a function and rethrows any exception as a {@link RuntimeException}.
     *
     * @param function the function to apply
     * @param left the first input to apply {@code function} on
     * @param right the second input to apply {@code function} on
     * @param <E> the type of checked exception the function may throw
     * @return the value returned from the function
     */
    public <E extends Throwable> double applyAsDouble(final FailableDoubleBinaryOperator<E> function,
        final double left, final double right) {
        Objects.requireNonNull(function, "Function");
        return getAsDouble(() -> function.applyAsDouble(left, right));
    }

    /**
     * Converts the given {@link FailableBiConsumer} into a standard {@link BiConsumer}.
     *
     * @param <T> the type of the first argument of the consumers
     * @param <U> the type of the second argument of the consumers
     * @param consumer a failable {@link BiConsumer}
     * @return a standard {@link BiConsumer}
     */
    public <T, U> BiConsumer<T, U> asBiConsumer(final FailableBiConsumer<T, U, ?> consumer) {
        Objects.requireNonNull(consumer, "Consumer");
        return (input1, input2) -> accept(consumer, input1, input2);
    }

    /**
     * Converts the given {@link FailableBiFunction} into a standard {@link BiFunction}.
     *
     * @param <T> the type of the first argument of the input of the functions
     * @param <U> the type of the second argument of the input of the functions
     * @param <R> the type of the output of the functions
     * @param function a {@link FailableBiFunction}
     * @return a standard {@link BiFunction}
     */
    public <T, U, R> BiFunction<T, U, R> asBiFunction(final FailableBiFunction<T, U, R, ?> function) {
        Objects.requireNonNull(function, "Function");
        return asFunction2(function);
    }

    /**
     * Converts the given {@link FailableBiFunction} into a standard {@link BiFunction}.
     *
     * @param <T> the type of the first argument of the input of the functions
     * @param <U> the type of the second argument of the input of the functions
     * @param <R> the type of the output of the functions
     * @param function a {@link FailableBiFunction}
     * @return a standard {@link BiFunction}
     */
    public <T, U, R> Function2<T, U, R> asFunction2(final FailableBiFunction<T, U, R, ?> function) {
        Objects.requireNonNull(function, "Function");
        return (input1, input2) -> apply(function, input1, input2);
    }

    /**
     * Converts the given {@link FailableBiPredicate} into a standard {@link BiPredicate}.
     *
     * @param <T> the type of the first argument used by the predicates
     * @param <U> the type of the second argument used by the predicates
     * @param predicate a {@link FailableBiPredicate}
     * @return a standard {@link BiPredicate}
     */
    public <T, U> BiPredicate<T, U> asBiPredicate(final FailableBiPredicate<T, U, ?> predicate) {
        Objects.requireNonNull(predicate, "Predicate");
        return (input1, input2) -> test(predicate, input1, input2);
    }

    /**
     * Converts the given {@link FailableCallable} into a standard {@link Callable}.
     *
     * @param <V> the type used by the callables
     * @param callable a {@link FailableCallable}
     * @return a standard {@link Callable}
     */
    public <V> Callable<V> asCallable(final FailableCallable<V, ?> callable) {
        Objects.requireNonNull(callable, "Callable");
        return () -> call(callable);
    }

    /**
     * Converts the given {@link FailableConsumer} into a standard {@link Consumer}.
     *
     * @param <T> the type used by the consumers
     * @param consumer a {@link FailableConsumer}
     * @return a standard {@link Consumer}
     */
    public <T> Consumer<T> asConsumer(final FailableConsumer<T, ?> consumer) {
        Objects.requireNonNull(consumer, "Consumer");
        return input -> accept(consumer, input);
    }

    /**
     * Converts the given {@link FailableFunction} into a standard {@link Function}.
     *
     * @param <T> the type of the input of the functions
     * @param <R> the type of the output of the functions
     * @param function a {code FailableFunction}
     * @return a standard {@link Function}
     */
    public <T, R> Function1<T, R> asFunction(final FailableFunction<T, R, ?> function) {
        Objects.requireNonNull(function, "Function");
        return input -> apply(function, input);
    }

    /**
     * Converts the given {@link FailablePredicate} into a standard {@link Predicate}.
     *
     * @param <T> the type used by the predicates
     * @param predicate a {@link FailablePredicate}
     * @return a standard {@link Predicate}
     */
    public <T> Predicate<T> asPredicate(final FailablePredicate<T, ?> predicate) {
        Objects.requireNonNull(predicate, "Predicate");
        return input -> test(predicate, input);
    }

    /**
     * Converts the given {@link FailableProcessor} into a standard {@link Processor}.
     *
     * @param <T> the type of the input of the processors
     * @param <R> the type of the output of the processors
     * @param processor a {@link FailableProcessor}
     * @return a standard {@link Processor}
     */
    public <T, R> Processor<T, R> asProcessor(final FailableProcessor<T, R, ? extends Throwable> processor) {
        Objects.requireNonNull(processor, "Processor");
        return input -> process(processor, input);
    }

    /**
     * Converts the given {@link FailableRunnable} into a standard {@link Runnable}.
     *
     * @param runnable a {@link FailableRunnable}
     * @return a standard {@link Runnable}
     */
    public Runnable asRunnable(final FailableRunnable<?> runnable) {
        Objects.requireNonNull(runnable, "Runnable");
        return () -> run(runnable);
    }

    /**
     * Converts the given {@link FailableSupplier} into a standard {@link Supplier}.
     *
     * @param <T> the type supplied by the suppliers
     * @param supplier a {@link FailableSupplier}
     * @return a standard {@link Supplier}
     */
    public <T> Supplier<T> asSupplier(final FailableSupplier<T, ?> supplier) {
        Objects.requireNonNull(supplier, "Supplier");
        return () -> get(supplier);
    }

    /**
     * 
     * @param openable
     * @return 
     */
    public Openable asOpenable(final FailableOpenable<?> openable) {
        Objects.requireNonNull(openable, "Openable");
        if (openable instanceof Openable op) {
            return op;
        }
        return new Openable() {
            @Override
            public void open() throws RuntimeException {
                run(openable::open);
            }

            @Override
            public boolean isOpenInvoked() {
                return openable.isOpenInvoked();
            }

            @Override
            public void close() throws RuntimeException {
                run(openable::close);
            }
        };
    }

    /**
     * 
     * @param autocloseable
     * @return 
     */
    public AutoCloseable asAutoCloseable(final FailableAutoCloseable<?> autocloseable) {
        Objects.requireNonNull(autocloseable, "AutoCloseable");
        return () -> {
            call(() -> {
                autocloseable.close();
                return null;
            });
        };
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public <T> Iterator<T> asIterator(final FailableIterator<T, ?> iterator) {
        Objects.requireNonNull(iterator, "Iterator");
        final Supplier<Boolean> hasNextSupp = asSupplier(iterator::hasNext);
        final Supplier<T> nextSupp = asSupplier(iterator::next);
        final Runnable remover = asRunnable(iterator::remove);
        return new AbstractIterator<T>() {
            @Override
            protected T computeNext() {
                if (hasNextSupp.get()) {
                    return nextSupp.get();
                }
                return endOfData();
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param iterable
     * @return 
     */
    public <T, E extends Throwable> Iterable<T> asIterable(final FailableIterable<T, E> iterable) {
        Objects.requireNonNull(iterable, "Iterable");
        final Supplier<FailableIterator<T, E>> supplier = asSupplier(iterable::iterator);
        return () -> asIterator(supplier.get());
    }

    /**
     * Calls a callable and rethrows any exception as a {@link RuntimeException}.
     *
     * @param callable the callable to call
     * @param <V> the return type of the callable
     * @param <E> the type of checked exception the callable may throw
     * @return the value returned from the callable
     */
    public <V, E extends Throwable> V call(final FailableCallable<V, E> callable) {
        return get(callable::call);
    }

    /**
     * Invokes a supplier, and returns the result.
     *
     * @param supplier The supplier to invoke.
     * @param <T> The suppliers output type.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The object, which has been created by the supplier
     */
    public <T, E extends Throwable> T get(final FailableSupplier<T, E> supplier) {
        try {
            return supplier.get();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Invokes a boolean supplier, and returns the result.
     *
     * @param supplier The boolean supplier to invoke.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The boolean, which has been created by the supplier
     */
    public <E extends Throwable> boolean getAsBoolean(final FailableBooleanSupplier<E> supplier) {
        try {
            return supplier.getAsBoolean();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Invokes a double supplier, and returns the result.
     *
     * @param supplier The double supplier to invoke.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The double, which has been created by the supplier
     */
    public <E extends Throwable> double getAsDouble(final FailableDoubleSupplier<E> supplier) {
        try {
            return supplier.getAsDouble();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Invokes an int supplier, and returns the result.
     *
     * @param supplier The int supplier to invoke.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The int, which has been created by the supplier
     */
    public <E extends Throwable> int getAsInt(final FailableIntSupplier<E> supplier) {
        try {
            return supplier.getAsInt();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Invokes a long supplier, and returns the result.
     *
     * @param supplier The long supplier to invoke.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The long, which has been created by the supplier
     */
    public <E extends Throwable> long getAsLong(final FailableLongSupplier<E> supplier) {
        try {
            return supplier.getAsLong();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Invokes a short supplier, and returns the result.
     *
     * @param supplier The short supplier to invoke.
     * @param <E> The type of checked exception, which the supplier can throw.
     * @return The short, which has been created by the supplier
     */
    public <E extends Throwable> short getAsShort(final FailableShortSupplier<E> supplier) {
        try {
            return supplier.getAsShort();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Rethrows a {@link Throwable} as an unchecked exception. If the argument is already unchecked, namely a
     * {@link RuntimeException} or {@link Error} then the argument will be rethrown without modification. If the
     * exception is {@link IOException} then it will be wrapped into a {@link UncheckedIOException}. In every other
     * cases the exception will be wrapped into a {@code
     * UndeclaredThrowableException}
     *
     * <p>
     * Note that there is a declared return type for this method, even though it never returns. The reason for that is
     * to support the usual pattern:
     * </p>
     *
     * <pre>
     * throw rethrow(myUncheckedException);
     * </pre>
     *
     * <p>
     * instead of just calling the method. This pattern may help the Java compiler to recognize that at that point an
     * exception will be thrown and the code flow analysis will not demand otherwise mandatory commands that could
     * follow the method call, like a {@code return} statement from a value returning method.
     * </p>
     *
     * @param throwable The throwable to rethrow possibly wrapped into an unchecked exception
     * @return Never returns anything, this method never terminates normally.
     */
    public RuntimeException rethrow(final Throwable throwable) {
        rethrowConsumer().accept(throwable);
        throw new UnsupportedOperationException("Rethrow Consumer did not worked properly");
    }

    /**
     * Runs a runnable and rethrows any exception as a {@link RuntimeException}.
     *
     * @param runnable The runnable to run
     * @param <E> the type of checked exception the runnable may throw
     */
    public <E extends Throwable> void run(final FailableRunnable<E> runnable) {
        try {
            runnable.run();
        } catch (final Throwable t) {
            throw rethrow(t);
        }
    }

    /**
     * Converts the given collection into a {@link FailableStream}. The {@link FailableStream} consists of the
     * collections elements. Shortcut for
     *
     * <pre>
     * Functions.stream(collection.stream());
     * </pre>
     *
     * @param collection The collection, which is being converted into a {@link FailableStream}.
     * @param <E> The collections element type. (In turn, the result streams element type.)
     * @return The created {@link FailableStream}.
     */
    public <E> FailableStream<E> stream(final Collection<E> collection) {
        return new FailableStream<>(collection.stream());
    }

    /**
     * Converts the given stream into a {@link FailableStream}. The {@link FailableStream} consists of the same
     * elements, than the input stream. However, failable lambdas, like {@link FailablePredicate},
     * {@link FailableFunction}, and {@link FailableConsumer} may be applied, rather than {@link Predicate},
     * {@link Function}, {@link Consumer}, etc.
     *
     * @param stream The stream, which is being converted into a {@link FailableStream}.
     * @param <T> The streams element type.
     * @return The created {@link FailableStream}.
     */
    public <T> FailableStream<T> stream(final Stream<T> stream) {
        return new FailableStream<>(stream);
    }

    /**
     * Tests a predicate and rethrows any exception as a {@link RuntimeException}.
     *
     * @param predicate the predicate to test
     * @param object1 the first input to test by {@code predicate}
     * @param object2 the second input to test by {@code predicate}
     * @param <T> the type of the first argument the predicate tests
     * @param <U> the type of the second argument the predicate tests
     * @param <E> the type of checked exception the predicate may throw
     * @return the boolean value returned by the predicate
     */
    public <T, U, E extends Throwable> boolean test(final FailableBiPredicate<T, U, E> predicate,
        final T object1, final U object2) {
        Objects.requireNonNull(predicate, "Predicate");
        return getAsBoolean(() -> predicate.test(object1, object2));
    }

    /**
     * Tests a predicate and rethrows any exception as a {@link RuntimeException}.
     *
     * @param predicate the predicate to test
     * @param object the input to test by {@code predicate}
     * @param <T> the type of argument the predicate tests
     * @param <E> the type of checked exception the predicate may throw
     * @return the boolean value returned by the predicate
     */
    public <T, E extends Throwable> boolean test(final FailablePredicate<T, E> predicate, final T object) {
        Objects.requireNonNull(predicate, "Predicate");
        return getAsBoolean(() -> predicate.test(object));
    }

    /**
     * A simple try-with-resources implementation, that can be used, if your objects do not implement the
     * {@link AutoCloseable} interface. The method executes the {@code action}. The method guarantees, that <em>all</em>
     * the {@code resources} are being executed, in the given order, afterwards, and regardless of success, or failure.
     * If either the original action, or any of the resource action fails, then the <em>first</em> failure (AKA
     * {@link Throwable}) is rethrown. Example use:
     *
     * <pre>
     * final FileInputStream fis = new FileInputStream("my.file");
     * Functions.tryWithResources(useInputStream(fis), null, () -&gt; fis.close());
     * </pre>
     *
     * @param action The action to execute. This object <em>will</em> always be invoked.
     * @param errorHandler An optional error handler, which will be invoked finally, if any error occurred. The error
     *        handler will receive the first error, AKA {@link Throwable}.
     * @param resources The resource actions to execute. <em>All</em> resource actions will be invoked, in the given
     *        order. A resource action is an instance of {@link FailableRunnable}, which will be executed.
     * @see #tryWithResources(FailableRunnable, FailableRunnable...)
     */
    @SafeVarargs
    public final void tryWithResources(final FailableRunnable<? extends Throwable> action,
        final FailableConsumer<Throwable, ? extends Throwable> errorHandler,
        final FailableRunnable<? extends Throwable>... resources) {
        Objects.requireNonNull(action, "Action");
        final FailableConsumer<Throwable, ? extends Throwable> actualErrorHandler;
        if (errorHandler == null) {
            actualErrorHandler = x -> rethrowConsumer().accept(x);
        } else {
            actualErrorHandler = errorHandler;
        }
        Streams.of(resources).forEach(r -> Objects.requireNonNull(r, "runnable"));
        Throwable th = null;
        try {
            action.run();
        } catch (final Throwable t) {
            th = t;
        }
        if (resources != null) {
            for (final FailableRunnable<?> runnable : resources) {
                try {
                    runnable.run();
                } catch (final Throwable t) {
                    if (th == null) {
                        th = t;
                    }
                }
            }
        }
        if (th != null) {
            try {
                actualErrorHandler.accept(th);
            } catch (final Throwable t) {
                throw rethrow(t);
            }
        }
    }

    /**
     * A simple try-with-resources implementation, that can be used, if your objects do not implement the
     * {@link AutoCloseable} interface. The method executes the {@code action}. The method guarantees, that <em>all</em>
     * the {@code resources} are being executed, in the given order, afterwards, and regardless of success, or failure.
     * If either the original action, or any of the resource action fails, then the <em>first</em> failure (AKA
     * {@link Throwable}) is rethrown. Example use:
     *
     * <pre>
     * final FileInputStream fis = new FileInputStream("my.file");
     * Functions.tryWithResources(useInputStream(fis), () -&gt; fis.close());
     * </pre>
     *
     * @param action The action to execute. This object <em>will</em> always be invoked.
     * @param resources The resource actions to execute. <em>All</em> resource actions will be invoked, in the given
     *        order. A resource action is an instance of {@link FailableRunnable}, which will be executed.
     * @see #tryWithResources(FailableRunnable, FailableConsumer, FailableRunnable...)
     */
    @SafeVarargs
    public final void tryWithResources(final FailableRunnable<? extends Throwable> action,
        final FailableRunnable<? extends Throwable>... resources) {
        tryWithResources(action, null, resources);
    }
    
    /**
     * 
     * @return 
     */
    abstract protected Consumer<Throwable> rethrowConsumer();

}
