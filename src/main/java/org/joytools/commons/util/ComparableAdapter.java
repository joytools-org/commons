/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import com.google.common.base.Supplier;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public abstract class ComparableAdapter<E> implements Supplier<E>, Comparable<ComparableAdapter<E>> {

    /**
     * 
     * @param <V>
     * @param value
     * @param comparator
     * @return 
     */
    public static <V> ComparableAdapter<V> of(
            final V value, 
            final Comparator<V> comparator) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(comparator);
        return new ComparableAdapter<V>() {
            @Override
            public V get() {
                return value;
            }

            @Override
            public Comparator<V> comparator() {
                return comparator;
            }
        };
    }
    
    /**
     * 
     * @param <V>
     * @param value
     * @return 
     */
    public static <V> ComparableAdapter<V> of(final V value) {
        Objects.requireNonNull(value);
        return new ComparableAdapter<V>() {
            @Override
            public V get() {
                return value;
            }

            @Override
            public Comparator<V> comparator() {
                return null;
            }
        };
    }

    /**
     * 
     * @return 
     */
    @Override
    public abstract E get();
    
    /**
     * 
     * @return 
     */
    public abstract Comparator<E> comparator();

    /**
     * 
     * @param that
     * @return 
     */
    @Override
    public int compareTo(final ComparableAdapter<E> that) {
        final E thisValue = get();
        final E thatValue = that.get();
        final Comparator<E> thisComparator = comparator();
        final Comparator<E> thatComparator = that.comparator();
        if (thisComparator == thatComparator) {
            if (thisComparator == null) {
                throw new IllegalStateException("No comparator available");
            }
            return thisComparator.compare(thisValue, thatValue);
        }
        final Integer c1 = thisComparator == null ? null : thisComparator.compare(thisValue, thatValue);
        final Integer c2 = thatComparator == null ? null : thatComparator.compare(thisValue, thatValue);
        if (c1 != null && c2 != null) {
            if (norm(c1) != norm(c2)) {
                throw new IllegalStateException("Comparators returned different comparison");
            }
            return c1;
        }
        return c1 != null ? c1 : c2;
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    static int norm(final Integer value) {
        return (value < 0) ? -1 : ((value > 0) ? +1 : 0);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int hashCode() {
        return get().hashCode();
    }
    
    /**
     * 
     * @param other
     * @return 
     */
    @Override
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof ComparableAdapter)) {
            return false;
        }
        return get().equals(ComparableAdapter.class.cast(other).get());
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return get().toString();
    }

}
