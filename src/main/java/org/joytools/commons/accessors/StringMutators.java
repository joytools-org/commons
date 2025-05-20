/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import java.util.Objects;
import static org.joytools.commons.accessors.MutatorImplUtils.checkNotImmutable;
import org.joytools.commons.accessors.delegated.DelegatedStringMapMutator;
import org.joytools.commons.accessors.collection.CollectionStringMutators;

/**
 *
 * @author AndreaR
 */
public class StringMutators {
 
    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    public static <V> StringMapMutator<V> of(final java.util.Map<String, V> map) {
        return CollectionStringMutators.of(checkNotImmutable(map));
    }
    
    /**
     * 
     * @param <V>
     * @param mut
     * @return 
     */
    public static <V> StringMapMutator<V> of(final MapMutator<String, V> mut) {
        Objects.requireNonNull(mut, "Accessor");
        if (mut instanceof StringMapMutator smm) {
            return smm;
        }
        return (DelegatedStringMapMutator<V>) () -> mut;
    }

}
