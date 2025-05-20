/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.collection.Seq;
import io.vavr.control.Option;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newUnsupportedOperationException;

/**
 *
 * @author AndreaR
 */
public class FixedSizeMutatorMethods extends AccessorMethods {

    /**
     *
     * @param <T>
     * @param <U>
     * @param array
     * @param index
     * @param value
     * @return
     */
    public static <T, U extends T> U set(final T[] array, final int index, final U value) {
        if (index < 0 || index >= array.length) {
            throw newItemNotFoundException("set", index);
        }
        array[index] = value;
        return value;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <U>
     * @param map
     * @param key
     * @param value
     * @return
     */
    public static <K, V, U extends V> U set(final java.util.Map<K, V> map, final K key, final U value) {
        if (!map.containsKey(key)) {
            throw newItemNotFoundException("set", key);
        }
        try {
            MutatorMethods.put(map, key, value);
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("set", key, ex);
        }
        return value;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @return
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M set(final M map, final K key, final U value) {
        if (!map.containsKey(key)) {
            throw newItemNotFoundException("set", key);
        }
        return (M) map.put(key, value);
    }

    /**
     *
     * @param <V>
     * @param <U>
     * @param list
     * @param index
     * @param value
     * @return
     */
    public static <V, U extends V> U set(final java.util.List<V> list, final int index, final U value) {
        if (index < 0 || index >= list.size()) {
            throw newItemNotFoundException("set", index);
        }
        try {
            list.set(index, value);
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("set", index, ex);
        }
        return value;
    }

    /**
     *
     * @param <V>
     * @param <U>
     * @param <S>
     * @param array
     * @param index
     * @param value
     * @return
     */
    public static <V, U extends V, S extends Seq<V>> S set(final S array, final int index, final U value) {
        if (index < 0 || index >= array.size()) {
            throw newItemNotFoundException("set", index);
        }
        return (S) array.update(index, value);
    }

    /**
     *
     * @param <V>
     * @param <U>
     * @param array
     * @param index
     * @param value
     * @return
     */
    public static <V, U extends V> boolean update(final V[] array, final int index, final U value) {
        if (index < 0 || index >= array.length) {
            return false;
        }
        array[index] = value;
        return true;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @return 
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M update(final M map, final K key, final U value) {
        if (!map.containsKey(key)) {
            return map;
        }
        return (M) map.put(key, value);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <U>
     * @param map
     * @param key
     * @param value
     * @return
     */
    public static <K, V, U extends V> boolean update(final java.util.Map<K, V> map, final K key, final U value) {
        if (!map.containsKey(key)) {
            return false;
        }
        try {
            map.put(key, value);
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("update", key, ex);
        }
        return true;
    }

    /**
     *
     * @param <V>
     * @param <U>
     * @param list
     * @param index
     * @param value
     * @return
     */
    public static <V, U extends V> boolean update(final java.util.List<V> list, final int index, final U value) {
        if (index < 0 || index >= list.size()) {
            return false;
        }
        try {
            list.set(index, value);
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("update", index, ex);
        }
        return true;
    }

    /**
     *
     * @param <V>
     * @param <U>
     * @param <S>
     * @param array
     * @param index
     * @param value
     * @return
     */
    public static <V, U extends V, S extends Seq<V>> S update(final S array, final int index, final U value) {
        if (index < 0 || index >= array.size()) {
            return array;
        }
        return (S) array.update(index, value);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param fromKey must exist
     * @param toKey must exist
     * @return
     */
    public static <K, V> V overwrite(final java.util.Map<K, V> map, final K fromKey, final K toKey) {
        final Option<V> value = AccessorMethods.get(map, fromKey);
        if (value.isEmpty()) {
            throw MutatorImplUtils.newSourceItemNotFoundException("overwrite", fromKey);
        }
        if (!map.containsKey(toKey)) {
            throw MutatorImplUtils.newTargetItemNotFoundException("overwrite", toKey);
        }
        final V val = value.get();
        map.put(toKey, val);
        return val;
    }
    
}
