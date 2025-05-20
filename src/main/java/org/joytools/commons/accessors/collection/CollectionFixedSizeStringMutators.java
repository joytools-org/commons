/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Objects;
import org.joytools.commons.accessors.FixedSizeIndexedStringMapMutator;
import org.joytools.commons.accessors.FixedSizeStringMapMutator;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class CollectionFixedSizeStringMutators {
    
    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    public static <V> FixedSizeStringMapMutator<V> of(final java.util.Map<String, V> map) {
        Objects.requireNonNull(map, "Map");
        return new JavaMapFixedSizeStringMapMutator<V>() {
            @Override
            protected java.util.Map<String, V> map() {
                return map;
            }
        };
    }
    
    /**
     * 
     * @param <V>
     * @param entry
     * @param cs
     * @return 
     */
    public static <V> FixedSizeIndexedStringMapMutator<V> of(final java.util.Map.Entry<String, V> entry,
            final CaseSensitivity cs) {
        Objects.requireNonNull(entry, "Map.Entry");
        Objects.requireNonNull(cs, "CaseSentivitity");
        return new MapEntryFixedSizeIndexedStringMapMutator<V>(entry) {
            @Override
            public CaseSensitivity caseSensitivity() {
                return cs;
            }
        };
    }

    /**
     * 
     * @param <V>
     * @param entry
     * @return 
     */
    public static <V> FixedSizeIndexedStringMapMutator<V> ofIgnoreCase(final java.util.Map.Entry<String, V> entry) {
        return of(entry, CaseSensitivity.INSENSITIVE);
    }
   
}
