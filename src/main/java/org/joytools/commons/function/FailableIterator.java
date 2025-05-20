/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.function;

import java.util.Iterator;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <E>
 */
public interface FailableIterator<T, E extends Throwable> {
    
    /**
     * 
     * @return
     * @throws E 
     */
    public boolean hasNext() throws E;
    
    /**
     * 
     * @return
     * @throws E 
     */
    public T next() throws E;

    /**
     * 
     * @throws E 
     */
    default void remove() throws E {
        throw new UnsupportedOperationException("remove");
    }
    
    /**
     * 
     * @param failable
     * @return 
     */
    default Iterator<T> asIterator(final Failable failable) {
        return failable.asIterator(this);
    }

}
