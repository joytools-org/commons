/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import io.vavr.Function3;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import static org.joytools.commons.accessors.AccessorFunctions.mapEntryToTuple;
import org.joytools.commons.lang.ObjectUtils;

/**
 *
 * @author AndreaR
 */
public class MapUtils {
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param k1
     * @param v1
     * @return 
     */
    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1) {
        return merge0(map, k1, v1, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2) {
        merge0(map, k1, v1, null);
        return merge0(map, k2, v2, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3) {
        merge0(map, k1, v1, null);
        merge0(map, k2, v2, null);
        return merge0(map, k3, v3, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4) {
        merge0(map, k1, v1, null);
        merge0(map, k2, v2, null);
        merge0(map, k3, v3, null);
        return merge0(map, k4, v4, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5) {
        merge0(map, k1, v1, null);
        merge0(map, k2, v2, null);
        merge0(map, k3, v3, null);
        merge0(map, k4, v4, null);
        return merge0(map, k5, v5, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6) {
        merge0(map, k1, v1, null);
        merge0(map, k2, v2, null);
        merge0(map, k3, v3, null);
        merge0(map, k4, v4, null);
        merge0(map, k5, v5, null);
        return merge0(map, k6, v6, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7) {
        merge0(map, k1, v1, null);
        merge0(map, k2, v2, null);
        merge0(map, k3, v3, null);
        merge0(map, k4, v4, null);
        merge0(map, k5, v5, null);
        merge0(map, k6, v6, null);
        return merge0(map, k7, v7, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8) {
        merge0(map, k1, v1, null);
        merge0(map, k2, v2, null);
        merge0(map, k3, v3, null);
        merge0(map, k4, v4, null);
        merge0(map, k5, v5, null);
        merge0(map, k6, v6, null);
        merge0(map, k7, v7, null);
        return merge0(map, k8, v8, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8,
            final K k9, final V v9) {
        merge0(map, k1, v1, null);
        merge0(map, k2, v2, null);
        merge0(map, k3, v3, null);
        merge0(map, k4, v4, null);
        merge0(map, k5, v5, null);
        merge0(map, k6, v6, null);
        merge0(map, k7, v7, null);
        merge0(map, k8, v8, null);
        return merge0(map, k9, v9, null);
    }

    public static <K, V, M extends java.util.Map<K, V>> M insert(final M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8,
            final K k9, final V v9,
            final K k10, final V v10) {
        merge0(map, k1, v1, null);
        merge0(map, k2, v2, null);
        merge0(map, k3, v3, null);
        merge0(map, k4, v4, null);
        merge0(map, k5, v5, null);
        merge0(map, k6, v6, null);
        merge0(map, k7, v7, null);
        merge0(map, k8, v8, null);
        merge0(map, k9, v9, null);
        return merge0(map, k10, v10, null);
    }

    /* public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(final M map, 
            final K k1, final V v1) {
        return insert(map, k1, v1);
    } */

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param k1
     * @param v1
     * @return 
     */
    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(final M map, 
            final K k1, final V v1) {
        return merge0(map, k1, v1, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2) {
        map = merge0(map, k1, v1, null);
        return merge0(map, k2, v2, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3) {
        map = merge0(map, k1, v1, null);
        map = merge0(map, k2, v2, null);
        return merge0(map, k3, v3, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4) {
        map = merge0(map, k1, v1, null);
        map = merge0(map, k2, v2, null);
        map = merge0(map, k3, v3, null);
        return merge0(map, k4, v4, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5) {
        map = merge0(map, k1, v1, null);
        map = merge0(map, k2, v2, null);
        map = merge0(map, k3, v3, null);
        map = merge0(map, k4, v4, null);
        return merge0(map, k5, v5, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6) {
        map = merge0(map, k1, v1, null);
        map = merge0(map, k2, v2, null);
        map = merge0(map, k3, v3, null);
        map = merge0(map, k4, v4, null);
        map = merge0(map, k5, v5, null);
        return merge0(map, k6, v6, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7) {
        map = merge0(map, k1, v1, null);
        map = merge0(map, k2, v2, null);
        map = merge0(map, k3, v3, null);
        map = merge0(map, k4, v4, null);
        map = merge0(map, k5, v5, null);
        map = merge0(map, k6, v6, null);
        return merge0(map, k7, v7, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8) {
        map = merge0(map, k1, v1, null);
        map = merge0(map, k2, v2, null);
        map = merge0(map, k3, v3, null);
        map = merge0(map, k4, v4, null);
        map = merge0(map, k5, v5, null);
        map = merge0(map, k6, v6, null);
        map = merge0(map, k7, v7, null);
        return merge0(map, k8, v8, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8,
            final K k9, final V v9) {
        map = merge0(map, k1, v1, null);
        map = merge0(map, k2, v2, null);
        map = merge0(map, k3, v3, null);
        map = merge0(map, k4, v4, null);
        map = merge0(map, k5, v5, null);
        map = merge0(map, k6, v6, null);
        map = merge0(map, k7, v7, null);
        map = merge0(map, k8, v8, null);
        return merge0(map, k9, v9, null);
    }

    public static <K, V, M extends io.vavr.collection.Map<K, V>> M insert(M map, 
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8,
            final K k9, final V v9,
            final K k10, final V v10) {
        map = merge0(map, k1, v1, null);
        map = merge0(map, k2, v2, null);
        map = merge0(map, k3, v3, null);
        map = merge0(map, k4, v4, null);
        map = merge0(map, k5, v5, null);
        map = merge0(map, k6, v6, null);
        map = merge0(map, k7, v7, null);
        map = merge0(map, k8, v8, null);
        map = merge0(map, k9, v9, null);
        return merge0(map, k10, v10, null);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param k1
     * @param v1
     * @return 
     */
    public static <K, V, U extends V, M extends java.util.Map<K, V>> M set(final M map, 
            final K k1, final V v1) {
        if (!map.containsKey(k1)) {
            throw new NoSuchElementException("Item " + k1 + " does not exist");
        }
        map.put(k1, v1);
        return map;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param k1
     * @param v1
     * @return 
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M set(final M map, 
            final K k1, final V v1) {
        if (!map.containsKey(k1)) {
            throw new NoSuchElementException("Item " + k1 + " does not exist");
        }
        return (M)map.put(k1, v1);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    @Deprecated
    public static <K, V> Comparator<K> comparatorOrNaturalOrder(final java.util.Map<K, V> map) {
        Comparator<K> comp = null;
        if (map != null && (map instanceof java.util.SortedMap)) {
            final java.util.SortedMap sm = java.util.SortedMap.class.cast(map);
            comp = sm.comparator();
        }
        return ObjectUtils.firstNonNull(comp, (Comparator<K>)Comparator.naturalOrder());
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    @Deprecated
    public static <K, V> Comparator<K> comparatorOrNaturalOrder(final io.vavr.collection.Map<K, V> map) {
        Comparator<K> comp = null;
        if (map != null && (map instanceof io.vavr.collection.Ordered)) {
            final io.vavr.collection.Ordered sm = io.vavr.collection.Ordered.class.cast(map);
            comp = sm.comparator();
        }
        return ObjectUtils.firstNonNull(comp, (Comparator<K>)Comparator.naturalOrder());
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data 
     * @return  
     */
    public static <K, V, U extends V, M extends java.util.Map<K, V>> M insertAll(final M map, 
            final java.util.Map<K, U> data) {
        return merge(map, data, (BiFunction<V, V, V>)null);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @param merger
     * @return 
     */
    public static <K, V, U extends V, M extends java.util.Map<K, V>> M merge(final M map, 
            final java.util.Map<K, U> data, 
            final BiFunction<? super V, ? super V, ? extends V> merger) {
        return merge(map, IterableAccessors.ofAll(data.entrySet()).map(mapEntryToTuple()), merger);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @param merger 
     * @return  
     */
    public static <K, V, U extends V, M extends java.util.Map<K, V>> M merge(final M map, 
            final java.util.Map<K, U> data, 
            final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        return merge(map, IterableAccessors.ofAll(data.entrySet()).map(mapEntryToTuple()), merger);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @return 
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M insertAll(final M map, 
            final java.util.Map<K, U> data) {
        return merge(map, data, (BiFunction<V, V, V>)null);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @param merger
     * @return 
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M merge(final M map, 
            final java.util.Map<K, U> data, 
            final BiFunction<? super V, ? super V, ? extends V> merger) {
        return merge(map, IterableAccessors.ofAll(data.entrySet()).map(mapEntryToTuple()), merger);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @param merger
     * @return 
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M merge(final M map, 
            final java.util.Map<K, U> data, 
            final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        return merge(map, IterableAccessors.ofAll(data.entrySet()).map(mapEntryToTuple()), merger);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data 
     * @return  
     */
    public static <K, V, U extends V, M extends java.util.Map<K, V>> M insertAll(final M map, 
            final Iterable<Tuple2<K, U>> data) {
        return merge(map, data, (BiFunction<V, V, V>)null);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @param merger
     * @return 
     */
    public static <K, V, M extends java.util.Map<K, V>> M merge(final M map, 
            final K key,
            final V value,
            final BiFunction<? super V, ? super V, ? extends V> merger) {
        return merge0(map, key, value, merger);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @param merger
     * @return 
     */
    public static <K, V, M extends java.util.Map<K, V>> M merge(final M map, 
            final K key,
            final V value,
            final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        return merge0(map, key, value, merger);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @param merger
     * @return 
     */
    static <K, V, M extends java.util.Map<K, V>> M merge0(final M map, 
            final K key,
            final V value,
            final Object merger) {
        if (map.containsKey(key)) {
            final V prevValue = map.get(key);
            if (merger == null) {
                return Mergers.throwDuplicates(key, prevValue, value); // Will throw exception
            } else {
                V newValue;
                if (merger instanceof BiFunction) {
                    final BiFunction<V, V, V> f = BiFunction.class.cast(merger);
                    newValue = f.apply(prevValue, value);
                } else {
                    if (merger instanceof Function3) {
                        final Function3<K, V, V, V> f = Function3.class.cast(merger);
                        newValue = f.apply(key, prevValue, value);
                    } else {
                        throw new IllegalArgumentException(merger + " is not a merger function");
                    }
                }
                if (newValue != prevValue) {
                    map.put(key, newValue);
                }
            }
        } else {
            map.put(key, value);
        }
        return map;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @param merger
     * @return 
     */
    public static <K, V, U extends V, M extends java.util.Map<K, V>> M merge(final M map, 
            final Iterable<Tuple2<K, U>> data, 
            final BiFunction<? super V, ? super V, ? extends V> merger) {
        for (final Tuple2<K, U> e : data) {
            merge0(map, e._1, e._2, merger);
        }
        return map;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @param merger 
     * @return  
     */
    public static <K, V, U extends V, M extends java.util.Map<K, V>> M merge(final M map, 
            final Iterable<Tuple2<K, U>> data, 
            final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        for (final Tuple2<K, U> e : data) {
            merge0(map, e._1, e._2, merger);
        }
        return map;
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @return 
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M insertAll(M map, 
            final Iterable<Tuple2<K, U>> data) {
        return merge(map, data, (BiFunction<V, V, V>)null);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @param merger
     * @return 
     */
    public static <K, V, M extends io.vavr.collection.Map<K, V>> M merge(M map, 
            final K key,
            final V value,
            final BiFunction<? super V, ? super V, ? extends V> merger) {
        return merge0(map, key, value, merger);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @param merger
     * @return 
     */
    public static <K, V, M extends io.vavr.collection.Map<K, V>> M merge(M map, 
            final K key,
            final V value,
            final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        return merge0(map, key, value, merger);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @param value
     * @param merger
     * @return 
     */
    static <K, V, M extends io.vavr.collection.Map<K, V>> M merge0(M map, 
            final K key,
            final V value,
            final Object merger) {
        Option<V> opt = map.get(key);
        if (opt.isDefined()) {
            final V prevValue = opt.get();
            if (merger == null) {
                return Mergers.throwDuplicates(key, prevValue, value); 
            } else {
                V newValue;
                if (merger instanceof BiFunction) {
                    final BiFunction<V, V, V> f = BiFunction.class.cast(merger);
                    newValue = f.apply(prevValue, value);
                } else {
                    if (merger instanceof Function3) {
                        final Function3<K, V, V, V> f = Function3.class.cast(merger);
                        newValue = f.apply(key, prevValue, value);
                    } else {
                        throw new IllegalArgumentException(merger + " is not a merger function");
                    }
                }
                if (newValue != prevValue) {
                    return (M)map.put(key, newValue);
                } else {
                    return map;
                }
            }
        } else {
            return (M)map.put(key, value);
        }
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @param merger
     * @return 
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M merge(M map, 
            final Iterable<Tuple2<K, U>> data, 
            final BiFunction<? super V, ? super V, ? extends V> merger) {
        for (final Tuple2<K, U> e : data) {
            map = merge0(map, e._1, e._2, merger);
        }
        return map;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <U>
     * @param <M>
     * @param map
     * @param data
     * @param merger
     * @return 
     */
    public static <K, V, U extends V, M extends io.vavr.collection.Map<K, V>> M merge(M map, 
            final Iterable<Tuple2<K, U>> data, 
            final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        for (final Tuple2<K, U> e : data) {
            map = merge0(map, e._1, e._2, merger);
        }
        return map;
    }
    
}
