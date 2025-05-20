/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.sandbox.util;


import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 */
public class ArgValue<V> {

    /**
     *
     */
    public ArgValue() { }

    /**
     *
     * @param other
     */
    public ArgValue(final ArgValue<? extends V> other) {
        this();
        if (other == null) {
            return;
        }
        setName(other.getName());
        setValues(other.getValues());
        setDefault(other.isDefault());
    }

    /**
     *
     * @param name
     */
    public ArgValue(final String name, 
            final List<V> values) {
        setName(name);
        setValues(values);
    }

    /**
     *
     */
    public ArgValue(final String name, 
            final List<V> values, 
            final boolean isDefault) {
        this(name, values);
        setDefault(isDefault);
    }

    /**
     *
     * @return 
     */
    public String getName() { 
        return this.name; 
    }

    /**
     *
     * @return 
     */
    public List<V> getValues() { 
        return valuesReadOnly; 
    }

    /**
     *
     * @return 
     */
    public boolean isDefault() { 
        return this.isDefault; 
    }

    /**
     *
     * @return 
     */
    @Override
    public String toString() {
        final ToStringBuilder b = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        b.append("name", getName())
                .append("values", getValues());
        return b.build();
    }

    /**
     *
     * @return 
     */
    @Override
    public int hashCode() {
        final HashCodeBuilder b = new HashCodeBuilder();
        b.append(getName())
                .append(getValues());
        return b.toHashCode();
    }

    /**
     *
     * @param o
     * @return 
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof ArgValue)) return false;
        final ArgValue other = (ArgValue)o;
        final EqualsBuilder b = new EqualsBuilder();
        b.append(getName(), other.getName())
                .append(getValues(), other.getValues());
        return b.isEquals();
    }

    /**
     *
     * @return 
     */
    public final ArgValue createClone() { 
        return (ArgValue)clone(); 
    }

    /**
     *
     */
    public ArgValue createEmptyClone() { 
        return new ArgValue(); 
    }

    /**
     *
     */
    protected Object clone() { 
        return new ArgValue(this); 
    }

    /**
     *
     */
    protected void setName(final String name) {
        this.name = name == null ? "" : name;
    }

    /**
     *
     */
    protected void setValues(final List<? extends V> values) {
        this.values.clear();
        if (values != null) {
            this.values.addAll(values);
        }
    }

    /**
     *
     */
    protected void setDefault(final boolean isDefault) {
        this.isDefault = isDefault;
    }

    /**
     * 
     */
    private String name = "";

    /**
     * 
     */
    private final List<V> values = new ArrayList<>();

    /**
     * 
     */
    private final List<V> valuesReadOnly = Collections.unmodifiableList(values);

    /**
     * 
     */
    private boolean isDefault = false;

}
