/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import static io.vavr.API.None;
import io.vavr.control.Option;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.commons.collections4.IteratorUtils;
import org.joytools.commons.accessors.Accessors;
import org.joytools.commons.accessors.ListAccessor;

/**
 * 
 */
class SeqAccImplEmpty extends SeqAccImpl<Object> {

    @Override
    public boolean contains(final Object element) {
        return false;
    }

    @Override
    public Option findFirst(final Predicate filter) {
        return None();
    }

    @Override
    public Option findLast(final Predicate filter) {
        return None();
    }

    @Override
    public Option first() {
        return None();
    }

    @Override
    public Option last() {
        return None();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Stream stream() {
        return StreamSupport.stream(spliterator(), false);
    }

    @Override
    public Stream parallelStream() {
        return StreamSupport.stream(spliterator(), true);
    }

    @Override
    // public final <T, F extends Function<Iterable<Object>, T>> T to(final F function) {
    public final <T> T to(final Function<? super Iterable<Object>, T> function) {
        return function.apply(this);
    }

    @Override
    public final ListAccessor toAccessor() {
        return Accessors.empty();
    }

    @Override
    public Spliterator spliterator() {
        return Spliterators.emptySpliterator();
    }

    @Override
    public Iterator iterator() {
        return IteratorUtils.emptyIterator();
    }

    @Override
    public Option<Object> get(final int index) {
        return IterableUtils.get(this, index);
    }

    @Override
    public Object getOrNull(final int index) {
        return IterableUtils.getOrNull(this, index);
    }

    @Override
    public Object getOrFail(final int index) {
        return IterableUtils.getOrFail(this, index);
    }

    @Override
    public Object getOrElse(final int index, final Object def) {
        return IterableUtils.getOrElse(this, index, def);
    }

};
