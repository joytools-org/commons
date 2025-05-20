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

/**
 *
 * @author AndreaR
 */
public abstract class AbstractCallableStatementDecorator extends AbstractPreparedStatementDecorator implements CallableStatement {

    protected CallableStatement callableStatement() {
        return CallableStatement.class.cast(statement());
    }

    @Override
    public void registerOutParameter(final int parameterIndex, final int sqlType) throws SQLException {
        callableStatement().registerOutParameter(parameterIndex, sqlType);
    }

    @Override
    public void registerOutParameter(final int parameterIndex, final int sqlType, final int scale) throws SQLException {
        callableStatement().registerOutParameter(parameterIndex, sqlType, scale);
    }

    @Override
    public boolean wasNull() throws SQLException {
        return callableStatement().wasNull();
    }

    @Override
    public String getString(final int parameterIndex) throws SQLException {
        return callableStatement().getString(parameterIndex);
    }

    @Override
    public boolean getBoolean(final int parameterIndex) throws SQLException {
        return callableStatement().getBoolean(parameterIndex);
    }

    @Override
    public byte getByte(final int parameterIndex) throws SQLException {
        return callableStatement().getByte(parameterIndex);
    }

    @Override
    public short getShort(final int parameterIndex) throws SQLException {
        return callableStatement().getByte(parameterIndex);
    }

    @Override
    public int getInt(final int parameterIndex) throws SQLException {
        return callableStatement().getInt(parameterIndex);
    }

    @Override
    public long getLong(final int parameterIndex) throws SQLException {
        return callableStatement().getLong(parameterIndex);
    }

    @Override
    public float getFloat(final int parameterIndex) throws SQLException {
        return callableStatement().getFloat(parameterIndex);
    }

    @Override
    public double getDouble(final int parameterIndex) throws SQLException {
        return callableStatement().getDouble(parameterIndex);
    }

    @Override
    public BigDecimal getBigDecimal(final int parameterIndex, final int scale) throws SQLException {
        return callableStatement().getBigDecimal(parameterIndex, scale);
    }

    @Override
    public byte[] getBytes(final int parameterIndex) throws SQLException {
        return callableStatement().getBytes(parameterIndex);
    }

    @Override
    public Date getDate(final int parameterIndex) throws SQLException {
        return callableStatement().getDate(parameterIndex);
    }

    @Override
    public Time getTime(final int parameterIndex) throws SQLException {
        return callableStatement().getTime(parameterIndex);
    }

    @Override
    public Timestamp getTimestamp(final int parameterIndex) throws SQLException {
        return callableStatement().getTimestamp(parameterIndex);
    }

    @Override
    public Object getObject(final int parameterIndex) throws SQLException {
        return callableStatement().getObject(parameterIndex);
    }

    @Override
    public BigDecimal getBigDecimal(final int parameterIndex) throws SQLException {
        return callableStatement().getBigDecimal(parameterIndex);
    }

    @Override
    public Object getObject(final int parameterIndex, final Map<String, Class<?>> map) throws SQLException {
        return callableStatement().getObject(parameterIndex, map);
    }

    @Override
    public Ref getRef(final int parameterIndex) throws SQLException {
        return callableStatement().getRef(parameterIndex);
    }

    @Override
    public Blob getBlob(final int parameterIndex) throws SQLException {
        return callableStatement().getBlob(parameterIndex);
    }

    @Override
    public Clob getClob(final int parameterIndex) throws SQLException {
        return callableStatement().getClob(parameterIndex);
    }

    @Override
    public Array getArray(final int parameterIndex) throws SQLException {
        return callableStatement().getArray(parameterIndex);
    }

    @Override
    public Date getDate(final int parameterIndex, final Calendar cal) throws SQLException {
        return callableStatement().getDate(parameterIndex, cal);
    }

    @Override
    public Time getTime(final int parameterIndex, final Calendar cal) throws SQLException {
        return callableStatement().getTime(parameterIndex, cal);
    }

    @Override
    public Timestamp getTimestamp(final int parameterIndex, final Calendar cal) throws SQLException {
        return callableStatement().getTimestamp(parameterIndex, cal);
    }

    @Override
    public void registerOutParameter(final int parameterIndex, final int sqlType, final String typeName) throws SQLException {
        callableStatement().registerOutParameter(parameterIndex, sqlType, typeName);
    }

    @Override
    public void registerOutParameter(final String parameterName, final int sqlType) throws SQLException {
        callableStatement().registerOutParameter(parameterName, sqlType);
    }

    @Override
    public void registerOutParameter(final String parameterName, final int sqlType, final int scale) throws SQLException {
        callableStatement().registerOutParameter(parameterName, sqlType, scale);
    }

