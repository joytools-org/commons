/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.control.Option;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemAlreadyExistsException;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newSourceItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newTargetItemAlreadyExistsException;
import static org.joytools.commons.accessors.MutatorImplUtils.newUnsupportedOperationException;

/**
 *
 * @author AndreaR
 */
public class MutatorMethods extends FixedSizeMutatorMethods {

    /**
     *
     * @param <V>
     * @param list
     * @param index
     */
    public static <V> void remove(final java.util.List<V> list, final int index) {
        if (index < 0 || index >= list.size()) {
            throw newItemNotFoundException("remove", index);
        }
        try {
            list.remove(index);
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("remove", index, ex);
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> void remove(final java.util.Map<K, V> map, final K key) {
        if (!map.containsKey(key)) {
            throw newItemNotFoundException("remove", key);
        }
        try {
            map.remove(key);
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("remove", key, ex);
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <U>
     * @param list
     * @param index
     * @param value
     * @return
     */
    public static <K, V, U extends V> U insert(final java.util.List<V> list, final int index, final U value) {
        if (index < 0) {
            throw newUnsupportedOperationException("put", index, null);
        }
        try {
            list.add(index, value);
            return value;
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("put", index, ex);
        }
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
    public static <K, V, U extends V> U insert(final java.util.Map<K, V> map, final K key, final U value) {
        if (map.containsKey(key)) {
            throw newItemAlreadyExistsException("insert", key);
        }
        try {
            return put(map, key, value);
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("insert", key, ex);
        }
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
    public static <K, V, M extends io.vavr.collection.Map<K, V>, U extends V> M insert(final M map, final K key, final U value) {
        if (map.containsKey(key)) {
            throw newItemAlreadyExistsException("insert", key);
        }
        final V val = (V) value;
        return (M) map.put(key, val);
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
    public static <K, V, U extends V> boolean putIfAbsent(final java.util.Map<K, V> map, final K key, final U value) {
        if (!map.containsKey(key)) {
            return false;
        }
        put(map, key, value);
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
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M putIfAbsent(final M map, final K key, final U value) {
        if (!map.containsKey(key)) {
            return map;
        }
        return put(map, key, value);
    }

    /**
     *
     * @param <V>
     * @param list
     * @param index
     * @return
     */
    public static <V> boolean delete(final java.util.List<V> list, final int index) {
        if (index < 0 || index >= list.size()) {
            return false;
        }
        try {
            list.remove(index);
            return true;
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("delete", index, ex);
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> boolean delete(final java.util.Map<K, V> map, final K key) {
        if (!map.containsKey(key)) {
            return false;
        }
        try {
            map.remove(key);
            return true;
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("delete", key, ex);
        }
    }

    /**
     *
     * @param <K>
     * @param key
     * @return
     */
    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    /**
     *
     * @param <V>
     * @param supplier
     * @return
     */
    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return
     */
    /**
     *
     * @param method
     * @param key
     * @return
     */
    /**
     *
     * @param method
     * @param key
     * @return
     */
    /**
     *
     * @param key
     * @return
     */
    /**
     *
     * @param key
     * @return
     */
    /**
     *
     * @param method
     * @param key
     * @return
     */
    /**
     *
     * @param <K>
     * @param method
     * @param key
     * @param cause
     * @return
     */
    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param defValue
     * @return
     */
    public static <K, V> V getOrNew(final java.util.Map<K, V> map, final K key, final V defValue) {
        final V ret = map.get(key);
        if (ret != null || map.containsKey(key)) {
            return ret;
        }
        try {
            return put(map, key, defValue);
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("getOrNew", key, ex);
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param supplier
     * @return
     */
    public static <K, V> V getOrNew(final java.util.Map<K, V> map, final K key, final Supplier<V> supplier) {
        final V ret = map.get(key);
        if (ret != null || map.containsKey(key)) {
            return ret;
        }
        try {
            return put(map, key, supplier.get());
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("getOrNew", key, ex);
        }
    }

    /**
     *
     * @param <V>
     * @param list
     * @param key
     * @param defValue
     * @return
     */
    public static <V> V getOrNew(final java.util.List<V> list, final int key, final V defValue) {
        int size = list.size();
        if (key == size) {
            try {
                return add(list, defValue);
            } catch (final RuntimeException ex) {
                throw newUnsupportedOperationException("getOrNew", key, ex);
            }
        }
        return AccessorMethods.getOrFail(list, key);
    }

    /**
     *
     * @param <V>
     * @param list
     * @param key
     * @param supplier
     * @return
     */
    public static <V> V getOrNew(final java.util.List<V> list, final int key, final Supplier<V> supplier) {
        int size = list.size();
        if (key == size) {
            try {
                return add(list, supplier.get());
            } catch (final RuntimeException ex) {
                throw newUnsupportedOperationException("getOrNew", key, ex);
            }
        }
        return AccessorMethods.getOrFail(list, key);
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
    public static <K, V, U extends V> U put(final java.util.Map<K, V> map, final K key, final U value) {
        try {
            map.put(key, value);
            return value;
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("put", key, ex);
        }
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
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M put(final M map, final K key, final U value) {
        return (M) map.put(key, value);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <U>
     * @param list
     * @param index
     * @param value
     * @return
     */
    public static <K, V, U extends V> U put(final java.util.List<V> list, final int index, final U value) {
        if (index < 0) {
            throw newUnsupportedOperationException("put", index, null);
        }
        try {
            while (list.size() <= index) {
                list.add(null);
            }
            list.set(index, value);
            return value;
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("put", index, ex);
        }
    }

    /**
     *
     * @param <V>
     * @param <U>
     * @param list
     * @param value
     * @return
     */
    public static <V, U extends V> U add(final java.util.List<V> list, final U value) {
        try {
            list.add(value);
            return value;
        } catch (final RuntimeException ex) {
            throw newUnsupportedOperationException("add", null, ex);
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param fromKey
     * @param toKey
     * @return
     */
    public static <K, V> V rename(final java.util.Map<K, V> map, final K fromKey, final K toKey) {
        if (!map.containsKey(fromKey)) {
            throw newSourceItemNotFoundException("rename ", fromKey);
        }
        if (map.containsKey(toKey)) {
            throw newTargetItemAlreadyExistsException("rename", toKey);
        }
        return put(map, toKey, map.remove(fromKey));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param fromKey
     * @param toKey
     * @return
     */
    public static <K, V, M extends io.vavr.collection.Map<K, V>> M rename(final M map, final K fromKey, final K toKey) {
        final Option<V> fromValue = AccessorMethods.get(map, fromKey);
        if (fromValue.isEmpty()) {
            throw newSourceItemNotFoundException("rename ", fromKey);
        }
        if (map.containsKey(toKey)) {
            throw newTargetItemAlreadyExistsException("rename", toKey);
        }
        return (M) map.remove(fromKey).put(toKey, fromValue.get());
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param fromKey must exist
     * @param toKey
     * @return
     */
    public static <K, V> V copy(final java.util.Map<K, V> map, final K fromKey, final K toKey) {
        final Option<V> opt = get(map, fromKey);
        if (opt.isEmpty()) {
            throw MutatorImplUtils.newSourceItemNotFoundException("copy", fromKey);
        }
        final V val = opt.get();
        map.put(toKey, val);
        return val;
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param fromKey
     * @param toKey
     * @return 
     */
    public static <K, V, M extends io.vavr.collection.Map<K, V>> M copy(final M map, final K fromKey, final K toKey) {
        final Option<V> opt = get(map, fromKey);
        if (opt.isEmpty()) {
            throw MutatorImplUtils.newSourceItemNotFoundException("copy", fromKey);
        }
        return (M)map.put(toKey, opt.get());
    }

}
