/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.control.Option;
import java.sql.Timestamp;
import java.time.Instant;
import org.joytools.commons.time.TimeConverters;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.time.TimeValues;

/**
 * 
 * @author AndreaR
 * @param <E> 
 */
public abstract class AbstractComboVariant<E> implements ComboVariant<E> {

    /**
     * 
     */
    protected AbstractComboVariant() {
    }

    /**
     * 
     * @return 
     */
    protected abstract Object doGetInner();
    
    /**
     * 
     * @return 
     */
    protected Object getInner() {
        final Object val = doGetInner();
        if (val != null) {
            if (type.isEmpty() ||
                    type.getClass() != val.getClass()) {
                type = Some(val.getClass());
            }
        }
        return val;
    }

    /**
     * 
     * @param value 
     */
    protected void putInner(final Object value) {
        throw new UnsupportedOperationException("Read Only");
    }

    /**
     * 
     * @return 
     */
    @Override
    public Option<Class> type() {
        return type;
    }
    
    /**
     * 
     * @return  
     */
    @Override
    public final Object getObject() {
        return getInner();
    }

    @Override
    public final void setObject(final Object value) {
        putInner(value);
    }

    @Override
    public final void set(final Object value) {
        getInner();
        if (type.isEmpty() || type.get().isAssignableFrom(value.getClass())) {
            putInner(value);
        }
        throw new ClassCastException(value.getClass().getName() + " not assignable to " + type.get().getName());
    }

    /**
     * 
     */
    private Option<Class> type = None();
    
    /**
     * 
     * @return 
     */
    @Override
    public final E get() {
        return (E)getInner();
    }
 
    @Override
    public final void setString(final String value) {
        setObject(value);
    }

    @Override
    public final String getString() {
        return Variants.toString(getInner());
    }

    @Override
    public final void setInteger(final Integer value) {
        setObject(value);
    }

    @Override
    public final Integer getInteger() {
        return Variants.toInteger(getInner());
    }

    @Override
    public final void setLong(final Long value) {
        setObject(value);
    }

    @Override
    public final Long getLong() {
        return Variants.toLong(getInner());
    }

    @Override
    public final void setFloat(final Float value) {
        setObject(value);
    }

    @Override
    public final Float getFloat() {
        return Variants.toFloat(getInner());
    }

    @Override
    public final void setDouble(final Double value) {
        setObject(value);
    }

    @Override
    public final Double getDouble() {
        return Variants.toDouble(getInner());
    }

    @Override
    public final void setBoolean(final Boolean value) {
        setObject(value);
    }

    @Override
    public final void setBytes(final byte[] value) {
        setObject(value);
    }

    @Override
    public final Boolean getBoolean() {
        return Variants.toBoolean(getInner());
    }

    @Override
    public final byte[] getBytes() {
        return Variants.toBytes(getInner());
    }

    @Override
    public final void setDate(final java.util.Date value) {
        setObject(value);
    }

    @Override
    public final java.util.Date getDate() {
        return TimeConverters.toDate().apply(getInner());
    }

    @Override
    public final void setSQLDate(final java.sql.Date value) {
        setObject(value);
    }

    @Override
    public final java.sql.Date getSQLDate() {
        return TimeConverters.toSQLDate().apply(getInner());
    }

    @Override
    public final void setSQLTime(final java.sql.Time value) {
        setObject(value);
    }

    @Override
    public final java.sql.Time getSQLTime() {
        return TimeConverters.toSQLTime().apply(getInner());
    }

    @Override
    public final void setSQLTimestamp(final Timestamp value) {
        setObject(value);
    }

    @Override
    public final Timestamp getSQLTimestamp() {
        return TimeConverters.toSQLTimestamp().apply(getInner());
    }
        
    @Override
    public final TimeValue<?> getTimeValue() {
        return TimeValues.findOrFailWithParseAll(getInner());
    }

    @Override
    public final void setInstant(final Instant value) {
        setObject(value);
    }

    @Override
    public final Instant getInstant() {
        return TimeConverters.toInstant().apply(getInner());
    }
    
}
