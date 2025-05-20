/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

/**
 *
 * @author AndreaR
 */
class EmptyIndexedMapAccessor<K, V> extends AbstractEmptyIndexedMapAccessor<K, V> {

    private final static EmptyIndexedMapAccessor<Object, Object> INSTANCE = new EmptyIndexedMapAccessor();
    
    public static <R, T> EmptyIndexedMapAccessor<R, T> instance() {
        return (EmptyIndexedMapAccessor<R, T>)INSTANCE;
    }
       
}
