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
public interface WritableTimeVariant<T> extends ReadableTimeVariant<T>, WritableVariant<T> {
    
    public void setTimeInMillis(final long timeInMillis);
    
}
