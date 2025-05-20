/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class IndexedMapEntryV1<K, V> implements IndexedMap.Entry<K, V> {

    /**
     *
     */
    public IndexedMapEntryV1() { }

    /**
     *
     * @param key
     * @param index
     * @param value
     */
    public IndexedMapEntryV1(final K key,
                     final int index,
                     final V value) {
        setKey(key);
        setIndex(index);
        setValue(value);
    }

    /**
     *
     * @return
     */
    @Override
    public K getKey() {
        return m_key;
    }

    /**
     *
     * @param key
     * @return
     */
    public K setKey(final K key) {
        if (m_keyAlreadySet) {
            throw new IllegalStateException("Key can be set only once");
        }
        m_keyAlreadySet = true;
        final K ret = getKey();
        m_key = key;
        return ret;
    }

    /**
     *
     * @return
     */
    @Override
    public V getValue() {
        return m_value;
    }

    /**
     *
     * @param value
     * @return
     */
    @Override
    public V setValue(final V value) {
        final V ret = getValue();
        m_value = value;
        return ret;
    }

    /**
     *
     * @return
     */
    @Override
    public int getIndex() {
        return m_index;
    }

    /**
     *
     * @param index
     * @return 
     */
    protected int setIndex(final int index) {
        final int ret = getIndex();
        m_index = index;
        return ret;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        final HashCodeBuilder b = new HashCodeBuilder()
                .append(m_key)
                .append(m_index)
                .append(m_value);
        return b.toHashCode();
    }

    /**
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof IndexedMap.Entry)) {
            return false;
        }
        final IndexedMap.Entry other = IndexedMap.Entry.class.cast(o);
        final EqualsBuilder b = new EqualsBuilder()
                .append(getKey(), other.getKey())
                .append(getIndex(), other.getIndex())
                .append(getValue(), other.getValue());
        return b.isEquals();
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "[" + getIndex()  + "]" + getKey() + "=" + getValue();
    }

    /**
     * 
     */
    private boolean m_keyAlreadySet = false;

    /**
     *
     */
    private K m_key;

    /**
     *
     */
    private int m_index;

    /**
     *
     */
    private V m_value;

}
