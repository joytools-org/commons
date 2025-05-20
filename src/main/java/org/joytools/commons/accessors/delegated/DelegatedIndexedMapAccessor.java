/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface DelegatedIndexedMapAccessor<K, V> extends DelegatedMapAccessor<K, V>, DelegatedListAccessor<V>, IndexedMapAccessor<K, V> {

    @Override
    default <Z> DelegatedIndexedMapAccessor<K, Z> castAs(final Class<Z> clazz) {
        return (DelegatedIndexedMapAccessor<K, Z>)this;
    }

    @Override
    default int size() {
        return ((IndexedMapAccessor<K, V>)delegated()).size();
    }

    @Override
    default boolean isEmpty() {
        return ((IndexedMapAccessor<K, V>)delegated()).isEmpty();
    }
    
    @Override
    default IterableAccessor<V> values() {
        return ((IndexedMapAccessor<K, V>)delegated()).values();
    }

    @Override
    default IndexedMapAccessor.Reader<K, V> read() {
        return ((IndexedMapAccessor<K, V>)delegated()).read();
    }
    
}
