/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.MapMutator;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface DelegatedMapMutator<K, V> extends DelegatedFixedSizeMapMutator<K, V>, MapMutator<K, V> {
    
    @Override
    default <Z> DelegatedMapMutator<K, Z> castAs(final Class<Z> clazz) {
        return (DelegatedMapMutator<K, Z>)this;
    }

    @Override
    default <U extends V> U put(final K key, final U val) {
        return ((MapMutator<K, V>)delegated()).put(key, val);
    }

    @Override
    default boolean delete(final K key) {
        return ((MapMutator<K, V>)delegated()).delete(key);
    }

    @Override
    default void remove(final K key) {
        ((MapMutator<K, V>)delegated()).remove(key);
    }

    @Override
    default <U extends V> U insert(final K key, final U val) {
        return ((MapMutator<K, V>)delegated()).insert(key, val);
    }
    
}
