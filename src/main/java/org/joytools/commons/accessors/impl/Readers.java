/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.impl;

import org.joytools.commons.accessors.Accessor;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.MapAccessor;

/**
 *
 * @author AndreaR
 */
public class Readers {

    /**
     *
     * @param <K>
     * @param <V>
     * @param acc
     * @return
     */
    public static <K, V> MapAccessor.Reader<K, V> of(final MapAccessor<K, V> acc) {
        return new IndexedMapAccessorReaderImpl(acc);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param acc
     * @return
     */
    public static <K, V> ListAccessor.Reader<V> of(final ListAccessor<V> acc) {
        return new IndexedMapAccessorReaderImpl(acc);
    }

    /**
     *
     * @param <K>
     * @param <V>
     * @param acc
     * @return
     */
    public static <K, V> IndexedMapAccessor.Reader<K, V> of(final IndexedMapAccessor<K, V> acc) {
        return new IndexedMapAccessorReaderImpl(acc);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    public static <K, V> IndexedMapAccessor.Reader<K, V> of(final Accessor<V> acc) {
        return new IndexedMapAccessorReaderImpl(acc);
    }

}
