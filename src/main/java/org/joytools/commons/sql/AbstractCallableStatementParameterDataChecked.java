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
import java.sql.CallableStatement;
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
import java.util.Objects;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractCallableStatementParameterDataChecked implements ResultSetColumnDataChecked {

    protected abstract CallableStatement cs();
    
    protected abstract int index();
    
    protected <T> T unsupported(final T t) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Array getArray() throws SQLException {
        return cs().getArray(index());
    }

    @Override
    public void setArray(final Array v) throws SQLException {
        cs().setArray(index(), v);
    }

    @Override
    public InputStream getAsciiStream() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setAsciiStream(final InputStream v) throws SQLException {
        cs().setAsciiStream(index(), v);
    }

    @Override
    public void setAsciiStream(final InputStream v, int len) throws SQLException {
        cs().setAsciiStream(index(), v, len);
    }

    @Override
    public void setAsciiStream(final InputStream v, long len) throws SQLException {
        cs().setAsciiStream(index(), v, len);
    }

    @Override
    public BigDecimal getBigDecimal() throws SQLException {
        return cs().getBigDecimal(index());
    }

    @Override
    public void setBigDecimal(final BigDecimal v) throws SQLException {
        cs().setBigDecimal(index(), v);
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setBinaryStream(final InputStream v) throws SQLException {
        cs().setBinaryStream(index(), v);
    }

    @Override
    public void setBinaryStream(final InputStream v, int len) throws SQLException {
        cs().setBinaryStream(index(), v, len);
    }

    @Override
    public void setBinaryStream(final InputStream v, long len) throws SQLException {
        cs().setBinaryStream(index(), v, len);
    }

    @Override
    public Blob getBlob() throws SQLException {
        return cs().getBlob(index());
    }

    @Override
    public void setBlob(final Blob v) throws SQLException {
        cs().setBlob(index(), v);
    }

    @Override
    public void setBlob(final InputStream v) throws SQLException {
        cs().setBlob(index(), v);
    }

    @Override
    public void setBlob(final InputStream v, int len) throws SQLException {
        cs().setBlob(index(), v, len);
    }

    @Override
    public void setBlob(final InputStream v, long len) throws SQLException {
        cs().setBlob(index(), v, len);
    }

    @Override
    public Boolean getBoolean() throws SQLException {
        final boolean b = cs().getBoolean(index());
        return cs().wasNull() ? null : b;
    }

    @Override
    public void setBoolean(final Boolean v) throws SQLException {
        if (v == null) {
            cs().setNull(index(), Type.BOOLEAN.value());
        } else {
            cs().setBoolean(index(), v);
        }
    }

    @Override
    public Byte getByte() throws SQLException {
        final byte b = cs().getByte(index());
        return cs().wasNull() ? null : b;
    }

    @Override
    public void setByte(final Byte v) throws SQLException {
        if (v == null) {
            cs().setNull(index(), Type.TINYINT.value());
        } else {
            cs().setByte(index(), v);
        }
    }

    @Override
    public byte[] getBytes() throws SQLException {
        final byte[] b = cs().getBytes(index());
        return cs().wasNull() ? null : b;
    }

    @Override
    public void setBytes(final byte[] v) throws SQLException {
        if (v == null) {
            cs().setNull(index(), Type.BINARY.value());
        } else {
            cs().setBytes(index(), v);
        }
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        return cs().getCharacterStream(index());
    }

    @Override
    public void setCharacterStream(final Reader v) throws SQLException {
        cs().setCharacterStream(index(), v);
    }

    @Override
    public void setCharacterStream(final Reader v, int len) throws SQLException {
        cs().setCharacterStream(index(), v, len);
    }

    @Override
    public void setCharacterStream(final Reader v, long len) throws SQLException {
        cs().setCharacterStream(index(), v, len);
    }

    @Override
    public Clob getClob() throws SQLException {
        return cs().getClob(index());
    }

    @Override
    public void setClob(final Clob v) throws SQLException {
        cs().setClob(index(), v);
    }

    @Override
    public void setClob(final Reader v) throws SQLException {
        cs().setClob(index(), v);
    }

    @Override
    public void setClob(final Reader v, int len) throws SQLException {
        cs().setClob(index(), v, len);
    }

    @Override
    public void setClob(final Reader v, long len) throws SQLException {
        cs().setClob(index(), v, len);
    }

    @Override
    public Date getDate() throws SQLException {
        return cs().getDate(index());
    }

    @Override
    public Date getDate(final Calendar c) throws SQLException {
        return cs().getDate(index(), c);
    }

    @Override
    public void setDate(final Date v) throws SQLException {
        cs().setDate(index(), v);
    }

    @Override
    public Double getDouble() throws SQLException {
        final double b = cs().getDouble(index());
        return cs().wasNull() ? null : b;
    }

    @Override
    public void setDouble(final Double v) throws SQLException {
        if (v == null) {
            cs().setNull(index(), Type.DOUBLE.value());
        } else {
            cs().setDouble(index(), v);
        }
    }

    @Override
    public Float getFloat() throws SQLException {
        final float b = cs().getFloat(index());
        return cs().wasNull() ? null : b;
    }

    @Override
    public void setFloat(final Float v) throws SQLException {
        if (v == null) {
            cs().setNull(index(), Type.FLOAT.value());
        } else {
            cs().setFloat(index(), v);
        }
    }

    @Override
    public Integer getInt() throws SQLException {
        final int b = cs().getInt(index());
        return cs().wasNull() ? null : b;
    }

    @Override
    public void setInt(final Integer v) throws SQLException {
        if (v == null) {
            cs().setNull(index(), Type.INTEGER.value());
        } else {
            cs().setInt(index(), v);
        }
    }

    @Override
    public Long getLong() throws SQLException {
        final long b = cs().getLong(index());
        return cs().wasNull() ? null : b;
    }

    @Override
    public void setLong(final Long v) throws SQLException {
        if (v == null) {
            cs().setNull(index(), Type.INTEGER.value()); 
        } else {
            cs().setLong(index(), v);
        }
    }


    @Override
    public Reader getNCharacterStream() throws SQLException {
        return cs().getNCharacterStream(index());
    }

    @Override
    public void setNCharacterStream(final Reader v) throws SQLException {
        cs().setNCharacterStream(index(), v);
    }

    @Override
    public void setNCharacterStream(final Reader v, int len) throws SQLException {
        cs().setNCharacterStream(index(), v, len);
    }

    @Override
    public void setNCharacterStream(final Reader v, long len) throws SQLException {
        cs().setNCharacterStream(index(), v, len);
    }

    @Override
    public NClob getNClob() throws SQLException {
        return cs().getNClob(index());
    }

    @Override
    public void setNClob(final NClob v) throws SQLException {
        cs().setNClob(index(), v);
    }

    @Override
    public void setNClob(final Reader v) throws SQLException {
        cs().setNClob(index(), v);
    }

    @Override
    public void setNClob(final Reader v, int len) throws SQLException {
        cs().setNClob(index(), v, len);
    }

    @Override
    public void setNClob(final Reader v, long len) throws SQLException {
        cs().setNClob(index(), v, len);
    }

    @Override
    public void setNull() throws SQLException {
        cs().setNull(index(), Type.OTHER.value());
    }

    @Override
    public void setNull(final Type t) throws SQLException {
        cs().setNull(index(), t.value());
    }

    @Override
    public String getNString() throws SQLException {
        return cs().getNString(index());
    }

    @Override
    public void setNString(final String v) throws SQLException {
        cs().setNString(index(), v);
    }

    @Override
    public Object getObject() throws SQLException {
        return cs().getObject(index());
    }

    @Override
    public <T> T getObject(final Class<T> type) throws SQLException {
        return cs().getObject(index(), type);
    }

    @Override
    public Object getObject(final Map<String, Class<?>> types) throws SQLException {
        return cs().getObject(index(), types);
    }

    @Override
    public void setObject(final Object v) throws SQLException {
        cs().setObject(index(), v);
    }

    @Override
    public void setObject(final Object v, int scaleOrLen) throws SQLException {
        cs().setObject(index(), v, scaleOrLen);
    }

    @Override
    public void setObject(final Object v, final SQLType type, final int scaleOrLen) throws SQLException {
        cs().setObject(index(), v, type, scaleOrLen);
    }

    @Override
    public Ref getRef() throws SQLException {
        return cs().getRef(index());
    }

    @Override
    public void setRef(final Ref v) throws SQLException {
        cs().setRef(index(), v);
    }

    @Override
    public RowId getRowId() throws SQLException {
        return cs().getRowId(index());
    }

    @Override
    public void setRowId(final RowId v) throws SQLException {
        cs().setRowId(index(), v);
    }

    @Override
    public Short getShort() throws SQLException {
        final short b = cs().getShort(index());
        return cs().wasNull() ? null : b;
    }

    @Override
    public void setShort(final Short v) throws SQLException {
        if (v == null) {
            cs().setNull(index(), Type.TINYINT.value());
        } else {
            cs().setShort(index(), v);
        }
    }

    @Override
    public SQLXML getSQLXML() throws SQLException {
        return cs().getSQLXML(index());
    }

    @Override
    public void setSQLXML(final SQLXML v) throws SQLException {
        cs().setSQLXML(index(), v);
    }

    @Override
    public String getString() throws SQLException {
        return cs().getString(index());
    }

    @Override
    public void setString(final String v) throws SQLException {
        cs().setString(index(), v);
    }

    @Override
    public Time getTime() throws SQLException {
        return cs().getTime(index());
    }

    @Override
    public Time getTime(final Calendar c) throws SQLException {
        return cs().getTime(index(), c);
    }

    @Override
    public void setTime(final Time v) throws SQLException {
        cs().setTime(index(), v);
    }

    @Override
    public Timestamp getTimestamp() throws SQLException {
        return cs().getTimestamp(index());
    }

    @Override
    public Timestamp getTimestamp(final Calendar c) throws SQLException {
        return cs().getTimestamp(index(), c);
    }

    @Override
    public void setTimestamp(final Timestamp v) throws SQLException {
        cs().setTimestamp(index(), v);
    }

    @Override
    public URL getURL() throws SQLException {
        return cs().getURL(index());
    }

    @Override
    public boolean equals(final Object other) {
        if (other == null || !(other instanceof ResultSetColumnDataChecked)) {
            return false;
        }
        try {
            return Objects.equals(getObject(), 
                    ResultSetColumnDataChecked.class.cast(other).getObject());
        } catch (final SQLException e) {
            throw new UncheckedSQLException(e);
        }
    }
    
    @Override
    public int hashCode() {
        try {
            return Objects.hashCode(getObject());
        } catch (final SQLException e) {
            throw new UncheckedSQLException(e);
        }
    }
    
    @Override
    public String toString() {
        try {
            return StringUtils.toString(getObject());
        } catch (final SQLException e) {
            throw new UncheckedSQLException(e);
        }
    }
    
}
