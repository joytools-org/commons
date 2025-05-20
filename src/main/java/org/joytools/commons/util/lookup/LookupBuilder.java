/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util.lookup;

import io.vavr.control.Option;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public class LookupBuilder<N, E> {
    
    /**
     * 
     * @param emptyMap 
     * @param norm 
     */
    public LookupBuilder(final io.vavr.collection.Map<N, E> emptyMap,
            final Function<N, N> norm) {
        Objects.requireNonNull(emptyMap, "Empty Map");
        Objects.requireNonNull(norm, "Normalizer");
        m_map = emptyMap.take(-1);
        m_normalizer = norm;
    }
    
    /**
     * 
     * @param emptyMap 
     */
    public LookupBuilder(final io.vavr.collection.Map<N, E> emptyMap) {
        this(emptyMap, Function.identity());
    }

    /**
     * 
     */
    public LookupBuilder() {
        this(io.vavr.collection.HashMap.empty(), Function.identity());
    }

    /**
     * 
     * @return 
     */
    public LookupFunction<N, E> build() {
        return build(m_map, m_normalizer);
    }

    /**
     * 
     * @param map
     * @param normalizer
     * @return 
     */
    protected LookupFunction<N, E> build(final io.vavr.collection.Map<N, E> map,
        final Function<N, N> normalizer) {
        final Function<N, Option<E>> mapper = name -> AccessorMethods.get(map, normalizer.apply(name));

        return new LookupFunction<N, E>() {

            @Override
            public E apply(final N name) {
                final E val = applyOrNull(name);
                if (val == null) {
                    throw new NoSuchElementException(StringUtils.toString(name));
                }
                return val;
            }

            @Override
            public E applyOrNull(final N name) {
                return map.getOrElse(normalizer.apply(name), null);
            }

            @Override
            public E applyOrElse(final N name, final E def) {
                final E val = applyOrNull(name);
                return val != null ? val : def;
            }
            
            @Override
            public Function<N, Option<E>> asMapper() {
                return mapper;
            }
            
        };
    }
    
    /**
     * 
     * @param item
     * @param name
     * @return 
     */
    public LookupBuilder<N, E> add(final E item, final N name) {
        Objects.requireNonNull(item, "Item");
        add0(item, name);
        return this;
    }

    /**
     * 
     * @param item
     * @param names
     * @return 
     */
    public LookupBuilder<N, E> addAll(final E item, final N... names) {
        Objects.requireNonNull(item, "Item");
        Objects.requireNonNull(names, "Names");
        for (final N name : names) {
            add0(item, name);
        }
        return this;
    }

    /**
     * 
     * @param item
     * @param names
     * @return 
     */
    public LookupBuilder<N, E> addAll(final E item, final Iterable<N> names) {
        Objects.requireNonNull(item, "Item");
        Objects.requireNonNull(names, "Names");
        for (final N name : names) {
            add0(item, name);
        }
        return this;
    }

    /**
     * 
     * @param item 
     * @param name 
     */
    protected void add0(final E item, final N name) {
        if (ObjectUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Empty name");
        }
        final N n = m_normalizer.apply(name);
        if (ObjectUtils.isEmpty(n)) {
            throw new IllegalArgumentException("Empty normalized name");
        }
        final E val = m_map.getOrElse(n, null);
        if (val != null && val != item && !val.equals(item)) {
            throw new IllegalArgumentException("Tried to assign to " + item + " a name that is already in use: " + name);
        }
        m_map = m_map.put(n, item);
    }

    /**
     * 
     * @param item
     * @param names 
     */
    protected void addAll0(final E item, final N... names) {
        for (final N name : names) {
            add0(item, name);
        }
    }

    /**
     * 
     * @param item
     * @param names 
     */
    protected void addAll0(final E item, final Iterable<N> names) {
        for (final N name : names) {
            add0(item, name);
        }
    }

    private io.vavr.collection.Map<N, E> m_map;
    
    private final Function<N, N> m_normalizer;
    
}
