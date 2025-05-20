/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.accessors.MutatorMethods;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.accessors.sql.ResultSetAccessor;
import org.joytools.commons.accessors.sql.ResultSetAccessors;
import org.joytools.commons.collections.MapUtils;
import org.joytools.commons.function.FailableAutoCloseables;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.text.PropertyStrings;


/**
 *
 * @author AndreaR
 */
public abstract class QueryProcessor {

    /**
     *
     * @param rs
     * @return
     */
    public static QueryProcessor on(final ResultSet rs) {
        return new RSQueryProcessor(rs);
    }

    /**
     *
     * @param stm
     * @return
     */
    public static STMQueryProcessor on(final Statement stm) {
        return new STMQueryProcessor(stm);
    }

    /**
     * 
     * @param stm
     * @return 
     */
    public static PSTMQueryProcessor on(final PreparedStatement stm) {
        return new PSTMQueryProcessor(stm);
    }

    /**
     *
     * @param cn
     * @return
     */
    public static CSQueryProcessor on(final Connection cn) {
        return new CSQueryProcessor(ConnectionSource.of(cn));
    }

    /**
     * 
     * @param psc
     * @return 
     */
    public static PSCQueryProcessor on(final PreparedStatementContainer psc) {
        return new PSCQueryProcessor(psc);
    }

    /**
     *
     * @param ds
     * @return
     */
    public static CSQueryProcessor on(final DataSource ds) {
        return new CSQueryProcessor(ConnectionSource.of(ds));
    }

    /**
     * 
     * @param cs
     * @return 
     */
    public static CSQueryProcessor on(final ConnectionSource cs) {
        return new CSQueryProcessor(cs);
    }

    /**
     * 
     */
    public final class Unchecked {

