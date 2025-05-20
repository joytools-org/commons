/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.sandbox.util;

import java.util.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @param <V>
 */
public class ArgDecl<V> {

    /**
     *
     */
    protected ArgDecl() { }

    /**
     *
     * @return 
     */
    public static ArgDecl create() { 
        return new ArgDecl(); 
    }

    /**
     *
     * @param <V>
     * @param other
     * @return 
     */
    public static <V> ArgDecl<V> copyOf(final ArgDecl<V> other) {
        if (other == null) {
            throw new NullPointerException("other");
        }
        final ArgDecl result = new ArgDecl();
        result.setName(other.getName());
        result.setDescription(other.getDescription());
        result.setRequired(other.isRequired());
        result.setPassword(other.isPassword());
        result.setSwitches(other.getSwitches());
        result.setDefaultValues(other.getDefaultValues());
        result.setAllowedValues(other.getAllowedValues());
        result.setMinValuesCount(other.getMinValuesCount());
        result.setMaxValuesCount(other.getMaxValuesCount());
        return result;
    }

    /**
     *
     * @param name
     * @param description
     * @return 
     */
    public static ArgDecl of(final String name,
                                    final String description) {
        final ArgDecl result = new ArgDecl();
        result.setName(name);
        result.setDescription(description);
        return result;
    }

    /**
     *
     * @param name
     * @param description
     * @param switches
     * @return 
     */
    public static ArgDecl of(final String name,
                                    final String description,
                                    final Iterable<? extends CharSequence> switches) {
        final ArgDecl result = new ArgDecl();
        result.setName(name);
        result.setDescription(description);
        result.setSwitches(switches);
        return result;
    }

    /**
     *
     * @param name
     * @param description
     * @param minValues
     * @param maxValues
     * @return 
     */
    public static ArgDecl of(final String name,
                                    final String description,
                                    int minValues,
                                    int maxValues) {
        final ArgDecl result = new ArgDecl();
        result.setName(name);
        result.setDescription(description);
        result.setMinValuesCount(minValues);
        result.setMaxValuesCount(maxValues);
        return result;
    }

    /**
     *
     * @param <V>
     * @param name
     * @param description
     * @param switches
     * @param values
     * @param minValues
     * @param maxValues
     * @return 
     */
    public static <V> ArgDecl of(final String name,
                                    final String description,
                                    final Iterable<? extends CharSequence> switches,
                                    final Iterable<V> values,
                                    final int minValues,
                                    final int maxValues) {
        final ArgDecl result = new ArgDecl();
        result.setName(name);
        result.setDescription(description);
        result.setSwitches(switches);
        result.setAllowedValues(values);
        result.setMinValuesCount(minValues);
        result.setMaxValuesCount(maxValues);
        return result;
    }

    /**
     *
     * @param name
     * @return 
     */
    public ArgDecl<V>  setName(final String name) {
        this.name = name == null ? "" : name;
        return this;
    }

    /**
     *
     * @param password
     * @return 
     */
    public ArgDecl<V> setPassword(final boolean password) {
        this.password = password;
        return this;
    }

    /**
     *
     * @param description
     * @return 
     */
    public ArgDecl<V>  setDescription(final String description) {
        this.description = description == null ? "" : description;
        return this;
    }

    /**
     *
     * @param switches
     * @return 
     */
    public ArgDecl<V>  setSwitches(final Iterable<? extends CharSequence> switches) {
        this.switches.clear();
        if (switches != null) {
            for (final Object o : switches) {
                this.switches.add(o == null ? null : o.toString());
            }
        }
        return this;
    }

    /**
     *
     * @param value
     * @return 
     */
    public final ArgDecl<V> setDefaultValue(final V value) {
        final List<V> values = new ArrayList<>();
        values.add(value);
        return setDefaultValues(values);
    }

    /**
     *
     * @param defaultValues
     * @return 
     */
    public ArgDecl<V> setDefaultValues(final List<? extends V> defaultValues) {
        this.defaultValues.clear();
        if (defaultValues != null) {
            for (final V value : defaultValues) {
                this.defaultValues.add(value);
            }
        }
        return this;
    }

    /**
     *
     * @param newAllowedValues
     * @return 
     */
    public ArgDecl<V> setAllowedValues(final Iterable<V> newAllowedValues) {
        setFilter(
            new Predicate() {
                @Override
                public boolean evaluate(final Object o) {
                    if (o == null || !(o instanceof ArgValue)) {
                        return false;
                    }
                    final ArgValue val = (ArgValue)o;
                    final List<V> values = val.getValues();
                    if (values == null) {
                        return false;
                    }
                    if (allowedValues.isEmpty()) {
                        return true;
                    }
                    for (final V value : values) {
                        if (!allowedValues.contains(value)) {
                            return false;
                        }
                    }
                    return true;
                }
            } 
        );
        if (newAllowedValues != null) {
            for (final V value : newAllowedValues) {
                this.allowedValues.add(value);
            }
        }
        return this;
    }

