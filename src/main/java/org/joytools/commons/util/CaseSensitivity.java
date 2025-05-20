/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import io.vavr.Tuple2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.collections4.Equator;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.joytools.commons.collections.Equators;
import org.joytools.commons.collections.IndexedMap;
import org.joytools.commons.collections.IndexedMapImpl;
import org.joytools.commons.collections.SetUtils;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.collections.MutatorLinkedHashMap;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.MutatorLinkedHashMapIgnoreCase;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.interop.Java;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public enum CaseSensitivity {
    
    SENSITIVE() {
        @Override
        public Comparator<String> comparator() {
            return Comparator.naturalOrder();
        }

        @Override
        public Equator<String> equator() {
            return Equators.caseSensitive();
        }

        @Override
        public boolean isCaseSensitive() {
            return true;
        }      

        @Override
        public boolean isCaseInsensitive() {
            return false;
        }

        @Override
        public boolean isIgnoreCase() {
            return false;
        }
        
        @Override
        public int hashCode(final char c) {
            return (int)c;
        }

        @Override
        public <V> Map<String, V> newLinkedMap() {
            return new MutatorLinkedHashMap();
        }

        @Override
        public <V> Map<String, V> newLinkedMap(final Map<String, V> data) {
            return new MutatorLinkedHashMap(data);
        }

        @Override
        public Set<String> newSet() {
            return new HashSet();
        }

        @Override
        public boolean isUnknown() {
            return false;
        }

    },
    
    INSENSITIVE() {
        @Override
        public Comparator<String> comparator() {
            return String.CASE_INSENSITIVE_ORDER;
        }

        @Override
        public Equator<String> equator() {
            return Equators.caseInsensitive();
        }

        @Override
        public boolean isCaseSensitive() {
            return false;
        }

        @Override
        public boolean isCaseInsensitive() {
            return true;
        }

        @Override
        public boolean isIgnoreCase() {
            return true;
        }

        @Override
        public int hashCode(final char c) {
            return (int)Character.toLowerCase(c);
        }

        @Override
        public <V> Map<String, V> newLinkedMap() {
            return new MutatorLinkedHashMapIgnoreCase();
        }

        @Override
        public <V> Map<String, V> newLinkedMap(final Map<String, V> data) {
            final Map<String, V> m = new MutatorLinkedHashMapIgnoreCase(data.size());
            m.putAll(data);
            return m;
        }

        @Override
        public Set<String> newSet() {
            return newSortedSet();
        }

        @Override
        public boolean isUnknown() {
            return false;
        }
        
    },
    
    UNKNOWN() {
        @Override
        public Comparator<String> comparator() {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }

        @Override
        public Equator<String> equator() {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }

        @Override
        public <V> Map<String, V> newLinkedMap() {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }

        @Override
        public <V> Map<String, V> newLinkedMap(final Map<String, V> data) {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }

        @Override
        public Set<String> newSet() {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }

        @Override
        public boolean isCaseSensitive() {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }

        @Override
        public boolean isCaseInsensitive() {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }

        @Override
        public boolean isIgnoreCase() {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }
        
        @Override
        public int hashCode(final char c) {
            throw new UnsupportedOperationException(IS_UNKNOWN_ERROR_MSG); 
        }
        
        @Override
        public boolean isUnknown() {
            return true;
        }

    };
    
    CaseSensitivity() {
    };
    
    /**
     * 
     * @param keys
     * @param containsKey
     * @return 
     */
    public static CaseSensitivity fromMapKeys(final Iterable<String> keys, final Predicate<String> containsKey) {
        final io.vavr.collection.Set<String> sensitive = io.vavr.collection.HashSet.ofAll(keys);
        if (sensitive.isEmpty()) {
            return UNKNOWN;
        }
        final io.vavr.collection.Set<String> insensitive = io.vavr.collection.TreeSet.ofAll(String.CASE_INSENSITIVE_ORDER, keys);
        if (sensitive.size() != insensitive.size()) {
            return SENSITIVE;
        }
        for (final String k : sensitive) {
            final String klow = k.toLowerCase();
            if (!k.equals(klow) && !containsKey.test(klow)) {
                return SENSITIVE;
            }
            final String ku = k.toUpperCase();
            if (!k.equals(ku) && !containsKey.test(ku)) {
                return SENSITIVE;
            }
        }
        return INSENSITIVE;
    }

    /**
     * 
     * @return 
     */
    public static CaseSensitivity forIgnoreCase() {
        return INSENSITIVE;
    }
    
    /**
     * 
     * @param ignoreCase
     * @return 
     */
    public static CaseSensitivity forIgnoreCase(final boolean ignoreCase) {
        return ignoreCase ? INSENSITIVE : SENSITIVE;
    }

    /**
     * 
     * @param comp
     * @return 
     */
    public static CaseSensitivity from(final Comparator comp) {
        if (comp == null 
                || comp == Comparator.naturalOrder()
                || comp == VAVR_NATURAL_ORDER) {
            return CaseSensitivity.SENSITIVE;
        }
        if (comp == String.CASE_INSENSITIVE_ORDER) {
            return CaseSensitivity.INSENSITIVE;
        }
        if (comp instanceof CaseSensitivitySupport css) {
            final CaseSensitivity cs = css.caseSensitivity();
            if (!cs.isUnknown()) {
                return cs;
            }
        }
        return CaseSensitivity.UNKNOWN;
    }
    
    /**
     * 
     * @param itr
     * @return 
     */
    public static CaseSensitivity from(final Iterable<?> itr) {
        if (itr == null) {
            return CaseSensitivity.UNKNOWN;
        }
        if (itr instanceof java.util.SortedSet ss) {
            return from(ss.comparator());
        }
        if (itr instanceof io.vavr.collection.Ordered ord) {
            return from(ord.comparator());
        }
        if (itr instanceof CaseSensitivitySupport css) {
            return css.caseSensitivity();
        }
        return CaseSensitivity.UNKNOWN;
    }

    /**
     * 
     * @param map
     * @return 
     */
    public static CaseSensitivity fromMap(final java.util.Map map) {
        if (map == null) {
            return CaseSensitivity.UNKNOWN;
        }
        if (map instanceof java.util.SortedMap sm) {
            final CaseSensitivity res = from(sm.comparator());
            if (!res.isUnknown()) {
                return res;
            }
        }
        if (map instanceof CaseSensitivitySupport css) {
            return css.caseSensitivity();
        }
        return fromMapKeys(map.keySet(), map::containsKey);
    }

    /**
     * 
     * @param map
     * @return 
     */
    public static CaseSensitivity fromMap(final io.vavr.collection.Map map) {
        if (map == null) {
            return CaseSensitivity.UNKNOWN;
        }
        if (map instanceof io.vavr.collection.SortedMap sm) {
            final CaseSensitivity res = from(sm.comparator());
            if (!res.isUnknown()) {
                return res;
            }
        }
        if (map instanceof CaseSensitivitySupport css) {
            return css.caseSensitivity();
        }
        return fromMapKeys(map.keySet(), map::containsKey);
    }

    /**
     * 
     * @param eq
     * @return 
     */
    public static CaseSensitivity from(final Equator eq) {
        if (eq == null || eq == DefaultEquator.INSTANCE) {
            return CaseSensitivity.SENSITIVE;
        }
        if (eq == Equators.caseInsensitive()) {
            return CaseSensitivity.INSENSITIVE;
        }
        if (eq instanceof CaseSensitivitySupport css) {
            final CaseSensitivity cs = css.caseSensitivity();
            if (!cs.isUnknown()) {
                return cs;
            }
        }
        return CaseSensitivity.UNKNOWN;
    }

    /**
     * 
     * @return 
     */
    public abstract Comparator<String> comparator();

    /**
     * 
     * @return 
     */
    public abstract Equator<String> equator();

    /**
     * 
     * @param <V>
     * @return 
     */
    public final <V> Supplier<Map<String, V>> linkedMapSupplier() {
        return () -> newLinkedMap();
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public final <V> Supplier<SortedMap<String, V>> sortedMapSupplier() {
        return () -> newSortedMap();
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public final <V> SortedMap<String, V> newSortedMap() {
        return new TreeMap<>(comparator());
    }

    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    public final <V> SortedMap<String, V> newSortedMap(final Map<String, V> data) {
        final SortedMap<String, V> m = new TreeMap<>(comparator());
        m.putAll(data);
        return m;
    }

    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    public final <V> SortedMap<String, V> newSortedMap(final Iterable<Tuple2<String, V>> data) {
        final SortedMap<String, V> m = new TreeMap<>(comparator());
        for (final Tuple2<String, V> e : data) {
            m.put(e._1(), e._2());
        }
        return m;
    }
    
    /**
     * 
     * @param <V>
     * @return 
     */
    abstract public <V> Map<String, V> newLinkedMap();
    
    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    abstract public <V> Map<String, V> newLinkedMap(final Map<String, V> data);

    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    public final <V> Map<String, V> newLinkedMap(final Iterable<Tuple2<String, V>> data) {
        final Map<String, V> map = newLinkedMap();
        for (final Tuple2<String, V> e : data) {
            map.put(e._1(), e._2());
        }
        return map;
    }

    /**
     * 
     * @return 
     */
    abstract public Set<String> newSet();

    /**
     * 
     * @param items
     * @return 
     */
    public final Set<String> newSet(final CharSequence... items) {
        return newSet(IterableAccessors.of(items));
    }

    /**
     * 
     * @param items
     * @return 
     */
    public final Set<String> newSet(final Iterable<? extends CharSequence> items) {
        final Set<String> set = newSet();
        if (items != null) {
            for (final CharSequence cs : items) {
                set.add(StringUtils.toString(cs));
            }
        }
        return set;
    }

    /**
     * 
     * @param items
     * @return 
     */
    public final SortedSet<String> newSortedSet(final CharSequence... items) {
        return newSortedSet(IterableAccessors.of(items));
    }

    /**
     * 
     * @param items
     * @return 
     */
    public final SortedSet<String> newSortedSet(final Iterable<? extends CharSequence> items) {
        final SortedSet<String> set = newSortedSet();
        if (items != null) {
            for (final CharSequence cs : items) {
                set.add(StringUtils.toString(cs));
            }
        }
        return set;
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public final <V> Function<Iterable<Tuple2<String, V>>, Map<String, V>> toLinkedMap() {
        return tuples -> {
            final Map<String, V> m = newLinkedMap();
            return MapUtils.insertAll(m, tuples);
        };
    }
            
    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    public final <V> Map<String, V> toLinkedMap(final Map<String, V> data) {
        return MapUtils.insertAll(newLinkedMap(), data);
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public final <V> Function<Iterable<Tuple2<String, V>>, Map<String, V>> toSortedMap() {
        return tuples -> {
            final Map<String, V> m = newSortedMap();
            return MapUtils.insertAll(m, tuples);
        };
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public final <V> IndexedMap<String, V> newIndexedMap() {
        return new CSIndexedMapImpl(newLinkedMap());
    }

    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    public final <V> Map<String, V> toMap(final Map<String, V> data) {
        return MapUtils.insertAll(newLinkedMap(), data);
        // return JdkMutatorUtils.putAllUnique(data, newLinkedMap());
    }

    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    public final <V> IndexedMap<String, V> toIndexedMap(final Map<String, V> data) {
        return MapUtils.insertAll(newIndexedMap(), data);
    }

    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    public final <V> SortedMap<String, V> toSortedMap(final Map<String, V> data) {
        return MapUtils.insertAll(newSortedMap(), data);
    }

    /**
     * 
     * @return 
     */
    public final Supplier<SortedSet<String>> sortedSetSupplier() {
        return () -> newSortedSet();
    }

    /**
     * 
     * @return 
     */
    public final SortedSet<String> newSortedSet() {
        return new TreeSet<>(comparator());
    }

    /**
     * 
     * @param data
     * @return 
     */
    public final SortedSet<String> toSortedSet(final Iterable<String> data) {
        return SetUtils.addAllUnique(data, newSortedSet());
    }

    /**
     * 
     * @param data
     * @return 
     */
    public final Set<String> toSet(final Iterable<String> data) {
        return SetUtils.addAllUnique(data, newSet());
    }

    /**
     * 
     * @return 
     */
    public abstract boolean isCaseSensitive();
    
    /**
     * 
     * @return 
     */
    public abstract boolean isCaseInsensitive();

    /**
     * 
     * @return 
     */
    public abstract boolean isIgnoreCase();

    /**
     * 
     * @return 
     */
    public abstract boolean isUnknown();

    /**
     * 
     * @param s1
     * @param s2
     * @return 
     */
    public final boolean equals(final CharSequence s1, final CharSequence s2) {
        if (isIgnoreCase()) {
            return StringUtils.equalsIgnoreCase(s1, s2);
        } else {
            return StringUtils.equals(s1, s2);
        }
    }
    
    /**
     * 
     * @param s1
     * @param s2
     * @return 
     */
    public final boolean startsWith(final CharSequence s1, final CharSequence s2) {
        if (isIgnoreCase()) {
            return StringUtils.startsWithIgnoreCase(s1, s2);
        } else {
            return StringUtils.startsWith(s1, s2);
        }
    }

    /**
     * 
     * @param s1
     * @param s2
     * @return 
     */
    public final boolean endsWith(final String s1, final String s2) {
        if (isIgnoreCase()) {
            return StringUtils.endsWithIgnoreCase(s1, s2);
        } else {
            return StringUtils.endsWith(s1, s2);
        }
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public final io.vavr.collection.List<String> findDuplicates(final Iterable<String>... elements) {
        io.vavr.collection.List<String> duplicates = io.vavr.collection.List.empty();
        if (elements == null || elements.length == 0) {
            return duplicates;
        }
        final SortedSet<String> dest = newSortedSet();
        for (final Iterable<String> itr : elements) {
            for (final String s : itr) {
                if (!dest.add(s)) {
                    duplicates = duplicates.prepend(s);
                }
            }
        }
        dest.clear();
        return duplicates.reverse();
    }
    
    /**
     * 
     * @param c
     * @return 
     */
    abstract public int hashCode(final char c);
    
    /**
     * 
     * @param cs
     * @return 
     */
    public final int hashCode(final CharSequence cs) {
        final int len = cs == null ? 0 : cs.length();
        if (len == 0) {
            return 0;
        }
        int h = 0;
        for (int i = 0; i < len; i++) {
            h = 31 * h + hashCode(cs.charAt(i));
        }
        return h;
    }

    /**
     * 
     * @param first
     * @param second
     * @param more
     * @return 
     */
    public List<String> intersection(
            final Iterable<String> first,
            final Iterable<String> second,
            final Iterable<String>... more) {
        final Equator<String> eq = equator();
        final List<String> commons = IterableAccessors.from(first)
                .distinct(eq)
                .to(Java.arrayList());
        for (final Iterable<String> iterable : ObjectUtils.varArgsToList(second, more)) {
            for (final Iterator<String> itr = commons.iterator(); itr.hasNext(); ) {
                final String value = itr.next();
                // final Predicate<String> filter = s -> comp.compare(s, value) == 0;
                final Predicate<String> filter = s -> eq.equate(s, value);
                if (IterableAccessors.from(iterable).findFirst(filter).isEmpty()) {
                    itr.remove();
                }
            }
        }
        return commons;
    }

    /**
     * 
     * @param target
     * @param elements 
     */
    public final void retainAll(
            final Iterable<String> target,
            final Iterable<String> elements) {
        final Comparator<String> comp = comparator();
        for (final Iterator<String> itr = target.iterator(); itr.hasNext(); ) {
            boolean remove = true;
            final String e = itr.next();
            for (final String s : elements) {
                if (equals(e, s)) {
                    remove = false;
                    break;
                }
            }
            if (remove) {
                itr.remove();
            }
        }
    }

    /**
     * 
     * @param cs
     * @param search
     * @param replacement
     * @return 
     */
    public final String replace(final CharSequence cs, final CharSequence search, final CharSequence replacement) {
        final String s1 = StringUtils.toString(cs);
        final String s2 = StringUtils.toString(search);
        final String s3 = StringUtils.toString(replacement);
        if (isIgnoreCase()) {
            return StringUtils.replaceIgnoreCase(s1, s2, s3);
        } else {
            return StringUtils.replace(s1, s2, s3);
        }
    }

    /**
     * 
     * @param target
     * @param elements 
     */
    public final void removeAll(
            final Iterable<String> target,
            final Iterable<String> elements) {
        final Comparator<String> comp = comparator();
        for (final Iterator<String> itr = target.iterator(); itr.hasNext(); ) {
            final String e = itr.next();
            for (final String s : elements) {
                if (equals(e, s)) {
                    itr.remove();
                    break;
                }
            }
        }
    }

    /**
     * 
     */
    final private static String IS_UNKNOWN_ERROR_MSG = "Unknown case sensitivity";

    /**
     * 
     */
    // final private static Comparator<String> JDK_NATURAL_ORDER = Comparator.naturalOrder();
    
    /**
     * 
     */
    final private static Comparator<String> VAVR_NATURAL_ORDER = io.vavr.collection.TreeSet.<String>empty().comparator();

    /**
     * 
     */
    // final private static Comparator<String> CASE_INSENSITIVE_COMPARATOR = String.CASE_INSENSITIVE_ORDER;

    /**
     * 
     * @param <K>
     * @param <V> 
     */
    static class CSIndexedMapImpl<K, V> extends IndexedMapImpl<K, V> {

        CSIndexedMapImpl(final Map<K, IndexedMap.Entry<K, V>> entryMap) {
            super(entryMap);
        }
        
    }
    
}