    @Override
    public void registerOutParameter(final String parameterName, final int sqlType, final String typeName) throws SQLException {
        callableStatement().registerOutParameter(parameterName, sqlType, typeName);
    }

    @Override
    public URL getURL(final int parameterIndex) throws SQLException {
        return callableStatement().getURL(parameterIndex);
    }

    @Override
    public void setURL(final String parameterName, final URL val) throws SQLException {
        callableStatement().setURL(parameterName, val);
    }

    @Override
    public void setNull(final String parameterName, final int sqlType) throws SQLException {
        callableStatement().setNull(parameterName, sqlType);
    }

    @Override
    public void setBoolean(final String parameterName, final boolean x) throws SQLException {
        callableStatement().setBoolean(parameterName, x);
    }

    @Override
    public void setByte(final String parameterName, final byte x) throws SQLException {
        callableStatement().setByte(parameterName, x);
    }

    @Override
    public void setShort(final String parameterName, final short x) throws SQLException {
        callableStatement().setShort(parameterName, x);
    }

    @Override
    public void setInt(final String parameterName, final int x) throws SQLException {
        callableStatement().setInt(parameterName, x);
    }

    @Override
    public void setLong(final String parameterName, final long x) throws SQLException {
        callableStatement().setLong(parameterName, x);
    }

    @Override
    public void setFloat(final String parameterName, final float x) throws SQLException {
        callableStatement().setFloat(parameterName, x);
    }

    @Override
    public void setDouble(final String parameterName, final double x) throws SQLException {
        callableStatement().setDouble(parameterName, x);
    }

    @Override
    public void setBigDecimal(final String parameterName, final BigDecimal x) throws SQLException {
        callableStatement().setBigDecimal(parameterName, x);
    }

    @Override
    public void setString(final String parameterName, final String x) throws SQLException {
        callableStatement().setString(parameterName, x);
    }

    @Override
    public void setBytes(final String parameterName, final byte[] x) throws SQLException {
        callableStatement().setBytes(parameterName, x);
    }

    @Override
    public void setDate(final String parameterName, final Date x) throws SQLException {
        callableStatement().setDate(parameterName, x);
    }

    @Override
    public void setTime(final String parameterName, final Time x) throws SQLException {
        callableStatement().setTime(parameterName, x);
    }

    @Override
    public void setTimestamp(final String parameterName, final Timestamp x) throws SQLException {
        callableStatement().setTimestamp(parameterName, x);
    }

    @Override
    public void setAsciiStream(final String parameterName, final InputStream x, final int length) throws SQLException {
        callableStatement().setAsciiStream(parameterName, x, length);
    }

    @Override
    public void setBinaryStream(final String parameterName, final InputStream x, final int length) throws SQLException {
        callableStatement().setBinaryStream(parameterName, x, length);
    }

    @Override
    public void setObject(final String parameterName, final Object x, final int targetSqlType, final int scale) throws SQLException {
        callableStatement().setObject(parameterName, x, targetSqlType, scale);
    }

    @Override
    public void setObject(final String parameterName, final Object x, final int targetSqlType) throws SQLException {
        callableStatement().setObject(parameterName, x, targetSqlType);
    }

    @Override
    public void setObject(final String parameterName, final Object x) throws SQLException {
        callableStatement().setObject(parameterName, x);
    }

    @Override
    public void setCharacterStream(final String parameterName, final Reader reader, final int length) throws SQLException {
        callableStatement().setCharacterStream(parameterName, reader, length);
    }

    @Override
    public void setDate(final String parameterName, final Date x, final Calendar cal) throws SQLException {
        callableStatement().setDate(parameterName, x, cal);
    }

    @Override
    public void setTime(final String parameterName, final Time x, final Calendar cal) throws SQLException {
        callableStatement().setTime(parameterName, x, cal);
    }

    @Override
    public void setTimestamp(final String parameterName, final Timestamp x, final Calendar cal) throws SQLException {
        callableStatement().setTimestamp(parameterName, x, cal);
    }

    @Override
    public void setNull(final String parameterName, final int sqlType, final String typeName) throws SQLException {
        callableStatement().setNull(parameterName, sqlType, typeName);
    }

    @Override
    public String getString(final String parameterName) throws SQLException {
        return callableStatement().getString(parameterName);
    }

    @Override
    public boolean getBoolean(final String parameterName) throws SQLException {
        return callableStatement().getBoolean(parameterName);
    }

    @Override
    public byte getByte(final String parameterName) throws SQLException {
        return callableStatement().getByte(parameterName);
    }

    @Override
    public short getShort(final String parameterName) throws SQLException {
        return callableStatement().getShort(parameterName);
    }

