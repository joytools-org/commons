/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.vavr;

import io.vavr.Tuple2;
import io.vavr.control.Option;
import io.vavr.collection.Map;
import java.util.Objects;
import org.joytools.commons.accessors.MapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullKey;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
class VavrMapAccessor<K, V> extends NakedMultiValueAccessor<K, V> implements MapAccessor<K, V> {

    protected VavrMapAccessor(final io.vavr.collection.Map<K, V> map) {
        this.map = Objects.requireNonNull(map, "Map");
    }
        
    @Override
    public V getOrFail(final K key) {
        final Option<V> value = map.get(nonNullKey(key));
        if (value.isEmpty()) {
            throw newItemNotFoundException("getOrFail", key);
        }
        return value.get();
    }

    @Override
    public V getOrElse(final K key, final V defValue) {
        return get(key).getOrElse(defValue);
    }

    @Override
    public Option<V> get(final K key) {
        return map.get(nonNullKey(key));
    }

    @Override
    public boolean contains(final K key) {
        return map.containsKey(nonNullKey(key));
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Override
    public IterableAccessor<K> keys() {
        return IterableAccessors.ofAll(map.keySet());
    }
    
    @Override
    public IterableAccessor<Tuple2<K, V>> entriesByKey() {
        return IterableAccessors.ofAll(map);
    }

    @Override
    public IterableAccessor<V> values() {
        return IterableAccessors.ofAll(map.values());
    }
    
    @Override
    public int size() {
        return map.size();
    }
    
    @Override
    public MapAccessor.Reader<K, V> read() {
        return read0();
    }

    final protected io.vavr.collection.Map<K, V> map;
        
}
