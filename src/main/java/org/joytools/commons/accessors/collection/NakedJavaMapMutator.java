/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.Map;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullKey;
import org.joytools.commons.collections.IterableAccessors;
import static org.joytools.commons.accessors.AccessorFunctions.mapEntryToTuple;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.FixedSizeMutatorMethods;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.MutatorMethods;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
abstract class NakedJavaMapMutator<K, V> extends NakedMultiValueAccessor<K, V> {

    protected abstract Map<K, V> map();
    
    /**
     * 
     * @return 
     */
    public CaseSensitivity caseSensitivity() {
        return CaseSensitivity.fromMap(map());
    }

    /**
     * 
     * @param key
     * @return 
     */
    public V getOrFail(final K key) {
        return AccessorMethods.getOrFail(map(), key);
    }

    /**
     * 
     * @param key
     * @return 
     */
    public Option<V> get(final K key) {
        return AccessorMethods.get(map(), key);
    }

    /**
     * 
     * @param key
     * @return 
     */
    public V getOrNull(final K key) {
        return AccessorMethods.getOrNull(map(), key);
    }

    /**
     * 
     * @param key
     * @param defValue
     * @return 
     */
    public V getOrElse(final K key, final V defValue) {
        return AccessorMethods.getOrElse(map(), key, defValue);
    }

    /**
     * 
     * @param key
     * @return 
     */
    public boolean contains(final K key) {
        return map().containsKey(nonNullKey(key));
    }

    /**
     * 
     * @return 
     */
    public boolean isUpdatable() {
        return true;
    }

    /**
     * 
     * @param key
     * @param val 
     */
    public <U extends V> U put(final K key, final U val) {
        map().put(nonNullKey(key), val);
        return val;
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param val
     * @return 
     */
    public <U extends V> U insert(final K key, final U val) {
        return MutatorMethods.insert(map(), key, val);
    }

    /**
     * 
     * @param key
     * @param val
     * @return 
     */
    public <U extends V> U set(final K key, final U val) {
        return FixedSizeMutatorMethods.set(map(), nonNullKey(key), val);
    }
    
    /**
     * 
     * @param <U>
     * @param key
     * @param val
     * @return 
     */
    public <U extends V> boolean update(final K key, final U val) {
        return FixedSizeMutatorMethods.update(map(), nonNullKey(key), val);
    }

    /**
     * 
     * @param key 
     */
    public boolean delete(final K key) {
        return MutatorMethods.delete(map(), key);
    }
    
    /**
     * 
     * @param key 
     */
    public void remove(final K key) {
        MutatorMethods.remove(map(), key);
    }

    /**
     * 
     * @param data
     * @param merger 
     */
    public <U extends V> void merge(final Iterable<Tuple2<K, U>> data, final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        MapUtils.merge(map(), data, merger);
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<K> keys() {
        return IterableAccessors.from(map().keySet());
    }
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<Tuple2<K, V>> entriesByKey() {
        return IterableAccessors.from(map().entrySet())
                .map(mapEntryToTuple());
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<V> values() {
        return IterableAccessors.from(map().values());
    }
    
    /**
     * 
     * @return 
     */
    public int size() {
        return map().size();
    }
    
    /**
     * 
     * @return 
     */
    public final MapAccessor.Reader<K, V> read() {
        return read0();
    }
    
    
}