    @Override
    public int getInt(final String parameterName) throws SQLException {
        return callableStatement().getInt(parameterName);
    }

    @Override
    public long getLong(final String parameterName) throws SQLException {
        return callableStatement().getLong(parameterName);
    }

    @Override
    public float getFloat(final String parameterName) throws SQLException {
        return callableStatement().getFloat(parameterName);
    }

    @Override
    public double getDouble(final String parameterName) throws SQLException {
        return callableStatement().getDouble(parameterName);
    }

    @Override
    public byte[] getBytes(final String parameterName) throws SQLException {
        return callableStatement().getBytes(parameterName);
    }

    @Override
    public Date getDate(final String parameterName) throws SQLException {
        return callableStatement().getDate(parameterName);
    }

    @Override
    public Time getTime(final String parameterName) throws SQLException {
        return callableStatement().getTime(parameterName);
    }

    @Override
    public Timestamp getTimestamp(final String parameterName) throws SQLException {
        return callableStatement().getTimestamp(parameterName);
    }

    @Override
    public Object getObject(final String parameterName) throws SQLException {
        return callableStatement().getObject(parameterName);
    }

    @Override
    public BigDecimal getBigDecimal(final String parameterName) throws SQLException {
        return callableStatement().getBigDecimal(parameterName);
    }

    @Override
    public Object getObject(final String parameterName, final Map<String, Class<?>> map) throws SQLException {
        return callableStatement().getObject(parameterName, map);
    }

    @Override
    public Ref getRef(final String parameterName) throws SQLException {
        return callableStatement().getRef(parameterName);
    }

    @Override
    public Blob getBlob(final String parameterName) throws SQLException {
        return callableStatement().getBlob(parameterName);
    }

    @Override
    public Clob getClob(final String parameterName) throws SQLException {
        return callableStatement().getClob(parameterName);
    }

    @Override
    public Array getArray(final String parameterName) throws SQLException {
        return callableStatement().getArray(parameterName);
    }

    @Override
    public Date getDate(final String parameterName, final Calendar cal) throws SQLException {
        return callableStatement().getDate(parameterName, cal);
    }

    @Override
    public Time getTime(final String parameterName, final Calendar cal) throws SQLException {
        return callableStatement().getTime(parameterName, cal);
    }

    @Override
    public Timestamp getTimestamp(final String parameterName, final Calendar cal) throws SQLException {
        return callableStatement().getTimestamp(parameterName, cal);
    }

    @Override
    public URL getURL(final String parameterName) throws SQLException {
        return callableStatement().getURL(parameterName);
    }

    @Override
    public RowId getRowId(final int parameterIndex) throws SQLException {
        return callableStatement().getRowId(parameterIndex);
    }

    @Override
    public RowId getRowId(final String parameterName) throws SQLException {
        return callableStatement().getRowId(parameterName);
    }

    @Override
    public void setRowId(final String parameterName, final RowId x) throws SQLException {
        callableStatement().setRowId(parameterName, x);
    }

    @Override
    public void setNString(final String parameterName, final String value) throws SQLException {
        callableStatement().setNString(parameterName, value);
    }

    @Override
    public void setNCharacterStream(final String parameterName, final Reader value, final long length) throws SQLException {
        callableStatement().setNCharacterStream(parameterName, value, length);
    }

    @Override
    public void setNClob(final String parameterName, final NClob value) throws SQLException {
        callableStatement().setNClob(parameterName, value);
    }

    @Override
    public void setClob(final String parameterName, final Reader reader, final long length) throws SQLException {
        callableStatement().setClob(parameterName, reader, length);
    }

    @Override
    public void setBlob(final String parameterName, final InputStream inputStream, final long length) throws SQLException {
        callableStatement().setBlob(parameterName, inputStream, length);
    }

    @Override
    public void setNClob(final String parameterName, final Reader reader, final long length) throws SQLException {
        callableStatement().setNClob(parameterName, reader, length);
    }

    @Override
    public NClob getNClob(final int parameterIndex) throws SQLException {
        return callableStatement().getNClob(parameterIndex);
    }

    @Override
    public NClob getNClob(final String parameterName) throws SQLException {
        return callableStatement().getNClob(parameterName);
    }

    @Override
    public void setSQLXML(final String parameterName, final SQLXML xmlObject) throws SQLException {
        callableStatement().setSQLXML(parameterName, xmlObject);
    }

    @Override
    public SQLXML getSQLXML(final int parameterIndex) throws SQLException {
        return callableStatement().getSQLXML(parameterIndex);
    }

    @Override
    public SQLXML getSQLXML(final String parameterName) throws SQLException {
        return callableStatement().getSQLXML(parameterName);
    }

