/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.joytools.commons.accessors;

import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public abstract interface Accessor<V> {
    
    /**
     * 
     * @return 
     */
    public int size();

    /**
     * 
     * @return 
     */
    default boolean isEmpty() {
        return size() == 0;
    }

}
