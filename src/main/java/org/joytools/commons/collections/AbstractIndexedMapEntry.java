/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractIndexedMapEntry<K, V> implements IndexedMap.Entry<K, V> {
    
    protected AbstractIndexedMapEntry() {
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == null ||
                !(other instanceof IndexedMap.Entry)) {
            return false;
        }
        final IndexedMap.Entry o = IndexedMap.Entry.class.cast(other);
        return new EqualsBuilder()
                .append(getKey(), o.getKey())
                .append(getIndex(), o.getIndex())
                .append(getValue(), o.getValue())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getIndex(), getValue());
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "[" + getIndex()  + "]" + getKey() + "=" + getValue();        
        /*
        return new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE)
                .append("key", getKey())
                .append("index", getIndex())
                .append("value", getValue())
                .build();
        */
    }
    
}
