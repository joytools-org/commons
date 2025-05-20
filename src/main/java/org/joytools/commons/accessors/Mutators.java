/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import com.google.common.collect.ImmutableList;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.joytools.commons.accessors.MutatorImplUtils.checkNotImmutable;
import org.joytools.commons.accessors.collection.CollectionMutators;
import org.joytools.commons.accessors.collection.CollectionStringMutators;
import org.joytools.commons.accessors.core.CoreMutators;

/**
 *
 * @author AndreaR
 */
public class Mutators {
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> IndexedMapMutator<K, V> unbound() {
        return CoreMutators.unbound();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param list
     * @return 
     */
    public static <K, V> ListMutator<V> of(final java.util.List<V> list) {
        return CollectionMutators.of(checkNotImmutable(list));
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    @Deprecated
    public static <K, V> IndexedMapAccessor<K, V> old_Mutator() {
        return Accessors.empty();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    @Deprecated
    public static <K, V> IndexedMapAccessor<K, V> old_EmptyMutator() {
        return Accessors.empty();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> MapMutator<K, V> of(final java.util.Map<K, V> map) {
        return CollectionMutators.of(checkNotImmutable(map));
    }


    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    @Deprecated
    public static <V> StringMapMutator<V> old_StringMutator(final java.util.Map<String, V> map) {
        return CollectionStringMutators.of(map);
    }


    /**
     * 
     * @param <V>
     * @param acc
     * @return 
     */
    @Deprecated
    public static <V> StringMapMutator<V> old_StringMutator(final MapMutator<String, V> acc) {
        Objects.requireNonNull(acc, "Mutator");
        if (acc instanceof StringMapMutator) {
            return StringMapMutator.class.cast(acc);
        }
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param <V>
     * @param <V1>
     * @param <V2>
     * @param entry
     * @return 
     */
    @Deprecated
    public static <V, V1 extends V, V2 extends V> MapMutator<String, V> old_Mutator(final Map.Entry<V1, V2> entry) {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param <V>
     * @param <V1>
     * @param <V2>
     * @param entry
     * @return 
     */
    @Deprecated
    public static <V, V1 extends V, V2 extends V> FixedSizeStringMapMutator<V> old_StringMutator(final Map.Entry<V1, V2> entry) {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param array
     * @return 
     */
    @Deprecated
    public static <K, V> ListMutator<V> old_ListMutator(final V[] array) {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param value
     * @return 
     */
    @Deprecated
    public static <K, V> ListAccessor<V> old_SingletonAccessor(final V value) {
        final List<V> list = Arrays.asList(value);
        return CollectionMutators.of(list);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    @Deprecated
    public static <K, V> MapAccessor<K, V> old_SingletonAccessor(final K key, V value) {
        final Map<K, V> map = new HashMap();
        map.put(key, value);
        return Accessors.of(map);
    }
 
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    @Deprecated
    public static <K, V> IndexedMapMutator<K, V> old_Unbound() {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    @Deprecated
    public static <K, V> IndexedMapMutator<K, V> old_empty() {
        return (IndexedMapMutator<K, V>)EMPTY;
    }
    
    /**
     * 
     */
    @Deprecated private final static IndexedMapMutator EMPTY = null; 

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    @Deprecated
    public static <K, V> Option<V> old_get(final MapMutator<K, V> map, final K key) {
        return map.get(key);
    }

    public static void main(final String... args) {
        final Map<String, String> m1 = new HashMap();
        final Map<String, String> m2 = Collections.unmodifiableMap(m1);
        m2.put("a", "a");
    }
    
}
