/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.Map;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Defines a functor interface implemented by classes that transform one
 * object into another.
 * <p>
 * A <code>Transformer</code> converts the input object to the output object.
 * The input object should be left unchanged.
 * Transformers are typically used for type conversions, or extracting data
 * from an object.
 * <p>
 * Standard implementations of common transformers are provided by
 * {@link TransformerUtils}. These include method invokation, returning a constant,
 * cloning and returning the string value.
 *
 * @param <K>
 * @param <V>
 * @since Commons Collections 1.7.1
 * @version $Revision: 1.0 $ $Date: 2005/10/14 20:08:57 $
 *
 * @author Andrea Rombaldi
 */
public interface IndexedMap<K, V> extends Map<K, V> {

    /**
     *
     * @param m
     * @return
     */
    public int setAll(final Map<? extends K, ? extends V> m);

    /**
     *
     * @param newValues
     * @param matcher
     * @return
     */
    public int setAll(final Collection<? extends V> newValues,
            final ElementIndexMatcher matcher);

    /**
     *
     * @param index
     * @return 
     */
    public V get(final int index);

    /**
     *
     * @param index
     * @param value
     * @return 
     */
    public V put(final int index, final V value);

    /**
     *
     * @param index
     * @return
     */
    public V remove(final int index);

    /**
     *
     * @param key
     * @return
     */
    public int indexOf(final Object key);

    /**
     *
     * @return
     */
    public List<K> keyList();

    /**
     *
     * @return
     */
    public List<V> valueList();

    /**
     *
     * @return List
     */
    public List<IndexedMap.Entry<K, V>> entryList();

    /**
     * 
     * @param <K>
     * @param <V> 
     */
    public interface Entry<K, V> extends Map.Entry<K, V> {

        /**
         *
         * @return
         */
        public int getIndex();

    }

}
