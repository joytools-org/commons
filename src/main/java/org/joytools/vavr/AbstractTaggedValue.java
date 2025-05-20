/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import io.vavr.collection.Iterator;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 *
 * @author AndreaR
 * @param <E>
 * @param <T>
 */
public abstract class AbstractTaggedValue<E, T> implements TaggedValue<E, T> {

    @Override
    public final boolean isAsync() {
        return false;
    }

    @Override
    public final boolean isLazy() {
        return false;
    }

    @Override
    public final boolean isSingleValued() {
        return true;
    }

    @Override
    public final Iterator<E> iterator() {
        if (isEmpty()) {
            return Iterator.empty();
        }
        return Iterator.of(get());
    }
    
    @Override
    public String stringPrefix() {
        return "TaggedValue";
    }
    
    @Override
    public <U> TaggedValue<U, T> map(final Function<? super E, ? extends U> mapper) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TaggedValue<E, T> peek(final Consumer<? super E> action) {
        if (!isEmpty()) {
            action.accept(get());
        }
        return this;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(stringPrefix()).append('(');
        if (!isEmpty()) {
            sb.append(get());
        } else {
            sb.append("()");
        }
        return sb.append(',').append(getTag()).append(')').toString();
    }
    
}
