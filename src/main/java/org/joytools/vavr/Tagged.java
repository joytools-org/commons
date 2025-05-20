/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import io.vavr.control.Option;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface Tagged<T> {
    
    /**
     * 
     * @return 
     */
    public Option<T> getTag();
    
    /**
     * 
     * @return 
     */
    default T getTagOrNull() {
        return getTag().getOrNull();
    }
        
    /**
     * 
     * @param t
     * @return 
     */
    default T getTagOrElse(final T t) {
        return getTag().getOrElse(t);
    }

}
