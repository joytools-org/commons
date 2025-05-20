/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.Map;
import java.util.function.Supplier;
import org.joytools.commons.accessors.MapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newUnsupportedOperationException;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullKey;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
abstract class OldNakedJavaMapEntryMutator<K, V> extends NakedMultiValueAccessor<K, V> {
    
    abstract protected Map.Entry<K, V> entry();
    
    /**
     * 
     * @param key
     * @return 
     */
    public boolean contains(final K key) {
        return equals(entry().getKey(), nonNullKey(key));
    }

    /**
     * 
     * @param key
     * @return 
     */
    public Option<V> get(final K key) {
        if (contains(key)) {
            return Some(entry().getValue());
        }
        return None();
    }

    /**
     * 
     * @param key
     * @param defValue
     * @return 
     */
    public V getOrElse(final K key, final V defValue) {
        if (contains(key)) {
            return entry().getValue();
        }
        return defValue;
    }

    /**
     * 
     * @param key
     * @param defValue
     * @return 
     */
    public V getOrElse(final K key, final Supplier<V> defValue) {
        if (contains(key)) {
            return entry().getValue();
        }
        return defValue.get();
    }

    /**
     * 
     * @param key
     * @return 
     */
    public V getOrFail(final K key) {
        if (contains(key)) {
            return entry().getValue();
        }
        throw newItemNotFoundException("getOrFail", key);
    }

    /**
     * 
     * @return 
     */
    public int size() {
        return 1;
    }
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<K> keys() {
        return IterableAccessors.of(entry().getKey());
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<V> values() {
        return IterableAccessors.of(entry().getValue());
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<Tuple2<K, V>> entriesByKey() {
        return IterableAccessors.of(Tuple(entry().getKey(), entry().getValue()));
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param value
     * @return 
     */
    public <U extends V> boolean update(final K key, final U value) {
        if (!contains(key)) {
            return false;
        }
        try {   
            entry().setValue(value);
            return true;
        } catch (final Exception ex) {
            throw newUnsupportedOperationException("update", key, ex);
        }
    }
    
    /**
     * 
     * @param <U>
     * @param key
     * @param value
     * @return 
     */
    public <U extends V> U set(final K key, final U value) {
        if (!contains(key)) {
            throw newItemNotFoundException("set", key);
        }
        try {   
            entry().setValue(value);
            return value;
        } catch (final Exception ex) {
            throw newUnsupportedOperationException("set", key, ex);
        }
    }
    
    /**
     * 
     * @return 
     */
    public final MapAccessor.Reader<K, V> read() {
        return read0();
    }
    
    
}
