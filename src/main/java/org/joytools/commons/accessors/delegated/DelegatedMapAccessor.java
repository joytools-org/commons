/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import io.vavr.Tuple2;
import io.vavr.control.Option;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface DelegatedMapAccessor<K, V> extends DelegatedAccessor<V>, MapAccessor<K, V> {
 
    @Override
    default <Z> DelegatedMapAccessor<K, Z> castAs(final Class<Z> clazz) {
        return (DelegatedMapAccessor<K, Z>)this;
    }

    @Override
    default int size() {
        return delegated().size();
    }
    
    @Override
    default boolean isEmpty() {
        return delegated().isEmpty();
    }

    @Override
    default boolean contains(final K key) {
        return ((MapAccessor<K, V>)delegated()).contains(key);
    }

    @Override
    default Option<V> get(final K key) {
        return ((MapAccessor<K, V>)delegated()).get(key);
    }
    
    @Override
    default V getOrFail(final K key) {
        return ((MapAccessor<K, V>)delegated()).getOrFail(key);
    }

    @Override
    default V getOrElse(final K key, final V defValue) {
        return ((MapAccessor<K, V>)delegated()).getOrElse(key, defValue);
    }
    
    @Override
    default IterableAccessor<K> keys() {
        return ((MapAccessor<K, V>)delegated()).keys();
    }

    @Override
    default IterableAccessor<V> values() {
        return ((MapAccessor<K, V>)delegated()).values();
    }

    @Override
    default IterableAccessor<Tuple2<K, V>> entriesByKey() {
        return ((MapAccessor<K, V>)delegated()).entriesByKey();
    }

    @Override
    default MapAccessor.Reader<K, V> read() {
        return ((MapAccessor<K, V>)delegated()).read();
    }
    
}
