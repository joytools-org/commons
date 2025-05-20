/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import io.vavr.collection.Traversable;
import io.vavr.collection.Vector;
import java.util.Enumeration;
import java.util.Iterator;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class Traversables {

    /**
     * 
     * @param <T>
     * @param <I>
     * @return 
     */
    public static <T, I extends Iterable<T>> Traversable<T> empty() {
        return io.vavr.collection.Stream.empty();
    }

    /**
     * 
     * @param <T>
     * @param iterable
     * @return 
     */
    public static <T> Traversable<T> ofAll(final Iterable<T> iterable) {
        if (iterable == null) {
            return Traversables.empty();
        }
        if (iterable instanceof Traversable trav) {
            return trav;
        }
        return io.vavr.collection.Stream.ofAll(iterable);
    }
    
    /**
     * 
     * @param <T>
     * @param iterator
     * @return 
     */
    public static <T> Traversable<T> ofAll(final Iterator<T> iterator) {
        if (iterator == null) {
            return Traversables.empty();
        }
        if (iterator instanceof Traversable trav) {
            return trav;
        }
        return io.vavr.collection.Iterator.ofAll(iterator);
    }

    /**
     * 
     * @param <T>
     * @param enumeration
     * @return 
     */
    public static <T> Traversable<T> ofAll(final Enumeration<T> enumeration) {
        if (enumeration == null) {
            return Traversables.empty();
        }
        if (enumeration instanceof Traversable trav) {
            return trav;
        }
        // return io.vavr.collection.Iterator.ofAll(IteratorUtils.asIterator(enumeration));
        final Iterator<T> itr = IterableAccessors.ofAll(enumeration).iterator();
        return io.vavr.collection.Iterator.ofAll(itr);
    }

    /**
     * 
     * @param <T>
     * @param element
     * @return 
     */
    public static <T> Traversable<T> of(final T element) {
        return io.vavr.collection.Stream.of(element);
    }

    /**
     * 
     * @param <T>
     * @param elements
     * @return 
     */
    public static <T> Traversable<T> of(final T... elements) {
        if (elements == null || elements.length == 0) {
            return Traversables.empty();
        }
        return io.vavr.collection.Stream.of(elements);
    }

    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final Vector<String> a = Vector.of("uno", "due", "tre");
        final Vector<String> b = a.toVector();
        System.out.println(a == b);
    }
    
}
