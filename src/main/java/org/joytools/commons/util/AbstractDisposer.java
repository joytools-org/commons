/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import java.util.Objects;
import java.util.Stack;

/**
 *
 * @author AndreaR
 */
abstract class AbstractDisposer<T extends AutoCloseable, E extends Exception> implements Disposer<E> {

    /**
     * 
     * @param closeable
     * @throws E 
     */
    abstract void close(final T closeable) throws E;
    
    /**
     * 
     * @throws E 
     */
    protected final void closeAll() throws E {
        while (!stack.isEmpty()) {
            final Tuple2<Object, T> p = stack.pop();
            try {
                close(p._2);
            } finally {
                // Close remaining ignoring additional exceptions
                while (!stack.isEmpty()) {
                    final Tuple2<Object, T> q = stack.pop();
                    try {
                        close(q._2);
                    } catch (final Exception ignore) {
                        // Ignore remaining
                    }
                }
            }
        }
    }
    
    /**
     * 
     * @param item
     * @return 
     */
    protected final <R, Z extends T> R push(final R item, final Z closeable) {
        Objects.requireNonNull(item, "Item");
        Objects.requireNonNull(closeable, "Closeable");
        for (final Tuple2<Object, T> p : stack) {
            if (item == p._1() || item == p._2()
                    || closeable == p._1() || closeable == p._2()) {
                throw new IllegalArgumentException("Item already registered in disposer: " + item);
            }
        }
        stack.push(Tuple(item, closeable));
        return item;
    }

    /**
     * 
     * @param openable
     * @return
     * @throws E 
     */
    protected FailableOpenable<E> checkedOpen0(final FailableOpenable<E> openable) throws E {
        return open0(openable);
        /*
        openable.open();
        return openable; 
        */
    }
    
    /**
     * 
     * @param openable
     * @return 
     */
    protected FailableOpenable<E> open0(final FailableOpenable<E> openable) {
        try {
            openable.open();
        } catch (final Exception e) {
            throw new IllegalStateException("Initialization of " + openable.getClass().getName() + " failed", e);
        }
        return openable;
    }

    /**
     * 
     */
    private final Stack<Tuple2<Object, T>> stack = new Stack();
    
    @Override
    public final void close() throws E {
        closeAll();
    }
    
}
