/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.interop;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.TreeBasedTable;
import com.google.common.collect.TreeMultiset;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import java.util.Comparator;
import java.util.function.Function;
import org.joytools.commons.collections.TableUtils;

/**
 *
 * @author AndreaR
 */
public class Guava {

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static <V> HashMultiset<V> toHashMultiset(final Iterable<V> entries) {
        return HashMultiset.create(entries);
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static <V> LinkedHashMultiset<V> toLinkedHashMultiset(final Iterable<V> entries) {
        return LinkedHashMultiset.create(entries);
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static <V extends Comparable<? super V>> TreeMultiset<V> toTreeMultiset(final Iterable<V> entries) {
        return TreeMultiset.create(entries);
    }

    /**
     * 
     * @param <V>
     * @param comp
     * @param entries
     * @return 
     */
    public static <V> TreeMultiset<V> toTreeMultiset(final Comparator<? super V> comp, final Iterable<V> entries) {
        final TreeMultiset<V> ms = TreeMultiset.create(comp);
        for (final V v : entries) {
            ms.add(v);
        }
        return ms;
    }

    /**
     * 
     * @param entries
     * @return 
     */
    public static TreeMultiset<String> toTreeMultisetIgnoreCase(final Iterable<String> entries) {
        return toTreeMultiset(String.CASE_INSENSITIVE_ORDER, entries);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <K, V> ImmutableBiMap<K, V> toImmutableBiMap(final Iterable<Tuple2<K, V>> entries) {
        final ImmutableBiMap.Builder<K, V> b = ImmutableBiMap.<K, V>builder();
        for (final Tuple2<K, V> e : entries) {
            b.put(e._1, e._2);
        }
        return b.buildOrThrow();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <K, V> ImmutableBiMap<K, V> toImmutableBiMap(final java.util.Map<K, V> entries) {
        final ImmutableBiMap.Builder<K, V> b = ImmutableBiMap.<K, V>builder();
        b.putAll(entries);
        return b.buildOrThrow();
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <K, V> HashBiMap<K, V> toHashBiMap(final Iterable<Tuple2<K, V>> entries) {
        final HashBiMap<K, V> m = HashBiMap.create();
        for (final Tuple2<K, V> e : entries) {
            m.put(e._1, e._2);
        }
        return m;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> HashBiMap<K, V> toHashBiMap(final java.util.Map<K, V> map) {
        final HashBiMap<K, V> m = HashBiMap.create(map);
        return m;
    }

    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <R, C, V> HashBasedTable<R, C, V> toHashBasedTable(final Iterable<Tuple3<R, C, V>> entries) {
        final HashBasedTable<R, C, V> t = HashBasedTable.create();
        return TableUtils.insertAll(t, entries);
    }
    
    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <R extends Comparable<? super R>, C extends Comparable<? super C>, V> TreeBasedTable<R, C, V> toTreeBasedTable(final Iterable<Tuple3<R, C, V>> entries) {
        final TreeBasedTable<R, C, V> t = TreeBasedTable.create();
        return TableUtils.insertAll(t, entries);
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static <V> TreeBasedTable<String, String, V> toTreeBasedTableIgnoreCase(final Iterable<Tuple3<String, String, V>> entries) {
        final TreeBasedTable<String, String, V> t = TreeBasedTable.create(String.CASE_INSENSITIVE_ORDER, String.CASE_INSENSITIVE_ORDER);
        return TableUtils.insertAll(t, entries);
    }

    /**
     * 
     * @param <R>
     * @param <C>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <R, C, V> ImmutableTable<R, C, V> toImmutableTable(final Iterable<Tuple3<R, C, V>> entries) {
        final ImmutableTable.Builder<R, C, V> b = ImmutableTable.builder();
        for (final Tuple3<R, C, V> e : entries) {
            b.put(e._1, e._2, e._3);
        }
        return b.buildOrThrow();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <K, V> HashMultimap<K, V> toHashMultimap(final Iterable<Tuple2<K, V>> entries) {
        final HashMultimap<K, V> b = HashMultimap.<K, V>create();
        for (final Tuple2<K, V> e : entries) {
            b.put(e._1, e._2);
        }
        return b;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param entries
     * @return 
     */
    public static <K, V> ImmutableMultimap<K, V> toImmutableMultimap(final Iterable<Tuple2<K, V>> entries) {
        final ImmutableMultimap.Builder<K, V> b = ImmutableMultimap.<K, V>builder();
        for (final Tuple2<K, V> e : entries) {
            b.put(e._1, e._2);
        }
        return b.build();
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */    
    public static <V> ImmutableList<V> toImmutableList(final Iterable<V> entries) {
        return ImmutableList.copyOf(entries);
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static <V> ImmutableSet<V> toImmutableSet(final Iterable<V> entries) {
        return ImmutableSet.copyOf(entries);
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static <V> ImmutableSortedSet<V> toImmutableSortedSet(final Iterable<V> entries) {
        return ImmutableSortedSet.copyOf(entries);
    }

    /**
     * 
     * @param <V>
     * @param entries
     * @return 
     */
    public static <V> ImmutableMultiset<V> toImmutableMultiset(final Iterable<V> entries) {
        return ImmutableMultiset.copyOf(entries);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, ImmutableBiMap<K, V>> immutableBiMap() {
        return Guava::toImmutableBiMap;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, HashBiMap<K, V>> hashBiMap() {
        return Guava::toHashBiMap;
    }

    /**
     *
     * @param <R>
     * @param <C>
     * @param <V>
     * @return
     */
    public static final <R extends Comparable<? super R>, C extends Comparable<? super C>, V> Function<Iterable<Tuple3<R, C, V>>, TreeBasedTable<R, C, V>> treeBasedTable() {
        return Guava::toTreeBasedTable;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, ImmutableMultimap<K, V>> immutableMultimap() {
        return Guava::toImmutableMultimap;
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, HashMultiset<V>> hashMultiset() {
        return Guava::toHashMultiset;
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static final <V> Function<Iterable<V>, LinkedHashMultiset<V>> linkedHashMultiset() {
        return Guava::toLinkedHashMultiset;
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V extends Comparable<? super V>> Function<Iterable<V>, TreeMultiset<V>> treeMultiset() {
        return Guava::toTreeMultiset;
    }

    /**
     *
     * @param <R>
     * @param <C>
     * @param <V>
     * @return
     */
    public static final <R, C, V> Function<Iterable<Tuple3<R, C, V>>, ImmutableTable<R, C, V>> immutableTable() {
        return Guava::toImmutableTable;
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<Tuple3<String, String, V>>, TreeBasedTable<String, String, V>> treeBasedTableIgnoreCase() {
        return Guava::toTreeBasedTableIgnoreCase;
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @return
     */
    public static final <K, V> Function<Iterable<Tuple2<K, V>>, HashMultimap<K, V>> hashMultimap() {
        return Guava::toHashMultimap;
    }

    /**
     *
     * @return
     */
    public static final Function<Iterable<String>, TreeMultiset<String>> treeMultisetIgnoreCase() {
        return Guava::toTreeMultisetIgnoreCase;
    }

    /**
     *
     * @param <R>
     * @param <C>
     * @param <V>
     * @return
     */
    public static final <R, C, V> Function<Iterable<Tuple3<R, C, V>>, HashBasedTable<R, C, V>> hashBasedTable() {
        return Guava::toHashBasedTable;
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, ImmutableList<V>> immutableList() {
        return Guava::toImmutableList;
    }

    /**
     *
     * @param <V>
     * @return
     */
    public static final <V> Function<Iterable<V>, ImmutableSet<V>> immutableSet() {
        return Guava::toImmutableSet;
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static final <V> Function<Iterable<V>, ImmutableMultiset<V>> immutableMultiset() {
        return Guava::toImmutableMultiset;
    }

}
