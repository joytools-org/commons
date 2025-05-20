/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.text;

import io.vavr.Value;
import java.util.function.Function;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.mappers.Mappers;

public class SimplePropertyString {

    /**
     *
     * @param str
     */
    public SimplePropertyString(final CharSequence str) {
        propStr = PropertyStringBuilder.of(str).build();
    }

    /**
     * 
     * @param str
     * @return 
     */
    public static SimplePropertyString of(final CharSequence str) {
        return new SimplePropertyString(str);
    }
    
    /**
     * 
     * @return 
     */
    public String resolved() {
        if (isResolved()) {
            return toString();
        }
        throw new IllegalStateException("Not resolved: " + propertySet());
    }
            
    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return propStr.toString();
    }

    /**
     *
     * @return Set
     */
    public ListOrderedSet<String> propertySet() {
        return propStr.propertySet();
    }

    /**
     * 
     * @return 
     */
    public ListOrderedSet<String> propertySetIgnoreCase() {
        return propStr.propertySetIgnoreCase();
    }

    /**
     *
     * @return boolean
     */
    public boolean isResolved() {
        return propertySet().isEmpty();
    }

    /**
     *
     * @param <T>
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyString reduce(final java.util.Map<String, T> map) {
        return new SimplePropertyString(doReduce(map, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param escapeMode
     * @return 
     */
    public <T> SimplePropertyString reduce(final java.util.Map<String, T> map, 
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyString(doReduce(map, escapeMode));
    }

    /**
     *
     * @param <T>
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyString reduce(final io.vavr.collection.Map<String, T> map) {
        return new SimplePropertyString(doReduce(map, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param escapeMode
     * @return 
     */
    public <T> SimplePropertyString reduce(final io.vavr.collection.Map<String, T> map, 
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyString(doReduce(map, escapeMode));
    }

    /**
     *
     * @param <T>
     * @param acc Map
     * @return String
     */
    public <T> SimplePropertyString reduce(final MapAccessor<String, T> acc) {
        return new SimplePropertyString(doReduce(acc, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param <T>
     * @param acc
     * @param escapeMode
     * @return 
     */
    public <T> SimplePropertyString reduce(final MapAccessor<String, T> acc, 
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyString(doReduce(acc, escapeMode));
    }

    /**
     *
     * @param <T>
     * @param <V>
     * @param func
     * @return String
     */
    public <T, V extends Value<T>> SimplePropertyString reduce(final Function<String, V> func) {
        return new SimplePropertyString(doReduce(func, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param <T>
     * @param <V>
     * @param func
     * @param escapeMode
     * @return 
     */
    public <T, V extends Value<T>> SimplePropertyString reduce(final Function<String, V> func,
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyString(doReduce(func, escapeMode));
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <V1>
     * @param <V2>
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2, V1 extends Value<V1>, V2 extends Value<V2>> SimplePropertyString reduce(final Function<String, V1> rawFunc, 
            final Function<String, V2> func) {
        return new SimplePropertyString(doReduce(rawFunc, func, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <V1>
     * @param <V2>
     * @param rawFunc
     * @param func
     * @param escapeMode
     * @return 
     */
    public <T1, T2, V1 extends Value<T1>, V2 extends Value<T2>> SimplePropertyString reduce(
            final Function<String, V1> rawFunc, 
            final Function<String, V2> func,
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyString(doReduce(rawFunc, func, escapeMode));
    }

    /**
     *
     * @param <T>
     * @param map Map
     * @return String
     */
    public <T> String replace(final java.util.Map<String, T> map) {
        return doReplace(map, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param escapeMode
     * @return 
     */
    public <T> String replace(final java.util.Map<String, T> map, 
            final PropertyString.EscapeMode escapeMode) {
        return doReplace(map, escapeMode);
    }

    /**
     *
     * @param <T>
     * @param map Map
     * @return String
     */
    public <T> String replace(final io.vavr.collection.Map<String, T> map) {
        return doReplace(map, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param escapeMode
     * @return 
     */
    public <T> String replace(final io.vavr.collection.Map<String, T> map, 
            final PropertyString.EscapeMode escapeMode) {
        return doReplace(map, escapeMode);
    }

    /**
     *
     * @param <T>
     * @param acc Map
     * @return String
     */
    public <T> String replace(final MapAccessor<String, T> acc) {
        return doReplace(acc, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param <T>
     * @param acc
     * @param escapeMode
     * @return 
     */
    public <T> String replace(final MapAccessor<String, T> acc, 
            final PropertyString.EscapeMode escapeMode) {
        return doReplace(acc, escapeMode);
    }

    /**
     *
     * @param <T>
     * @param <V>
     * @param func
     * @return String
     */
    public <T, V extends Value<T>> String replace(final Function<String, V> func) {
        return doReplace(func, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param <T>
     * @param <V>
     * @param func
     * @param escapeMode
     * @return 
     */
    public <T, V extends Value<T>> String replace(final Function<String, V> func,
            final PropertyString.EscapeMode escapeMode) {
        return doReplace(func, escapeMode);
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <V1>
     * @param <V2>
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2, V1 extends Value<T1>, V2 extends Value<T2>> String replace(final Function<String, V1> rawFunc, 
            final Function<String, V2> func) {
        return doReplace(rawFunc, func, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <V1>
     * @param <V2>
     * @param rawFunc
     * @param func
     * @param escapeMode
     * @return 
     */
    public <T1, T2, V1 extends Value<T1>, V2 extends Value<T2>> String replace(final Function<String, V1> rawFunc, 
            final Function<String, V2> func,
            final PropertyString.EscapeMode escapeMode) {
        return doReplace(rawFunc, func, escapeMode);
    }
    
    /**
     * 
     * @param <T>
     * @param reduce
     * @param resolver
     * @param escapeMode
     * @return 
     */
    protected <T, V extends Value<T>> String apply0(
            final boolean reduce,
            final Function<String, V> resolver,
            final PropertyString.EscapeMode escapeMode) {
        final PropertyString newPropStr = PropertyStringBuilder.of(propStr.toString())
                .escape(escapeMode)
                .resolver(resolver)
                .build();
        if (reduce) {
            return newPropStr.reduce().toString();
        }
        return newPropStr.resolve();
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <V1>
     * @param <V2>
     * @param reduce
     * @param rawResolver
     * @param resolver
     * @param escapeMode
     * @return 
     */
    protected <T1, T2, V1 extends Value<T1>, V2 extends Value<T2>> String apply0(
            final boolean reduce,
            final Function<String, V1> rawResolver,
            final Function<String, V2> resolver,
            final PropertyString.EscapeMode escapeMode) {
        final PropertyString newPropStr = PropertyStringBuilder.of(propStr.toString())
                .escape(escapeMode)
                .rawResolver(rawResolver)
                .resolver(resolver)
                .build();
        if (reduce) {
            return newPropStr.reduce().toString();
        }
        return newPropStr.resolve();
    }

    /**
     * 
     * @param <T>
     * @param <V>
     * @param resolver
     * @param escapeMode
     * @return 
     */
    protected <T, V extends Value<T>> String doReduce(
            final Function<String, V> resolver,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(true, resolver, escapeMode);
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param rawResolver
     * @param resolver
     * @param escapeMode
     * @return 
     */
    protected <T1, T2, V1 extends Value<T1>, V2 extends Value<T2>> String doReduce(
            final Function<String, V1> rawResolver,
            final Function<String, V2> resolver,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(true, rawResolver, resolver, escapeMode);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param escapeMode
     * @return 
     */
    protected <T> String doReduce(
            final java.util.Map<String, T> map,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(true, Mappers.forMapIgnoreCase(map), escapeMode);
        /*
        final PropertyString newPropStr = PropertyStringBuilder.of(propStr.toString())
                .escape(escapeMode)
                .resolver(Mappers.forMapIgnoreCase(props))
                .build();
        if (reduce) {
            return newPropStr.reduce().toString();
        }
        return newPropStr.resolve(); */
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param escapeMode
     * @return 
     */
    protected <T> String doReduce(
            final io.vavr.collection.Map<String, T> map,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(true, Mappers.forMapIgnoreCase(map), escapeMode);
        /*
        final PropertyString newPropStr = PropertyStringBuilder.of(propStr.toString())
                .escape(escapeMode)
                .resolver(Mappers.forMapIgnoreCase(props))
                .build();
        if (reduce) {
            return newPropStr.reduce().toString();
        }
        return newPropStr.resolve();
        */
    }

    /**
     * 
     * @param <T>
     * @param acc
     * @param escapeMode
     * @return 
     */
    protected <T> String doReduce(
            final MapAccessor<String, T> acc,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(true, Mappers.forAccessor(acc), escapeMode);
        /*
        final PropertyString newPropStr = PropertyStringBuilder.of(propStr.toString())
                .escape(escapeMode)
                .resolver(Mappers.forMapIgnoreCase(props))
                .build();
        if (reduce) {
            return newPropStr.reduce().toString();
        }
        return newPropStr.resolve(); */
    }

    /**
     * 
     * @param <T>
     * @param <V>
     * @param resolver
     * @param escapeMode
     * @return 
     */
    protected <T, V extends Value<T>> String doReplace(
            final Function<String, V> resolver,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(false, resolver, escapeMode);
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <V1>
     * @param <V2>
     * @param rawResolver
     * @param resolver
     * @param escapeMode
     * @return 
     */
    protected <T1, T2, V1 extends Value<T1>, V2 extends Value<T2>> String doReplace(
            final Function<String, V1> rawResolver,
            final Function<String, V2> resolver,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(false, rawResolver, resolver, escapeMode);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param escapeMode
     * @return 
     */
    protected <T> String doReplace(
            final java.util.Map<String, T> map,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(false, Mappers.forMapIgnoreCase(map), escapeMode);
        /*
        final PropertyString newPropStr = PropertyStringBuilder.of(propStr.toString())
                .escape(escapeMode)
                .resolver(Mappers.forMapIgnoreCase(props))
                .build();
        if (reduce) {
            return newPropStr.reduce().toString();
        }
        return newPropStr.resolve(); */
    }

    /**
     * 
     * @param <T>
     * @param map
     * @param escapeMode
     * @return 
     */
    protected <T> String doReplace(
            final io.vavr.collection.Map<String, T> map,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(false, Mappers.forMapIgnoreCase(map), escapeMode);
        /*
        final PropertyString newPropStr = PropertyStringBuilder.of(propStr.toString())
                .escape(escapeMode)
                .resolver(Mappers.forMapIgnoreCase(props))
                .build();
        if (reduce) {
            return newPropStr.reduce().toString();
        }
        return newPropStr.resolve();
        */
    }

    /**
     * 
     * @param <T>
     * @param acc
     * @param escapeMode
     * @return 
     */
    protected <T> String doReplace(
            final MapAccessor<String, T> acc,
            final PropertyString.EscapeMode escapeMode) {
        return apply0(false, Mappers.forAccessor(acc), escapeMode);
        /*
        final PropertyString newPropStr = PropertyStringBuilder.of(propStr.toString())
                .escape(escapeMode)
                .resolver(Mappers.forMapIgnoreCase(props))
                .build();
        if (reduce) {
            return newPropStr.reduce().toString();
        }
        return newPropStr.resolve(); */
    }


    private final PropertyString propStr;
    
}
