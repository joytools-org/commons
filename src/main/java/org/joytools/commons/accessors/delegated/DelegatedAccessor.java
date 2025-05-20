/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.Accessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface DelegatedAccessor<V> {
    
    public Accessor<V> delegated();
    
}
