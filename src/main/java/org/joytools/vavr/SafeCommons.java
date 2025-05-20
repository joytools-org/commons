/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import java.util.function.Function;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 */
class SafeCommons {
       
    /**
     * 
     * @param <R>
     * @param throwable
     * @return 
     */
    static <R> R throwUnchecked(final Throwable throwable) {
        return ExceptionUtils.wrapAndThrow(throwable);
    }
    
    /**
     * 
     * @param <R>
     * @param throwable
     * @param mapper
     * @return 
     */
    static <R> R throwUnchecked(
            final Throwable throwable,
            final Function<Throwable, ? extends Throwable> mapper) {
        final Throwable t = mapper == null ? throwable : mapper.apply(throwable);
        return ExceptionUtils.wrapAndThrow(t);
    }

}
