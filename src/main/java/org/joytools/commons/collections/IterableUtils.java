/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.Value;
import io.vavr.collection.Seq;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Supplier;

/**
 *
 * @author AndreaR
 */
public class IterableUtils {
    
    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static <V> Iterable<V> emptyIfNull(final Iterable<V> elements) {
        if (elements == null) {
            return IterableAccessors.empty();
        }
        return elements;
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static <V> V onlyElement(final Iterable<V> elements) {
        Objects.requireNonNull(elements, "Elements");
        final Iterator<V> itr = elements.iterator();
        if (!itr.hasNext()) {
            throw new NoSuchElementException();
        }
        final V ret = itr.next();
        if (itr.hasNext()) {
            throw new IllegalArgumentException("Multiple elements found");
        }
        return ret;
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static <V> V firstOrFail(final Iterable<V> elements) {
        final Iterator<V> itr = elements.iterator();
        if (!itr.hasNext()) {
            throw new NoSuchElementException();
        }
        return itr.next();
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @return 
     */
    public static <V> V firstOrNull(final Iterable<V> elements) {
        return firstOrElse(elements, (V)null);
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @param def
     * @return 
     */
    public static <V> V firstOrElse(final Iterable<V> elements, final V def) {
        final Iterator<V> itr = elements.iterator();
        if (!itr.hasNext()) {
            return def;
        }
        return itr.next();
    }
    
    /**
     * 
     * @param <V>
     * @param elements
     * @param supplier
     * @return 
     */
    public static <V> V firstOrElse(final Iterable<V> elements, final Supplier<V> supplier) {
        final Iterator<V> itr = elements.iterator();
        if (!itr.hasNext()) {
            return supplier.get();
        }
        return itr.next();
    }
    
    /**
     * 
     * @param elements
     * @return 
     */
    public static int size(final Iterable<?> elements) {
        final int s = knownSize(elements);
        if (s >= 0) {
            return s;
        }
        return org.apache.commons.collections4.IteratorUtils.size(elements.iterator());
    }
    
    /**
     * 
     * @param elements
     * @return 
     */
    public static boolean isEmpty(final Iterable<?> elements) {
        final int s = knownSize(elements);
        if (s == 0) {
            return true;
        }
        if (s > 0) {
            return false;
        }
        return !elements.iterator().hasNext();
    }

    /**
     * 
     * @param elements
     * @return 
     */
    public static int knownSize(final Iterable<?> elements) {
        if (elements == null) {
            return 0;
        }
        if (elements instanceof Collection coll) {
            return coll.size();
        }
        if (elements instanceof Traversable trav) {
            if (trav.isTraversableAgain() && !trav.isLazy() && trav.hasDefiniteSize()) {
                return trav.size();
            }
        }
        if (elements instanceof Value v) {
            if (v.isEmpty()) {
                return 0;
            }
            if (v.isSingleValued()) {
                return 1;
            }
        }
        return -1;
    }
    
    /**
     * 
     * @param <V>
     * @param elements
     * @param index
     * @return 
     */
    public static <V> Option<V> get(final Iterable<V> elements, final int index) {
        try {
            return Some(getOrFail(elements, index));
        } catch (final IndexOutOfBoundsException ioobe) {
            return None();
        }
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @param index
     * @return 
     */
    public static <V> V getOrNull(final Iterable<V> elements, final int index) {
        return getOrElse(elements, index, null);
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @param index
     * @param def
     * @return 
     */
    public static <V> V getOrElse(final Iterable<V> elements, final int index, final V def) {
        try {
            if (elements instanceof java.util.List<V> list) {
                return list.get(index);
            }
            if (elements instanceof Seq<V> seq) {
                return seq.get(index);
            }
            return org.apache.commons.collections4.IteratorUtils.get(elements.iterator(), index);
        } catch (final IndexOutOfBoundsException ioobe) {
            return def;
        }
    }

    /**
     * 
     * @param <V>
     * @param elements
     * @param index
     * @return 
     */
    public static <V> V getOrFail(final Iterable<V> elements, final int index) {
        Objects.requireNonNull(elements, "Elements");
        if (elements instanceof java.util.List<V> list) {
            return list.get(index);
        }
        if (elements instanceof Seq<V> seq) {
            return seq.get(index);
        }
        return org.apache.commons.collections4.IterableUtils.get(elements, index);
    }

}
