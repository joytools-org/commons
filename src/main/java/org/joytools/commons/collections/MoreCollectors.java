/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.apache.commons.collections4.Equator;
import org.joytools.commons.function.Functions;

/**
 *
 * @author AndreaR
 */
public class MoreCollectors {
    
    /**
     *
     * @param <T>
     * @param <M>
     * @param supplier
     * @return 
     */
    public static <T, M extends Map<T, Integer>> Collector<T, M, M> cardinality(final Supplier<M> supplier) {
        final BiConsumer<M, T> accumulator = (map, key) -> {
            final Integer count = map.getOrDefault(key, 0);
            map.put(key, count + 1);
        };
        final BinaryOperator<M> combiner = (a, b) -> {
            b.entrySet().forEach(e -> {
                final T key = e.getKey();
                final Integer count = a.getOrDefault(key, 0);
                a.put(key, count + e.getValue());
            });
            return a; 
        };
        return Collector.of(supplier, accumulator, combiner);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Collector<T, Map<T, Integer>, Map<T, Integer>> cardinality() {
        return cardinality(() -> new HashMap<T, Integer>());
    }

    /**
     * 
     * @param <T>
     * @param eq
     * @return 
     */
    public static <T> Collector<T, Map<T, Integer>, Map<T, Integer>> cardinality(final Equator<T> eq) {
        return cardinality(() -> new EquatorLinkedHashMap<T, Integer>(eq));
    }
    
    /**
     * 
     * @param <T>
     * @param eq
     * @return 
     */
    public static <T> Collector<T, SortedMap<T, Integer>, SortedMap<T, Integer>> cardinality(final Comparator<T> eq) {
        return cardinality(() -> new TreeMap<T, Integer>(eq));
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Collector<T, ?, SortedMap<Integer, T>> indexingToMap() {
        final Function<List<T>, SortedMap<Integer, T>> finisher = l -> {
            final SortedMap<Integer, T> tm = new TreeMap();
            final Function<T, Integer> index = Functions.intIndex();
            l.forEach(x -> tm.put(index.apply(x), x));
            return  tm;
        }; 
        return Collectors.collectingAndThen(Collectors.toList(), finisher);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    @Deprecated
    public static <T> Collector<T, SortedMap<Integer, T>, SortedMap<Integer, T>> indexingToMapV1() {
        final AtomicInteger counter = new AtomicInteger();
        final Supplier<SortedMap<Integer, T>> supplier = () -> {
            counter.set(0);
            return new TreeMap();
        };
        final BiConsumer<SortedMap<Integer, T>, T> accumulator = (map, value) -> {
            map.put(counter.getAndIncrement(), value);
        };
        final BinaryOperator<SortedMap<Integer, T>> combiner = (a, b) -> {
            throw new UnsupportedOperationException();
        };
        return Collector.of(supplier, accumulator, combiner, Collector.Characteristics.IDENTITY_FINISH);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Collector<T, ?, List<Tuple2<Integer, T>>> indexingToTuple() {
        final Function<List<T>, List<Tuple2<Integer, T>>> finisher = l -> {
            final List<Tuple2<Integer, T>> result = new ArrayList<>(l.size());
            final Function<T, Tuple2<Integer, T>> tupler = Functions.intIndexTuple();
            l.forEach(item -> result.add(tupler.apply(item)));
            return result;
        }; 
        return Collectors.collectingAndThen(Collectors.toList(), finisher);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    @Deprecated
    public static <T> Collector<T, List<Tuple2<Integer, T>>, List<Tuple2<Integer, T>>> indexingToTupleV1() {
        final AtomicInteger counter = new AtomicInteger(0);
        final Supplier<List<Tuple2<Integer, T>>> supplier = () -> {
            counter.set(0);
            return new ArrayList();
        };
        final BiConsumer<List<Tuple2<Integer, T>>, T> accumulator = (list, value) -> {
            list.add(Tuple(counter.getAndIncrement(), value));
        };
        final BinaryOperator<List<Tuple2<Integer, T>>> combiner = (a, b) -> {
            throw new UnsupportedOperationException();
        };
        return Collector.of(supplier, accumulator, combiner, Collector.Characteristics.IDENTITY_FINISH);
    }

    /**
     * 
     * @param <E>
     * @param mapper
     * @return 
     */
    public static <E> Collector<E, ?, List<List<E>>> partitioningBy(final Function<? super E, Integer> mapper) {
        // final Collector<E, Map<Integer, List<E>>, Map<Integer, List<E>>> coll = 
        return Collectors.collectingAndThen(
                Collectors.groupingBy(mapper, () -> new TreeMap<Integer, List<E>>(), Collectors.toList()), 
                partitioningByFinisher());
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T extends Object> Collector<T, ?, T> onlyElement() {
        return com.google.common.collect.MoreCollectors.onlyElement();
    }    

    /**
     * 
     * @param <E>
     * @return 
     */
    protected static <E> Function<SortedMap<Integer, List<E>>, List<List<E>>> partitioningByFinisher() {
        final Function<SortedMap<Integer, List<E>>, List<List<E>>> finisher = (m) -> {
            final List<List<E>> result = new ArrayList(m.size());
            m.entrySet().forEach(e -> {
                final int index = e.getKey();
                while (index >= result.size()) {
                    result.add(Collections.EMPTY_LIST);
                }
                final List<E> newData = e.getValue();
                if (!newData.isEmpty()) {
                    final List<E> currData = result.get(index);
                    if (currData.isEmpty()) {
                        result.set(index, newData);
                    } else {
                        currData.addAll(newData);
                    }
                }
            });
            return result;
        };
        return finisher;
    }
    
    /**
     * 
     * @param <E>
     * @return 
     */
    protected static <E> Function<SortedMap<Integer, List<E>>, List<List<E>>> partitioningByCountFinisher() {
        final Function<SortedMap<Integer, List<E>>, List<List<E>>> finisher = (m) -> {
            final List<List<E>> result = new ArrayList();
            final int sz = m.isEmpty() ? 0 : m.get(0).size();
            if (sz == 0) {
                return result;
            }
            int i = 0;
            for (final E item : IterableAccessors.zip(m.values())) {
                int index = i++ / sz;
                while (index >= result.size()) {
                    result.add(new ArrayList());
                }
                result.get(index).add(item);
            }
            return result;
        };
        return finisher;
    }

    /**
     * 
     * @param <E>
     * @param size
     * @return 
     */
    public static <E> Collector<E, ?, List<List<E>>> partitioningBySize(final int size) {
        checkArgument(size > 0, "Size must be greater than 0");
        return partitioningBy(Functions.intIndex().andThen(index -> index / size));
    }
    
    /**
     * 
     * @param <E>
     * @param count
     * @return 
     */
    public static <E> Collector<E, ?, List<List<E>>> partitioningByCount(final int count) {
        checkArgument(count > 0, "Count must be greater than 0");
        // final Collector<E, Map<Integer, List<E>>, Map<Integer, List<E>>> coll = 
        return Collectors.collectingAndThen(
                Collectors.groupingBy(Functions.intIndex().andThen(index -> index % count), 
                        () -> new TreeMap<Integer, List<E>>(), Collectors.toList()), 
                partitioningByCountFinisher());
    }
    
    /**
     * 
     * @param predicates
     * @return 
     */
    public static <E> Collector<E, ?, List<List<E>>> partitioningBy(final Predicate<? super E>... predicates) {
        return partitioningBy(Functions.partitionIndex(predicates));
    }

    /**
     * 
     * @param predicates
     * @return 
     */
    public static <E> Collector<E, ?, List<List<E>>> partitioningBy(final List<Predicate<? super E>> predicates) {
        return partitioningBy(Functions.partitionIndex(predicates));
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    static <T> BinaryOperator<T> unsupportedCombiner() {
        return (a, b) -> {
            throw new UnsupportedOperationException("Combiner");
        };
    };

    /**
     * 
     * @param <E>
     * @return 
     */
    public static <E> Collector<E, ImmutableList.Builder<E>, ImmutableList<E>> toImmutableList() {
        return Collector.of(
                () -> ImmutableList.builder(), 
                (b, e) -> { b.add(e); },       
                unsupportedCombiner(),            
                (b) -> b.build());
    }

    /**
     * 
     * @param <E>
     * @return 
     */
    public static <E> Collector<E, ImmutableSet.Builder<E>, ImmutableSet<E>> toImmutableSet() {
        return Collector.of(
                () -> ImmutableSet.builder(), 
                (b, e) -> { b.add(e); },       
                unsupportedCombiner(),            
                (b) -> b.build());
    }

    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param t
     * @param r
     * @param c
     * @param v
     * @return 
     */
    static <R, C, V> V putValue(final Table<R, C, V> t, final R r, final C c, final V v) {
        if (t.put(r, c, v) != null) {
            throw new IllegalArgumentException("Duplicated value found: [" + r + ", " + c + "]");
        }
        return v;
    }
    
    /**
     * 
     * @param <T>
     * @param <K>
     * @param <U>
     * @param keyMapper
     * @param valueMapper
     * @return 
     */
    static <T, K, U> Collector<T, ?, Map<K, U>> toMap(final Function<? super T, ? extends K> keyMapper,
                                    final Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper);
    }
    
    /**
     * 
     * @param <T>
     * @param <K>
     * @param <U>
     * @param keyMapper
     * @param valueMapper
     * @param mergeFunction
     * @return 
     */ /*
    public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(final Function<? super T, ? extends K> keyMapper,
                                    final Function<? super T, ? extends U> valueMapper,
                                    final BinaryOperator<U> mergeFunction) {
        return Collectors.toMap(keyMapper, valueMapper, mergeFunction);
    } */

    /**
     * 
     * @param <T>
     * @param <K>
     * @param <U>
     * @param <M>
     * @param keyMapper
     * @param valueMapper
     * @param mapSupplier
     * @return 
     */
    static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(
            final Function<? super T, ? extends K> keyMapper,
            final Function<? super T, ? extends U> valueMapper,
            final Supplier<M> mapSupplier) {
        return Collectors.toMap(keyMapper, valueMapper, Mergers.mergeNoneBinaryOperator(), mapSupplier);
    }

    /**
     * 
     * @param <T>
     * @param <K>
     * @param <U>
     * @param <M>
     * @param keyMapper
     * @param valueMapper
     * @param mergeFunction
     * @param mapSupplier
     * @return 
     */
    static <T, K, U, M extends Map<K, U>> Collector<T, ?, M> toMap(final Function<? super T, ? extends K> keyMapper,
                                final Function<? super T, ? extends U> valueMapper,
                                final BinaryOperator<U> mergeFunction,
                                final Supplier<M> mapSupplier) {
        return Collectors.toMap(keyMapper, valueMapper, mergeFunction, mapSupplier);
    }

    /**
     * 
     * @param <T>
     * @param <V>
     * @param valueMapper
     * @return 
     */
    static <T, V> Collector<T, ?, Map<T, V>> toMapByKey(final Function<? super T, ? extends V> valueMapper) {
        return Collectors.toMap(Function.identity(), valueMapper);
    }    
    
    /**
     * 
     * @param <T>
     * @param <V>
     * @param valueMapper
     * @param mergeFunction
     * @return 
     */
    static <T, V> Collector<T, ?, Map<T, V>> toMapByKey(
            final Function<? super T, ? extends V> valueMapper,
            final BinaryOperator<V> mergeFunction) {
        return Collectors.toMap(Function.identity(), valueMapper, mergeFunction);
    }    

    /**
     * 
     * @param <T>
     * @param <V>
     * @param <M>
     * @param valueMapper
     * @param mapSupplier
     * @return 
     */
    static <T, V, M extends Map<T, V>> Collector<T, ?, M> toMapByKey(
            final Function<? super T, ? extends V> valueMapper,
            final Supplier<M> mapSupplier) {
        return Collectors.toMap(Function.identity(), valueMapper, Mergers.mergeNoneBinaryOperator(), mapSupplier);
    }    

    /**
     * 
     * @param <T>
     * @param <V>
     * @param <M>
     * @param valueMapper
     * @param mergeFunction
     * @param mapSupplier
     * @return 
     */
    static <T, V, M extends Map<T, V>> Collector<T, ?, M> toMapByKey(
            final Function<? super T, ? extends V> valueMapper,
            final BinaryOperator<V> mergeFunction, 
            final Supplier<M> mapSupplier) {
        return Collectors.toMap(Function.identity(), valueMapper, mergeFunction, mapSupplier);
    }    
    
    /**
     * 
     * @param <T>
     * @param <K>
     * @param keyMapper
     * @return 
     */
    static <T, K> Collector<T, ?, Map<K, T>> toMapByValue(
            final Function<? super T, ? extends K> keyMapper) {
        return Collectors.toMap(keyMapper, Function.identity());
    }    

    /**
     * 
     * @param <T>
     * @param <K>
     * @param keyMapper
     * @param mergeFunction
     * @return 
     */
    static <T, K> Collector<T, ?, Map<K, T>> toMapByValue(
            final Function<? super T, ? extends K> keyMapper,
            final BinaryOperator<T> mergeFunction) {
        return Collectors.toMap(keyMapper, Function.identity(), mergeFunction);
    }    

    /**
     * 
     * @param <T>
     * @param <K>
     * @param <M>
     * @param keyMapper
     * @param mapSupplier
     * @return 
     */
    static <T, K, M extends Map<K, T>> Collector<T, ?, M> toMapByValue(
            final Function<? super T, ? extends K> keyMapper,
            final Supplier<M> mapSupplier) {
        return Collectors.toMap(keyMapper, Function.identity(), Mergers.mergeNoneBinaryOperator(), mapSupplier);
    }    

    /**
     * 
     * @param <T>
     * @param <K>
     * @param <M>
     * @param keyMapper
     * @param mergeFunction
     * @param mapSupplier
     * @return 
     */
    static <T, K, M extends Map<K, T>> Collector<T, ?, M> toMapByValue(
            final Function<? super T, ? extends K> keyMapper,
            final BinaryOperator<T> mergeFunction,
            final Supplier<M> mapSupplier) {
        return Collectors.toMap(keyMapper, Function.identity(), mergeFunction, mapSupplier);
    }    

    /**
     * 
     * @param <E>
     * @param <R>
     * @param <C>
     * @param <V>
     * @param <T>
     * @param supplier
     * @param keyMapper
     * @param colMapper
     * @param valMapper
     * @return 
     */
    public static <E, R, C, V, T extends Table<R, C, V>> Collector<E, ?, T> toTable(
            final Supplier<T> supplier,
            final Function<? super E, ? extends R> keyMapper,
            final Function<? super E, ? extends C> colMapper,
            final Function<? super E, ? extends V> valMapper) {
        Objects.requireNonNull(supplier, "Supplier");
        Objects.requireNonNull(keyMapper, "Key Mapper");
        Objects.requireNonNull(colMapper, "Column Mapper");
        Objects.requireNonNull(valMapper, "Value Mapper");
        final BiConsumer<T, E> accumulator = (t, e) -> {
            putValue(t, keyMapper.apply(e), colMapper.apply(e), valMapper.apply(e));
        };
        final BinaryOperator<T> combiner = (a, b) -> {
            b.cellSet().forEach(e -> {
                putValue(a, e.getRowKey(), e.getColumnKey(), e.getValue());
            });
            return a;
        };
        return Collector.of(supplier, accumulator, combiner);
    }
    
    /**
     * 
     * @param <E>
     * @param <R>
     * @param <C>
     * @param <V>
     * @param keyMapper
     * @param colMapper
     * @param valMapper
     * @return 
     */
    public static <E, R, C, V> Collector<E, ImmutableTable.Builder<R, C, V>, ImmutableTable<R, C, V>> toImmutableTable(
            final Function<? super E, ? extends R> keyMapper,
            final Function<? super E, ? extends C> colMapper,
            final Function<? super E, ? extends V> valMapper) {
        Objects.requireNonNull(keyMapper, "Key Mapper");
        Objects.requireNonNull(colMapper, "Column Mapper");
        Objects.requireNonNull(valMapper, "Value Mapper");
        return Collector.of(
                () -> ImmutableTable.builder(), 
                (t, e) -> { t.put(keyMapper.apply(e), colMapper.apply(e), valMapper.apply(e)); }, 
                unsupportedCombiner(), 
                (b) -> b.build());
    }

    /**
     * 
     * @param <E>
     * @param <R>
     * @param <C>
     * @param <V>
     * @param rowMapper
     * @param colMapper
     * @param valMapper
     * @return 
     */
    public static <E, R, C, V> Collector<E, ?, Table<R, C, V>> toTable(
            final Function<? super E, ? extends R> rowMapper,
            final Function<? super E, ? extends C> colMapper,
            final Function<? super E, ? extends V> valMapper) {
        return toTable(() -> HashBasedTable.create(), rowMapper, colMapper, valMapper);
    }
        
}
