/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import java.util.Objects;
import static org.joytools.commons.accessors.MutatorImplUtils.checkNotImmutable;
import org.joytools.commons.accessors.delegated.DelegatedFixedSizeStringMapMutator;
import org.joytools.commons.accessors.collection.CollectionFixedSizeStringMutators;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class FixedSizeStringMutators {
    
    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    public static <V> FixedSizeStringMapMutator<V> of(final java.util.Map<String, V> map) {
        return CollectionFixedSizeStringMutators.of(checkNotImmutable(map));
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
        return CollectionFixedSizeStringMutators.of(checkNotImmutable(entry), cs);
    }

    /**
     * 
     * @param <V>
     * @param mut
     * @return 
     */
    public static <V> FixedSizeStringMapMutator<V> of(final FixedSizeMapMutator<String, V> mut) {
        Objects.requireNonNull(mut, "Accessor");
        if (mut instanceof FixedSizeStringMapMutator) {
            return FixedSizeStringMapMutator.class.cast(mut);
        }
        return (DelegatedFixedSizeStringMapMutator<V>) () -> mut;
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
