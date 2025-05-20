/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.function.Predicate;
import com.google.common.collect.PeekingIterator;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.apache.commons.collections4.Equator;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.function.Processor;
import org.joytools.commons.accessors.ListAccessor;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public interface IterableAccessor<E> extends java.lang.Iterable<E> {
    
    /**
     * 
     * @param consumer
     * @return 
     */
    public IterableAccessor<E> accept(final Consumer<? super E> consumer);

    /**
     * 
     * @param element
     * @return 
     */
    public IterableAccessor<E> append(final E element);

    /**
     * 
     * @param elements
     * @return 
     */
    public IterableAccessor<E> append(final E... elements);

    /**
     * 
     * @param elements
     * @return 
     */
    public IterableAccessor<E> append(final Iterable<E> elements);

    /**
     * 
     * @param elements
     * @return 
     */
    public IterableAccessor<E> append(final Iterator<E> elements);

    /**
     * 
     * @param elements
     * @return 
     */
    public IterableAccessor<E> append(final io.vavr.collection.Iterator<E> elements);

    /**
     * 
     * @param elements
     * @return 
     */
    public IterableAccessor<E> append(final Enumeration<E> elements);

    /**
     * 
     * @param offset
     * @param max
     * @return 
     */
    public IterableAccessor<E> bounded(final long offset, final long max);

    /**
     * 
     * @param other
     * @return 
     */
    public IterableAccessor<E> collated(final Iterator<? extends E> other);

    /**
     * 
     * @param other
     * @return 
     */
    public IterableAccessor<E> collated(final Iterable<? extends E> other);

    /**
     * 
     * @param comparator
     * @param other
     * @return 
     */
    public IterableAccessor<E> collated(final Comparator<? super E> comparator, final Iterator<? extends E> other);
    
    /**
     * 
     * @param comparator
     * @param other
     * @return 
     */
    public IterableAccessor<E> collated(final Comparator<? super E> comparator, final Iterable<? extends E> other);
    
    /**
     * 
     * @param <C>
     * @param collection
     * @return 
     */
    public <C extends Collection<? super E>> C collect(final C collection);

    /**
     * 
     * @param <C>
     * @param supplier
     * @return 
     */
    public <C extends Collection<? super E>> C collect(final Supplier<C> supplier);

    /**
     * 
     * @param <R>
     * @param <A>
     * @param collector
     * @return 
     */
    public <R, A> R collect(final Collector<? super E, A, R> collector);

    /**
     * 
     * @param element
     * @return 
     */
    public boolean contains(final E element);

    /**
     * 
     * @param elements
     * @return 
     */
    public boolean containsAll(final Iterable<E> elements);

    /**
     * 
     * @param elements
     * @return 
     */
    public boolean containsAll(final E... elements);

    /**
     * 
     * @param elements
     * @return 
     */
    public boolean containsAny(final Iterable<E> elements);
    /**
     * 
     * @param elements
     * @return 
     */
    public boolean containsAny(final E... elements);

    /**
     * 
     * @return 
     */
    public IterableAccessor<E> distinct();

    /**
     * 
     * @param comp
     * @return 
     */
    public IterableAccessor<E> distinct(final Comparator<E> comp);

    /**
     * 
     * @param <R>
     * @param func
     * @return 
     */
    public <R> IterableAccessor<E> distinct(final Function<E, R> func);

    /**
     * 
     * @param eq
     * @return 
     */
    public IterableAccessor<E> distinct(final Equator<E> eq);

    /**
     * 
     * @return 
     */
    public IterableAccessor<E> eval();

    /**
     * 
     * @return 
     */
    public IterableAccessor<E> cycling();

    /**
     * 
     * @param <T>
     * @param mapper
     * @return 
     */
    public <T> IterableAccessor<T> map(final Function<? super E, ? extends T> mapper);

    /**
     * 
     * @param filter
     * @return 
     */
    public Option<E> findFirst(final Predicate<? super E> filter);

    /**
     * 
     * @param filter
     * @return 
     */
    public E findFirstOrNull(final Predicate<? super E> filter);

    /**
     * 
     * @param filter
     * @param defValue
     * @return 
     */
    public E findFirstOrElse(final Predicate<? super E> filter, final E defValue);

    /**
     * 
     * @param filter
     * @param defValue
     * @return 
     */
    public E findFirstOrElse(final Predicate<? super E> filter, final Supplier<E> defValue);

    /**
     * 
     * @param filter
     * @return 
     */
    public E findFirstOrFail(final Predicate<? super E> filter);

    /**
     * 
     * @param filter
     * @return 
     */
    public Option<E> findLast(final Predicate<? super E> filter);

    /**
     * 
     * @param filter
     * @return 
     */
    public E findLastOrNull(final Predicate<? super E> filter);

    /**
     * 
     * @param filter
     * @param defValue
     * @return 
     */
    public E findLastOrElse(final Predicate<? super E> filter, final E defValue);

    /**
     * 
     * @param filter
     * @param defValue
     * @return 
     */
    public E findLastOrElse(final Predicate<? super E> filter, final Supplier<E> defValue);

    /**
     * 
     * @param filter
     * @return 
     */
    public E findLastOrFail(final Predicate<? super E> filter);

    /**
     * 
     * @return 
     */
    public Option<E> first();
    
    /**
     * 
     * @return 
     */
    public E firstOrNull();

    /**
     * 
     * @param defValue
     * @return 
     */
    public E firstOrElse(final E defValue);

    /**
     * 
     * @param defValue
     * @return 
     */
    public E firstOrElse(final Supplier<E> defValue);

    /**
     * 
     * @return 
     */
    public E firstOrFail();
    
    /**
     * 
     * @param <R>
     * @param flattener
     * @return 
     */
    public <R> IterableAccessor<R> flatMap(final Function<? super E, ? extends Iterable<? extends R>> flattener);

    /**
     * 
     */
    public void forEach();

    /**
     * 
     * @param action 
     */
    @Override
    public void forEach(final Consumer<? super E> action);
    
    /**
     * 
     * @param action 
     */
    public void forEachWithIndex(final ObjIntConsumer<? super E> action);

    /**
     * 
     * @param <T>
     * @param action 
     * @throws T 
     */
    public <T extends Throwable> void forEachFailable(final FailableConsumer<? super E, T> action) throws T;

    /**
     * 
     * @param <V> 
     */
    public interface Interruptible<V> {
        
        public void forEach() throws InterruptedException;

        public void forEach(final Consumer<? super V> action) throws InterruptedException;
        
        public void forEachWithIndex(final ObjIntConsumer<? super V> action) throws InterruptedException;

        public <T extends Throwable> void forEachFailable(final FailableConsumer<? super V, T> action) throws T, InterruptedException;
        
    }
    
    /**
     * 
     * @return 
     */
    public Interruptible<E> interruptible();
    
    /**
     * 
     * @return 
     */
    public Option<E> last();

    /**
     * 
     * @return 
     */
    public E lastOrNull();

    /**
     * 
     * @param defValue
     * @return 
     */
    public E lastOrElse(final E defValue);

    /**
     * 
     * @param defValue
     * @return 
     */
    public E lastOrElse(final Supplier<E> defValue);

    /**
     * 
     * @return 
     */
    public E lastOrFail();

    /**
     * 
     * @return 
     */
    public boolean isEmpty();
    
    /**
     * 
     * @param filter
     * @return 
     */
    public IterableAccessor<E> filter(final Predicate<? super E> filter);
    /**
     * 
     * @return 
     */
    public PeekingIterator<E> peekingIterator();
    
    /**
     * 
     * @param element
     * @return 
     */
    public IterableAccessor<E> intersperse(final E element);

    /**
     * 
     * @return 
     */
    public String mkString();
    
    /**
     * 
     * @param delimiter
     * @return 
     */
    public String mkString(final CharSequence delimiter);

    /**
     * 
     * @param prefix
     * @param delimiter
     * @param suffix
     * @return 
     */
    public String mkString(final CharSequence prefix, final CharSequence delimiter, final CharSequence suffix);

    /**
     * 
     * @return 
     */
    public IterableAccessor<E> singleUse();
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<E> multipleUse();
    
    /**
     * 
     * @param index
     * @return 
     */
    public Option<E> get(final int index);
    
    /**
     * 
     * @param index
     * @return 
     */
    public E getOrNull(final int index);

    /**
     * 
     * @param index
     * @param def
     * @return 
     */
    public E getOrElse(final int index, final E def);

    /**
     * 
     * @param index
     * @return 
     */
    public E getOrFail(final int index);

    /**
     * 
     * @param filter
     * @return 
     */
    public IterableAccessor<E> omit(final Predicate<? super E> filter);

    /**
     * 
     * @return 
     */
    public IterableAccessor<E> remove();
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<E> reversed();

    /**
     * 
     */
    public int size();
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<E> sorted();

    /**
     * 
     * @param comparator
     * @return 
     */
    public IterableAccessor<E> sorted(final Comparator<? super E> comparator);

    /**
     * 
     * @param ascending
     * @param comparator
     * @return 
     */
    public IterableAccessor<E> sorted(final Comparator<? super E> comparator,
            final boolean ascending);

    /**
     * 
     * @return 
     */
    public Stream<E> stream();

    /**
     * 
     * @return 
     */
    public Stream<E> parallelStream();
    
    /**
     * 
     * @param processor
     * @return 
     */
    public IterableAccessor<E> process(final Processor<? super E, Boolean> processor);

    /**
     * 
     * @param max
     * @return 
     */
    public IterableAccessor<E> top(final long max);

    /**
     * 
     * @param <T>
     * @param function
     * @return 
     */
    // public <T, F extends Function<Iterable<E>, T>> T to(final F function);
    public <T> T to(final Function<? super Iterable<E>, T> function);
    
    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T>
     * @param mapper1
     * @param mapper2
     * @param function
     * @return 
     */
    public <T1, T2, T> T to(
            final Function<? super E, T1> mapper1,
            final Function<? super E, T2> mapper2,
            final Function<? super Iterable<Tuple2<T1, T2>>, T> function);

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T>
     * @param mapper1
     * @param mapper2
     * @param mapper3
     * @param function
     * @return 
     */
    public <T1, T2, T3, T> T to(
            final Function<? super E, T1> mapper1,
            final Function<? super E, T2> mapper2,
            final Function<? super E, T3> mapper3,
            final Function<? super Iterable<Tuple3<T1, T2, T3>>, T> function);

    /**
     * 
     * @param <T>
     * @param function
     * @return 
     */
    public <T> T evalTo(final Function<? super Iterable<E>, T> function);

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T>
     * @param mapper1
     * @param mapper2
     * @param function
     * @return 
     */
    public <T1, T2, T> T evalTo(
            final Function<? super E, T1> mapper1,
            final Function<? super E, T2> mapper2,
            final Function<? super Iterable<Tuple2<T1, T2>>, T> function);

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T>
     * @param mapper1
     * @param mapper2
     * @param mapper3
     * @param function
     * @return 
     */
    public <T1, T2, T3, T> T evalTo(
            final Function<? super E, T1> mapper1,
            final Function<? super E, T2> mapper2,
            final Function<? super E, T3> mapper3,
            final Function<? super Iterable<Tuple3<T1, T2, T3>>, T> function);

    /**
     * 
     * @return 
     */
    public ListAccessor<E> toAccessor();

    /**
     * 
     * @return 
     */
    public Iterable<E> toIterable();

    /**
     * 
     * @return 
     */
    public IterableAccessor<E> unmodifiable();

}

