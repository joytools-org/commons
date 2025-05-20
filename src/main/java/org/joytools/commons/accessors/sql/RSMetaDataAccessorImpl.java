/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.sql;

import static com.google.common.base.Preconditions.checkArgument;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;
import org.joytools.commons.accessors.IndexedMapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.newUnsupportedOperationException;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import static org.joytools.commons.lang.ObjectUtils.coalesce;
import org.joytools.commons.sql.ResultSetColumnMetaData;
import org.joytools.commons.sql.ResultSetColumnMetaDataBean;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
class RSMetaDataAccessorImpl extends NakedMultiValueAccessor<String, ResultSetColumnMetaData> implements ResultSetMetaDataAccessor {

    /**
     * 
     * @param md
     * @throws SQLException 
     */
    protected RSMetaDataAccessorImpl(final ResultSetMetaData md) throws SQLException {
        this.md = Objects.requireNonNull(md, "ResultSetMetaData");
        this.size = md.getColumnCount();
        this.indexes = IterableAccessors.ofAll(Vector.range(0, size));
        // Compute case sensitivity
        CaseSensitivity tempCS = CaseSensitivity.INSENSITIVE;
        for (int i = 1; tempCS.isIgnoreCase() && i <= size; i++) {
            if (md.isCaseSensitive(i)) {
                tempCS = CaseSensitivity.SENSITIVE;
            }
        }
        final io.vavr.collection.List<String> duplicates = tempCS.findDuplicates(indexes.map(i -> columnNameOrFail(i)));
        checkArgument(duplicates.isEmpty(), "Duplicated column names: %s", duplicates.mkString(", "));
        this.cs = tempCS;
    }
    
    /**
     * 
     * @param index
     * @return
     * @throws SQLException 
     */
    protected ResultSetColumnMetaData get0(final int index) throws SQLException {
        makeData();
        return metaDataArray[index];
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    protected String columnNameOrFail(final int i) {
        try {
            return md.getColumnName(i + 1);
        } catch (final SQLException ex) {
            throw newUnsupportedOperationException("columnName", i, ex);
        }
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    protected String columnNameOrNull(final int i) {
        try {
            return md.getColumnName(i + 1);
        } catch (final SQLException ex) {
            return null;
        }
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<String> keys() {
        makeData();
        return keys;
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<ResultSetColumnMetaData> values() {
        makeData();
        return values;
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<Tuple2<String, ResultSetColumnMetaData>> entriesByKey() {
        if (entriesByKey == null) {
            entriesByKey = IterableAccessors.ofAll(indexes().map(i -> Tuple(columnNameOrFail(i), metaDataArray[i])));
        }
        return entriesByKey;
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<Tuple2<Integer, ResultSetColumnMetaData>> entriesByIndex() {
        if (entriesByIndex == null) {
            entriesByIndex = IterableAccessors.ofAll(indexes().map(i -> Tuple(i, metaDataArray[i])));
        }
        return entriesByIndex;
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<Integer> indexes() {
        return indexes;
    }
 
    /**
     * 
     */
    private ResultSetColumnMetaData[] metaDataArray;

    /**
     * 
     */
    private java.util.Map<String, Integer> keyToIndexMap;
    
    /**
     * 
     */
    private IterableAccessor<ResultSetColumnMetaData> values;

    /**
     * 
     * @return 
     */
    protected void makeData() {
        if (metaDataArray == null) {
            metaDataArray = new ResultSetColumnMetaData[size];
            keyToIndexMap = caseSensitivity().newLinkedMap();
            for (int i = 0; i < size; i++) {
                final ResultSetColumnMetaDataBean bean = ResultSetColumnMetaDataBean.of(md, i + 1);
                try {
                    final String name = md.getColumnName(i + 1);
                    metaDataArray[i] = bean;
                    keyToIndexMap.put(name, i);
                } catch (final SQLException ex) {
                    metaDataArray = null;
                    keyToIndexMap = null;
                    keys = null;
                    throw new UnsupportedOperationException(ex);
                }
            }
            keys = IterableAccessors.ofAll(keyToIndexMap.keySet());
            values = IterableAccessors.of(metaDataArray);
        }
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public CaseSensitivity caseSensitivity() {
        /* if (cs == null) {
            try {
                CaseSensitivity temp = CaseSensitivity.INSENSITIVE;
                for (int i = 1; temp.isIgnoreCase() && i <= size; i++) {
                    if (md.isCaseSensitive(i)) {
                        temp = CaseSensitivity.SENSITIVE;
                    }
                }
                final io.vavr.collection.List<String> duplicates = temp.findDuplicates(indexes().map(i -> columnName(i)));
                checkArgument(duplicates.isEmpty(), "Duplicated column names: %s", duplicates.mkString(", "));
                cs = temp;
            } catch (final Exception ex) {
                throw newUnsupportedOperationException("caseSensitivity", null, ex);
            }
        } */
        return cs;
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public int indexOf(final String key) {
        makeData();
        return coalesce(keyToIndexMap.get(key), -1);
    }

    /**
     * 
     * @param key
     * @return 
     */    
    @Override
    public boolean contains(final String key) {
        return indexOf(key) >= 0;
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public boolean contains(final int index) {
        return index >= 0 && index < size();
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int size() {
        return size;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Option<ResultSetColumnMetaData> get(final String key) {
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
    @Override
    public Option<ResultSetColumnMetaData> get(final int index) {
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
    @Override
    public ResultSetColumnMetaData getOrFail(final String key) {
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
    @Override
    public ResultSetColumnMetaData getOrFail(final int index) {
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
    @Override
    public ResultSetColumnMetaData getOrElse(final String key, final ResultSetColumnMetaData defValue) {
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
    @Override
    public ResultSetColumnMetaData getOrElse(final int index, final ResultSetColumnMetaData defValue) {
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
    @Override
    public IndexedMapAccessor.Reader<String, ResultSetColumnMetaData> read() {
        return read0();
    }
    
    private IterableAccessor<String> keys;
    private final IterableAccessor<Integer> indexes;
    private final CaseSensitivity cs;
    final protected ResultSetMetaData md;
    final protected int size;
    private IterableAccessor<Tuple2<String, ResultSetColumnMetaData>> entriesByKey;
    private IterableAccessor<Tuple2<Integer, ResultSetColumnMetaData>> entriesByIndex;
    
}
