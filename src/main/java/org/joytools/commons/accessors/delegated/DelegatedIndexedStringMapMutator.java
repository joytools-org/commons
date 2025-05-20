/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.IndexedStringMapMutator;

/**
 *
 * @author AndreaR
 * @param <V>
 */
@FunctionalInterface
public interface DelegatedIndexedStringMapMutator<V> extends DelegatedStringMapMutator<V>, DelegatedIndexedMapMutator<String, V>, IndexedStringMapMutator<V> {

    @Override
    default <Z> DelegatedIndexedStringMapMutator<Z> castAs(final Class<Z> clazz) {
        return (DelegatedIndexedStringMapMutator<Z>)this;
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
