/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Objects;
import org.joytools.commons.accessors.StringMapMutator;

/**
 *
 * @author AndreaR
 */
public class CollectionStringMutators {
    
    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    public static <V> StringMapMutator<V> of(final java.util.Map<String, V> map) {
        Objects.requireNonNull(map, "Map");
        return new JavaMapStringMapMutator<V>() {
            @Override
            protected java.util.Map<String, V> map() {
                return map;
            }
        };
    }
    
}
