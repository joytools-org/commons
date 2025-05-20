/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.IndexedMapAccessor;

/**
 *
 * @author AndreaR
 */
class UnboundIndexedMapAccessor<K, V> extends NakedUnboundIndexedMapAccessor<K, V> implements IndexedMapAccessor<K, V> {

    private final static UnboundIndexedMapAccessor<Object, Object> INSTANCE = new UnboundIndexedMapAccessor();
    
    public static <R, T> UnboundIndexedMapAccessor<R, T> instance() {
        return (UnboundIndexedMapAccessor<R, T>)INSTANCE;
    }
    
}
