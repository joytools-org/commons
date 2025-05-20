/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.function;

/**
 *
 * @author AndreaR
 */
public class FailableAutoCloseables {
    
    public static <X extends Exception> FailableAutoCloseable<X> of(final FailableAutoCloseable<X> x) {
        return x;
    }
    
}
