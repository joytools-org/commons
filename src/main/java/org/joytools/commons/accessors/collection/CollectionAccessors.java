/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Objects;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.MapAccessor;

/**
 *
 * @author AndreaR
 */
public class CollectionAccessors {

    /**
     * 
     * @param <K>
     * @param <V>
     * @param list
     * @return 
     */
    public static <K, V> ListAccessor<V> of(final java.util.List<V> list) {
        Objects.requireNonNull(list, "List");
        return new JavaListListAccessor<V>() {
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
    public static <K, V> MapAccessor<K, V> of(final java.util.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return new JavaMapMapAccessor<K, V>() {
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
    public static <K, V> IndexedMapAccessor<K, V> of(final java.util.Map.Entry<K, V> entry) {
        return new MapEntryIndexedMapAccessor<>(entry);
    }

}
