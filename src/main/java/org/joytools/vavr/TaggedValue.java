/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import io.vavr.Value;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <E>
 */
public interface TaggedValue<E, T> extends Value<E>, Tagged<T> {
    
    @SuppressWarnings("unchecked")
    static <E, T> TaggedValue<E, T> narrow(final TaggedValue<? extends E, ? extends T> value) {
        return (TaggedValue<E, T>) value;
    }    
    
    @Override
    public <U> TaggedValue<U, T> map(final Function<? super E, ? extends U> mapper);
    
    @Override
    public TaggedValue<E, T> peek(final Consumer<? super E> action);
    
}
