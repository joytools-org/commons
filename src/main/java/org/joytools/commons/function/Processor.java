/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.function;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 * @param <P>
 * @param <Q>
 */
@FunctionalInterface
public interface Processor<P, Q> {
    
    /**
     * 
     * @param payload
     * @return 
     */
    public Q process(final P payload);
    
    /**
     * 
     * @return 
     */
    default Consumer<P> consumer() {
        return p -> {
            process(p);
        };
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

    /**
     * 
     * @param <C>
     * @param exceptions
     * @return 
     */
    default <C extends Collection<Exception>> Processor<P, Q> catchAndRethrow(final C exceptions) {
        Objects.requireNonNull(exceptions, "Exceptions");
        return p -> {
            try {
                return process(p);
            } catch (final Exception e) {
                exceptions.add(e);
                return ExceptionUtils.wrapAndThrow(e);
            }
        };
    }
    
}
