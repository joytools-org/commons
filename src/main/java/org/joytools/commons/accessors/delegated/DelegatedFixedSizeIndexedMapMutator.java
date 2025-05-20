/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;

/**
 *
 * @author AndreaR
 */
@FunctionalInterface
public interface DelegatedFixedSizeIndexedMapMutator<K, V> extends DelegatedFixedSizeMapMutator<K, V>, DelegatedFixedSizeListMutator<V>, DelegatedIndexedMapAccessor<K, V>, FixedSizeIndexedMapMutator<K, V> {
    
    @Override
    default <Z> DelegatedFixedSizeIndexedMapMutator<K, Z> castAs(final Class<Z> clazz) {
        return (DelegatedFixedSizeIndexedMapMutator<K, Z>)this;
    }
    
    @Override
    default boolean isUpdatable() {
        return true;
    }

}
