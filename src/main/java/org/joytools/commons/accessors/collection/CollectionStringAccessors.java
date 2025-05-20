/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Objects;
import org.joytools.commons.accessors.IndexedStringMapAccessor;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class CollectionStringAccessors {
    
    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final java.util.Map<String, V> map) {
        Objects.requireNonNull(map, "Map");
        return new JavaMapStringMapAccessor<V>() {
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
    public static <V> IndexedStringMapAccessor<V> of(final java.util.Map.Entry<String, V> entry,
            final CaseSensitivity cs) {
        Objects.requireNonNull(entry, "Map.Entry");
        Objects.requireNonNull(cs, "CaseSensitivity");
        return new MapEntryIndexedStringMapAccessor<V>(entry) {
            @Override
            public CaseSensitivity caseSensitivity() {
                return cs;
            }
        };
    }

}
