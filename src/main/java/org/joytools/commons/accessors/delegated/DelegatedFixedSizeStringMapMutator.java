/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.FixedSizeStringMapMutator;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface DelegatedFixedSizeStringMapMutator<V> extends DelegatedStringMapAccessor<V>, DelegatedFixedSizeMapMutator<String, V>, FixedSizeStringMapMutator<V> {
 
    @Override
    default <Z> DelegatedFixedSizeStringMapMutator<Z> castAs(final Class<Z> clazz) {
        return (DelegatedFixedSizeStringMapMutator<Z>)this;
    }

    /*
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
    default SequenceAccessor<K> keys() {
        return ((MapAccessor<K, V>)delegated()).keys();
    }

    @Override
    default SequenceAccessor<V> values() {
        return ((MapAccessor<K, V>)delegated()).values();
    }

    @Override
    default SequenceAccessor<Tuple2<K, V>> entriesByKey() {
        return ((MapAccessor<K, V>)delegated()).entriesByKey();
    }

    @Override
    default MapAccessor.Reader<K, V> read() {
        return ((MapAccessor<K, V>)delegated()).read();
    }*/
    
}
