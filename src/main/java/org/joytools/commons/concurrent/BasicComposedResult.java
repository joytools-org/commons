/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface BasicComposedResult<V> extends ComposedResult<Integer, V> {
    
    /**
     * 
     * @param value
     * @return 
     */
    public BasicComposedResult<V> append(final Either<Exception, V> value);
    
    /**
     * 
     * @param value
     * @return 
     */
    public BasicComposedResult<V> appendAll(final Iterable<Either<Exception, V>> value);

    /**
     * 
     * @param condition
     * @throws Exception 
     */
    public void throwIf(final Predicate<? super BasicComposedResult<V>> condition) throws Exception;

    /**
     * 
     * @param condition
     * @param mapper
     * @throws Exception 
     */
    public void throwIf(final Predicate<? super BasicComposedResult<V>> condition,
            final Function<? super BasicComposedResult<V>, ? extends Exception> mapper) throws Exception;
    
}
