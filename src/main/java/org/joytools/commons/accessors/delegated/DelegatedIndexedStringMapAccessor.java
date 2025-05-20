/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.IndexedStringMapAccessor;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface DelegatedIndexedStringMapAccessor<V> extends DelegatedStringMapAccessor<V>, DelegatedIndexedMapAccessor<String, V>, IndexedStringMapAccessor<V> {

    default <Z> DelegatedIndexedStringMapAccessor<Z> castAs(final Class<Z> clazz) {
        return (DelegatedIndexedStringMapAccessor<Z>)this;
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
