/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util.lookup;

import io.vavr.control.Option;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public class StringLookupBuilderV1<E> {
    
    /**
     * 
     * @param cs 
     * @param norm 
     */
    public StringLookupBuilderV1(final CaseSensitivity cs,
            final Function<String, String> norm) {
        Objects.requireNonNull(cs, "CaseSensitivity");
        Objects.requireNonNull(norm, "Normalizer");
        m_data = cs.newLinkedMap();
        m_normalizer = norm;
    }
    
    /**
     * 
     * @param ignoreCase 
     * @param norm 
     */
    public StringLookupBuilderV1(final boolean ignoreCase,
            final Function<String, String> norm) {
        this(CaseSensitivity.forIgnoreCase(ignoreCase), norm);
    }
    
    /**
     * 
     * @param cs 
     */
    public StringLookupBuilderV1(final CaseSensitivity cs) {
        this(cs, Function.identity());
    }
    
    /**
     * 
     * @param ignoreCase 
     */
    public StringLookupBuilderV1(final boolean ignoreCase) {
        this(CaseSensitivity.forIgnoreCase(ignoreCase), Function.identity());
    }

    /**
     * 
     * @return 
     */
    public LookupFunction<String, E> build() {
        final Map<String, E> names = CaseSensitivity.fromMap(m_data)
                .newLinkedMap(m_data);
        final Function<String, String> normalizer = m_normalizer;
        final Function<String, Option<E>> mapper = name -> AccessorMethods.get(names, normalizer.apply(name));

        return new LookupFunction<String, E>() {

            @Override
            public E apply(final String name) {
                final E val = names.get(normalizer.apply(name));
                if (val == null) {
                    throw new IllegalArgumentException(name);
                }
                return val;
            }

            @Override
            public E applyOrNull(final String name) {
                return names.get(normalizer.apply(name));
            }

            @Override
            public E applyOrElse(final String name, final E def) {
                return ObjectUtils.coalesce(names.get(normalizer.apply(name)), def);
            }
            
            @Override
            public Function<String, Option<E>> asMapper() {
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
    public StringLookupBuilderV1<E> add(final E item, final String name) {
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
    public StringLookupBuilderV1<E> addAll(final E item, final String... names) {
        Objects.requireNonNull(item, "Item");
        Objects.requireNonNull(names, "Names");
        for (final String name : names) {
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
    public StringLookupBuilderV1<E> addAll(final E item, final Iterable<String> names) {
        Objects.requireNonNull(item, "Item");
        Objects.requireNonNull(names, "Names");
        for (final String name : names) {
            add0(item, name);
        }
        return this;
    }

    /**
     * 
     * @param item 
     * @param name 
     */
    protected void add0(final E item, final String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Empty name");
        }
        final String n = m_normalizer.apply(name);
        if (StringUtils.isEmpty(n)) {
            throw new IllegalArgumentException("Empty normalized name");
        }
        final E val = m_data.get(n);
        if (val != null && val != item && !val.equals(item)) {
            throw new IllegalArgumentException("Tried to assign to " + item + " a name that is already in use: " + name);
        }
        m_data.put(n, item);
    }

    /**
     * 
     * @param item
     * @param names 
     */
    protected void addAll0(final E item, final String... names) {
        for (final String name : names) {
            add0(item, name);
        }
    }

    /**
     * 
     * @param item
     * @param names 
     */
    protected void addAll0(final E item, final Iterable<String> names) {
        for (final String name : names) {
            add0(item, name);
        }
    }

    private final Map<String, E> m_data;
    
    private final Function<String, String> m_normalizer;
    
}
