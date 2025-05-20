/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.function;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public interface FailableAutoCloseable<E extends Exception> extends AutoCloseable {
    
    @Override
    public void close() throws E;
    
}
