/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.function;

import java.util.Objects;
import org.apache.commons.lang3.function.FailableConsumer;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <E>
 */
@FunctionalInterface
public interface FailableIterable<T, E extends Throwable> {
    
    /**
     * 
     * @return
     * @throws E 
     */
    public FailableIterator<T, E> iterator() throws E;
    
    /**
     * 
     * @param action
     * @throws E 
     */
    default void forEach(final FailableConsumer<T, E> action) throws E {
        Objects.requireNonNull(action, "Action");
        final FailableIterator<T, E> itr = iterator();
        while (itr.hasNext()) {
            action.accept(itr.next());
        }
    }
    
}
