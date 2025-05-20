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
public abstract class AbstractResultSetColumnData implements ResultSetColumnData {

    protected abstract ResultSet rs();
    
    protected abstract int index();
    
    @Override
    public Array getArray() {
        try {
            return rs().getArray(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setArray(final Array v) {
        try {
            rs().updateArray(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public InputStream getAsciiStream() {
        try {
            return rs().getAsciiStream(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setAsciiStream(final InputStream v) {
        try {
            rs().updateAsciiStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setAsciiStream(final InputStream v, int len) {
        try {
            rs().updateAsciiStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setAsciiStream(final InputStream v, long len) {
        try {
            rs().updateAsciiStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public BigDecimal getBigDecimal() {
        try {
            return rs().getBigDecimal(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBigDecimal(final BigDecimal v) {
        try {
            rs().updateBigDecimal(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public InputStream getBinaryStream() {
        try {
            return rs().getBinaryStream(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBinaryStream(final InputStream v) {
        try {
            rs().updateBinaryStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBinaryStream(final InputStream v, int len) {
        try {
            rs().updateBinaryStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBinaryStream(final InputStream v, long len) {
        try {
            rs().updateBinaryStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Blob getBlob() {
        try {
            return rs().getBlob(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final Blob v) {
        try {
            rs().updateBlob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v) {
        try {
            rs().updateBlob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v, int len) {
        try {
            rs().updateBlob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBlob(final InputStream v, long len) {
        try {
            rs().updateBlob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Boolean getBoolean() {
        try {
            final boolean b = rs().getBoolean(index());
            return rs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBoolean(final Boolean v) {
        try {
            if (v == null) {
                rs().updateNull(index());
            } else {
                rs().updateBoolean(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Byte getByte() {
        try {
            final byte b = rs().getByte(index());
            return rs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setByte(final Byte v) {
        try {
            if (v == null) {
                rs().updateNull(index());
            } else {
                rs().updateByte(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public byte[] getBytes() {
        try {
            final byte[] b = rs().getBytes(index());
            return rs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setBytes(final byte[] v) {
        try {
            if (v == null) {
                rs().updateNull(index());
            } else {
                rs().updateBytes(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Reader getCharacterStream() {
        try {
            return rs().getCharacterStream(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setCharacterStream(final Reader v) {
        try {
            rs().updateCharacterStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setCharacterStream(final Reader v, int len) {
        try {
            rs().updateCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setCharacterStream(final Reader v, long len) {
        try {
            rs().updateCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Clob getClob() {
        try {
            return rs().getClob(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Clob v) {
        try {
            rs().updateClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v) {
        try {
            rs().updateClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v, int len) {
        try { 
            rs().updateClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setClob(final Reader v, long len) {
        try {
            rs().updateClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Date getDate() {
        try {
            return rs().getDate(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Date getDate(final Calendar c) {
        try {
            return rs().getDate(index(), c);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setDate(final Date v) {
        try {
            rs().updateDate(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Double getDouble() {
        try {
            final double b = rs().getDouble(index());
            return rs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setDouble(final Double v) {
        try {
            if (v == null) {
                rs().updateNull(index());
            } else {
                rs().updateDouble(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Float getFloat() {
        try {
            final float b = rs().getFloat(index());
            return rs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setFloat(final Float v) {
        try {
            if (v == null) {
                rs().updateNull(index());
            } else {
                rs().updateFloat(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
}

    @Override
    public Integer getInt() {
        try {
            final int b = rs().getInt(index());
            return rs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setInt(final Integer v) {
        try {
            if (v == null) {
                rs().updateNull(index());
            } else {
                rs().updateInt(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Long getLong() {
        try {
            final long b = rs().getLong(index());
            return rs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setLong(final Long v) {
        try {
            if (v == null) {
                rs().updateNull(index());
            } else {
                rs().updateLong(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }


    @Override
    public Reader getNCharacterStream() {
        try {
            return rs().getNCharacterStream(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNCharacterStream(final Reader v) {
        try {
            rs().updateNCharacterStream(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNCharacterStream(final Reader v, int len) {
        try {
            rs().updateNCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNCharacterStream(final Reader v, long len) {
        try {
            rs().updateNCharacterStream(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public NClob getNClob() {
        try {
            return rs().getNClob(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final NClob v) {
        try {
            rs().updateNClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v) {
        try {
            rs().updateNClob(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v, int len) {
        try {
            rs().updateNClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNClob(final Reader v, long len) {
        try {
            rs().updateNClob(index(), v, len);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNull() {
        try {
            rs().updateNull(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNull(final Type t) {
        try {
            rs().updateNull(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }
    
    @Override
    public String getNString() {
        try {
            return rs().getNString(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setNString(final String v) {
        try {
            rs().updateNString(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Object getObject() {
        try {
            return rs().getObject(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public <T> T getObject(final Class<T> type) {
        try {
            return rs().getObject(index(), type);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Object getObject(final Map<String, Class<?>> types) {
        try {
            return rs().getObject(index(), types);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setObject(final Object v) {
        try {
            rs().updateObject(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setObject(final Object v, int scaleOrLen) {
        try {
            rs().updateObject(index(), v, scaleOrLen);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setObject(final Object v, final SQLType type, final int scaleOrLen) {
        try {
            rs().updateObject(index(), v, type, scaleOrLen);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Ref getRef() {
        try {
            return rs().getRef(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setRef(final Ref v) {
        try {
            rs().updateRef(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public RowId getRowId() {
        try {
            return rs().getRowId(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setRowId(final RowId v) {
        try {
            rs().updateRowId(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Short getShort() {
        try {
            final short b = rs().getShort(index());
            return rs().wasNull() ? null : b;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setShort(final Short v) {
        try {
            if (v == null) {
                rs().updateNull(index());
            } else {
                rs().updateShort(index(), v);
            }
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public SQLXML getSQLXML() {
        try {
            return rs().getSQLXML(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setSQLXML(final SQLXML v) {
        try {
            rs().updateSQLXML(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public String getString() {
        try {
            return rs().getString(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setString(final String v) {
        try {
            rs().updateString(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Time getTime() {
        try {
            return rs().getTime(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Time getTime(final Calendar c) {
        try {
            return rs().getTime(index(), c);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setTime(final Time v) {
        try {
            rs().updateTime(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Timestamp getTimestamp() {
        try {
            return rs().getTimestamp(index());
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public Timestamp getTimestamp(final Calendar c) {
        try {
            return rs().getTimestamp(index(), c);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public void setTimestamp(final Timestamp v) {
        try {
            rs().updateTimestamp(index(), v);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    @Override
    public URL getURL() {
        try {
            return rs().getURL(index());
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
