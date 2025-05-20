/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util;

import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.function.FailableAutoCloseable;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public interface Disposer<E extends Exception> extends FailableAutoCloseable<E> {
    
    /**
     * 
     * @param <X>
     * @param <T>
     * @param autoCloseable
     * @return 
     * @throws E 
     */
    public <X extends E, T extends FailableAutoCloseable<X>> T checkedDispose(final T autoCloseable) throws E;

    /**
     * 
     * @param <X>
     * @param <T>
     * @param autoCloseable
     * @return 
     */
    public <X extends E, T extends FailableAutoCloseable<X>> T dispose(final T autoCloseable);

    /**
     * 
     * @param <X>
     * @param <T>
     * @param item
     * @param autoCloseable
     * @return 
     */
    public <X extends E, T> T dispose(final T item, final FailableAutoCloseable<X> autoCloseable);

    /**
     * 
     * @param <X>
     * @param <T>
     * @param item
     * @param closer
     * @return 
     */
    public <X extends E, T> T dispose(final T item, final FailableConsumer<? super T, X> closer);
        
}
