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
public abstract class AbstractCallableStatementParameterData implements ResultSetColumnData {

    protected abstract CallableStatement cs();
    
    protected abstract int index();
    
    protected <T> T unsupported(final T t) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Array getArray() {
        try {
            return cs().getArray(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setArray(final Array v) {
        try {
            cs().setArray(index(), v);
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
            cs().setAsciiStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setAsciiStream(final InputStream v, int len) {
        try {
            cs().setAsciiStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setAsciiStream(final InputStream v, long len) {
        try {
            cs().setAsciiStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public BigDecimal getBigDecimal() {
        try {
            return cs().getBigDecimal(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBigDecimal(final BigDecimal v) {
        try {
            cs().setBigDecimal(index(), v);
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
            cs().setBinaryStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBinaryStream(final InputStream v, int len) {
        try {
            cs().setBinaryStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBinaryStream(final InputStream v, long len) {
        try {
            cs().setBinaryStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Blob getBlob() {
        try {
            return cs().getBlob(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final Blob v) {
        try {
            cs().setBlob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v) {
        try {
            cs().setBlob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v, int len) {
        try {
            cs().setBlob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v, long len) {
        try {
            cs().setBlob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Boolean getBoolean() {
        try {
            final boolean b = cs().getBoolean(index());
            return cs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBoolean(final Boolean v) {
        try {
            if (v == null) {
                cs().setNull(index(), Type.BOOLEAN.value());
            } else {
                cs().setBoolean(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Byte getByte() {
        try {
            final byte b = cs().getByte(index());
            return cs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setByte(final Byte v) {
        try {
            if (v == null) {
                cs().setNull(index(), Type.TINYINT.value());
            } else {
                cs().setByte(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
}

    @Override
    public byte[] getBytes() {
        try {
            final byte[] b = cs().getBytes(index());
            return cs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBytes(final byte[] v) {
        try {
            if (v == null) {
                cs().setNull(index(), Type.BINARY.value());
            } else {
                cs().setBytes(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Reader getCharacterStream() {
        try {
            return cs().getCharacterStream(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setCharacterStream(final Reader v) {
        try {
            cs().setCharacterStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setCharacterStream(final Reader v, int len) {
        try {
            cs().setCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setCharacterStream(final Reader v, long len) {
        try {
            cs().setCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Clob getClob() {
        try {
            return cs().getClob(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Clob v) {
        try {
            cs().setClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v) {
        try {
            cs().setClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v, int len) {
        try {
            cs().setClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v, long len) {
        try {
            cs().setClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Date getDate() {
        try {
            return cs().getDate(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Date getDate(final Calendar c) {
        try {
            return cs().getDate(index(), c);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setDate(final Date v) {
        try {
            cs().setDate(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Double getDouble() {
        try {
            final double b = cs().getDouble(index());
            return cs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setDouble(final Double v) {
        try {
            if (v == null) {
                cs().setNull(index(), Type.DOUBLE.value());
            } else {
                cs().setDouble(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Float getFloat() {
        try {
            final float b = cs().getFloat(index());
            return cs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setFloat(final Float v) {
        try {
            if (v == null) {
                cs().setNull(index(), Type.FLOAT.value());
            } else {
                cs().setFloat(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Integer getInt() {
        try {
            final int b = cs().getInt(index());
            return cs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setInt(final Integer v) {
        try {
            if (v == null) {
                cs().setNull(index(), Type.INTEGER.value());
            } else {
                cs().setInt(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Long getLong() {
        try {
            final long b = cs().getLong(index());
            return cs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setLong(final Long v) {
        try {
            if (v == null) {
                cs().setNull(index(), Type.INTEGER.value()); 
            } else {
                cs().setLong(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }


    @Override
    public Reader getNCharacterStream() {
        try {
            return cs().getNCharacterStream(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNCharacterStream(final Reader v) {
        try {
            cs().setNCharacterStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNCharacterStream(final Reader v, int len) {
        try {
            cs().setNCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNCharacterStream(final Reader v, long len) {
        try {
            cs().setNCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public NClob getNClob() {
        try {
            return cs().getNClob(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final NClob v) {
        try {
            cs().setNClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v) {
        try {
            cs().setNClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v, int len) {
        try {
            cs().setNClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v, long len) {
        try {
            cs().setNClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNull() {
        try {
            cs().setNull(index(), Type.OTHER.value());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNull(final Type t) {
        try {
            cs().setNull(index(), t.value());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public String getNString() {
        try {
            return cs().getNString(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNString(final String v) {
        try {
            cs().setNString(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Object getObject() {
        try {
            return cs().getObject(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public <T> T getObject(final Class<T> type) {
        try {
            return cs().getObject(index(), type);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Object getObject(final Map<String, Class<?>> types) {
        try {
            return cs().getObject(index(), types);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setObject(final Object v) {
        try {
            cs().setObject(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setObject(final Object v, int scaleOrLen) {
        try {
            cs().setObject(index(), v, scaleOrLen);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setObject(final Object v, final SQLType type, final int scaleOrLen) {
        try {
            cs().setObject(index(), v, type, scaleOrLen);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Ref getRef() {
        try {
            return cs().getRef(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setRef(final Ref v) {
        try {
            cs().setRef(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public RowId getRowId() {
        try {
            return cs().getRowId(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setRowId(final RowId v) {
        try {
            cs().setRowId(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Short getShort() {
        try {
            final short b = cs().getShort(index());
            return cs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setShort(final Short v) {
        try {
            if (v == null) {
                cs().setNull(index(), Type.TINYINT.value());
            } else {
                cs().setShort(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public SQLXML getSQLXML() {
        try {
            return cs().getSQLXML(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setSQLXML(final SQLXML v) {
        try {
            cs().setSQLXML(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public String getString() {
        try {
            return cs().getString(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setString(final String v) {
        try {
            cs().setString(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Time getTime() {
        try {
            return cs().getTime(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Time getTime(final Calendar c) {
        try {
            return cs().getTime(index(), c);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setTime(final Time v) {
        try {
            cs().setTime(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Timestamp getTimestamp() {
        try {
            return cs().getTimestamp(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Timestamp getTimestamp(final Calendar c) {
        try {
            return cs().getTimestamp(index(), c);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setTimestamp(final Timestamp v) {
        try {
            cs().setTimestamp(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public URL getURL() {
        try {
            return cs().getURL(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
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
