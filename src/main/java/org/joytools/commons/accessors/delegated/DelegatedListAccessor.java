/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import io.vavr.Tuple2;
import io.vavr.control.Option;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
@FunctionalInterface
public interface DelegatedListAccessor<V> extends DelegatedAccessor<V>, ListAccessor<V> {
 
    @Override
    default <Z> DelegatedListAccessor<Z> castAs(final Class<Z> clazz) {
        return (DelegatedListAccessor<Z>)this;
    }

    @Override
    default int size() {
        return delegated().size();
    }
    
    @Override
    default boolean isEmpty() {
        return delegated().isEmpty();
    }

    @Override
    default boolean contains(final int index) {
        return ((ListAccessor<V>)delegated()).contains(index);
    }

    @Override
    default Option<V> get(final int index) {
        return ((ListAccessor<V>)delegated()).get(index);
    }
    
    @Override
    default V getOrFail(final int index) {
        return ((ListAccessor<V>)delegated()).getOrFail(index);
    }

    @Override
    default V getOrElse(final int index, final V defValue) {
        return ((ListAccessor<V>)delegated()).getOrElse(index, defValue);
    }
    
    @Override
    default IterableAccessor<Integer> indexes() {
        return ((ListAccessor<V>)delegated()).indexes();
    }

    @Override
    default IterableAccessor<V> values() {
        return ((ListAccessor<V>)delegated()).values();
    }

    @Override
    default IterableAccessor<Tuple2<Integer, V>> entriesByIndex() {
        return ((ListAccessor<V>)delegated()).entriesByIndex();
    }

    @Override
    default ListAccessor.Reader<V> read() {
        return ((ListAccessor<V>)delegated()).read();
    }
    
}
