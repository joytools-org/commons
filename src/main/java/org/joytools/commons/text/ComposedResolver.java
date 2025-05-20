/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.text;

import static io.vavr.API.None;
import io.vavr.control.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import org.joytools.commons.mappers.ComboMapper;
import org.joytools.commons.mappers.Mappers;

/**
 *
 * @author AndreaR
 * @param <T>
 */
@Deprecated
public class ComposedResolver<T> implements Function<String, Option<T>> {
    
    /**
     * 
     * @return 
     */
    public static ComposedResolver create() {
        return new ComposedResolver();
    }
    
    /**
     * 
     * @param <Z>
     * @param clazz
     * @return 
     */
    public static <Z> ComposedResolver create(final Class<Z> clazz) {
        return new ComposedResolver<Z>();
    }

    /**
     * 
     * @param map
     * @return 
     */
    public ComposedResolver add(final java.util.Map<String, T> map) {
        if (map != null) {
            return add(Mappers.forMap(map));
        }
        return this;
    }
    
    /**
     * 
     * @param map
     * @param prefixes
     * @return 
     */
    public ComposedResolver add(final java.util.Map<String, T> map,
            final String... prefixes) {
        if (map != null) {
            return add(Resolvers.forMap(map, prefixes));
        }
        return this;
    }

    /**
     * 
     * @param maps
     * @return 
     */
    public ComposedResolver addAll(final java.util.Map<String, T>... maps) {
        if (maps == null || maps.length == 0) {
            return this;
        }
        return add(Mappers.forMaps(maps));
    }

    /**
     * 
     * @param elements
     * @return 
     */ 
    /* public ComposedResolver2 add(final String... elements) {
        if (elements == null || elements.length == 0) {
            return this;
        }
        return add(Resolvers2.forMap(elements));
    } */

    /**
     * 
     * @param map
     * @return 
     */
    public ComposedResolver addIgnoreCase(final java.util.Map<String, T> map) {
        if (map != null) {
            return add(Mappers.forMapIgnoreCase(map));
        }
        return this;
    }

    /**
     * 
     * @param map
     * @param prefixes
     * @return 
     */
    public ComposedResolver addIgnoreCase(final java.util.Map<String, T> map,
            final String... prefixes) {
        if (map != null) {
            return add(Resolvers.forMapIgnoreCase(map, prefixes));
        }
        return this;
    }

    /**
     * 
     * @param maps
     * @return 
     */
    public ComposedResolver addAllIgnoreCase(final java.util.Map<String, T>... maps) {
        if (maps == null || maps.length == 0) {
            return this;
        }
        return add(Mappers.forMapsIgnoreCase(maps));
    }

    /**
     * 
     * @param <R>
     * @param <F>
     * @param func
     * @return 
     */
    public <R extends T, F extends Function<String, Option<R>>> ComposedResolver add(final F func) {
        if (func != null) {
            /* final Function<String, Option<T>> fobj = (final String propName) -> {
                return PropertyString.resolve0AsOptionString(propName, func);
            }; 
            resolvers.add(fobj); */
            resolvers.add((Function<String, Option<T>>)(Function)func);
        }
        return this;
    }

    /**
     * 
     * @param <R>
     * @param mapper
     * @return 
     */
    public <R extends T> ComposedResolver add(final ComboMapper<String, R> mapper) {
        if (mapper != null) {
            add(mapper.mapper());
        }
        return this;
    }

    /**
     * 
     * @param <R>
     * @param <F>
     * @param func
     * @param prefixes
     * @return 
     */
    public <R extends T, F extends Function<String, Option<R>>> ComposedResolver add(final F func,
            final String... prefixes) {
        if (func != null) {
            /*
            final Function<String, Option<String>> fobj = (final String propName) -> {
                return PropertyString.resolve0AsOptionString(propName, func);
            };
            resolvers.add(Resolvers2.withPrefix(fobj, prefixes));*/
            resolvers.add(Resolvers.withPrefix((Function<String, Option<T>>)(Function)func, prefixes));
        }
        return this;
    }

    /**
     * 
     * @param <R>
     * @param mapper
     * @param prefixes
     * @return 
     */
    public <R extends T> ComposedResolver add(final ComboMapper<String, R> mapper,
            final String... prefixes) {
        if (mapper != null) {
            add(mapper.mapper(), prefixes);
        }
        return this;
    }

    /**
     * 
     * @param func
     * @param prefixes
     * @return 
     */
    public ComposedResolver addIgnoreCase(final Function<String, Option<T>> func,
            final String... prefixes) {
        if (func == null) {
            return this;
        }
        return add(Resolvers.withPrefixIgnoreCase(func, prefixes));
    }

    /**
     * 
     * @param funcs
     * @return 
     */
    public ComposedResolver addAll(final Function<String, Option<T>>... funcs) {
        if (funcs == null || funcs.length == 0) {
            return this;
        }
        for (final Function<String, Option<T>> func : funcs) {
            add(func);
        }
        return this;
    }

    /**
     * 
     * @param propName
     * @return 
     */
    @Override
    public Option<T> apply(final String propName) {
        for (final Function<String, Option<T>> f : resolvers) {
            final Option<T> resolved = f.apply(propName);
            if (resolved.isDefined()) {
                return resolved;
            }
        }
        return None();
    }

    private final List<Function<String, Option<T>>> resolvers = new ArrayList();
    
}
