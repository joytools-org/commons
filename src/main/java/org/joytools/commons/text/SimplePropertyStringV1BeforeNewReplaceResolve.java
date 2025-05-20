/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.text;

import io.vavr.control.Option;
import java.util.function.Function;
import org.apache.commons.collections4.set.ListOrderedSet;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.mappers.Mappers;

public class SimplePropertyStringV1BeforeNewReplaceResolve {

    /**
     *
     * @param str
     */
    public SimplePropertyStringV1BeforeNewReplaceResolve(final CharSequence str) {
        propStr = PropertyStringBuilder.of(str).build();
    }

    /**
     * 
     * @param str
     * @return 
     */
    public static SimplePropertyStringV1BeforeNewReplaceResolve of(final CharSequence str) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(str);
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
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final java.util.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param map
     * @param escapeMode
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final java.util.Map<String, T> map, 
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, escapeMode));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final io.vavr.collection.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param map
     * @param escapeMode
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final io.vavr.collection.Map<String, T> map, 
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, escapeMode));
    }

    /**
     *
     * @param acc Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final MapAccessor<String, T> acc) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, acc, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param acc
     * @param escapeMode
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final MapAccessor<String, T> acc, 
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, acc, escapeMode));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceHtml(final java.util.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.HTML));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceHtml(final io.vavr.collection.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.HTML));
    }

    /**
     *
     * @param acc Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceHtml(final MapAccessor<String, T> acc) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, acc, PropertyString.EscapeMode.HTML));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceXml(final java.util.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.XML));
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceXml(final io.vavr.collection.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.XML));
    }

    /**
     * 
     * @param map
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceCsv(final java.util.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.CSV));
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceCsv(final io.vavr.collection.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.CSV));
    }

    /**
     * 
     * @param <T>
     * @param acc
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceCsv(final MapAccessor<String, T> acc) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, acc, PropertyString.EscapeMode.CSV));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceSql(final java.util.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.SQL));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceSql(final io.vavr.collection.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.SQL));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceSql(final MapAccessor<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.SQL));
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final Function<String, Option<T>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, func, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param func
     * @param escapeMode
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final Function<String, Option<T>> func,
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, func, escapeMode));
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> SimplePropertyStringV1BeforeNewReplaceResolve reduce(final Function<String, Option<T1>> rawFunc, 
            final Function<String, Option<T2>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, rawFunc, func, PropertyString.EscapeMode.NONE));
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @param escapeMode
     * @return 
     */
    public <T1, T2> SimplePropertyStringV1BeforeNewReplaceResolve reduce(
            final Function<String, Option<T1>> rawFunc, 
            final Function<String, Option<T2>> func,
            final PropertyString.EscapeMode escapeMode) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, rawFunc, func, escapeMode));
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceHtml(final Function<String, Option<T>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, func, PropertyString.EscapeMode.HTML));
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> SimplePropertyStringV1BeforeNewReplaceResolve reduceHtml(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, rawFunc, func, PropertyString.EscapeMode.HTML));
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceXml(final Function<String, Option<T>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, func, PropertyString.EscapeMode.XML));
    }

    /**
     * 
     * @param func
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceCsv(final Function<String, Option<T>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, func, PropertyString.EscapeMode.CSV));
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> SimplePropertyStringV1BeforeNewReplaceResolve reduceXml(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, rawFunc, func, PropertyString.EscapeMode.XML));
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> SimplePropertyStringV1BeforeNewReplaceResolve reduceCsv(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, rawFunc, func, PropertyString.EscapeMode.CSV));
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceJson(final Function<String, Option<T>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, func, PropertyString.EscapeMode.JSON));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceJson(final java.util.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.JSON));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceJson(final io.vavr.collection.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.JSON));
    }

    /**
     *
     * @param acc Map
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceJson(final MapAccessor<String, T> acc) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, acc, PropertyString.EscapeMode.JSON));
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> SimplePropertyStringV1BeforeNewReplaceResolve reduceJson(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, rawFunc, func, PropertyString.EscapeMode.JSON));
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceSql(final Function<String, Option<T>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, func, PropertyString.EscapeMode.SQL));
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> SimplePropertyStringV1BeforeNewReplaceResolve reduceSql(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, rawFunc, func, PropertyString.EscapeMode.SQL));
    }

    /**
     * 
     * @param map
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceUrl(final java.util.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.URL));
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceUrl(final io.vavr.collection.Map<String, T> map) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, map, PropertyString.EscapeMode.URL));
    }

    /**
     * 
     * @param <T>
     * @param acc
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceUrl(final MapAccessor<String, T> acc) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, acc, PropertyString.EscapeMode.URL));
    }

    /**
     * 
     * @param func
     * @return 
     */
    public <T> SimplePropertyStringV1BeforeNewReplaceResolve reduceUrl(final Function<String, Option<T>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, func, PropertyString.EscapeMode.URL));
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> SimplePropertyStringV1BeforeNewReplaceResolve reduceUrl(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return new SimplePropertyStringV1BeforeNewReplaceResolve(doReduce(true, rawFunc, func, PropertyString.EscapeMode.URL));
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> String replace(final java.util.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param map
     * @param escapeMode
     * @return 
     */
    public <T> String replace(final java.util.Map<String, T> map, 
            final PropertyString.EscapeMode escapeMode) {
        return doReduce(false, map, escapeMode);
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> String replace(final io.vavr.collection.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param map
     * @param escapeMode
     * @return 
     */
    public <T> String replace(final io.vavr.collection.Map<String, T> map, 
            final PropertyString.EscapeMode escapeMode) {
        return doReduce(false, map, escapeMode);
    }

    /**
     *
     * @param acc Map
     * @return String
     */
    public <T> String replace(final MapAccessor<String, T> acc) {
        return doReduce(false, acc, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param acc
     * @param escapeMode
     * @return 
     */
    public <T> String replace(final MapAccessor<String, T> acc, 
            final PropertyString.EscapeMode escapeMode) {
        return doReduce(false, acc, escapeMode);
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> String replaceHtml(final java.util.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.HTML);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    public <T> String replaceHtml(final io.vavr.collection.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.HTML);
    }

    /**
     *
     * @param acc Map
     * @return String
     */
    public <T> String replaceHtml(final MapAccessor<String, T> acc) {
        return doReduce(false, acc, PropertyString.EscapeMode.HTML);
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> String replaceXml(final java.util.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.XML);
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> String replaceXml(final io.vavr.collection.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.XML);
    }

    /**
     *
     * @param acc Map
     * @return String
     */
    public <T> String replaceXml(final MapAccessor<String, T> acc) {
        return doReduce(false, acc, PropertyString.EscapeMode.XML);
    }

    /**
     * 
     * @param map
     * @return 
     */
    public <T> String replaceCsv(final java.util.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.CSV);
    }

    /**
     * 
     * @param map
     * @return 
     */
    public <T> String replaceCsv(final io.vavr.collection.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.CSV);
    }

    /**
     * 
     * @param acc
     * @return 
     */
    public <T> String replaceCsv(final MapAccessor<String, T> acc) {
        return doReduce(false, acc, PropertyString.EscapeMode.CSV);
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> String replaceSql(final java.util.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.SQL);
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> String replaceSql(final io.vavr.collection.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.SQL);
    }

    /**
     *
     * @param acc Map
     * @return String
     */
    public <T> String replaceSql(final MapAccessor<String, T> acc) {
        return doReduce(false, acc, PropertyString.EscapeMode.SQL);
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> String replace(final Function<String, Option<T>> func) {
        return doReduce(false, func, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param func
     * @param escapeMode
     * @return 
     */
    public <T> String replace(final Function<String, Option<T>> func,
            final PropertyString.EscapeMode escapeMode) {
        return doReduce(false, func, escapeMode);
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> String replace(final Function<String, Option<T1>> rawFunc, 
            final Function<String, Option<T2>> func) {
        return doReduce(false, rawFunc, func, PropertyString.EscapeMode.NONE);
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @param escapeMode
     * @return 
     */
    public <T1, T2> String replace(final Function<String, Option<T1>> rawFunc, 
            final Function<String, Option<T2>> func,
            final PropertyString.EscapeMode escapeMode) {
        return doReduce(false, rawFunc, func, escapeMode);
    }
    
    /**
     *
     * @param func
     * @return String
     */
    public <T> String replaceHtml(final Function<String, Option<T>> func) {
        return doReduce(false, func, PropertyString.EscapeMode.HTML);
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> String replaceHtml(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return doReduce(false, rawFunc, func, PropertyString.EscapeMode.HTML);
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> String replaceXml(final Function<String, Option<T>> func) {
        return doReduce(false, func, PropertyString.EscapeMode.XML);
    }

    /**
     * 
     * @param func
     * @return 
     */
    public <T> String replaceCsv(final Function<String, Option<T>> func) {
        return doReduce(false, func, PropertyString.EscapeMode.CSV);
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> String replaceXml(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return doReduce(false, rawFunc, func, PropertyString.EscapeMode.XML);
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> String replaceCsv(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return doReduce(false, rawFunc, func, PropertyString.EscapeMode.CSV);
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> String replaceSql(final Function<String, Option<T>> func) {
        return doReduce(false, func, PropertyString.EscapeMode.SQL);
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> String replaceSql(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return doReduce(false, rawFunc, func, PropertyString.EscapeMode.SQL);
    }

    /**
     *
     * @param func
     * @return String
     */
    public <T> String replaceJson(final Function<String, Option<T>> func) {
        return doReduce(false, func, PropertyString.EscapeMode.JSON);
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> String replaceJson(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return doReduce(false, rawFunc, func, PropertyString.EscapeMode.JSON);
    }

    /**
     *
     * @param map Map
     * @return String
     */
    public <T> String replaceJson(final java.util.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.JSON);
    }

    /**
     * 
     * @param <T>
     * @param map
     * @return 
     */
    public <T> String replaceJson(final io.vavr.collection.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.JSON);
    }

    /**
     *
     * @param acc Map
     * @return String
     */
    public <T> String replaceJson(final MapAccessor<String, T> acc) {
        return doReduce(false, acc, PropertyString.EscapeMode.JSON);
    }

    /**
     * 
     * @param map
     * @return 
     */
    public <T> String replaceUrl(final java.util.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.URL);
    }

    /**
     * 
     * @param map
     * @return 
     */
    public <T> String replaceUrl(final io.vavr.collection.Map<String, T> map) {
        return doReduce(false, map, PropertyString.EscapeMode.URL);
    }

    /**
     * 
     * @param acc
     * @return 
     */
    public <T> String replaceUrl(final MapAccessor<String, T> acc) {
        return doReduce(false, acc, PropertyString.EscapeMode.URL);
    }

    /**
     * 
     * @param func
     * @return 
     */
    public <T> String replaceUrl(final Function<String, Option<T>> func) {
        return doReduce(false, func, PropertyString.EscapeMode.URL);
    }

    /**
     * 
     * @param rawFunc
     * @param func
     * @return 
     */
    public <T1, T2> String replaceUrl(final Function<String, Option<T1>> rawFunc, final Function<String, Option<T2>> func) {
        return doReduce(false, rawFunc, func, PropertyString.EscapeMode.URL);
    }

    /**
     * 
     * @param reduce
     * @param map
     * @param escapeMode
     * @return 
     */
    protected <T> String doReduce(
            final boolean reduce,
            final java.util.Map<String, T> map,
            final PropertyString.EscapeMode escapeMode) {
        return doReduce(reduce, Mappers.forMapIgnoreCase(map), escapeMode);
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
     * @param reduce
     * @param map
     * @param escapeMode
     * @return 
     */
    protected <T> String doReduce(
            final boolean reduce,
            final io.vavr.collection.Map<String, T> map,
            final PropertyString.EscapeMode escapeMode) {
        return doReduce(reduce, Mappers.forMapIgnoreCase(map), escapeMode);
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
     * @param reduce
     * @param acc
     * @param escapeMode
     * @return 
     */
    protected <T> String doReduce(
            final boolean reduce,
            final MapAccessor<String, T> acc,
            final PropertyString.EscapeMode escapeMode) {
        return doReduce(reduce, Mappers.forAccessor(acc), escapeMode);
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
     * @param reduce
     * @param resolver
     * @param escapeMode
     * @return 
     */
    protected <T> String doReduce(
            final boolean reduce,
            final Function<String, Option<T>> resolver,
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
     * @param reduce
     * @param rawResolver
     * @param resolver
     * @param escapeMode
     * @return 
     */
    protected <T1, T2> String doReduce(
            final boolean reduce,
            final Function<String, Option<T1>> rawResolver,
            final Function<String, Option<T2>> resolver,
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

    private final PropertyString propStr;
    
}
