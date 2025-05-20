/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import static io.vavr.API.Left;
import static io.vavr.API.None;
import static io.vavr.API.Right;
import static io.vavr.API.Some;
import io.vavr.CheckedConsumer;
import io.vavr.CheckedFunction0;
import io.vavr.CheckedFunction1;
import io.vavr.CheckedFunction2;
import io.vavr.CheckedFunction3;
import io.vavr.CheckedPredicate;
import io.vavr.CheckedRunnable;
import io.vavr.Function0;
import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Function3;
import io.vavr.Value;
import io.vavr.collection.HashSet;
import io.vavr.collection.LinkedHashSet;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import org.joytools.commons.accessors.MapMutator;
import static org.joytools.vavr.SafeCommons.throwUnchecked;

/**
 *
 * @author AndreaR
 */
public class APIX {
    
    /**
     * 
     */
    public APIX() {
    }
    
    public static <E, T> TaggedOption<E, T> TaggedSome(E value) {
        return TaggedOption.some(value);
    }

    /**
     * 
     * @param <E>
     * @param <T>
     * @param value
     * @param tag
     * @return 
     */
    public static <E, T> TaggedValue<E, T> Tagged(E value, T tag) {
        return TaggedValues.some(value, tag);
    }

    /**
     * 
     * @param <E>
     * @param <T>
     * @param value
     * @return 
     */
    public static <E, T> TaggedOption<E, T> TaggedNone(E value) {
        return TaggedOption.none();
    }

    /**
     * 
     * @param <R>
     * @return 
     */
    public static <R> Option<R> Null() {
        return NULL;
    }


