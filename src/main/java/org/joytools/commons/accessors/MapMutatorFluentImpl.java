/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.Tuple2;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import org.joytools.commons.function.Predicates;

/**
 *
 * @author AndreaR
 */
class MapMutatorFluentImpl<K, V> implements MapMutator.Fluent<K, V> {
    
    MapMutatorFluentImpl(final MapMutator<K, V> ma) {
        this.ma = Objects.requireNonNull(ma, "MapMutator");
    }
    
    //===== INSERT =====

    @Override
    public MapMutator.Fluent<K, V> insertAll(final java.util.Map<K, V> map) {
        return insertAll(map, Predicates.alwaysTrue(), Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> insertAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Map.Entry<K, V> e : map.entrySet()) {
            final K k = e.getKey();
            final V v = e.getValue();
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.insert(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insertAllKeys(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter) {
        return insertAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> insertAllValues(final java.util.Map<K, V> map,
            final Predicate<? super V> valueFilter) {
        return insertAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> insertAll(final Iterable<Tuple2<K, V>> map) {
        for (final Tuple2<K, V> e : map) {
            ma.insert(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insertAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.insert(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insertAllKeys(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter) {
        return insertAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> insertAllValues(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super V> valueFilter) {
        return insertAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> insertAll(final MapAccessor<K, V> map) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            ma.insert(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insertAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.insert(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insertAllKeys(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter) {
        return insertAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> insertAllKeys(final MapAccessor<K, V> map,
            final K... keys) {
        for (final K key : keys) {
            ma.insert(key, map.getOrFail(key));
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insertAllValues(final MapAccessor<K, V> map,
            final Predicate<? super V> valueFilter) {
        return insertAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> insert(
            final K k1, final V v1) {
        ma.insert(k1, v1);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insert(
            final K k1, final V v1,
            final K k2, final V v2) {
        ma.insert(k1, v1);
        ma.insert(k2, v2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insert(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3) {
        ma.insert(k1, v1);
        ma.insert(k2, v2);
        ma.insert(k3, v3);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insert(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4) {
        ma.insert(k1, v1);
        ma.insert(k2, v2);
        ma.insert(k3, v3);
        ma.insert(k4, v4);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insert(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5) {
        ma.insert(k1, v1);
        ma.insert(k2, v2);
        ma.insert(k3, v3);
        ma.insert(k4, v4);
        ma.insert(k5, v5);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insert(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6) {
        ma.insert(k1, v1);
        ma.insert(k2, v2);
        ma.insert(k3, v3);
        ma.insert(k4, v4);
        ma.insert(k5, v5);
        ma.insert(k6, v6);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insert(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7) {
        ma.insert(k1, v1);
        ma.insert(k2, v2);
        ma.insert(k3, v3);
        ma.insert(k4, v4);
        ma.insert(k5, v5);
        ma.insert(k6, v6);
        ma.insert(k7, v7);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> insert(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8) {
        ma.insert(k1, v1);
        ma.insert(k2, v2);
        ma.insert(k3, v3);
        ma.insert(k4, v4);
        ma.insert(k5, v5);
        ma.insert(k6, v6);
        ma.insert(k7, v7);
        ma.insert(k8, v8);
        return this;
    }

    //===== DELETE =====

    @Override
    public MapMutator.Fluent<K, V> delete(final K... keys) {
        for (final K key : keys) {
            ma.delete(key);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> deleteAll(final Iterable<K> keys) {
        for (final K key : keys) {
            ma.delete(key);
        }
        return this;
    }

    //===== UPDATE =====

    @Override
    public MapMutator.Fluent<K, V> updateAll(final java.util.Map<K, V> map) {
        return updateAll(map, Predicates.alwaysTrue(), Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> updateAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Map.Entry<K, V> e : map.entrySet()) {
            final K k = e.getKey();
            final V v = e.getValue();
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.update(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> updateAllKeys(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter) {
        return updateAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> updateAllValues(final java.util.Map<K, V> map,
            final Predicate<? super V> valueFilter) {
        return updateAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> updateAll(final Iterable<Tuple2<K, V>> map) {
        for (final Tuple2<K, V> e : map) {
            ma.update(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> updateAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.update(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> updateAllKeys(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter) {
        return updateAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> updateAllValues(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super V> valueFilter) {
        return updateAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> updateAll(final MapAccessor<K, V> map) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            ma.update(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> updateAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.update(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> updateAllKeys(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter) {
        return updateAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> updateAllKeys(final MapAccessor<K, V> map,
            final K... keys) {
        for (final K key : keys) {
            ma.update(key, map.getOrFail(key));
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> updateAllValues(final MapAccessor<K, V> map,
            final Predicate<? super V> valueFilter) {
        return updateAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> update(
            final K k1, final V v1) {
        ma.update(k1, v1);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> update(
            final K k1, final V v1,
            final K k2, final V v2) {
        ma.update(k1, v1);
        ma.update(k2, v2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> update(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3) {
        ma.update(k1, v1);
        ma.update(k2, v2);
        ma.update(k3, v3);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> update(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4) {
        ma.update(k1, v1);
        ma.update(k2, v2);
        ma.update(k3, v3);
        ma.update(k4, v4);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> update(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5) {
        ma.update(k1, v1);
        ma.update(k2, v2);
        ma.update(k3, v3);
        ma.update(k4, v4);
        ma.update(k5, v5);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> update(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6) {
        ma.update(k1, v1);
        ma.update(k2, v2);
        ma.update(k3, v3);
        ma.update(k4, v4);
        ma.update(k5, v5);
        ma.update(k6, v6);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> update(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7) {
        ma.update(k1, v1);
        ma.update(k2, v2);
        ma.update(k3, v3);
        ma.update(k4, v4);
        ma.update(k5, v5);
        ma.update(k6, v6);
        ma.update(k7, v7);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> update(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8) {
        ma.update(k1, v1);
        ma.update(k2, v2);
        ma.update(k3, v3);
        ma.update(k4, v4);
        ma.update(k5, v5);
        ma.update(k6, v6);
        ma.update(k7, v7);
        ma.update(k8, v8);
        return this;
    }

    //===== PUT =====

    @Override
    public MapMutator.Fluent<K, V> putAll(final java.util.Map<K, V> map) {
        return putAll(map, Predicates.alwaysTrue(), Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> putAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Map.Entry<K, V> e : map.entrySet()) {
            final K k = e.getKey();
            final V v = e.getValue();
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.put(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putAllKeys(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter) {
        return putAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> putAllValues(final java.util.Map<K, V> map,
            final Predicate<? super V> valueFilter) {
        return putAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> putAll(final Iterable<Tuple2<K, V>> map) {
        for (final Tuple2<K, V> e : map) {
            ma.put(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.put(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putAllKeys(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter) {
        return putAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> putAllValues(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super V> valueFilter) {
        return putAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> putAll(final MapAccessor<K, V> map) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            ma.put(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.put(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putAllKeys(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter) {
        return putAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> putAllKeys(final MapAccessor<K, V> map,
            final K... keys) {
        for (final K key : keys) {
            ma.put(key, map.getOrFail(key));
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putAllValues(final MapAccessor<K, V> map,
            final Predicate<? super V> valueFilter) {
        return putAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> put(
            final K k1, final V v1) {
        ma.put(k1, v1);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> put(
            final K k1, final V v1,
            final K k2, final V v2) {
        ma.put(k1, v1);
        ma.put(k2, v2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> put(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3) {
        ma.put(k1, v1);
        ma.put(k2, v2);
        ma.put(k3, v3);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> put(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4) {
        ma.put(k1, v1);
        ma.put(k2, v2);
        ma.put(k3, v3);
        ma.put(k4, v4);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> put(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5) {
        ma.put(k1, v1);
        ma.put(k2, v2);
        ma.put(k3, v3);
        ma.put(k4, v4);
        ma.put(k5, v5);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> put(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6) {
        ma.put(k1, v1);
        ma.put(k2, v2);
        ma.put(k3, v3);
        ma.put(k4, v4);
        ma.put(k5, v5);
        ma.put(k6, v6);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> put(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7) {
        ma.put(k1, v1);
        ma.put(k2, v2);
        ma.put(k3, v3);
        ma.put(k4, v4);
        ma.put(k5, v5);
        ma.put(k6, v6);
        ma.put(k7, v7);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> put(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8) {
        ma.put(k1, v1);
        ma.put(k2, v2);
        ma.put(k3, v3);
        ma.put(k4, v4);
        ma.put(k5, v5);
        ma.put(k6, v6);
        ma.put(k7, v7);
        ma.put(k8, v8);
        return this;
    }

    //===== PUTIFABSENT =====

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAll(final java.util.Map<K, V> map) {
        return putIfAbsentAll(map, Predicates.alwaysTrue(), Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Map.Entry<K, V> e : map.entrySet()) {
            final K k = e.getKey();
            final V v = e.getValue();
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.putIfAbsent(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAllKeys(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter) {
        return putIfAbsentAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAllValues(final java.util.Map<K, V> map,
            final Predicate<? super V> valueFilter) {
        return putIfAbsentAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAll(final Iterable<Tuple2<K, V>> map) {
        for (final Tuple2<K, V> e : map) {
            ma.putIfAbsent(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.putIfAbsent(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAllKeys(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter) {
        return putIfAbsentAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAllValues(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super V> valueFilter) {
        return putIfAbsentAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAll(final MapAccessor<K, V> map) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            ma.putIfAbsent(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.putIfAbsent(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAllKeys(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter) {
        return putIfAbsentAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAllKeys(final MapAccessor<K, V> map,
            final K... keys) {
        for (final K key : keys) {
            ma.putIfAbsent(key, map.getOrFail(key));
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsentAllValues(final MapAccessor<K, V> map,
            final Predicate<? super V> valueFilter) {
        return putIfAbsentAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsent(
            final K k1, final V v1) {
        ma.putIfAbsent(k1, v1);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsent(
            final K k1, final V v1,
            final K k2, final V v2) {
        ma.putIfAbsent(k1, v1);
        ma.putIfAbsent(k2, v2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsent(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3) {
        ma.putIfAbsent(k1, v1);
        ma.putIfAbsent(k2, v2);
        ma.putIfAbsent(k3, v3);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsent(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4) {
        ma.putIfAbsent(k1, v1);
        ma.putIfAbsent(k2, v2);
        ma.putIfAbsent(k3, v3);
        ma.putIfAbsent(k4, v4);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsent(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5) {
        ma.putIfAbsent(k1, v1);
        ma.putIfAbsent(k2, v2);
        ma.putIfAbsent(k3, v3);
        ma.putIfAbsent(k4, v4);
        ma.putIfAbsent(k5, v5);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsent(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6) {
        ma.putIfAbsent(k1, v1);
        ma.putIfAbsent(k2, v2);
        ma.putIfAbsent(k3, v3);
        ma.putIfAbsent(k4, v4);
        ma.putIfAbsent(k5, v5);
        ma.putIfAbsent(k6, v6);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsent(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7) {
        ma.putIfAbsent(k1, v1);
        ma.putIfAbsent(k2, v2);
        ma.putIfAbsent(k3, v3);
        ma.putIfAbsent(k4, v4);
        ma.putIfAbsent(k5, v5);
        ma.putIfAbsent(k6, v6);
        ma.putIfAbsent(k7, v7);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> putIfAbsent(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8) {
        ma.putIfAbsent(k1, v1);
        ma.putIfAbsent(k2, v2);
        ma.putIfAbsent(k3, v3);
        ma.putIfAbsent(k4, v4);
        ma.putIfAbsent(k5, v5);
        ma.putIfAbsent(k6, v6);
        ma.putIfAbsent(k7, v7);
        ma.putIfAbsent(k8, v8);
        return this;
    }

    //===== SET =====

    @Override
    public MapMutator.Fluent<K, V> setAll(final java.util.Map<K, V> map) {
        return setAll(map, Predicates.alwaysTrue(), Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> setAll(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Map.Entry<K, V> e : map.entrySet()) {
            final K k = e.getKey();
            final V v = e.getValue();
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.set(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> setAllKeys(final java.util.Map<K, V> map,
            final Predicate<? super K> keyFilter) {
        return setAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> setAllValues(final java.util.Map<K, V> map,
            final Predicate<? super V> valueFilter) {
        return setAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> setAll(final Iterable<Tuple2<K, V>> map) {
        for (final Tuple2<K, V> e : map) {
            ma.set(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> setAll(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.set(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> setAllKeys(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super K> keyFilter) {
        return setAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> setAllValues(final Iterable<Tuple2<K, V>> map,
            final Predicate<? super V> valueFilter) {
        return setAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> setAll(final MapAccessor<K, V> map) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            ma.set(e._1, e._2);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> setAll(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter,
            final Predicate<? super V> valueFilter) {
        for (final Tuple2<K, V> e : map.entriesByKey()) {
            final K k = e._1;
            final V v = e._2;
            if (keyFilter.test(k) && valueFilter.test(v)) {
                ma.set(k, v);
            }
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> setAllKeys(final MapAccessor<K, V> map,
            final Predicate<? super K> keyFilter) {
        return setAll(map, keyFilter, Predicates.alwaysTrue());
    }

    @Override
    public MapMutator.Fluent<K, V> setAllKeys(final MapAccessor<K, V> map,
            final K... keys) {
        for (final K key : keys) {
            ma.set(key, map.getOrFail(key));
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> setAllValues(final MapAccessor<K, V> map,
            final Predicate<? super V> valueFilter) {
        return setAll(map, Predicates.alwaysTrue(), valueFilter);
    }

    @Override
    public MapMutator.Fluent<K, V> set(
            final K k1, final V v1) {
        ma.set(k1, v1);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> set(
            final K k1, final V v1,
            final K k2, final V v2) {
        ma.set(k1, v1);
        ma.set(k2, v2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> set(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3) {
        ma.set(k1, v1);
        ma.set(k2, v2);
        ma.set(k3, v3);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> set(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4) {
        ma.set(k1, v1);
        ma.set(k2, v2);
        ma.set(k3, v3);
        ma.set(k4, v4);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> set(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5) {
        ma.set(k1, v1);
        ma.set(k2, v2);
        ma.set(k3, v3);
        ma.set(k4, v4);
        ma.set(k5, v5);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> set(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6) {
        ma.set(k1, v1);
        ma.set(k2, v2);
        ma.set(k3, v3);
        ma.set(k4, v4);
        ma.set(k5, v5);
        ma.set(k6, v6);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> set(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7) {
        ma.set(k1, v1);
        ma.set(k2, v2);
        ma.set(k3, v3);
        ma.set(k4, v4);
        ma.set(k5, v5);
        ma.set(k6, v6);
        ma.set(k7, v7);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> set(
            final K k1, final V v1,
            final K k2, final V v2, 
            final K k3, final V v3, 
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8) {
        ma.set(k1, v1);
        ma.set(k2, v2);
        ma.set(k3, v3);
        ma.set(k4, v4);
        ma.set(k5, v5);
        ma.set(k6, v6);
        ma.set(k7, v7);
        ma.set(k8, v8);
        return this;
    }

    //===== REMOVE =====

    @Override
    public MapMutator.Fluent<K, V> remove(final K... keys) {
        for (final K key : keys) {
            ma.remove(key);
        }
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> removeAll(final Iterable<K> keys) {
        for (final K key : keys) {
            ma.remove(key);
        }
        return this;
    }
    
    @Override
    public MapMutator.Fluent<K, V> rename(
            final K a1, final K a2) {
        ma.rename(a1, a2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> rename(
            final K a1, final K a2, final K b1, final K b2) {
        ma.rename(a1, a2);
        ma.rename(b1, b2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> rename(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2) {
        ma.rename(a1, a2);
        ma.rename(b1, b2);
        ma.rename(c1, c2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> rename(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2) {
        ma.rename(a1, a2);
        ma.rename(b1, b2);
        ma.rename(c1, c2);
        ma.rename(d1, d2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> rename(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2,
            final K e1, final K e2) {
        ma.rename(a1, a2);
        ma.rename(b1, b2);
        ma.rename(c1, c2);
        ma.rename(d1, d2);
        ma.rename(e1, e2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> rename(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2,
            final K e1, final K e2, final K f1, final K f2) {
        ma.rename(a1, a2);
        ma.rename(b1, b2);
        ma.rename(c1, c2);
        ma.rename(d1, d2);
        ma.rename(e1, e2);
        ma.rename(f1, f2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> rename(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2,
            final K e1, final K e2, final K f1, final K f2, final K g1, final K g2) {
        ma.rename(a1, a2);
        ma.rename(b1, b2);
        ma.rename(c1, c2);
        ma.rename(d1, d2);
        ma.rename(e1, e2);
        ma.rename(f1, f2);
        ma.rename(g1, g2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> rename(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2,
            final K e1, final K e2, final K f1, final K f2, final K g1, final K g2, final K h1, final K h2) {
        ma.rename(a1, a2);
        ma.rename(b1, b2);
        ma.rename(c1, c2);
        ma.rename(d1, d2);
        ma.rename(e1, e2);
        ma.rename(f1, f2);
        ma.rename(g1, g2);
        ma.rename(h1, h2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> overwrite(
            final K a1, final K a2) {
        ma.overwrite(a1, a2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> overwrite(
            final K a1, final K a2, final K b1, final K b2) {
        ma.overwrite(a1, a2);
        ma.overwrite(b1, b2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> overwrite(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2) {
        ma.overwrite(a1, a2);
        ma.overwrite(b1, b2);
        ma.overwrite(c1, c2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> overwrite(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2) {
        ma.overwrite(a1, a2);
        ma.overwrite(b1, b2);
        ma.overwrite(c1, c2);
        ma.overwrite(d1, d2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> overwrite(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2,
            final K e1, final K e2) {
        ma.overwrite(a1, a2);
        ma.overwrite(b1, b2);
        ma.overwrite(c1, c2);
        ma.overwrite(d1, d2);
        ma.overwrite(e1, e2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> overwrite(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2,
            final K e1, final K e2, final K f1, final K f2) {
        ma.overwrite(a1, a2);
        ma.overwrite(b1, b2);
        ma.overwrite(c1, c2);
        ma.overwrite(d1, d2);
        ma.overwrite(e1, e2);
        ma.overwrite(f1, f2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> overwrite(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2,
            final K e1, final K e2, final K f1, final K f2, final K g1, final K g2) {
        ma.overwrite(a1, a2);
        ma.overwrite(b1, b2);
        ma.overwrite(c1, c2);
        ma.overwrite(d1, d2);
        ma.overwrite(e1, e2);
        ma.overwrite(f1, f2);
        ma.overwrite(g1, g2);
        return this;
    }

    @Override
    public MapMutator.Fluent<K, V> overwrite(
            final K a1, final K a2, final K b1, final K b2, final K c1, final K c2, final K d1, final K d2,
            final K e1, final K e2, final K f1, final K f2, final K g1, final K g2, final K h1, final K h2) {
        ma.overwrite(a1, a2);
        ma.overwrite(b1, b2);
        ma.overwrite(c1, c2);
        ma.overwrite(d1, d2);
        ma.overwrite(e1, e2);
        ma.overwrite(f1, f2);
        ma.overwrite(g1, g2);
        ma.overwrite(h1, h2);
        return this;
    }
    
    private final MapMutator<K, V> ma;

}
