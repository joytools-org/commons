/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import java.util.Objects;
import java.util.Stack;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.function.FailableAutoCloseable;
import org.joytools.commons.function.Failables;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public class DefaultFailableDisposerV1BeforeAbstractDisposer<E extends Exception> implements Disposer<E> {

    /**
     * 
     */
    protected DefaultFailableDisposerV1BeforeAbstractDisposer() {
    }
    
    /**
     * 
     * @param <X>
     * @param <T>
     * @param item
     * @return 
     */
    protected <X extends E, T extends FailableAutoCloseable<X>> T dispose0(final T item) throws E {
        Objects.requireNonNull(item);
        if (item instanceof FailableOpenable) {
            final FailableOpenable<E> o = FailableOpenable.class.cast(item);
            if (!o.isOpenInvoked()) {
                push(o, o);
                o.open();
            }               
            return item;
        }
        return push(item, item);
    }

    /**
     * 
     * @param <T>
     * @param item
     * @param closer
     * @return 
     */
    @Override
    public <X extends E, T> T dispose(final T item, final FailableConsumer<? super T, X> closer) {
        Objects.requireNonNull(item);
        Objects.requireNonNull(closer);
        return dispose(item, () -> closer.accept(item));
    }

    /**
     * 
     * @param <T>
     * @param item
     * @param closeable 
     * @return  
     */
    protected <T> T push(final T item, final FailableAutoCloseable<? extends E> closeable) {
        for (final Tuple2<Object, FailableAutoCloseable<E>> p : stack) {
            if (item == p._1()) {
                throw new IllegalArgumentException("Item already registered in disposer: " + item);
            }
        }
        stack.push(Tuple(item, (FailableAutoCloseable<E>)closeable));
        return item;
    }
    
    /**
     * 
     * @param <T>
     * @param item
     * @return 
     */
    @Override
    public <X extends E, T extends FailableAutoCloseable<X>> T checkedDispose(final T item) throws E {
        return dispose0(item);
    }

    /**
     * 
     * @param <X>
     * @param <T>
     * @param item
     * @param failable
     * @return 
     */
    @Override
    public <X extends E, T extends FailableAutoCloseable<X>> T dispose(final T item) {
        try {
            return dispose0(item);
        } catch (final Exception e) {
            throw Failables.wrapped.rethrow(e);
        }
    }

    /**
     * 
     * @param <T>
     * @param item
     * @param closeable
     * @return 
     */
    @Override
    public <X extends E, T> T dispose(final T item, final FailableAutoCloseable<X> closeable) {
        Objects.requireNonNull(item);
        Objects.requireNonNull(closeable);
        return push(item, closeable);
    }

    /**
     * 
     * @throws E
     */
    protected void closeAll() throws E {
        while (!stack.isEmpty()) {
            final Tuple2<Object, FailableAutoCloseable<E>> p = stack.pop();
            try {
                p._2.close();
            } finally {
                // Close remaining ignoring additional exceptions
                while (!stack.isEmpty()) {
                    final Tuple2<Object, FailableAutoCloseable<E>> q = stack.pop();
                    try {
                        q._2.close();
                    } catch (final Exception ignore) {
                        // Ignore remaining
                    }
                }
            }
        }
    }

    /**
     * 
     */
    @Override
    public void close() throws E {
        closeAll();
    }
    
    /**
     * 
     */
    private final Stack<Tuple2<Object, FailableAutoCloseable<E>>> stack = new Stack<>();
    
}
