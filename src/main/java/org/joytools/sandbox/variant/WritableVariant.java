/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.sandbox.variant;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface WritableVariant<T> extends ReadableVariant<T> {
    
    public void set(final T value);
    
}
