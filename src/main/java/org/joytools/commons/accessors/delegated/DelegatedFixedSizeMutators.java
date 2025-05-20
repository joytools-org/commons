/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import java.util.Objects;
import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;
import org.joytools.commons.accessors.FixedSizeListMutator;
import org.joytools.commons.accessors.FixedSizeMapMutator;

/**
 *
 * @author AndreaR
 */
public class DelegatedFixedSizeMutators {
    
    public static <K, V> DelegatedFixedSizeMapMutator<K, V> of(final FixedSizeMapMutator<K, V> acc) {
        Objects.requireNonNull(acc, "MapMutator");
        return () -> acc;
    }
    
    public static <K, V> DelegatedFixedSizeListMutator<V> of(final FixedSizeListMutator<V> acc) {
        Objects.requireNonNull(acc, "ListMutator");
        return () -> acc;
    }

    public static <K, V> DelegatedFixedSizeIndexedMapMutator<K, V> of(final FixedSizeIndexedMapMutator<K, V> acc) {
        Objects.requireNonNull(acc, "IndexedMapMutator");
        return () -> acc;
    }

}
