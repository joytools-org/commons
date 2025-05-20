/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util;

import org.joytools.commons.function.FailableAutoCloseable;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public interface FailableOpenable<E extends Exception> extends FailableAutoCloseable<E> {
    
    /**
     * 
     * @throws E 
     */
    public void open() throws E;

    /**
     * 
     * @return 
     */
    public boolean isOpenInvoked();
    
}
