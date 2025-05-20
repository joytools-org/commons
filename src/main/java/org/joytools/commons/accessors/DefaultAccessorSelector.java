/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.Function1;
import io.vavr.Value;
import io.vavr.collection.Seq;
import java.time.Instant;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Optional;
import org.joytools.commons.accessors.core.CoreAccessors;
import org.joytools.commons.accessors.collection.CollectionAccessors;
import org.joytools.commons.accessors.vavr.VavrAccessors;
import org.joytools.commons.collections.IterableAccessors;

// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.time.TimeValue;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <U>
 * @param <K>
 * @param <V>
 */
public abstract class DefaultAccessorSelector<T, U, K, V> implements AccessorSelector<K, V> {
    
    abstract protected T key();

    abstract protected U value();

    /**
     * 
     * @return 
     */
    @Override
    public ListAccessor<V> list() {
        return listFrom(value());
    } 

    /**
     * 
     * @return 
     */
    @Override
    public ListAccessor<V> singleList() {
        return Accessors.singleValued(listFrom(value()));
    } 

    /**
     * 
     * @return 
     */
    @Override
    public ListAccessor<V> nonEmptyList() {
        return Accessors.nonEmpty(listFrom(value()));
    } 

    /**
     * 
     * @param minSize
     * @param maxSize
     * @return 
     */
    @Override
    public ListAccessor<V> sizedList(final int minSize, final int maxSize) {
        return Accessors.sized(listFrom(value()), minSize, maxSize);
    } 

    /**
     * 
     * @return 
     */
    @Override
    public ListAccessor<V> nonMultipleList() {
        return Accessors.nonMultiValued(listFrom(value()));
    } 

    /**
     * 
     * @return 
     */
    @Override
    public MapAccessor<K, V> map() {
        return mapFrom(value());
    } 

    /**
     * 
     * @return 
     */
    @Override
    public MapAccessor<K, V> nonEmptyMap() {
        return Accessors.nonEmpty(mapFrom(value()));
    }    
    
    /**
     * 
     * @param <T>
     * @param <U>
     * @param value
     * @return 
     */
    public static <T, U> ListAccessor<U> listFrom(final Object value) {
        if (value == null) {
            return Accessors.empty();
        }
        if (value instanceof ListAccessor acc) {
            return acc;
        }
        if (value instanceof java.util.List list) {
            return Accessors.of(list);
        }
        if (value instanceof Seq seq) {
            return VavrAccessors.of(seq);
        }
        if (value instanceof Object[]) {
            return CoreAccessors.of((U[])value);
        }
        if (value instanceof CharSequence
                || value instanceof Number
                || value instanceof Boolean
                || value instanceof java.util.Date
                || value instanceof Instant
                || value instanceof TimeValue) {
            return CollectionAccessors.of(Arrays.asList((U)value));
        }
        // Includes Option()
        if (value instanceof Value) {
            final Value<U> val = (Value<U>)value;
            if (val.isEmpty()) {
                return Accessors.empty(); // .empty();
            }
            if (val.isSingleValued()) {
                return CollectionAccessors.of(Arrays.asList(val.get()));
            } 
        }
        if (value instanceof Iterable itr) {
            return VavrAccessors.of(io.vavr.collection.Array.ofAll(itr));
            // return ListMutator(StreamIterables.of(Iterable.class.cast(value)).toVector());
        }
        if (value instanceof Iterator itr) {
            return VavrAccessors.of(io.vavr.collection.Array.ofAll(IterableAccessors.ofAll(itr))); 
            // return ListMutator(StreamIterables.of(Iterator.class.cast(value)).toVector());
        }
        if (value instanceof Enumeration elem) {
            return VavrAccessors.of(io.vavr.collection.Array.ofAll(IterableAccessors.ofAll(elem))); 
            // return ListMutator(StreamIterables.of(Enumeration.class.cast(value)).toVector());
        }
        if (value instanceof Optional) {
            final Optional<U> val = (Optional<U>)value;
            if (val.isPresent()) {
                return CollectionAccessors.of(Arrays.asList(val.get()));
            } else {
                return Accessors.empty();
            }
        }
        /*
        if (value instanceof java.util.Map 
                || value instanceof io.vavr.collection.Map) {
            throw new UnsupportedOperationException(value.getClass().getName());
        } */
        throw new UnsupportedOperationException(String.format("Could not convert %s (%s) to ListMutator", value, value.getClass().getName()));
    }

    /**
     * 
     * @param <T>
     * @param <U>
     * @param value
     * @return 
     */
    public static <T, U> MapAccessor<T, U> mapFrom(final Object value) {
        if (value == null) {
            return Accessors.empty();
        }
        throw new UnsupportedOperationException(value.getClass().getName() + " could not be converted to MapAccessor: " + value);
    }

    /**
     * 
     * @param <V>
     * @param <T>
     * @param <U>
     * @return 
     */
    public static <V, T, U> Function1<V, AccessorSelector<T, U>> asAccessor() {
        return (final V val) -> {
            return new DefaultAccessorSelector<Object, Object, T, U>() {
                @Override
                protected Object key() {
                    throw new UnsupportedOperationException("key()"); 
                }

                @Override
                protected Object value() {
                    return val;
                }
            };
        };
    }

    /**
     * 
     * @param <X>
     * @param <T>
     * @param <U>
     * @return 
     */
    public static <X, T, U> Function1<X, ListAccessor<U>> asListAccessor() {
        return (final X x) -> {
            return listFrom(x);
        };
    }

    /**
     * 
     * @param <X>
     * @param <T>
     * @param <U>
     * @return 
     */
    public static <X, T, U> Function1<X, MapAccessor<T, U>> asMapMutator() {
        return (final X x) -> {
            return mapFrom(x);
        };
    }

}
