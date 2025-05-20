/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import java.util.concurrent.atomic.AtomicReference;
import static org.joytools.commons.accessors.MutatorImplUtils.checkNotImmutable;
import org.joytools.commons.accessors.core.CoreFixedSizeMutators;
import org.joytools.commons.accessors.collection.CollectionFixedSizeMutators;

/**
 *
 * @author AndreaR
 */
public class FixedSizeMutators {
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> FixedSizeIndexedMapMutator<K, V> unbound() {
        return CoreFixedSizeMutators.unbound();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param elements
     * @return 
     */
    public static <K, V> FixedSizeListMutator<V> of(final V[] elements) {
        return CoreFixedSizeMutators.of(elements);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param list
     * @return 
     */
    public static <K, V> FixedSizeListMutator<V> of(final java.util.List<V> list) {
        return CollectionFixedSizeMutators.of(checkNotImmutable(list));
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> FixedSizeMapMutator<K, V> of(final java.util.Map<K, V> map) {
        return CollectionFixedSizeMutators.of(checkNotImmutable(map));
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> FixedSizeIndexedMapMutator<K, V> of(final java.util.Map.Entry<K, V> map) {
        return CollectionFixedSizeMutators.of(checkNotImmutable(map));
    }
    
    /**
     * 
     * @param <V>
     * @param ref
     * @return 
     */
    public static <V> FixedSizeListMutator<V> of(final AtomicReference<V> ref) {
        return CoreFixedSizeMutators.of(ref);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param ref
     * @return 
     */
    public static <K, V> FixedSizeIndexedMapMutator<K, V> of(final K key, final AtomicReference<V> ref) {
        return CoreFixedSizeMutators.of(key, ref);
    }

}
