/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public interface FixedSizeIndexedStringMapMutator<V> extends FixedSizeIndexedMapMutator<String, V>, IndexedStringMapAccessor<V> {
    
    @Override
    default <Z> FixedSizeIndexedStringMapMutator<Z> castAs(final Class<Z> type) {
        return (FixedSizeIndexedStringMapMutator<Z>)this;
    }

}
