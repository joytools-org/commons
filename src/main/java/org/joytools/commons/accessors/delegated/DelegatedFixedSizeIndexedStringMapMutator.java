/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.FixedSizeIndexedStringMapMutator;

/**
 *
 * @author AndreaR
 * @param <V>
 */
@FunctionalInterface
public interface DelegatedFixedSizeIndexedStringMapMutator<V> extends DelegatedFixedSizeStringMapMutator<V>, DelegatedFixedSizeIndexedMapMutator<String, V>, FixedSizeIndexedStringMapMutator<V> {

    @Override
    default <Z> DelegatedFixedSizeIndexedStringMapMutator<Z> castAs(final Class<Z> clazz) {
        return (DelegatedFixedSizeIndexedStringMapMutator<Z>)this;
    }
    
    /*
    @Override
    default int size() {
        return ((IndexedMapAccessor<K, V>)delegated()).size();
    }

    @Override
    default boolean isEmpty() {
        return ((IndexedMapAccessor<K, V>)delegated()).isEmpty();
    }
    
    @Override
    default SequenceAccessor<V> values() {
        return ((IndexedMapAccessor<K, V>)delegated()).values();
    }

    @Override
    default IndexedMapAccessor.Reader<K, V> read() {
        return ((IndexedMapAccessor<K, V>)delegated()).read();
    } */
    
}
