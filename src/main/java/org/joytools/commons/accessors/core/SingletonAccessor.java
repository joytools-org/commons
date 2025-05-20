/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.impl.NakedSingletonMutator;
import org.joytools.commons.accessors.IndexedMapAccessor;

/**
 *
 * @author AndreaR
 */
class SingletonAccessor<K, V> extends NakedSingletonMutator<K, V> implements IndexedMapAccessor<K, V> {

    private final V value;
    
    protected SingletonAccessor(final K key, V value) {
        super(key);
        this.value = value;
    }
    
    @Override
    protected V get0() {
        return value;
    }

    @Override
    protected void set0(final V value) {
        throw new UnsupportedOperationException("set");
    }

}
