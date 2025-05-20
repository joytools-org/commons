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
import java.sql.SQLException;
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
public interface ResultSetColumnDataChecked {
    
    public ResultSetColumnMetaData getMetaData() throws SQLException;
    
    public Array getArray() throws SQLException;
    
    public void setArray(final Array v) throws SQLException;

    public InputStream getAsciiStream() throws SQLException;
    
    public void setAsciiStream(final InputStream v) throws SQLException;

    public void setAsciiStream(final InputStream v, final int len) throws SQLException;

    public void setAsciiStream(final InputStream v, final long len) throws SQLException;

    public BigDecimal getBigDecimal() throws SQLException;
    
    public void setBigDecimal(final BigDecimal v) throws SQLException;
    
    public InputStream getBinaryStream() throws SQLException;
    
    public void setBinaryStream(final InputStream v) throws SQLException;

    public void setBinaryStream(final InputStream v, final int len) throws SQLException;

    public void setBinaryStream(final InputStream v, final long len) throws SQLException;

    public Blob getBlob() throws SQLException;
    
    public void setBlob(final Blob v) throws SQLException;

    public void setBlob(final InputStream v) throws SQLException;

    public void setBlob(final InputStream v, final int len) throws SQLException;

    public void setBlob(final InputStream v, final long len) throws SQLException;

    public Boolean getBoolean() throws SQLException;
    
    public void setBoolean(final Boolean v) throws SQLException;

    public Byte getByte() throws SQLException;
    
    public void setByte(final Byte v) throws SQLException;

    public byte[] getBytes() throws SQLException;
    
    public void setBytes(final byte[] v) throws SQLException;

    public Reader getCharacterStream() throws SQLException;
    
    public void setCharacterStream(final Reader v) throws SQLException;

    public void setCharacterStream(final Reader v, final int len) throws SQLException;

    public void setCharacterStream(final Reader v, final long len) throws SQLException;

    public Clob getClob() throws SQLException;
    
    public void setClob(final Clob v) throws SQLException;

    public void setClob(final Reader v) throws SQLException;

    public void setClob(final Reader v, final int len) throws SQLException;

    public void setClob(final Reader v, final long len) throws SQLException;

    public Date getDate() throws SQLException;
    
    public Date getDate(final Calendar c) throws SQLException;
    
    public void setDate(final Date v) throws SQLException;

    public Double getDouble() throws SQLException;
    
    public void setDouble(final Double v) throws SQLException;

    public Float getFloat() throws SQLException;
    
    public void setFloat(final Float v) throws SQLException;

    public Integer getInt() throws SQLException;
    
    public void setInt(final Integer v) throws SQLException;

    public Long getLong() throws SQLException;
    
    public void setLong(final Long v) throws SQLException;

    public Reader getNCharacterStream() throws SQLException;
    
    public void setNCharacterStream(final Reader v) throws SQLException;

    public void setNCharacterStream(final Reader v, final int len) throws SQLException;

    public void setNCharacterStream(final Reader v, final long len) throws SQLException;

    public NClob getNClob() throws SQLException;
    
    public void setNClob(final NClob v) throws SQLException;
    
    public void setNClob(final Reader v) throws SQLException;

    public void setNClob(final Reader v, final int len) throws SQLException;

    public void setNClob(final Reader v, final long len) throws SQLException;
    
    public void setNull() throws SQLException;

    public void setNull(final Type t) throws SQLException;

    public String getNString() throws SQLException;
    
    public void setNString(final String v) throws SQLException;

    public Object getObject() throws SQLException;

    public <T> T getObject(final Class<T> type) throws SQLException;

    public Object getObject(final Map<String, Class<?>> types) throws SQLException;

    public void setObject(final Object v) throws SQLException;

    public void setObject(final Object v, final int scaleOrLen) throws SQLException;

    public void setObject(final Object v, final SQLType type, int scaleOrLen) throws SQLException;

    public Ref getRef() throws SQLException;
    
    public void setRef(final Ref v) throws SQLException;

    public RowId getRowId() throws SQLException;
    
    public void setRowId(final RowId v) throws SQLException;

    public Short getShort() throws SQLException;
    
    public void setShort(final Short v) throws SQLException;

    public SQLXML getSQLXML() throws SQLException;
    
    public void setSQLXML(final SQLXML v) throws SQLException;

    public String getString() throws SQLException;
    
    public void setString(final String v) throws SQLException;

    public Time getTime() throws SQLException;
    
    public Time getTime(final Calendar c) throws SQLException;
    
    public void setTime(final Time v) throws SQLException;

    public Timestamp getTimestamp() throws SQLException;
    
    public Timestamp getTimestamp(final Calendar c) throws SQLException;
    
    public void setTimestamp(final Timestamp v) throws SQLException;

    public URL getURL() throws SQLException;
    
}
