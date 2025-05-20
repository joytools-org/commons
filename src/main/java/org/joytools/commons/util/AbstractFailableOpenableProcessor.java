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
 * @param <R>
 * @param <E>
 */
public abstract class AbstractFailableOpenableProcessor<T, R, E extends Exception> extends AbstractFailableOpenable<E> implements FailableOpenableProcessor<T, R, E> {

    /**
     * 
     * @param payload
     * @return 
     * @throws E 
     */
    @Override
    public final R process(final T payload) throws E {
        checkedMakeOpen();
        return computeProcess(payload);
    }

    /**
     * 
     * @param item 
     * @return  
     * @throws E  
     */
    abstract protected R computeProcess(final T item) throws E;
    
}
