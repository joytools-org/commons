/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.cache;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.util.Objects;
import java.util.function.Supplier;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 * @param <R>
 */
public final class CachedSupplier<R> implements Supplier<R> {
    
    /**
     * 
     * @param supp
     * @param builder 
     */
    CachedSupplier(final Supplier<R> supp, final CacheBuilder<Object, R> builder) {
        Objects.requireNonNull(supp, "Supplier");
        checkArgument(!(supp instanceof CachedSupplier), "Supplier is already cached");
        final CacheBuilder<Object, R> cb = builder != null ? builder : (CacheBuilder<Object, R>)CacheBuilder.newBuilder().maximumSize(0);
        final com.google.common.base.Supplier<R> guavaSupp = () -> supp.get();
        cache = cb.build(CacheLoader.from(guavaSupp));
    }

    /**
     * 
     * @return 
     */
    public Cache<Object, R> cache() {
        return cache;
    }
    
    /**
     * 
     */
    private final LoadingCache<Object, R> cache;
            
    /**
     * 
     * @return 
     */
    @Override
    public R get() {
        try {
            return cache.getUnchecked(0);
        } catch (final UncheckedExecutionException ex) {
            return ExceptionUtils.wrapAndThrow(ex.getCause());
        }
    }
    
}
