/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.ArrayTable;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.LinkedHashMultiset;
import com.google.common.collect.Multiset;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Vector;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.interop.Guava;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;


/**
 *
 * @author AndreaR
 */
public class ResultSetUtils {

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static CaseSensitivity caseSensitivity(final ResultSet rs) throws SQLException {
        final Predicate<String> pred = k -> {
            try {
                return rs.findColumn(k) >= 0;
            } catch (final SQLException ex) {
                return false;
            }
        };
        final Iterable<String> keys = columnNames(rs);
        return CaseSensitivity.fromMapKeys(keys, pred);
    }
    
    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static IterableAccessor<String> columnNames(final ResultSet rs) throws SQLException {
        final ResultSetMetaData md = rs.getMetaData();
        final String[] colNames = new String[md.getColumnCount()];
        for (int i = 0; i < colNames.length; i++) {
            colNames[i] = md.getColumnName(i + 1);
        }
        final String dupCols = Vector.of(colNames)
                .groupBy(Function.identity())
                .filter(e -> e._2.size() > 1)
                .map(e -> e._1)
                .mkString(", ");
        if (!dupCols.isEmpty()) {
            throw new SQLException("Duplicated column name(s): " + dupCols);
        }
        return IterableAccessors.of(colNames);
    }

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static IterableAccessor<String> columnNamesV2(final ResultSet rs) throws SQLException {
        final ResultSetMetaData md = rs.getMetaData();
        final String[] colNames = new String[md.getColumnCount()];
        final java.util.HashSet<String> uniqueColNames = new LinkedHashSet();
        final java.util.HashSet<String> duplicatedColNames = new LinkedHashSet();
        for (int i = 0; i < md.getColumnCount(); i++) {
            final String colName = md.getColumnName(i + 1);
            if (!uniqueColNames.add(colName)) {
                duplicatedColNames.add(colName);
            }
            colNames[i] = colName;
        }
        if (!duplicatedColNames.isEmpty()) {
            throw new SQLException("Duplicated column name(s): " + IterableAccessors.ofAll(duplicatedColNames).mkString(", "));
        }
        return IterableAccessors.of(colNames);
    }

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static IterableAccessor<String> columnNamesV1(final ResultSet rs) throws SQLException {
        final ResultSetMetaData md = rs.getMetaData();
        final Function<Integer, String> getColName = i -> {
            try {
                return md.getColumnName(i + 1);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        };
        final Vector<String> keys = io.vavr.collection.Vector.range(0, md.getColumnCount()).map(getColName);
        return IterableAccessors.ofAll(keys);
    }

    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */    
    public static BiMap<Integer, String> columnIndexNameBiMap(final ResultSet rs) throws SQLException {
        return columnIndexNameBiMap(Objects.requireNonNull(rs, "ResultSet").getMetaData());
    }

    /**
     * 
     * @param md
     * @return
     * @throws SQLException 
     */
    public static BiMap<Integer, String> columnIndexNameBiMap(final ResultSetMetaData md) throws SQLException {
        Objects.requireNonNull(md, "ResultSetMetaData");
        final BiMap<Integer, String> m = HashBiMap.create(md.getColumnCount());
        for (int i = 1; i <= md.getColumnCount(); i++) {
            m.put(i, md.getColumnName(i));
        }
        return m;
    }
    
    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static BiMap<String, Integer> columnNameIndexBiMap(final ResultSet rs) throws SQLException {
        return columnNameIndexBiMap(Objects.requireNonNull(rs, "ResultSet").getMetaData());
    }

    /**
     * 
     * @param md
     * @return
     * @throws SQLException 
     */
    public static BiMap<String, Integer> columnNameIndexBiMap(final ResultSetMetaData md) throws SQLException {
        Objects.requireNonNull(md, "ResultSetMetaData");
        final BiMap<String, Integer> m = HashBiMap.create(md.getColumnCount());
        for (int i = 1; i <= md.getColumnCount(); i++) {
            m.put(md.getColumnName(i), i);
        }
        return m;
    }

    /**
     * 
     * @param <M>
     * @param <S>
     * @param rs
     * @param supplier
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static <M extends Map<String, Object>, S extends Supplier<M>> M putAll_old(
            final ResultSet rs,
            final S supplier) throws SQLException {
        Objects.requireNonNull(supplier, "Supplier");
        return ResultSetUtils.putAll_old(rs, supplier.get());
    }

    /**
     * 
     * @param <M>
     * @param rs
     * @param dest
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static <M extends Map<String, Object>> M putAll_old(
            final ResultSet rs,
            final M dest) throws SQLException {
        Objects.requireNonNull(rs, "ResultSet");
        Objects.requireNonNull(dest, "Map");
        final ResultSetMetaData md = rs.getMetaData();
        for (int i = 1; i <= md.getColumnCount(); i++) {
            dest.put(md.getColumnName(i), rs.getObject(i));
        }
        return dest;
    }
    
    /**
     * 
     * @param rs
     * @return 
     */
    public static IterableAccessor<Tuple2<String, Object>> tuplesByName(final ResultSet rs) {
        try {
            final ResultSetMetaData md = rs.getMetaData();
            final int cols = md.getColumnCount();
            final Tuple2[] data = new Tuple2[cols];
            for (int i = 0; i < cols; i++) {
                data[i] = Tuple(md.getColumnName(i + 1), rs.getObject(i + 1));
            }
            return IterableAccessors.of(data);
        } catch (final SQLException sqlex) {
            throw new UncheckedSQLException(sqlex);
        }
    }
    
    /**
     * 
     * @param rs
     * @return 
     */
    public static IterableAccessor<Tuple2<Integer, Object>> tuplesByIndex(final ResultSet rs) {
        try {
            final ResultSetMetaData md = rs.getMetaData();
            final int cols = md.getColumnCount();
            final Tuple2[] data = new Tuple2[cols];
            for (int i = 0; i < cols; i++) {
                data[i] = Tuple(i, rs.getObject(i + 1));
            }
            return IterableAccessors.of(data);
        } catch (final SQLException sqlex) {
            throw new UncheckedSQLException(sqlex);
        }
    }
    
    /**
     * 
     * @param <M>
     * @param <S>
     * @param rs
     * @param supplier
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static <M extends Map<String, Object>, S extends Supplier<M>> M insertAll_old(
            final S supplier,
            final ResultSet rs) throws SQLException {
        Objects.requireNonNull(supplier, "Supplier");
        return insertAll_old(supplier.get(), rs);
    }

    /**
     * 
     * @param <M>
     * @param rs
     * @param dest
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static <M extends Map<String, Object>> M insertAll_old(
            final M dest,
            final ResultSet rs) throws SQLException {
        Objects.requireNonNull(dest, "Map");
        Objects.requireNonNull(rs, "ResultSet");
        final ResultSetMetaData md = rs.getMetaData();
        for (int i = 1; i <= md.getColumnCount(); i++) {
            MapUtils.insert(dest, md.getColumnName(i), rs.getObject(i));
        }
        return dest;
    }

    /**
     * 
     * @param <M>
     * @param <S>
     * @param <C>
     * @param rs
     * @param coll
     * @param supplier
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public static <M extends Map<String, Object>, S extends Supplier<M>, C extends Collection<M>> C old_collect(
            final ResultSet rs,
            final C coll,
            final S supplier) throws SQLException {
        Objects.requireNonNull(coll, "Collection");
        while (rs.next()) {
            coll.add(ResultSetUtils.putAll_old(rs, supplier.get()));
        }          
        return coll;
    }

    /**
     * 
     * @param <A>
     * @param <R>
     * @param rs
     * @param collector
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public final static <A, R> R old_collect(final ResultSet rs, final Collector<? super ResultSet, A, R> collector) throws SQLException {
        final A data = collector.supplier().get();
        final BiConsumer<A, ? super ResultSet> accumulator = collector.accumulator();
        while (rs.next()) {
            accumulator.accept(data, rs);
        }
        return collector.finisher().apply(data);
    }

    /**
     * 
     * @param rs
     * @return 
     */
    public static Iterator<ResultSet> iterator(final ResultSet rs) {
        return new ResultSetIterator(rs);
    }

    /**
     * 
     * @param rs
     * @return 
     */
    public static Iterator<ResultSet> iterator2(final ResultSet rs) {
        return new IterableAgainResultSetIterator(rs);
    }

    /**
     * 
     * @param rs
     * @return 
     */
    public static IterableAccessor<ResultSet> iteration(final ResultSet rs) {
        return IterableAccessors.from(iterator(rs));
    }
    
    /**
     * 
     * @param rs
     * @return 
     */
    public static IterableAccessor<ResultSet> iteration2(final ResultSet rs) {
        final Iterable<ResultSet> itr = () -> iterator2(rs);
        return IterableAccessors.from(itr);
    }

    /**
     * 
     */
    private static class ResultSetIterator extends AbstractIterator<ResultSet> implements Iterator<ResultSet> {

        /**
         * 
         * @param rs 
         */
        protected ResultSetIterator(final ResultSet rs) {
            this.rs = checkNotNull(rs, "ResultSet");
            this.urs = currentRow(rs);
        }

        /**
         * 
         * @return 
         */
        @Override
        protected ResultSet computeNext() {
            try {
                // Reset the Iterator if it is the case
                if (rs.isAfterLast()) {
                    rs.beforeFirst();
                }
                if (rs.next()) {
                    return urs;
                }
                return endOfData();
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }

        /**
         * 
         */
        private final ResultSet rs;
        
        private final ResultSet urs;

    }

    /**
     * 
     */
    private static class IterableAgainResultSetIterator extends AbstractIterator<ResultSet> implements Iterator<ResultSet> {

        /**
         * 
         * @param rs 
         */
        protected IterableAgainResultSetIterator(final ResultSet rs) {
            this.rs = checkNotNull(rs, "ResultSet");
            this.urs = currentRow(rs);
        }

        /**
         * 
         * @return 
         */
        @Override
        protected ResultSet computeNext() {
            try {
                if (firstCall) {
                    if (rs.isAfterLast()) {
                        // Special case when result set is empty
                        if (!rs.isBeforeFirst()) {
                            rs.beforeFirst();
                        }
                    } else {
                        if (!rs.isBeforeFirst()) {
                            throw new SQLException("Cursor is not beforeFirst or afterLast, check for concurrent usage of the ResultSet");
                        }
                    }
                    firstCall = false;
                }
                if (rs.next()) {
                    return urs;
                }
                return endOfData();
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }

        private boolean firstCall = true;
        
        private final ResultSet rs;
        
        private final ResultSet urs;

    }

    /**
     * 
     * @param rs
     * @return 
     */
    public static ResultSet currentRow(final ResultSet rs) {
        Objects.requireNonNull(rs, "ResultSet");
        if (rs instanceof CurrentRowResultSet) {
            return rs;
        }
        return new CurrentRowResultSet() {
            @Override
            protected ResultSet rs() throws SQLException {
                return rs;
            }
        };
    }
    
    /**
     * 
     */
    static abstract class CurrentRowResultSet extends AbstractResultSetDecorator {

        @Override
        protected boolean isUpdateEnabled() {
            return true;
        }

        @Override
        protected boolean isScrollEnabled() {
            return false;
        }

        @Override
        protected boolean isCloseEnabled() {
            return false;
        }
        
    }

    /**
     * 
     * @param rs
     * @param isUpdateEnabled
     * @param isScrollEnabled
     * @param isCloseEnabled
     * @return 
     */
    public static ResultSet defaultDecorator0(final ResultSet rs, final boolean isUpdateEnabled, final boolean isScrollEnabled, final boolean isCloseEnabled) {
        Objects.requireNonNull(rs, "ResultSet");
        return new AbstractResultSetDecorator() {
            @Override
            protected ResultSet rs() throws SQLException {
                return rs;
            }

            @Override
            protected boolean isUpdateEnabled() {
                return isUpdateEnabled;
            }

            @Override
            protected boolean isScrollEnabled() {
                return isScrollEnabled;
            }

            @Override
            protected boolean isCloseEnabled() {
                return isCloseEnabled;
            }
        };
    }
    
}
