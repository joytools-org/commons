/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLType;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

/**
 *
 * @author AndreaR
 */
public interface ResultSetColumnData {
    
    public ResultSetColumnMetaData getMetaData();
    
    public Array getArray();
    
    public void setArray(final Array v);

    public InputStream getAsciiStream();
    
    public void setAsciiStream(final InputStream v);

    public void setAsciiStream(final InputStream v, final int len);

    public void setAsciiStream(final InputStream v, final long len);

    public BigDecimal getBigDecimal();
    
    public void setBigDecimal(final BigDecimal v);
    
    public InputStream getBinaryStream();
    
    public void setBinaryStream(final InputStream v);

    public void setBinaryStream(final InputStream v, final int len);

    public void setBinaryStream(final InputStream v, final long len);

    public Blob getBlob();
    
    public void setBlob(final Blob v);

    public void setBlob(final InputStream v);

    public void setBlob(final InputStream v, final int len);

    public void setBlob(final InputStream v, final long len);

    public Boolean getBoolean();
    
    public void setBoolean(final Boolean v);

    public Byte getByte();
    
    public void setByte(final Byte v);

    public byte[] getBytes();
    
    public void setBytes(final byte[] v);

    public Reader getCharacterStream();
    
    public void setCharacterStream(final Reader v);

    public void setCharacterStream(final Reader v, final int len);

    public void setCharacterStream(final Reader v, final long len);

    public Clob getClob();
    
    public void setClob(final Clob v);

    public void setClob(final Reader v);

    public void setClob(final Reader v, final int len);

    public void setClob(final Reader v, final long len);

    public Date getDate();
    
    public Date getDate(final Calendar c);
    
    public void setDate(final Date v);

    public Double getDouble();
    
    public void setDouble(final Double v);

    public Float getFloat();
    
    public void setFloat(final Float v);

    public Integer getInt();
    
    public void setInt(final Integer v);

    public Long getLong();
    
    public void setLong(final Long v);

    public Reader getNCharacterStream();
    
    public void setNCharacterStream(final Reader v);

    public void setNCharacterStream(final Reader v, final int len);

    public void setNCharacterStream(final Reader v, final long len);

    public NClob getNClob();
    
    public void setNClob(final NClob v);
    
    public void setNClob(final Reader v);

    public void setNClob(final Reader v, final int len);

    public void setNClob(final Reader v, final long len);
    
    public void setNull();

    public void setNull(final Type t);

    public String getNString();
    
    public void setNString(final String v);

    public Object getObject();

    public <T> T getObject(final Class<T> type);

    public Object getObject(final Map<String, Class<?>> types);

    public void setObject(final Object v);

    public void setObject(final Object v, final int scaleOrLen);

    public void setObject(final Object v, final SQLType type, int scaleOrLen);

    public Ref getRef();
    
    public void setRef(final Ref v);

    public RowId getRowId();
    
    public void setRowId(final RowId v);

    public Short getShort();
    
    public void setShort(final Short v);

    public SQLXML getSQLXML();
    
    public void setSQLXML(final SQLXML v);

    public String getString();
    
    public void setString(final String v);

    public Time getTime();
    
    public Time getTime(final Calendar c);
    
    public void setTime(final Time v);

    public Timestamp getTimestamp();
    
    public Timestamp getTimestamp(final Calendar c);
    
    public void setTimestamp(final Timestamp v);

    public URL getURL();
    
}
