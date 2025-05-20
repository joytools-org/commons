/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import io.vavr.Tuple2;
import io.vavr.control.Option;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
class NakedUnboundIndexedMapAccessor<K, V> extends NakedMultiValueAccessor<K, V> {

    protected <T> T unbound() {
        throw new UnsupportedOperationException("Accessor or Mutator is not bound");
    }            
    
    public boolean contains(final K key) {
        return unbound();
    }

    public IterableAccessor<K> keys() {
        return unbound();
    }

    public IterableAccessor<Tuple2<K, V>> entriesByKey() {
        return unbound();
    }

    public Option<V> get(final K key) {
        return unbound();
    }

    public V getOrElse(final K key, final V defValue) {
        return unbound();
    }

    public V getOrElse(final int key, final V defValue) {
        return unbound();
    }

    public int size() {
        return unbound();
    }

    public boolean isEmpty() {
        return unbound();
    }

    public IterableAccessor<V> values() {
        return unbound();
    }

    public Option<V> get(final int index) {
        return unbound();
    }

    public V getOrFail(final K key) {
        return unbound();
    }

    public V getOrFail(final int index) {
        return unbound();
    }

    public IterableAccessor<Tuple2<Integer, V>> entriesByIndex() {
        return unbound();
    }
 
    public <U extends V> boolean update(K key, U val) {
        return unbound();
    }

    public <U extends V> U set(K key, U val) {
        return unbound();
    }

    public boolean isUpdatable() {
        return unbound();
    }

    public <U extends V> U set(int index, U value) {
        return unbound();
    }

    public <U extends V> boolean update(int index, U value) {
        return unbound();
    }

    public <U extends V> U put(K key, U val) {
        return unbound();
    }

    public boolean delete(K key) {
        return unbound();
    }

    public <U extends V> U insert(K key, U val) {
        return unbound();
    }

    public void remove(K key) {
        unbound();
    }

    public <U extends V> U add(U value) {
        return unbound();
    }

    public <U extends V> U insert(int index, U value) {
        return unbound();
    }

    public <U extends V> U put(int index, U value) {
        return unbound();
    }

    public boolean delete(int index) {
        return unbound();
    }

    public void remove(int index) {
        unbound();
    }

    public IndexedMapAccessor.Reader<K, V> read() {
        return unbound();
    }
    
}
