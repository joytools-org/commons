/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import java.util.Objects;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.MapAccessor;

/**
 *
 * @author AndreaR
 */
public class DelegatedAccessors {
    
    public static <K, V> DelegatedMapAccessor<K, V> of(final MapAccessor<K, V> acc) {
        Objects.requireNonNull(acc, "MapAccessor");
        return () -> acc;
    }
    
    public static <K, V> DelegatedListAccessor<V> of(final ListAccessor<V> acc) {
        Objects.requireNonNull(acc, "MapAccessor");
        return () -> acc;
    }

    public static <K, V> DelegatedIndexedMapAccessor<K, V> of(final IndexedMapAccessor<K, V> acc) {
        Objects.requireNonNull(acc, "IndexedMapAccessor");
        return () -> acc;
    }

}