    /**
     * 
     * @param <T>
     * @param <V>
     * @param value
     * @return 
     */
    public static <T, V extends Value<T>> V SingleValue(final V value) {
        if (!value.isSingleValued()) {
            throw new IllegalArgumentException("Not a single value: " + value);
        }
        return value;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return 
     */
    @Deprecated
    static <K, V> Either<RuntimeException, V> Either1(final java.util.Map<K, V> map, final K key) {
        final V v = map.get(key);
        if (v != null || map.containsKey(key)) {
            return Right(v);
        }
        return Left(new NoSuchElementException(Objects.toString(key)));
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return 
     */
    @Deprecated
    static <K, V> Either<RuntimeException, V> Either1(final io.vavr.collection.Map<K, V> map, final K key) {
        Objects.requireNonNull(map, "Map");
        final Option<V> v = map.get(key);
        if (v.isDefined()) {
            return Right(v.get());
        }
        return Left(new NoSuchElementException(Objects.toString(key)));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    @Deprecated
    static <K, V> Option<V> old_Get(final MapMutator<K, V> map, final K key) {
        return map.get(key);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    @Deprecated
    static <K, V> Option<V> old_Get(final Map<K, V> map, final K key) {
        final V v = map.get(key);
        if (v != null || map.containsKey(key)) {
            return Some(v);
        }
        return None();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param map
     * @param key
     * @return
     */
    @Deprecated
    static <K, V> Option<V> old_Get(final io.vavr.collection.Map<K, V> map, final K key) {
        return map.get(key);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param list
     * @param index
     * @return
     */
    @Deprecated
    static <K, V> Option<V> old_Get(final List<V> list, final int index) {
        if (index >= list.size() || index < 0) {
            return None();
        }
        return Some(list.get(index));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param seq
     * @param index
     * @return
     */
    @Deprecated
    static <K, V> Option<V> old_Get(final Seq<V> seq, final int index) {
        return seq.isDefinedAt(index) ? Option.some(seq.get(index)) : Option.none();
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param array
     * @param index
     * @return
     */
    @Deprecated
    static <K, V> Option<V> old_Get(final V[] array, final int index) {
        if (index >= array.length || index < 0) {
            return None();
        }
        return Some(array[index]);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param list
     * @param index
     * @return 
     */
    @Deprecated
    static <K, V> Either<RuntimeException, V> Either1(final java.util.List<V> list, final int index) {
        Objects.requireNonNull(list, "List");
        try {
            return Right(list.get(index));
        } catch (final RuntimeException rte) {
            return Left(rte);
        }
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param seq
     * @param index
     * @return 
     */
    @Deprecated
    static <K, V> Either<RuntimeException, V> Either1(final io.vavr.collection.Seq<V> seq, final int index) {
        Objects.requireNonNull(seq, "List");
        try {
            return Right(seq.get(index));
        } catch (final RuntimeException rte) {
            return Left(rte);
        }
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param array
     * @param index
     * @return 
     */
    @Deprecated
    static <K, V> Either<RuntimeException, V> Either1(final V[] array, final int index) {
        Objects.requireNonNull(array, "Array");
        try {
            return Right(array[index]);
        } catch (final RuntimeException rte) {
            return Left(rte);
        }
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param func
     * @return 
     */
    public static <T, R> Function1<T, R> Function1(final Function<T, R> func) {
        Objects.requireNonNull(func, "Function");
        if (func instanceof Function1 f1) {
            return f1;
        }
        return func::apply;
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <R>
     * @param func
     * @return 
     */
    public static <T1, T2, R> Function2<T1, T2, R> Function2(final BiFunction<T1, T2, R> func) {
        Objects.requireNonNull(func, "Function");
        if (func instanceof Function2 f2) {
            return f2;
        }
        return func::apply;
    }

    /**
     * 
     * @param <R>
     * @param consumer
     * @return 
     */
    public static <R> Consumer<R> SafeUncheckedConsumer(final CheckedConsumer<R> consumer) {
        Objects.requireNonNull(consumer, "Consumer");
        return (t1) -> {
            try {
                consumer.accept(t1);
            } catch(final Throwable t) {
                throwUnchecked(t);
            }
        };
    }
    
    /**
     * 
     * @param <R>
     * @param consumer
     * @param mapper
     * @return 
     */
    public static <R> Consumer<R> SafeUncheckedConsumer(final CheckedConsumer<R> consumer,
            final Function<Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(consumer, "Consumer");
        Objects.requireNonNull(mapper, "Mapper");
        return (t1) -> {
            try {
                consumer.accept(t1);
            } catch(final Throwable t) {
                throwUnchecked(t, mapper);
            }
        };
    }

    /**
     * 
     * @param <R>
     * @param function
     * @return 
     */
    public static <R> Function0<R> SafeUncheckedFunction(final CheckedFunction0<R> function) {
        Objects.requireNonNull(function, "Function");
        return () -> {
            try {
                return function.apply();
            } catch(final Throwable t) {
                return throwUnchecked(t);
            }
        };
    }
    
    /**
     * 
     * @param <R>
     * @param function
     * @param mapper
     * @return 
     */
    public static <R> Function0<R> SafeUncheckedFunction(final CheckedFunction0<R> function,
            final Function<Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(function, "Function");
        Objects.requireNonNull(mapper, "Mapper");
        return () -> {
            try {
                return function.apply();
            } catch(final Throwable t) {
                return throwUnchecked(t, mapper);
            }
        };
    }

    /**
     * 
     * @param <T1>
     * @param <R>
     * @param function
     * @return 
     */
    public static <T1, R> Function1<T1, R> SafeUncheckedFunction(final CheckedFunction1<T1, R> function) {
        Objects.requireNonNull(function, "Function");
        return (t1) -> {
            try {
                return function.apply(t1);
            } catch(final Throwable t) {
                return throwUnchecked(t);
            }
        };
    }
    
    /**
     * 
     * @param <T1>
     * @param <R>
     * @param function
     * @param mapper
     * @return 
     */
    public static <T1, R> Function1<T1, R> SafeUncheckedFunction(final CheckedFunction1<T1, R> function,
            final Function<Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(function, "Function");
        Objects.requireNonNull(mapper, "Mapper");
        return (t1) -> {
            try {
                return function.apply(t1);
            } catch(final Throwable t) {
                return throwUnchecked(t, mapper);
            }
        };
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param function
     * @return 
     */
    public static <T1, T2, R> Function2<T1, T2, R> SafeUncheckedFunction(final CheckedFunction2<T1, T2, R> function) {
        Objects.requireNonNull(function, "Function");
        return (t1, t2) -> {
            try {
                return function.apply(t1, t2);
            } catch(final Throwable t) {
                return throwUnchecked(t);
            }
        };
    }
    
    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <R>
     * @param function
     * @param mapper
     * @return 
     */
    public static <T1, T2, R> Function2<T1, T2, R> SafeUncheckedFunction(final CheckedFunction2<T1, T2, R> function,
            final Function<Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(function, "Function");
        Objects.requireNonNull(mapper, "Mapper");
        return (t1, t2) -> {
            try {
                return function.apply(t1, t2);
            } catch(final Throwable t) {
                return throwUnchecked(t, mapper);
            }
        };
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <R>
     * @param function
     * @return 
     */
    public static <T1, T2, T3, R> Function3<T1, T2, T3, R> SafeUncheckedFunction(final CheckedFunction3<T1, T2, T3, R> function) {
        Objects.requireNonNull(function, "Function");
        return (t1, t2, t3) -> {
            try {
                return function.apply(t1, t2, t3);
            } catch(final Throwable t) {
                return throwUnchecked(t);
            }
        };
    }
    
    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <R>
     * @param function
     * @param mapper
     * @return 
     */
    public static <T1, T2, T3, R> Function3<T1, T2, T3, R> SafeUncheckedFunction(final CheckedFunction3<T1, T2, T3, R> function,
            final Function<Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(function, "Function");
        Objects.requireNonNull(mapper, "Mapper");
        return (t1, t2, t3) -> {
            try {
                return function.apply(t1, t2, t3);
            } catch(final Throwable t) {
                return throwUnchecked(t, mapper);
            }
        };
    }

    /**
     * 
     * @param <R>
     * @param predicate
     * @return 
     */
    public static <R> Predicate<R> SafeUncheckedPredicate(final CheckedPredicate<R> predicate) {
        Objects.requireNonNull(predicate, "Predicate");
        return (t1) -> {
            try {
                return predicate.test(t1);
            } catch(final Throwable t) {
                return throwUnchecked(t);
            }
        };
    }
    
    /**
     * 
     * @param <R>
     * @param predicate
     * @param mapper
     * @return 
     */
    public static <R> Predicate<R> SafeUncheckedPredicate(final CheckedPredicate<R> predicate,
            final Function<Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(predicate, "Predicate");
        Objects.requireNonNull(mapper, "Mapper");
        return (t1) -> {
            try {
                return predicate.test(t1);
            } catch(final Throwable t) {
                return throwUnchecked(t, mapper);
            }
        };
    }

    /**
     * 
     * @param runnable
     * @return 
     */
    public static Runnable SafeUncheckedRunnable(final CheckedRunnable runnable) {
        Objects.requireNonNull(runnable, "Runnable");
        return () -> {
            try {
                runnable.run();
            } catch(final Throwable t) {
                throwUnchecked(t);
            }
        };
    }
    
    /**
     * 
     * @param runnable
     * @param mapper
     * @return 
     */
    public static Runnable SafeUncheckedRunnable(final CheckedRunnable runnable,
            final Function<Throwable, ? extends Throwable> mapper) {
        Objects.requireNonNull(runnable, "Runnable");
        Objects.requireNonNull(mapper, "Mapper");
        return () -> {
            try {
                runnable.run();
            } catch(final Throwable t) {
                throwUnchecked(t, mapper);
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @param data
     * @return 
     */
    public static <T> io.vavr.collection.Array<T> toArray(final Iterable<T> data) {
        if (data == null) {
            return io.vavr.collection.Array.empty();
        }
        if (data instanceof Value v) {
            if (v.isEmpty()) {
                return io.vavr.collection.Array.empty();
            }
        }
        if (data instanceof Collection c) {
            if (c.isEmpty()) {
                return io.vavr.collection.Array.empty();
            }
        }
        return io.vavr.collection.Array.ofAll(data);
    }

    /**
     * 
     * @param <T>
     * @param data
     * @return 
     */
    public static <T> io.vavr.collection.List<T> toList(final Iterable<T> data) {
        if (data == null) {
            return io.vavr.collection.List.empty();
        }
        if (data instanceof Value v) {
            if (v.isEmpty()) {
                return io.vavr.collection.List.empty();
            }
        }
        if (data instanceof Collection c) {
            if (c.isEmpty()) {
                return io.vavr.collection.List.empty();
            }
        }
        return io.vavr.collection.List.ofAll(data);
    }

    /**
     * 
     * @param <T>
     * @param data
     * @return 
     */
    public static <T> Vector<T> toVector(final Iterable<T> data) {
        if (data == null) {
            return Vector.empty();
        }
        if (data instanceof Value v) {
            if (v.isEmpty()) {
                return Vector.empty();
            }
        }
        if (data instanceof Collection c) {
            if (c.isEmpty()) {
                return Vector.empty();
            }
        }
        return Vector.ofAll(data);
    }
    
    /**
     * 
     * @param <T>
     * @param data
     * @return 
     */
    public static <T> HashSet<T> toHashSet(final Iterable<T> data) {
        if (data == null) {
            return HashSet.empty();
        }
        if (data instanceof Value v) {
            if (v.isEmpty()) {
                return HashSet.empty();
            }
        }
        if (data instanceof Collection c) {
            if (c.isEmpty()) {
                return HashSet.empty();
            }
        }
        return HashSet.ofAll(data);
    }

    /**
     * 
     * @param <T>
     * @param data
     * @return 
     */
    public static <T> LinkedHashSet<T> toLinkedHashSet(final Iterable<T> data) {
        if (data == null) {
            return LinkedHashSet.empty();
        }
        if (data instanceof Value v) {
            if (v.isEmpty()) {
                return LinkedHashSet.empty();
            }
        }
        if (data instanceof Collection c) {
            if (c.isEmpty()) {
                return LinkedHashSet.empty();
            }
        }
        return LinkedHashSet.ofAll(data);
    }

    /**
     * 
     */
    private final static Option NULL = Some(null);
    
}
