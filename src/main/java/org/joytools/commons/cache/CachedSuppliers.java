/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import org.apache.commons.lang3.function.FailableSupplier;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 */
public class CachedSuppliers {
    
    /**
     * 
     * @param <R>
     * @param supplier
     * @param builder
     * @return 
     */
    public static <R> CachedSupplier<R> of(final Supplier<R> supplier, final CacheBuilder<Object, Object> builder) {
        return new CachedSupplier(supplier, builder);
    }

    /**
     * 
     * @param <R>
     * @param supplier
     * @param cacheSpecs
     * @return 
     */
    public static <R> CachedSupplier<R> of(final Supplier<R> supplier, final CacheBuilderSpec cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedSupplier(supplier, null);
        }
        return new CachedSupplier(supplier, (CacheBuilder<Object, R>)CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @param <R>
     * @param supplier
     * @param cacheSpecs
     * @return 
     */
    public static <R> CachedSupplier<R> of(final Supplier<R> supplier, final String cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedSupplier(supplier, null);
        }
        return new CachedSupplier(supplier, (CacheBuilder<Object, R>)CacheBuilder.from(cacheSpecs));
    }
    
    /**
     * 
     * @param <R>
     * @param callable
     * @param builder
     * @return 
     */
    public static <R> CachedSupplier<R> ofChecked(final Callable<R> callable, final CacheBuilder<Object, Object> builder) {
        return new CachedSupplier(toSupplier(callable), builder);
    }

    /**
     * 
     * @param <R>
     * @param callable
     * @param cacheSpecs
     * @return 
     */
    public static <R> CachedSupplier<R> ofChecked(final Callable<R> callable, final CacheBuilderSpec cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedSupplier(toSupplier(callable), null);
        }
        return new CachedSupplier(toSupplier(callable), (CacheBuilder<Object, R>)CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @param <R>
     * @param callable
     * @param cacheSpecs
     * @return 
     */
    public static <R> CachedSupplier<R> ofChecked(final Callable<R> callable, final String cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedSupplier(toSupplier(callable), null);
        }
        return new CachedSupplier(toSupplier(callable), (CacheBuilder<Object, R>)CacheBuilder.from(cacheSpecs));
    }
    
    /**
     * 
     * @param <R>
     * @param <E>
     * @param supplier
     * @param builder
     * @return 
     */
    public static <R, E extends Exception> CachedSupplier<R> ofFailable(final FailableSupplier<R, E> supplier, final CacheBuilder<Object, Object> builder) {
        return new CachedSupplier(toSupplier(supplier), builder);
    }

    /**
     * 
     * @param <R>
     * @param <E>
     * @param supplier
     * @param cacheSpecs
     * @return 
     */
    public static <R, E extends Exception> CachedSupplier<R> ofFailable(final FailableSupplier<R, E> supplier, final CacheBuilderSpec cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedSupplier(toSupplier(supplier), null);
        }
        return new CachedSupplier(toSupplier(supplier), (CacheBuilder<Object, R>)CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @param <R>
     * @param <E>
     * @param supplier
     * @param cacheSpecs
     * @return 
     */
    public static <R, E extends Exception> CachedSupplier<R> ofFailable(final FailableSupplier<R, E> supplier, final String cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedSupplier(toSupplier(supplier), null);
        }
        return new CachedSupplier(toSupplier(supplier), (CacheBuilder<Object, R>)CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @param <V>
     * @param callable
     * @return 
     */
    static <V> Supplier<V> toSupplier(final Callable<V> callable) {
        Objects.requireNonNull(callable, "Callable");
        return () -> {
            try {
                return callable.call();
            } catch (final Exception e) {
                return ExceptionUtils.wrapAndThrow(e);
            }
        };
    }

    /**
     * 
     * @param <V>
     * @param <E>
     * @param supplier
     * @return 
     */
    static <V, E extends Exception> Supplier<V> toSupplier(final FailableSupplier<V, E> supplier) {
        Objects.requireNonNull(supplier, "FailableSupplier");
        return () -> {
            try {
                return supplier.get();
            } catch (final Exception e) {
                return ExceptionUtils.wrapAndThrow(e);
            }
        };
    }
    
}
