/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import static io.vavr.API.Tuple;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.collection.Ordered;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IteratorUtils;
import static org.apache.commons.collections4.IteratorUtils.asMultipleUseIterable;
import org.apache.commons.collections4.iterators.IteratorChain;
import org.joytools.commons.accessors.MultiValueAccessor;
import org.joytools.commons.accessors.SingleValueAccessor;
import org.joytools.commons.function.FailableIterator;
import org.joytools.commons.function.Failables;
import static org.joytools.commons.function.Functions.asString;
import org.joytools.commons.interop.Java;
import org.joytools.commons.interop.Vavr;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class IterableAccessors {

    /**
     * 
     * @param <T>
     * @return 
     */
    public final static <T> IterableAccessor<T> empty() {
        return SeqAccImpl.empty();
    }

    /**
     * 
     * @param <T>
     * @param element
     * @return 
     */
    public final static <T> IterableAccessor<T> of(final T element) {
        return SeqAccImpl.of(element);
    }

    /**
     * 
     * @param <T>
     * @param elements
     * @return 
     */
    public final static <T> IterableAccessor<T> of(final T... elements) {
        return SeqAccImpl.of(elements);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public final static <T> IterableAccessor<T> ofAll(final Iterator<T> iterator) {
        return SeqAccImpl.ofAll(iterator);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public final static <T> IterableAccessor<T> ofAll(final Enumeration<T> iterator) {
        return SeqAccImpl.ofAll(iterator);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public final static <T> IterableAccessor<T> ofAll(final io.vavr.collection.Iterator<T> iterator) {
        return SeqAccImpl.ofAll(iterator);
    }

    
    /**
     * 
     * @param <T>
     * @param iterable
     * @return 
     */
    public final static <T> IterableAccessor<T> ofAll(final Iterable<T> iterable) {
        return SeqAccImpl.ofAll(iterable);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public final static <T> IterableAccessor<T> iterableAgain(final Iterator<T> iterator) {
        return SeqAccImpl.ofAll(asMultipleUseIterable(iterator));
    }
    
    /**
     * 
     * @param <T>
     * @param enumeration
     * @return 
     */
    public final static <T> IterableAccessor<T> iterableAgain(final Enumeration<T> enumeration) {
        final Iterator<T> itr = SeqAccImpl.ofAll(enumeration).iterator();
        return iterableAgain(itr);
    }
    
    /**
     * 
     * @param <T>
     * @param traversable
     * @return 
     */
    public final static <T> IterableAccessor<T> iterableAgain(final Traversable<T> traversable) {
        if (traversable.isTraversableAgain()) {
            return ofAll(traversable);
        }
        final Iterator<T> iterator = traversable.iterator();
        return iterableAgain(iterator);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public final static <T> IterableAccessor<T> iterableAgain(final io.vavr.collection.Iterator<T> iterator) {
        return iterableAgain(Traversable.class.cast(iterator));
    }
    
    /**
     * 
     * @param <T>
     * @param iterables
     * @return 
     */
    public final static <T> IterableAccessor<T> concat(final Iterable<T>... iterables) {
        if (iterables == null || iterables.length == 0) {
            return empty();
        }
        return new SeqAccImplIterator<T>() {
            @Override
            public Iterator<T> iterator() {
                final Collection<Iterator<T>> iterators = of(iterables)
                        .map(iterable -> ofAll(iterable).iterator())
                        .to(Java.arrayList());
                return new IteratorChain(iterators);
            };
        };
    }
    
    /**
     * 
     * @param <T>
     * @param iterables
     * @return 
     */
    public final static <T> IterableAccessor<T> concat(final Iterable<? extends Iterable<T>> iterables) {
        if (iterables == null) {
            return empty();
        }
        return new SeqAccImplIterator<T>() {
            @Override
            public Iterator<T> iterator() {
                final Collection<Iterator<T>> iterators = ofAll(iterables)
                        .map(i -> ofAll(i).iterator())
                        .to(Java.arrayList());
                return new IteratorChain(iterators);
            };
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param k1
     * @param v1
     * @return 
     */
    
    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1) {
        return of(
            Tuple(k1, v1));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2),
            Tuple(k3, v3));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2),
            Tuple(k3, v3),
            Tuple(k4, v4));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2),
            Tuple(k3, v3),
            Tuple(k4, v4),
            Tuple(k5, v5));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2),
            Tuple(k3, v3),
            Tuple(k4, v4),
            Tuple(k5, v5),
            Tuple(k6, v6));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2),
            Tuple(k3, v3),
            Tuple(k4, v4),
            Tuple(k5, v5),
            Tuple(k6, v6),
            Tuple(k7, v7));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2),
            Tuple(k3, v3),
            Tuple(k4, v4),
            Tuple(k5, v5),
            Tuple(k6, v6),
            Tuple(k7, v7),
            Tuple(k8, v8));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8,
            final K k9, final V v9) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2),
            Tuple(k3, v3),
            Tuple(k4, v4),
            Tuple(k5, v5),
            Tuple(k6, v6),
            Tuple(k7, v7),
            Tuple(k8, v8),
            Tuple(k9, v9));
    }

    public static <K, V> IterableAccessor<Tuple2<K, V>> tuples(
            final K k1, final V v1,
            final K k2, final V v2,
            final K k3, final V v3,
            final K k4, final V v4,
            final K k5, final V v5,
            final K k6, final V v6,
            final K k7, final V v7,
            final K k8, final V v8,
            final K k9, final V v9,
            final K k10, final V v10) {
        return of(
            Tuple(k1, v1),
            Tuple(k2, v2),
            Tuple(k3, v3),
            Tuple(k4, v4),
            Tuple(k5, v5),
            Tuple(k6, v6),
            Tuple(k7, v7),
            Tuple(k8, v8),
            Tuple(k9, v9),
            Tuple(k10, v10));
    }
    
    /**
     * 
     * @param <T>
     * @param a
     * @param b
     * @param others
     * @return 
     */
    public static final <T> IterableAccessor<T> disjunction(
            final Iterable<T> a,
            final Iterable<T> b,
            final Iterable<T>... others) {
        final Seq<Iterable<T>> merged = mergeArgs(a, b, others);
        return setOp(CollectionUtils::disjunction, merged.asJava());
    }

    /**
     * 
     * @param <T>
     * @param a
     * @param b
     * @param others
     * @return 
     */
    public static final <T> IterableAccessor<T> disjunction(
            final T[] a,
            final T[] b,
            final T[]... others) {
        final List<Iterable<T>> params = new ArrayList();
        mergeArgs(a, b, others).forEach(x -> params.add(from(x)));
        return setOp(CollectionUtils::disjunction, params);
    }

    /**
     * 
     * @param <T>
     * @param iterables
     * @return 
     */
    public static final <T> IterableAccessor<T> intersection(final Iterable<T>... iterables) {
        return setOp(CollectionUtils::intersection, iterables);
    }

    /**
     * 
     * @param <T>
     * @param arrays
     * @return 
     */
    public static final <T> IterableAccessor<T> intersection(final T[]... arrays) {
        return setOp(CollectionUtils::intersection, arrays);
    }

    /**
     * 
     * @param <T>
     * @param iterables
     * @return 
     */
    public static final <T> IterableAccessor<T> subtraction(final Iterable<T>... iterables) {
        return setOp(CollectionUtils::subtract, iterables);
    }

    /**
     * 
     * @param <T>
     * @param arrays
     * @return 
     */
    public static final <T> IterableAccessor<T> subtraction(final T[]... arrays) {
        return setOp(CollectionUtils::subtract, arrays);
    }

    /**
     * 
     * @param <T>
     * @param iterables
     * @return 
     */
    public static final <T> IterableAccessor<T> union(final Iterable<T>... iterables) {
        return setOp(CollectionUtils::union, iterables);
    }

    /**
     * 
     * @param <T>
     * @param arrays
     * @return 
     */
    public static final <T> IterableAccessor<T> union(final T[]... arrays) {
        return setOp(CollectionUtils::union, arrays);
    }

    /**
     * 
     */
    private final static BiFunction<Object, Object, Tuple2<Object, Object>> TO_TUPLE = (x, y) -> Tuple(x, y);

    /**
     * 
     * @param <T>
     * @param <R>
     * @return 
     */
    protected final static <T, R> BiFunction<T, R, Tuple2<T, R>> toTuple() {
        return (BiFunction<T, R, Tuple2<T, R>>)(BiFunction)TO_TUPLE;
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static IterableAccessor<Object> find(final Object o) {
        return findOrSingleton(o);
    }

    /**
     * 
     * @param <T>
     * @param o
     * @param type
     * @return 
     */
    public static <T> IterableAccessor<T> find(final Object o, final Class<T> type) {
        return findOrSingleton(o, type);
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static IterableAccessor<Object> findOrFail(final Object o) {
        return findOrFail(o, Object.class);
    }

    /**
     * 
     * @param <T>
     * @param o
     * @param type
     * @return 
     */
    public static <T> IterableAccessor<T> findOrFail(final Object o, final Class<T> type) {
        final IterableAccessor<T> f = findOrNull(o, type);
        if (f == null) {
            throw new UnsupportedOperationException(o.getClass().getName() + " could not be converted to Iteration");
        }
        return f;
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static IterableAccessor<Object> findOrEmpty(final Object o) {
        final IterableAccessor<Object> f = findOrNull(o);
        return f == null ? empty() : f;
    }

    /**
     * 
     * @param <T>
     * @param o
     * @param type
     * @return 
     */
    public static <T> IterableAccessor<T> findOrEmpty(final Object o, final Class<T> type) {
        final IterableAccessor<T> f = findOrNull(o, type);
        return f == null ? empty() : f;
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static IterableAccessor<Object> findOrSingleton(final Object o) {
        final IterableAccessor<Object> f = findOrNull(o);
        return f != null ? f : of(o);
    }

    /**
     * 
     * @param <T>
     * @param o
     * @param type
     * @return 
     */
    public static <T> IterableAccessor<T> findOrSingleton(final Object o, final Class<T> type) {
        final IterableAccessor<T> f = findOrNull(o, type);
        return f != null ? f : (IterableAccessor<T>)of(o);
    }

    /**
     * 
     * @param o
     * @return 
     */
    public static IterableAccessor<Object> findOrNull(final Object o) {
        if (o == null) {
            return empty();
        }
        if (o instanceof CharSequence
                || o instanceof Number
                || o instanceof Boolean
                || o instanceof Enum
                || o instanceof Record
                || o instanceof TemporalAccessor
                || o instanceof java.util.Date
                || o instanceof java.util.Calendar
                || o instanceof java.util.Map.Entry) {
            return of(o);
        }
        if (o instanceof Iterable itr) {
            return ofAll(itr);
        }
        if (o instanceof MultiValueAccessor mva) {
            return ofAll(mva.values());
        }
        if (o instanceof SingleValueAccessor sva) {
            return of(sva.get());
        }
        if (o instanceof Object[]) {
            return of((Object[])o);
        }
        if (o instanceof Iterator itr) {
            return ofAll(itr);
        }
        if (o instanceof Enumeration e) {
            return SeqAccImpl.ofAll(e);
        }
        if (o instanceof Optional opt) {
            return SeqAccImpl.ofOption(opt);
        }
        if (o.getClass().getName().startsWith("io.vavr.Tuple")) {
            return of(o);
        }
        return null;
    }

    /**
     * 
     * @param <T>
     * @param o
     * @param type
     * @return 
     */
    public static <T> IterableAccessor<T> findOrNull(final Object o, final Class<T> type) {
        Objects.requireNonNull(type, "Type");
        return(IterableAccessor<T>)findOrNull(o);
    }

    /**
     * 
     */
    private final static Function1 TO_ITERATION = (x) -> findOrFail(x);
    
    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function1<V, IterableAccessor<Object>> toIteration() {
        return (Function1<V, IterableAccessor<Object>>)TO_ITERATION;
    }

    /**
     * 
     * @param <V>
     * @param <Z>
     * @param type
     * @return 
     */
    public static <V, Z> Function1<V, IterableAccessor<Z>> toIteration(final Class<Z> type) {
        Objects.requireNonNull(type, "type");
        return (Function1<V, IterableAccessor<Z>>)TO_ITERATION;
    }

    /**
     * 
     * @param <T>
     * @param option
     * @return 
     */ /*
    public static <T> Iteration<T> from(final Option<T> option) {
        return Iteration.ofOption(option);
    } */

    /**
     * 
     * @param <T>
     * @param optional
     * @return 
     */
    public static <T> IterableAccessor<T> from(final Optional<T> optional) {
        return SeqAccImpl.ofOption(optional);
    }

    /**
     * 
     * @param <T>
     * @param elements
     * @return 
     */
    public static <T> IterableAccessor<T> from(final T[] elements) {
        return SeqAccImpl.of(elements);
    }
    
    /**
     * Creates a new RichIterable from the provided elements.
     * <p>
     * The returned iterable's iterator does not support {@code remove()}.
     *
     * @param <T>  the element type
     * @param first
     * @param others  the elements to be contained in the RichIterable
     * @return a new RichIterable containing the provided elements
     */
    public static <T> IterableAccessor<T> from(final T first, final T... others) {
        if (others == null || others.length == 0) {
            return of(first);
        }
        return ofAll(ObjectUtils.varArgsToList(first, others));
    }
    
    /**
     * Construct a new RichIterable from the provided iterable. If the
 iterable is already an instance of RichIterable, the instance
 will be returned instead.
 <p>
     * The returned iterable's iterator supports {@code remove()} when the
     * corresponding input iterator supports it.
     *
     * @param <T>  the element type
     * @param iterable  the iterable to wrap into a RichIterable, may not be null
     * @return a new RichIterable wrapping the provided iterable
     * @throws NullPointerException if iterable is null
     */
    public static <T> IterableAccessor<T> from(final Iterable<T> iterable) {
        return ofAll(iterable);
    }

    /**
     * 
     * @param <T>
     * @param iteration
     * @return 
     */
    public static <T> IterableAccessor<T> from(final IterableAccessor<T> iteration) {
        return ofAll(iteration);
    }

    /**
     * 
     * @param <T>
     * @param traversable
     * @return 
     */
    public static <T> IterableAccessor<T> from(final Traversable<T> traversable) {
        return ofAll(traversable);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public static <T> IterableAccessor<T> from(final Iterator<T> iterator) {
        return ofAll(iterator);
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public static <T> IterableAccessor<T> from(final FailableIterator<T, ? extends Throwable> iterator) {
        return ofAll(Failables.wrapped.asIterator(iterator));
    }

    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public static <T> IterableAccessor<T> from(final io.vavr.collection.Iterator<T> iterator) {
        return ofAll(iterator);
    }

    /**
     * 
     * @param <T>
     * @param enumeration
     * @return 
     */
    public static <T> IterableAccessor<T> from(final Enumeration<T> enumeration) {
        return SeqAccImpl.ofAll(enumeration);
    }

    /**
     * 
     * @param elements
     * @param ignoreCase
     * @return 
     */
    public static IterableAccessor<String> sorted(final Iterable<String> elements, final boolean ignoreCase) {
        if (elements == null) {
            return IterableAccessors.empty();
        }
        final Comparator<String> comp = CaseSensitivity.forIgnoreCase(ignoreCase).comparator();
        if (elements instanceof Ordered) {
            final Ordered ordered = Ordered.class.cast(elements);
            if (comp == ordered.comparator()) {
                return from(elements);
            }
        }
        if (elements instanceof java.util.SortedSet) {
            final java.util.SortedSet sortedSet = java.util.SortedSet.class.cast(elements);
            if (comp == sortedSet.comparator()) {
                return from(elements);
            }
        }
        return ofAll(io.vavr.collection.TreeSet.ofAll(comp, elements));
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public static IterableAccessor<String> sorted(final Iterable<String> elements) {
        return sorted(elements, false);
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public static IterableAccessor<String> sortedIgnoreCase(final Iterable<String> elements) {
        return sorted(elements, true);
    }

    /**
     * 
     * @param ignoreCase
     * @param elements
     * @return 
     */
    public static IterableAccessor<String> sorted(final boolean ignoreCase, 
            final CharSequence... elements) {
        return sorted(from(elements).map(asString()), ignoreCase);
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public static IterableAccessor<String> sorted(final CharSequence... elements) {
        return sorted(from(elements).map(asString()));
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public static IterableAccessor<String> sortedIgnoreCase(final CharSequence... elements) {
        return sorted(from(elements).map(asString()), true);
    }

    /**
     * 
     * @param <T>
     * @param iterables
     * @return 
     */
    public final static <T> IterableAccessor<T> zip(final Iterable<T>... iterables) {
        if (iterables == null || iterables.length == 0) {
            return empty();
        }
        return new SeqAccImplIterator<T>() {
            @Override
            public Iterator<T> iterator() {
                final Iterator<T> itrArray[] = of(iterables)
                        .map(iterable -> ofAll(iterable).iterator())
                        .to(Java.array(Iterator[]::new));
                return IteratorUtils.zippingIterator(itrArray);
            };
        };
    }
    
    /**
     * 
     * @param <T>
     * @param iterables
     * @return 
     */
    public final static <T> IterableAccessor<T> zip(final Iterable<? extends Iterable<T>> iterables) {
        if (iterables == null) {
            return empty();
        }
        return new SeqAccImplIterator<T>() {
            @Override
            public Iterator<T> iterator() {
                final Iterator<T> itrArray[] = ofAll(iterables)
                        .map(iterable -> ofAll(iterable).iterator())
                        .to(Java.array(Iterator[]::new));
                return IteratorUtils.zippingIterator(itrArray);
            };
        };
    }
    
    /**
     * 
     * @param <T>
     * @param op
     * @param iterables
     * @return 
     */
    static final <T> IterableAccessor<T> setOp(
            final BiFunction<Iterable<T>, Iterable<T>, Iterable<T>> op, 
            final List<Iterable<T>> iterables) {
        final int size = size(iterables);
        switch (size) {
            case 0:
                return empty();
            case 1:
                return ofAll(iterables.get(0));
            default:
                Iterable<T> result = op.apply(
                        nonNullIterable(iterables.get(0)), 
                        nonNullIterable(iterables.get(1)));
                for (int i = 2; i < size; i++) {
                    result = op.apply(
                            result, 
                            nonNullIterable(iterables.get(i)));
                }
                return ofAll(result);
        }
    }
    
    /**
     * 
     * @param <T>
     * @param op
     * @param iterables
     * @return 
     */
    static final <T> IterableAccessor<T> setOp(
            final BiFunction<Iterable<T>, Iterable<T>, Iterable<T>> op, 
            final Iterable<T>... iterables) {
        final int size = size(iterables);
        switch (size) {
            case 0:
                return empty();
            case 1:
                return ofAll(iterables[0]);
            default:
                Iterable<T> result = op.apply(
                        nonNullIterable(iterables[0]), 
                        nonNullIterable(iterables[1]));
                for (int i = 2; i < size; i++) {
                    result = op.apply(
                            result, 
                            nonNullIterable(iterables[i]));
                }
                return ofAll(result);
        }
    }

    /**
     * 
     * @param <T>
     * @param op
     * @param arrays
     * @return 
     */
    static final <T> IterableAccessor<T> setOp(
            final BiFunction<Iterable<T>, Iterable<T>, Iterable<T>> op, 
            final T[]... arrays) {
        final int size = size(arrays);
        switch (size) {
            case 0:
                return empty();
            case 1:
                return of(arrays[0]);
            default:
                Iterable<T> result = op.apply(
                        nonNullIterable(arrays[0]), 
                        nonNullIterable(arrays[1]));
                for (int i = 2; i < size; i++) {
                    result = op.apply(
                            result, 
                            nonNullIterable(arrays[i]));
                }
                return ofAll(result);
        }
    }

    /**
     * 
     * @param <T>
     * @param a
     * @param b
     * @param others
     * @return 
     */
    static <T> io.vavr.collection.Seq<T> mergeArgs(final T a, final T b, final T... others) {
        return of(a).append(b).append(others).to(Vavr.list());
    }

    /**
     * 
     * @param <T>
     * @param array
     * @return 
     */
    static <T> int size(final T[] array) {
        return array == null ? 0 : array.length;
    }
    
    /**
     * 
     * @param <T>
     * @param itr
     * @return 
     */
    static <T> int size(final Iterable<T> itr) {
        if (itr == null) {
            return 0;
        }
        if (itr instanceof Collection coll) {
            return coll.size();
        }
        if (itr instanceof Traversable trav) {
            return trav.size();
        }
        return -1;
    }
    
    static <T> Iterator<T> nonNullIterator(final Iterable<T> itr) {
        if (itr == null) {
            return IteratorUtils.EMPTY_ITERATOR;
        }
        return itr.iterator();
    }
    
    static <T> Iterator<T> nonNullIterator(final T[] array) {
        if (array == null || array.length == 0) {
            return IteratorUtils.EMPTY_ITERATOR;
        }
        return IteratorUtils.arrayIterator(array);
    }

    static <T> Iterable<T> nonNullIterable(final Iterable<T> itr) {
        if (itr == null) {
            return empty();
        }
        return itr;
    }
    
    static <T> Iterable<T> nonNullIterable(final T[] array) {
        if (array == null || array.length == 0) {
            return empty();
        }
        return from(IteratorUtils.arrayIterator(array));
    }   
    
}
