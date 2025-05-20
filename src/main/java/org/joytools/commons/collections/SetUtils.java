/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Supplier;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class SetUtils {
    
    /**
     * 
     * @param <E>
     * @param <S>
     * @param from
     * @param to 
     * @return  
     */
    public static <E, S extends Set<E>> S addAllUnique(final Iterable<E> from, final S to) {
        if (from == null || to == null) {
            return to;
        }
        final Comparator<E> compFrom = comparatorOf(from);
        final Comparator<E> compTo = comparatorOf(to);
        final SortedSet<E> toSortedSet = compFrom == compTo ? null : new TreeSet<>(compTo);
        List<E> duplicated = null; 
        for (final E e : from) {
            if (toSortedSet != null && !toSortedSet.add(e)) {
                if (duplicated == null) {
                    duplicated = new ArrayList<>();
                }
                duplicated.add(e);
            } else {
                to.add(e);
            }
        }
        if (duplicated != null) {
            throw new IllegalArgumentException("Duplicated keys on destination set: " + 
                    StringUtils.join(duplicated, ", ") + ". Check case sensitivity on source and destination.");
        }
        return to;
    }    
            
    /**
     *
     * @param set
     * @return
     */
    public static <E> Comparator<E> comparatorOf(final Iterable<E> set) {
        Comparator c = null;
        if (set != null && (set instanceof SortedSet sortedSet)) {
            c = sortedSet.comparator();
        }
        if (c == null) {
            return (Comparator<E>)Comparator.naturalOrder();
        }
        return c;
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param elements
     * @return 
     */
    public static <T, E extends T> Set<T> build(final E... elements) {
        return build(new HashSet(), elements);
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param elements
     * @return 
     */
    public static <T, E extends T> Set<T> buildLinked(final E... elements) {
        return build(new LinkedHashSet(), elements);
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param elements
     * @return 
     */
    public static <T, E extends T> SortedSet<T> buildSorted(final E... elements) {
        return build(new TreeSet(), elements);
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param comparator
     * @param elements
     * @return 
     */
    public static <T, E extends T> SortedSet<T> buildSorted(final Comparator<T> comparator, final E... elements) {
        return build(new TreeSet(comparator), elements);
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param <M>
     * @param set
     * @param elements
     * @return 
     */
    public static <T, E extends T, M extends Set<T>> M build(final M set, final E... elements) {
        if (set != null && elements != null) {
            for (final E element : elements) {
                set.add(element);
            }
        }
        return set;
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param <M>
     * @param supp
     * @param elements
     * @return 
     */
    public static <T, E extends T, M extends Set<T>> M build(final Supplier<M> supp, final E... elements) {
        return build(supp.get(), elements);
    }
    
}
