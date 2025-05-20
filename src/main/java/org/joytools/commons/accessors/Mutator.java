/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public abstract interface Mutator<V> extends Accessor<V> {

    /**
     * 
     * @return 
     */
    // public boolean isIndexed();

    /**
     * 
     * @return 
     */
    public boolean isUpdatable();
    
}
