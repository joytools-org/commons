/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.IndexedMapMutator;

/**
 *
 * @author AndreaR
 */
class UnboundIndexedMapMutator<K, V> extends NakedUnboundIndexedMapAccessor<K, V> implements IndexedMapMutator<K, V> {

    private final static UnboundIndexedMapMutator<Object, Object> INSTANCE = new UnboundIndexedMapMutator();
    
    public static <R, T> UnboundIndexedMapMutator<R, T> instance() {
        return (UnboundIndexedMapMutator<R, T>)INSTANCE;
    }

}
