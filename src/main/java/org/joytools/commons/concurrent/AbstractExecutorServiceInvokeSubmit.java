/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Java;
// import org.joytools.commons.collections.StreamIterables;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractExecutorServiceInvokeSubmit<E> extends AbstractExecutorServiceWrapper {
    
    /**
     * 
     * @param es 
     */
    protected AbstractExecutorServiceInvokeSubmit(final ExecutorService es) {
        super(es);
    }

    /**
     * 
     * @return 
     */
    protected Iterable<Callable<E>> callables() {
        return callItr;
    }
    
    /**
     * 
     * @return 
     */
    protected List<Callable<E>> callableList() {
        if (callItr instanceof List list) {
            return list;
        }
        return IterableAccessors.from(callItr).to(Java.arrayList());
    }

    /**
     * 
     * @param callable
     * @return 
     */
    protected void add0(final Callable<E> callable) {
        checkNotNull(callable, "Callable");
        if (this.callItr instanceof List) {
            List.class.cast(callItr).add(callable);
        } else {
            throw new IllegalStateException("Callables are not updatable");
        }
    }

    /**
     * 
     * @param callables 
     */
    protected void set0(final Iterable<Callable<E>> callables) {
        checkNotNull(callables, "Callables");
        this.callItr = IterableAccessors.from(callables);
    }
    
    /**
     * 
     * @param <U>
     * @param callable
     * @param errorThreshold
     * @param counter
     * @param toBeCancelled
     * @param excludeIndex
     * @return 
     */
    static <U> Callable<U> watchErrors(final Callable<U> callable,
            final int errorThreshold,
            final AtomicInteger counter,
            final List<Future<U>> toBeCancelled,
            final int excludeIndex) {
        if (errorThreshold < 0 || errorThreshold == Integer.MAX_VALUE) {
            return callable;
        }
        return () -> {
            try {
                return callable.call();
            } catch (final Throwable t) {
                if (counter.incrementAndGet() > errorThreshold) {
                    synchronized(toBeCancelled) {
                        final ListIterator<Future<U>> itr = toBeCancelled.listIterator();
                        while (itr.hasNext()) {
                            final Future<U> f = itr.next();
                            final int index = itr.previousIndex();
                            if (!f.isDone() && index != excludeIndex) {
                                f.cancel(true);
                            }
                        }
                    }
                }
                throw t;
            }
        };
    };
    
    protected boolean isAboveErrorThreshold(final AtomicInteger errorCount) {
        if (isErrorThreshold()) {
            return errorCount.intValue() > errorThreshold;
        }
        return false;
    }
    
    protected boolean isErrorThreshold() {
        return errorThreshold >= 0 && errorThreshold < Integer.MAX_VALUE;
    }
    
    protected void errorThreshold0(final Integer threshold) {
        this.errorThreshold = threshold == null ? -1 : threshold;
    }

    protected int errorThreshold() {
        return errorThreshold;
    }
    
    private int errorThreshold = -1;

    private Iterable<Callable<E>> callItr = new ArrayList();
            
}
