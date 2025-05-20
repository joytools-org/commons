/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import java.util.Objects;
import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;
import org.joytools.commons.accessors.FixedSizeMapMutator;

/**
 *
 * @author AndreaR
 */
public class DelegatedFixedSizeStringMutators {
    
    public static <V> DelegatedFixedSizeStringMapMutator<V> of(final FixedSizeMapMutator<String, V> acc) {
        Objects.requireNonNull(acc, "MapMutator");
        return () -> acc;
    }
    
    public static <V> DelegatedFixedSizeIndexedStringMapMutator<V> of(final FixedSizeIndexedMapMutator<String, V> acc) {
        Objects.requireNonNull(acc, "IndexedMapMutator");
        return () -> acc;
    } 

}
