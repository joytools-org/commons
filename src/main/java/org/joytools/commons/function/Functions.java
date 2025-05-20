/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.function;

import static com.google.common.base.Preconditions.checkArgument;
import com.google.common.collect.ImmutableList;
import static io.vavr.API.Left;
import static io.vavr.API.Some;
import static io.vavr.API.None;
import static io.vavr.API.Right;
import static io.vavr.API.Tuple;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.Value;
import io.vavr.collection.Array;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.LongFunction;
import java.util.function.Predicate;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.accessors.MapMutator;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.ComboVariant;
import org.joytools.commons.util.ComboVariants;
import org.joytools.commons.util.Variant;
import static org.joytools.vavr.APIX.SingleValue;

/**
 *
 * @author AndreaR
 */
public class Functions {

    public Functions() {
    }
    
    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    public static <T> Function1<T, T> identity() {
        return (Function1<T, T>)IDENTITY;
    }

    /**
     * 
     */
    private static final Function1<Object, Object> IDENTITY = (final Object input) -> input;    
    
    /**
     * 
     * @param <V>
     * @param <Z>
     * @param type
     * @return 
     */
    public static final <V, Z> Function1<V, Z> castAs(final Class<Z> type) { 
        Objects.requireNonNull(type, "type");
        return x -> type.cast(x);
    }

    /**
     * 
     * @param <T>
     * @param condition
     * @param ifTrue
     * @return 
     */
    public static <T> Function1<T, T> iif(final Predicate<? super T> condition, final Function<? super T, ? extends T> ifTrue) {
        Objects.requireNonNull(condition, "Condition");
        Objects.requireNonNull(ifTrue, "ifTrue");
        return (x) -> condition.test(x) ? ifTrue.apply(x) : x;
    }
    
