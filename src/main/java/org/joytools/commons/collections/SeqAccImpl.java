/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.AbstractIterator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.Value;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.apache.commons.collections4.Bag;
import org.apache.commons.collections4.Equator;
import org.apache.commons.collections4.IteratorUtils;
import org.apache.commons.collections4.bag.TreeBag;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.collections.IteratorsInternal.ConsumerIterator;
import org.joytools.commons.collections.IteratorsInternal.ProcessIterator;
import org.joytools.commons.function.Predicates;
import org.joytools.commons.function.Processor;
import org.joytools.commons.interop.Java;
import org.joytools.commons.accessors.ListAccessor;

/**
 *
 * @author AndreaR
 * @param <E>
 */
abstract class SeqAccImpl<E> implements IterableAccessor<E> {
    
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> IterableAccessor<T> empty() {
        return (IterableAccessor<T>)EMPTY_ITERATION;
    }

    /**
     * 
     * @param <T>
     * @param element
     * @return 
     */
    public static <T> IterableAccessor<T> of(final T element) {
        final List<T> list = new AbstractList<T>() {
            @Override
            public T get(final int index) {
                if (index == 0) {
                    return element;
                }
                throw new IndexOutOfBoundsException(String.valueOf(index));
            }
            @Override
            public int size() {
                return 1;
            }
        };
        return ofCollection0(list);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public static <T> IterableAccessor<T> ofAll(final java.util.Iterator<T> iterator) {
        if (iterator == null) {
            return ofJavaIterator0(IteratorUtils.EMPTY_ITERATOR);
        }
        // Special case for io.vavr.collection.Iterator
        if (iterator instanceof Iterable itr) {
            return ofAll(itr);
        }
        return ofJavaIterator0(iterator);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    static <T> IterableAccessor<T> ofAll(final io.vavr.collection.Iterator<T> iterator) {
        if (iterator == null) {
            return empty();
        }
        return ofValue0(Value.class.cast(iterator));
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    protected final static <T> IterableAccessor<T> ofJavaIterator0(final java.util.Iterator<T> iterator) {
        return new SeqAccImplSingleUseIterator<T>() {
            @Override
            protected Iterator<T> delegated() {
                return iterator;
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param enumeration
     * @return 
     */
    public static <T> IterableAccessor<T> ofAll(final Enumeration<T> enumeration) {
        // return ofAll(IteratorUtils.asIterator(enumeration));
        if (enumeration == null) {
            return ofAll(IteratorUtils.EMPTY_ITERATOR);
        }
        final Iterator<T> itr = new AbstractIterator<T>() {
            @Override
            protected T computeNext() {
                if (enumeration.hasMoreElements()) {
                   return enumeration.nextElement();
                }
                return endOfData();
            }
        };
        return ofAll(itr);
    }

    /**
     * 
     * @param <T>
     * @param iterable
     * @return 
     */
    public static <T> IterableAccessor<T> ofAll(final Iterable<T> iterable) {
        if (iterable == null) {
            return empty();
        }
        if (iterable instanceof IterableAccessor<T> seq) {
            return seq;
        }
        if (iterable instanceof ListAccessor list) {
            return list.values();
        }
        if (iterable instanceof Collection<T> coll) {
            return ofCollection0(coll);
        }
        if (iterable instanceof io.vavr.Value<T> value) {
            if (value.isEmpty()) {
                return empty();
            }
            if (iterable instanceof io.vavr.collection.Traversable<T> trav) {
                return ofTraversable0(trav);
            }
            if (iterable instanceof io.vavr.Value<T>) {
                return ofValue0(value);
            }
        }
        return new SeqAccImplIterator<T>() {
            @Override
            public Iterator<T> iterator() {
                return iterable.iterator();
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param iterable
     * @return 
     */
    static <T> IterableAccessor<T> ofAll(final Collection<T> iterable) {
        if (iterable == null) {
            return empty();
        }
        return ofCollection0(Collection.class.cast(iterable));
    }

    /**
     * 
     * @param <T>
     * @param iteration
     * @return 
     */
    public static <T> IterableAccessor<T> ofAll(final IterableAccessor<T> iteration) {
        if (iteration == null) {
            return empty();
        }
        return IterableAccessor.class.cast(iteration);
    }

    /**
     * 
     * @param <T>
     * @param value
     * @return 
     */
    static <T> IterableAccessor<T> ofAll(final Value<T> value) {
        if (value == null || value.isEmpty()) {
            return empty();
        }
        if (value instanceof Traversable trav) {
            return ofTraversable0(trav);
        }
        return ofValue0(Value.class.cast(value));
    }

    /**
     * 
     * @param <T>
     * @param traversable
     * @return 
     */
    static <T> IterableAccessor<T> ofAll(final Traversable<T> traversable) {
        if (traversable == null || traversable.isEmpty()) {
            return empty();
        }
        return ofTraversable0(Traversable.class.cast(traversable));
    }

    /**
     * 
     * @param <T>
     * @param value
     * @return 
     */
    protected final static <T> IterableAccessor<T> ofValue0(final Value<T> value) {
        return new SeqAccImplValue<T>() {
            @Override
            protected Value<T> delegated() {
                return value;
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param value
     * @return 
     */
    protected final static <T> IterableAccessor<T> ofTraversable0(final Traversable<T> value) {
        return new SeqAccImplTraversable<T>() {
            @Override
            protected Traversable<T> delegated() {
                return value;
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param collection
     * @return 
     */
    protected final static <T> IterableAccessor<T> ofCollection0(final Collection<T> collection) {
        return new SeqAccImplCollection<T>() {
            @Override
            protected Collection<T> delegated() {
                return collection;
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param elements
     * @return 
     */
    public static <T> IterableAccessor<T> of(final T... elements) {
        if (elements == null || elements.length == 0) {
            return empty();
        }
        final List<T> list = new AbstractList<T>() {
            @Override
            public T get(final int index) {
                return elements[index];
            }
            @Override
            public int size() {
                return elements.length;
            }
        };
        return ofCollection0(list);
    }
    
    /**
     * 
     * @param <T>
     * @param element
     * @return 
     */
    public static <T> IterableAccessor<T> ofOption(final Option<T> element) {
        Objects.requireNonNull(element, "Element");
        return element.isDefined() ? of(element.get()) : empty();
    }

    /**
     * 
     * @param <T>
     * @param element
     * @return 
     */
    public static <T> IterableAccessor<T> ofOption(final Optional<T> element) {
        Objects.requireNonNull(element, "Element");
        return element.isPresent() ? of(element.get()) : empty();
    }

    /**
     * 
     * @param consumer
     * @return 
     */
    @Override
    public final IterableAccessor<E> accept(
            final Consumer<? super E> consumer) {
        if (consumer == null) {
            return this;
        }
        final Iterable<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return new ConsumerIterator<>(self.iterator(), consumer);
            }
        };
    }

    /**
     * 
     * @param element
     * @return 
     */
    @Override
    public final IterableAccessor<E> append(final E element) {
        return append0(of(element));
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final IterableAccessor<E> append(final E... elements) {
        if (elements == null || elements.length == 0) {
            return this;
        }
        return append0(of(elements));
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final IterableAccessor<E> append(final Iterable<E> elements) {
        if (elements == null) {
            return this;
        }
        return append0(ofAll(elements));
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final IterableAccessor<E> append(final Iterator<E> elements) {
        if (elements == null) {
            return this;
        } 
        return append0(ofAll(elements));
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final IterableAccessor<E> append(final io.vavr.collection.Iterator<E> elements) {
        if (elements == null) {
            return this;
        } 
        return append0(ofAll(elements));
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final IterableAccessor<E> append(final Enumeration<E> elements) {
        if (elements == null) {
            return this;
        }
        return append0(ofAll(elements));
    }

    /**
     * 
     * @param other
     * @return 
     */
    protected final IterableAccessor<E> append0(final Iterable<E> other) {
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return IteratorUtils.chainedIterator(self.iterator(), other.iterator());
            }
        };
    }

    /**
     * 
     * @param offset
     * @param max
     * @return 
     */
    @Override
    public final IterableAccessor<E> bounded(final long offset, final long max) {
        checkArgument(offset >= 0, "Offset must not be negative");
        checkArgument(max >= 0, "Max must not be negative");
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return IteratorUtils.boundedIterator(self.iterator(), offset, max);
            }
        };
    }

    /**
     * 
     * @param other
     * @return 
     */
    @Override
    public final IterableAccessor<E> collated(final Iterator<? extends E> other) {
        return collated(null, other);
    }

    /**
     * 
     * @param other
     * @return 
     */
    @Override
    public final IterableAccessor<E> collated(final Iterable<? extends E> other) {
        return collated(null, other);
    }

    /**
     * 
     * @param comparator
     * @param other
     * @return 
     */
    @Override
    public final IterableAccessor<E> collated(final Comparator<? super E> comparator, final Iterator<? extends E> other) {
        if (other == null) {
            return this;
        }
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return IteratorUtils.collatedIterator(comparator, self.iterator(), other);
            }
        };
    }
    
    /**
     * 
     * @param comparator
     * @param other
     * @return 
     */
    @Override
    public final IterableAccessor<E> collated(final Comparator<? super E> comparator, final Iterable<? extends E> other) {
        if (other == null) {
            return this;
        }
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return IteratorUtils.collatedIterator(comparator, self.iterator(), other.iterator());
            }
        };
    }
    
    /**
     * 
     * @param <C>
     * @param collection
     * @return 
     */
    @Override
    public final <C extends Collection<? super E>> C collect(final C collection) {
        Objects.requireNonNull(collection, "Collection");
        for (final E item : this) {
            collection.add(item);
        }
        return collection;
    }

    /**
     * 
     * @param <C>
     * @param supplier
     * @return 
     */
    @Override
    public final <C extends Collection<? super E>> C collect(final Supplier<C> supplier) {
        Objects.requireNonNull(supplier, "Supplier");
        return collect(supplier.get());
    }

    /**
     * 
     * @param <R>
     * @param <A>
     * @param collector
     * @return 
     */
    @Override
    public final <R, A> R collect(final Collector<? super E, A, R> collector) {
        final A a = collector.supplier().get();
        final BiConsumer<A, ? super E> accumulator = collector.accumulator();
        for (final E e : this) {
            accumulator.accept(a, e);
        }
        return collector.finisher().apply(a);
    }

    /**
     * 
     * @param element
     * @return 
     */
    @Override
    public abstract boolean contains(final E element);

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final boolean containsAll(final Iterable<E> elements) {
        boolean isEmpty = true;
        for (final E e : elements) {
            if (isEmpty) isEmpty = false;
            if (!contains(e)) {
                return false;
            }
        }
        /* if (isEmpty) {
            throw new IllegalArgumentException("Elements is empty");
        } */
        return !isEmpty;
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final boolean containsAll(final E... elements) {
        return containsAll(SeqAccImpl.of(elements));
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final boolean containsAny(final Iterable<E> elements) {
        // boolean isEmpty = true;
        for (final E e : elements) {
            // isEmpty = false;
            if (contains(e)) {
                return true;
            }
        }
        /* if (isEmpty) {
            throw new IllegalArgumentException("Elements is empty");
        } */
        return false;
    }

    /**
     * 
     * @param elements
     * @return 
     */
    @Override
    public final boolean containsAny(final E... elements) {
        return containsAny(SeqAccImpl.of(elements));
    }

    /**
     * 
     * @param <E>
     * @param itr
     * @param element
     * @return 
     */
    protected static <E> boolean contains0(final E element, final Iterable<E> itr) {
        for (final E item : itr) {
            if (Objects.equals(item, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @param <E>
     * @param itr
     * @param element
     * @return 
     */
    protected static <E> boolean contains0(final E element, final E[] elements) {
        for (final E item : elements) {
            if (Objects.equals(item, element)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<E> distinct() {
        return filter(Predicates.distinct());
    }

    /**
     * 
     * @param comp
     * @return 
     */
    @Override
    public final IterableAccessor<E> distinct(final Comparator<E> comp) {
        if (comp == null) {
            return distinct();
        }
        return filter(Predicates.distinct(comp));
    }

    /**
     * 
     * @param <R>
     * @param func
     * @return 
     */
    @Override
    public final <R> IterableAccessor<E> distinct(final Function<E, R> func) {
        return filter(Predicates.distinct(func));
    }

    /**
     * 
     * @param eq
     * @return 
     */
    @Override
    public final IterableAccessor<E> distinct(final Equator<E> eq) {
        if (eq == null) {
            return distinct();
        }
        return filter(Predicates.distinct(eq));
    }

    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<E> eval() {
        return ofAll(io.vavr.collection.List.ofAll(this));
    }

    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<E> cycling() {
        // final Iterable<E> itr = Iterables.cycle(this);
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return Iterators.cycle(self);
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param mapper
     * @return 
     */
    @Override
    public <T> IterableAccessor<T> map(final Function<? super E, ? extends T> mapper) {
        Objects.requireNonNull(mapper, "Mapper");
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<T>() {
            @Override
            public Iterator<T> iterator() {
                final Iterator<E> itr = self.iterator();
                return new AbstractIterator<T>() {
                    @Override
                    protected T computeNext() {
                        if (itr.hasNext()) {
                            return mapper.apply(itr.next());
                        }
                        return endOfData();
                    }
                };
            }
        }; 
        /*
        final Transformer<? super E, ? extends T> trans = mapper::apply;
        return new IteratorIteration<T>() {
            @Override
            public Iterator<T> iterator() {
                return IteratorUtils.transformedIterator(self.iterator(), trans);
            }
        }; */
    }

    /**
     * 
     * @param filter
     * @return 
     */
    @Override
    public abstract Option<E> findFirst(final Predicate<? super E> filter);

    /**
     * 
     * @param filter
     * @return 
     */
    @Override
    public final E findFirstOrNull(final Predicate<? super E> filter) {
        return findFirst(filter).getOrNull();
    }

    /**
     * 
     * @param filter
     * @param defValue
     * @return 
     */
    @Override
    public final E findFirstOrElse(final Predicate<? super E> filter, final E defValue) {
        return findFirst(filter).getOrElse(defValue);
    }

    /**
     * 
     * @param filter
     * @param defValue
     * @return 
     */
    @Override
    public final E findFirstOrElse(final Predicate<? super E> filter, final Supplier<E> defValue) {
        return findFirst(filter).getOrElse(defValue);
    }

    /**
     * 
     */
    protected final static Supplier<NoSuchElementException> FIND_FIRST_OR_THROW_EXCEPTION_SUPPLIER = () -> new NoSuchElementException("findFirst()");
    
    /**
     * 
     * @param filter
     * @return 
     */
    @Override
    public final E findFirstOrFail(final Predicate<? super E> filter) {
        return findFirst(filter).getOrElseThrow(FIND_FIRST_OR_THROW_EXCEPTION_SUPPLIER);
    }

    /**
     * 
     * @param filter
     * @return 
     */
    @Override
    public abstract Option<E> findLast(final Predicate<? super E> filter);

    /**
     * 
     * @param filter
     * @return 
     */
    @Override
    public final E findLastOrNull(final Predicate<? super E> filter) {
        return findLast(filter).getOrNull();
    }

    /**
     * 
     * @param filter
     * @param defValue
     * @return 
     */
    @Override
    public final E findLastOrElse(final Predicate<? super E> filter, final E defValue) {
        return findLast(filter).getOrElse(defValue);
    }

    /**
     * 
     * @param filter
     * @param defValue
     * @return 
     */
    @Override
    public final E findLastOrElse(final Predicate<? super E> filter, final Supplier<E> defValue) {
        return findLast(filter).getOrElse(defValue);
    }

    /**
     * 
     */
    protected final static Supplier<NoSuchElementException> FIND_LAST_OR_THROW_EXCEPTION_SUPPLIER = () -> new NoSuchElementException("findLast()");

    /**
     * 
     * @param filter
     * @return 
     */
    @Override
    public final E findLastOrFail(final Predicate<? super E> filter) {
        return findLast(filter).getOrElseThrow(FIND_LAST_OR_THROW_EXCEPTION_SUPPLIER);
    }

    /**
     * 
     * @param <E>
     * @param iterable
     * @param filter
     * @return 
     */
    protected final static <E> Option<E> findFirst0(final Iterable<E> iterable, final Predicate<? super E> filter) {
        return SeqAccImpl.ofAll(iterable).filter(filter).first();
    }

    /**
     * 
     * @param <E>
     * @param iterable
     * @param filter
     * @return 
     */
    protected final static <E> Option<E> findLast0(final Iterable<E> iterable, final Predicate<? super E> filter) {
        return SeqAccImpl.ofAll(iterable).filter(filter).last();
    }

    /**
     * 
     * @return 
     */
    @Override
    public abstract Option<E> first();
    
    /**
     * 
     * @return 
     */
    @Override
    public final E firstOrNull() {
        return first().getOrNull();
    }

    /**
     * 
     * @param defValue
     * @return 
     */
    @Override
    public final E firstOrElse(final E defValue) {
        return first().getOrElse(defValue);
    }

    /**
     * 
     * @param defValue
     * @return 
     */
    @Override
    public final E firstOrElse(final Supplier<E> defValue) {
        return first().getOrElse(defValue);
    }

    /**
     * 
     */
    protected final static Supplier<NoSuchElementException> FIRST_OR_THROW_EXCEPTION_SUPPLIER = () -> new NoSuchElementException("first()");

    /**
     * 
     * @return 
     */
    @Override
    public E firstOrFail() {
        return first().getOrElseThrow(FIRST_OR_THROW_EXCEPTION_SUPPLIER);
    }
    
    /**
     * 
     * @param <R>
     * @param flattener
     * @return 
     */
    @Override
    public <R> IterableAccessor<R> flatMap(
            final Function<? super E, ? extends Iterable<? extends R>> flattener) {
        Objects.requireNonNull(flattener, "Flattener");
        final Iterable<E> self = this;
        final Iterable<R> iterable = () ->
            new AbstractIterator<R>() {
                final Iterator<E> outerItr = self.iterator();
                Iterator<R> innerItr = null;
                @Override
                protected R computeNext() {
                    do {
                        if (innerItr == null) {
                            if (outerItr.hasNext()) {
                                final E outerValue = outerItr.next();
                                innerItr = (Iterator<R>)flattener.apply(outerValue).iterator();
                                Objects.requireNonNull(innerItr, "Flattener returned a null Iterable");
                            } else {
                                return endOfData();
                            }
                        }
                        if (innerItr.hasNext()) {
                            return innerItr.next();
                        } else {
                            innerItr = null;
                        }
                    } while (true);
                }
            };
        return new SeqAccImplIterator<R>() {
            @Override
            public Iterator<R> iterator() {
                return iterable.iterator();
            }
        };
    }

    /**
     * 
     */
    @Override
    public final void forEach() {
        for (final E item : this) {
        }
    }

    /**
     * 
     * @param action 
     */
    @Override
    public final void forEach(final Consumer<? super E> action) {
        Objects.requireNonNull(action, "Action");
        for (final E item : this) {
            action.accept(item);
        }
    }
    
    /**
     * 
     * @param action 
     */
    @Override
    public final void forEachWithIndex(final ObjIntConsumer<? super E> action) {
        Objects.requireNonNull(action, "Action");
        int index = 0;
        for (final E t : this) {
            action.accept(t, index++);
        }
    }    
    
    @Override
    public final Interruptible<E> interruptible() {
        final Iterable<E> self = this;
        return new Interruptible<E>() {
            @Override
            public void forEach() throws InterruptedException {
                for (final E item : self) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                }
            }

            @Override
            public void forEach(final Consumer<? super E> action) throws InterruptedException {
                Objects.requireNonNull(action, "Action");
                for (final E item : self) {
                    action.accept(item);
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                }
            }

            @Override
            public void forEachWithIndex(final ObjIntConsumer<? super E> action) throws InterruptedException {
                Objects.requireNonNull(action, "Action");
                int index = 0;
                for (final E t : self) {
                    action.accept(t, index++);
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                }
            }

            @Override
            public <T extends Throwable> void forEachFailable(final FailableConsumer<? super E, T> action) throws T, InterruptedException {
                Objects.requireNonNull(action, "Action");
                for (final E item : self) {
                    action.accept(item);
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    }
                }
            }
        };
    };

    /**
     * 
     * @param <T>
     * @param action 
     * @throws T 
     */
    @Override
    public final <T extends Throwable> void forEachFailable(final FailableConsumer<? super E, T> action) throws T {
        Objects.requireNonNull(action, "Action");
        for (final E item : this) {
            action.accept(item);
        }
    }

    /**
     * 
     * @return 
     */
    @Override
    public abstract Option<E> last();

    /**
     * 
     * @return 
     */
    @Override
    public final E lastOrNull() {
        return last().getOrNull();
    }

    /**
     * 
     * @param defValue
     * @return 
     */
    @Override
    public final E lastOrElse(final E defValue) {
        return last().getOrElse(defValue);
    }

    /**
     * 
     * @param defValue
     * @return 
     */
    @Override
    public final E lastOrElse(final Supplier<E> defValue) {
        return last().getOrElse(defValue);
    }

    /**
     * 
     */
    protected final static Supplier<NoSuchElementException> LAST_OR_THROW_EXCEPTION_SUPPLIER = () -> new NoSuchElementException("last()");

    /**
     * 
     * @return 
     */
    @Override
    public E lastOrFail() {
        return last().getOrElseThrow(LAST_OR_THROW_EXCEPTION_SUPPLIER);
    }

    /**
     * 
     * @return 
     */
    @Override
    public abstract boolean isEmpty();
    
    /**
     * 
     * @param filter
     * @return 
     */
    @Override
    public IterableAccessor<E> filter(final Predicate<? super E> filter) {
        Objects.requireNonNull(filter, "Filter");
        final IterableAccessor<E> self = this;
        final org.apache.commons.collections4.Predicate<? super E> p = filter::test;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return IteratorUtils.filteredIterator(self.iterator(), p);
            }
        };
    }

    /**
     * 
     * @return 
     */
    @Override
    public final PeekingIterator<E> peekingIterator() {
        return Iterators.peekingIterator(iterator());
    }
    
    /**
     * 
     * @param element
     * @return 
     */
    @Override
    public final IterableAccessor<E> intersperse(final E element) {
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return io.vavr.collection.Iterator.ofAll(self.iterator()).intersperse(element);
            }
        };
    }

    /**
     * 
     * @return 
     */
    @Override
    public final String mkString() {
        return io.vavr.collection.Iterator.ofAll(this).mkString();
    }
    
    /**
     * 
     * @param delimiter
     * @return 
     */
    @Override
    public final String mkString(final CharSequence delimiter) {
        return io.vavr.collection.Iterator.ofAll(this).mkString(delimiter);
    }

    /**
     * 
     * @param prefix
     * @param delimiter
     * @param suffix
     * @return 
     */
    @Override
    public final String mkString(final CharSequence prefix, final CharSequence delimiter, final CharSequence suffix) {
        return io.vavr.collection.Iterator.ofAll(this).mkString(prefix, delimiter, suffix);
    }

    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<E> singleUse() {
        final IterableAccessor<E> self = this;
        return new SeqAccImplSingleUseIterator<E>() {
            @Override
            protected Iterator<E> delegated() {
                return self.iterator();
            }
        };
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<E> multipleUse() {
        final Iterable<E> iterable = IteratorUtils.asMultipleUseIterable(iterator());
        return new SeqAccImplIterable<E>() {
            @Override
            protected Iterable<E> delegated() {
                return iterable;
            }
        };
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public abstract Option<E> get(final int index);
    
    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public abstract E getOrNull(final int index);

    /**
     * 
     * @param index
     * @param def
     * @return 
     */
    @Override
    public abstract E getOrElse(final int index, final E def);

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public abstract E getOrFail(final int index);

    /**
     * 
     * @param <T>
     * @param itr
     * @return 
     */
    protected static <T> T get0(final Iterable<T> itr) {
        if (itr == null) {
            throw new NoSuchElementException();
        }
        if (itr instanceof Option<T> opt) {
            return opt.get();
        }
        final Iterator<T> i = itr.iterator();
        if (i.hasNext()) {
            final T value = i.next();
            if (i.hasNext()) {
                throw new IllegalArgumentException("Multiple elements found");
            }
            return value;
        } 
        throw new NoSuchElementException();
    }

    /**
     * 
     * @param <T>
     * @param elements
     * @return 
     */
    protected static <T> T get0(final T[] elements) {
        if (elements == null || elements.length == 0) {
            throw new NoSuchElementException();
        }
        if (elements.length == 1) {
            return elements[0];
        }
        throw new IllegalArgumentException("Multiple elements found");
    }

    /**
     * 
     * @param <T>
     * @param itr
     * @return 
     */
    protected static <T> Option<T> toOption0(final Iterable<T> itr) {
        if (itr == null) {
            return None();
        }
        if (itr instanceof Option) {
            return Option.class.cast(itr);
        }
        final Iterator<T> i = itr.iterator();
        if (i.hasNext()) {
            final T value = i.next();
            if (i.hasNext()) {
                throw new IllegalArgumentException("Multiple elements found");
            }
            return Some(value);
        } 
        return None();
    }

    /**
     * 
     * @param <T>
     * @param elements
     * @return 
     */
    protected static <T> Option<T> toOption0(final T[] elements) {
        if (elements == null || elements.length == 0) {
            return None();
        }
        if (elements.length == 1) {
            return Some(elements[0]);
        }
        throw new IllegalArgumentException("Multiple elements found");
    }

    /**
     * 
     * @param filter
     * @return 
     */
    @Override
    public IterableAccessor<E> omit(final Predicate<? super E> filter) {
        Objects.requireNonNull(filter, "Filter");
        return filter(filter.negate());
    }

    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<E> remove() {
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return Iterators.consumingIterator(self.iterator());
            }
        };
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<E> reversed() {
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return org.apache.commons.collections4.IterableUtils.reversedIterable(self).iterator();
            }
        };
    }

    public abstract int size();
    
    /**
     * 
     * @return 
     */
    public final IterableAccessor<E> sorted() {
        return sorted(null, true);
    }

    /**
     * 
     * @param comparator
     * @return 
     */
    @Override
    public final IterableAccessor<E> sorted(final Comparator<? super E> comparator) {
        return sorted(comparator, true);
    }

    /**
     * 
     * @param ascending
     * @param comparator
     * @return 
     */
    @Override
    public final IterableAccessor<E> sorted(final Comparator<? super E> comparator,
            final boolean ascending) {
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                final Bag<E> bag = new TreeBag(ascending ? comparator : Collections.reverseOrder(comparator));
                for (final E e : self) {
                    bag.add(e);
                }
                return IteratorUtils.unmodifiableIterator(bag.iterator());
            };
        };
    }

    /**
     * 
     * @return 
     */
    @Override
    public abstract Stream<E> stream();

    /**
     * 
     * @return 
     */
    @Override
    public abstract Stream<E> parallelStream();
    
    /**
     * 
     * @param processor
     * @return 
     */
    @Override
    public final IterableAccessor<E> process(final Processor<? super E, Boolean> processor) {
        if (processor == null) {
            return this;
        }
        final Iterable self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return new ProcessIterator(self.iterator(), processor);
            }
        };
    }

    /**
     * 
     * @param max
     * @return 
     */
    @Override
    public final IterableAccessor<E> top(final long max) {
        if (max < 0) {
            return this;
        }
        return bounded(0, max);
    }

    /**
     * 
     * @param <T>
     * @param function
     * @return 
     */
    // public abstract <T, F extends Function<Iterable<E>, T>> T to(final F function);
    @Override
    public abstract <T> T to(final Function<? super Iterable<E>, T> function);
    
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
    @Override
    public final <T1, T2, T> T to(
            final Function<? super E, T1> mapper1,
            final Function<? super E, T2> mapper2,
            final Function<? super Iterable<Tuple2<T1, T2>>, T> function) {
        Objects.requireNonNull(mapper1, "Mapper 1");
        Objects.requireNonNull(mapper2, "Mapper 2");
        return map(t -> Tuple(mapper1.apply(t), mapper2.apply(t))).to(function);
    }

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
    @Override
    public final <T1, T2, T3, T> T to(
            final Function<? super E, T1> mapper1,
            final Function<? super E, T2> mapper2,
            final Function<? super E, T3> mapper3,
            final Function<? super Iterable<Tuple3<T1, T2, T3>>, T> function) {
        Objects.requireNonNull(mapper1, "Mapper 1");
        Objects.requireNonNull(mapper2, "Mapper 2");
        Objects.requireNonNull(mapper3, "Mapper 3");
        return map(t -> Tuple(mapper1.apply(t), mapper2.apply(t), mapper3.apply(t))).to(function);
    }

    /**
     * 
     * @param <T>
     * @param function
     * @return 
     */
    @Override
    public final <T> T evalTo(final Function<? super Iterable<E>, T> function) {
        return function.apply(eval());
    }

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
    @Override
    public final <T1, T2, T> T evalTo(
            final Function<? super E, T1> mapper1,
            final Function<? super E, T2> mapper2,
            final Function<? super Iterable<Tuple2<T1, T2>>, T> function) {
        Objects.requireNonNull(mapper1, "Mapper 1");
        Objects.requireNonNull(mapper2, "Mapper 2");
        return map(t -> Tuple(mapper1.apply(t), mapper2.apply(t))).evalTo(function);
    }

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
    @Override
    public final <T1, T2, T3, T> T evalTo(
            final Function<? super E, T1> mapper1,
            final Function<? super E, T2> mapper2,
            final Function<? super E, T3> mapper3,
            final Function<? super Iterable<Tuple3<T1, T2, T3>>, T> function) {
        Objects.requireNonNull(mapper1, "Mapper 1");
        Objects.requireNonNull(mapper2, "Mapper 2");
        Objects.requireNonNull(mapper3, "Mapper 3");
        return map(t -> Tuple(mapper1.apply(t), mapper2.apply(t), mapper3.apply(t))).evalTo(function);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    @Override
    public abstract ListAccessor<E> toAccessor();

    /**
     * 
     * @return 
     */ /*
    public final HashSet<E> toHashSet() {
        return collect(new HashSet<>());
    } */

    /**
     * 
     * @return 
     */ /*
    public final TreeSet<E> toTreeSet() {
        return collect(new TreeSet<>());
    } */
    
    /**
     * 
     * @param comparator
     * @return 
     */ /*
    public final TreeSet<E> toTreeSet(final Comparator<? super E> comparator) {
        return collect(new TreeSet<>(comparator));
    } */

    /**
     * 
     * @return 
     */
    /* public abstract com.google.common.collect.ImmutableList<E> toImmutableList(); */
    
    /**
     * 
     * @return 
     */  /*
    public final io.vavr.collection.Seq<E> toImmutableList() {
        return toVector();
    } */

    /**
     * 
     * @param <T>
     * @param check
     * @param iterable
     * @return 
     */
    protected <T extends Iterable<E>> T checked(final Predicate<? super E> check, 
            final T iterable) {
        Objects.requireNonNull(check, "Predicate");
        Objects.requireNonNull(iterable, "Iterable");
        for (final E item : iterable) {
            if (check.test(item)) {
                throw new IllegalArgumentException("Invalid item: " + item);
            }
        }
        return iterable;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final Iterable<E> toIterable() {
        return this;
    }

    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<E> unmodifiable() {
        final IterableAccessor<E> self = this;
        return new SeqAccImplIterator<E>() {
            @Override
            public Iterator<E> iterator() {
                return IteratorUtils.unmodifiableIterator(self.iterator());
            }
        };
    }
    
    /**
     * 
     * @return 
     */
    @Override
    abstract public Spliterator<E> spliterator();
    
    /**
     * 
     */
    protected final static String COULD_NOT_BE_ITERATED_AGAIN = "Could not be iterated again";

    /**
     * 
     * @param <E>
     * @param iteration
     * @return 
     */
    protected final static <E> Object[] toArray0(final IterableAccessor<E> iteration) {
        final List<E> list = iteration.to(Java.arrayList());
        final Object[] array = list.toArray();
        list.clear();
        return array;
    }
    
    /**
     * 
     * @param <E>
     * @param iterable
     * @return 
     */
    protected final static <E> Option<E> first0(final Iterable<E> iterable) {
        if (iterable instanceof List) {
            final List<E> list = List.class.cast(iterable);
            if (list.isEmpty()) {
                return None();
            }
            return Some(list.get(0));
        }
        final Iterator<E> itr = iterable.iterator();
        if (!itr.hasNext()) {
            return None();
        }
        return Some(itr.next());
    }

    /**
     * 
     * @param <E>
     * @param iterable
     * @return 
     */
    protected final static <E> Option<E> last0(final Iterable<E> iterable) {
        if (iterable instanceof java.util.List<E> list) {
            if (list.isEmpty()) {
                return None();
            }
            return Some(list.get(list.size() - 1));
        }
        for (final Iterator<E> itr = iterable.iterator(); itr.hasNext(); ) {
            final E item = itr.next();
            if (!itr.hasNext()) {
                return Some(item);
            }
        }
        return None();
    }
    
    // private final static Object[] EMPTY_ARRAY = ArrayUtils.EMPTY_OBJECT_ARRAY;
    
    /**
     * 
     */
    private final static IterableAccessor<?> EMPTY_ITERATION = new SeqAccImplEmpty();

}

