/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.interop;

import io.vavr.Tuple2;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.IntFunction;
import org.joytools.commons.collections.IterableUtils;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.lang.ArrayUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class Java {
    
    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static <V> Optional<V> toOptional(final Iterable<V> elements) {
        Objects.requireNonNull(elements, "Elements");
        final Iterator<V> itr = elements.iterator();
        if (!itr.hasNext()) {
            return Optional.empty();
        }
        final Optional<V> result = Optional.of(itr.next());
        if (itr.hasNext()) {
            return Commons.throwMoreThanOneElementFound();
        }
        return result;
    }
    
    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function<Iterable<V>, Optional<V>> optional() {
        return Java::toOptional;
    }

    /**
     * 
     * @param <V>
     * @param cs
     * @param entries
     * @return 
     */
    public static final <V> TreeSet<String> toTreeSet(final CaseSensitivity cs, final Iterable<String> entries) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        if (cs.isCaseSensitive()) {
            return toTreeSet(entries);
        } else {
            return toTreeSetIgnoreCase(entries);
        }
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static final <V> TreeSet<String> toTreeSetIgnoreCase(final Iterable<String> entries) {
        final TreeSet<String> set = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        if (entries instanceof Collection coll) {
            set.addAll(coll);
        } else {
            for (final String s : entries) {
                set.add(s);
            }
        }
        return set;
    }

    /**
     * 
     * @param <V>
     * @param cs
     * @param entries
     * @return 
     */
    public static final <V> TreeMap<String, V> toTreeMap(final CaseSensitivity cs, final Iterable<Tuple2<String, V>> entries) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        if (cs.isCaseSensitive()) {
            return toTreeMap(entries);
        } else {
            return toTreeMapIgnoreCase(entries);
        }
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static final <V> TreeMap<String, V> toTreeMapIgnoreCase(final Iterable<Tuple2<String, V>> entries) {
        final TreeMap<String, V> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        return MapUtils.insertAll(map, entries);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static final <K, V> TreeMap<K, V> toTreeMap(final Iterable<Tuple2<K, V>> entries) {
        final TreeMap<K, V> map = new TreeMap<>();
        return MapUtils.insertAll(map, entries);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static final <K, V> HashMap<K, V> toHashMap(final Iterable<Tuple2<K, V>> entries) {
        final HashMap<K, V> map = new HashMap<>();
        return MapUtils.insertAll(map, entries);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static final <K, V> LinkedHashMap<K, V> toLinkedHashMap(final Iterable<Tuple2<K, V>> entries) {
        final LinkedHashMap<K, V> map = new LinkedHashMap<>();
        return MapUtils.insertAll(map, entries);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static final <K, V> ConcurrentHashMap<K, V> toConcurrentHashMap(final Iterable<Tuple2<K, V>> entries) {
        final ConcurrentHashMap<K, V> map = new ConcurrentHashMap<>();
        return MapUtils.insertAll(map, entries);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, HashMap<K, V>> hashMap() {
        return (Function<Iterable<Tuple2<K, V>>, HashMap<K, V>>)(Function)TO_HASHMAP;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Tuple2<Object, Object>>, HashMap<Object, Object>> TO_HASHMAP = Java::toHashMap;

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, TreeMap<K, V>> treeMap() {
        return (Function<Iterable<Tuple2<K, V>>, TreeMap<K, V>>)(Function)TO_TREEMAP;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Tuple2<Object, Object>>, TreeMap<Object, Object>> TO_TREEMAP = Java::toTreeMap;

    /**
     * 
     * @param cs
     * @return 
     */
    public static final Function<Iterable<String>, TreeSet<String>> treeSet(final CaseSensitivity cs) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        if (cs.isCaseSensitive()) {
            return treeSet();
        } else {
            return treeSetIgnoreCase();
        }
    }

    /**
     *
     * @return
     */
    public static final Function<Iterable<String>, TreeSet<String>> treeSetIgnoreCase() {
        return TO_TREESET_IGNORECASE;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<String>, TreeSet<String>> TO_TREESET_IGNORECASE = Java::toTreeSetIgnoreCase;

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, ConcurrentHashMap<K, V>> concurrentHashMap() {
        return (Function<Iterable<Tuple2<K, V>>, ConcurrentHashMap<K, V>>)(Function)TO_CONCURRENTHASHMAP;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Tuple2<Object, Object>>, ConcurrentHashMap<Object, Object>> TO_CONCURRENTHASHMAP = Java::toConcurrentHashMap;

    /**
     * 
     * @param <V>
     * @param cs
     * @return 
     */
    public static final <V> Function<Iterable<Tuple2<String, V>>, TreeMap<String, V>> treeMap(final CaseSensitivity cs) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        if (cs.isCaseSensitive()) {
            return treeMap();
        } else {
            return treeMapIgnoreCase();
        }
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<Tuple2<String, V>>, TreeMap<String, V>> treeMapIgnoreCase() {
        return (Function<Iterable<Tuple2<String, V>>, TreeMap<String, V>>)(Function)TO_TREEMAP_IGNORECASE;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Tuple2<String, Object>>, TreeMap<String, Object>> TO_TREEMAP_IGNORECASE = Java::toTreeMapIgnoreCase;

    /**
     * 
     * @param <V>
     * @param factory
     * @return 
     */
    public static <V> Function<Iterable<V>, V[]> array(final IntFunction<V[]> factory) {
        Objects.requireNonNull(factory, "Factory");
        return x -> toArray(x, factory);
    }

    /**
     * 
     * @param <V>
     * @param type
     * @return 
     */
    public static <V> Function<Iterable<V>, V[]> array(final Class<V> type) {
        Objects.requireNonNull(type, "Type");
        return x -> toArray(x, type);
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @param type
     * @return 
     */
    public static <V> V[] toArray(final Iterable<V> elements, final Class<V> type) {
        Objects.requireNonNull(elements);
        Objects.requireNonNull(type);
        final int knownSize = IterableUtils.knownSize(elements);
        if (knownSize < 0) {
            return org.apache.commons.collections4.IteratorUtils.toArray(elements.iterator(), type);
        }
        final V[] array = (V[])Array.newInstance(type, knownSize);
        return copyItr2Array(elements, array);
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @param factory
     * @return 
     */
    public static <V> V[] toArray(final Iterable<V> elements, final IntFunction<V[]> factory) {
        Objects.requireNonNull(elements, "Elements");
        Objects.requireNonNull(factory, "Array Factory");
        Iterable<V> items = elements;
        int size = IterableUtils.knownSize(elements);
        if (size < 0) {
            final ArrayList<V> al = toArrayList(elements);
            items = al;
            size = al.size();
        }
        final V[] array = factory.apply(size);
        return copyItr2Array(items, array);
    }
    
    /**
     * 
     * @param <V>
     * @param items
     * @param array
     * @return 
     */
    static <V> V[] copyItr2Array(final Iterable<V> items, final V[] array) {
        int index = 0;
        for (final V v : items) {
            array[index++] = v;
        }
        if (index != array.length) {
            throw new IllegalStateException("Last index != array length: " + index + ", " + array.length);
        }
        return array;
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, ArrayList<V>> arrayList() {
        return (Function<Iterable<V>, ArrayList<V>>)(Function)TO_ARRAYLIST;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, ArrayList<Object>> TO_ARRAYLIST = Java::toArrayList;

    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static final <V> ArrayList<V> toArrayList(final Iterable<V> elements) {
        if (elements instanceof Collection coll) {
            return new ArrayList(coll);
        }
        final int knownSize = IterableUtils.knownSize(elements);
        final ArrayList<V> list = knownSize > 0 ? 
                new ArrayList<>(knownSize) : 
                new ArrayList<>();
        for (final V v : elements) {
            list.add(v);
        }
        return list;
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static final <V> Function<Iterable<V>, HashSet<V>> hashSet() {
        return (Function<Iterable<V>, HashSet<V>> )(Function)TO_HASHSET;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, HashSet<Object>> TO_HASHSET = Java::toHashSet;

    /**
     * 
     * @param <V>
     * @return 
     */
    public static final <V> Function<Iterable<V>, LinkedHashSet<V>> linkedHashSet() {
        return (Function<Iterable<V>, LinkedHashSet<V>>)(Function)TO_LINKEDHASHSET;
    }

    /**
     * 
     */
    private final static Function<Iterable<Object>, LinkedHashSet<Object>> TO_LINKEDHASHSET = Java::toLinkedHashSet;

    /**
     * 
     * @param <V>
     * @param x
     * @return 
     */
    public static final <V> HashSet<V> toHashSet(final Iterable<V> x) {
        if (x instanceof Collection coll) {
            return new HashSet(coll);
        }
        final HashSet<V> set = new HashSet<>();
        for (final V v : x) {
            set.add(v);
        }
        return set;
    }

    /**
     * 
     * @param <V>
     * @param x
     * @return 
     */
    public static final <V> LinkedHashSet<V> toLinkedHashSet(final Iterable<V> x) {
        if (x instanceof Collection coll) {
            return new LinkedHashSet(coll);
        }
        final LinkedHashSet<V> set = new LinkedHashSet<>();
        for (final V v : x) {
            set.add(v);
        }
        return set;
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static final <V> Function<Iterable<V>, TreeSet<V>> treeSet() {
        return (Function<Iterable<V>, TreeSet<V>>)(Function)TO_TREESET;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, TreeSet<Object>> TO_TREESET = Java::toTreeSet;

    /**
     * 
     * @param <V>
     * @param x
     * @return 
     */
    public static final <V> TreeSet<V> toTreeSet(final Iterable<V> x) {
        if (x instanceof Collection coll) {
            return new TreeSet(coll);
        }
        final TreeSet<V> set = new TreeSet<>();
        for (final V v : x) {
            set.add(v);
        }
        return set;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, LinkedHashMap<K, V>> linkedHashMap() {
        return (Function<Iterable<Tuple2<K, V>>, LinkedHashMap<K, V>>)(Function)TO_LINKEDHASHMAP;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Tuple2<Object, Object>>, LinkedHashMap<Object, Object>> TO_LINKEDHASHMAP = Java::toLinkedHashMap;
    
}
