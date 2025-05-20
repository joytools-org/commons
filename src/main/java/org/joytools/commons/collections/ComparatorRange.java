/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import org.joytools.commons.util.ComparableAdapter;
import com.google.common.collect.BoundType;
import com.google.common.collect.Range;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 * @param <C>
 */
public class ComparatorRange<C> implements Predicate<C> {
    
    ComparatorRange(
            final Range<ComparableAdapter<C>> range,
            final Comparator<C> comparator) {
        this.range = Objects.requireNonNull(range, "Range must not be null");
        this.comparator = Objects.requireNonNull(comparator, "Comparator must be not null");
    }
    
    /**
     * 
     */
    private final Range<ComparableAdapter<C>> range;
    
    /**
     * 
     */
    private final Comparator<C> comparator;
    
    /**
     * 
     * @param <E>
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> all(final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.all();
        return new ComparatorRange<>(range, comparator);
    }
    
    /**
     * 
     * @param <E>
     * @param endPoint
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> atLeast(final E endPoint, 
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.atLeast(
                ComparableAdapter.of(endPoint, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param endPoint
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> atMost(final E endPoint, 
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.atMost(
                ComparableAdapter.of(endPoint, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param lower
     * @param upper
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> closed(final E lower, 
            final E upper,
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.closed(
                ComparableAdapter.of(lower, comparator),
                ComparableAdapter.of(upper, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param lower
     * @param upper
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> closedOpen(final E lower, 
            final E upper,
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.closedOpen(
                ComparableAdapter.of(lower, comparator),
                ComparableAdapter.of(upper, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param endPoint
     * @param boundType
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> downTo(final E endPoint,
            final BoundType boundType,
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.downTo(
                ComparableAdapter.of(endPoint, comparator),
                boundType);
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param values
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> encloseAll(final Iterable<E> values,
            final Comparator<E> comparator) {
        Objects.requireNonNull(values);
        final Range<ComparableAdapter<E>> range = Range.encloseAll(
                IterableAccessors.from(values)
                        .map((o) -> (ComparableAdapter.of(o, comparator))));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param endPoint
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> greaterThan(final E endPoint, 
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.greaterThan(
                ComparableAdapter.of(endPoint, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param endPoint
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> lessThan(final E endPoint, 
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.lessThan(
                ComparableAdapter.of(endPoint, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param lower
     * @param upper
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> open(final E lower, 
            final E upper,
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.open(
                ComparableAdapter.of(lower, comparator),
                ComparableAdapter.of(upper, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param lower
     * @param upper
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> openClosed(final E lower, 
            final E upper,
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.openClosed(
                ComparableAdapter.of(lower, comparator),
                ComparableAdapter.of(upper, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param lower
     * @param lowerType
     * @param upper
     * @param upperType
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> range(
            final E lower, final BoundType lowerType, 
            final E upper, final BoundType upperType,
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.range(
                ComparableAdapter.of(lower, comparator), lowerType,
                ComparableAdapter.of(upper, comparator), upperType);
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param value
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> singleton(final E value,
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.singleton(
                ComparableAdapter.of(value, comparator));
        return new ComparatorRange<>(range, comparator);
    }

    /**
     * 
     * @param <E>
     * @param endPoint
     * @param boundType
     * @param comparator
     * @return 
     */
    public static <E> ComparatorRange<E> upTo(final E endPoint,
            final BoundType boundType,
            final Comparator<E> comparator) {
        final Range<ComparableAdapter<E>> range = Range.upTo(
                ComparableAdapter.of(endPoint, comparator),
                boundType);
        return new ComparatorRange<>(range, comparator);
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public boolean contains(final C value) {
        return range.contains(ComparableAdapter.of(value, comparator));
    }
    
    /**
     * 
     * @param values
     * @return 
     */
    public boolean containsAll(final Iterable<? extends C> values) {
        return range.containsAll(
                IterableAccessors.from(values)
                        .map((o) -> (ComparableAdapter.of(o, comparator))));
    }

    /**
     * 
     * @return 
     */
    public boolean hasLowerBound() {
        return range.hasLowerBound();
    }

    /**
     * 
     * @return 
     */
    public boolean hasUpperBound() {
        return range.hasUpperBound();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isEmpty() {
        return range.isEmpty();
    }

    /**
     * 
     * @return 
     */
    public BoundType lowerBoundType() {
        return range.lowerBoundType();
    }

    /**
     * 
     * @return 
     */
    public C lowerEndpoint() {
        return range.lowerEndpoint().get();
    }

    /**
     * 
     * @return 
     */
    public BoundType upperBoundType() {
        return range.upperBoundType();
    }

    /**
     * 
     * @return 
     */
    public C upperEndpoint() {
        return range.upperEndpoint().get();
    }
    
    /**
     * 
     * @param otherRange
     * @return 
     */
    public boolean isConnected(final ComparatorRange<C> otherRange) {
        return range.isConnected(otherRange.range);
    }

    /**
     * 
     * @param connectedRange
     * @return 
     */
    public ComparatorRange<C> intersection(final ComparatorRange<C> connectedRange) {
        return new ComparatorRange<>(range.intersection(connectedRange.range), comparator);
    }

    /**
     * 
     * @param other
     * @return 
     */
    public ComparatorRange<C> gap(final ComparatorRange<C> other) {
        return new ComparatorRange<>(range.gap(other.range), comparator);
    }

    /**
     * 
     * @param connectedRange
     * @return 
     */
    public ComparatorRange<C> span(final ComparatorRange<C> connectedRange) {
        return new ComparatorRange<>(range.span(connectedRange.range), comparator);
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return range.toString();
    }

    /**
     * 
     * @param value
     * @return 
     */
    @Override
    public boolean test(final C value) {
        return contains(value);
    }
    
}
