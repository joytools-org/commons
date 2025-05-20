/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.cache;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheBuilderSpec;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import static io.vavr.API.Tuple;
import io.vavr.Function2;
import io.vavr.Tuple2;
import java.util.Objects;
import java.util.function.BiFunction;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 * @param <T1>
 * @param <T2>
 * @param <R>
 */
public class CachedFunction2<T1, T2, R> implements Function2<T1, T2, R> {
    
    /**
     * 
     * @param func
     * @param builder 
     */
    public CachedFunction2(final BiFunction<T1, T2, R> func, final CacheBuilder builder) {
        Objects.requireNonNull(func, "Function");
        checkArgument(!(func instanceof CachedFunction2), "Function is already cached");
        final CacheBuilder<Object, R> cb = builder != null ? builder : (CacheBuilder<Object, R>)CacheBuilder.newBuilder().maximumSize(0);
        final com.google.common.base.Function<Tuple2<T1, T2>, R> guavaFunc = t -> func.apply(t._1, t._2);
        cache = cb.build(CacheLoader.from(guavaFunc));
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <R>
     * @param func
     * @param builder
     * @return 
     */
    public static <T1, T2, R> CachedFunction2<T1, T2, R> of(final Function2<T1, T2, R> func, final CacheBuilder builder) {
        return new CachedFunction2(func, builder);
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <R>
     * @param func
     * @param cacheSpecs
     * @return 
     */
    public static <T1, T2, R> CachedFunction2<T1, T2, R> of(final Function2<T1, T2, R> func, final CacheBuilderSpec cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedFunction2(func, null);
        }
        return new CachedFunction2(func, CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <R>
     * @param func
     * @param cacheSpecs
     * @return 
     */
    public static <T1, T2, R> CachedFunction2<T1, T2, R> of(final Function2<T1, T2, R> func, final String cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedFunction2(func, null);
        }
        return new CachedFunction2(func, CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @return 
     */
    public Cache<Tuple2<T1, T2>, R> cache() {
        return cache;
    }
    
    /**
     * 
     */
    private final LoadingCache<Tuple2<T1, T2>, R> cache;
            
    /**
     * 
     * @return 
     */
    @Override
    public R apply(final T1 t1, final T2 t2) {
        Objects.requireNonNull(t1, "Null arguments are not allowed with cached functions");
        Objects.requireNonNull(t2, "Null arguments are not allowed with cached functions");
        try {
            return cache.getUnchecked(Tuple(t1, t2));
        } catch (final UncheckedExecutionException ex) {
            return ExceptionUtils.wrapAndThrow(ex.getCause());
        }
    }
    
}
