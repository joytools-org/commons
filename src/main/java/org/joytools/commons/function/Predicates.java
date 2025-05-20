/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.function;

import static com.google.common.base.Preconditions.checkArgument;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.collections4.Equator;
import org.apache.commons.lang3.ArrayUtils;
import org.joytools.commons.collections.EquatorHashMap;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.concurrent.RemainingTime;
import org.joytools.commons.concurrent.TimeValueUnit;
import org.joytools.commons.concurrent.TimeValueUnits;
import org.joytools.commons.concurrent.Timeouts;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class Predicates {
    
    public Predicates() {
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Predicate<T> alwaysTrue() {
        return (Predicate<T>)ALWAYS_TRUE;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Predicate<T> alwaysFalse() {
        return (Predicate<T>)ALWAYS_FALSE;
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Predicate<T> isNull() {
        return (Predicate<T>)IS_NULL;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Predicate<T> isNotNull() {
        return (Predicate<T>)IS_NOT_NULL;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Predicate<T> isEmpty() {
        return (Predicate<T>)IS_EMPTY;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Predicate<T> isNotEmpty() {
        return (Predicate<T>)IS_NOT_EMPTY;
    }
    
    /**
     * 
     * @param <T>
     * @param targetRef
     * @return 
     */
    public static <T> Predicate<T> isEqual(final Object targetRef) {
        return Predicate.isEqual(targetRef);
    }

    /**
     * 
     * @param type
     * @return 
     */
    public static Predicate<Object> instanceOf(final Class type) {
        Objects.requireNonNull(type, "Type");
        return (Predicate) (final Object input) -> {
            if (input == null) {
                return false;
            }
            return type.isInstance(input);
        };
    }
    
    /**
     * 
     * @param className
     * @return 
     */
    public static Predicate<Object> instanceOf(final CharSequence className) {
        checkArgument(StringUtils.isNotEmpty(className), "ClassName is empty");
        final Class c;
        try {
            c = Class.forName(className.toString());
        } catch (final ClassNotFoundException cnfe) {
            return alwaysFalse();
        }
        return (Predicate) (final Object input) -> {
            if (input == null) {
                return false;
            }
            return c.isInstance(input);
        };
    }

    /**
     * 
     * @param <R>
     * @param array
     * @return 
     */
    @Deprecated
    public static <R> Predicate<R> old_contains(final R[] array) {
        return (final R input) -> array == null ? false : ArrayUtils.contains(array, input);
    }

    /**
     * 
     * @param <R>
     * @param m
     * @return 
     */
    @Deprecated
    public static <R> Predicate<R> old_contains(final Collection<R> m) {
        return (final R input) -> m == null ? false : m.contains(input);
    }

    /**
     * 
     * @param <R>
     * @param m
     * @return 
     */
    public static <R> Predicate<R> containsKey(final Map<R, ?> m) {
        return (final R input) -> m == null ? false : m.containsKey(input);
    }

    /**
     * 
     * @param <R>
     * @param m
     * @return 
     */
    public static <R> Predicate<R> containsKey(final io.vavr.collection.Map<R, ?> m) {
        return (final R input) -> m == null ? false : m.containsKey(input);
    }

    /**
     * 
     * @param <R>
     * @param m
     * @return 
     */
    public static <R> Predicate<R> containsValue(final Map<?, R> m) {
        return (final R input) -> m == null ? false : m.containsValue(input);
    }

    /**
     * 
     * @param <R>
     * @param m
     * @return 
     */
    public static <R> Predicate<R> containsValue(final io.vavr.collection.Map<?, R> m) {
        return (final R input) -> m == null ? false : m.containsValue(input);
    }

    /**
     * 
     * @param <T>
     * @param <Z>
     * @param f
     * @return 
     */
    public static <T, Z> Predicate<T> isDefined(final Function<T, Option<Z>> f) {
        Objects.requireNonNull(f);
        return (final T t) -> f.apply(t).isDefined();
    }

    /**
     * 
     * @param <T>
     * @param <Z>
     * @param f
     * @return 
     */
    public static <T, Z> Predicate<T> isPresent(final Function<T, Optional<Z>> f) {
        Objects.requireNonNull(f);
        return (final T t) -> f.apply(t).isPresent();
    }
    
    /**
     * 
     * @param <T>
     * @param size
     * @return 
     */
    public static <T> Predicate<T> limit(final int size) {
        if (size < 0) {
            return alwaysTrue();
        }
        final AtomicInteger counter = new AtomicInteger();
        return x -> {
            return counter.incrementAndGet() <= size;
        };
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param function
     * @param predicate
     * @return 
     */
    public static <K, V> Predicate<? super K> mapping(final Function<? super K, ? extends V> function, final Predicate<? super V> predicate) {
        Objects.requireNonNull(function, "Function");
        Objects.requireNonNull(predicate, "Predicate");
        return x -> predicate.test(function.apply(x));
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Predicate<T> distinct() {
        final Set<T> elements = new HashSet<>();
        return (final T t) -> {
            synchronized(elements) {
                return elements.add(t);
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @param comp
     * @return 
     */
    public static <T> Predicate<T> distinct(final Comparator<T> comp) {
        final SortedSet<T> elements = new TreeSet<>(comp);
        return (final T t) -> {
            synchronized(elements) {
                return elements.add(t);
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param func
     * @return 
     */
    public static <T, R> Predicate<T> distinct(final Function<T, R> func) {
        Objects.requireNonNull(func, "Function");
        final SortedSet<R> elements = new TreeSet<>();
        return (final T t) -> {
            synchronized(elements) {
                return elements.add(func.apply(t));
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param eq
     * @return 
     */
    public static <T> Predicate<T> distinct(final Equator<T> eq) {
        final EquatorHashMap<T, Integer> elements = new EquatorHashMap<>(eq);
        return (final T t) -> {
            synchronized(elements) {
                return elements.put(t, 0) == null;
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param element
     * @param moreElements
     * @return 
     */
    public static <T> Predicate<T> equalsAny(final T element, final T... moreElements) {
        if (ArrayUtils.isEmpty(moreElements)) {
            return (final T t) -> Objects.equals(t, element);
        }
        final T[] elements = ArrayUtils.addFirst(moreElements, element);
        final io.vavr.collection.Set<T> s = io.vavr.collection.HashSet.of(elements);
        return (final T t) -> s.contains(t);
    }

    /**
     * 
     * @param <T>
     * @param <I>
     * @param elements
     * @return 
     */
    public static <T, I extends Iterable<T>> Predicate<T> equalsAny(final I elements) {
        checkArgument(elements != null, "No elements specified");
        return (final T t) -> IterableAccessors.from(elements).contains(t);
    }

    /**
     * 
     * @param <T>
     * @param count
     * @return 
     */
    public static <T> Predicate<T> every(final long count) {
        if (count <= 0) {
            return alwaysTrue();
        }
        final AtomicLong counter = new AtomicLong();
        return x -> {
            if (counter.incrementAndGet() >= count) {
                counter.set(0);
                return true;
            }
            return false;
        };
    }

    /**
     * 
     * @param <T>
     * @param value
     * @return 
     */
    public static <T> Predicate<T> every(final CharSequence value) {
        return every(TimeValueUnits.of(value));
    }

    /**
     * 
     * @param <T>
     * @param value
     * @param unit
     * @return 
     */
    public static <T> Predicate<T> every(final long value, final TimeUnit unit) {
        return every(TimeValueUnits.of(value, unit));
    }

    /**
     * 
     * @param <T>
     * @param timeout
     * @return 
     */
    public static <T> Predicate<T> every(final TimeValueUnit timeout) {
        Objects.requireNonNull(timeout, "Timeout");
        final AtomicReference<RemainingTime> timeoutRef = new AtomicReference(Timeouts.fromNow(timeout));
        return x -> {
            if (timeoutRef.get().isAfter()) {
                timeoutRef.set(Timeouts.fromNow(timeout));
                return true;
            }
            return false;
        };
    }

    /**
     * 
     * @param <L>
     * @param <R>
     * @return 
     */
    public static <L, R> Predicate<Either<L, R>> isLeft() {
        return (Predicate<Either<L, R>>)((Predicate)IS_LEFT);
    }
    
    /**
     * 
     * @param <L>
     * @param <R>
     * @return 
     */
    public static <L, R> Predicate<Either<L, R>> isRight() {
        return (Predicate<Either<L, R>>)((Predicate)IS_RIGHT);
    }

    /**
     * 
     */
    private final static Predicate<Either<Object, Object>> IS_LEFT = e -> e.isLeft();
    
    /**
     * 
     */
    private final static Predicate<Either<Object, Object>> IS_RIGHT = e -> e.isRight();

    /**
     * 
     */
    private static final Predicate<Object> ALWAYS_TRUE = (final Object input) -> true;

    /**
     * 
     */
    private static final Predicate<Object> ALWAYS_FALSE = (final Object input) -> false;

    /**
     * 
     */
    private static final Predicate<Object> IS_NULL = (final Object input) -> input == null;

    /**
     * 
     */
    private static final Predicate<Object> IS_NOT_NULL = (final Object input) -> input != null;

    /**
     * 
     */
    private static final Predicate<Object> IS_EMPTY = ObjectUtils::isEmpty;

    /**
     * 
     */
    private static final Predicate<Object> IS_NOT_EMPTY = ObjectUtils::isNotEmpty;
        
}
