/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.vavr;

import org.joytools.commons.accessors.StringMapAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
abstract class VavrStringMapAccessor<V> extends VavrMapAccessor<String, V> implements StringMapAccessor<V> {
    
    protected VavrStringMapAccessor(final io.vavr.collection.Map<String, V> map) {
        super(map);
    }
    
}
