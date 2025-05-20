/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import java.util.Objects;
import org.joytools.commons.accessors.IndexedMapMutator;
import org.joytools.commons.accessors.MapMutator;

/**
 *
 * @author AndreaR
 */
public class DelegatedStringMutators {
    
    public static <V> DelegatedStringMapMutator<V> of(final MapMutator<String, V> acc) {
        Objects.requireNonNull(acc, "MapMutator");
        return () -> acc;
    }

    public static <V> DelegatedIndexedStringMapMutator<V> of(final IndexedMapMutator<String, V> acc) {
        Objects.requireNonNull(acc, "IndexedMapMutator");
        return () -> acc;
    } 

}
