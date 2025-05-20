/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Callable;

public class CallablesNew {
    
    /**
     * 
     * @param <T>
     * @param <C>
     * @param type
     * @return 
     */
    public static <T, C extends Callable<T>> SeqComposedSequentialCallableBuilder<T> composed(final Class<T> type) {
        Objects.requireNonNull(type, "Type");
        return new SeqComposedSequentialCallableBuilder<>();
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param tasks
     * @return 
     */
    public static <T, C extends Callable<? extends T>> SeqComposedSequentialCallableBuilder<T> composed(final Iterable<? extends Callable<T>> tasks) {
        final SeqComposedSequentialCallableBuilder<T> builder = new SeqComposedSequentialCallableBuilder<>();
        return builder.addAll(tasks);
    }

    /**
     * 
     * @param <T>
     * @param <C>
     * @param tasks
     * @return 
     */
    public static <T, C extends Callable<? extends T>> SeqComposedSequentialCallableBuilder<T> composed(final C... tasks) {
        final SeqComposedSequentialCallableBuilder<T> builder = new SeqComposedSequentialCallableBuilder<>();
        return builder.addAll(tasks);
    }
        
}