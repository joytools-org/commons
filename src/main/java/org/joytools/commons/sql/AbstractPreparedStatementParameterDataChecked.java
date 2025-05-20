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
import java.sql.PreparedStatement;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractPreparedStatementParameterDataChecked implements ResultSetColumnDataChecked {

    protected abstract PreparedStatement ps();
    
    protected abstract int index();
    
    protected <T> T unsupported(final T t) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Array getArray() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setArray(final Array v) throws SQLException {
        ps().setArray(index(), v);
    }

    @Override
    public InputStream getAsciiStream() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setAsciiStream(final InputStream v) throws SQLException {
        ps().setAsciiStream(index(), v);
    }

    @Override
    public void setAsciiStream(final InputStream v, int len) throws SQLException {
        ps().setAsciiStream(index(), v, len);
    }

    @Override
    public void setAsciiStream(final InputStream v, long len) throws SQLException {
        ps().setAsciiStream(index(), v, len);
    }

    @Override
    public BigDecimal getBigDecimal() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setBigDecimal(final BigDecimal v) throws SQLException {
        ps().setBigDecimal(index(), v);
    }

    @Override
    public InputStream getBinaryStream() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setBinaryStream(final InputStream v) throws SQLException {
        ps().setBinaryStream(index(), v);
    }

    @Override
    public void setBinaryStream(final InputStream v, int len) throws SQLException {
        ps().setBinaryStream(index(), v, len);
    }

    @Override
    public void setBinaryStream(final InputStream v, long len) throws SQLException {
        ps().setBinaryStream(index(), v, len);
    }

    @Override
    public Blob getBlob() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setBlob(final Blob v) throws SQLException {
        ps().setBlob(index(), v);
    }

    @Override
    public void setBlob(final InputStream v) throws SQLException {
        ps().setBlob(index(), v);
    }

    @Override
    public void setBlob(final InputStream v, int len) throws SQLException {
        ps().setBlob(index(), v, len);
    }

    @Override
    public void setBlob(final InputStream v, long len) throws SQLException {
        ps().setBlob(index(), v, len);
    }

    @Override
    public Boolean getBoolean() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setBoolean(final Boolean v) throws SQLException {
        if (v == null) {
            ps().setNull(index(), Type.BOOLEAN.value());
        } else {
            ps().setBoolean(index(), v);
        }
    }

    @Override
    public Byte getByte() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setByte(final Byte v) throws SQLException {
        if (v == null) {
            ps().setNull(index(), Type.TINYINT.value());
        } else {
            ps().setByte(index(), v);
        }
    }

    @Override
    public byte[] getBytes() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setBytes(final byte[] v) throws SQLException {
        if (v == null) {
            ps().setNull(index(), Type.BINARY.value());
        } else {
            ps().setBytes(index(), v);
        }
    }

    @Override
    public Reader getCharacterStream() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setCharacterStream(final Reader v) throws SQLException {
        ps().setCharacterStream(index(), v);
    }

    @Override
    public void setCharacterStream(final Reader v, int len) throws SQLException {
        ps().setCharacterStream(index(), v, len);
    }

    @Override
    public void setCharacterStream(final Reader v, long len) throws SQLException {
        ps().setCharacterStream(index(), v, len);
    }

    @Override
    public Clob getClob() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setClob(final Clob v) throws SQLException {
        ps().setClob(index(), v);
    }

    @Override
    public void setClob(final Reader v) throws SQLException {
        ps().setClob(index(), v);
    }

    @Override
    public void setClob(final Reader v, int len) throws SQLException {
        ps().setClob(index(), v, len);
    }

    @Override
    public void setClob(final Reader v, long len) throws SQLException {
        ps().setClob(index(), v, len);
    }

    @Override
    public Date getDate() throws SQLException {
        return unsupported(null);
    }

    @Override
    public Date getDate(final Calendar c) throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setDate(final Date v) throws SQLException {
        ps().setDate(index(), v);
    }

    @Override
    public Double getDouble() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setDouble(final Double v) throws SQLException {
        if (v == null) {
            ps().setNull(index(), Type.DOUBLE.value());
        } else {
            ps().setDouble(index(), v);
        }
    }

    @Override
    public Float getFloat() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setFloat(final Float v) throws SQLException {
        if (v == null) {
            ps().setNull(index(), Type.FLOAT.value());
        } else {
            ps().setFloat(index(), v);
        }
    }

    @Override
    public Integer getInt() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setInt(final Integer v) throws SQLException {
        if (v == null) {
            ps().setNull(index(), Type.INTEGER.value());
        } else {
            ps().setInt(index(), v);
        }
    }

    @Override
    public Long getLong() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setLong(final Long v) throws SQLException {
        if (v == null) {
            ps().setNull(index(), Type.INTEGER.value()); 
        } else {
            ps().setLong(index(), v);
        }
    }


    @Override
    public Reader getNCharacterStream() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setNCharacterStream(final Reader v) throws SQLException {
        ps().setNCharacterStream(index(), v);
    }

    @Override
    public void setNCharacterStream(final Reader v, int len) throws SQLException {
        ps().setNCharacterStream(index(), v, len);
    }

    @Override
    public void setNCharacterStream(final Reader v, long len) throws SQLException {
        ps().setNCharacterStream(index(), v, len);
    }

    @Override
    public NClob getNClob() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setNClob(final NClob v) throws SQLException {
        ps().setNClob(index(), v);
    }

    @Override
    public void setNClob(final Reader v) throws SQLException {
        ps().setNClob(index(), v);
    }

    @Override
    public void setNClob(final Reader v, int len) throws SQLException {
        ps().setNClob(index(), v, len);
    }

    @Override
    public void setNClob(final Reader v, long len) throws SQLException {
        ps().setNClob(index(), v, len);
    }

    @Override
    public void setNull() throws SQLException {
        ps().setNull(index(), Type.OTHER.value());
    }

    @Override
    public void setNull(final Type t) throws SQLException {
        ps().setNull(index(), t.value());
    }

    @Override
    public String getNString() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setNString(final String v) throws SQLException {
        ps().setNString(index(), v);
    }

    @Override
    public Object getObject() throws SQLException {
        return unsupported(null);
    }

    @Override
    public <T> T getObject(final Class<T> type) throws SQLException {
        return unsupported(null);
    }

    @Override
    public Object getObject(final Map<String, Class<?>> types) throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setObject(final Object v) throws SQLException {
        ps().setObject(index(), v);
    }

    @Override
    public void setObject(final Object v, int scaleOrLen) throws SQLException {
        ps().setObject(index(), v, scaleOrLen);
    }

    @Override
    public void setObject(final Object v, final SQLType type, final int scaleOrLen) throws SQLException {
        ps().setObject(index(), v, type, scaleOrLen);
    }

    @Override
    public Ref getRef() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setRef(final Ref v) throws SQLException {
        ps().setRef(index(), v);
    }

    @Override
    public RowId getRowId() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setRowId(final RowId v) throws SQLException {
        ps().setRowId(index(), v);
    }

    @Override
    public Short getShort() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setShort(final Short v) throws SQLException {
        if (v == null) {
            ps().setNull(index(), Type.TINYINT.value());
        } else {
            ps().setShort(index(), v);
        }
    }

    @Override
    public SQLXML getSQLXML() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setSQLXML(final SQLXML v) throws SQLException {
        ps().setSQLXML(index(), v);
    }

    @Override
    public String getString() throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setString(final String v) throws SQLException {
        ps().setString(index(), v);
    }

    @Override
    public Time getTime() throws SQLException {
        return unsupported(null);
    }

    @Override
    public Time getTime(final Calendar c) throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setTime(final Time v) throws SQLException {
        ps().setTime(index(), v);
    }

    @Override
    public Timestamp getTimestamp() throws SQLException {
        return unsupported(null);
    }

    @Override
    public Timestamp getTimestamp(final Calendar c) throws SQLException {
        return unsupported(null);
    }

    @Override
    public void setTimestamp(final Timestamp v) throws SQLException {
        ps().setTimestamp(index(), v);
    }

    @Override
    public URL getURL() throws SQLException {
        return unsupported(null);
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
