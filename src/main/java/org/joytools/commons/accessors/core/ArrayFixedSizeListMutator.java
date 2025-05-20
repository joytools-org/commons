/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.FixedSizeListMutator;

/**
 *
 * @author AndreaR
 */
class ArrayFixedSizeListMutator<V> extends NakedArrayMutator<V> implements FixedSizeListMutator<V> {
 
    public ArrayFixedSizeListMutator(final V[] array) {
        super(array);
    }
    
}
