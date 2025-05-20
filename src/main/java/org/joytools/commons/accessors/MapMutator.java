/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.Function3;
import io.vavr.Tuple2;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import static org.joytools.commons.accessors.AccessorFunctions.mapEntryToTuple;
import org.joytools.commons.collections.IterableAccessors;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public interface MapMutator<K, V> extends FixedSizeMapMutator<K, V> {
 
    /**
     * 
     * @param key
     * @param supplier
     * @return 
     */
    default public V getOrNew(final K key, final Supplier<V> supplier) {
        return MutatorImplUtils.defaultGetOrNew(this, key, supplier);
    }

    /**
     * 
     * @param key
     * @param defVal
     * @return 
     */
    default public V getOrNew(final K key, final V defVal) {
        return MutatorImplUtils.defaultGetOrNew(this, key, defVal);
    }

    /**
     * 
     * @param <U>
     * @param data 
     */
    default <U extends V> void merge(final Iterable<Tuple2<K, U>> data) {
        merge(data, null);
    }

    /**
     * 
     * @param <U>
     * @param data
     * @param merger 
     */
    default <U extends V> void merge(final Iterable<Tuple2<K, U>> data, final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        MutatorImplUtils.defaultMerge(this, data, merger);
    }

    /**
     * 
     * @param <U>
     * @param data 
     */
    default <U extends V> void merge(final MapAccessor<K, U> data) {
        merge(data, null);
    }

    /**
     * 
     * @param data
     * @param merger 
     */
    default <U extends V> void merge(final MapAccessor<K, U> data, final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        merge(data.entriesByKey(), merger);
    }

    /**
     * 
     * @param data 
     */
    default <U extends V> void merge(final java.util.Map<K, U> data) {
        merge(data, null);
    }

    /**
     * 
     * @param data
     * @param merger 
     */
    default <U extends V> void merge(final java.util.Map<K, U> data, final Function3<? super K, ? super V, ? super V, ? extends V> merger) {
        merge(IterableAccessors.ofAll(data.entrySet()).map(mapEntryToTuple()), merger);
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param val 
     * @return  
     */
    public <U extends V> U put(final K key, final U val);
    
    /**
     * 
     * @param <U>
     * @param tuple
     * @return 
     */
    default public <U extends V> Tuple2<K, U> put(final Tuple2<K, U> tuple) {
        put(tuple._1, tuple._2);
        return tuple;
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2 
     */
    default void put(
            final K key1, final V val1,
            final K key2, final V val2) {
        put(key1, val1);
        put(key2, val2);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3 
     */
    default void put(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3) {
        put(key1, val1);
        put(key2, val2);
        put(key3, val3);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4 
     */
    default void put(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4) {
        put(key1, val1);
        put(key2, val2);
        put(key3, val3);
        put(key4, val4);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5 
     */
    default void put(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5) {
        put(key1, val1);
        put(key2, val2);
        put(key3, val3);
        put(key4, val4);
        put(key5, val5);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6 
     */
    default void put(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6) {
        put(key1, val1);
        put(key2, val2);
        put(key3, val3);
        put(key4, val4);
        put(key5, val5);
        put(key6, val6);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6
     * @param key7
     * @param val7 
     */
    default void put(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7) {
        put(key1, val1);
        put(key2, val2);
        put(key3, val3);
        put(key4, val4);
        put(key5, val5);
        put(key6, val6);
        put(key7, val7);
    }
    
    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6
     * @param key7
     * @param val7
     * @param key8
     * @param val8 
     */
    default void put(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7,
            final K key8, final V val8) {
        put(key1, val1);
        put(key2, val2);
        put(key3, val3);
        put(key4, val4);
        put(key5, val5);
        put(key6, val6);
        put(key7, val7);
        put(key8, val8);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int putAll(final java.util.Map<K, V> map) {
        return putAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int putAll(final Iterable<Tuple2<K, V>> map) {
        return putAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int putAll(final MapAccessor<K, V> map) {
        return putAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int putAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return putAll(IterableAccessors.ofAll(map.entrySet())
                .map(mapEntryToTuple()), keyFilter, valueFilter);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int putAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        int count = 0;
        for (final Tuple2<K, V> e : map) {
            if ((keyFilter == null || keyFilter.test(e._1))
                    && (valueFilter == null || valueFilter.test(e._2))) {
                put(e._1, e._2);
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int putAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return putAll(map.entriesByKey(), keyFilter, valueFilter);
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param val
     * @return 
     */
    default public <U extends V> U putIfAbsent(final K key, final U val) {
        if (contains(key)) {
            return val;
        }
        return put(key, val);
    }
    
    /**
     * 
     * @param tuple
     * @return 
     */
    default public Tuple2<K, V> putIfAbsent(final Tuple2<K, V> tuple) {
        putIfAbsent(tuple._1, tuple._2);
        return tuple;
    }
    
    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2 
     */
    default void putIfAbsent(
            final K key1, final V val1,
            final K key2, final V val2) {
        putIfAbsent(key1, val1);
        putIfAbsent(key2, val2);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3 
     */
    default void putIfAbsent(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3) {
        putIfAbsent(key1, val1);
        putIfAbsent(key2, val2);
        putIfAbsent(key3, val3);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4 
     */
    default void putIfAbsent(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4) {
        putIfAbsent(key1, val1);
        putIfAbsent(key2, val2);
        putIfAbsent(key3, val3);
        putIfAbsent(key4, val4);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5 
     */
    default void putIfAbsent(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5) {
        putIfAbsent(key1, val1);
        putIfAbsent(key2, val2);
        putIfAbsent(key3, val3);
        putIfAbsent(key4, val4);
        putIfAbsent(key5, val5);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6 
     */
    default void putIfAbsent(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6) {
        putIfAbsent(key1, val1);
        putIfAbsent(key2, val2);
        putIfAbsent(key3, val3);
        putIfAbsent(key4, val4);
        putIfAbsent(key5, val5);
        putIfAbsent(key6, val6);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6
     * @param key7
     * @param val7 
     */
    default void putIfAbsent(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7) {
        putIfAbsent(key1, val1);
        putIfAbsent(key2, val2);
        putIfAbsent(key3, val3);
        putIfAbsent(key4, val4);
        putIfAbsent(key5, val5);
        putIfAbsent(key6, val6);
        putIfAbsent(key7, val7);
    }
    
    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6
     * @param key7
     * @param val7
     * @param key8
     * @param val8 
     */
    default void putIfAbsent(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7,
            final K key8, final V val8) {
        putIfAbsent(key1, val1);
        putIfAbsent(key2, val2);
        putIfAbsent(key3, val3);
        putIfAbsent(key4, val4);
        putIfAbsent(key5, val5);
        putIfAbsent(key6, val6);
        putIfAbsent(key7, val7);
        putIfAbsent(key8, val8);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int putIfAbsentAll(final java.util.Map<K, V> map) {
        return putIfAbsentAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int putIfAbsentAll(final Iterable<Tuple2<K, V>> map) {
        return putIfAbsentAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int putIfAbsentAll(final MapAccessor<K, V> map) {
        return putIfAbsentAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int putIfAbsentAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return putIfAbsentAll(IterableAccessors.ofAll(map.entrySet())
                .map(mapEntryToTuple()), keyFilter, valueFilter);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int putIfAbsentAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        int count = 0;
        for (final Tuple2<K, V> e : map) {
            if ((keyFilter == null || keyFilter.test(e._1))
                    && (valueFilter == null || valueFilter.test(e._2))) {
                putIfAbsent(e._1, e._2);
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int putIfAbsentAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return putIfAbsentAll(map.entriesByKey(), keyFilter, valueFilter);
    }

    /**
     * 
     * @param key 
     * @return  
     */
    public boolean delete(final K key);
    
    /**
     * 
     * @param key
     * @param keys 
     */
    default void delete(final K key, final K... keys) {
        delete(key);
        for (final K k : keys) {
            delete(k);
        }
    }
    
    /**
     * 
     * @param keys 
     */
    default void deleteAll(final Iterable<K> keys) {
        for (final K key : keys) {
            delete(key);
        }
    }

    /**
     * 
     * @param from
     * @param to
     * @return 
     */
    default public V copy(final K from, final K to) {
        return MutatorImplUtils.defaultCopy(this, from, to);
    }

    /**
     * 
     * @param from
     * @param to
     * @param others 
     */
    default public void copy(final K from, final K to, final K... others) {
        if (others.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        copy(from, to);
        for (int i = 0; i < others.length; i += 2) {
            copy(others[i], others[i + 1]);
        }
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param val
     * @return 
     */ /*
    default public <U extends V> U insert(final K key, final U val) {
        return MutatorImplUtils.defaultInsert(this, key, val);
    } */
    public <U extends V> U insert(final K key, final U val);

    /**
     * 
     * @param <U>
     * @param tuple
     * @return 
     */
    default public <U extends V> Tuple2<K, U> insert(final Tuple2<K, U> tuple) {
        insert(tuple._1, tuple._2);
        return tuple;
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2 
     */
    default void insert(
            final K key1, final V val1,
            final K key2, final V val2) {
        insert(key1, val1);
        insert(key2, val2);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3 
     */
    default void insert(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3) {
        insert(key1, val1);
        insert(key2, val2);
        insert(key3, val3);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4 
     */
    default void insert(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4) {
        insert(key1, val1);
        insert(key2, val2);
        insert(key3, val3);
        insert(key4, val4);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5 
     */
    default void insert(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5) {
        insert(key1, val1);
        insert(key2, val2);
        insert(key3, val3);
        insert(key4, val4);
        insert(key5, val5);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6 
     */
    default void insert(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6) {
        insert(key1, val1);
        insert(key2, val2);
        insert(key3, val3);
        insert(key4, val4);
        insert(key5, val5);
        insert(key6, val6);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6
     * @param key7
     * @param val7 
     */
    default void insert(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7) {
        insert(key1, val1);
        insert(key2, val2);
        insert(key3, val3);
        insert(key4, val4);
        insert(key5, val5);
        insert(key6, val6);
        insert(key7, val7);
    }
    
    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2
     * @param key3
     * @param val3
     * @param key4
     * @param val4
     * @param key5
     * @param val5
     * @param key6
     * @param val6
     * @param key7
     * @param val7
     * @param key8
     * @param val8 
     */
    default void insert(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7,
            final K key8, final V val8) {
        insert(key1, val1);
        insert(key2, val2);
        insert(key3, val3);
        insert(key4, val4);
        insert(key5, val5);
        insert(key6, val6);
        insert(key7, val7);
        insert(key8, val8);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int insertAll(final java.util.Map<K, V> map) {
        return insertAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int insertAll(final Iterable<Tuple2<K, V>> map) {
        return insertAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int insertAll(final MapAccessor<K, V> map) {
        return insertAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int insertAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return insertAll(IterableAccessors.ofAll(map.entrySet())
                .map(mapEntryToTuple()), keyFilter, valueFilter);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int insertAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        int count = 0;
        for (final Tuple2<K, V> e : map) {
            if ((keyFilter == null || keyFilter.test(e._1))
                    && (valueFilter == null || valueFilter.test(e._2))) {
                insert(e._1, e._2);
                count++;
            }
        }
        return count;
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int insertAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return insertAll(map.entriesByKey(), keyFilter, valueFilter);
    }

    /**
     * 
     * @param from
     * @param to
     * @return 
     */
    default public V rename(final K from, final K to) {
        return MutatorImplUtils.defaultRename(this, from, to);
    }

    /**
     * 
     * @param from
     * @param to
     * @param others 
     */
    default public void rename(final K from, final K to, final K... others) {
        if (others.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        rename(from, to);
        for (int i = 0; i < others.length; i += 2) {
            rename(others[i], others[i + 1]);
        }
    }

    /**
     * 
     * @param key 
     */ /*
    default public void remove(final K key) {
        MutatorImplUtils.defaultRemove(this, key);
    } */
    public void remove(final K key);

    /**
     * 
     * @param key
     * @param keys 
     */
    default void remove(final K key, final K... keys) {
        remove(key);
        for (final K k : keys) {
            remove(k);
        }
    }
    
    /**
     * 
     * @param keys 
     */
    default void removeAll(final Iterable<K> keys) {
        for (final K key : keys) {
            remove(key);
        }
    }

    /**
     * 
     * @param <T>
     * @param <R> 
     */
    public interface Fluent<T, R> {

        //===== INSERT =====
        
        Fluent<T, R> insertAll(final java.util.Map<T, R> map);

        Fluent<T, R> insertAll(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> insertAllKeys(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter);

        default Fluent<T, R> insertAllKeys(final java.util.Map<T, R> map, 
                final T... keys) {
            return insertAllKeys(Accessors.of(map), keys);
        }

        Fluent<T, R> insertAllValues(final java.util.Map<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> insertAll(final Iterable<Tuple2<T, R>> map);

        Fluent<T, R> insertAll(final Iterable<Tuple2<T, R>> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> insertAllKeys(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> insertAllValues(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> insertAll(final MapAccessor<T, R> map);

        Fluent<T, R> insertAll(final MapAccessor<T, R> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> insertAllKeys(final MapAccessor<T, R> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> insertAllKeys(final MapAccessor<T, R> map, 
                final T... keys);

        Fluent<T, R> insertAllValues(final MapAccessor<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> insert(final T k1, final R v1);
        
        Fluent<T, R> insert(final T k1, final R v1, final T k2, final R v2);
        
        Fluent<T, R> insert(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3);
        
        Fluent<T, R> insert(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4);
        
        Fluent<T, R> insert(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5);
        
        Fluent<T, R> insert(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6);
        
        Fluent<T, R> insert(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7);
        
        Fluent<T, R> insert(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7, final T k8, final R v8);

        //===== UPDATE =====
        
        Fluent<T, R> updateAll(final java.util.Map<T, R> map);

        Fluent<T, R> updateAll(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> updateAllKeys(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter);

        default Fluent<T, R> updateAllKeys(final java.util.Map<T, R> map, 
                final T... keys) {
            return updateAllKeys(Mutators.of(map), keys);
        }

        Fluent<T, R> updateAllValues(final java.util.Map<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> updateAll(final Iterable<Tuple2<T, R>> map);

        Fluent<T, R> updateAll(final Iterable<Tuple2<T, R>> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> updateAllKeys(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> updateAllValues(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> updateAll(final MapAccessor<T, R> map);

        Fluent<T, R> updateAll(final MapAccessor<T, R> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> updateAllKeys(final MapAccessor<T, R> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> updateAllKeys(final MapAccessor<T, R> map, 
                final T... keys);

        Fluent<T, R> updateAllValues(final MapAccessor<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> update(final T k1, final R v1);
        
        Fluent<T, R> update(final T k1, final R v1, final T k2, final R v2);
        
        Fluent<T, R> update(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3);
        
        Fluent<T, R> update(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4);
        
        Fluent<T, R> update(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5);
        
        Fluent<T, R> update(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6);
        
        Fluent<T, R> update(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7);
        
        Fluent<T, R> update(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7, final T k8, final R v8);

        //===== PUT =====
        
        Fluent<T, R> putAll(final java.util.Map<T, R> map);

        Fluent<T, R> putAll(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putAllKeys(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter);

        default Fluent<T, R> putAllKeys(final java.util.Map<T, R> map, 
                final T... keys) {
            return putAllKeys(Mutators.of(map), keys);
        }

        Fluent<T, R> putAllValues(final java.util.Map<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putAll(final Iterable<Tuple2<T, R>> map);

        Fluent<T, R> putAll(final Iterable<Tuple2<T, R>> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putAllKeys(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> putAllValues(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putAll(final MapAccessor<T, R> map);

        Fluent<T, R> putAll(final MapAccessor<T, R> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putAllKeys(final MapAccessor<T, R> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> putAllKeys(final MapAccessor<T, R> map, 
                final T... keys);

        Fluent<T, R> putAllValues(final MapAccessor<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> put(final T k1, final R v1);
        
        Fluent<T, R> put(final T k1, final R v1, final T k2, final R v2);
        
        Fluent<T, R> put(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3);
        
        Fluent<T, R> put(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4);
        
        Fluent<T, R> put(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5);
        
        Fluent<T, R> put(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6);
        
        Fluent<T, R> put(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7);
        
        Fluent<T, R> put(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7, final T k8, final R v8);
        
        //===== PUTIFABSENT =====
        
        Fluent<T, R> putIfAbsentAll(final java.util.Map<T, R> map);

        Fluent<T, R> putIfAbsentAll(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putIfAbsentAllKeys(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter);

        default Fluent<T, R> putIfAbsentAllKeys(final java.util.Map<T, R> map, 
                final T... keys) {
            return putAllKeys(Mutators.of(map), keys);
        }

        Fluent<T, R> putIfAbsentAllValues(final java.util.Map<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putIfAbsentAll(final Iterable<Tuple2<T, R>> map);

        Fluent<T, R> putIfAbsentAll(final Iterable<Tuple2<T, R>> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putIfAbsentAllKeys(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> putIfAbsentAllValues(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putIfAbsentAll(final MapAccessor<T, R> map);

        Fluent<T, R> putIfAbsentAll(final MapAccessor<T, R> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putIfAbsentAllKeys(final MapAccessor<T, R> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> putIfAbsentAllKeys(final MapAccessor<T, R> map, 
                final T... keys);

        Fluent<T, R> putIfAbsentAllValues(final MapAccessor<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> putIfAbsent(final T k1, final R v1);
        
        Fluent<T, R> putIfAbsent(final T k1, final R v1, final T k2, final R v2);
        
        Fluent<T, R> putIfAbsent(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3);
        
        Fluent<T, R> putIfAbsent(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4);
        
        Fluent<T, R> putIfAbsent(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5);
        
        Fluent<T, R> putIfAbsent(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6);
        
        Fluent<T, R> putIfAbsent(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7);
        
        Fluent<T, R> putIfAbsent(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7, final T k8, final R v8);

        //===== SET =====
        
        Fluent<T, R> setAll(final java.util.Map<T, R> map);

        Fluent<T, R> setAll(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> setAllKeys(final java.util.Map<T, R> map, 
                final Predicate<? super T> keyFilter);

        default Fluent<T, R> setAllKeys(final java.util.Map<T, R> map, 
                final T... keys) {
            return setAllKeys(Mutators.of(map), keys);
        }

        Fluent<T, R> setAllValues(final java.util.Map<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> setAll(final Iterable<Tuple2<T, R>> map);

        Fluent<T, R> setAll(final Iterable<Tuple2<T, R>> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> setAllKeys(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> setAllValues(final Iterable<Tuple2<T, R>> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> setAll(final MapAccessor<T, R> map);

        Fluent<T, R> setAll(final MapAccessor<T, R> map,
                final Predicate<? super T> keyFilter, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> setAllKeys(final MapAccessor<T, R> map, 
                final Predicate<? super T> keyFilter);

        Fluent<T, R> setAllKeys(final MapAccessor<T, R> map, 
                final T... keys);

        Fluent<T, R> setAllValues(final MapAccessor<T, R> map, 
                final Predicate<? super R> valueFilter);

        Fluent<T, R> set(final T k1, final R v1);
        
        Fluent<T, R> set(final T k1, final R v1, final T k2, final R v2);
        
        Fluent<T, R> set(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3);
        
        Fluent<T, R> set(final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4);
        
        Fluent<T, R> set(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5);
        
        Fluent<T, R> set(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6);
        
        Fluent<T, R> set(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7);
        
        Fluent<T, R> set(
                final T k1, final R v1, final T k2, final R v2, final T k3, final R v3, final T k4, final R v4,
                final T k5, final R v5, final T k6, final R v6, final T k7, final R v7, final T k8, final R v8);

        //===== DELETE =====

        Fluent<T, R> delete(final T... keys);

        Fluent<T, R> deleteAll(final Iterable<T> keys);

        //===== REMOVE =====

        Fluent<T, R> remove(final T... keys);
        
        Fluent<T, R> removeAll(final Iterable<T> keys);
        
        //===== RENAME =====
        
        Fluent<T, R> rename(final T a1, final T a2);
        
        Fluent<T, R> rename(final T a1, final T a2, final T b1, final T b2);
        
        Fluent<T, R> rename(final T a1, final T a2, final T b1, final T b2, final T c1, final T c2);
        
        Fluent<T, R> rename(final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2);
        
        Fluent<T, R> rename(
                final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2,
                final T e1, final T e2);
        
        Fluent<T, R> rename(
                final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2,
                final T e1, final T e2, final T f1, final T f2);
        
        Fluent<T, R> rename(
                final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2,
                final T e1, final T e2, final T f1, final T f2, final T g1, final T g2);
        
        Fluent<T, R> rename(
                final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2,
                final T e1, final T e2, final T f1, final T f2, final T g1, final T g2, final T h1, final T h2);
        
        //===== OVERWRITE =====
        
        Fluent<T, R> overwrite(final T a1, final T a2);
        
        Fluent<T, R> overwrite(final T a1, final T a2, final T b1, final T b2);
        
        Fluent<T, R> overwrite(final T a1, final T a2, final T b1, final T b2, final T c1, final T c2);
        
        Fluent<T, R> overwrite(final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2);
        
        Fluent<T, R> overwrite(
                final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2,
                final T e1, final T e2);
        
        Fluent<T, R> overwrite(
                final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2,
                final T e1, final T e2, final T f1, final T f2);
        
        Fluent<T, R> overwrite(
                final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2,
                final T e1, final T e2, final T f1, final T f2, final T g1, final T g2);

        Fluent<T, R> overwrite(
                final T a1, final T a2, final T b1, final T b2, final T c1, final T c2, final T d1, final T d2,
                final T e1, final T e2, final T f1, final T f2, final T g1, final T g2, final T h1, final T h2);       
        
    }
    
    /**
     * 
     * @param <Z>
     * @param type
     * @return 
     */
    @Override
    default <Z> MapMutator<K, Z> castAs(final Class<Z> type) {
        return (MapMutator<K, Z>)this;
    }
    
    /**
     * 
     * @return 
     */
    default Fluent<K, V> fluent() {
        return new MapMutatorFluentImpl(this);
    }
    
}
