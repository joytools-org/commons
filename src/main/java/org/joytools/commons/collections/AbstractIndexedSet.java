/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.*;

/**
 * 
 * @author AndreaR
 */
public abstract class AbstractIndexedSet<E> extends AbstractList<E> implements IndexedSet<E> {

    /**
     *
     */
    public AbstractIndexedSet() { }

    /**
     *
     * @return
     */
    public Comparator<E> comparator() {
        return new Comparator<E>() {
            @Override
            public int compare(final E o1,
                    final E o2) {
                final int i1 = indexOf(o1);
                final int i2 = indexOf(o2);
                if (i1 < 0 && i2 < 0) {
                    throw new ClassCastException("Both objects are not contained in the list");
                }
                if (i1 < i2 || i2 < 0) {
                    return -1;
                }
                if (i1 > i2 || i1 < 0) {
                    return 1;
                }
                return 0;
            }
        };
    }

    /**
     *
     * @param index
     * @param item
     */
    @Override
    public void add(final int index,
        final E item) {
        if (indexOf(item) < 0) {
            appendItem(item);
        }
    }

    /**
     *
     * @param item
     */
    abstract protected void appendItem(final E item);

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean add(final E item) {
        if (indexOf(item) >= 0) {
            return false;
        }
        appendItem(item);
        return true;
    }

}
