/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface ResettableVariable<T> extends Variable<T> {
    
    /**
     * 
     */
    default void reset() {
        throw new UnsupportedOperationException();
    }
    
}
