/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joytools.commons.beans.ArrayBean;

/**
 *
 * @author AndreaR
 */
@Deprecated
final class OldTimeValueUnitImpl extends ArrayBean implements TimeValueUnit {

    OldTimeValueUnitImpl() {
    }
    
    void setUnit(final TimeUnit unit) {
        set(super.size() + 0, Objects.requireNonNull(unit, "Time Unit"));
    }
    
    TimeUnit getUnit() {
        return TimeUnit.class.cast(get(super.size() + 0));
    }
    
    void setValue(final Long value) {
        Objects.requireNonNull(value, "Value");
        set(super.size() + 1, value < 0 ? Long.MAX_VALUE : value);
    }
    
    Long getValue() {
        return Long.class.cast(get(super.size() + 1));
    }

    @Override
    public String toString() {
        final ToStringBuilder tsb = new ToStringBuilder(this, ToStringStyle.NO_CLASS_NAME_STYLE);
        tsb.append("value", get(super.size() + 1));
        tsb.append("unit", get(super.size() + 0));
        return tsb.build();    
    }
    
    @Override
    protected int size() {
        return super.size() + 2;
    }
    
    @Override
    protected void initValues(final Object[] values) {
        super.initValues(values);
        int index = super.size();
        values[index++] = TimeUnit.MILLISECONDS;
        values[index++] = Long.MAX_VALUE;
    }
    
    @Override
    protected boolean hasMutableValues() {
        return false;
    }

    @Override
    public TimeUnit unit() {
        return getUnit();
    }

    @Override
    public long value() {
        return getValue();
    }
    
}
