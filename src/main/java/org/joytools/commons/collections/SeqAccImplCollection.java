/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import io.vavr.control.Option;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.joytools.commons.accessors.Accessors;
import org.joytools.commons.accessors.ListAccessor;
import static org.joytools.commons.collections.SeqAccImpl.findFirst0;
import static org.joytools.commons.collections.SeqAccImpl.findLast0;
import static org.joytools.commons.collections.SeqAccImpl.first0;
import static org.joytools.commons.collections.SeqAccImpl.last0;
import org.joytools.commons.interop.Java;
import org.joytools.commons.interop.Vavr;

/**
 * 
 * @param <E> 
 */
abstract class SeqAccImplCollection<E> extends SeqAccImpl<E> {

    protected abstract Collection<E> delegated();

    @Override
    public String toString() {
        return delegated().toString();
    }

    @Override
    public final boolean contains(final E element) {
        return delegated().contains(element);
    }

    @Override
    public final Iterator<E> iterator() {
        return delegated().iterator();
    }

    @Override
    public final Stream<E> stream() {
        return delegated().stream();
    }

    @Override
    public final Stream<E> parallelStream() {
        return delegated().stream().parallel();
    }

    @Override
    public final Spliterator<E> spliterator() {
        return delegated().spliterator();
    }

    @Override
    public final <T> T to(final Function<? super Iterable<E>, T> function) {
        return function.apply(delegated());
    }

    /* @Override
    public final com.google.common.collect.ImmutableList<E> toImmutableList() {
        return com.google.common.collect.ImmutableList.copyOf(delegated());
    } */

    @Override
    public final ListAccessor<E> toAccessor() {
        final Collection<E> c = delegated();
        if (c instanceof List<E> list) {
            return Accessors.of(list);
        }
        return Accessors.of(to(Java.arrayList()));
    }

    @Override
    public final Option<E> first() {
        return first0(delegated());
    }

    @Override
    public final Option<E> last() {
        return last0(delegated());
    }

    @Override
    public final Option<E> findFirst(final Predicate<? super E> filter) {
        return findFirst0(delegated(), filter);
    }

    @Override
    public final Option<E> findLast(final Predicate<? super E> filter) {
        return findLast0(delegated(), filter);
    }

    @Override
    public final boolean isEmpty() {
        return delegated().isEmpty();
    }

    @Override
    public final int size() {
        return delegated().size();
    }

    @Override
    public Option<E> get(final int index) {
        return IterableUtils.get(delegated(), index);
    }

    @Override
    public E getOrNull(final int index) {
        return IterableUtils.getOrNull(delegated(), index);
    }

    @Override
    public E getOrFail(final int index) {
        return IterableUtils.getOrFail(delegated(), index);
    }

    @Override
    public E getOrElse(final int index, final E def) {
        return IterableUtils.getOrElse(delegated(), index, def);
    }

}
