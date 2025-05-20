/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import java.util.Objects;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.MapAccessor;

/**
 *
 * @author AndreaR
 */
public class DelegatedStringAccessors {
    
    public static <V> DelegatedStringMapAccessor<V> of(final MapAccessor<String, V> acc) {
        Objects.requireNonNull(acc, "MapAccessor");
        return () -> acc;
    }
    
    public static <V> DelegatedIndexedStringMapAccessor<V> of(final IndexedMapAccessor<String, V> acc) {
        Objects.requireNonNull(acc, "IndexedMapAccessor");
        return () -> acc;
    } 

}
