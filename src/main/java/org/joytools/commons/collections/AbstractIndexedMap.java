/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author AndreaR
 * @param <K>
 * @param <V> 
 */
public abstract class AbstractIndexedMap<K, V> implements IndexedMap<K, V> {

    /**
     *
     */
    public AbstractIndexedMap() { }

    /**
     *
     * @return
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }


  /**
   *
   * @param o
   * @return
   */
    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof IndexedMap)) {
            return false;
        }
        final IndexedMap other = (IndexedMap)o;
        return entrySet().equals(other.entrySet());
    }

    /**
     *
     * @param m
     * @return
     */
    @Override
    public int setAll(final Map m) {
        final int mod = Utils.setAll(this, m);
        checkSetAll(mod);
        return mod;
    }

    /**
     *
     * @return
     */
    @Override
    public int setAll(final Collection newValues, 
            final ElementIndexMatcher matcher) {
        final int mod = Utils.setAll(this, newValues, matcher);
        checkSetAll(mod);
        return mod;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        // return entryList().toString();
        final StringBuffer sb = new StringBuffer();
        final ToStringBuilder b = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE, sb);
        for (final IndexedMap.Entry<K, V> e : entryList()) {
            b.append(e);
        }
        int i = 0;
        if (sb.charAt(i) == '[') {
            sb.setCharAt(i, '{');
        }
        i = sb.length() - 1;
        if (i >= 0 && sb.charAt(i) == ',') {
            sb.setCharAt(i, '}');
        }
        return sb.toString();
    }

    /**
     *
     * @param mod
     */
    protected void checkSetAll(final int mod) {
        if (!isPartialSetAllAllowed() && mod < size()) {
            final int missing = size() - mod;
            throw new IllegalArgumentException("" + missing + " argument" +
                (mod == 1 ? " is" : "s are") + " missing from the source object");
        }
    }

    /**
     *
     */
    @Override
    public boolean containsKey(final Object key) {
        return indexOf(key) >= 0;
    }

    /**
     *
     */
    @Override
    public boolean containsValue(final Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    @Override
    public V get(final Object key) {
        final IndexedMap.Entry<K, V> e = findEntry(key);
        return e == null ? null : e.getValue();
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public V get(final int index) {
        final IndexedMap.Entry<K, V> e = findEntry(index);
        return e.getValue();
    }

    // Modification Operations

    /**
     *
     * @param index
     * @param value
     */
    @Override
    public V put(final int index, final V value) {
        final IndexedMap.Entry<K, V> e = findEntry(index);
        return e.setValue(value);
    }

    /**
     *
     */
    @Override
    public V put(final K key, final V value) {
        IndexedMap.Entry<K, V> e = findEntry(key);
        if (e != null) {
            return e.setValue(value);
        }
        e = newEntry(key, size(), value);
        storeEntry(e);
        return null;
    }

    /**
     *
     * @param t
     */
    @Override
    public void putAll(final Map<? extends K, ? extends V> t) {
        putAll(t, false);
    }

    /**
     *
     * @param map
     * @param existingOnly
     * @return 
     */
    public int putAll(final Map<? extends K, ? extends V> map,
        final boolean existingOnly) {
        if (map == null) {
            return 0;
        }
        int count = 0;
        for (final Map.Entry<? extends K, ? extends V> e : map.entrySet()) {
            final K key = e.getKey();
            final V value = e.getValue();
            if (existingOnly) {
                final int pos = indexOf(key);
                if (pos >= 0) {
                    put(pos, value);
                    count++;
                }
            } else {
                put(key, value);
                count++;
            }
        }
        return count;
    }

    /**
     *
     * @param itr
     * @return 
     */
    public int putAll(final Iterable<? extends K> itr) {
        if (itr == null) {
            return 0;
        }
        return putAll(itr.iterator());
    }

    /**
     *
     * @param i
     * @return 
     */
    public int putAll(final Iterator<? extends K> i) {
        if (i == null) return 0;
        int count = 0;
        while (i.hasNext()) {
            final K key = i.next();
            put(key, null);
            count++;
        }
        return count;
    }

    /**
     *
     */
    @Override
    public void clear() {
        while (size() > 0) {
            remove(size() - 1);
        }
    }

    /**
     *
     */
    @Override
    public Set<K> keySet() {
        return m_keyList;
    }

    /**
     *
     * @return
     */
    @Override
    public List<K> keyList() {
        return m_keyList;
    }

    /**
     *
     * @return
     */
    @Override
    public List<V> valueList() {
        return m_valueList;
    }

    /**
     *
     */
    @Override
    public Collection<V> values() {
        return m_valueList;
    }

    /**
     *
     * @return
     */
    @Override
    abstract public int size();

    /**
     *
     * @param key
     * @param index
     * @param value
     * @return
     */
    protected IndexedMap.Entry<K, V> newEntry(final K key,
                                          final int index,
                                          final V value) {
        return new IndexedMapEntry(key, index, value);
    }

    /**
     *
     * @param entry
     */
    abstract protected void storeEntry(final IndexedMap.Entry<K, V> entry);

    /**
     *
     * @param entry
     */
    protected void removeEntry(final IndexedMap.Entry<K, V> entry) {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param index 
     * @return  
     */
    protected IndexedMap.Entry<K, V> removeEntry(final int index) {
        final IndexedMap.Entry<K, V> e = findEntry(index);
        removeEntry(e);
        return e;
    }

    /**
     *
     * @param index
     * @return
     */
    abstract protected IndexedMap.Entry<K, V> findEntry(final int index);

    /**
     *
     * @param key
     * @return
     */
    abstract protected IndexedMap.Entry<K, V> findEntry(final Object key);

    /**
     *
     */
    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return m_entrySet;
    }

    /**
     *
     * @return List
     */
    @Override
    public List<IndexedMap.Entry<K, V>> entryList() {
        return m_entryList;
    }

    /**
     *
     * @param index
     * @return
     */
    @Override
    public V remove(final int index) {
        final IndexedMap.Entry<K, V> e = removeEntry(index);
        return e.getValue();
    }

    /**
     *
     */
    @Override
    public V remove(final Object key) {
        final IndexedMap.Entry<K, V> e = findEntry(key);
        if (e == null) {
            throw new NoSuchElementException(key == null ? null : key.toString());
        }
        removeEntry(e);
        return e.getValue();
    }

    /**
     *
     * @param key
     * @return
     */
    @Override
    public int indexOf(final Object key) {
        final IndexedMap.Entry<K, V> e = findEntry(key);
        return e == null ? -1 : e.getIndex();
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return entrySet().hashCode();
    }

    /**
     *
     * @return
     */
    protected boolean isPartialSetAllAllowed() {
        return true;
    }

    /**
     *
     */
    protected class OLD_BasicEntry implements IndexedMap.Entry<K, V> {

        /**
         *
         */
        protected OLD_BasicEntry() { }

        /**
         *
         * @param key
         * @param index
         * @param value
         */
        protected OLD_BasicEntry(final K key,
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

    /**
     *
     */
    protected class EntryList extends AbstractList<IndexedMap.Entry<K, V>> {

        /**
         *
         */
        protected EntryList() { }

        /**
         *
         * @return
         */
        @Override
        public int size() {
            return AbstractIndexedMap.this.size();
        }

        /**
         *
         * @param index
         * @return
         */
        @Override
        public IndexedMap.Entry<K, V> get(final int index) {
            return findEntry(index);
        }

        /**
         *
         * @param index
         * @return
         */
        @Override
        public Entry<K, V> remove(final int index) {
            return AbstractIndexedMap.this.removeEntry(index);
        }

    };

    /**
     * 
     */
    protected class EntrySet extends AbstractList<Map.Entry<K, V>> implements Set<Map.Entry<K, V>> {

        /**
         *
         */
        protected EntrySet() { }

        /**
         * 
         * @param index
         * @return 
         */
        @Override
        public Entry<K, V> get(int index) {
            return findEntry(index);
        }

        /**
         * 
         * @return 
         */
        @Override
        public int size() {
            return AbstractIndexedMap.this.size();
        }

        /**
         * 
         * @return 
         */
        @Override
        public Spliterator<Map.Entry<K, V>> spliterator() {
            return Spliterators.spliterator(this, 0);
        }        

    };

    /**
     *
     */
    protected class KeyList extends AbstractList<K> implements Set<K> {

        /**
         *
         */
        protected KeyList() { }

        /**
         *
         * @param index
         * @return
         */
        @Override
        public K get(final int index) {
            final IndexedMap.Entry<K, V> e = findEntry(index);
            return e.getKey();
        }

        /**
         *
         * @param index
         * @param key
         */
        @Override
        public void add(final int index,
                        final K key) {
            if (index != size()) {
                throw new IllegalArgumentException("A new key can be appended only");
            }
            put(key, null);
        }

        /**
         *
         * @return
         */
        @Override
        public int size() {
            return AbstractIndexedMap.this.size();
        }

        /**
         *
         * @param index
         * @return
         */
        @Override
        public K remove(final int index) {
            final IndexedMap.Entry<K, V> e = AbstractIndexedMap.this.removeEntry(index);
            return e.getKey();
        }

        @Override
        public Spliterator<K> spliterator() {
            return Spliterators.spliterator(this, 0);
        }        
        
    }

    /**
     *
     */
    protected class ValueList extends AbstractList<V> {

        /**
         *
         */
        protected ValueList() { }

        /**
         *
         * @param index
         * @return
         */
        @Override
        public V get(final int index) {
            final IndexedMap.Entry<K, V> e = findEntry(index);
            return e.getValue();
        }

        /**
         *
         * @param index
         * @param element
         * @return
         */
        @Override
        public V set(int index, final V element) {
            final IndexedMap.Entry<K, V> e = findEntry(index);
            return e.setValue(element);
        }

        /**
         *
         * @return
         */
        @Override
        public int size() {
            return AbstractIndexedMap.this.size();
        }

        /**
         *
         * @param index
         * @return
         */
        @Override
        public V remove(final int index) {
            return AbstractIndexedMap.this.remove(index);
        }

    }


    /**
     *
     */
    private final KeyList m_keyList = new KeyList();

    /**
     *
     */
    private final ValueList m_valueList = new ValueList();

    /**
     *
     */
    private final EntryList m_entryList = new EntryList();

    /**
     * 
     */
    private final EntrySet m_entrySet = new EntrySet();

}
