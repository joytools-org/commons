/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import static io.vavr.API.Tuple;
import io.vavr.Tuple4;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import org.joytools.commons.collections.IndexedMap;
import org.joytools.commons.collections.IndexedMapBuilder;
import org.joytools.commons.function.FailableAutoCloseable;
import org.joytools.commons.function.FailableAutoCloseables;
import org.joytools.commons.util.FailableAutoCloseableContainer;

/**
 *
 * @author AndreaR
 */
public class PreparedStatementContainer extends FailableAutoCloseableContainer<SQLException> {

    /**
     * 
     * @param cn
     * @param type
     * @param concurrency
     * @param holdability 
     */
    protected PreparedStatementContainer(final Connection cn,
            final ResultSetType type,
            final ResultSetConcurrency concurrency,
            final ResultSetHoldability holdability) {
        m_cn = Objects.requireNonNull(cn, "Connection");
        m_type = type;
        m_concurrency = concurrency;
        m_holdability = holdability;
    }
    
    /**
     * 
     * @param type
     * @param concurrency
     * @param holdability
     * @return 
     */
    protected final Builder newBuilder(final ResultSetType type,
            final ResultSetConcurrency concurrency,
            final ResultSetHoldability holdability) {
        if (type != null && concurrency != null && holdability != null) {
            return (final String sql) -> m_cn.prepareStatement(sql, 
                    type.value(),
                    concurrency.value(),
                    holdability.value());
        }
        if (type != null && concurrency != null) {
            return (final String sql) -> m_cn.prepareStatement(sql, 
                    type.value(),
                    concurrency.value());
        } 
        if (type == null && concurrency == null && holdability == null) {
            return (final String sql) -> m_cn.prepareStatement(sql);
        } 
        throw new IllegalArgumentException("Invalid combination of Prepared Statement Parameters");
    }
    
    /**
     * 
     * @param cn
     * @return 
     */
    public static PreparedStatementContainer of(final Connection cn) {
        return new PreparedStatementContainer(cn, null, null, null);
    }
       
    /**
     * 
     * @param cn
     * @param type
     * @param concurrency
     * @return 
     */
    public static PreparedStatementContainer of(final Connection cn,
            final ResultSetType type,
            final ResultSetConcurrency concurrency) {
        return new PreparedStatementContainer(cn, type, concurrency, null);
    }

    /**
     * 
     * @param cn
     * @return 
     */
    public static PreparedStatementContainer forForwardOnlyReadOnly(final Connection cn) {
        return of(cn, ResultSetType.FORWARD_ONLY, ResultSetConcurrency.READ_ONLY);
    }

    /**
     * 
     * @param cn
     * @return 
     */
    public static PreparedStatementContainer forForwardOnlyUpdatable(final Connection cn) {
        return of(cn, ResultSetType.FORWARD_ONLY, ResultSetConcurrency.UPDATABLE);
    }

    /**
     * 
     * @param cn
     * @return 
     */
    public static PreparedStatementContainer forScrollSensitiveReadOnly(final Connection cn) {
        return of(cn, ResultSetType.SCROLL_SENSITIVE, ResultSetConcurrency.READ_ONLY);
    }

    /**
     * 
     * @param cn
     * @return 
     */
    public static PreparedStatementContainer forScrollInsensitiveReadOnly(final Connection cn) {
        return of(cn, ResultSetType.SCROLL_INSENSITIVE, ResultSetConcurrency.READ_ONLY);
    }

    /**
     * 
     * @param cn
     * @return 
     */
    public static PreparedStatementContainer forScrollSensitiveUpdatable(final Connection cn) {
        return of(cn, ResultSetType.SCROLL_SENSITIVE, ResultSetConcurrency.UPDATABLE);
    }

    /**
     * 
     * @param cn
     * @param type
     * @param concurrency
     * @param holdability
     * @return 
     */
    public static PreparedStatementContainer of(final Connection cn,
            final ResultSetType type,
            final ResultSetConcurrency concurrency,
            final ResultSetHoldability holdability) {
        return new PreparedStatementContainer(cn, type, concurrency, holdability);
    }

