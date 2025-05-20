/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.impl;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.Objects;
import org.joytools.commons.accessors.IndexedMapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public abstract class NakedSingletonMutator<K, V> extends NakedMultiValueAccessor<K, V> {

    protected NakedSingletonMutator(final K key) {
        this.key = Objects.requireNonNull(key, "Key");
        this.keys = IterableAccessors.of(key);
    }
    
    public int size() {
        return 1;
    }
    
    public boolean isEmpty() {
        return false;
    }
    
    abstract protected V get0();

    abstract protected void set0(final V value);

    public boolean contains(final K key) {
        return equals(this.key, key);
    }
    
    public boolean contains(final int index) {
        return index == 0;
    }

    public Option<V> get(final K key) {
        if (contains(key)) {
            return Some(get0());
        }
        return None();
    }
    
    public Option<V> get(final int index) {
        if (contains(index)) {
            return Some(get0());
        }
        return None();
    }

    public V getOrFail(final K key) {
        if (contains(key)) {
            return get0();
        }
        throw newItemNotFoundException("getOrFail", key);
    }
    
    public V getOrFail(final int index) {
        if (contains(index)) {
            return get0();
        }
        throw newItemNotFoundException("getOrFail", index);
    }
    
    public V getOrElse(final K key, final V defValue) {
        if (contains(key)) {
            return get0();
        }
        throw newItemNotFoundException("getOrElse", key);
    }
    
    public V getOrElse(final int index, final V defValue) {
        if (contains(index)) {
            return get0();
        }
        throw newItemNotFoundException("getOrElse", index);
    }

    public <U extends V> U set(final K key, final U value) {
        if (!contains(key)) {
            throw newItemNotFoundException("set", key, null);
        }
        set0(value);
        return value;
    }

    public <U extends V> U set(final int index , final U value) {
        if (!contains(index)) {
            throw newItemNotFoundException("set", index, null);
        }
        set0(value);
        return value;
    }

    public <U extends V> boolean update(final K key, final U value) {
        if (!contains(key)) {
            return false;
        }
        set0(value);
        return true;
    }

    public <U extends V> boolean update(final int index , final U value) {
        if (!contains(index)) {
            return false;
        }
        set0(value);
        return true;
    }
    
    public IterableAccessor<K> keys() {
        return keys;
    }

    public IterableAccessor<Integer> indexes() {
        return INDEXES;
    }
    
    public IterableAccessor<V> values() {
        return IterableAccessors.of(get0());
    }

    public IterableAccessor<Tuple2<K, V>> entriesByKey() {
        return IterableAccessors.of(Tuple(key, get0()));
    }

    public IterableAccessor<Tuple2<Integer, V>> entriesByIndex() {
        return IterableAccessors.of(Tuple(0, get0()));
    }

    public IndexedMapAccessor.Reader<K, V> read() {
        return read0();
    }
    
    private final K key;

    private final IterableAccessor<K> keys;

    private final static IterableAccessor<Integer> INDEXES = IterableAccessors.of(0);
    
}
