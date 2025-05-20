/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import static com.google.common.base.Preconditions.checkArgument;
import static io.vavr.API.Left;
import static io.vavr.API.Right;
import io.vavr.Value;
import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Seq;
import io.vavr.control.Either;
import java.lang.reflect.UndeclaredThrowableException;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.function.Supplier;
import org.apache.commons.lang3.function.FailableSupplier;
import org.joytools.commons.accessors.Accessors;
import org.joytools.commons.collections.IndexedMap;
import org.joytools.commons.collections.IndexedMapBuilder;

// import org.joytools.commons.collections.StreamIterable;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.Variable;
import org.joytools.commons.util.Variables;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.time.TimeValue;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.sql.ResultSetColumnDataAccessor;
import org.joytools.commons.accessors.sql.ResultSetColumnDataAccessors;
import org.joytools.commons.accessors.vavr.VavrAccessors;

/**
 *
 * @author AndreaR
 */
public class SQLUtils {

    /**
     * 
     */
    public SQLUtils() { }

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static CaseSensitivity caseSensitivity_old(final ResultSet rs) throws SQLException {
        return caseSensitivity_old(Objects.requireNonNull(rs, "ResultSet").getMetaData());
    }

    /**
     * 
     * @param md
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static CaseSensitivity caseSensitivity_old(final ResultSetMetaData md) throws SQLException {
        return caseSensitivity0(Objects.requireNonNull(md, "ResultSetMetaData"));
    }

    /**
     * 
     * @param md
     * @return
     * @throws SQLException 
     */
    @Deprecated
    static CaseSensitivity caseSensitivity0(final ResultSetMetaData md) throws SQLException {
        for (int i = 1; i <= md.getColumnCount(); i++) {
            if (md.isCaseSensitive(i)) {
                return CaseSensitivity.SENSITIVE;
            }
        }
        return CaseSensitivity.INSENSITIVE;
    }

