/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.beans;

import java.util.*;

import org.apache.commons.lang3.builder.*;

/**
 * <p>
 * Title: </p>
 * <p>
 * Description: </p>
 * <p>
 * Copyright: Copyright (c) 2003</p>
 * <p>
 * Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public abstract class ArrayBean extends AbstractBean implements Comparable {

    /**
     *
     */
    public ArrayBean() {
    }

    /**
     *
     * @param <T>
     * @param index int
     * @param value Object
     * @return Object
     */
    protected <T> T set(final int index,
            final T value) {
        assertModifiable(index);
        final Object[] v = values();
        final Object prev = v[index];
        v[index] = value;
        if (prev != value) {
            makeModified();
        }
        return value;
    }

    /**
     *
     * @param field int
     */
    protected void makeReadOnly(final int field) {
        if (m_readOnlyFields == null) {
            m_readOnlyFields = new BitSet(size());
        }
        m_readOnlyFields.set(field);
    }

    /**
     *
     * @param field
     */
    protected void assertModifiable(final int field) {
        assertModifiable();
        if (m_readOnlyFields != null && m_readOnlyFields.get(field)) {
            throw new UnsupportedOperationException(String.format("Attempt fo modify a read only field: %s", field));
        }
    }

    /**
     * 
     * @param index
     * @return 
     */
    protected Object get(final int index) {
        return values()[index];
    }

    /**
     * 
     * @param <T>
     * @param index
     * @param clazz
     * @return 
     */
    protected <T> T get(final int index, final Class<T> clazz) {
        return clazz.cast(get(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    protected Integer getInteger(final int index) {
        return Integer.class.cast(get(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    protected String getString(final int index) {
        return String.class.cast(get(index));
    }

    /**
     * 
     * @param index
     * @return 
     */
    protected Boolean getBoolean(final int index) {
        return Boolean.class.cast(get(index));
    }

    /**
     * 
     * @return 
     */
    protected Object[] values() {
        if (m_values == null) {
            m_values = new Object[size()];
            initValues(m_values);
        }
        return m_values;
    }

    /**
     *
     */
    protected void clear() {
        assertModifiable();
        m_values = null;
    }

    /**
     *
     * @param val Object[]
     */
    protected void initValues(final Object[] val) {
        for (int i = 0; i < val.length; i++) {
            val[i] = null;
        }
    }

    /**
     *
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (o == this) {
            return true;
        }
        final ArrayBean other = ArrayBean.class.cast(o);
        final EqualsBuilder b = new EqualsBuilder();
        b.append(values(), other.values());
        return b.isEquals();
    }

    /**
     * 
     * @return 
     */
    @Override
    protected int computeHashCode() {
        final HashCodeBuilder b = new HashCodeBuilder();
        b.append(values());
        return b.build();
    }

    /**
     *
     * @param o Object
     * @return int
     */
    @Override
    public int compareTo(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            throw new ClassCastException("Object is not comparable");
        }
        final ArrayBean other = ArrayBean.class.cast(o);
        final CompareToBuilder b = new CompareToBuilder();
        b.append(values(), other.values());
        return b.toComparison();
    }

    /**
     *
     */
    private Object[] m_values;
    
    /**
     *
     */
    private BitSet m_readOnlyFields;

    /**
     *
     * @return int
     */
    protected int size() {
        return 0;
    }

}
