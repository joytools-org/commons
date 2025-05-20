/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;
import org.joytools.commons.accessors.IndexedMapMutator;

/**
 *
 * @author AndreaR
 */
public class CoreMutators {
 
    /**
     * 
     * @param <T>
     * @param <R>
     * @return 
     */
    public static <T, R> IndexedMapMutator<T, R> unbound() {
        return UnboundIndexedMapMutator.instance();
    }
    
}
