/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.text;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.Function1;
import io.vavr.Value;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.util.Objects;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.joytools.commons.lang.ArrayUtils;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.mappers.Mappers;
import static org.joytools.vavr.APIX.SingleValue;

/**
 *
 * @author AndreaR
 */
public class Resolvers {
    
    /**
     * 
     * @return 
     */
    @Deprecated
    public static ComposedResolver<Object> composed() {
        return ComposedResolver.create();
    }

    /**
     * 
     * @param clazz
     * @return 
     */
    @Deprecated
    public static <T> ComposedResolver<T> composed(final Class<T> clazz) {
        return ComposedResolver.create(clazz);
    }
    
    /**
     * 
     * @param prefixes
     * @return 
     */
    protected static Seq<String> buildPrefixes(final String... prefixes) {
        final Seq<String> pref = Vector.of(prefixes);
        if (pref.isEmpty()) {
            throw new IllegalArgumentException("No prefix specified");
        }
        final int emptyCount = pref.filter(StringPredicates.isEmpty()).size();
        if (emptyCount > 0) {
            throw new IllegalArgumentException(emptyCount + " prefixes are null or empty");
        }
        return pref;
    }
    
    /**
     * 
     * @param <T>
     * @param func
     * @param startPrefix
     * @param prefixes
     * @return 
     */
    public static <T> Function1<String, Option<T>> withPrefix(final Function<String, Option<T>> func, 
            final String... prefixes) {
        Objects.requireNonNull(func, "Function");
        final Seq<String> pref = buildPrefixes(prefixes);
        return s -> {
            for (final String p : pref) {
                if (StringUtils.startsWith(s, p)) {
                    return func.apply(s.substring(p.length()));
                }
            }
            return None();
        };
    }
    
    /**
     * 
     * @param <T>
     * @param func
     * @param prefixes
     * @return 
     */
    public static <T> Function1<String, Option<T>> withPrefixIgnoreCase(final Function<String, Option<T>> func, 
            final String... prefixes) {
        Objects.requireNonNull(func, "Function");
        final Seq<String> pref = buildPrefixes(prefixes);
        return s -> {
            for (final String p : pref) {
                if (StringUtils.startsWithIgnoreCase(s, p)) {
                    return func.apply(s.substring(p.length()));
                }
            }
            return None();
        };
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param prefixes
     * @return 
     */
    public static <T> Function<String, Option<T>> forMap(final java.util.Map<String, T> map,
            final String... prefixes) {
        final Function<String, Option<T>> mapper = Mappers.forMap(map);
        if (ArrayUtils.isEmpty(prefixes)) {
            return mapper;
        }
        return withPrefix(mapper, prefixes);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param prefixes
     * @return 
     */
    public static <T> Function<String, Option<T>> forMap(final io.vavr.collection.Map<String, T> map,
            final String... prefixes) {
        final Function<String, Option<T>> mapper = Mappers.forMap(map);
        if (ArrayUtils.isEmpty(prefixes)) {
            return mapper;
        }
        return withPrefix(mapper, prefixes);
    }

    /**
     * 
     * @param <T>
     * @param maps
     * @return 
     */
    @Deprecated
    static <T> Function<String, Option<T>> old_forMapsIgnoreCase(final java.util.Map<String, T>... maps) {
        return Mappers.forMapsIgnoreCase(maps);
    }

    /**
     * 
     * @param <T>
     * @param maps
     * @return 
     */
    @Deprecated
    static <T> Function<String, Option<T>> old_forMapsIgnoreCase(final io.vavr.collection.Map<String, T>... maps) {
        return Mappers.forMapsIgnoreCase(maps);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    @Deprecated
    static <T> Function<String, Option<T>> old_forMapIgnoreCase(final java.util.Map<String, T> map) {
        Objects.requireNonNull(map, "Map");
        return Mappers.forMapsIgnoreCase(map);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param prefixes
     * @return 
     */
    public static <T> Function<String, Option<T>> forMapIgnoreCase(final java.util.Map<String, T> map,
            final String... prefixes) {
        final Function<String, Option<T>> mapper = Mappers.forMapIgnoreCase(map);
        if (ArrayUtils.isEmpty(prefixes)) {
            return mapper;
        }
        return withPrefixIgnoreCase(mapper, prefixes);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    @Deprecated
    static <T> Function<String, Option<T>> old_forMapIgnoreCase(final io.vavr.collection.Map<String, T> map) {
        Objects.requireNonNull(map, "Map");
        return Mappers.forMapsIgnoreCase(map);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param prefixes
     * @return 
     */
    public static <T> Function<String, Option<T>> forMapIgnoreCase(final io.vavr.collection.Map<String, T> map,
            final String... prefixes) {
        final Function<String, Option<T>> mapper = Mappers.forMapIgnoreCase(map);
        if (ArrayUtils.isEmpty(prefixes)) {
            return mapper;
        }
        return withPrefixIgnoreCase(mapper, prefixes);
    }

    /**
     * 
     * @param <T>
     * @param <V>
     * @param <F>
     * @param mapper
     * @return 
     */
    public static <T, V extends Value<T>, F extends Function<String, V>> Function<String, Option<T>> toOptionResolver(final F mapper) {
        if (mapper == null) {
            return Mappers.empty();
        }
        return key -> {
            final V res = Objects.requireNonNull(mapper.apply(key), "Mapper returned null for key " + key);
            if (res instanceof Option opt) {
                return opt;
            }
            if (SingleValue(res).isEmpty()) {
                return None();
            }
            return Some(res.get());
        };
    }

    /**
     * 
     * @param <T>
     * @param <V>
     * @param <F>
     * @param func
     * @return 
     */
    public static <T, V extends Value<T>, F extends Function<String, V>> Function<String, Value<Object>> toObjectResolver(final F func) {
        if (func == null) {
            return (Function<String, Value<Object>>)(Function)Mappers.empty();
        }
        return (Function<String, Value<Object>>)(Function)func;
    }
    
    /**
     * 
     * @param <T>
     * @param <F>
     * @param func
     * @return 
     */
    public static <T, F extends Function<String, Option<T>>> Function<String, Option<String>> toStringResolver(final F func) {
        if (func == null) {
            return Mappers.empty();
        }
        return prop -> toResolvedString(func.apply(prop));
    }

    /**
     * 
     * @param <T>
     * @param opt
     * @return 
     */
    public static <T> Option<String> toResolvedString(final Option<T> opt) {
        Objects.requireNonNull(opt, "Option");
        if (opt.isEmpty()) {
            return (Option<String>)opt;
        }
        final Object val = opt.get();
        if (val == null || val instanceof String) {
            return (Option<String>)opt;
        }
        return Some(StringLookups.toLookupString(val));
    }

}
