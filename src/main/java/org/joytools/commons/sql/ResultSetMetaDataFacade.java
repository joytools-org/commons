/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.joytools.commons.collections.AbstractImmutableIndexedMapEntry;
import org.joytools.commons.collections.AbstractIndexedMap;
import org.joytools.commons.collections.IndexedMap;

/**
 *
 * @author AndreaR
 */
public class ResultSetMetaDataFacade implements Iterable<ResultSetColumnMetaData> {

    /**
     * 
     * @param md 
     */
    protected ResultSetMetaDataFacade(
            final ResultSetMetaData md)  {
        this.md = md;
    }
    
    // private final ResultSet rs;
    
    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static ResultSetMetaDataFacade of(final ResultSet rs) throws SQLException {
        Objects.requireNonNull(rs, "ResultSet");
        return new ResultSetMetaDataFacade(rs.getMetaData());
    }

    /**
     * 
     * @param md
     * @return
     * @throws SQLException 
     */
    public static ResultSetMetaDataFacade of(final ResultSetMetaData md) throws SQLException {
        Objects.requireNonNull(md, "ResultSetMetaData");
        return new ResultSetMetaDataFacade(md);
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public int columnCount() throws SQLException {
        return md.getColumnCount();
    }
    
    /**
     * 
     * @return 
     */
    protected int uncheckedColumnCount() {
        try {
            return md.getColumnCount();
        } catch (final SQLException e) {
            throw new UncheckedSQLException(e);
        }
    }

    @Override
    public Iterator<ResultSetColumnMetaData> iterator() {
        return list().listIterator();
    }

    /**
     * 
     * @return 
     */
    public List<ResultSetColumnMetaData> list() {
        if (metaDataAsList == null) {
            metaDataAsList = new AbstractList<ResultSetColumnMetaData>() {
                @Override
                public ResultSetColumnMetaData get(final int index) {
                    try {
                        return buildColumnMetaData(index);
                    } catch (final SQLException ex) {
                        throw new UncheckedSQLException(ex);
                    }
                }

                @Override
                public int size() {
                    try {
                        return md.getColumnCount();
                    } catch (final SQLException ex) {
                        throw new UncheckedSQLException(ex);
                    }
                }
            };
        }
        return metaDataAsList;
    }
    
    /**
     * 
     * @return 
     */
    public IndexedMap<String, ResultSetColumnMetaData> map() {
        if (metaDataAsMap == null) {
            // metaDataAsMap_map = new HashMap();
            metaDataAsMapEntries = new ArrayList();
            for (int i = 0; i < uncheckedColumnCount(); i++) {
                final int index0 = i;
                final int index1 = i + 1;
                try {
                    final String name =  md.getColumnName(index1);
                    final IndexedMap.Entry<String, ResultSetColumnMetaData> e = new AbstractImmutableIndexedMapEntry<String, ResultSetColumnMetaData>() {
                        @Override
                        public int getIndex() {
                            return index0;
                        }

                        @Override
                        public String getKey() {
                            return name;
                        }

                        @Override
                        public ResultSetColumnMetaData getValue() {
                            return uncheckedColumn(index0);
                        }
                    };
                    // metaDataAsMap_map.put(name, e);
                    metaDataAsMapEntries.add(e);
                } catch (final SQLException e) {
                    throw new UncheckedSQLException(e);
                }
            }
            metaDataAsMap = new AbstractIndexedMap<String, ResultSetColumnMetaData>() {
                @Override
                public int size() {
                    return uncheckedColumnCount();
                }

                @Override
                protected void storeEntry(final IndexedMap.Entry<String, ResultSetColumnMetaData> entry) {
                    throw new UnsupportedOperationException("Not supported");
                }

                @Override
                protected IndexedMap.Entry<String, ResultSetColumnMetaData> findEntry(final int index) {
                    return metaDataAsMapEntries.get(index);
                }

                @Override
                protected IndexedMap.Entry<String, ResultSetColumnMetaData> findEntry(final Object key) {
                    final int index = ResultSetMetaDataFacade.this.indexOf(key.toString());
                    if (index < 0) {
                        return null;
                    }
                    return metaDataAsMapEntries.get(index);
                    // return metaDataAsMap_map.get(key);
                }
            };
        }
        return metaDataAsMap;
    }

    private final ResultSetMetaData md;
    
    // private Map<String, Integer> name2Index = null;

    private Map<String, Integer> nameUpper2Index = null;
    
    /**
     * 
     * @param name
     * @return 
     */
    public int indexOf(final String name) {
        try {
            return columnIndex(name);
        } catch (final SQLException e) {
            return -1;
        }
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    public int columnIndex(final String name) throws SQLException {
        // return rs.findColumn(name) - 1;
        if (nameUpper2Index == null) {
            final Map<String, Integer> temp = new HashMap();
            for (int i = 1; i <= md.getColumnCount(); i++) {
                temp.put(md.getColumnName(i).toUpperCase(), i - 1);
            }
            nameUpper2Index = temp;
        }
        final Integer index = nameUpper2Index.get(name.toUpperCase());
        if (index == null) {
            throw new SQLException("Column " + name + " not found");
        }
        return index;
    }
    
    /**
     * 
     * @param index
     * @return 
     * @throws java.sql.SQLException 
     */
    public ResultSetColumnMetaData column(final int index) throws SQLException {
        return buildColumnMetaData(index);
    }

    /**
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    public ResultSetColumnMetaData column(final String name) throws SQLException {
        return column(columnIndex(name));
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    protected ResultSetColumnMetaData uncheckedColumn(final int index) {
        try {
            return column(index);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    /**
     * 
     * @param name
     * @return 
     */
    protected ResultSetColumnMetaData uncheckedColumn(final String name) {
        try {
            return column(name);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public List<String> columnNames() throws SQLException {
        if (colNames == null) {
            final int colCount = md.getColumnCount();
            final String[] names = new String[colCount];
            for (int col = 1; col <= colCount; col++) {
                names[col - 1] = md.getColumnName(col);
            }
            colNames = new AbstractList<String>() {
                @Override
                public String get(final int index) {
                    return names[index];
                }

                @Override
                public int size() {
                    return colCount;
                }
            };
        }
        return colNames;
    }
    
    private List<String> colNames = null;
    
    /**
     * 
     * @param i
     * @return
     * @throws SQLException 
     */
    protected ResultSetColumnMetaData buildColumnMetaData(final int i) throws SQLException {
        checkIndex(i);
        final int index = i + 1;
        while (index > built.size()) {
            built.add(null);
        }
        ResultSetColumnMetaData result = built.get(i);
        if (result == null) {
            result = new ResultSetColumnMetaDataBean(md, index);
            built.set(i, result);
        }
        return result;
    }
    
    /**
     * 
     */
    private List<ResultSetColumnMetaData> metaDataAsList = null;
    
    private IndexedMap<String, ResultSetColumnMetaData> metaDataAsMap = null;
    
    // private Map<String, IndexedMap.Entry<String, ResultSetColumnMetaData>> metaDataAsMap_map;

    private List<IndexedMap.Entry<String, ResultSetColumnMetaData>> metaDataAsMapEntries;

    private final List<ResultSetColumnMetaData> built = new ArrayList();
    
    /**
     * 
     * @param index
     * @return
     * @throws SQLException 
     */
    protected int checkIndex(final int index) throws SQLException {
        if (index < 0 || index >= md.getColumnCount()) {
            throw new IndexOutOfBoundsException();
        }
        return index;
    }
    
}
