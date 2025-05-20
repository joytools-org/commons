/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import java.util.concurrent.atomic.AtomicReference;
import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;
import org.joytools.commons.accessors.FixedSizeListMutator;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class CoreFixedSizeMutators {

    /**
     * 
     * @param <T>
     * @param <R>
     * @return 
     */
    public static <T, R> FixedSizeIndexedMapMutator<T, R> unbound() {
        return UnboundFixedSizeIndexedMapMutator.instance();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param elements
     * @return
     */
    public static <K, V> FixedSizeListMutator<V> of(final V[] elements) {
        return new ArrayFixedSizeListMutator<>(elements);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    public static <K, V> FixedSizeIndexedMapMutator<K, V> of(final K key, final AtomicReference<V> value) {
        return new AtomicReferenceFixedSizeMutator<>(key, value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param value
     * @return 
     */
    public static <V> FixedSizeListMutator<V> of(final AtomicReference<V> value) {
        return new AtomicReferenceFixedSizeMutator<>(StringUtils.EMPTY, value);
    }
    
    
}
