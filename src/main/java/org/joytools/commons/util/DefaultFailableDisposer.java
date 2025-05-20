/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import java.util.Objects;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.function.FailableAutoCloseable;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public class DefaultFailableDisposer<E extends Exception> extends AbstractDisposer<FailableAutoCloseable<E>, E> {

    /**
     * 
     */
    protected DefaultFailableDisposer() {
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T extends Exception> DefaultFailableDisposer<T> of() {
        return new DefaultFailableDisposer();
    }
    
    /**
     * 
     * @param <T>
     * @param clazz
     * @return 
     */
    public static <T extends Exception> DefaultFailableDisposer<T> of(final Class<T> clazz) {
        return new DefaultFailableDisposer();
    }

    /**
     * 
     * @param ac
     * @throws E 
     */
    @Override
    protected void close(final FailableAutoCloseable<E> ac) throws E {
        ac.close();
    }

    /**
     * 
     * @param <T>
     * @param item
     * @return 
     * @throws java.lang.Exception 
     */
    @Override
    public <X extends E, T extends FailableAutoCloseable<X>> T checkedDispose(final T item) throws Exception {
        return checkedDispose0(item);
    }

    /**
     * 
     * @param <X>
     * @param <T>
     * @param item
     * @return 
     */
    @Override
    public <X extends E, T extends FailableAutoCloseable<X>> T dispose(final T item) {
        return dispose0(item);
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
        return push(item, (FailableAutoCloseable<E>)closeable);
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
        Objects.requireNonNull(closer);
        return push(item, () -> closer.accept(item));
    }

    /**
     * 
     * @param <X>
     * @param <T>
     * @param item
     * @return
     * @throws Exception 
     */
    protected <X extends E, T extends FailableAutoCloseable<X>> T checkedDispose0(final T item) throws Exception {
        Objects.requireNonNull(item, "Item");
        if (item instanceof FailableOpenable op) {
            if (!op.isOpenInvoked()) {
                push(op, op);
                checkedOpen0(op);
            }               
            return item;
        }
        return push(item, (FailableAutoCloseable<E>)item);
    }

    /**
     * 
     * @param <X>
     * @param <T>
     * @param item
     * @return 
     */
    protected <X extends E, T extends FailableAutoCloseable<X>> T dispose0(final T item) {
        Objects.requireNonNull(item, "Item");
        if (item instanceof FailableOpenable op) {
            if (!op.isOpenInvoked()) {
                push(op, op);
                open0(op);
            }               
            return item;
        }
        return push(item, (FailableAutoCloseable<E>)item);
    }

}
