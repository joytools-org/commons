/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public abstract interface MultiValueAccessor<V> extends Accessor<V> {
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<V> values();
    
}
