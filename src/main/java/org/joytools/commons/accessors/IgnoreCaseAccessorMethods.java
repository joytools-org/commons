/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.Map;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import org.joytools.commons.collections.StringMaps;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 */
public class IgnoreCaseAccessorMethods {

    /**
     *
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <V> V getOrNull(final Map<String, V> map, final String key) {
        final Option<V> value = get(map, key);
        return value.getOrNull();
    }

    /**
     *
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <V> V getOrNull(final io.vavr.collection.Map<String, V> map, final String key) {
        final Option<V> value = get(map, key);
        return value.getOrNull();
    }

    /**
     * 
     * @param <V>
     * @param map
     * @param key
     * @return 
     */
    public static <V> V getOrNull(final MapAccessor<String, V> map, final String key) {
        final Option<V> value = get(map, key);
        return value.getOrNull();
    }

    /**
     *
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <V> V getOrElse(final Map<String, V> map, final String key, final V defValue) {
        final Option<V> value = get(map, key);
        return value.getOrElse(defValue);
    }

    /**
     *
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <V> V getOrElse(final io.vavr.collection.Map<String, V> map, final String key, final V defValue) {
        final Option<V> value = get(map, key);
        return value.getOrElse(defValue);
    }

    /**
     * 
     * @param <V>
     * @param map
     * @param key
     * @param defValue
     * @return 
     */
    public static <V> V getOrElse(final MapAccessor<String, V> map, final String key, final V defValue) {
        final Option<V> value = get(map, key);
        return value.getOrElse(defValue);
    }

    /**
     *
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <V> Option<V> get(final Map<String, V> map, final String key) {
        Option<V> value = AccessorMethods.get(map, key);
        if (value.isDefined()) {
            return value;
        }
        // Check if nothing more to do
        if (map.isEmpty() || StringMaps.caseSensitivityOf(map) == CaseSensitivity.INSENSITIVE) {
            return None();
        }
        if (!StringUtils.isAllLowerCase(key) && (value = AccessorMethods.get(map, key.toLowerCase())).isDefined()) {
            return value;
        }
        if (!StringUtils.isAllUpperCase(key) && (value = AccessorMethods.get(map, key.toUpperCase())).isDefined()) {
            return value;
        }
        for (final Map.Entry<String, V> e : map.entrySet()) {
            if (StringUtils.equalsIgnoreCase(key, e.getKey())) {
                return Some(e.getValue());
            }
        }
        return None();
    }

    /**
     *
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <V> Option<V> get(final io.vavr.collection.Map<String, V> map, final String key) {
        Option<V> value = AccessorMethods.get(map, key);
        if (value.isDefined()) {
            return value;
        }
        // Check if nothing more to do
        if (map.isEmpty() || StringMaps.caseSensitivityOf(map) == CaseSensitivity.INSENSITIVE) {
            return None();
        }
        if (!StringUtils.isAllLowerCase(key) && (value = AccessorMethods.get(map, key.toLowerCase())).isDefined()) {
            return value;
        }
        if (!StringUtils.isAllUpperCase(key) && (value = AccessorMethods.get(map, key.toUpperCase())).isDefined()) {
            return value;
        }
        for (final Tuple2<String, V> e : map) {
            if (StringUtils.equalsIgnoreCase(key, e._1)) {
                return Some(e._2);
            }
        }
        return None();
    }

    /**
     *
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <V> V getOrFail(final Map<String, V> map, final String key) {
        final Option<V> value = get(map, key);
        if (value.isDefined()) {
            return value.get();
        }
        throw newItemNotFoundException("getOrFailIgnoreCase", key);
    }

    /**
     *
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    public static <V> V getOrFail(final io.vavr.collection.Map<String, V> map, final String key) {
        final Option<V> value = get(map, key);
        if (value.isDefined()) {
            return value.get();
        }
        throw newItemNotFoundException("getOrFailIgnoreCase", key);
    }

    /**
     * 
     * @param <V>
     * @param map
     * @param key
     * @return 
     */
    public static <V> V getOrFail(final MapAccessor<String, V> map, final String key) {
        final Option<V> value = get(map, key);
        if (value.isDefined()) {
            return value.get();
        }
        throw newItemNotFoundException("getOrFailIgnoreCase", key);
    }

    /**
     *
     * @param <V>
     * @param acc
     * @param key
     * @return
     */
    public static <V> Option<V> get(final MapAccessor<String, V> acc, final String key) {
        MutatorUtils.nonNullAccessor(acc);
        Option<V> value = acc.get(key);
        if (acc.isEmpty() || value.isDefined()) {
            return value;
        }
        if (acc instanceof CaseSensitivitySupport css) {
            final CaseSensitivity cs = css.caseSensitivity();
            if (cs.isIgnoreCase()) {
                return value;
            }
        }
        if (acc.size() > 5) {
            if (!StringUtils.isAllLowerCase(key) && (value = acc.get(key.toLowerCase())).isDefined()) {
                return value;
            }
            if (!StringUtils.isAllUpperCase(key) && (value = acc.get(key.toUpperCase())).isDefined()) {
                return value;
            }
        }
        for (final Tuple2<String, V> e : acc.entriesByKey()) {
            if (StringUtils.equalsIgnoreCase(key, e._1)) {
                return Some(e._2);
            }
        }
        return None();
    }
    
}
