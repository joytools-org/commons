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
import io.vavr.Function3;
import io.vavr.Tuple3;
import java.util.Objects;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 * @param <T1>
 * @param <T2>
 * @param <T3>
 * @param <R>
 */
public class CachedFunction3<T1, T2, T3, R> implements Function3<T1, T2, T3, R> {
    
    /**
     * 
     * @param func
     * @param builder 
     */
    public CachedFunction3(final Function3<T1, T2, T3, R> func, final CacheBuilder builder) {
        Objects.requireNonNull(func, "Function");
        checkArgument(!(func instanceof CachedFunction3), "Function is already cached");
        final CacheBuilder<Object, R> cb = builder != null ? builder : (CacheBuilder<Object, R>)CacheBuilder.newBuilder().maximumSize(0);
        final com.google.common.base.Function<Tuple3<T1, T2, T3>, R> guavaFunc = t -> func.apply(t._1, t._2, t._3);
        cache = cb.build(CacheLoader.from(guavaFunc));
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <R>
     * @param func
     * @param builder
     * @return 
     */
    public static <T1, T2, T3, R> CachedFunction3<T1, T2, T3, R> of(final Function3<T1, T2, T3, R> func, final CacheBuilder builder) {
        return new CachedFunction3(func, builder);
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <R>
     * @param func
     * @param cacheSpecs
     * @return 
     */
    public static <T1, T2, T3, R> CachedFunction3<T1, T2, T3, R> of(final Function3<T1, T2, T3, R> func, final CacheBuilderSpec cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedFunction3(func, null);
        }
        return new CachedFunction3(func, CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <R>
     * @param func
     * @param cacheSpecs
     * @return 
     */
    public static <T1, T2, T3, R> CachedFunction3<T1, T2, T3, R> of(final Function3<T1, T2, T3, R> func, final String cacheSpecs) {
        if (cacheSpecs == null) {
            return new CachedFunction3(func, null);
        }
        return new CachedFunction3(func, CacheBuilder.from(cacheSpecs));
    }

    /**
     * 
     * @return 
     */
    public Cache<Tuple3<T1, T2, T3>, R> cache() {
        return cache;
    }
    
    /**
     * 
     */
    private final LoadingCache<Tuple3<T1, T2, T3>, R> cache;
            
    /**
     * 
     * @return 
     */
    @Override
    public R apply(final T1 t1, final T2 t2, final T3 t3) {
        Objects.requireNonNull(t1, "Null arguments are not allowed with cached functions");
        Objects.requireNonNull(t2, "Null arguments are not allowed with cached functions");
        Objects.requireNonNull(t3, "Null arguments are not allowed with cached functions");
        try {
            return cache.getUnchecked(Tuple(t1, t2, t3));
        } catch (final UncheckedExecutionException ex) {
            return ExceptionUtils.wrapAndThrow(ex.getCause());
        }
    }
    
}
