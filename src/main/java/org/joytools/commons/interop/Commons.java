/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.interop;

/**
 *
 * @author AndreaR
 */
class Commons {
    
    /**
     * 
     * @param <R>
     * @return 
     */
    static <R> R throwMoreThanOneElementFound() {
        throw new IllegalStateException("More than one element found");
    }
    
}
