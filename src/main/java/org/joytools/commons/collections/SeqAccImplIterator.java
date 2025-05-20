/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import io.vavr.control.Option;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.joytools.commons.accessors.Accessors;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.interop.Vavr;

/**
 * 
 * @param <E> 
 */
abstract class SeqAccImplIterator<E> extends SeqAccImpl<E> {

    @Override
    public final boolean contains(final E item) {
        return contains0(item, this);
    }

    @Override
    public final Stream<E> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public final Stream<E> parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    @Override
    public final Spliterator<E> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }

    @Override
    public final <T> T to(final Function<? super Iterable<E>, T> function) {
        return function.apply(this);
    }

    @Override
    public final ListAccessor<E> toAccessor() {
        return Accessors.of(to(Vavr.array()).asJava());
    }

    @Override
    public final Option<E> first() {
        return first0(this);
    }

    @Override
    public final Option<E> last() {
        return last0(this);
    }

    @Override
    public final Option<E> findFirst(final Predicate<? super E> filter) {
        return findFirst0(this, filter);
    }

    @Override
    public final Option<E> findLast(final Predicate<? super E> filter) {
        return findLast0(this, filter);
    }

    @Override
    public final boolean isEmpty() {
        return IterableUtils.isEmpty(this);
    }

    @Override
    public final int size() {
        return IterableUtils.size(this);
    }

    @Override
    public Option<E> get(final int index) {
        return IterableUtils.get(this, index);
    }

    @Override
    public E getOrNull(final int index) {
        return IterableUtils.getOrNull(this, index);
    }

    @Override
    public E getOrFail(final int index) {
        return IterableUtils.getOrFail(this, index);
    }

    @Override
    public E getOrElse(final int index, final E def) {
        return IterableUtils.getOrElse(this, index, def);
    }

}
