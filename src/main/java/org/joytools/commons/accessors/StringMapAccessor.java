/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.Tuple2;
import org.joytools.commons.accessors.vavr.VavrStringAccessors;
import org.joytools.commons.util.CaseSensitivitySupport;
import static org.joytools.commons.accessors.Mutators.of;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface StringMapAccessor<V> extends MapAccessor<String, V>, CaseSensitivitySupport {
    
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

    /**
     * 
     * @param <V> 
     */
    interface ToJava<V> {
        
        public java.util.Map<String, V> linkedMap();

        public java.util.SortedMap<String, V> sortedMap();
        
        default MapMutator<String, V> linkedMapMutator() {
            return of(linkedMap());
        }

        default MapMutator<String, V> sortedMapMutator() {
            return of(sortedMap());
        }

    }
    
    /**
     * 
     * @param <V> 
     */
    interface ToVavr<V> {
        
        public io.vavr.collection.SortedMap<String, V> sortedMap();

        default StringMapAccessor<V> sortedMapAccessor() {
            return VavrStringAccessors.of(sortedMap());
        }

    }
    
    /**
     * 
     * @return 
     */
    default public ToJava<V> toJava() {
        
        return new ToJava<V>() {
            @Override
            public java.util.Map<String, V> linkedMap() {
                final java.util.Map<String, V> res = caseSensitivity().newLinkedMap();
                for (final Tuple2<String, V> e : entriesByKey()) {
                    MutatorMethods.insert(res, e._1(), e._2());
                }
                return res;
            }

            @Override
            public java.util.SortedMap<String, V> sortedMap() {
                final java.util.SortedMap<String, V> res = caseSensitivity().newSortedMap();
                for (final Tuple2<String, V> e : entriesByKey()) {
                    MutatorMethods.insert(res, e._1(), e._2());
                }
                return res;
            }
        };
                
    }
    
    /**
     * 
     * @return 
     */
    default public ToVavr<V> toVavr() {
        
        return new ToVavr<V>() {
            @Override
            public io.vavr.collection.SortedMap<String, V> sortedMap() {
                return io.vavr.collection.TreeMap.ofEntries(caseSensitivity().comparator(), entriesByKey());
            }
        };
    
    }

    @Override
    default <Z> StringMapAccessor<Z> castAs(final Class<Z> type) {
        return (StringMapAccessor<Z>)this;
    }

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
