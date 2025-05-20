/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class CoreAccessors {

    /**
     * 
     * @param <T>
     * @param <R>
     * @return 
     */
    public static <T, R> IndexedMapAccessor<T, R> empty() {
        return EmptyIndexedMapAccessor.instance();
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @return 
     */
    public static <T, R> IndexedMapAccessor<T, R> unbound() {
        return UnboundIndexedMapAccessor.instance();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    public static <K, V> IndexedMapAccessor<K, V> of(final K key, final AtomicReference<V> value) {
        return new AtomicReferenceAccessor<>(key, value);
    }

    /**
     * 
     * @param <V>
     * @param value
     * @return 
     */
    public static <V> ListAccessor<V> of(final AtomicReference<V> value) {
        return new AtomicReferenceAccessor<>(StringUtils.EMPTY, value);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param elements
     * @return
     */
    public static <K, V> ListAccessor<V> of(final V[] elements) {
        return new ArrayListAccessor<>(elements);
    }
            
    /**
     * 
     * @param <K>
     * @param <V>
     * @param element
     * @return 
     */
    public static <K, V> ListAccessor<V> singleton(final V element) {
        return new SingletonAccessor(StringUtils.EMPTY, element);
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
        return new SingletonAccessor(key, element);
    }

}
