/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.FixedSizeMapMutator;

/**
 *
 * @author AndreaR
 */
@FunctionalInterface
public interface DelegatedFixedSizeMapMutator<K, V> extends DelegatedMapAccessor<K, V>, FixedSizeMapMutator<K, V> {
    
    @Override
    default <Z> DelegatedFixedSizeMapMutator<K, Z> castAs(final Class<Z> clazz) {
        return (DelegatedFixedSizeMapMutator<K, Z>)this;
    }

    @Override
    default boolean isUpdatable() {
        return true;
    }
    
    @Override
    default <U extends V> U set(final K key, final U val) {
        return ((FixedSizeMapMutator<K, V>)delegated()).set(key, val);
    }

    @Override
    default <U extends V> boolean update(final K key, final U val) {
        return ((FixedSizeMapMutator<K, V>)delegated()).update(key, val);
    }

}
