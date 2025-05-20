/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.function;

import io.vavr.CheckedConsumer;
import java.util.function.Consumer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.util.AbstractFailableOpenableConsumer;
import org.joytools.commons.util.FailableOpenable;

/**
 *
 * @author AndreaR
 * @param <P>
 * @param <Q>
 * @param <E>
 */
@FunctionalInterface
public interface FailableProcessor<P, Q, E extends Exception> {
    
    /**
     * 
     * @param payload
     * @return 
     * @throws E 
     */
    public Q process(final P payload) throws E;

    /**
     * 
     * @return 
     */
    @Deprecated
    default FailableConsumer<P, E> asFailableConsumer1() {
        return FailableConsumers.of(this);
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    default FailableConsumer<P, E> asFailableConsumerV1() {
        if (this instanceof FailableOpenable) {
            final FailableOpenable<E> op = FailableOpenable.class.cast(this);
            return new AbstractFailableOpenableConsumer<P, E>() {
                @Override
                protected boolean isLazyOpen() {
                    return true;
                }

                @Override
                protected void init() throws E {
                    dispose(op);
                }
                
                @Override
                protected void consume(final P item) throws E {
                    process(item);
                }
            };
        }
        return this::process;
    }

    /**
     * 
     * @param failable
     * @return 
     */
    @Deprecated
    default Processor<P, Q> asProcessor1(final Failable failable) {
        return failable.asProcessor(this);
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    default CheckedConsumer<P> asCheckedConsumer1() {
        return x -> {
            process(x);
        };
    }

    /**
     * 
     * @param failable
     * @return 
     */
    @Deprecated
    default Consumer<P> asConsumer1(final Failable failable) {
        return failable.asConsumer(x -> {
            process(x);
        });
    }

    /**
     * 
     * @param <C>
     * @param exceptions
     * @return 
     */
    /*
    default <C extends Collection<Exception>> Processor<P, Q> catchAndFilter(final C exceptions) {
        Objects.requireNonNull(exceptions, "Exceptions");
        return p -> {
            try {
                return process(p);
            } catch (final Exception e) {
                exceptions.add(e);
                return false;
            }
        };
    }
    */
    
    /**
     * 
     * @param <C>
     * @param exceptions
     * @return 
     */
    /*
    default <C extends Collection<Exception>> Processor<P, Q> catchAndContinue(final C exceptions) {
        Objects.requireNonNull(exceptions, "Exceptions");
        return p -> {
            try {
                return process(p);
            } catch (final Exception e) {
                exceptions.add(e);
                return true;
            }
        };
    }
    */
    
}
