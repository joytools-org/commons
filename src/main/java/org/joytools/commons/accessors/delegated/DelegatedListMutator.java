/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.ListMutator;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface DelegatedListMutator<V> extends DelegatedFixedSizeListMutator<V>, ListMutator<V> {
    
    @Override
    default <Z> DelegatedListMutator<Z> castAs(final Class<Z> clazz) {
        return (DelegatedListMutator<Z>)this;
    }

    @Override
    default <U extends V> U add(final U value) {
        return ((ListMutator<V>)delegated()).add(value);
    }

    @Override
    default <U extends V> U put(final int key, final U val) {
        return ((ListMutator<V>)delegated()).put(key, val);
    }

    @Override
    default boolean delete(final int key) {
        return ((ListMutator<V>)delegated()).delete(key);
    }

    @Override
    default void remove(final int key) {
        ((ListMutator<V>)delegated()).remove(key);
    }

    @Override
    default <U extends V> U insert(final int key, final U val) {
        return ((ListMutator<V>)delegated()).insert(key, val);
    }
    
}