    /**
     * 
     * @param sql
     * @return
     * @throws SQLException 
     */
    public PreparedStatement get(final String sql) throws SQLException {
        return get(sql, m_type, m_concurrency, m_holdability);
    }
    
    /**
     * 
     * @param sql
     * @param type
     * @param concurrency
     * @return
     * @throws SQLException 
     */
    public PreparedStatement get(final String sql,
            final ResultSetType type, 
            final ResultSetConcurrency concurrency) throws SQLException {
        return get(sql, type, concurrency, null);
    }

    /**
     * 
     * @param sql
     * @param type
     * @param concurrency
     * @param holdability
     * @return
     * @throws SQLException 
     */
    public PreparedStatement get(final String sql,
            final ResultSetType type, 
            final ResultSetConcurrency concurrency,
            final ResultSetHoldability holdability) throws SQLException {
        final Tuple4<String, ResultSetType, ResultSetConcurrency, ResultSetHoldability> key = Tuple(sql, type, concurrency, holdability);
        PreparedStatement ps = m_psMap.get(key);
        if (ps == null) {
            ps = buildAndDispose(sql, type, concurrency, holdability);
            m_psMap.put(key, ps);
        }
        return ps;
    }

    /**
     * 
     * @return 
     */
    public Connection connection() {
        return m_cn;
    }
    
    /**
     * 
     * @param sql
     * @return
     * @throws SQLException 
     */
    protected int indexOf(final String sql) throws SQLException {
        return indexOf(sql, m_type, m_concurrency, m_holdability);
    }

    /**
     * 
     * @param sql
     * @param type
     * @param concurrency
     * @return
     * @throws SQLException 
     */
    protected int indexOf(final String sql,
            final ResultSetType type, 
            final ResultSetConcurrency concurrency) throws SQLException {
        return indexOf(sql, type, concurrency, null);
    }

    /**
     * 
     * @param sql
     * @param type
     * @param concurrency
     * @param holdability
     * @return
     * @throws SQLException 
     */
    protected int indexOf(final String sql,
            final ResultSetType type, 
            final ResultSetConcurrency concurrency,
            final ResultSetHoldability holdability) throws SQLException {
        final Tuple4<String, ResultSetType, ResultSetConcurrency, ResultSetHoldability> key = Tuple(sql, type, concurrency, holdability);
        int index = m_psMap.indexOf(key);
        if (index < 0) {
            get(sql, type, concurrency, holdability);
            index = m_psMap.indexOf(key);
        }
        if (index < 0) {
            throw new IllegalStateException("Unable to find index of query");
        }
        return index;
    }

    /**
     * 
     * @param index
     * @return
     * @throws SQLException 
     */
    protected PreparedStatement get(final int index) throws SQLException {
        throw new UnsupportedOperationException();
        // return m_defaultMap.get(index);
    }

    /**
     * 
     * @param sql
     * @param type
     * @param concurrency
     * @param holdability
     * @return
     * @throws SQLException 
     */
    protected PreparedStatement buildAndDispose(
            final String sql,
            final ResultSetType type,
            final ResultSetConcurrency concurrency,
            final ResultSetHoldability holdability) throws SQLException {
        final PreparedStatement ps = newBuilder(type, concurrency, holdability).build(sql);
        return dispose(ps, FailableAutoCloseables.of(ps::close));
    }

    @FunctionalInterface
    protected interface Builder {
        public PreparedStatement build(final String sql) throws SQLException;
    }
    
    private final Connection m_cn;
    
    private final ResultSetType m_type;

    private final ResultSetConcurrency m_concurrency;

    private final ResultSetHoldability m_holdability;
    
    private final IndexedMap<Tuple4<String, ResultSetType, ResultSetConcurrency, ResultSetHoldability>, PreparedStatement> m_psMap = IndexedMapBuilder.create();
    
}
