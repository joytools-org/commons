/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.sql;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.control.Option;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;
import org.joytools.commons.accessors.IndexedMapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newUnsupportedOperationException;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
abstract class NakedRSGenericMutator<V> extends NakedMultiValueAccessor<String, V> {

    /**
     * 
     * @param rs 
     */
    protected NakedRSGenericMutator(final ResultSet rs) throws SQLException {
        this.rs = Objects.requireNonNull(rs, "ResultSet");
        this.md = new RSMetaDataAccessorImpl(rs.getMetaData());
    }
    
    /**
     * 
     * @return 
     */
    public boolean isUpdatable() {
        return true;
    }
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<Integer> indexes() {
        return md.indexes();
    }
    
    /**
     * 
     * @return 
     */
    public CaseSensitivity caseSensitivity() {
        return md.caseSensitivity();
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    abstract protected V get0(final int index) throws SQLException;

    /**
     * 
     * @param index
     * @return 
     */
    protected V get0Unchecked(final int index) {
        try {
            return get0(index);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("getOrFail", index, ex);
        }
    }

    /**
     * 
     * @param index
     * @param value
     * @throws SQLException 
     */
    abstract protected void set0(final int index, final V value) throws SQLException;

    /**
     * 
     * @param key
     * @return 
     */
    protected int indexOf(final String key) {
        return md.indexOf(key);
    }

    /**
     * 
     * @param key
     * @return 
     */    
    public boolean contains(final String key) {
        return indexOf(key) >= 0;
    }

    /**
     * 
     * @param index
     * @return 
     */
    public boolean contains(final int index) {
        return index >= 0 && index < size();
    }
    
    /**
     * 
     * @return 
     */
    public int size() {
        return md.size();
    }
    
    /**
     * 
     * @return 
     */
    public boolean isEmpty() {
        return md.isEmpty();
    }

    /**
     * 
     * @param key
     * @return 
     */
    public Option<V> get(final String key) {
        final int index = indexOf(key);
        if (index < 0) {
            return None();
        }
        try {
            return Some(get0(index));
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("get", key, ex);
        }
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    public Option<V> get(final int index) {
        if (!contains(index)) {
            return None();
        }
        try {
            return Some(get0(index));
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("get", index, ex);
        }
    }

    /**
     * 
     * @param key
     * @return 
     */
    public V getOrFail(final String key) {
        final int index = indexOf(key);
        if (index < 0) {
            throw newItemNotFoundException("getOrFail", key);
        }
        try {
            return get0(index);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("getOrFail", key, ex);
        }
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    public V getOrFail(final int index) {
        if (!contains(index)) {
            throw newItemNotFoundException("getOrFail", index);
        }
        try {
            return get0(index);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("getOrFail", index, ex);
        }
    }

    /**
     * 
     * @param key
     * @param defValue
     * @return 
     */
    public V getOrElse(final String key, final V defValue) {
        final int index = indexOf(key);
        if (index < 0) {
            return defValue;
        }
        try {
            return get0(index);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("getOrElse", key, ex);
        }
    }
    
    /**
     * 
     * @param index
     * @param defValue
     * @return 
     */
    public V getOrElse(final int index, final V defValue) {
        if (!contains(index)) {
            return defValue;
        }
        try {
            return get0(index);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("getOrElse", index, ex);
        }
    }
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<String> keys() {
        return md.keys();
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<Tuple2<String, V>> entriesByKey() {
        return indexes().map(i -> Tuple(md.columnNameOrFail(i), get0Unchecked(i)));
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<V> values() {
        return indexes().map(i -> get0Unchecked(i));
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<Tuple2<Integer, V>> entriesByIndex() {
        return indexes().map(i -> Tuple(i, get0Unchecked(i)));
    }

    /**
     * 
     * @param <U>
     * @param key
     * @return 
     */
    public <U extends V> U set(final String key, final U value) {
        final int index = md.indexOf(key);
        if (index < 0) {
            throw newItemNotFoundException("set", key);
        }
        try {
            set0(index, value);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("set", index, ex);
        }
        return value;
    }

    /**
     * 
     * @param <U>
     * @param index
     * @return 
     */
    public <U extends V> U set(final int index, final U value) {
        if (!contains(index)) {
            throw newItemNotFoundException("set", index);
        }
        try {
            set0(index, value);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("set", index, ex);
        }
        return value;
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param value
     * @return 
     */
    public <U extends V> boolean update(final String key, final U value) {
        final int index = md.indexOf(key);
        if (index < 0) {
            return false;
        }
        try {
            set0(index, value);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("set", index, ex);
        }
        return true;
    }

    /**
     * 
     * @param <U>
     * @param index
     * @return 
     */
    public <U extends V> boolean update(final int index, final U value) {
        if (!contains(index)) {
            return false;
        }
        try {
            set0(index, value);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("set", index, ex);
        }
        return true;
    }

    /**
     * 
     * @return 
     */
    public ResultSetMetaDataAccessor metaData() {
        return md;
    }
    
    /**
     * 
     * @return 
     */
    public IndexedMapAccessor.Reader<String, V> read() {
        return read0();
    }
    
    final protected ResultSet rs;
    
    final protected RSMetaDataAccessorImpl md;
    // final protected ResultSetMetaData md1;
    
}
