/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface FixedSizeStringMapMutator<V> extends StringMapAccessor<V>, FixedSizeMapMutator<String, V> {
    
    @Override
    default <Z> FixedSizeStringMapMutator<Z> castAs(final Class<Z> type) {
        return (FixedSizeStringMapMutator<Z>)this;
    }

    /**
     * 
     * @return 
     */ /*
    default java.util.SortedMap<String, V> toMutableSortedMap() {
        final java.util.SortedMap<String, V> res = new java.util.TreeMap<>(caseSensitivity().comparator());
        for (final Tuple2<String, V> e : entriesByKey()) {
            MutatorImplUtils.insert(res, e._1(), e._2());
        }
        return res;
    } */

    /**
     * 
     * @return 
     */ /*
    default io.vavr.collection.SortedMap<String, V> toImmutableSortedMap() {
        return io.vavr.collection.TreeMap.ofEntries(caseSensitivity().comparator(), entriesByKey());
    } */

    /*
    default public java.util.SortedMap<String, V> toJdkSortedMap() {
        return toJdkSortedMap(CaseSensitivity.SENSITIVE.comparator());
    }

    default public java.util.SortedMap<String, V> toJdkSortedMapIgnoreCase() {
        return toJdkSortedMap(CaseSensitivity.INSENSITIVE.comparator());
    }

    default public java.util.SortedMap<String, V> toJdkSortedMap(final CaseSensitivity cs) {
        return toJdkSortedMap(cs.comparator());
    }

    default public java.util.SortedMap<String, V> toJdkSortedMap(final Comparator<String> keyComp) {
        final java.util.SortedMap<String, V> res = new java.util.TreeMap<>(keyComp);
        for (final Tuple2<String, V> e : entriesByKey()) {
            MutatorImplUtils.insert(res, e._1(), e._2());
        }
        return res;
    }
    
    default public io.vavr.collection.SortedMap<String, V> toVavrSortedMap() {
        return toVavrSortedMap(CaseSensitivity.SENSITIVE.comparator());
    }

    default public io.vavr.collection.SortedMap<String, V> toVavrSortedMapIgnoreCase() {
        return toVavrSortedMap(CaseSensitivity.INSENSITIVE.comparator());
    }

    default public io.vavr.collection.SortedMap<String, V> toVavrSortedMap(final CaseSensitivity cs) {
        return toVavrSortedMap(cs.comparator());
    }

    default public io.vavr.collection.SortedMap<String, V> toVavrSortedMap(final Comparator<String> keyComp) {
        return io.vavr.collection.TreeMap.ofEntries(keyComp, entriesByKey());
    } */
    
}
