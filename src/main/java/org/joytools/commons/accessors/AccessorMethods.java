/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Supplier;
import static org.joytools.commons.accessors.MutatorImplUtils.newEmptyValueException;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newNullValueException;
import org.joytools.commons.lang.ObjectUtils;

/**
 *
 * @author AndreaR
 */
public class AccessorMethods {

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return 
     */
    public static <K, V> boolean contains(final java.util.Map<K, V> map, final K key) {
        return map.containsKey(key);
    }

    /**
     * 
     * @param <V>
     * @param list
     * @param index
     * @return 
     */
    public static <V> boolean contains(final java.util.List<V> list, final int index) {
        return index >= 0 && index < list.size();
    }

    /**
     * 
     * @param <V>
     * @param array
     * @param index
     * @return 
     */
    public static <V> boolean contains(final V[] array, final int index) {
        return index >= 0 && index < array.length;
    }

    /**
     * 
     * @param <V>
     * @param seq
     * @param index
     * @return 
     */
    public static <V> boolean contains(final io.vavr.collection.Seq<V> seq, final int index) {
        return index >= 0 && index < seq.size();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> Option<V> get(final java.util.Map<K, V> map, final K key) {
        final V v = map.get(key);
        if (v != null || map.containsKey(key)) {
            return Some(v);
        }
        return None();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> Option<V> get(final io.vavr.collection.Map<K, V> map, final K key) {
        return map.get(key);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param list
     * @param index
     * @return
     */
    public static <K, V> Option<V> get(final java.util.List<V> list, final int index) {
        if (index >= list.size() || index < 0) {
            return None();
        }
        return Some(list.get(index));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param seq
     * @param index
     * @return
     */
    public static <K, V> Option<V> get(final Seq<V> seq, final int index) {
        return seq.isDefinedAt(index) ? Option.some(seq.get(index)) : Option.none();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param array
     * @param index
     * @return
     */
    public static <K, V> Option<V> get(final V[] array, final int index) {
        if (index >= array.length || index < 0) {
            return None();
        }
        return Some(array[index]);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param arr
     * @param index
     * @return
     */
    public static <K, V> V getOrNull(final V[] arr, final int index) {
        return getOrElse(arr, index, null);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param list
     * @param index
     * @return
     */
    public static <K, V> V getOrNull(final java.util.List<V> list, final int index) {
        return getOrElse(list, index, null);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param seq
     * @param index
     * @return
     */
    public static <K, V> V getOrNull(final Seq<V> seq, final int index) {
        return getOrElse(seq, index, null);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> V getOrNull(final java.util.Map<K, V> map, final K key) {
        return map.get(key);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> V getOrNull(final io.vavr.collection.Map<K, V> map, final K key) {
        return map.getOrElse(key, null);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> V getOrFail(final java.util.Map<K, V> map, final K key) {
        final V value = map.get(key);
        if (value != null || map.containsKey(key)) {
            return value;
        }
        throw newItemNotFoundException("getOrFail", key);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> V getOrFail(final io.vavr.collection.Map<K, V> map, final K key) {
        final Option<V> val = map.get(key);
        if (val.isDefined()) {
            return val.get();
        }
        throw newItemNotFoundException("getOrFail", key);
    }

    /**
     *
     * @param <V>
     * @param list
     * @param index
     * @return
     */
    public static <V> V getOrFail(final java.util.List<V> list, final int index) {
        try {
            return list.get(index);
        } catch (final RuntimeException ex) {
            throw newItemNotFoundException("getOrFail", index, ex);
        }
    }

    /**
     *
     * @param <V>
     * @param list
     * @param index
     * @return
     */
    public static <V> V getOrFail(final Seq<V> list, final int index) {
        try {
            return list.get(index);
        } catch (final RuntimeException ex) {
            throw newItemNotFoundException("getOrFail", index, ex);
        }
    }

    /**
     *
     * @param <V>
     * @param list
     * @param index
     * @return
     */
    public static <V> V getOrFail(final V[] list, final int index) {
        try {
            return list[index];
        } catch (final RuntimeException ex) {
            throw newItemNotFoundException("getOrFail", index, ex);
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param defValue
     * @return
     */
    public static <K, V> V getOrElse(final java.util.Map<K, V> map, final K key, final V defValue) {
        final V ret = map.get(key);
        if (ret != null || map.containsKey(key)) {
            return ret;
        }
        return defValue;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param defValue
     * @return 
     */
    public static <K, V> V getOrElse(final java.util.Map<K, V> map, final K key, final Supplier<V> defValue) {
        final V ret = map.get(key);
        if (ret != null || map.containsKey(key)) {
            return ret;
        }
        return defValue.get();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param list
     * @param index
     * @param defValue
     * @return
     */
    public static <K, V> V getOrElse(final java.util.List<V> list, final int index, final V defValue) {
        if (index >= list.size() || index < 0) {
            return defValue;
        }
        return list.get(index);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param array
     * @param index
     * @param defValue
     * @return
     */
    public static <K, V> V getOrElse(final V[] array, final int index, final V defValue) {
        if (index >= array.length || index < 0) {
            return defValue;
        }
        return array[index];
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param seq
     * @param index
     * @param defValue
     * @return
     */
    public static <K, V> V getOrElse(final Seq<V> seq, final int index, final V defValue) {
        if (index >= seq.size() || index < 0) {
            return defValue;
        }
        return seq.get(index);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @param defValue
     * @return
     */
    public static <K, V> V getOrElse(final io.vavr.collection.Map<K, V> map, final K key, final V defValue) {
        return map.getOrElse(key, defValue);
    }
    
    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> V readNonNull(final java.util.Map<K, V> map, final K key) {
        final V value = getOrFail(map, key);
        if (value == null) {
            throw newNullValueException(key);
        }
        return value;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return 
     */
    public static <K, V> V readNonNull(final io.vavr.collection.Map<K, V> map, final K key) {
        final V value = getOrFail(map, key);
        if (value == null) {
            throw newNullValueException(key);
        }
        return value;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <K, V> V readNonEmpty(final java.util.Map<K, V> map, final K key) {
        final V value = getOrFail(map, key);
        if (ObjectUtils.isEmpty(value)) {
            throw newEmptyValueException(key);
        }
        return value;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return 
     */
    public static <K, V> V readNonEmpty(final io.vavr.collection.Map<K, V> map, final K key) {
        final V value = getOrFail(map, key);
        if (ObjectUtils.isEmpty(value)) {
            throw newEmptyValueException(key);
        }
        return value;
    }

    
    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key must exist
     * @return
     */
    public static <K, V> V read(final java.util.Map<K, V> map, final K key) {
        return getOrFail(map, key);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <T>
     * @param map
     * @param key
     * @param func
     * @return 
     */
    public static <K, V, T> T read(final java.util.Map<K, V> map, final K key, final Function<? super V, T> func) {
        return func.apply(getOrFail(map, key));
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return 
     */
    public static <K, V> V read(final io.vavr.collection.Map<K, V> map, final K key) {
        return getOrFail(map, key);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <T>
     * @param map
     * @param key
     * @param func
     * @return 
     */
    public static <K, V, T> T read(final io.vavr.collection.Map<K, V> map, final K key, final Function<? super V, T> func) {
        return func.apply(getOrFail(map, key));
    }


}
