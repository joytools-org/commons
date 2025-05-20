/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.joytools.commons.function.FailableAutoCloseable;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <E>
 */
public final class EarlyFailableDisposer<T, E extends Exception> {
 
    /**
     * 
     * @param disposer 
     */
    public EarlyFailableDisposer(final Disposer<E> disposer) {
        this.disposer = Objects.requireNonNull(disposer, "Disposer");
    }
    
    /**
     * 
     * @param item
     * @param autoCloseable
     * @return 
     */
    public T dispose(final T item, final FailableAutoCloseable<E> autoCloseable) {
        final Proxy proxy = registerValidatedProxy(item, autoCloseable);
        disposer.dispose(proxy);
        return item;
    }

    /**
     * 
     * @param item
     * @param autoCloseable
     * @return 
     * @throws E 
     */
    public T checkedDispose(final T item, final FailableAutoCloseable<E> autoCloseable) throws E {
        final Proxy proxy = registerValidatedProxy(item, autoCloseable);
        disposer.checkedDispose(proxy);
        return item;
    }
    
    /**
     * 
     * @param item
     * @param autoCloseable
     * @return 
     */
    protected Proxy registerValidatedProxy(final T item,
            final FailableAutoCloseable<E> autoCloseable) {
        Objects.requireNonNull(item, "Item");
        Objects.requireNonNull(autoCloseable, "FailableAutoCloseable");
        if (autoCloseable instanceof FailableOpenable openable) {
            if (openable.isOpenInvoked()) {
                throw new IllegalArgumentException("Item " + item + " is an openable managed in the outer scope");
            }
        }
        for (final Tuple2<T, Proxy> reg : registered) {
            if (reg._1() == item || reg._2() == autoCloseable) {
                throw new IllegalArgumentException("Item " + reg + " is already registered on this EarlyFailableDisposer");
            }
        }
        final Proxy proxy = new Proxy(autoCloseable);
        registered.add(Tuple(item, proxy));
        return proxy;
    }
    
    /**
     * 
     * @param item 
     * @throws E 
     */
    public void earlyClose(final T item) throws E {
        Objects.requireNonNull(item, "Item");
        for (final Iterator<Tuple2<T, Proxy>> itr = registered.iterator(); itr.hasNext(); ) {
            final Tuple2<T, Proxy> p = itr.next();
            if (p._1() == item) {
                try {
                    p._2().close();
                    return;
                } finally {
                    itr.remove();
                }
            }
        }
        throw new IllegalArgumentException("Item " + item + " is not registered on this EarlyFailableDisposer");
    }
    
    /**
     * 
     */
    protected class Proxy implements FailableAutoCloseable<E> {

        Proxy(final FailableAutoCloseable<E> item) {
            this.item = Objects.requireNonNull(item, "FailableAutoCloseable");
        }
        
        @Override
        public void close() throws E {
            if (item != null) {
                try {
                    item.close();
                } finally {
                    item = null;
                }
            }
        }
        
        private FailableAutoCloseable<E> item;
        
    }
    
    private final Disposer<E> disposer;
    
    private final List<Tuple2<T, Proxy>> registered = new ArrayList<>();
    
}

