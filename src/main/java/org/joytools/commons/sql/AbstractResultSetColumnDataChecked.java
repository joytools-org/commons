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
import java.sql.ResultSet;
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
public abstract class AbstractResultSetColumnDataChecked implements ResultSetColumnDataChecked {

    protected abstract ResultSet rs();
    
    protected abstract int index();
    
    @Override
    public Array getArray() throws SQLException {
        return rs().getArray(index());
    }

    @Override
    public void setArray(final Array v) throws SQLException {
        rs().updateArray(index(), v);
    }

    @Override
    public InputStream getAsciiStream() throws SQLException {
        return rs().getAsciiStream(index());
    }

    @Override
    public void setAsciiStream(final InputStream v) throws SQLException {
        rs().updateAsciiStream(index(), v);
    }

    @Override
    public void setAsciiStream(final InputStream v, int len) throws SQLException {
        rs().updateAsciiStream(index(), v, len);
    }

    @Override
    public void setAsciiStream(final InputStream v, long len) throws SQLException {
        rs().updateAsciiStream(index(), v, len);
    }

    @Override
    public BigDecimal getBigDecimal() throws SQLException {
        return rs().getBigDecimal(index());
    }

    @Override
    public void setBigDecimal(final BigDecimal v) throws SQLException {
        rs().updateBigDecimal(index(), v);
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        return rs().getBinaryStream(index());
    }

    @Override
    public void setBinaryStream(final InputStream v) throws SQLException {
        rs().updateBinaryStream(index(), v);
    }

    @Override
    public void setBinaryStream(final InputStream v, int len) throws SQLException {
        rs().updateBinaryStream(index(), v, len);
    }

    @Override
    public void setBinaryStream(final InputStream v, long len) throws SQLException {
        rs().updateBinaryStream(index(), v, len);
    }

    @Override
    public Blob getBlob() throws SQLException {
        return rs().getBlob(index());
    }

    @Override
    public void setBlob(final Blob v) throws SQLException {
        rs().updateBlob(index(), v);
    }

    @Override
    public void setBlob(final InputStream v) throws SQLException {
        rs().updateBlob(index(), v);
    }

    @Override
    public void setBlob(final InputStream v, int len) throws SQLException {
        rs().updateBlob(index(), v, len);
    }

    @Override
    public void setBlob(final InputStream v, long len) throws SQLException {
        rs().updateBlob(index(), v, len);
    }

    @Override
    public Boolean getBoolean() throws SQLException {
        final boolean b = rs().getBoolean(index());
        return rs().wasNull() ? null : b;
    }

    @Override
    public void setBoolean(final Boolean v) throws SQLException {
        if (v == null) {
            rs().updateNull(index());
        } else {
            rs().updateBoolean(index(), v);
        }
    }

    @Override
    public Byte getByte() throws SQLException {
        final byte b = rs().getByte(index());
        return rs().wasNull() ? null : b;
    }

    @Override
    public void setByte(final Byte v) throws SQLException {
        if (v == null) {
            rs().updateNull(index());
        } else {
            rs().updateByte(index(), v);
        }
    }

    @Override
    public byte[] getBytes() throws SQLException {
        final byte[] b = rs().getBytes(index());
        return rs().wasNull() ? null : b;
    }

    @Override
    public void setBytes(final byte[] v) throws SQLException {
        if (v == null) {
            rs().updateNull(index());
        } else {
            rs().updateBytes(index(), v);
        }
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        return rs().getCharacterStream(index());
    }

    @Override
    public void setCharacterStream(final Reader v) throws SQLException {
        rs().updateCharacterStream(index(), v);
    }

    @Override
    public void setCharacterStream(final Reader v, int len) throws SQLException {
        rs().updateCharacterStream(index(), v, len);
    }

    @Override
    public void setCharacterStream(final Reader v, long len) throws SQLException {
        rs().updateCharacterStream(index(), v, len);
    }

    @Override
    public Clob getClob() throws SQLException {
        return rs().getClob(index());
    }

    @Override
    public void setClob(final Clob v) throws SQLException {
        rs().updateClob(index(), v);
    }

    @Override
    public void setClob(final Reader v) throws SQLException {
        rs().updateClob(index(), v);
    }

    @Override
    public void setClob(final Reader v, int len) throws SQLException {
        rs().updateClob(index(), v, len);
    }

    @Override
    public void setClob(final Reader v, long len) throws SQLException {
        rs().updateClob(index(), v, len);
    }

    @Override
    public Date getDate() throws SQLException {
        return rs().getDate(index());
    }

    @Override
    public Date getDate(final Calendar c) throws SQLException {
        return rs().getDate(index(), c);
    }

    @Override
    public void setDate(final Date v) throws SQLException {
        rs().updateDate(index(), v);
    }

    @Override
    public Double getDouble() throws SQLException {
        final double b = rs().getDouble(index());
        return rs().wasNull() ? null : b;
    }

    @Override
    public void setDouble(final Double v) throws SQLException {
        if (v == null) {
            rs().updateNull(index());
        } else {
            rs().updateDouble(index(), v);
        }
    }

