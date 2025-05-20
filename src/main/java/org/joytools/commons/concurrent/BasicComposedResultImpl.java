/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import io.vavr.Tuple2;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import static org.joytools.commons.function.Functions.getLeft;
import static org.joytools.commons.function.Functions.getRight;
import static org.joytools.commons.function.Predicates.isLeft;
import static org.joytools.commons.function.Predicates.isRight;

/**
 *
 * @author AndreaR
 * @param <V>
 */
final class BasicComposedResultImpl<V> extends AbstractComposedResultImpl<Integer, V> implements BasicComposedResult<V>  {

    protected BasicComposedResultImpl() {
        m_entriesSeq = Vector.empty();
    }
    
    protected BasicComposedResultImpl(final Vector<Either<Exception, V>> other) {
        m_entriesSeq = Objects.requireNonNull(other, "Other");
    }

    @Override
    public String toString() {
        return m_entriesSeq.toString();
    }
    
    @Override
    public int hashCode() {
        return m_entriesSeq.hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BasicComposedResultImpl<?> other = (BasicComposedResultImpl<?>) obj;
        return Objects.equals(this.m_entriesSeq, other.m_entriesSeq);
    }

    /* protected BasicComposedResultImpl(final BasicComposedResultImpl<V> other) {
        this.m_entriesSeq = other.m_entriesSeq;
        this.m_entriesMap = other.m_entriesMap;
    } */

    private final Vector<Either<Exception, V>> m_entriesSeq;
    
    private Map<Integer, Either<Exception, V>> m_entriesMap;

    private Vector<V> m_succeededSeq;

    private Vector<Exception> m_failedSeq;

    @Override
    public Seq<Either<Exception, V>> entriesSeq() {
        return m_entriesSeq;
    }

    @Override
    public Map<Integer, Either<Exception, V>> entriesMap() {
        synchronized(this) {
            if (m_entriesMap == null) {
                final Seq<Tuple2<Integer, Either<Exception, V>>> entries = m_entriesSeq.zipWithIndex().map(t -> t.swap());
                m_entriesMap = LinkedHashMap.ofEntries(entries);
            }
        }
        return m_entriesMap;
    }

    @Override
    public int size() {
        return m_entriesSeq.size();
    }

    @Override
    public Seq<Integer> keysSeq() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Seq<V> valuesSeq() {
        checkNoFailures();
        return succeededSeq();
    }

    @Override
    public Seq<V> succeededSeq() {
        synchronized(this) {
            if (m_succeededSeq == null) {
                m_succeededSeq = m_entriesSeq.filter(isRight()).map(getRight());
            }
        }
        return m_succeededSeq;
    }

    @Override
    public Seq<Exception> failedSeq() {
        synchronized(this) {
            if (m_failedSeq == null) {
                m_failedSeq = m_entriesSeq.filter(isLeft()).map(getLeft());
            }
        }
        return m_failedSeq;
    }

    @Override
    public BasicComposedResult<V> append(final Either<Exception, V> value) {
        Objects.requireNonNull(value, "Value");
        final BasicComposedResultImpl<V> result = new BasicComposedResultImpl<>(m_entriesSeq.append(value));
        return result;
    }

    @Override
    public BasicComposedResult<V> appendAll(final Iterable<Either<Exception, V>> values) {
        Objects.requireNonNull(values, "Values");
        final BasicComposedResultImpl<V> result = new BasicComposedResultImpl<>(m_entriesSeq.appendAll(values));
        return result;
    }

    @Override
    public Map<Integer, V> valuesMap() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<Integer, V> succeededMap() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<Integer, Exception> failedMap() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    /**
     * 
     * @param condition
     * @throws Exception 
     */
    @Override
    public void throwIf(final Predicate<? super BasicComposedResult<V>> condition) throws Exception {
        Objects.requireNonNull(condition, "Condition");
        if (condition.test(this)) {
            throwExceptions();
        }
    }

    /**
     * 
     * @param condition
     * @param mapper
     * @throws Exception 
     */
    @Override
    public void throwIf(final Predicate<? super BasicComposedResult<V>> condition,
            final Function<? super BasicComposedResult<V>, ? extends Exception> mapper) throws Exception {
        Objects.requireNonNull(condition, "Condition");
        if (!condition.test(this)) {
            return;
        }
        if (mapper != null) {
            final Exception ex = mapper.apply(this);
            if (ex != null) {
                throw ex;
            }
        } 
        throwExceptions();
    }

    @Override
    public SeqComposedResult<V> asSeq() {
        return ComposedResults.ofSeq(entriesSeq());
    }
    

    @Override
    public MapComposedResult<Integer, V> asMap() {
        return ComposedResults.ofMap(entriesMap());
    }
    
}
