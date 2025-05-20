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
public interface FixedSizeIndexedMapMutator<K, V> extends IndexedMapAccessor<K, V>, FixedSizeMapMutator<K, V>, FixedSizeListMutator<V> {
    
    @Override
    default <Z> FixedSizeIndexedMapMutator<K, Z> castAs(final Class<Z> type) {
        return (FixedSizeIndexedMapMutator<K, Z>)this;
    }

}
