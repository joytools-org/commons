/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.impl;

import static io.vavr.API.Tuple;
import io.vavr.Function1;
import io.vavr.Tuple2;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.text.TextStringBuilder;
import org.joytools.commons.accessors.Accessor;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.MutatorUtils;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class AccessorImplUtils {
    
    /**
     * 
     * @param <V>
     * @param acc
     * @return 
     */
    public static <V> String toString(final Accessor<V> acc) {
        if (acc instanceof IndexedMapAccessor) {
            return toString0(MapAccessor.class.cast(acc));
        }
        if (acc instanceof MapAccessor ma) {
            return toString0(ma);
        }
        if (acc instanceof ListAccessor la) {
            return toString0(la);
        }
        return "<Unknown Mutator>";
    }

    /**
     * 
     * @param acc
     * @return 
     */
    private static String toString0(final MapAccessor acc) {
        final TextStringBuilder sb = new TextStringBuilder("{")
                .appendWithSeparators(MutatorUtils.entries(acc).map(TUPLE_TO_STRING), ",")
                .append("}");
        return sb.toString();
    }

    /**
     * 
     * @param acc
     * @return 
     */
    private static String toString0(final ListAccessor acc) {
        final TextStringBuilder sb = new TextStringBuilder("[")
                .appendWithSeparators(acc.values(), ",")
                .append("]");
        return sb.toString();
    }

    /**
     * 
     */
    private final static Function1<Tuple2<?, ?>, String> TUPLE_TO_STRING = (final Tuple2<?, ?> t) -> {
        return t._1() + "=" + t._2();
    };

    /**
     *
     * @param <K>
     * @param <V>
     * @param <Z>
     * @param key
     * @param value
     * @param function
     * @return
     */
    public static <K, V, Z> Z apply(final K key, final V value, final Function<? super V, ? extends Z> function) {
        try {
            Objects.requireNonNull(function, "Function");
            return function.apply(value);
        } catch (final Exception rte) {
            final StringBuilder sb = new StringBuilder("Function call failed for ");
            if (key != null) {
                sb.append("key '").append(key).append("' ");
            }
            sb.append("value '").append(value).append("': ").append(rte.getMessage());
            throw new UnsupportedOperationException(sb.toString(), rte);
        }
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param acc
     * @return
     */
    public static <K, V> IterableAccessor<Tuple2<K, V>> defaultEntriesByKey(final MapAccessor<K, V> acc) {
        return acc.keys().map(k -> Tuple(k, acc.getOrFail(k)));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param acc
     * @return
     */
    public static <K, V> IterableAccessor<Tuple2<Integer, V>> defaultEntriesByIndex(final ListAccessor<V> acc) {
        return acc.indexes().map(i -> Tuple(i, acc.getOrFail(i)));
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param key
     * @param value
     * @param validator
     * @return
     */
    public static <K, V> boolean test(final K key, final V value, final Predicate<? super V> validator) {
        try {
            Objects.requireNonNull(validator, "Predicate");
            return validator.test(value);
        } catch (final Exception rte) {
            final StringBuilder sb = new StringBuilder("Validation failed for ");
            if (key != null) {
                sb.append("key '").append(key).append("' ");
            }
            sb.append("value '").append(value).append("': ").append(rte.getMessage());
            throw new UnsupportedOperationException(sb.toString(), rte);
        }
    }
    
}
