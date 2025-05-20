/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Objects;
import org.joytools.commons.accessors.ListMutator;
import org.joytools.commons.accessors.MapMutator;

/**
 *
 * @author AndreaR
 */
public class CollectionMutators {
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param list
     * @return 
     */
    public static <K, V> ListMutator<V> of(final java.util.List<V> list) {
        Objects.requireNonNull(list, "List");
        return new JavaListListMutator<V>() {
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
    public static <K, V> MapMutator<K, V> of(final java.util.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return new JavaMapMapMutator<K, V>() {
            @Override
            protected java.util.Map<K, V> map() {
                return map;
            }
        };
    }
    
}
