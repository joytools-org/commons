/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import java.util.Objects;
import org.joytools.commons.accessors.Accessor;
import org.joytools.commons.accessors.FixedSizeListMutator;
import org.joytools.commons.accessors.IndexedMapMutator;
import org.joytools.commons.accessors.ListMutator;
import org.joytools.commons.accessors.MapMutator;

/**
 *
 * @author AndreaR
 */
public class DelegatedMutators {
    
    public static <K, V> DelegatedMapMutator<K, V> of(final MapMutator<K, V> acc) {
        Objects.requireNonNull(acc, "MapMutator");
        return () -> acc;
    }
    
    public static <K, V> DelegatedListMutator<V> of(final ListMutator<V> acc) {
        Objects.requireNonNull(acc, "ListMutator");
        return () -> acc;
    }

    public static <K, V> DelegatedIndexedMapMutator<K, V> of(final IndexedMapMutator<K, V> acc) {
        Objects.requireNonNull(acc, "MapMutator");
        return () -> acc;
    }

}
