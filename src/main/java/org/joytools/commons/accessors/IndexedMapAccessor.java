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
public interface IndexedMapAccessor<K, V> extends MapAccessor<K, V>, ListAccessor<V> {
    
    /**
     * 
     * @param <Z>
     * @param type
     * @return 
     */
    @Override
    default <Z> IndexedMapAccessor<K, Z> castAs(final Class<Z> type) {
        return (IndexedMapAccessor<K, Z>)this;
    }

    /**
     * 
     * @param <K>
     * @param <V> 
     */
    public interface Reader<K, V> extends MapAccessor.Reader<K, V>, ListAccessor.Reader<V> {
    }    
    
    /**
     * 
     * @return 
     */
    @Override
    public Reader<K, V> read();
    
}
