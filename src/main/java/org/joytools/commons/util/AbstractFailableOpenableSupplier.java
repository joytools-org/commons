/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <E>
 */
public abstract class AbstractFailableOpenableSupplier<T, E extends Exception> extends AbstractFailableOpenable<E> implements FailableOpenableSupplier<T, E> {

    /**
     * 
     * @return 
     * @throws E 
     */
    @Override
    public final T get() throws E {
        checkedMakeOpen();
        return computeGet();
    }

    /**
     * 
     * @return  
     * @throws E  
     */
    abstract protected T computeGet() throws E;
    
}
