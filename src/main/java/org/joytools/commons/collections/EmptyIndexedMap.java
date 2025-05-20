/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

/**
 * 
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public class EmptyIndexedMap<K, V> extends AbstractIndexedMap<K, V> {

    /**
     *
     */
    protected EmptyIndexedMap() { }

    /**
     *
     * @param key Object
     * @return Entry
     */
    @Override
    public IndexedMap.Entry<K, V> findEntry(final Object key) {
        return null;
    }

    /**
     *
     * @param index int
     * @return Entry
     */
    @Override
    public IndexedMap.Entry<K, V> findEntry(final int index) {
        return null;
    }

    /**
     *
     * @param entry Entry
     */
    @Override
    public void storeEntry(final IndexedMap.Entry<K, V> entry) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @return int
     */
    @Override
    public int size() { 
        return 0; 
    }

}
