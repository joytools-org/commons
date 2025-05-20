/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.IndexedStringMapAccessor;

/**
 *
 * @author AndreaR
 */
public class CoreStringAccessors {

    public static <R> IndexedStringMapAccessor<R> empty() {
        return EmptyIndexedStringMapAccessor.instance();
    }
    
}
