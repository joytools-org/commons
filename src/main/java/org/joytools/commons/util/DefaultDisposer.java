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
 */
public class DefaultDisposer extends AbstractDisposer<AutoCloseable, Exception> {

    /**
     * 
     */
    protected DefaultDisposer() {
    }
    
    /**
     * 
     * @return 
     */
    public static DefaultDisposer create() {
        return new DefaultDisposer();
    }
    
    /**
     * 
     * @param ac
     * @throws Exception 
     */
    @Override
    protected void close(final AutoCloseable ac) throws Exception {
        ac.close();
    }
    
    /**
     * 
     * @param <T>
     * @param item
     * @return 
     */
    public <T extends AutoCloseable> T dispose(final T item) {
        return dispose0(item);
    }

    /**
     * 
     * @param <T>
     * @param item
     * @return
     * @throws Exception 
     */
    protected <T extends AutoCloseable> T checkedDispose0(final T item) throws Exception {
        Objects.requireNonNull(item, "Item");
        if (item instanceof FailableOpenable op) {
            if (!op.isOpenInvoked()) {
                push(op, op);
                checkedOpen0(op);
            }               
            return item;
        }
        return push(item, item);
    }

    /**
     * 
     * @param <T>
     * @param item
     * @return 
     */
    protected <T extends AutoCloseable> T dispose0(final T item) {
        Objects.requireNonNull(item, "Item");
        if (item instanceof FailableOpenable op) {
            if (!op.isOpenInvoked()) {
                push(op, op);
                open0(op);
            }               
            return item;
        }
        return push(item, item);
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
    public <X extends Exception, T extends FailableAutoCloseable<X>> T dispose(final T item) {
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
    public <X extends Exception, T> T dispose(final T item, final FailableAutoCloseable<X> closeable) {
        return push(item, closeable);
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
        Objects.requireNonNull(closer);
        return push(item, () -> closer.accept(item));
    }

}
