/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.IndexedMapMutator;

/**
 *
 * @author AndreaR
 */
@FunctionalInterface
public interface DelegatedIndexedMapMutator<K, V> extends DelegatedMapMutator<K, V>, DelegatedListMutator<V>, DelegatedFixedSizeIndexedMapMutator<K, V>, IndexedMapMutator<K, V> {
    
    @Override
    default <Z> DelegatedIndexedMapMutator<K, Z> castAs(final Class<Z> clazz) {
        return (DelegatedIndexedMapMutator<K, Z>)this;
    }
    
    /* @Override
    default boolean isUpdatable() {
        return true;
    } */

}
