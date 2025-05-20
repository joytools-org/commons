/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.math.BigDecimal;
import java.sql.Array;
import java.sql.BatchUpdateException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Objects;
import org.joytools.commons.util.Variable;
import org.joytools.commons.util.Variables;

/**
 *
 * @author AndreaR
 */
public class PreparedStatementExecute {

    /**
     * 
     * @param ps
     * @throws SQLException 
     */
    protected PreparedStatementExecute(final PreparedStatement ps) throws SQLException {
        m_ps = Objects.requireNonNull(ps, "PreparedStatement");
        m_parmCount = m_ps.getParameterMetaData().getParameterCount();
    }            
    
    /**
     * 
     * @param ps
     * @return
     * @throws SQLException 
     */
    public static PreparedStatementExecute of(final PreparedStatement ps) throws SQLException {
        return new PreparedStatementExecute(ps);
    }
    
    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addArray(final Array v) throws SQLException {
        m_ps.setArray(incIndex(), v);
        return this;
    }
    
    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addBigDecimal(final BigDecimal v) throws SQLException {
        m_ps.setBigDecimal(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addBoolean(final boolean v) throws SQLException {
        m_ps.setBoolean(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addByte(final byte v) throws SQLException {
        m_ps.setByte(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addBytes(final byte[] v) throws SQLException {
        m_ps.setBytes(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addDate(final java.sql.Date v) throws SQLException {
        m_ps.setDate(incIndex(), v);
        return this;
    }
    
    /**
     * 
     * @param v
     * @param cal
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addDate(final java.sql.Date v, final Calendar cal) throws SQLException {
        m_ps.setDate(incIndex(), v, cal);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addDouble(final double v) throws SQLException {
        m_ps.setDouble(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addFloat(final float v) throws SQLException {
        m_ps.setFloat(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addInt(final int v) throws SQLException {
        m_ps.setInt(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addLong(final long v) throws SQLException {
        m_ps.setLong(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addNString(final String v) throws SQLException {
        m_ps.setNString(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param type
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addNull(final Type type) throws SQLException {
        m_ps.setNull(incIndex(), type.value());
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addObject(final Object v) throws SQLException {
        m_ps.setObject(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addShort(final short v) throws SQLException {
        m_ps.setShort(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addString(final String v) throws SQLException {
        m_ps.setString(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addTime(final java.sql.Time v) throws SQLException {
        m_ps.setTime(incIndex(), v);
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addTimestamp(final java.sql.Timestamp v) throws SQLException {
        m_ps.setTimestamp(incIndex(), v);
        return this;
    }
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute clear() throws SQLException {
        m_parmIndex = 0;
        return this;
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addAll(final Object... v) throws SQLException {
        Objects.requireNonNull(v, "Values");
        for (final Object o : v) {
            addObject(o);
        }
        return this;
    }
    
    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addAll(final Iterable v) throws SQLException {
        Objects.requireNonNull(v, "Values");
        for (final Object o : v) {
            addObject(o);
        }
        return this;
    }
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public PreparedStatementExecute addBatch() throws SQLException {
        beforeExecute();
        try {
            m_ps.addBatch();
        } finally {
            m_ps.clearParameters();
        }
        return this;
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    protected int incIndex() throws SQLException {
        if (m_parmIndex == 0) {
            m_ps.clearParameters();
        }
        return ++m_parmIndex;
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public ResultSet executeQuery() throws SQLException {
        beforeExecute();
        try {
            return m_ps.executeQuery();
        } finally {
            m_ps.clearParameters();
        }
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public ResultSet executeQueryWithParams(final Object... v) throws SQLException {
        addAll(v);
        return executeQuery();
    }

    /**
     * 
     * @param v
     * @return
     * @throws SQLException 
     */
    public ResultSet executeQueryWithParams(final Iterable v) throws SQLException {
        addAll(v);
        return executeQuery();
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public int executeUpdate() throws SQLException {
        beforeExecute();
        try {
            return m_ps.executeUpdate();
        } finally {
            m_ps.clearParameters();
        }
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public long executeLargeUpdate() throws SQLException {
        beforeExecute();
        try {
            return m_ps.executeLargeUpdate();
        } finally {
            m_ps.clearParameters();
        }
    }
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public ExecuteBatchResult executeBatch() throws SQLException {
        m_parmIndex = 0;
        int[] statusCodes;
        final Variable<BatchUpdateException> batchUpdateException = 
                Variables.forClass(BatchUpdateException.class);
        try {
            statusCodes = m_ps.executeBatch();
        } catch (final BatchUpdateException bue) {
            batchUpdateException.set(bue);
            statusCodes = bue.getUpdateCounts();
        } finally {
            m_ps.clearBatch();
        }
        return ExecuteBatchResult.of(statusCodes, batchUpdateException.get());
    }
    
    /**
     * 
     */
    protected void beforeExecute() {
        final int oldParmIndex = m_parmIndex;
        m_parmIndex = 0;
        if (oldParmIndex != m_parmCount) {
            throw new IllegalArgumentException("Expected " + m_parmCount + " parameters, provided " + m_parmIndex);
        }
    }

    private final PreparedStatement m_ps;
    
    private int m_parmIndex = 0;
    
    private final int m_parmCount;
    
}
