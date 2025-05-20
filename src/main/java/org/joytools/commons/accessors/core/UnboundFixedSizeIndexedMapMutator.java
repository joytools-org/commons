/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;

/**
 *
 * @author AndreaR
 */
class UnboundFixedSizeIndexedMapMutator<K, V> extends NakedUnboundIndexedMapAccessor<K, V> implements FixedSizeIndexedMapMutator<K, V> {

    private final static UnboundFixedSizeIndexedMapMutator<Object, Object> INSTANCE = new UnboundFixedSizeIndexedMapMutator();
    
    public static <R, T> UnboundFixedSizeIndexedMapMutator<R, T> instance() {
        return (UnboundFixedSizeIndexedMapMutator<R, T>)INSTANCE;
    }

}