    /**
     *
     * @param filter
     * @return 
     */
    public ArgDecl<V>  setFilter(final Predicate<V> filter) {
        this.allowedValues.clear();
        this.filter = filter;
        return this;
    }

    /**
     *
     * @param o
     * @return 
     */
    @Override
    public boolean equals(final Object o) {
        if (o == null || !(o instanceof ArgDecl)) {
            return false;
        }
        final ArgDecl other = (ArgDecl)o;
        final EqualsBuilder b = new EqualsBuilder();
        b.append(getName(), other.getName())
                .append(getDescription(), other.getDescription())
                .append(getSwitches(), other.getSwitches())
                .append(getDefaultValues(), other.getDefaultValues())
                .append(getAllowedValues(), other.getAllowedValues())
                .append(getMinValuesCount(), other.getMinValuesCount())
                .append(getMaxValuesCount(), other.getMaxValuesCount());
        return b.isEquals();
    }

    /**
     *
     * @return 
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                getName(), 
                getDescription(), 
                getSwitches(), 
                getDefaultValues(), 
                getAllowedValues(), 
                getMinValuesCount(), 
                getMaxValuesCount());
    }

    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        final ToStringBuilder b = new ToStringBuilder(this, 
                ToStringStyle.SHORT_PREFIX_STYLE);
        b.append("name", getName())
                .append("description", getDescription())
                .append("switches", getSwitches())
                .append("required", isRequired())
                .append("defaultValues", getDefaultValues())
                .append("allowedValues", getAllowedValues())
                .append("password", isRequired())
                .append("minValuesCount", getMinValuesCount())
                .append("maxValuesCount", getMaxValuesCount());
        return b.build();
    }

    /**
     *
     * @param minValues
     * @return 
     */
    public ArgDecl<V>  setMinValuesCount(final int minValues) { 
        this.minValues = minValues; 
        return this;
    }

    /**
     *
     * @param maxValues
     * @return 
     */
    public ArgDecl<V>  setMaxValuesCount(final int maxValues) { 
        this.maxValues = maxValues; 
        return this;
    }

    /**
     *
     * @param required
     * @return 
     */
    public ArgDecl<V>  setRequired(final boolean required) { 
        this.required = required; 
        return this;
    }

    /**
     *
     * @return 
     */
    public String getName() { 
        return name; 
    }

    /**
     *
     * @return 
     */
    public String getDescription() { 
        return description; 
    }

    /**
     *
     * @return 
     */
    public Set<String> getSwitches() { 
        return new HashSet<>(switches); 
    }

    /**
     *
     * @return 
     */
    public List<V> getDefaultValues() { 
        return new ArrayList(defaultValues); 
    }

    /**
     *
     * @return 
     */
    public Set<V> getAllowedValues() { 
        return new HashSet(allowedValues); 
    }

    /**
     *
     * @return 
     */
    public Predicate<V> getFilter() {
        return filter != null ? filter :
            new Predicate<V>() {
                @Override
                public boolean evaluate(final V o) { 
                    return true; 
                }
            };
    }

    /**
     *
     * @return 
     */
    public int getMinValuesCount() { 
        return minValues; 
    }

    /**
     *
     * @return 
     */
    public int getMaxValuesCount() { 
        return maxValues; 
    }

    /**
     *
     * @return 
     */
    public boolean isRequired() { 
        return required; 
    }

    /**
     *
     * @return 
     */
    public final boolean hasDefaultValues() {
        return defaultValues.size() > 0;
    }

    /**
     *
     * @return 
     */
    public boolean isPassword() { 
        return password; 
    }

    /**
     * 
     */
    private String name = "";

    /**
     * 
     */
    private String description = "";

    /**
     * 
     */
    private final Set<String> switches = new HashSet<>();

    /**
     * 
     */
    private final Set<V> allowedValues = new HashSet<>();

    /**
     * 
     */
    private Predicate<V> filter;

    /**
     * 
     */
    private final List<V> defaultValues = new ArrayList<>();

    /**
     * 
     */
    private int minValues = -1;

    /**
     * 
     */
    private int maxValues = -1;

    /**
     * 
     */
    private boolean required = false;

    /**
     * 
     */
    private boolean password = false;
    
}
