/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.Tuple2;
import java.util.Objects;
import java.util.function.Predicate;
import static org.joytools.commons.accessors.AccessorFunctions.mapEntryToTuple;
import org.joytools.commons.collections.IterableAccessors;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public interface FixedSizeMapMutator<K, V> extends MapAccessor<K, V>, Mutator<V> {
 
    @Override
    default <Z> FixedSizeMapMutator<K, Z> castAs(final Class<Z> clazz) {
        return (FixedSizeMapMutator<K, Z>)this;
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param val
     * @return 
     */
    public <U extends V> boolean update(final K key, final U val);

    /**
     * 
     * @param tuple
     * @return 
     */
    default public boolean update(final Tuple2<K, V> tuple) {
        return update(tuple._1, tuple._2);
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2 
     */
    default void update(
            final K key1, final V val1,
            final K key2, final V val2) {
        update(key1, val1);
        update(key2, val2);
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
    default void update(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3) {
        update(key1, val1);
        update(key2, val2);
        update(key3, val3);
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
    default void update(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4) {
        update(key1, val1);
        update(key2, val2);
        update(key3, val3);
        update(key4, val4);
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
    default void update(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5) {
        update(key1, val1);
        update(key2, val2);
        update(key3, val3);
        update(key4, val4);
        update(key5, val5);
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
    default void update(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6) {
        update(key1, val1);
        update(key2, val2);
        update(key3, val3);
        update(key4, val4);
        update(key5, val5);
        update(key6, val6);
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
    default void update(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7) {
        update(key1, val1);
        update(key2, val2);
        update(key3, val3);
        update(key4, val4);
        update(key5, val5);
        update(key6, val6);
        update(key7, val7);
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
    default void update(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7,
            final K key8, final V val8) {
        update(key1, val1);
        update(key2, val2);
        update(key3, val3);
        update(key4, val4);
        update(key5, val5);
        update(key6, val6);
        update(key7, val7);
        update(key8, val8);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int updateAll(final java.util.Map<K, V> map) {
        return updateAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int updateAll(final Iterable<Tuple2<K, V>> map) {
        return updateAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int updateAll(final MapMutator<K, V> map) {
        return updateAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int updateAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return updateAll(IterableAccessors.ofAll(map.entrySet())
                .map(mapEntryToTuple()), keyFilter, valueFilter);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int updateAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        int count = 0;
        for (final Tuple2<K, V> e : map) {
            if ((keyFilter == null || keyFilter.test(e._1))
                    && (valueFilter == null || valueFilter.test(e._2))) {
                update(e._1, e._2);
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
    default public int updateAll(final MapMutator<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return updateAll(map.entriesByKey(), keyFilter, valueFilter);
    }

    /**
     * 
     * @param <U>
     * @param key must exist
     * @param val 
     * @return  
     */
    public <U extends V> U set(final K key, final U val);

    /**
     * 
     * @param <U>
     * @param tuple
     * @return 
     */
    default public <U extends V> Tuple2<K, U> set(final Tuple2<K, U> tuple) {
        set(tuple._1, tuple._2);
        return tuple;
    }

    /**
     * 
     * @param key1
     * @param val1
     * @param key2
     * @param val2 
     */
    default void set(
            final K key1, final V val1,
            final K key2, final V val2) {
        set(key1, val1);
        set(key2, val2);
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
    default void set(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3) {
        set(key1, val1);
        set(key2, val2);
        set(key3, val3);
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
    default void set(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4) {
        set(key1, val1);
        set(key2, val2);
        set(key3, val3);
        set(key4, val4);
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
    default void set(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5) {
        set(key1, val1);
        set(key2, val2);
        set(key3, val3);
        set(key4, val4);
        set(key5, val5);
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
    default void set(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6) {
        set(key1, val1);
        set(key2, val2);
        set(key3, val3);
        set(key4, val4);
        set(key5, val5);
        set(key6, val6);
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
    default void set(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7) {
        set(key1, val1);
        set(key2, val2);
        set(key3, val3);
        set(key4, val4);
        set(key5, val5);
        set(key6, val6);
        set(key7, val7);
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
    default void set(
            final K key1, final V val1,
            final K key2, final V val2, 
            final K key3, final V val3, 
            final K key4, final V val4,
            final K key5, final V val5,
            final K key6, final V val6,
            final K key7, final V val7,
            final K key8, final V val8) {
        set(key1, val1);
        set(key2, val2);
        set(key3, val3);
        set(key4, val4);
        set(key5, val5);
        set(key6, val6);
        set(key7, val7);
        set(key8, val8);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int setAll(final java.util.Map<K, V> map) {
        return setAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int setAll(final Iterable<Tuple2<K, V>> map) {
        return setAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @return 
     */
    default public int setAll(final MapAccessor<K, V> map) {
        return setAll(map, null, null);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int setAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return setAll(IterableAccessors.ofAll(map.entrySet())
                .map(mapEntryToTuple()), keyFilter, valueFilter);
    }

    /**
     * 
     * @param map
     * @param keyFilter
     * @param valueFilter
     * @return 
     */
    default public int setAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        int count = 0;
        for (final Tuple2<K, V> e : map) {
            if ((keyFilter == null || keyFilter.test(e._1))
                    && (valueFilter == null || valueFilter.test(e._2))) {
                set(e._1, e._2);
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
    default public int setAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        Objects.requireNonNull(map, "Map");
        return setAll(map.entriesByKey(), keyFilter, valueFilter);
    }

    /**
     * 
     * @param from
     * @param to
     * @return 
     */
    default public V overwrite(final K from, final K to) {
        return MutatorImplUtils.defaultOverwrite(this, from, to);
    }

    /**
     * 
     * @param from
     * @param to
     * @param others 
     */
    default public void overwrite(final K from, final K to, final K... others) {
        if (others.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of arguments");
        }
        overwrite(from, to);
        for (int i = 0; i < others.length; i += 2) {
            overwrite(others[i], others[i + 1]);
        }
    }
    
}
