/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.interop;

import io.vavr.control.Option;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import org.joytools.commons.collections.IterableUtils;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class Element {
    
    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function<Iterable<V>, V> only() {
        return (Function<Iterable<V>, V>)(Function)ONLY;
    }
    
    /**
     * 
     */
    private final static Function<Iterable<Object>, Object> ONLY = IterableUtils::onlyElement;

    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function<Iterable<V>, Option<V>> first() {
        return elements -> IterableAccessors.ofAll(Objects.requireNonNull(elements, "Elements")).first();
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function<Iterable<V>, V> firstOrFail() {
        return IterableUtils::firstOrFail;
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function<Iterable<V>, V> firstOrNull() {
        return IterableUtils::firstOrNull;
    }

    /**
     * 
     * @param <V>
     * @param def
     * @return 
     */
    public static <V> Function<Iterable<V>, V> firstOrElse(final V def) {
        return elements -> IterableUtils.firstOrElse(elements, def);
    }

    /**
     * 
     * @param <V>
     * @param def
     * @return 
     */
    public static <V> Function<Iterable<V>, V> firstOrElse(final Supplier<V> def) {
        return elements -> IterableUtils.firstOrElse(elements, def);
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function<Iterable<V>, Option<V>> last() {
        return elements -> IterableAccessors.ofAll(Objects.requireNonNull(elements, "Elements")).last();
    }
   
    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function<Iterable<V>, V> lastOrNull() {
        return elements -> IterableAccessors.ofAll(Objects.requireNonNull(elements, "Elements")).lastOrNull();
    }

    /**
     * 
     * @param <V>
     * @param def
     * @return 
     */
    public static <V> Function<Iterable<V>, V> lastOrElse(final V def) {
        return elements -> IterableAccessors.ofAll(Objects.requireNonNull(elements, "Elements")).lastOrElse(def);
    }

    /**
     * 
     * @param <V>
     * @param supplier
     * @return 
     */
    public static <V> Function<Iterable<V>, V> lastOrElse(final Supplier<V> supplier) {
        return elements -> IterableAccessors.ofAll(Objects.requireNonNull(elements, "Elements")).lastOrElse(supplier);
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> Function<Iterable<V>, V> lastOrFail() {
        return elements -> IterableAccessors.ofAll(Objects.requireNonNull(elements, "Elements")).lastOrFail();
    }

}
