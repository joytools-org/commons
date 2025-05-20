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
public abstract class AbstractPreparedStatementParameterData implements ResultSetColumnData {

    protected abstract PreparedStatement ps();
    
    protected abstract int index();
    
    protected <T> T unsupported(final T t) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Array getArray() {
        return unsupported(null);
    }

    @Override
    public void setArray(final Array v) {
        try {
            ps().setArray(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public InputStream getAsciiStream() {
        return unsupported(null);
    }

    @Override
    public void setAsciiStream(final InputStream v) {
        try {
            ps().setAsciiStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setAsciiStream(final InputStream v, int len) {
        try {
            ps().setAsciiStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setAsciiStream(final InputStream v, long len) {
        try {
            ps().setAsciiStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public BigDecimal getBigDecimal() {
        return unsupported(null);
    }

    @Override
    public void setBigDecimal(final BigDecimal v) {
        try {
            ps().setBigDecimal(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public InputStream getBinaryStream() {
        return unsupported(null);
    }

    @Override
    public void setBinaryStream(final InputStream v) {
        try {
            ps().setBinaryStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBinaryStream(final InputStream v, int len) {
        try {
            ps().setBinaryStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBinaryStream(final InputStream v, long len) {
        try {
            ps().setBinaryStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Blob getBlob() {
        return unsupported(null);
    }

    @Override
    public void setBlob(final Blob v) {
        try {
            ps().setBlob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v) {
        try {
            ps().setBlob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v, int len) {
        try {
            ps().setBlob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v, long len) {
        try {
            ps().setBlob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Boolean getBoolean() {
        return unsupported(null);
    }

    @Override
    public void setBoolean(final Boolean v) {
        try {
            if (v == null) {
                ps().setNull(index(), Type.BOOLEAN.value());
            } else {
                ps().setBoolean(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Byte getByte() {
        return unsupported(null);
    }

    @Override
    public void setByte(final Byte v) {
        try { 
            if (v == null) {
                ps().setNull(index(), Type.TINYINT.value());
            } else {
                ps().setByte(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public byte[] getBytes() {
        return unsupported(null);
    }

    @Override
    public void setBytes(final byte[] v) {
        try {
            if (v == null) {
                ps().setNull(index(), Type.BINARY.value());
            } else {
                ps().setBytes(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Reader getCharacterStream() {
        return unsupported(null);
    }

    @Override
    public void setCharacterStream(final Reader v) {
        try {
            ps().setCharacterStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setCharacterStream(final Reader v, int len) {
        try {
            ps().setCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setCharacterStream(final Reader v, long len) {
        try {
            ps().setCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Clob getClob() {
        return unsupported(null);
    }

    @Override
    public void setClob(final Clob v) {
        try {
            ps().setClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v) {
        try {
            ps().setClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v, int len) {
        try {
            ps().setClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v, long len) {
        try {
            ps().setClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Date getDate() {
        return unsupported(null);
    }

    @Override
    public Date getDate(final Calendar c) {
        return unsupported(null);
    }

    @Override
    public void setDate(final Date v) {
        try {
            ps().setDate(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Double getDouble() {
        return unsupported(null);
    }

    @Override
    public void setDouble(final Double v) {
        try {
            if (v == null) {
                ps().setNull(index(), Type.DOUBLE.value());
            } else {
                ps().setDouble(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Float getFloat() {
        return unsupported(null);
    }

    @Override
    public void setFloat(final Float v) {
        try {
            if (v == null) {
                ps().setNull(index(), Type.FLOAT.value());
            } else {
                ps().setFloat(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Integer getInt() {
        return unsupported(null);
    }

    @Override
    public void setInt(final Integer v) {
        try {
            if (v == null) {
                ps().setNull(index(), Type.INTEGER.value());
            } else {
                ps().setInt(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Long getLong() {
        return unsupported(null);
    }

    @Override
    public void setLong(final Long v) {
        try {
            if (v == null) {
                ps().setNull(index(), Type.INTEGER.value()); 
            } else {
                ps().setLong(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }


    @Override
    public Reader getNCharacterStream() {
        return unsupported(null);
    }

    @Override
    public void setNCharacterStream(final Reader v) {
        try {
            ps().setNCharacterStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNCharacterStream(final Reader v, int len) {
        try {
            ps().setNCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNCharacterStream(final Reader v, long len) {
        try {
            ps().setNCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public NClob getNClob() {
        return unsupported(null);
    }

    @Override
    public void setNClob(final NClob v) {
        try {
            ps().setNClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v) {
        try {
            ps().setNClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v, int len) {
        try {
            ps().setNClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v, long len) {
        try {
            ps().setNClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNull() {
        try {
            ps().setNull(index(), Type.OTHER.value());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNull(final Type t) {
        try {
            ps().setNull(index(), t.value());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public String getNString() {
        return unsupported(null);
    }

    @Override
    public void setNString(final String v) {
        try {
            ps().setNString(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Object getObject() {
        return unsupported(null);
    }

    @Override
    public <T> T getObject(final Class<T> type) {
        return unsupported(null);
    }

    @Override
    public Object getObject(final Map<String, Class<?>> types) {
        return unsupported(null);
    }

    @Override
    public void setObject(final Object v) {
        try {
            ps().setObject(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setObject(final Object v, int scaleOrLen) {
        try {
            ps().setObject(index(), v, scaleOrLen);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setObject(final Object v, final SQLType type, final int scaleOrLen) {
        try {
            ps().setObject(index(), v, type, scaleOrLen);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Ref getRef() {
        return unsupported(null);
    }

    @Override
    public void setRef(final Ref v) {
        try {
            ps().setRef(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public RowId getRowId() {
        return unsupported(null);
    }

    @Override
    public void setRowId(final RowId v) {
        try {
            ps().setRowId(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Short getShort() {
        return unsupported(null);
    }

    @Override
    public void setShort(final Short v) {
        try {
            if (v == null) {
                ps().setNull(index(), Type.TINYINT.value());
            } else {
                ps().setShort(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public SQLXML getSQLXML() {
        return unsupported(null);
    }

    @Override
    public void setSQLXML(final SQLXML v) {
        try {
            ps().setSQLXML(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public String getString() {
        return unsupported(null);
    }

    @Override
    public void setString(final String v) {
        try {
            ps().setString(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Time getTime() {
        return unsupported(null);
    }

    @Override
    public Time getTime(final Calendar c) {
        return unsupported(null);
    }

    @Override
    public void setTime(final Time v) {
        try {
            ps().setTime(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Timestamp getTimestamp() {
        return unsupported(null);
    }

    @Override
    public Timestamp getTimestamp(final Calendar c) {
        return unsupported(null);
    }

    @Override
    public void setTimestamp(final Timestamp v) {
        try {
            ps().setTimestamp(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public URL getURL() {
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
        return Objects.hashCode(getObject());
    }
    
    @Override
    public String toString() {
        return StringUtils.toString(getObject());
    }
    
}