        public <M extends Map<String, Object>, C extends Collection<M>> C collect(
            final C coll,
            final Supplier<M> supplier) {
            try {
                return QueryProcessor.this.collect(coll, supplier);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }

        public <A, R> R collect(final Collector<? super ResultSetAccessor, A, R> collector) {
            try {
                return QueryProcessor.this.collect(collector);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }
        
        public <A, U, R> R collect(
                final Function<? super ResultSetAccessor, ? extends U> mapper, 
                final Collector<? super U, A, R> downstream) {
            try {
                return QueryProcessor.this.collect(mapper, downstream);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }

        public <M extends Map<String, Object>> M collect(final M map) {
            try {
                return QueryProcessor.this.collect(map);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }
        
        public int forEach(final Consumer<ResultSetAccessor<Object>> consumer) {
            try {
                return QueryProcessor.this.forEach(consumer::accept);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }
        
        public <U, R> R to(
            final Function<? super ResultSetAccessor<Object>, U> mapper, 
            final Function<? super Iterable<U>, R> finisher) {
            try {
                return QueryProcessor.this.to(mapper, finisher);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }

        public <T1, T2, T> T to(
            final Function<? super ResultSetAccessor<Object>, T1> mapper1, 
            final Function<? super ResultSetAccessor<Object>, T2> mapper2, 
            final Function<? super Iterable<Tuple2<T1, T2>>, T> finisher) {
            try {
                return QueryProcessor.this.to(mapper1, mapper2, finisher);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }
        
        public <T1, T2, T3, T> T to(
            final Function<? super ResultSetAccessor<Object>, T1> mapper1, 
            final Function<? super ResultSetAccessor<Object>, T2> mapper2, 
            final Function<? super ResultSetAccessor<Object>, T3> mapper3, 
            final Function<? super Iterable<Tuple3<T1, T2, T3>>, T> finisher) {
            try {
                return QueryProcessor.this.to(mapper1, mapper2, mapper3, finisher);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }
        
        public <U, R> R evalTo(
            final Function<? super ResultSetAccessor<Object>, U> mapper, 
            final Function<? super Iterable<U>, R> finisher) {
            try {
                return QueryProcessor.this.evalTo(mapper, finisher);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }

        public <T1, T2, T> T evalTo(
            final Function<? super ResultSetAccessor<Object>, T1> mapper1, 
            final Function<? super ResultSetAccessor<Object>, T2> mapper2, 
            final Function<? super Iterable<Tuple2<T1, T2>>, T> finisher) {
            try {
                return QueryProcessor.this.evalTo(mapper1, mapper2, finisher);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }
        
        public <T1, T2, T3, T> T evalTo(
            final Function<? super ResultSetAccessor<Object>, T1> mapper1, 
            final Function<? super ResultSetAccessor<Object>, T2> mapper2, 
            final Function<? super ResultSetAccessor<Object>, T3> mapper3, 
            final Function<? super Iterable<Tuple3<T1, T2, T3>>, T> finisher) {
            try {
                return QueryProcessor.this.evalTo(mapper1, mapper2, mapper3, finisher);
            } catch (final SQLException ex) {
                throw new UncheckedSQLException(ex);
            }
        }

    }

    /**
     * 
     */
    private Unchecked m_unchecked;
    
    /**
     * 
     * @return 
     */
    public final Unchecked unchecked() {
        if (m_unchecked == null) {
            m_unchecked = new Unchecked();
        }
        return m_unchecked;
    }
    
    /**
     * 
     * @param consumer
     * @return
     * @throws SQLException 
     */
    public abstract int forEach(final FailableConsumer<ResultSetAccessor<Object>, SQLException> consumer) throws SQLException;

    /**
     * 
     * @param rs
     * @param accessor
     * @param consumer
     * @return
     * @throws SQLException 
     */
    protected final int forEach0(
            final ResultSet rs,
            final ResultSetAccessor<Object> accessor, 
            final FailableConsumer<ResultSetAccessor<Object>, SQLException> consumer) throws SQLException {
        try {
            int count = 0;
            while (rs.next()) {
                count++;
                if (consumer != null) {
                    consumer.accept(accessor);
                }
            }
            return count;
        } catch (final UncheckedSQLException sre) {
            throw sre.getCause();
        }
    }

    /**
     * 
     * @param <M>
     * @param map
     * @return 
     * @throws SQLException 
     */
    public final <M extends Map<String, Object>> M collect(final M map) throws SQLException {
        Objects.requireNonNull(map, "Map");
        final AtomicInteger count = new AtomicInteger();
        forEach(acc -> {
            if (count.incrementAndGet() > 1) {
                throw new SQLException("More that one record found");
            }
            for (final String key : acc.keys()) {
                MapUtils.insert(map, key, acc.getOrFail(key));
            }
        });
        if (count.get() == 0) {
            throw new SQLException("No record found");
        }
        return map;
    }

    /**
     * 
     * @param <M>
     * @param <C>
     * @param coll
     * @param supplier
     * @return
     * @throws SQLException 
     */
    public final <M extends Map<String, Object>, C extends Collection<M>> C collect(
            final C coll,
            final Supplier<M> supplier) throws SQLException {
        Objects.requireNonNull(coll, "Collection");
        Objects.requireNonNull(supplier, "Map supplier");
        forEach(rs -> {
            final M m = supplier.get();
            for (final String key : rs.keys()) {
                MutatorMethods.insert(m, key, rs.getOrFail(key));
            }
            coll.add(m);
        });
        return coll;
    }
    
    /**
     * 
     * @param <A>
     * @param <R>
     * @param collector
     * @return 
     * @throws java.sql.SQLException 
     */
    public final <A, R> R collect(final Collector<? super ResultSetAccessor, A, R> collector) throws SQLException {
        Objects.requireNonNull(collector, "Collector");
        try (final QueryResult qr = executeQuery()) {
            final A data = collector.supplier().get();
            final BiConsumer<A, ? super ResultSetAccessor> accumulator = collector.accumulator();
            for (final ResultSetAccessor<Object> acc : qr.rows()) {
                accumulator.accept(data, acc);
            }
            return collector.finisher().apply(data);
        }
    }

    /**
     * 
     * @param <A>
     * @param <U>
     * @param <R>
     * @param mapper
     * @param downstream
     * @return
     * @throws SQLException 
     */
    public final <A, U, R> R collect(
            final Function<? super ResultSetAccessor, ? extends U> mapper, 
            final Collector<? super U, A, R> downstream) throws SQLException {
        return collect(Collectors.mapping(mapper, downstream));
    }

    /**
     * 
     * @param <U>
     * @param <R>
     * @param mapper
     * @param finisher
     * @return
     * @throws SQLException 
     */
    public final <U, R> R evalTo(
            final Function<? super ResultSetAccessor<Object>, U> mapper, 
            final Function<? super Iterable<U>, R> finisher) throws SQLException {
        Objects.requireNonNull(mapper, "Mapper");
        Objects.requireNonNull(finisher, "Finisher");
        try (final QueryResult qr = executeQuery()) {
            return qr.rows()
                    .map(acc -> mapper.apply(acc))
                    .eval()
                    .to(finisher);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T>
     * @param mapper1
     * @param mapper2
     * @param finisher
     * @return
     * @throws SQLException 
     */
    public final <T1, T2, T> T evalTo(
            final Function<? super ResultSetAccessor<Object>, ? extends T1> mapper1, 
            final Function<? super ResultSetAccessor<Object>, ? extends T2> mapper2, 
            final Function<? super Iterable<Tuple2<T1, T2>>, T> finisher) throws SQLException {
        Objects.requireNonNull(mapper1, "Mapper 1");
        Objects.requireNonNull(mapper2, "Mapper 2");
        Objects.requireNonNull(finisher, "Finisher");
        try (final QueryResult qr = executeQuery()) {
            return qr.rows()
                    .map(acc -> Tuple(mapper1.apply(acc), mapper2.apply(acc)))
                    .eval()
                    .to(finisher);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T>
     * @param mapper1
     * @param mapper2
     * @param mapper3
     * @param finisher
     * @return
     * @throws SQLException 
     */
    public final <T1, T2, T3, T> T evalTo(
            final Function<? super ResultSetAccessor<Object>, ? extends T1> mapper1, 
            final Function<? super ResultSetAccessor<Object>, ? extends T2> mapper2, 
            final Function<? super ResultSetAccessor<Object>, ? extends T3> mapper3, 
            final Function<? super Iterable<Tuple3<T1, T2, T3>>, T> finisher) throws SQLException {
        Objects.requireNonNull(mapper1, "Mapper 1");
        Objects.requireNonNull(mapper2, "Mapper 2");
        Objects.requireNonNull(mapper3, "Mapper 3");
        Objects.requireNonNull(finisher, "Finisher");
        try (final QueryResult qr = executeQuery()) {
            return qr.rows()
                    .map(acc -> Tuple(mapper1.apply(acc), mapper2.apply(acc), mapper3.apply(acc)))
                    .eval()
                    .to(finisher);
        }
    }
    /**
     * 
     * @param <U>
     * @param <R>
     * @param mapper
     * @param finisher
     * @return 
     * @throws java.sql.SQLException 
     */
    public final <U, R> R to(
            final Function<? super ResultSetAccessor<Object>, U> mapper, 
            final Function<? super Iterable<U>, R> finisher) throws SQLException {
        Objects.requireNonNull(mapper, "Mapper");
        Objects.requireNonNull(finisher, "Finisher");
        try (final QueryResult qr = executeQuery()) {
            return qr.rows()
                    .map(acc -> mapper.apply(acc))
                    .to(finisher);
        } 
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T>
     * @param mapper1
     * @param mapper2
     * @param finisher
     * @return
     * @throws SQLException 
     */
    public final <T1, T2, T> T to(
            // final Function<? super ResultSetAccessor<Object>, ? extends K> keyMapper, 
            // final Function<? super ResultSetAccessor<Object>, ? extends V> valueMapper, 
            final Function<? super ResultSetAccessor<Object>, T1> mapper1, 
            final Function<? super ResultSetAccessor<Object>, T2> mapper2, 
            final Function<? super Iterable<Tuple2<T1, T2>>, T> finisher) throws SQLException {
        Objects.requireNonNull(mapper1, "Mapper 1");
        Objects.requireNonNull(mapper2, "Mapper 2");
        Objects.requireNonNull(finisher, "Finisher");
        try (final QueryResult qr = executeQuery()) {
            return qr.rows()
                    .map(acc -> Tuple(mapper1.apply(acc), mapper2.apply(acc)))
                    .to(finisher);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T>
     * @param mapper1
     * @param mapper2
     * @param mapper3
     * @param finisher
     * @return
     * @throws SQLException 
     */
    public final <T1, T2, T3, T> T to(
            // final Function<? super ResultSetAccessor<Object>, ? extends K> keyMapper, 
            // final Function<? super ResultSetAccessor<Object>, ? extends V> valueMapper, 
            final Function<? super ResultSetAccessor<Object>, T1> mapper1, 
            final Function<? super ResultSetAccessor<Object>, T2> mapper2, 
            final Function<? super ResultSetAccessor<Object>, T3> mapper3, 
            final Function<? super Iterable<Tuple3<T1, T2, T3>>, T> finisher) throws SQLException {
        Objects.requireNonNull(mapper1, "Mapper 1");
        Objects.requireNonNull(mapper2, "Mapper 2");
        Objects.requireNonNull(mapper3, "Mapper 3");
        Objects.requireNonNull(finisher, "Finisher");
        try (final QueryResult qr = executeQuery()) {
            return qr.rows()
                    .map(acc -> Tuple(mapper1.apply(acc), mapper2.apply(acc), mapper3.apply(acc)))
                    .to(finisher);
        }
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */ /*
    public JsonArray toJsonArray() throws SQLException {
        final JsonArrayBuilder builder = Json.createArrayBuilder();
        old_forEachMap(m -> {
            builder.add(m.toJsonObject());
        });
        return builder.build();
    } */
    
    /**
     * 
     * @return 
     */
    abstract public QueryResult executeQuery();

    /**
     *
     * @param o
     * @return 
     */
    protected static String checkedQuery(final Object o) {
        String s = null;
        if (o != null) {
            if (o instanceof CharSequence) {
                s = StringUtils.toEmptyString(o).trim();
            } else
            if (o instanceof Supplier) {
                final CharSequence cs = CharSequence.class.cast(Supplier.class.cast(o).get());
                s = StringUtils.toEmptyString(cs).trim();
            } else
            if (o instanceof QueryDef) {
                final QueryDef qd = QueryDef.class.cast(o);
                s = StringUtils.toEmptyString(qd.sql()).trim();
            } else {
                throw new IllegalArgumentException("Unknown query type: " + o.getClass());
            }
        }
        if (StringUtils.isEmpty(s)) {
            throw new IllegalArgumentException("No query specified");
        }
        return s;
    }

    /**
     *
     */
    private static class RSQueryProcessor extends QueryProcessor {

        public RSQueryProcessor(final ResultSet rs) {
            this.rs = Objects.requireNonNull(rs, "ResultSet");
        }
        
        @Override
        public int forEach(final FailableConsumer<ResultSetAccessor<Object>, SQLException> consumer) throws SQLException {
            final ResultSetAccessor<Object> accessor = ResultSetAccessors.of(rs);
            return forEach0(rs, accessor, consumer);
        }

        private final ResultSet rs;

        @Override
        public QueryResult executeQuery() {
            return new QueryResult() {
                @Override
                public void init() {
                }

                @Override
                protected ResultSet getRS0() throws SQLException {
                    return rs;
                }
            };
        }

    }

    /**
     *
     */
    public static class STMQueryProcessor extends QueryProcessor {

        public STMQueryProcessor(final Statement stm) {
            this.stm = Objects.requireNonNull(stm);
        }

        public STMQueryProcessor query(final Supplier<? extends CharSequence> select) {
            this.query = select;
            return this;
        }
        
        public STMQueryProcessor query(final CharSequence select) {
            this.query = select;
            return this;
        }

        public STMQueryProcessor query(final CharSequence select,
                final CharSequence... items) {
            return query(PropertyStrings.of(select, items));
        }

        public STMQueryProcessor query(final QueryDef qd) {
            if (!qd.parameters().isEmpty()) {
                throw new UnsupportedOperationException("Statement does not support " + qd.parameters().size() + " parameter(s) in QueryDef");
            }      
            this.query = qd;
            return this;
        }

        @Override
        public int forEach(final FailableConsumer<ResultSetAccessor<Object>, SQLException> consumer) throws SQLException {
            final String q = checkedQuery(query);
            try (final ResultSet rs = stm.executeQuery(q)) {
                final ResultSetAccessor<Object> accessor = ResultSetAccessors.of(rs);
                return forEach0(rs, accessor, consumer);
            }
        }

        @Override
        public QueryResult executeQuery() {
            return new QueryResult() {
                @Override
                public void init() throws SQLException {
                    final String q = checkedQuery(query);
                    rs = stm.executeQuery(q);
                    dispose(rs, FailableAutoCloseables.of(rs::close));
                }
                
                @Override
                protected ResultSet getRS0() throws SQLException {
                    return rs;
                }
                
                private ResultSet rs = null;
            };
        }
        
        public int executeUpdate() throws SQLException {
            return stm.executeUpdate(checkedQuery(query));
        }

        final private Statement stm;
        
        //private Supplier<? extends CharSequence> query = null;
        private Object query = null;

    }

    /**
     *
     */
    public static class PSTMQueryProcessor extends QueryProcessor {

        public PSTMQueryProcessor(final PreparedStatement stm) {
            this.ps = Objects.requireNonNull(stm);
        }

        @Override
        public int forEach(final FailableConsumer<ResultSetAccessor<Object>, SQLException> consumer) throws SQLException {
            try (final ResultSet rs = execQuery0()) {
                final ResultSetAccessor<Object> accessor = ResultSetAccessors.of(rs);
                return forEach0(rs, accessor, consumer);
            }
        }

        protected ResultSet execQuery0() throws SQLException {
            try {
                return SQLUtils.setParameters(ps, parameters).executeQuery();
            } finally {
                ps.clearParameters();
            }
        }

        public int executeUpdate() throws SQLException {
            try {
                return SQLUtils.setParameters(ps, parameters).executeUpdate();
            } finally {
                ps.clearParameters();
            }
        }

        @Override
        public QueryResult executeQuery() {
            return new QueryResult() {
                @Override
                public void init() throws SQLException {
                    rs = execQuery0();
                    dispose(rs, FailableAutoCloseables.of(rs::close));
                }
                
                @Override
                protected ResultSet getRS0() throws SQLException {
                    return rs;
                }
                
                private ResultSet rs = null;
            };
        }
        
        public PSTMQueryProcessor parameters(final Object... params) {
            parameters = Vector.of(Objects.requireNonNull(params, "Parameters"));
            return this;
        }

        public PSTMQueryProcessor parameters(final Seq<?> params) {
            parameters = Objects.requireNonNull(params, "Parameters");
            return this;
        }

        public PSTMQueryProcessor parameters(final List<?> params) {
            parameters = Vector.ofAll(Objects.requireNonNull(params, "Parameters"));
            return this;
        } 

        final private PreparedStatement ps;
        
        // private Supplier<? extends CharSequence> query = null;
        
        private Seq<?> parameters;

    }

    /**
     *
     */
    public static class CSQueryProcessor extends QueryProcessor {

        public CSQueryProcessor(final ConnectionSource ds) {
            this.ds = Objects.requireNonNull(ds, "DataSource");
        }

        public CSQueryProcessor query(final Supplier<? extends CharSequence> select) {
            this.query = select;
            return this;
        }

        public CSQueryProcessor query(final CharSequence select) {
            this.query = select;
            return this;
        }
        
        public CSQueryProcessor query(final CharSequence select,
                final CharSequence... items) {
            return query(PropertyStrings.of(select, items));
        }

        public CSQueryProcessor query(final QueryDef qd) {
            this.query = qd;
            parameters(qd.parameters());
            return this;
        }

        public CSQueryProcessor forwardOnlyReadOnly() {
            return options(ResultSetType.FORWARD_ONLY, ResultSetConcurrency.READ_ONLY);
        }

        public CSQueryProcessor scrollSensitiveUpdatable() {
            return options(ResultSetType.SCROLL_SENSITIVE, ResultSetConcurrency.UPDATABLE);
        }
    
        public CSQueryProcessor scrollInsensitiveReadOnly() {
            return options(ResultSetType.SCROLL_INSENSITIVE, ResultSetConcurrency.READ_ONLY);
        }

        public CSQueryProcessor parameters(final Object... params) {
            parameters = Vector.of(Objects.requireNonNull(params, "Parameters"));
            return this;
        }

        public CSQueryProcessor parameters(final Seq<?> params) {
            parameters = Objects.requireNonNull(params, "Parameters");
            return this;
        }

        public CSQueryProcessor parameters(final List<?> params) {
            parameters = Vector.ofAll(Objects.requireNonNull(params, "Parameters"));
            return this;
        } 

        public CSQueryProcessor options(final ResultSetType type,
                final ResultSetConcurrency concurrency) {
            this.resultSetType = Objects.requireNonNull(type, "ResultSet Type");
            this.resultSetConcurrency = Objects.requireNonNull(concurrency, "ResultSet Concurrency");
            this.resultSetHoldability = null;
            return this;
        }

        public CSQueryProcessor options(final ResultSetType type,
                final ResultSetConcurrency concurrency,
                final ResultSetHoldability holdability) {
            this.resultSetType = Objects.requireNonNull(type, "ResultSet Type");
            this.resultSetConcurrency = Objects.requireNonNull(concurrency, "ResultSet Concurrency");
            this.resultSetHoldability = Objects.requireNonNull(holdability, "ResultSet Holdability");
            return this;
        }

        @Override
        public int forEach(final FailableConsumer<ResultSetAccessor<Object>, SQLException> consumer) throws SQLException {
            final String q = checkedQuery(query);
            try (final ConnectionSession s = ds.newSession()) {
                final Connection cn = s.connection();
                if (parameters == null) {
                    try (final Statement stm = createStatement(cn);
                        final ResultSet rs = stm.executeQuery(q)) {
                        final ResultSetAccessor<Object> accessor = ResultSetAccessors.of(rs);
                        return forEach0(rs, accessor, consumer);
                    }
                } else {
                    try (final PreparedStatement ps = createPreparedStatement(cn);
                        final ResultSet rs = SQLUtils.setParameters(ps, parameters).executeQuery()) {
                        final ResultSetAccessor<Object> accessor = ResultSetAccessors.of(rs);
                        return forEach0(rs, accessor, consumer);
                    } finally {
                        parameters = null;
                    }
                }
            }
        }

        /**
         * 
         * @param cn
         * @return
         * @throws SQLException 
         */
        protected Statement createStatement(final Connection cn) throws SQLException {
            if (resultSetType == null &&
                    resultSetConcurrency == null &&
                    resultSetHoldability == null) {
                return cn.createStatement();
            }
            if (resultSetType != null &&
                    resultSetConcurrency != null &&
                    resultSetHoldability != null) {
                return cn.createStatement(resultSetType.value(),
                        resultSetConcurrency.value(),
                        resultSetHoldability.value());
            }
            if (resultSetType != null &&
                    resultSetConcurrency != null) {
                return cn.createStatement(resultSetType.value(),
                        resultSetConcurrency.value());
            }
            throw new IllegalArgumentException("Invalid combination of ResultSet Type, Concurrency and Holdability");
        }

        /**
         * 
         * @param cn
         * @return
         * @throws SQLException 
         */
        protected PreparedStatement createPreparedStatement(final Connection cn) throws SQLException {
            final String sql = checkedQuery(query);
            if (resultSetType == null &&
                    resultSetConcurrency == null &&
                    resultSetHoldability == null) {
                return cn.prepareStatement(sql);
            }
            if (resultSetType != null &&
                    resultSetConcurrency != null &&
                    resultSetHoldability != null) {
                return cn.prepareStatement(sql,
                        resultSetType.value(),
                        resultSetConcurrency.value(),
                        resultSetHoldability.value());
            }
            if (resultSetType != null &&
                    resultSetConcurrency != null) {
                return cn.prepareStatement(sql, 
                        resultSetType.value(),
                        resultSetConcurrency.value());
            }
            throw new IllegalArgumentException("Invalid combination of ResultSet Type, Concurrency and Holdability");
        }

        /**
         * 
         * @return 
         */
        @Override
        public QueryResult executeQuery() {
            return new QueryResult() {
                @Override
                public void init() throws SQLException {
                    final String q = checkedQuery(query);
                    final ConnectionSession s = checkedDispose(ds.newSession());
                    final Connection cn = s.connection();
                    if (parameters == null) {
                        final Statement stm = createStatement(cn);
                        dispose(stm, FailableAutoCloseables.of(stm::close));
                        rs = stm.executeQuery(q);
                        dispose(rs, FailableAutoCloseables.of(rs::close));
                    } else {
                        final PreparedStatement ps = createPreparedStatement(cn);
                        dispose(ps, FailableAutoCloseables.of(ps::close));
                        try {
                            rs = SQLUtils.setParameters(ps, parameters).executeQuery();
                            dispose(rs, FailableAutoCloseables.of(rs::close));
                        } finally {
                            parameters = null;
                        }
                    }
                }

                @Override
                protected ResultSet getRS0() throws SQLException {
                    return rs;
                }

                private ResultSet rs = null;
            };
        }

        /**
         * 
         * @return
         * @throws SQLException 
         */
        public int executeUpdate() throws SQLException {
            final String q = checkedQuery(query);
            try (final ConnectionSession s = ds.newSession()) {
                final Connection cn = s.connection();
                if (parameters == null) {
                    try (final Statement stm = createStatement(cn)) {
                        return stm.executeUpdate(q);
                    }
                } else {
                    try (final PreparedStatement ps = createPreparedStatement(cn)) {
                        return SQLUtils.setParameters(ps, parameters).executeUpdate();
                    } finally {
                        parameters = null;
                    }
                }
            }
        }

        final private ConnectionSource ds;
        
        //private Supplier<? extends CharSequence> query = null;
        private Object query = null;
        
        private ResultSetType resultSetType = null;

        private ResultSetConcurrency resultSetConcurrency = null;

        private ResultSetHoldability resultSetHoldability = null;
        
        private Seq<?> parameters;
    }
     
    /**
     *
     */
    public static class PSCQueryProcessor extends QueryProcessor {

        public PSCQueryProcessor(final PreparedStatementContainer psc) {
            this.psc = Objects.requireNonNull(psc, "PreparedStatementContainer");
        }

        public PSCQueryProcessor query(final Supplier<? extends CharSequence> select) {
            this.query = select;
            return this;
        }

        public PSCQueryProcessor query(final CharSequence select) {
            this.query = select;
            return this;
        }
        
        public PSCQueryProcessor query(final CharSequence select,
                final CharSequence... items) {
            return query(PropertyStrings.of(select, items));
        }

        public PSCQueryProcessor query(final QueryDef qd) {
            this.query = qd;
            parameters(qd.parameters());
            return this;
        }

        public PSCQueryProcessor forwardOnlyReadOnly() {
            return options(ResultSetType.FORWARD_ONLY, ResultSetConcurrency.READ_ONLY);
        }

        public PSCQueryProcessor scrollSensitiveUpdatable() {
            return options(ResultSetType.SCROLL_SENSITIVE, ResultSetConcurrency.UPDATABLE);
        }
    
        public PSCQueryProcessor options(final ResultSetType type,
                final ResultSetConcurrency concurrency) {
            this.resultSetType = Objects.requireNonNull(type, "ResultSet Type");
            this.resultSetConcurrency = Objects.requireNonNull(concurrency, "ResultSet Concurrency");
            this.resultSetHoldability = null;
            return this;
        }

        public PSCQueryProcessor options(final ResultSetType type,
                final ResultSetConcurrency concurrency,
                final ResultSetHoldability holdability) {
            this.resultSetType = Objects.requireNonNull(type, "ResultSet Type");
            this.resultSetConcurrency = Objects.requireNonNull(concurrency, "ResultSet Concurrency");
            this.resultSetHoldability = Objects.requireNonNull(holdability, "ResultSet Holdability");
            return this;
        }

        @Override
        public int forEach(final FailableConsumer<ResultSetAccessor<Object>, SQLException> consumer) throws SQLException {
            try (final ResultSet rs = execQuery0()) {
                final ResultSetAccessor<Object> accessor = ResultSetAccessors.of(rs);
                return forEach0(rs, accessor, consumer);
            }
        }

        public PSCQueryProcessor parameters(final Object... params) {
            parameters = Vector.of(Objects.requireNonNull(params, "Parameters"));
            return this;
        }

        public PSCQueryProcessor parameters(final Seq<?> params) {
            parameters = Objects.requireNonNull(params, "Parameters");
            return this;
        }

        public PSCQueryProcessor parameters(final List<?> params) {
            parameters = Vector.ofAll(Objects.requireNonNull(params, "Parameters"));
            return this;
        } 

        protected ResultSet execQuery0() throws SQLException {
            final PreparedStatement ps = psc.get(checkedQuery(query), resultSetType, resultSetConcurrency, resultSetHoldability);
            try {
                return SQLUtils.setParameters(ps, parameters).executeQuery();
            } finally {
                ps.clearParameters();
            }
        }
        
        @Override
        public QueryResult executeQuery() {
            return new QueryResult() {
                @Override
                public void init() throws SQLException {
                    rs = execQuery0();
                    dispose(rs, FailableAutoCloseables.of(rs::close));
                }

                @Override
                protected ResultSet getRS0() throws SQLException {
                    return rs;
                }

                private ResultSet rs = null;
            };
        }

        public int executeUpdate() throws SQLException {
            final PreparedStatement ps = psc.get(checkedQuery(query), resultSetType, resultSetConcurrency, resultSetHoldability);
            try {
                return SQLUtils.setParameters(ps, parameters).executeUpdate();
            } finally {
                ps.clearParameters();
            }
        }

        final private PreparedStatementContainer psc;
        
        //private Supplier<? extends CharSequence> query = null;
        private Object query = null;
        
        private ResultSetType resultSetType = null;

        private ResultSetConcurrency resultSetConcurrency = null;

        private ResultSetHoldability resultSetHoldability = null;
        
        private Seq<?> parameters;
    }

}
