/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractImmutableIndexedMapEntry<K, V> extends AbstractIndexedMapEntry<K, V> {
    
    @Override
    public V setValue(final V value) {
        throw new IllegalStateException("'" + getKey() + "' is read only");
    }
    
}