    /**
     * 
     * @param <T>
     * @param condition
     * @param ifTrue
     * @return 
     */
    public static <T> Function1<T, T> iif(final Predicate<? super T> condition, final T ifTrue) {
        Objects.requireNonNull(condition, "Condition");
        Objects.requireNonNull(ifTrue, "ifTrue");
        return (x) -> condition.test(x) ? ifTrue : x;
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param condition
     * @param ifTrue
     * @param ifFalse
     * @return 
     */
    public static <T, R> Function1<T, R> iif(final Predicate<? super T> condition, 
            final Function<? super T, ? extends R> ifTrue,
            final Function<? super T, ? extends R> ifFalse) {
        Objects.requireNonNull(condition, "Condition");
        Objects.requireNonNull(ifTrue, "ifTrue");
        Objects.requireNonNull(ifFalse, "ifFalse");
        return (x) -> condition.test(x) ? ifTrue.apply(x) : ifFalse.apply(x);
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param condition
     * @param ifTrue
     * @param ifFalse
     * @return 
     */
    public static <T, R> Function1<T, R> iif(final Predicate<? super T> condition, 
            final R ifTrue,
            final Function<? super T, ? extends R> ifFalse) {
        Objects.requireNonNull(condition, "Condition");
        Objects.requireNonNull(ifFalse, "ifFalse");
        return (x) -> condition.test(x) ? ifTrue : ifFalse.apply(x);
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param condition
     * @param ifTrue
     * @param ifFalse
     * @return 
     */
    public static <T, R> Function1<T, R> iif(final Predicate<? super T> condition, 
            final Function<? super T, ? extends R> ifTrue,
            final R ifFalse) {
        Objects.requireNonNull(condition, "Condition");
        Objects.requireNonNull(ifTrue, "ifTrue");
        return (x) -> condition.test(x) ? ifTrue.apply(x) : ifFalse;
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param test
     * @param branches
     * @return 
     */
    public static <T, R> Function1<T, R> choose(
            final Function<? super T, Integer> test, 
            final Function<? super T, ? extends R>... branches) {
        return choose(test, Arrays.asList(branches));
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param chooser
     * @param choices
     * @return 
     */
    public static <T, R> Function1<T, R> choose(
            final Function<? super T, Integer> chooser, 
            final List<Function<? super T, ? extends R>> choices) {
        final Array<Function<? super T, ? extends R>> branches = Array.ofAll(choices);
        return (x) -> {
            final int choice = chooser.apply(x);
            checkArgument(choice >= 0 && choice < branches.size(), "Choice index is out of range: " + choice);
            return branches.get(choice).apply(x);
        };
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param value
     * @return 
     */
    public static <A, B> Function1<A, B> constant(final B value) {
        return (final A input) -> value;
    }    

    /**
     * 
     * @param <K>
     * @param <V>
     * @param inputMapper
     * @param function
     * @return 
     */
    public static <K, V> Function1<K, V> mapInput(final Function<? super K, ? extends K> inputMapper, 
            final Function<K, V> function) {
        Objects.requireNonNull(inputMapper, "Input Mapper");
        Objects.requireNonNull(function, "Function");
        return (k -> function.apply(inputMapper.apply(k)));
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param <V>
     * @param functions
     * @return 
     */
    public static <A, B, V extends Value<B>> Function1<A, Value<B>> firstDefined(final Iterable<? extends Function<A, V>> functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            for (final Function<A, V> function : functions) {
                final V val = SingleValue(function.apply(input));
                if (!val.isEmpty()) {
                    return val;
                }
            }
            return None();
        };
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <V>
     * @param absent
     * @param functions
     * @return 
     */
    public static <A, B, V extends Value<B>> Function1<A, V> firstDefined(final V absent, final Iterable<? extends Function<A, V>> functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            for (final Function<A, V> function : functions) {
                final V val = SingleValue(function.apply(input));
                if (!val.isEmpty()) {
                    return val;
                }
            }
            return absent;
        };
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param <V>
     * @param <F>
     * @param functions
     * @return 
     */
    public static <A, B, V extends Value<B>, F extends Function<A, V>> Function1<A, Value<B>> firstDefined(final F... functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            for (final Function<A, V> function : functions) {
                final V val = SingleValue(function.apply(input));
                if (!val.isEmpty()) {
                    return val;
                }
            }
            return None();
        };
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <V>
     * @param <F>
     * @param defValue
     * @param functions
     * @return 
     */
    public static <A, B, V extends Value<B>, F extends Function<A, V>> Function1<A, V> firstDefined(final V defValue, final F... functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            for (final Function<A, V> function : functions) {
                final V val = SingleValue(function.apply(input));
                if (!val.isEmpty()) {
                    return val;
                }
            }
            return defValue;
        };
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param functions
     * @return 
     */
    public static <A, B> Function1<A, Optional<B>> firstPresent(final Iterable<? extends Function<A, Optional<B>>> functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            for (final Function<A, Optional<B>> function : functions) {
                final Optional<B> val = function.apply(input);
                if (val.isPresent()) {
                    return val;
                }
            }
            return Optional.empty();
        };
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param functions
     * @return 
     */
    public static <A, B> Function1<A, Optional<B>> firstPresent(final Function<A, Optional<B>>... functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            for (final Function<A, Optional<B>> function : functions) {
                final Optional<B> val = function.apply(input);
                if (val.isPresent()) {
                    return val;
                }
            }
            return Optional.empty();
        };
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param <V>
     * @param functions
     * @return 
     */
    public static <A, B, V extends Value<B>> Function1<A, Seq<B>> allDefined(final Iterable<? extends Function<A, V>> functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            final List<B> result = new ArrayList();
            for (final Function<A, V> function : functions) {
                final V val = SingleValue(function.apply(input));
                if (!val.isEmpty()) {
                    result.add(val.get());
                }
            }
            return io.vavr.collection.Vector.ofAll(result);
        };
    }
    
    public static <A, B> Function1<A, List<B>> allDefinedV1(final Iterable<? extends Function<A, Option<B>>> functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            final List<B> result = new ArrayList();
            for (final Function<A, Option<B>> function : functions) {
                final Option<B> val = function.apply(input);
                if (val.isDefined()) {
                    result.add(val.get());
                }
            }
            return result;
        };
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param <V>
     * @param <F>
     * @param functions
     * @return 
     */
    public static <A, B, V extends Value<B>, F extends Function<A, V>> Function1<A, Seq<B>> allDefined(final F... functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            final List<B> result = new ArrayList();
            for (final Function<A, V> function : functions) {
                final V val = SingleValue(function.apply(input));
                if (!val.isEmpty()) {
                    result.add(val.get());
                }
            }
            return io.vavr.collection.Vector.ofAll(result);
        };
    }

    public static <A, B, F extends Function<A, Option<B>>> Function1<A, List<B>> allDefinedV1(final F... functions) {
        Objects.requireNonNull(functions);
        return (final A input) -> {
            final List<B> result = new ArrayList();
            for (final Function<A, Option<B>> function : functions) {
                final Option<B> val = function.apply(input);
                if (val.isDefined()) {
                    result.add(val.get());
                }
            }
            return result;
        };
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param filter
     * @param functions
     * @return 
     */
    public static <A, B> Function1<A, Option<B>> firstMatching(final Predicate<B> filter,
            final Iterable<Function<A, B>> functions) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(functions);
        return (final A input) -> {
            for (final Function<A, B> function : functions) {
                final B val = function.apply(input);
                if (filter.test(val)) {
                    return Some(val);
                }
            }
            return None();
        };
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param filter
     * @param functions
     * @return 
     */
    public static <A, B> Function1<A, Option<B>> firstMatching(final Predicate<B> filter,
            final Function<A, B>... functions) {
        Objects.requireNonNull(filter);
        Objects.requireNonNull(functions);
        return (final A input) -> {
            for (final Function<A, B> function : functions) {
                final B val = function.apply(input);
                if (filter.test(val)) {
                    return Some(val);
                }
            }
            return None();
        };
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param functions
     * @return 
     */
    public static <A, B> Function1<A, Option<B>> firstNonNull(
            final Iterable<Function<A, B>> functions) {
        return Functions.firstMatching(Predicates.isNotNull(), functions);
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param functions
     * @return 
     */
    public static <A, B> Function1<A, Option<B>> firstNonNull(
            final Function<A, B>... functions) {
        return firstMatching(Predicates.isNotNull(), functions);
    }    

    /**
     * 
     * @param <A>
     * @param <B>
     * @param functions
     * @return 
     */
    public static <A, B> Function1<A, Option<B>> firstNonEmpty(
            final Iterable<Function<A, B>> functions) {
        return firstMatching(Predicates.isNotEmpty(), functions);
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param functions
     * @return 
     */
    public static <A, B> Function1<A, Option<B>> firstNonEmpty(
            final Function<A, B>... functions) {
        return firstMatching(Predicates.isNotEmpty(), functions);
    }    

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> Function1<K, V> forMap(final java.util.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return (final K key) -> {
            return map.get(key);
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
     public static <K, V> Function1<K, V> forMap(final io.vavr.collection.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return (final K key) -> {
            return map.getOrElse(key, null);
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Option<V>> old_forMapOption(final java.util.Map<K, V> map) {
        return old_forMapOption(map, false);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param nullToAbsent
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Option<V>> old_forMapOption(final java.util.Map<K, V> map,
            final boolean nullToAbsent) {
        Objects.requireNonNull(map, "Map");
        return (final K key) -> {
            final V value = map.get(key);
            if (nullToAbsent && value == null) {
                return None();
            }
            if (value != null || map.containsKey(key)) {
                return Some(value);
            }
            return None();
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Option<V>> old_forMapOption(final io.vavr.collection.Map<K, V> map) {
        return old_forMapOption(map, false);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @param nullToAbsent
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Option<V>> old_forMapOption(final io.vavr.collection.Map<K, V> map,
            final boolean nullToAbsent) {
        Objects.requireNonNull(map, "Map");
        return (final K key) -> {
            final Option<V> value = map.get(key);
            if (nullToAbsent) {
                if (value.getOrNull() == null) {
                    return None();
                }
            }
            return value;
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    public static <K, V> Function1<K, V> forAccessor(final MapAccessor<K, V> acc) {
        Objects.requireNonNull(acc, "MapAccessor");
        return (final K key) -> {
            return acc.getOrNull(key);
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Option<V>> old_forMutatorOption(final MapMutator<K, V> acc) {
        return old_forMutatorOption(acc, false);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @param nullToAbsent
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Option<V>> old_forMutatorOption(final MapMutator<K, V> acc,
            final boolean nullToAbsent) {
        Objects.requireNonNull(acc, "Mutator");
        return (final K key) -> {
            final Option<V> value = acc.get(key);
            if (nullToAbsent && value.getOrNull() == null) {
                return None();
            }
            return value;
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Optional<V>> old_forMapOptional(final java.util.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return (final K key) -> {
            final V value = map.get(key);
            if (value != null) {
                return Optional.of(value);
            }
            return Optional.empty();
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Optional<V>> old_forMapOptional(final io.vavr.collection.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return (final K key) -> map.get(key).toJavaOptional();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param maps
     * @return 
     */
    public static <K, V, M extends java.util.Map<K, V>> Function1<K, Option<V>> getFirstDefined(final M... maps) {
        return getFirstMatching(Predicates.alwaysTrue(), maps);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param maps
     * @return 
     */
    public static <K, V, M extends io.vavr.collection.Map<K, V>> Function1<K, Option<V>> getFirstDefined(final M... maps) {
        return getFirstMatching(Predicates.alwaysTrue(), maps);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param maps
     * @return 
     */
    public static <K, V, M extends java.util.Map<K, V>> Function1<K, Option<V>> getFirstDefined(final Iterable<M> maps) {
        return getFirstMatching(Predicates.alwaysTrue(), maps);
    }    

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param filter
     * @param maps
     * @return 
     */
    public static <K, V, M extends java.util.Map<K, V>> Function1<K, Option<V>> getFirstMatching(
            final Predicate<V> filter,
            final M... maps) {
        Objects.requireNonNull(filter, "Filter");
        return (k) -> {
            if (maps != null) {
                for (final M map : maps) {
                    final Option<V> v = AccessorMethods.get(map, k);
                    if (v.isDefined() && filter.test(v.get())) {
                        return v;
                    }
                }
            }
            return None();
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param filter
     * @param maps
     * @return 
     */
    public static <K, V, M extends io.vavr.collection.Map<K, V>> Function1<K, Option<V>> getFirstMatching(
            final Predicate<V> filter,
            final M... maps) {
        Objects.requireNonNull(filter, "Filter");
        return (k) -> {
            if (maps != null) {
                for (final M map : maps) {
                    final Option<V> v = map.get(k);
                    if (v.isDefined() && filter.test(v.get())) {
                        return v;
                    }
                }
            }
            return None();
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param filter
     * @param maps
     * @return 
     */
    public static <K, V, M extends Map<K, V>> Function1<K, Option<V>> getFirstMatching(
            final Predicate<V> filter,
            final Iterable<M> maps) {
        Objects.requireNonNull(filter, "Filter");
        Objects.requireNonNull(filter, "Maps");
        return (k) -> {
            if (maps != null) {
                for (final M map : maps) {
                    final Option<V> v = AccessorMethods.get(map, k);
                    if (v.isDefined() && filter.test(v.get())) {
                        return v;
                    }
                }
            }
            return None();
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param maps
     * @return 
     */
    public static <K, V, M extends Map<K, V>> Function1<K, Option<V>> getFirstNonNull(final M... maps) {
        return Functions.getFirstMatching(Predicates.isNotNull(), maps);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param maps
     * @return 
     */
    public static <K, V, M extends Map<K, V>> Function1<K, Option<V>> getFirstNonNull(final Iterable<M> maps) {
        return getFirstMatching(Predicates.isNotNull(), maps);
    }    

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param maps
     * @return 
     */
    public static <K, V, M extends java.util.Map<K, V>> Function1<K, Option<V>> getFirstNonEmpty(final M... maps) {
        return Functions.getFirstMatching(Predicates.isNotEmpty(), maps);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param maps
     * @return 
     */
    public static <K, V, M extends io.vavr.collection.Map<K, V>> Function1<K, Option<V>> getFirstNonEmpty(final M... maps) {
        return Functions.getFirstMatching(Predicates.isNotEmpty(), maps);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param <M>
     * @param maps
     * @return 
     */
    public static <K, V, M extends Map<K, V>> Function1<K, Option<V>> getFirstNonEmpty(final Iterable<M> maps) {
        return getFirstMatching(Predicates.isNotEmpty(), maps);
    }    

    /**
     * 
     * @param <T>
     * @param p
     * @return 
     */
    public static <T> Function1<T, Boolean> predicate(final Predicate<T> p) {
        Objects.requireNonNull(p, "Predicate");
        if (p instanceof Function1 func) {
            return func;
        }
        return (final T input) -> p.test(input);
    }
    
    /**
     * 
     * @param <T>
     * @param predicates
     * @return 
     */
    public static <T> Function1<T, Integer> partitionIndex(final Predicate<? super T>... predicates) {
        final Iterable<Predicate<? super T>> prs = IterableAccessors.from(predicates);
        return x -> {
            int i = 0;
            for (final Predicate<? super T> p : prs) {
                if (p.test(x)) {
                    return i;
                }
                i++;
            }
            return i;
        };
    }

    /**
     * 
     * @param <T>
     * @param predicates
     * @return 
     */
    public static <T> Function1<T, Integer> partitionIndex(final List<Predicate<? super T>> predicates) {
        final List<Predicate<? super T>> prs = ImmutableList.copyOf(predicates);
        return x -> {
            int i = 0;
            for (final Predicate<? super T> p : prs) {
                if (p.test(x)) {
                    return i;
                }
                i++;
            }
            return i;
        };
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    @Deprecated
    public static <T> Function1<T, Integer> old_getAndIncrementInt() {
        final AtomicInteger counter = new AtomicInteger();
        return x -> counter.getAndIncrement();
    }

    /**
     * 
     * @param <T>
     * @param func
     * @return 
     */
    @Deprecated
    public static <T> Function1<T, Integer> old_getAndIncrementIntAndThen(final IntFunction<Integer> func) {
        Objects.requireNonNull(func);
        final AtomicInteger counter = new AtomicInteger();
        return x -> {
            return func.apply(counter.getAndIncrement());
        };
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    @Deprecated
    public static <T> Function1<T, Long> old_getAndIncrementLong() {
        final AtomicLong counter = new AtomicLong();
        return x -> counter.getAndIncrement();
    }

    /**
     * 
     * @param <T>
     * @param func
     * @return 
     */
    @Deprecated
    public static <T> Function1<T, Long> old_getAndIncrementLongAndThen(final LongFunction<Long> func) {
        Objects.requireNonNull(func);
        final AtomicLong counter = new AtomicLong();
        return x -> {
            return func.apply(counter.getAndIncrement());
        };
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Integer> intIndex() {
        final AtomicInteger counter = new AtomicInteger();
        return x -> counter.getAndIncrement();
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Tuple2<Integer, T>> intIndexTuple() {
        final AtomicInteger counter = new AtomicInteger();
        return x -> Tuple(counter.getAndIncrement(), x);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Long> longIndex() {
        final AtomicLong counter = new AtomicLong();
        return x -> counter.getAndIncrement();
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Tuple2<Long, T>> longIndexTuple() {
        final AtomicLong counter = new AtomicLong();
        return x -> Tuple(counter.getAndIncrement(), x);
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param function
     * @return 
     */
    public static <T, R> Function1<T, io.vavr.control.Either<Exception, R>> tryCatch(final Function<T, R> function) {
        Objects.requireNonNull(function, "Function");
        return (final T input) -> {
            try {
                return Right(function.apply(input));
            } catch (final Exception e) {
                return Left(e);
            }
        };
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Option<T>> toOption() {
        return toOption(true);
    }
    
    /**
     * 
     * @param <T>
     * @param nullToAbsent
     * @return 
     */
    public static <T> Function1<T, Option<T>> toOption(final boolean nullToAbsent) {
        return (final T input) -> {
            if (input == null && nullToAbsent) {
                return None();
            }
            return Some(input);
        };
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Optional<T>> toOptional() {
        return (final T input) -> Optional.ofNullable(input);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, String> toObjectString() {
        return (Function1<T, String>)TO_STRING;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, CharSequence> toCharSequence() {
        return (Function1<T, CharSequence>)TO_CHAR_SEQUENCE;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Object> asObject() {
        return (Function1<T, Object>)AS_OBJECT;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, String> asString() {
        return (Function1<T, String>)TO_STRING;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Integer> toInteger() {
        return (Function1<T, Integer>)TO_INTEGER;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Long> toLong() {
        return (Function1<T, Long>)TO_LONG;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, String> toNullString() {
        return (Function1<T, String>)TO_NULL_STRING;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, String> toEmptyString() {
        return (Function1<T, String>)TO_EMPTY_STRING;
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, Variant<T>> toVariant() {
        return (T t) -> ComboVariants.of(t);
    }

    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Function1<T, ComboVariant<T>> toComboVariant() {
        return (T t) -> ComboVariants.of(t);
    }

    /**
     * 
     * @param <L>
     * @param <R>
     * @return 
     */
    public final static <L, R> Function1<Either<L, R>, L> getLeft() {
        return (Function1<Either<L, R>, L>)((Function1)GET_LEFT);
    }

    /**
     * 
     * @param <L>
     * @param <R>
     * @return 
     */
    public final static <L, R> Function1<Either<L, R>, R> getRight() {
        return (Function1<Either<L, R>, R>)((Function1)GET_RIGHT);
    }

    /**
     * 
     */
    final static Function1<Either<Object, Object>, Object> GET_RIGHT = e -> e.get();
            
    /**
     * 
     */
    final static Function1<Either<Object, Object>, Object> GET_LEFT = e -> e.getLeft();

    /**
     * 
     */
    private static final Function1<Object, Integer> TO_INTEGER = 
            (final Object input) -> input == null ? null : Integer.valueOf(input.toString());

    /**
     * 
     */
    private static final Function1<Object, Long> TO_LONG = 
            (final Object input) -> input == null ? null : Long.valueOf(input.toString());

    /**
     * 
     */
    private static final Function1<Object, String> TO_STRING = 
            (final Object input) -> input == null ? null : input.toString();

    /**
     * 
     */
    private static final Function1<Object, Object> AS_OBJECT = x -> (Object)x;

    /**
     * 
     */
    private static final Function1<Object, CharSequence> TO_CHAR_SEQUENCE = 
            (final Object input) -> input == null ? null : input instanceof CharSequence cs ? cs : input.toString();

    /**
     * 
     */
    private static final Function1<Object, String> TO_NULL_STRING = 
            (final Object input) -> StringUtils.toNullableString(input);

    /**
     * 
     */
    private static final Function1<Object, String> TO_EMPTY_STRING = 
            (final Object input) -> StringUtils.toEmptyString(input);
    
}
