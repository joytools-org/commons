/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import java.sql.Timestamp;
import java.time.Instant;
import org.joytools.commons.time.TimeValue;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public interface Variant<E> {
    
    public void set(final E value);
    
    public E get();

    public void setObject(final Object value);
    
    public Object getObject();

    public void setString(final String value);
    
    public String getString();

    public void setInteger(final Integer value);
    
    public Integer getInteger();
    
    public void setLong(final Long value);
    
    public Long getLong();

    public void setFloat(final Float value);
    
    public Float getFloat();

    public void setDouble(final Double value);
    
    public Double getDouble();

    public void setBoolean(final Boolean value);
    
    public Boolean getBoolean();

    public void setDate(final java.util.Date date);
    
    public java.util.Date getDate();
    
    public void setSQLDate(final java.sql.Date date);
    
    public java.sql.Date getSQLDate();

    public void setSQLTime(final java.sql.Time time);
    
    public java.sql.Time getSQLTime();

    public void setSQLTimestamp(final Timestamp date);
    
    public Timestamp getSQLTimestamp();
    
    public void setInstant(final Instant date);
    
    public Instant getInstant();
    
    public TimeValue<?> getTimeValue();

    public void setBytes(final byte[] bytes);
    
    public byte[] getBytes();

}
