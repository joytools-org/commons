/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.function.FailableAutoCloseable;


/**
 *
 * @author AndreaR
 * @param <E>
 */
public abstract class FailableAutoCloseableContainer<E extends Exception> implements FailableAutoCloseable<E> {

    /**
     * 
     */
    protected FailableAutoCloseableContainer() {
    }
    
    /**
     * 
     * @throws E
     */
    @Override
    public void close() throws E {
        if (disp != null) {
            disp.close();
        }
    }
    
    /**
     * 
     * @param <X>
     * @param <T>
     * @param autoCloseable
     * @return 
     */
    protected <X extends E, T extends FailableAutoCloseable<X>> T checkedDispose(final T autoCloseable) throws E {
        return disposer().checkedDispose(autoCloseable);
    }

    /**
     * 
     * @param <X>
     * @param <T>
     * @param autoCloseable
     * @return 
     */
    protected <X extends E, T extends FailableAutoCloseable<X>> T dispose(final T autoCloseable) {
        return disposer().dispose(autoCloseable);
    }

    /**
     * 
     * @param <T>
     * @param <X>
     * @param item
     * @param autoCloseable
     * @return 
     */
    protected <X extends E, T> T dispose(final T item, final FailableAutoCloseable<X> autoCloseable) {
        return disposer().dispose(item, autoCloseable);
    }

    /**
     * 
     * @param <T>
     * @param <X>
     * @param item
     * @param closer
     * @return 
     */
    protected <X extends E, T> T dispose(final T item, final FailableConsumer<? super T, X> closer) {
        return disposer().dispose(item, closer);
    }
    
    /**
     * 
     * @return 
     */
    protected Disposer<E> disposer() {
        if (disp == null) {
            disp = new DefaultFailableDisposer<>();
        }
        return disp;
    }
    
    /**
     * 
     * @param o
     * @return
     * @throws E 
     */
    protected <R> R maybeCheckedDispose(final R o) throws E {
        if (o == null || !(o instanceof FailableOpenable)) {
            return o;
        }
        final FailableOpenable<E> f = FailableOpenable.class.cast(o);
        checkedDispose(f);
        return o;
    }
    
    /**
     * 
     * @param <R>
     * @param o
     * @return 
     */
    protected <R> boolean maybeDispose(final R o) {
        if (o == null || !(o instanceof FailableOpenable)) {
            return false;
        }
        dispose(FailableOpenable.class.cast(o));
        return true;
    }

    /**
     * 
     */
    private Disposer<E> disp = null;
    
}
