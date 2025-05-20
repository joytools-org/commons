/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 *
 * @author AndreaR
 * @param <E>
 */
@Deprecated
public abstract class AbstractBlockingQueueProxyV1<E> implements BlockingQueue<E> {

    protected abstract BlockingQueue<E> queue();
    
    @Override
    public boolean add(final E e) {
        return queue().add(e);
    }

    @Override
    public boolean offer(final E e) {
        return queue().offer(e);
    }

    @Override
    public void put(final E e) throws InterruptedException {
        queue().put(e);
    }

    @Override
    public boolean offer(final E e, final long timeout, final TimeUnit unit) throws InterruptedException {
        return queue().offer(e, timeout, unit);
    }

    @Override
    public E take() throws InterruptedException {
        return queue().take();
    }

    @Override
    public E poll(final long timeout, final TimeUnit unit) throws InterruptedException {
        return queue().poll(timeout, unit);
    }

    @Override
    public int remainingCapacity() {
        return queue().remainingCapacity();
    }

    @Override
    public boolean remove(final Object o) {
        return queue().remove(o);
    }

    @Override
    public boolean contains(final Object o) {
        return queue().contains(o);
    }

    @Override
    public int drainTo(final Collection<? super E> c) {
        return queue().drainTo(c);
    }

    @Override
    public int drainTo(final Collection<? super E> c, final int maxElements) {
        return queue().drainTo(c, maxElements);
    }

    @Override
    public E remove() {
        return queue().remove();
    }

    @Override
    public E poll() {
        return queue().poll();
    }

    @Override
    public E element() {
        return queue().element();
    }

    @Override
    public E peek() {
        return queue().peek();
    }

    @Override
    public int size() {
        return queue().size();
    }

    @Override
    public boolean isEmpty() {
        return queue().isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return queue().iterator();
    }

    @Override
    public Object[] toArray() {
        return queue().toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return queue().toArray(a);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return queue().containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        return queue().addAll(c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        return queue().removeAll(c);
    }

    @Override
    public boolean removeIf(final Predicate<? super E> filter) {
        return queue().removeIf(filter);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        return queue().retainAll(c);
    }

    @Override
    public void clear() {
        queue().clear();
    }

    @Override
    public Spliterator<E> spliterator() {
        return queue().spliterator();
    }

    @Override
    public Stream<E> stream() {
        return queue().stream();
    }

    @Override
    public Stream<E> parallelStream() {
        return queue().parallelStream();
    }

    @Override
    public void forEach(final Consumer<? super E> action) {
        queue().forEach(action);
    }
    
}
