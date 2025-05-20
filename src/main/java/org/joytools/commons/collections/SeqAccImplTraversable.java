/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import static org.joytools.commons.collections.SeqAccImpl.COULD_NOT_BE_ITERATED_AGAIN;
import static org.joytools.commons.collections.SeqAccImpl.ofTraversable0;

/**
 * 
 * @param <E> 
 */
abstract class SeqAccImplTraversable<E> extends SeqAccImplValue<E> {

    private boolean used = false;

    @Override
    abstract protected Traversable<E> delegated();

    @Override
    public final Iterator<E> iterator() {
        final Traversable<E> t = delegated();
        if (!t.isTraversableAgain()) {
            if (used) {
                throw new IllegalStateException(COULD_NOT_BE_ITERATED_AGAIN);
            }
            used = true;
        }
        return t.iterator();
    }

    @Override
    public final Option<E> first() {
        return delegated().headOption();
    }

    @Override
    public final E firstOrFail() {
        return delegated().head();
    }

    @Override
    public final Option<E> last() {
        return delegated().lastOption();
    }

    @Override
    public final E lastOrFail() {
        return delegated().last();
    }

    @Override
    public final Option<E> findFirst(final Predicate<? super E> filter) {
        return delegated().find(filter);
    }

    @Override
    public final Option<E> findLast(final Predicate<? super E> filter) {
        return delegated().findLast(filter);
    }

    @Override
    public IterableAccessor<E> filter(final Predicate<? super E> filter) {
        return ofTraversable0(delegated().filter(filter));
    }

    @Override
    public IterableAccessor<E> omit(final Predicate<? super E> filter) {
        return ofTraversable0(delegated().reject(filter));
    }

    @Override
    public <R> IterableAccessor<R> flatMap(
        final Function<? super E, ? extends Iterable<? extends R>> flattener) {
        return ofTraversable0(delegated().flatMap(flattener));
    }

}
