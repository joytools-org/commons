/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.ListAccessor;

/**
 *
 * @author AndreaR
 */
class ArrayListAccessor<V> extends NakedArrayMutator<V> implements ListAccessor<V> {
 
    public ArrayListAccessor(final V[] array) {
        super(array);
    }
    
}