    /**
     * 
     * @param <S>
     * @param ps
     * @param data
     * @return
     * @throws SQLException 
     */
    public static <S extends PreparedStatement> S setParameters(final S ps, final Seq<?> data) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(data, "Parameter values");
        return SQLUtils.setParameters(ps, VavrAccessors.of(data));
        // return setParameters0(ps, data);
    }

    /**
     *
     * @param <S>
     * @param ps
     * @param data
     * @return
     * @throws SQLException
     */
    public static <S extends PreparedStatement> S setParameters(final S ps, final List<?> data) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(data, "Parameter values");
        return SQLUtils.setParameters(ps, Accessors.of(data));
        // return setParameters0(ps, data);
    }
    
    /**
     * 
     * @param <S>
     * @param ps
     * @param data
     * @return
     * @throws SQLException 
     */
    public static <S extends PreparedStatement> S setParameters(final S ps, final Object... data) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(data, "Parameter values");
        return SQLUtils.setParameters(ps, Accessors.of(data));
    }

    /**
     * 
     * @param <S>
     * @param ps
     * @param data
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static <S extends PreparedStatement> S setParametersV1(final S ps, final Iterable<?> data) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(data, "Parameter values");
        int colIndex = 0;
        for (final Object value : data) {
            ps.setObject(++colIndex, value);
        }
        final int parmCount = ps.getParameterMetaData().getParameterCount();
        if (colIndex != parmCount) {
            throw new SQLException("Wrong number of parameters in PreparedStatement: found " + colIndex + ", expected " + parmCount);
        }
        // System.out.println("setParameters: " + data);
        return ps;
    }

    /**
     * 
     * @param <S>
     * @param ps
     * @param data
     * @return
     * @throws SQLException 
     */
    public static <S extends PreparedStatement> S setParameters(final S ps, final ListAccessor<?> data) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(data, "Parameter values");
        final int size = data.size();
        final int parmCount = ps.getParameterMetaData().getParameterCount(); 
        if (size != parmCount) {
            throw new SQLException("Wrong number of parameters in Prepared Statement: found " + size + ", expected " + parmCount);
        }
        for (int i = 0; i < size; i++) {
            /*
            Object value = data.getOrFail(i);
            if (value != null) {
                if (value instanceof Enum) {
                    value = Enum.class.cast(value).name();
                } else
                if (value instanceof CharSequence) {
                    value = StringUtils.toString(value);
                } else
                if (value instanceof TimeMillis) {
                    final Long timeMillis = TimeMillis.class.cast(value).timeMillis();
                    value = timeMillis == null ? null : new java.sql.Timestamp(timeMillis);
                } 
            } */
            final Object value = computeParameterValue(data.getOrFail(i));
            ps.setObject(i + 1, value);
        }
        return ps;
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static Object computeParameterValue(final Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof CharSequence cs) {
            return cs.toString();
        }
        if (value instanceof Number
                || value instanceof java.util.Date
                || value instanceof java.util.Calendar) {
            return value;
        }
        if (value instanceof Instant instant) {
            return new java.sql.Timestamp(instant.toEpochMilli());
        }
        if (value instanceof TimeValue tv) {
            return tv.isEmpty() ? null : tv.toSQLTimestamp();
        }
        if (value instanceof Value v) {
            return v.isSingleValued() && !v.isEmpty() ? computeParameterValue(v.get()) : null;
        }
        if (value instanceof Optional o) {
            return o.isPresent() ? computeParameterValue(o.get()) : null;
        }
        if (value instanceof Supplier s) {
            return computeParameterValue(s.get());
        }
        if (value instanceof Enum en) {
            return en.name();
        }
        if (value instanceof io.vavr.Value v) {
            if (v.isSingleValued()) {
                return v.isEmpty() ? null : computeParameterValue(v.get());
            }
        }
        if (value instanceof java.util.List list) {
            return list.isEmpty() ? null : computeParameterValue(list.get(0));
        }
        if (value instanceof io.vavr.collection.Seq seq) {
            return seq.isEmpty() ? null : computeParameterValue(seq.get(0));
        }
        return value;
    }
    
    /**
     * 
     * @return 
     */
    public static String uniqueObjectName() {
        return uniqueObjectName(null, 0);
    }

    /**
     * 
     * @param prefix
     * @return 
     */
    public static String uniqueObjectName(
            final String prefix) {
        return uniqueObjectName(prefix, 0);
    }

    /**
     * 
     * @param prefix
     * @param maxLen
     * @return 
     */
    public static String uniqueObjectName(
            final String prefix, 
            final int maxLen) {
        final String p = StringUtils.trimToEmpty(prefix);
        String uuid = null;
        do {
            uuid = UUID.randomUUID().toString();
        } while (p.length() == 0 && !StringUtils.isAlpha(uuid.substring(0, 1)));
        final String n = p + uuid.replace("-", "");
        if (maxLen <= 0) {
            return n;
        }
        return StringUtils.left(n, maxLen);
    }
    
    /**
     * 
     * @param cn
     * @param autoCommit
     * @return
     * @throws SQLException 
     */
    public static AutoCloseable setAutoCommit(final Connection cn, final boolean autoCommit) throws SQLException {
        Objects.requireNonNull(cn, "Connection");
        final boolean prevAutoCommit = cn.getAutoCommit();
        cn.setAutoCommit(autoCommit);
        return () -> {
            cn.setAutoCommit(prevAutoCommit);
        };
    }
    
    /**
     * 
     * @param cn
     * @param transactionIsolation
     * @return
     * @throws SQLException 
     */
    public static AutoCloseable setTransactionIsolation(final Connection cn, final int transactionIsolation) throws SQLException {
        Objects.requireNonNull(cn, "Connection");
        final int prevTransactionIsolation = cn.getTransactionIsolation();
        cn.setTransactionIsolation(transactionIsolation);
        return () -> {
            cn.setTransactionIsolation(prevTransactionIsolation);
        };
    }

    /**
     * 
     * @param cn
     * @param value
     * @return
     * @throws SQLException 
     */
    public static AutoCloseable setTransactionIsolation(final Connection cn, 
            final TransactionIsolation value) throws SQLException {
        return setTransactionIsolation(cn, Objects.requireNonNull(value, "TransactionIsolation").value());
    }

    /**
     * 
     * @param cn
     * @param t
     * @return 
     * @throws java.sql.SQLException 
     */
    public static IndexedMap<String, Integer> getPrimaryKeyColumns(
            final Connection cn,
            final String t) throws SQLException {
        final DatabaseMetaData md = cn.getMetaData();
        return getPrimaryKeyColumns0(md, t);
    }

    /**
     * 
     * @param md
     * @param t
     * @return
     * @throws SQLException 
     */
    public static IndexedMap<String, Integer> getPrimaryKeyColumns(
            final DatabaseMetaData md,
            final String t) throws SQLException {
        return getPrimaryKeyColumns0(md, t);
    }

    /**
     * 
     * @param cn
     * @param t
     * @return 
     * @throws java.sql.SQLException 
     */
    public static IndexedMap<String, Integer> getNonEmptyPrimaryKeyColumns(
            final Connection cn,
            final String t) throws SQLException {
        final IndexedMap<String, Integer> res = getPrimaryKeyColumns(cn, t);
        checkArgument(!res.isEmpty(), "No primary key found for " + t);
        return res;
    }

    /**
     * 
     * @param md
     * @param t
     * @return
     * @throws SQLException 
     */
    public static IndexedMap<String, Integer> getNonEmptyPrimaryKeyColumns(
            final DatabaseMetaData md,
            final String t) throws SQLException {
        final IndexedMap<String, Integer> res = getPrimaryKeyColumns(md, t);
        checkArgument(!res.isEmpty(), "No primary key found for " + t);
        return res;
    }

    /**
     * 
     * @param md
     * @param t
     * @return
     * @throws SQLException 
     */
    private static IndexedMap<String, Integer> getPrimaryKeyColumns0(
            final DatabaseMetaData md,
            final String tt) throws SQLException {
        final String t;
        if (StringUtils.startsWithIgnoreCase(tt, "dbo.")) {
            t = StringUtils.substringAfter(tt, ".");
        } else
        if (tt.contains(".")) {
            throw new UnsupportedOperationException("getColumns0 for table name " + tt + " is not supported");
        } else {
            t = tt;
        }
        try (final ResultSet rsPK = md.getPrimaryKeys(null, null, t)) {
            final ResultSetColumnDataAccessor rf = ResultSetColumnDataAccessors.of(rsPK);
            final SortedMap<Integer, String> pkColumns = new TreeMap();
            final SortedSet<String> tables = CaseSensitivity.INSENSITIVE.newSortedSet();
            final SortedSet<String> primaryKeys = CaseSensitivity.INSENSITIVE.newSortedSet();
            while (rsPK.next()) {
                final String catalogMD = rf.getOrFail("TABLE_CAT").getNString();
                final String schemaMD = rf.getOrFail("TABLE_SCHEM").getNString();
                final String tableMD = rf.getOrFail("TABLE_NAME").getNString();
                final String columnMD = rf.getOrFail("COLUMN_NAME").getNString();
                final int keySeqMD = rf.getOrFail("KEY_SEQ").getInt();
                final String pkNameMD = rf.getOrFail("PK_NAME").getNString();
                final String table = Name.on(md).table(catalogMD, schemaMD, tableMD).toString();
                tables.add(table);
                try {
                    MapUtils.insert(pkColumns, keySeqMD, columnMD);
                } catch (final Exception e) {
                    throw new SQLException("A problem occurred accessing primary key data", e);
                }
                primaryKeys.add(pkNameMD);
            }
            if (tables.size() > 1) {
                throw new SQLException("Multiple tables found: " + tables);
            }
            if (primaryKeys.size() > 1) {
                throw new SQLException("Multiple primary keys found: " + primaryKeys);
            }
            final IndexedMap<String, Integer> result = IndexedMapBuilder.create();
            int index = 0;
            for (final String name : pkColumns.values()) {
                result.put(name, index++);
            }
            return result;
        }
    }

    @Deprecated
    private static IndexedMap<String, Integer> old_getPrimaryKeyColumns0(
            final DatabaseMetaData md,
            final String t) throws SQLException {
        try (final ResultSet rsPK = md.getPrimaryKeys(null, null, t)) {
            final ResultSetFacade rf = ResultSetFacade.of(rsPK);
            final SortedMap<Integer, String> pkColumns = new TreeMap();
            final SortedSet<String> tables = CaseSensitivity.INSENSITIVE.newSortedSet();
            final SortedSet<String> primaryKeys = CaseSensitivity.INSENSITIVE.newSortedSet();
            while (rsPK.next()) {
                final String catalogMD = rf.columnData("TABLE_CAT").getString();
                final String schemaMD = rf.columnData("TABLE_SCHEM").getString();
                final String tableMD = rf.columnData("TABLE_NAME").getString();
                final String columnMD = rf.columnData("COLUMN_NAME").getString();
                final int keySeqMD = rf.columnData("KEY_SEQ").getInt();
                final String pkNameMD = rf.columnData("PK_NAME").getString();
                final String table = Name.on(md).table(catalogMD, schemaMD, tableMD).toString();
                tables.add(table);
                try {
                    MapUtils.insert(pkColumns, keySeqMD, columnMD);
                } catch (final Exception e) {
                    throw new SQLException("A problem occurred accessing primary key data", e);
                }
                primaryKeys.add(pkNameMD);
            }
            if (tables.size() > 1) {
                throw new SQLException("Multiple tables found: " + tables);
            }
            if (primaryKeys.size() > 1) {
                throw new SQLException("Multiple primary keys found: " + primaryKeys);
            }
            final IndexedMap<String, Integer> result = IndexedMapBuilder.create();
            int index = 0;
            for (final String name : pkColumns.values()) {
                result.put(name, index++);
            }
            return result;
        }
    }

    /**
     * https://docs.oracle.com/javase/8/docs/api/java/sql/DatabaseMetaData.html#getColumns-java.lang.String-java.lang.String-java.lang.String-java.lang.String-
     * 
     * @param cn
     * @param t
     * @return
     * @throws SQLException 
     */
    public static IndexedMap<String, Integer> getColumns(
            final Connection cn,
            final String t) throws SQLException {
        final DatabaseMetaData md = cn.getMetaData();
        return getColumns0(md, t);
    }

    /**
     * 
     * @param md
     * @param t
     * @return
     * @throws SQLException 
     */
    public static IndexedMap<String, Integer> getColumns(
            final DatabaseMetaData md,
            final String t) throws SQLException {
        return getColumns0(md, t);
    }

    /**
     * 
     * @param cn
     * @param t
     * @return
     * @throws SQLException 
     */
    public static IndexedMap<String, Integer> getNonEmptyColumns(
            final Connection cn,
            final String t) throws SQLException {
        final IndexedMap<String, Integer> res = getColumns(cn, t);
        checkArgument(!res.isEmpty(), "No columns found for " + t);
        return res;
    }

    /**
     * 
     * @param md
     * @param t
     * @return
     * @throws SQLException 
     */
    public static IndexedMap<String, Integer> getNonEmptyColumns(
            final DatabaseMetaData md,
            final String t) throws SQLException {
        final IndexedMap<String, Integer> res = getColumns(md, t);
        checkArgument(!res.isEmpty(), "No columns found for " + t);
        return res;
    }

    /**
     * 
     * @param md
     * @param t
     * @return
     * @throws SQLException 
     */
    private static IndexedMap<String, Integer> getColumns0(
            final DatabaseMetaData md,
            final String tt) throws SQLException {
        final String t;
        if (StringUtils.startsWithIgnoreCase(tt, "dbo.")) {
            t = StringUtils.substringAfter(tt, ".");
        } else
        if (tt.contains(".")) {
            throw new UnsupportedOperationException("getColumns0 for table name " + tt + " is not supported");
        } else {
            t = tt;
        }
        try (final ResultSet rsPK = md.getColumns(null, null, t, null)) {
            final ResultSetColumnDataAccessor rf = ResultSetColumnDataAccessors.of(rsPK);
            final SortedMap<Integer, String> pkColumns = new TreeMap();
            final SortedSet<String> tables = CaseSensitivity.INSENSITIVE.newSortedSet();
            while (rsPK.next()) {
                final String catalogMD = rf.getOrFail("TABLE_CAT").getNString();
                final String schemaMD = rf.getOrFail("TABLE_SCHEM").getNString();
                final String tableMD = rf.getOrFail("TABLE_NAME").getNString();
                final String columnMD = rf.getOrFail("COLUMN_NAME").getNString();
                final int columnSeqMD = rf.getOrFail("ORDINAL_POSITION").getInt() - 1;
                final String table = Name.on(md).table(catalogMD, schemaMD, tableMD).toString();
                tables.add(table);
                try {
                    MapUtils.insert(pkColumns, columnSeqMD, columnMD);
                } catch (final Exception e) {
                    throw new SQLException("A problem occurred accessing primary key data", e);
                }
            }
            if (tables.size() > 1) {
                throw new SQLException("Multiple tables found: " + tables);
            }
            final IndexedMap<String, Integer> result = IndexedMapBuilder.<String, Integer>ignoreCase().create();
            int index = 0;
            for (final String name : pkColumns.values()) {
                result.put(name, index++);
            }
            return result;
        }
    }

    @Deprecated
    private static IndexedMap<String, Integer> old_getColumns0(
            final DatabaseMetaData md,
            final String t) throws SQLException {
        try (final ResultSet rsPK = md.getColumns(null, null, t, null)) {
            final ResultSetFacade rf = ResultSetFacade.of(rsPK);
            final SortedMap<Integer, String> pkColumns = new TreeMap();
            final SortedSet<String> tables = CaseSensitivity.INSENSITIVE.newSortedSet();
            while (rsPK.next()) {
                final String catalogMD = rf.columnData("TABLE_CAT").getString();
                final String schemaMD = rf.columnData("TABLE_SCHEM").getString();
                final String tableMD = rf.columnData("TABLE_NAME").getString();
                final String columnMD = rf.columnData("COLUMN_NAME").getString();
                final int columnSeqMD = rf.columnData("ORDINAL_POSITION").getInt() - 1;
                final String table = Name.on(md).table(catalogMD, schemaMD, tableMD).toString();
                tables.add(table);
                try {
                    MapUtils.insert(pkColumns, columnSeqMD, columnMD);
                } catch (final Exception e) {
                    throw new SQLException("A problem occurred accessing primary key data", e);
                }
            }
            if (tables.size() > 1) {
                throw new SQLException("Multiple tables found: " + tables);
            }
            final IndexedMap<String, Integer> result = IndexedMapBuilder.<String, Integer>ignoreCase().create();
            int index = 0;
            for (final String name : pkColumns.values()) {
                result.put(name, index++);
            }
            return result;
        }
    }

    /**
     * 
     * @param <P>
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static <P extends PreparedStatement> P addBatch(final P ps, 
            final Object... params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        SQLUtils.setParameters(ps, params).addBatch();
        return ps;
    }

    /**
     * 
     * @param <P>
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static <P extends PreparedStatement> P addBatch(final P ps, 
            final List<?> params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        SQLUtils.setParameters(ps, params).addBatch();
        return ps;
    }

    /**
     * 
     * @param <P>
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static <P extends PreparedStatement> P addBatch(final P ps, 
            final Seq<?> params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        SQLUtils.setParameters(ps, params).addBatch();
        return ps;
    }

    /**
     * 
     */
    private final static Predicate<String> IS_LOCK_STRING = StringPredicates.containsIgnoreCase("lock");
    
    /**
     * 
     */
    private final static Predicate<String> IS_DUPLICATE_KEY_STRING = s -> 
            StringUtils.containsIgnoreCase(s, "duplicate key") 
                    || StringUtils.containsIgnoreCase(s, "duplicated key");

    /**
     * 
     * @param ex
     * @return 
     */
    public static boolean isLockException(final Exception ex) {
        if (ex instanceof SQLException se) {
            return isLockException(se);
        }
        if (ex instanceof UncheckedSQLException use) {
            return isLockException(use);
        }
        return false;
    }

    /**
     * 
     * @param sqle
     * @return 
     */
    public static boolean isLockException(final SQLException sqle) {
        final String name = sqle.getClass().getSimpleName();
        final String s = sqle.toString();
        return IS_LOCK_STRING.test(name) || IS_LOCK_STRING.test(s);
    }
    
    /**
     * 
     * @param sqle
     * @return 
     */
    public static boolean isLockException(final UncheckedSQLException sqle) {
        return isLockException(sqle.getCause());
    }

    /**
     * 
     * @param ex
     * @return 
     */
    public static boolean isDuplicateKeyException(final Exception ex) {
        if (ex instanceof SQLException se) {
            return isDuplicateKeyException(se);
        }
        if (ex instanceof UncheckedSQLException use) {
            return isDuplicateKeyException(use);
        }
        return false;
    }

    /**
     * 
     * @param sqle
     * @return 
     */
    public static boolean isDuplicateKeyException(final SQLException sqle) {
        final String name = sqle.getClass().getSimpleName();
        final String s = sqle.toString();
        return IS_DUPLICATE_KEY_STRING.test(name) || IS_DUPLICATE_KEY_STRING.test(s);
    }
    
    /**
     * 
     * @param sqle
     * @return 
     */
    public static boolean isDuplicateKeyException(final UncheckedSQLException sqle) {
        return isDuplicateKeyException(sqle.getCause());
    }

    /**
     * 
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static ResultSet executeQuery(final PreparedStatement ps, 
            final Object... params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        try {
            return SQLUtils.setParameters(ps, params).executeQuery();
        } finally {
            ps.clearParameters();
        }
    }

    /**
     * 
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static ResultSet executeQuery(final PreparedStatement ps, 
            final List<?> params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        try {
            return SQLUtils.setParameters(ps, params).executeQuery();
        } finally {
            ps.clearParameters();
        }
    }

    /**
     * 
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static ResultSet executeQuery(final PreparedStatement ps, 
            final IndexedSeq<?> params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        try {
            return SQLUtils.setParameters(ps, params).executeQuery();
        } finally {
            ps.clearParameters();
        }
    }

    /**
     * 
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static ResultSet executeQueryV1(final PreparedStatement ps, 
            final Object... params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        ps.clearParameters();
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        return ps.executeQuery();
    }

    /**
     * 
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static int executeUpdate(final PreparedStatement ps, 
            final Object... params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        try {
            return SQLUtils.setParameters(ps, params).executeUpdate();
        } finally {
            ps.clearParameters();
        }
    }

    /**
     * 
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static int executeUpdate(final PreparedStatement ps, 
            final List<?> params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        try {
            return SQLUtils.setParameters(ps, params).executeUpdate();
        } finally {
            ps.clearParameters();
        }
    }

    /**
     * 
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    public static int executeUpdate(final PreparedStatement ps, 
            final IndexedSeq<?> params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        try {
            return SQLUtils.setParameters(ps, params).executeUpdate();
        } finally {
            ps.clearParameters();
        }
    }

    /**
     * 
     * @param ps
     * @param params
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static int executeUpdateV1(final PreparedStatement ps, 
            final Object... params) throws SQLException {
        Objects.requireNonNull(ps, "PreparedStatement");
        Objects.requireNonNull(params, "Parameters");
        ps.clearParameters();
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        return ps.executeUpdate();
    }

    /**
     * 
     * @param ps
     * @return
     * @throws SQLException 
     */
    public static ExecuteBatchResult executeBatch(final PreparedStatement ps) throws SQLException {
        return PreparedStatementExecute.of(ps).executeBatch();
    }

    /**
     * 
     * @param ps
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static ExecuteBatchResult executeBatchV1(final PreparedStatement ps) throws SQLException {
        int[] statusCodes;
        final Variable<BatchUpdateException> batchUpdateException = 
                Variables.forClass(BatchUpdateException.class);
        try {
            statusCodes = ps.executeBatch();
        } catch (final BatchUpdateException bue) {
            batchUpdateException.set(bue);
            statusCodes = bue.getUpdateCounts();
        }
        /*
        final List<String> l = new ArrayList();
        for (int i = 0; i < statusCodes.length; i++) {
            l.add(String.valueOf(statusCodes[i]));
        }
        System.out.println(StringUtils.join(l));
        */
        
        return ExecuteBatchResult.of(statusCodes, batchUpdateException.get());
        
        /*
        final SortedMap<Integer, Integer> succeededCount = new TreeMap();
        final SortedMap<Integer, SQLException> failedCount = new TreeMap();
        final Variable<Boolean> failureFound = Variables.withDefault(false);
        final List<Integer> updateCodes = new ArrayList();
        for (int index = 0; index < statusCodes.length; index++) {
            final int statCode = statusCodes[index];
            updateCodes.add(statCode);
            if (statCode == Statement.EXECUTE_FAILED) {
                failedCount.put(index, null);
                failureFound.set(true);
            }  else {
                succeededCount.put(index, Math.max(0, statCode));
            }
        }
                
        return new ExecuteBatchResultV1() {
            @Override
            public List<Integer> resultCodes() {
                return updateCodes;
            }

            @Override
            public SortedMap<Integer, Integer> succeededCount() {
                return succeededCount;
            }

            @Override
            public SortedMap<Integer, SQLException> failedCount() {
                return failedCount;
            }

            @Override
            public boolean hasFailed() {
                return failureFound.get();
            }

            @Override
            public BatchUpdateException batchUpdateException() {
                return batchUpdateException.get();
            }
        };
        */
    }

    /**
     * 
     * @param ps
     * @return
     * @throws SQLException 
     */
    @Deprecated
    private static ExecuteBatchResultV1 executeBatchV3(final PreparedStatement ps) throws SQLException {
        int[] statusCodes;
        final Variable<BatchUpdateException> batchUpdateException = 
                Variables.forClass(BatchUpdateException.class);
        try {
            statusCodes = ps.executeBatch();
        } catch (final BatchUpdateException bue) {
            batchUpdateException.set(bue);
            statusCodes = bue.getUpdateCounts();
        }
        final List<String> l = new ArrayList();
        for (int i = 0; i < statusCodes.length; i++) {
            l.add(String.valueOf(statusCodes[i]));
        }
        System.out.println(StringUtils.join(l));
        
        return newExecuteBatchResult(statusCodes, batchUpdateException.get());
        /*
        final SortedMap<Integer, Integer> succeededCount = new TreeMap();
        final SortedMap<Integer, SQLException> failedCount = new TreeMap();
        final Variable<Boolean> failureFound = Variables.withDefault(false);
        final List<Integer> updateCodes = new ArrayList();
        for (int index = 0; index < statusCodes.length; index++) {
            final int statCode = statusCodes[index];
            updateCodes.add(statCode);
            if (statCode == Statement.EXECUTE_FAILED) {
                failedCount.put(index, null);
                failureFound.set(true);
            }  else {
                succeededCount.put(index, Math.max(0, statCode));
            }
        }
                
        return new ExecuteBatchResultV1() {
            @Override
            public List<Integer> resultCodes() {
                return updateCodes;
            }

            @Override
            public SortedMap<Integer, Integer> succeededCount() {
                return succeededCount;
            }

            @Override
            public SortedMap<Integer, SQLException> failedCount() {
                return failedCount;
            }

            @Override
            public boolean hasFailed() {
                return failureFound.get();
            }

            @Override
            public BatchUpdateException batchUpdateException() {
                return batchUpdateException.get();
            }
        };
        */
    }
    
    /**
     * 
     * @param len
     * @param value
     * @return 
     */
    public static ExecuteBatchResultV1 newExecuteBatchResult(final int len, final int value) {
        final int[] statusCodes = new int[len];
        for (int i = 0; i < len; i++) {
            statusCodes[i] = value;
        }
        return newExecuteBatchResult(statusCodes, null);
    }
    
    /**
     * 
     * @param statusCodes
     * @param batchUpdateException
     * @return 
     */
    public static ExecuteBatchResultV1 newExecuteBatchResult(final int[] statusCodes,
            final BatchUpdateException batchUpdateException) {
        final SortedMap<Integer, Integer> succeeded = new TreeMap();
        final SortedMap<Integer, SQLException> failed = new TreeMap();
        final Variable<Boolean> failureFound = Variables.withDefault(false);
        final List<Integer> updateCodes = new ArrayList();
        for (int index = 0; index < statusCodes.length; index++) {
            final int statCode = statusCodes[index];
            updateCodes.add(statCode);
            if (statCode == Statement.EXECUTE_FAILED) {
                failed.put(index, null);
                failureFound.set(true);
            }  else {
                succeeded.put(index, Math.max(0, statCode));
            }
        }
        return new ExecuteBatchResultV1() {
            @Override
            public List<Integer> resultCodes() {
                return updateCodes;
            }

            @Override
            public SortedMap<Integer, Integer> succeeded() {
                return succeeded;
            }

            @Override
            public SortedMap<Integer, SQLException> failed() {
                return failed;
            }

            @Override
            public boolean hasFailed() {
                return failureFound.get();
            }

            @Override
            public BatchUpdateException batchUpdateException() {
                return batchUpdateException;
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @param supplier
     * @return 
     */
    public static <T> Either<SQLException, T> lenient(final FailableSupplier<T, SQLException> supplier) {
        try {
            return Right(Objects.requireNonNull(supplier, "Supplier").get());
        } catch (final SQLException e) {
            return Left(e);
        }
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param supplier
     * @param exceptions
     * @return 
     */
    public static <T, E extends Collection<? super SQLException>> T lenient(final FailableSupplier<T, SQLException> supplier,
            final E exceptions) {
        try {
            return Objects.requireNonNull(supplier, "Supplier").get();
        } catch (final SQLException e) {
            exceptions.add(e);
            return null;
        }
    }

    /**
     * 
     * @param <R>
     * @param exception
     * @return
     * @throws SQLException 
     */
    public static <R> R throwSQLException(final Exception exception) throws SQLException{
        Throwable t = Objects.requireNonNull(exception, "Exception");
        if (exception instanceof InterruptedException) {
            Thread.currentThread().interrupt();
        }
        do {
            if (t instanceof SQLException se) {
                throw se;
            }
            if (t instanceof UncheckedSQLException use) {
                throw use.getCause();
            }
            t = t.getCause();
        } while (t != null);
        throw new UndeclaredThrowableException(exception);
    }
    
    /**
     * 
     * @param transIsolationName
     * @return 
     */
    public static int toTransactionIsolation(final String transIsolationName) {
        return TransactionIsolation.of(transIsolationName).value();
    }
        
}
