/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Stack;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.function.Failable;
import org.joytools.commons.function.FailableAutoCloseable;
import org.joytools.commons.function.Failables;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 */
public class DefaultDisposerV1BeforeAbstractDisposer implements Disposer<Exception> {

    /**
     * 
     */
    protected DefaultDisposerV1BeforeAbstractDisposer() {
    }
    
    /**
     * 
     * @return 
     */
    public static DefaultDisposerV1BeforeAbstractDisposer create() {
        return new DefaultDisposerV1BeforeAbstractDisposer();
    }
    
    /**
     * 
     * @param <T>
     * @param item
     * @return 
     * @throws java.lang.Exception 
     */
    public <T extends AutoCloseable> T dispose(final T item) {
        try {
            return dispose0(item);
        } catch (final Exception ex) {
            throw Failables.wrapped.rethrow(ex);
        }
    }

    /**
     * 
     * @param <T>
     * @param item
     * @return
     * @throws Exception 
     */
    protected <T extends AutoCloseable> T dispose0(final T item) throws Exception {
        Objects.requireNonNull(item);
        if (item instanceof FailableOpenable) {
            final FailableOpenable o = FailableOpenable.class.cast(item);
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
    public <X extends Exception, T> T dispose(final T item, final FailableConsumer<? super T, X> closer) {
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
    protected <T> T push(final T item, final AutoCloseable closeable) {
        for (final Tuple2<Object, AutoCloseable> p : stack) {
            if (item == p._1()) {
                throw new IllegalArgumentException("Item already registered in disposer: " + item);
            }
        }
        stack.push(Tuple(item, closeable));
        return item;
    }
    
    /**
     * 
     * @param <T>
     * @param item
     * @return 
     * @throws java.lang.Exception 
     */
    @Override
    public <X extends Exception, T extends FailableAutoCloseable<X>> T checkedDispose(final T item) throws Exception {
        return dispose0(item);
    }

    /**
     * 
     * @param <X>
     * @param <T>
     * @param item
     * @return 
     */
    @Override
    public <X extends Exception, T extends FailableAutoCloseable<X>> T dispose(final T item) {
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
    public <X extends Exception, T> T dispose(final T item, final FailableAutoCloseable<X> closeable) {
        Objects.requireNonNull(item);
        Objects.requireNonNull(closeable);
        return push(item, closeable);
    }

    /**
     * 
     * @param <T>
     * @param item
     * @return 
     * @throws java.lang.Exception 
     */
    @Deprecated
    private <T> T old_closeNow(final T item) throws Exception {
        Objects.requireNonNull(item);
        boolean found = false;
        final List<Exception> exceptions = new ArrayList<>();
        for (final ListIterator<Tuple2<Object, AutoCloseable>> itr = stack.listIterator(stack.size()); itr.hasPrevious(); ) {
            final Tuple2<Object, AutoCloseable> p = itr.previous();
            if (p._1 == item) {
                found = true;
                try {
                    p._2.close();
                } catch (final Exception e) {
                    exceptions.add(e);
                } finally {
                    itr.remove();
                }
            }
        }
        if (exceptions.isEmpty()) {
            if (!found) {
                throw new IllegalArgumentException("Item not registered on this disposer: " + item);
            }
            return item;
        }
        return ExceptionUtils.throwMultipleException(exceptions);
    }

    /**
     * 
     * @throws Exception 
     */
    protected void closeAll() throws Exception {
        final List<Exception> exceptions = new ArrayList<>();
        while (!stack.isEmpty()) {
            final Tuple2<Object, AutoCloseable> p = stack.pop();
            try {
                p._2.close();
            } catch (final Exception e) {
                exceptions.add(e);
            }
        }
        ExceptionUtils.throwMultipleException(exceptions);
    }

    /**
     * 
     * @throws Exception 
     */
    @Override
    public void close() throws Exception {
        closeAll();
    }
    
    /**
     * 
     */
    private final Stack<Tuple2<Object, AutoCloseable>> stack = new Stack<>();
    
}
