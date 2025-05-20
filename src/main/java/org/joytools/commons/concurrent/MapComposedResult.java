/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import io.vavr.Tuple2;
import io.vavr.collection.Map;
import io.vavr.control.Either;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public interface MapComposedResult<K, V> extends ComposedResult<K, V> {
    
    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    public MapComposedResult<K, V> append(final K key, Either<Exception, V> value);

    public MapComposedResult<K, V> appendAll(final Iterable<Tuple2<K, Either<Exception, V>>> values);

    public MapComposedResult<K, V> appendAll(final java.util.Map<K, Either<Exception, V>> values);

    /**
     * 
     * @param condition
     * @throws Exception 
     */
    public void throwIf(final Predicate<? super MapComposedResult<K, V>> condition) throws Exception;

    /**
     * 
     * @param condition
     * @param mapper
     * @throws Exception 
     */
    public void throwIf(final Predicate<? super MapComposedResult<K, V>> condition,
            final Function<? super MapComposedResult<K, V>, ? extends Exception> mapper) throws Exception;
    
}
