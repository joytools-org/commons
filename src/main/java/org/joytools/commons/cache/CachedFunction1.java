/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.cache;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import io.vavr.Function1;
import java.util.Objects;
import java.util.function.Function;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <R>
 */
public class CachedFunction1<T, R> implements Function1<T, R> {
    
    /**
     * 
     * @param func
     * @param builder 
     */
    public CachedFunction1(final Function<T, R> func, final CacheBuilder builder) {
        checkNotNull(func, "Function");
        checkArgument(!(func instanceof CachedFunction1), "Function is already cached");
        final CacheBuilder<Object, R> cb = builder != null ? builder : (CacheBuilder<Object, R>)CacheBuilder.newBuilder().maximumSize(0);
        final com.google.common.base.Function<T, R> guavaFunc = func::apply;
        cache = cb.build(CacheLoader.from(guavaFunc));
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param func
     * @param builder
     * @return 
     */
    public static <T, R> CachedFunction1<T, R> of(final Function<T, R> func, final CacheBuilder builder) {
        return new CachedFunction1(func, builder);
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param func
     * @param cacheSpecs
     * @return 
     */
    public static <T, R> CachedFunction1<T, R> of(final Function<T, R> func, final CacheBuilderSpec cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedFunction1(func, null);
        }
        return new CachedFunction1(func, (CacheBuilder<T, R>)CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param func
     * @param cacheSpecs
     * @return 
     */
    public static <T, R> CachedFunction1<T, R> of(final Function<T, R> func, final String cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedFunction1(func, null);
        }
        return new CachedFunction1(func, (CacheBuilder<T, R>)CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @return 
     */
    public Cache<T, R> cache() {
        return cache;
    }
    
    /**
     * 
     */
    private final LoadingCache<T, R> cache;
            
    /**
     * 
     * @param t
     * @return 
     */
    @Override
    public R apply(final T t) {
        Objects.requireNonNull(t, "Null arguments are not allowed with cached functions");
        try {
            return cache.getUnchecked(t);
        } catch (final UncheckedExecutionException ex) {
            return ExceptionUtils.wrapAndThrow(ex.getCause());
        }
    }
    
}
