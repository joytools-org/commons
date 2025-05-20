/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import io.vavr.control.Either;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import org.apache.commons.lang3.concurrent.ConcurrentInitializer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 */
public class AtomicUtils {

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> FailableConsumer<T, Exception> defaultCloser() {
        return (FailableConsumer<T, Exception>)DEFAULT_CLOSER;
    }

    /**
     *
     */
    static final FailableConsumer<Object, Exception> DEFAULT_CLOSER = x -> {
        if (x == null) {
            return;
        }
        if (x instanceof AutoCloseable ac) {
            ac.close();
            return;
        }
        try {
            MethodUtils.invokeExactMethod(x, "close");
            return;
        } catch (final Exception e) {
            // Ignore
        }
    };

    /**
     *
     * @param <T>
     * @param <E>
     * @param supplier
     * @return
     */
    public static <T, E extends Exception> FailableSupplier<Either<E, T>, E> toEither(final FailableSupplier<T, E> supplier) {
        Objects.requireNonNull(supplier, "FailableSupplier");
        return () -> {
            try {
                return Right(supplier.get());
            } catch (final Exception ex) {
                return Left((E) ex);
            }
        };
    }

    /**
     *
     * @param <T>
     * @param <E>
     * @param supplier
     * @return
     */
    public static <T, E extends Exception> Supplier<Either<E, T>> toEither(final Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "Supplier");
        return () -> {
            try {
                return Right(supplier.get());
            } catch (final Exception ex) {
                return Left((E) ex);
            }
        };
    }

    /**
     *
     * @param <T>
     * @param <E>
     * @param callable
     * @return
     */
    public static <T, E extends Exception> Callable<Either<E, T>> toEither(final Callable<T> callable) {
        Objects.requireNonNull(callable, "Callable");
        return () -> {
            try {
                return Right(callable.call());
            } catch (final Exception ex) {
                return Left((E) ex);
            }
        };
    }

    /**
     *
     * @param <T>
     * @param <E>
     * @param initializer
     * @return
     */
    public static <T, E extends Exception> Supplier<T> toSupplier(final ConcurrentInitializer<Either<E, T>> initializer) {
        return () -> {
            Exception cause;
            try {
                final Either<E, T> e = initializer.get();
                if (e.isRight()) {
                    return e.get();
                }
                cause = e.getLeft();
            } catch (final Exception ex) {
                cause = ex;
            }
            throw new IllegalStateException("Initialization failed", ExceptionUtils.unwrapped(cause));
        };
    }

}
