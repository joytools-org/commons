/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.control.Option;
import java.util.Iterator;
import org.apache.commons.collections4.IteratorUtils;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface Variable<T> extends Iterable<T> {
    
    default String name() {
        return null;
    }
    
    public T set(final T newValue);

    public T get();
    
    default boolean isSet() {
        return true;
    }
    
    @Override
    default Iterator<T> iterator() {
        return isSet() ? IteratorUtils.singletonIterator(get()) : IteratorUtils.emptyIterator();
    }
    
    default Option<T> toOption() {
        return isSet() ? Some(get()) : None();
    }

    /*
    default Value<T> toValue() {
        return isSet() ? Value.of(get()) : Value.absent();
    } */

}
