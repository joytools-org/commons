/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.*;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 */
public class IndexedMapImpl<K, V> extends AbstractIndexedMap<K, V> implements CaseSensitivitySupport {

    /**
     *
     * @param entryMap
     */
    protected IndexedMapImpl(final Map<K, IndexedMap.Entry<K, V>> entryMap) { 
        this.entryMap = Objects.requireNonNull(entryMap, "Entry Map");
    }

    /**
     *
     * @return
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        return new IndexedMapImpl(this);
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    protected IndexedMap.Entry<K, V> findEntry(final Object key) {
        return entryMap.get(key);
    }

    /**
     *
     * @return
     */
    @Override
    protected IndexedMap.Entry findEntry(final int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        return entryList[index];
    }

    /**
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    @Override
    protected IndexedMap.Entry newEntry(final K key,
                                      final int index,
                                      final V value) {
        return new IndexedMapEntryImpl(key, index, value);
    }

    @Override
    public CaseSensitivity caseSensitivity() {
        return StringMaps.caseSensitivityOf(entryMap);
    }

    /**
     * 
     */
    protected class IndexedMapEntryImpl extends IndexedMapEntry {

        /**
         *
         * @param key
         * @param index
         * @param value
         */
        protected IndexedMapEntryImpl(final K key,
                                final int index,
                                final V value) {
            super(key, index, value);
        }

        /**
         *
         */
        protected void decrementIndex() {
            setIndex(getIndex() - 1);
        }

    }

    /**
     *
     * @return
     */
    @Override
    public int size() { 
        return entryMap.size(); 
    }

    /**
     *
     * @param e
     */
    @Override
    protected void storeEntry(final IndexedMap.Entry<K, V> e) {
        final int index = e.getIndex();
        final int oldSize = size();
        entryMap.put(e.getKey(), e);
        final int newSize = size();
        if (oldSize != newSize && newSize > entryList.length) {
            final IndexedMap.Entry[] newEntryList = new IndexedMap.Entry[entryList.length + 32];
            System.arraycopy(entryList, 0, newEntryList, 0, entryList.length);
            entryList = newEntryList;
        }
        entryList[index] = e;
    }

    /**
     *
     * @param e
     */
    @Override
    protected void removeEntry(final IndexedMap.Entry<K, V> e) {
        int index = e.getIndex();
        int s = size() - 1;
        System.arraycopy(entryList, index + 1, entryList, index, s - index + 1);
        for(; index < s; index++) {
            final IndexedMapEntryImpl t = (IndexedMapEntryImpl)entryList[index];
            t.decrementIndex();
        }
        entryMap.remove(e.getKey());
    }

    /**
     *
     */
    private final Map<K, IndexedMap.Entry<K, V>> entryMap;

    /**
     *
     */
    private IndexedMap.Entry<K, V> entryList[] = new IndexedMap.Entry[32];
    
}