    @Override
    public Float getFloat() throws SQLException {
        final float b = rs().getFloat(index());
        return rs().wasNull() ? null : b;
    }

    @Override
    public void setFloat(final Float v) throws SQLException {
        if (v == null) {
            rs().updateNull(index());
        } else {
            rs().updateFloat(index(), v);
        }
    }

    @Override
    public Integer getInt() throws SQLException {
        final int b = rs().getInt(index());
        return rs().wasNull() ? null : b;
    }

    @Override
    public void setInt(final Integer v) throws SQLException {
        if (v == null) {
            rs().updateNull(index());
        } else {
            rs().updateInt(index(), v);
        }
    }

    @Override
    public Long getLong() throws SQLException {
        final long b = rs().getLong(index());
        return rs().wasNull() ? null : b;
    }

    @Override
    public void setLong(final Long v) throws SQLException {
        if (v == null) {
            rs().updateNull(index());
        } else {
            rs().updateLong(index(), v);
        }
    }


    @Override
    public Reader getNCharacterStream() throws SQLException {
        return rs().getNCharacterStream(index());
    }

    @Override
    public void setNCharacterStream(final Reader v) throws SQLException {
        rs().updateNCharacterStream(index(), v);
    }

    @Override
    public void setNCharacterStream(final Reader v, int len) throws SQLException {
        rs().updateNCharacterStream(index(), v, len);
    }

    @Override
    public void setNCharacterStream(final Reader v, long len) throws SQLException {
        rs().updateNCharacterStream(index(), v, len);
    }

    @Override
    public NClob getNClob() throws SQLException {
        return rs().getNClob(index());
    }

    @Override
    public void setNClob(final NClob v) throws SQLException {
        rs().updateNClob(index(), v);
    }

    @Override
    public void setNClob(final Reader v) throws SQLException {
        rs().updateNClob(index(), v);
    }

    @Override
    public void setNClob(final Reader v, int len) throws SQLException {
        rs().updateNClob(index(), v, len);
    }

    @Override
    public void setNClob(final Reader v, long len) throws SQLException {
        rs().updateNClob(index(), v, len);
    }

    @Override
    public void setNull() throws SQLException {
        rs().updateNull(index());
    }

    @Override
    public void setNull(final Type t) throws SQLException {
        rs().updateNull(index());
    }
    
    @Override
    public String getNString() throws SQLException {
        return rs().getNString(index());
    }

    @Override
    public void setNString(final String v) throws SQLException {
        rs().updateNString(index(), v);
    }

    @Override
    public Object getObject() throws SQLException {
        return rs().getObject(index());
    }

    @Override
    public <T> T getObject(final Class<T> type) throws SQLException {
        return rs().getObject(index(), type);
    }

    @Override
    public Object getObject(final Map<String, Class<?>> types) throws SQLException {
        return rs().getObject(index(), types);
    }

    @Override
    public void setObject(final Object v) throws SQLException {
        rs().updateObject(index(), v);
    }

    @Override
    public void setObject(final Object v, int scaleOrLen) throws SQLException {
        rs().updateObject(index(), v, scaleOrLen);
    }

    @Override
    public void setObject(final Object v, final SQLType type, final int scaleOrLen) throws SQLException {
        rs().updateObject(index(), v, type, scaleOrLen);
    }

    @Override
    public Ref getRef() throws SQLException {
        return rs().getRef(index());
    }

    @Override
    public void setRef(final Ref v) throws SQLException {
        rs().updateRef(index(), v);
    }

    @Override
    public RowId getRowId() throws SQLException {
        return rs().getRowId(index());
    }

    @Override
    public void setRowId(final RowId v) throws SQLException {
        rs().updateRowId(index(), v);
    }

    @Override
    public Short getShort() throws SQLException {
        final short b = rs().getShort(index());
        return rs().wasNull() ? null : b;
    }

    @Override
    public void setShort(final Short v) throws SQLException {
        if (v == null) {
            rs().updateNull(index());
        } else {
            rs().updateShort(index(), v);
        }
    }

    @Override
    public SQLXML getSQLXML() throws SQLException {
        return rs().getSQLXML(index());
    }

    @Override
    public void setSQLXML(final SQLXML v) throws SQLException {
        rs().updateSQLXML(index(), v);
    }

    @Override
    public String getString() throws SQLException {
        return rs().getString(index());
    }

    @Override
    public void setString(final String v) throws SQLException {
        rs().updateString(index(), v);
    }

    @Override
    public Time getTime() throws SQLException {
        return rs().getTime(index());
    }

    @Override
    public Time getTime(final Calendar c) throws SQLException {
        return rs().getTime(index(), c);
    }

    @Override
    public void setTime(final Time v) throws SQLException {
        rs().updateTime(index(), v);
    }

    @Override
    public Timestamp getTimestamp() throws SQLException {
        return rs().getTimestamp(index());
    }

    @Override
    public Timestamp getTimestamp(final Calendar c) throws SQLException {
        return rs().getTimestamp(index(), c);
    }

    @Override
    public void setTimestamp(final Timestamp v) throws SQLException {
        rs().updateTimestamp(index(), v);
    }

    @Override
    public URL getURL() throws SQLException {
        return rs().getURL(index());
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
