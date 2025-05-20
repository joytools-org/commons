/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.mappers;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Function1;
import io.vavr.Tuple2;
import io.vavr.Value;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.IgnoreCaseAccessorMethods;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.MapMutator;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.function.Failables;
import org.joytools.commons.function.Predicates;
import org.joytools.commons.util.CaseSensitivity;
import static org.joytools.vavr.APIX.SingleValue;

/**
 *
 * @author AndreaR
 */
public class Mappers {
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> empty() {
        return (Function1<K, Option<V>>)((Function)EMPTY);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> of(final MapAccessor<K, V> acc) {
        return forAccessor(acc);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Option<V>> of1(final MapMutator<K, V> acc) {
        return forMutator1(acc);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forAccessor(final MapAccessor<K, V> acc) {
        Objects.requireNonNull(acc, "Accessor");
        return k -> acc.get(k);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    @Deprecated
    public static <K, V> Function1<K, Option<V>> forMutator1(final MapMutator<K, V> acc) {
        Objects.requireNonNull(acc, "Mutator");
        return k -> acc.get(k);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param func
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forFunction(final Function<K, V> func) {
        return forFunction(func, Predicates.isNull());
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param func
     * @param noneTest
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forFunction(
            final Function<K, V> func,
            final Predicate<? super V> noneTest) {
        Objects.requireNonNull(func, "Function");
        Objects.requireNonNull(noneTest, "None Predicate");
        return k -> {
            final V value = func.apply(k);
            return noneTest.test(value) ? None() : Some(value);
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> of(final java.util.Map<K, V> map) {
        return forMap(map);
    }

    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    public static <V> Function1<String, Option<V>> ofIgnoreCase(final java.util.Map<String, V> map) {
        return forMapIgnoreCase(map);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forMap(final java.util.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return k -> AccessorMethods.get(map, k);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> of(final io.vavr.collection.Map<K, V> map) {
        return forMap(map);
    }

    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    public static <V> Function1<String, Option<V>> ofIgnoreCase(final io.vavr.collection.Map<String, V> map) {
        return forMapIgnoreCase(map);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forMap(final io.vavr.collection.Map<K, V> map) {
        Objects.requireNonNull(map, "Map");
        return k -> map.get(k);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    public static <T> Function1<String, Option<T>> forMapIgnoreCase(final java.util.Map<String, T> map) {
        Objects.requireNonNull(map, "Map");
        return (final String key) -> IgnoreCaseAccessorMethods.get(map, key);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    public static <T> Function1<String, Option<T>> forMapIgnoreCase(final io.vavr.collection.Map<String, T> map) {
        Objects.requireNonNull(map, "Map");
        return (final String key) -> IgnoreCaseAccessorMethods.get(map, key);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> of(final Predicate<? super K> p, final V value) {
        return forValue(p, value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forValue(final Predicate<? super K> p, final V value) {
        Objects.requireNonNull(p, "Predicate");
        return k -> p.test(k) ? Some(value) : None();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> of(final Predicate<? super K> p, final Option<V> value) {
        return forOption(p, value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forOption(final Predicate<? super K> p, final Option<V> value) {
        Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(value, "Value");
        return k -> p.test(k) ? value : None();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param supplier
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> of(final Predicate<? super K> p, final Supplier<V> supplier) {
        return forSupplier(p, supplier);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param supplier
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forSupplier(final Predicate<? super K> p, final Supplier<V> supplier) {
        Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(supplier, "Supplier");
        return k -> p.test(k) ? Some(supplier.get()) : None();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param callable
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forCallable(final Predicate<? super K> p, final Callable<V> callable) {
        Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(callable, "Callable");
        return Failables.wrapped.asFunction(k -> {
            return p.test(k) ? Some(callable.call()) : None();
        });
    }

    /**
     * 
     * @param <K>
     * @param <O>
     * @param <V>
     * @param mappers
     * @return 
     */
    public static <K, O, V extends Value<O>> Function1<K, Option<O>> concat(final Function<K, ? extends V>... mappers) {
        Objects.requireNonNull(mappers, "Mappers");
        return k -> {
            for (final Function<K, ? extends V> f : mappers) {
                final V ret = SingleValue(f.apply(k));
                if (!ret.isEmpty()) {
                    return ret.toOption();
                }
            }
            return None();
        };
    }

    /**
     * 
     * @param <K>
     * @param <O>
     * @param <V>
     * @param absent
     * @param mappers
     * @return 
     */
    static <K, O, V extends Value<O>> Function1<K, V> concat(final V absent, final Function<K, ? extends V>... mappers) {
        Objects.requireNonNull(absent, "Absent");
        Objects.requireNonNull(mappers, "Mappers");
        return k -> {
            for (final Function<K, ? extends V> f : mappers) {
                final V ret = SingleValue(f.apply(k));
                if (!ret.isEmpty()) {
                    return ret;
                }
            }
            return absent;
        };
    }

    /**
     * 
     * @param elements
     * @return 
     */
    static List<Tuple2<String, String>> toTuples(final String... elements) {
        final List<Tuple2<String, String>> tuples = new ArrayList();
        if (elements != null && elements.length > 0) {
            if (elements.length % 2 > 0) {
                throw new IllegalArgumentException("Wrong number of arguments, must be even: " + elements.length);
            }
            for (int index = 0; index < elements.length; index +=2) {
                tuples.add(Tuple(elements[index], elements[index + 1]));
            }
        }
        return tuples;
    }
    
    /**
     * 
     * @param elements
     * @return 
     */
    public static Function1<String, Option<String>> forMap(final String... elements) {
        if (elements == null || elements.length == 0) {
            return empty();
        }
        final java.util.Map<String, String> m = MapUtils.insertAll(
                CaseSensitivity.SENSITIVE.newLinkedMap(),
                toTuples(elements));
        return forMap(m);
    }
    
    /**
     * 
     * @param elements
     * @return 
     */
    public static Function1<String, Option<String>> forMapIgnoreCase(final String... elements) {
        if (elements == null || elements.length == 0) {
            return empty();
        }
        final java.util.Map<String, String> m = MapUtils.insertAll(
                CaseSensitivity.INSENSITIVE.newLinkedMap(),
                toTuples(elements));
        return forMap(m);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param maps
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forMaps(final java.util.Map<K, V>... maps) {
        if (maps == null || maps.length == 0) {
            return empty();
        }
        return (final K key) -> {
            for (final java.util.Map<K, V> map : maps) {
                final Option<V> value = AccessorMethods.get(map, key);
                if (value.isDefined()) {
                    return value;
                }
            }
            return None();
        };
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param maps
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forMaps(final io.vavr.collection.Map<K, V>... maps) {
        if (maps == null || maps.length == 0) {
            return empty();
        }
        return (final K key) -> {
            for (final io.vavr.collection.Map<K, V> map : maps) {
                final Option<V> value = map.get(key);
                if (value.isDefined()) {
                    return value;
                }
            }
            return None();
        };
    }

    /**
     * 
     * @param <V>
     * @param maps
     * @return 
     */
    public static <V> Function1<String, Option<V>> forMapsIgnoreCase(final java.util.Map<String, V>... maps) {
        if (maps == null || maps.length == 0) {
            return empty();
        }
        return (final String key) -> {
            for (final java.util.Map<String, V> map : maps) {
                final Option<V> value = IgnoreCaseAccessorMethods.get(map, key);
                if (value.isDefined()) {
                    return value;
                }
            }
            return None();
        };
    }

    /**
     * 
     * @param <V>
     * @param maps
     * @return 
     */
    public static <V> Function1<String, Option<V>> forMapsIgnoreCase(final io.vavr.collection.Map<String, V>... maps) {
        if (maps == null || maps.length == 0) {
            return empty();
        }
        return (final String key) -> {
            for (final io.vavr.collection.Map<String, V> map : maps) {
                final Option<V> value = IgnoreCaseAccessorMethods.get(map, key);
                if (value.isDefined()) {
                    return value;
                }
            }
            return None();
        };
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> equals(final K key, final V value) {
        return Mappers.of(Predicates.equalsAny(key), value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> equals(final K key, final Option<V> value) {
        return Mappers.of(Predicates.equalsAny(key), value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> equals(final K key, final Supplier<V> value) {
        return Mappers.of(Predicates.equalsAny(key), value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> equals(final K key, final Function<? super K, Option<V>> value) {
        return Mappers.of(Predicates.equalsAny(key), value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param mapper
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> of(final Predicate<? super K> p, 
            final Function<? super K, Option<V>> mapper) {
        return forMapper(p, mapper);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param p
     * @param mapper
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forMapper(final Predicate<? super K> p, 
            final Function<? super K, Option<V>> mapper) {
        Objects.requireNonNull(p, "Predicate");
        Objects.requireNonNull(mapper, "Mapper");
        return k -> {
            return p.test(k) ? mapper.apply(k) : None();
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forNull(final V value) {
        return Mappers.of(Predicates.isNull(), value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forNull(final Option<V> value) {
        return Mappers.of(Predicates.isNull(), value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param value
     * @return 
     */
    public static <K, V> Function1<K, Option<V>> forNull(final Supplier<V> value) {
        return Mappers.of(Predicates.isNull(), value);
    }

    /**
     * 
     */
    private final static Function1<?, Option<?>> EMPTY = x -> { return None(); };

}
