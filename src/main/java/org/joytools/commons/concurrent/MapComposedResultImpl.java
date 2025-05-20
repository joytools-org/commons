/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.LinkedHashMap;
import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import io.vavr.collection.Set;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import org.joytools.commons.collections.IterableAccessors;
import static org.joytools.commons.function.Functions.getLeft;
import static org.joytools.commons.function.Functions.getRight;
import static org.joytools.commons.function.Predicates.isLeft;
import static org.joytools.commons.function.Predicates.isRight;
import org.joytools.commons.interop.Vavr;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
final class MapComposedResultImpl<K, V> extends AbstractComposedResultImpl<K, V> implements MapComposedResult<K, V>  {

    protected MapComposedResultImpl() {
        m_entriesMap = LinkedHashMap.empty();
    }
    
    protected MapComposedResultImpl(final LinkedHashMap<K, Either<Exception, V>> other) {
        m_entriesMap = Objects.requireNonNull(other, "Other");
    }

    @Override
    public String toString() {
        return m_entriesMap.toString();
    }
    
    @Override
    public int hashCode() {
        return m_entriesMap.hashCode();
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
        final MapComposedResultImpl<?, ?> other = (MapComposedResultImpl<?, ?>) obj;
        return Objects.equals(this.m_entriesMap, other.m_entriesMap);
    }

    /* protected MapComposedResultImpl(final MapComposedResultImpl<K, V> other) {
        this.m_entriesSeq = other.m_entriesSeq;
        this.m_entriesMap = other.m_entriesMap;
    } */

    private final LinkedHashMap<K, Either<Exception, V>> m_entriesMap;

    private Vector<Either<Exception, V>> m_entriesSeq;

    private Vector<V> m_succeededSeq;

    private Vector<Exception> m_failedSeq;

    @Override
    public Seq<Either<Exception, V>> entriesSeq() {
        synchronized(this) {
            if (m_entriesSeq == null) {
                m_entriesSeq = Vector.ofAll(m_entriesMap.values());
            }
        }
        return m_entriesSeq;
    }

    @Override
    public Map<K, Either<Exception, V>> entriesMap() {
        return m_entriesMap;
    }

    @Override
    public int size() {
        return m_entriesMap.size();
    }

    @Override
    public Seq<K> keysSeq() {
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
                m_succeededSeq = Vector.ofAll(m_entriesMap.values().filter(isRight()).map(getRight()));
            }
        }
        return m_succeededSeq;
    }

    @Override
    public Seq<Exception> failedSeq() {
        synchronized(this) {
            if (m_failedSeq == null) {
                m_failedSeq = Vector.ofAll(m_entriesMap.values().filter(isLeft()).map(getLeft()));
            }
        }
        return m_failedSeq;
    }

    @Override
    public MapComposedResult<K, V> append(final K key, final Either<Exception, V> value) {
        Objects.requireNonNull(key, "Key");
        Objects.requireNonNull(value, "Value");
        if (m_entriesMap.containsKey(key)) {
            throw new IllegalArgumentException("Duplicated key: " + key);
        }
        final MapComposedResultImpl<K, V> result = new MapComposedResultImpl<>(m_entriesMap.put(key, value));
        return result;
    }

    @Override
    public MapComposedResult<K, V> appendAll(final Iterable<Tuple2<K, Either<Exception, V>>> values) {
        Objects.requireNonNull(values, "Values");
        final Set<K> newKeys = IterableAccessors.from(values).map(t -> t._1()).to(Vavr.hashSet());
        final Set<K> dupKeys = m_entriesMap.keySet().filter(k -> newKeys.contains(k));
        if (!dupKeys.isEmpty()) {
            throw new IllegalArgumentException("Duplicated keys: " + dupKeys.mkString(", "));
        }
        final LinkedHashMap<K, Either<Exception, V>> newMap = LinkedHashMap.ofEntries(values);
        final MapComposedResultImpl<K, V> result = new MapComposedResultImpl<>(m_entriesMap.merge(newMap));
        return result;
    }

    @Override
    public MapComposedResult<K, V> appendAll(final java.util.Map<K, Either<Exception, V>> values) {
        Objects.requireNonNull(values, "Values");
        final Set<K> newKeys = IterableAccessors.from(values.keySet()).to(Vavr.hashSet());
        final Set<K> dupKeys = m_entriesMap.keySet().filter(k -> newKeys.contains(k));
        if (!dupKeys.isEmpty()) {
            throw new IllegalArgumentException("Duplicated keys: " + dupKeys.mkString(", "));
        }
        final LinkedHashMap<K, Either<Exception, V>> newMap = LinkedHashMap.ofAll(values);
        final MapComposedResultImpl<K, V> result = new MapComposedResultImpl<>(m_entriesMap.merge(newMap));
        return result;
    }

    @Override
    public Map<K, V> valuesMap() {
        super.checkNoFailures();
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<K, V> succeededMap() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Map<K, Exception> failedMap() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * 
     * @param condition
     * @throws Exception 
     */
    @Override
    public void throwIf(final Predicate<? super MapComposedResult<K, V>> condition) throws Exception {
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
    public void throwIf(final Predicate<? super MapComposedResult<K, V>> condition,
            final Function<? super MapComposedResult<K, V>, ? extends Exception> mapper) throws Exception {
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
    public MapComposedResult<K, V> asMap() {
        return this;
    }

}
