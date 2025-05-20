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
public abstract class AbstractFailableOpenableConsumer<T, E extends Exception> extends AbstractFailableOpenable<E> implements FailableOpenableConsumer<T, E> {

    /**
     * 
     * @param payload 
     * @throws E 
     */
    @Override
    public final void accept(final T payload) throws E {
        checkedMakeOpen();
        consume(payload);
    }

    /**
     * 
     * @param item 
     * @retuows E  
     */
   
    abstract protected void consume(final T item) throws E;
    
}
