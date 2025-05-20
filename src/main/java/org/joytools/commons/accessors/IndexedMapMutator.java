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
public interface IndexedMapMutator<K, V> extends FixedSizeIndexedMapMutator<K, V>, MapMutator<K, V>, ListMutator<V> {
    
    /**
     * 
     * @param <Z>
     * @param type
     * @return 
     */
    @Override
    default <Z> IndexedMapMutator<K, Z> castAs(final Class<Z> type) {
        return (IndexedMapMutator<K, Z>)this;
    }

}
