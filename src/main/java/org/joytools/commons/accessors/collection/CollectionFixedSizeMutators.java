/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Objects;
import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;
import org.joytools.commons.accessors.FixedSizeListMutator;
import org.joytools.commons.accessors.FixedSizeMapMutator;

/**
 *
 * @author AndreaR
 */
public class CollectionFixedSizeMutators {
    

    /**
     * 
     * @param <K>
     * @param <V>
     * @param list
     * @return 
     */
    public static <K, V> FixedSizeListMutator<V> of(final java.util.List<V> list) {
        Objects.requireNonNull(list, "List");
        return new JavaListFixedSizeListMutator<V>() {
            @Override
            protected java.util.List<V> list() {
                return list;
            }
        };
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> FixedSizeMapMutator<K, V> of(final java.util.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return new JavaMapFixedSizeMapMutator<K, V>() {
            @Override
            protected java.util.Map<K, V> map() {
                return map;
            }
        };
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param entry
     * @return 
     */
    public static <K, V> FixedSizeIndexedMapMutator<K, V> of(final java.util.Map.Entry<K, V> entry) {
        Objects.requireNonNull(entry, "Map.Entry");
        return new MapEntryFixedSizeIndexedMapMutator<>(entry);
    }
    
}
