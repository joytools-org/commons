/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import org.joytools.commons.accessors.core.CoreAccessors;
import org.joytools.commons.accessors.collection.CollectionAccessors;
import org.joytools.commons.accessors.reflect.RecordAccessor;
import org.joytools.commons.accessors.reflect.RecordAccessors;
import org.joytools.commons.accessors.vavr.VavrAccessors;

/**
 *
 * @author AndreaR
 */
public class Accessors {

    /**
     *
     * @param <K>
     * @param <V>
     * @param <A>
     * @param accessor
     * @return
     */
    public static <K, V, A extends Accessor<V>> A multiValued(final A accessor) {
        if (accessor.size() <= 1) {
            throw new IllegalArgumentException("Accessor is not multi valued");
        }
        return accessor;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @return
     */
    public static <K, V> MapAccessor<K, V> of(final java.util.Map<K, V> map) {
        return CollectionAccessors.of(map);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @return
     */
    public static <K, V> MapAccessor<K, V> of(final java.util.Map.Entry<K, V> map) {
        return CollectionAccessors.of(map);
    }

    /**
     * 
     * @param <V>
     * @param ref
     * @return 
     */
    public static <V> ListAccessor<V> of(final AtomicReference<V> ref) {
        return CoreAccessors.of(ref);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param ref
     * @return 
     */
    public static <K, V> IndexedMapAccessor<K, V> of(final K key, final AtomicReference<V> ref) {
        return CoreAccessors.of(key, ref);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param elements
     * @return
     */
    public static <K, V> ListAccessor<V> of(final V[] elements) {
        return CoreAccessors.of(elements);
    }

    /**
     * 
     * @param <V>
     * @param element
     * @return 
     */
    public static <V> ListAccessor<V> singleton(final V element) {
        return CoreAccessors.singleton(element);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param element
     * @return 
     */
    public static <K, V> IndexedMapAccessor<K, V> singleton(final K key, final V element) {
        return CoreAccessors.singleton(key, element);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param list
     * @return
     */
    public static <K, V> ListAccessor<V> of(final java.util.List<V> list) {
        return CollectionAccessors.of(list);
    }

    /**
     * 
     * @param <V>
     * @param opt
     * @return 
     */
    public static <V> ListAccessor<V> of(final Option<V> opt) {
        return opt.isDefined() ? singleton(opt.get()) : empty();
    }

    /**
     * 
     * @param <V>
     * @param opt
     * @return 
     */
    public static <V> ListAccessor<V> of(final Optional<V> opt) {
        return opt.isPresent() ? singleton(opt.get()) : empty();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @return
     */
    public static <K, V> MapAccessor<K, V> of(final io.vavr.collection.Map<K, V> map) {
        return VavrAccessors.of(map);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param seq
     * @return
     */
    public static <K, V> ListAccessor<V> of(final Seq<V> seq) {
        return VavrAccessors.of(seq);
    }

    /**
     * 
     * @param <R>
     * @param record
     * @return 
     */
    public static <R extends Record> RecordAccessor<R> of(final R record) {
        return RecordAccessors.of(record);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> IndexedMapAccessor<K, V> empty() {
        return CoreAccessors.empty();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> IndexedMapAccessor<K, V> unbound() {
        return CoreAccessors.unbound();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <A>
     * @param accessor
     * @return
     */
    public static <K, V, A extends Accessor<V>> A nonEmpty(final A accessor) {
        if (accessor.isEmpty()) {
            throw new IllegalArgumentException("Accessor is empty");
        }
        return accessor;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <A>
     * @param accessor
     * @return
     */
    public static <K, V, A extends Accessor<V>> A nonMultiValued(final A accessor) {
        if (accessor.size() > 1) {
            throw new IllegalArgumentException("Accessor is multi valued");
        }
        return accessor;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <A>
     * @param accessor
     * @return
     */
    public static <K, V, A extends Accessor<V>> A singleValued(final A accessor) {
        return nonMultiValued(nonEmpty(accessor));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <A>
     * @param accessor
     * @param minSize
     * @param maxSize
     * @return
     */
    public static <K, V, A extends Accessor<V>> A sized(final A accessor, final int minSize, final int maxSize) {
        final int size = accessor.size();
        if (size >= minSize && size <= maxSize) {
            return accessor;
        }
        throw new IllegalArgumentException("Invalid accessor size " + size + ", must be between " + minSize + " and " + maxSize);
    }
    
}
