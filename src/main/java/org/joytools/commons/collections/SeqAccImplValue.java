/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import io.vavr.Value;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.Iterator;
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
import static org.joytools.commons.collections.SeqAccImpl.ofValue0;

/**
 * 
 * @param <E> 
 */
abstract class SeqAccImplValue<E> extends SeqAccImpl<E> {

    abstract protected Value<E> delegated();

    @Override
    public final String toString() {
        return delegated().toString();
    }

    @Override
    public final boolean contains(final E element) {
        return delegated().contains(element);
    }

    @Override
    public Iterator<E> iterator() {
        return delegated().iterator();
    }

    @Override
    public final Stream<E> stream() {
        return delegated().toJavaStream();
    }

    @Override
    public final Stream<E> parallelStream() {
        return delegated().toJavaParallelStream();
    }

    @Override
    public final Spliterator<E> spliterator() {
        return delegated().spliterator();
    }

    @Override
    // public final <T, F extends Function<Iterable<E>, T>> T to(final F function) {
    public final <T> T to(final Function<? super Iterable<E>, T> function) {
        return function.apply(delegated());
    }

    /* @Override
    public final com.google.common.collect.ImmutableList<E> toImmutableList() {
        return com.google.common.collect.ImmutableList.copyOf(delegated());
    } */

    @Override
    public final ListAccessor<E> toAccessor() {
        final Value<E> v = delegated();
        if (v instanceof Seq<E> seq) {
            return Accessors.of(seq);
        }
        return Accessors.of(v.toArray().asJava());
    }

    @Override
    public Option<E> first() {
        return first0(delegated());
    }

    @Override
    public Option<E> last() {
        return last0(delegated());
    }

    @Override
    public Option<E> findFirst(final Predicate<? super E> filter) {
        return findFirst0(delegated(), filter);
    }

    @Override
    public Option<E> findLast(final Predicate<? super E> filter) {
        return findLast0(delegated(), filter);
    }

    @Override
    public <T> IterableAccessor<T> map(final Function<? super E, ? extends T> mapper) {
        return ofValue0(delegated().map(mapper));
    }

    @Override
    public final boolean isEmpty() {
        return delegated().isEmpty();
    }

    @Override
    public final int size() {
        return IterableUtils.size(delegated());
    }

    @Override
    public final Option<E> get(final int index) {
        return IterableUtils.get(delegated(), index);
    }

    @Override
    public final E getOrNull(final int index) {
        return IterableUtils.getOrNull(delegated(), index);
    }

    @Override
    public final E getOrFail(final int index) {
        return IterableUtils.getOrFail(delegated(), index);
    }

    @Override
    public final E getOrElse(final int index, final E def) {
        return IterableUtils.getOrElse(delegated(), index, def);
    }

}
