/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import static io.vavr.API.None;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import org.joytools.commons.accessors.IndexedMapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
class AbstractEmptyIndexedMapAccessor<K, V> extends NakedMultiValueAccessor<K, V> implements IndexedMapAccessor<K, V> {

    @Override
    public Reader<K, V> read() {
        return read0();
    }
    
    @Override
    public boolean contains(final K key) {
        return false;
    }

    @Override
    public IterableAccessor<K> keys() {
        return IterableAccessors.empty();
    }

    @Override
    public IterableAccessor<Tuple2<K, V>> entriesByKey() {
        return IterableAccessors.empty();
    }

    @Override
    public Option<V> get(final K key) {
        return None();
    }

    @Override
    public V getOrNull(final K key) {
        return null;
    }

    @Override
    public V getOrNull(final int index) {
        return null;
    }

    @Override
    public V getOrElse(final K key, final V defValue) {
        return defValue;
    }

    @Override
    public V getOrElse(final int key, final V defValue) {
        return defValue;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public IterableAccessor<V> values() {
        return IterableAccessors.empty();
    }

    @Override
    public Option<V> get(final int index) {
        return None();
    }

    @Override
    public V getOrFail(final K key) {
        throw newItemNotFoundException("getOrFail", key);
    }

    @Override
    public V getOrFail(final int index) {
        throw newItemNotFoundException("getOrFail", index);
    }

    @Override
    public IterableAccessor<Tuple2<Integer, V>> entriesByIndex() {
        return IterableAccessors.empty();
    }
    
}