    @Override
    public String getNString(final int parameterIndex) throws SQLException {
        return callableStatement().getNString(parameterIndex);
    }

    @Override
    public String getNString(final String parameterName) throws SQLException {
        return callableStatement().getNString(parameterName);
    }

    @Override
    public Reader getNCharacterStream(final int parameterIndex) throws SQLException {
        return callableStatement().getNCharacterStream(parameterIndex);
    }

    @Override
    public Reader getNCharacterStream(final String parameterName) throws SQLException {
        return callableStatement().getNCharacterStream(parameterName);
    }

    @Override
    public Reader getCharacterStream(final int parameterIndex) throws SQLException {
        return callableStatement().getCharacterStream(parameterIndex);
    }

    @Override
    public Reader getCharacterStream(final String parameterName) throws SQLException {
        return callableStatement().getCharacterStream(parameterName);
    }

    @Override
    public void setBlob(final String parameterName, final Blob x) throws SQLException {
        callableStatement().setBlob(parameterName, x);
    }

    @Override
    public void setClob(final String parameterName, final Clob x) throws SQLException {
        callableStatement().setClob(parameterName, x);
    }

    @Override
    public void setAsciiStream(final String parameterName, final InputStream x, final long length) throws SQLException {
        callableStatement().setAsciiStream(parameterName, x, length);
    }

    @Override
    public void setBinaryStream(final String parameterName, final InputStream x, final long length) throws SQLException {
        callableStatement().setBinaryStream(parameterName, x, length);
    }

    @Override
    public void setCharacterStream(final String parameterName, final Reader reader, final long length) throws SQLException {
        callableStatement().setCharacterStream(parameterName, reader, length);
    }

    @Override
    public void setAsciiStream(final String parameterName, final InputStream x) throws SQLException {
        callableStatement().setAsciiStream(parameterName, x);
    }

    @Override
    public void setBinaryStream(final String parameterName, final InputStream x) throws SQLException {
        callableStatement().setBinaryStream(parameterName, x);
    }

    @Override
    public void setCharacterStream(final String parameterName, final Reader reader) throws SQLException {
        callableStatement().setCharacterStream(parameterName, reader);
    }

    @Override
    public void setNCharacterStream(final String parameterName, final Reader value) throws SQLException {
        callableStatement().setNCharacterStream(parameterName, value);
    }

    @Override
    public void setClob(final String parameterName, final Reader reader) throws SQLException {
        callableStatement().setClob(parameterName, reader);
    }

    @Override
    public void setBlob(final String parameterName, final InputStream inputStream) throws SQLException {
        callableStatement().setBlob(parameterName, inputStream);
    }

    @Override
    public void setNClob(final String parameterName, final Reader reader) throws SQLException {
        callableStatement().setNClob(parameterName, reader);
    }

    @Override
    public <T> T getObject(final int parameterIndex, final Class<T> type) throws SQLException {
        return callableStatement().getObject(parameterIndex, type);
    }

    @Override
    public <T> T getObject(final String parameterName, final Class<T> type) throws SQLException {
        return callableStatement().getObject(parameterName, type);
    }

    @Override
    public void setObject(final String parameterName, final Object x, final SQLType targetSqlType, final int scaleOrLength) throws SQLException {
        callableStatement().setObject(parameterName, x, targetSqlType, scaleOrLength);
    }

    @Override
    public void setObject(final String parameterName, final Object x, final SQLType targetSqlType) throws SQLException {
        callableStatement().setObject(parameterName, x, targetSqlType);
    }

    @Override
    public void registerOutParameter(final int parameterIndex, final SQLType sqlType) throws SQLException {
        callableStatement().registerOutParameter(parameterIndex, sqlType);
    }

    @Override
    public void registerOutParameter(final int parameterIndex, final SQLType sqlType, final int scale) throws SQLException {
        callableStatement().registerOutParameter(parameterIndex, sqlType, scale);
    }

    @Override
    public void registerOutParameter(final int parameterIndex, final SQLType sqlType, final String typeName) throws SQLException {
        callableStatement().registerOutParameter(parameterIndex, sqlType, typeName);
    }

    @Override
    public void registerOutParameter(final String parameterName, final SQLType sqlType) throws SQLException {
        callableStatement().registerOutParameter(parameterName, sqlType);
    }

    @Override
    public void registerOutParameter(final String parameterName, final SQLType sqlType, final int scale) throws SQLException {
        callableStatement().registerOutParameter(parameterName, sqlType, scale);
    }

    @Override
    public void registerOutParameter(final String parameterName, final SQLType sqlType, final String typeName) throws SQLException {
        callableStatement().registerOutParameter(parameterName, sqlType, typeName);
    }

}
