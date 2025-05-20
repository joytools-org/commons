/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.joytools.commons.collections.AbstractIndexedMap;
import org.joytools.commons.collections.AbstractIndexedMapEntry;
import org.joytools.commons.collections.IndexedMap;
import org.joytools.commons.collections.IndexedMapBuilder;
import org.joytools.commons.json.JsonUtils;

/**
 *
 * @author AndreaR
 */
@Deprecated
public class ResultSetFacade {

    /**
     * 
     * @param rs 
     */
    protected ResultSetFacade(final ResultSet rs) {
        this.rs = Objects.requireNonNull(rs, "ResultSet");
        this.unmodRS = ResultSetUtils.currentRow(rs);
    }
    
    /**
     * 
     * @param rs
     * @return 
     */
    public static ResultSetFacade of(final ResultSet rs) {
        return new ResultSetFacade(rs);
    }
    
    /**
     * 
     * @return 
     */
    protected ResultSet rs() {
        return rs;
    }
    
    /**
     * 
     * @return 
     */
    protected ResultSet resultSet() {
        return unmodRS;
    }

    /**
     * 
     * @throws SQLException 
     */ 
    @Deprecated
    public void old_close() throws SQLException {
        rs().close();
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public List<String> columnNames() throws SQLException {
        return metaData().columnNames();
    }
    
    /**
     * 
     * @param <M>
     * @param map
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public <M extends Map<String, Object>> M old_copyInto(final M map) throws SQLException {
        final List<String> colNames = columnNames();
        final int count = colNames.size();
        for (int index = 0; index < count; index++) {
            map.put(colNames.get(index), rs().getObject(index + 1));
        }
        return map;
    }
    
    /**
     * 
     * @param <L>
     * @param list
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public <L extends List<Object>> L old_copyInto(final L list) throws SQLException {
        final List<String> colNames = columnNames();
        final int count = colNames.size();
        while (list.size() < count) {
            list.add(null);
        }
        for (int index = 0; index < count; index++) {
            list.set(index, rs().getObject(index + 1));
        }
        return list;
    }
    
    /**
     * 
     * @param array
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public Object[] old_copyInto(final Object[] array) throws SQLException {
        final int count = columnCount();
        for (int index = 0; index < count; index++) {
            array[index] = rs().getObject(index + 1);
        }
        return array;
    }

    /**
     * 
     * @return 
     * @throws java.sql.SQLException 
     */
    @Deprecated
    public JsonObject old_toJsonObject() throws SQLException {
        final List<String> colNames = columnNames();
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        final int count = colNames.size();
        for (int index = 0; index < count; index++) {
            JsonUtils.add(builder, colNames.get(index), rs().getObject(index + 1));
        }
        return builder.build();
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public IndexedMap<String, Object> old_toIndexexMap() throws SQLException {
        final IndexedMap<String, Object> m = IndexedMapBuilder.create();
        return old_copyInto(m);
    }
    
    /**
     *
     * @return 
     * @throws SQLException
     */
    @Deprecated
    public ResultSetFacade old_cancelRowUpdates() throws SQLException {
        rs().cancelRowUpdates();
        return this;
    }

    /**
     *
     * @param index
     * @return
     * @throws SQLException
     */
    protected int checkIndex(final int index) throws SQLException {
        if (index < 0 || index >= columnCount()) {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
        return index;
    }

    /**
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public ResultSetColumnDataChecked columnData(final String name) throws SQLException {
        return columns().get(columnIndex(name));
    }

    /**
     *
     * @param index
     * @return
     * @throws SQLException
     */
    public ResultSetColumnDataChecked columnData(final int index) throws SQLException {
        return columns().get(index);
    }

    /**
     * 
     * @param name
     * @return
     * @throws SQLException 
     */
    public ResultSetColumnMetaData columnMetaData(final String name) throws SQLException {
        return metaData().column(name);
    }

    /**
     *
     * @param index
     * @return
     * @throws SQLException
     */
    public ResultSetColumnMetaData columnMetaData(final int index) throws SQLException {
        return metaData().column(index);
    }

    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    public int columnCount() throws SQLException {
        if (size < 0) {
            size = metaData().columnCount();
        }
        return size;
    }

    /**
     *
     * @param name
     * @return
     * @throws SQLException
     */
    public int columnIndex(final String name) throws SQLException {
        return rs().findColumn(name) - 1;
    }

    /**
     *
     * @return
     * @throws SQLException
     */
    public List<ResultSetColumnDataChecked> columns() throws SQLException {
        final int size = columnCount();
        final ResultSet thisRS = rs();
        if (columnData == null) {
            final ResultSetColumnDataChecked[] columnDataArray = new ResultSetColumnDataChecked[size];
            for (int i = 0; i < size; i++) {
                final int index = i + 1;
                columnDataArray[i] = new AbstractResultSetColumnDataChecked() {
                    @Override
                    protected ResultSet rs() {
                        return thisRS;
                    }

                    @Override
                    protected int index() {
                        return index;
                    }

                    @Override
                    public ResultSetColumnMetaData getMetaData() throws SQLException {
                        return metaData().column(index);
                    }
                };
            }
            columnData = new AbstractList<ResultSetColumnDataChecked>() {
                @Override
                public ResultSetColumnDataChecked get(final int index) {
                    return columnDataArray[index];
                }

                @Override
                public int size() {
                    return size;
                }
            };
        }
        return columnData;
    }

    /**
     *
     * @param name
     * @return
     */
    public int indexOf(final String name) {
        try {
            return rs().findColumn(name) - 1;
        } catch (final SQLException e) {
            return -1;
        }
    }

    /**
     * 
     * @return 
     */
    @Deprecated
    public boolean old_isScrollable() {
        return false;
    }
    
    /**
     *
     * @return
     */
    public List<?> listView() {
        if (resultSetAsList == null) {
            resultSetAsList = new AbstractList<Object>() {
                @Override
                public Object set(final int index, final Object value) {
                    return uncheckedSet(index, value);
                }

                @Override
                public Object get(final int index) {
                    return uncheckedGet(index);
                }

                @Override
                public int size() {
                    return uncheckedSize();
                }
            };
        }
        return resultSetAsList;
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public IndexedMap<String, ?> old_toMap() throws SQLException {
        return old_copyInto(IndexedMapBuilder.create());
    }
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public List<?> old_toList() throws SQLException {
        return old_copyInto(new ArrayList());
    }

    /**
     *
     * @return
     */
    public IndexedMap<String, ?> mapView() {
        if (resultSetAsMap == null) {
            // resultSetAsMap_map = new HashMap();
            resultSetAsMapEntries = new ArrayList();
            for (int i = 0; i < uncheckedSize(); i++) {
                final int index0 = i;
                final int index1 = i + 1;
                try {
                    final String name = metaData().list().get(index0).getName();
                    final IndexedMap.Entry<String, Object> e = new AbstractIndexedMapEntry<String, Object>() {
                        @Override
                        public int getIndex() {
                            return index0;
                        }

                        @Override
                        public String getKey() {
                            return name;
                        }

                        @Override
                        public Object getValue() {
                            return uncheckedGet(index0);
                        }

                        @Override
                        public Object setValue(final Object value) {
                            uncheckedSet(index0, value);
                            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                        }
                    };
                    // resultSetAsMap_map.put(name, e);
                    resultSetAsMapEntries.add(e);
                } catch (final SQLException e) {
                    throw new UncheckedSQLException(e);
                }
            }
            resultSetAsMap = new AbstractIndexedMap<String, Object>() {
                @Override
                public int size() {
                    return uncheckedSize();
                }

                @Override
                protected void storeEntry(final IndexedMap.Entry<String, Object> entry) {
                    throw new UnsupportedOperationException("Not supported");
                }

                @Override
                protected IndexedMap.Entry<String, Object> findEntry(final int index) {
                    return resultSetAsMapEntries.get(index);
                }

                @Override
                protected IndexedMap.Entry<String, Object> findEntry(final Object key) {
                    final int index = ResultSetFacade.this.indexOf(key.toString());
                    if (index < 0) {
                        return null;
                    }
                    return resultSetAsMapEntries.get(index);
                    // return resultSetAsMap_map.get(key);
                }
            };
        }
        return resultSetAsMap;
    }

    /**
     *
     * @return
     * @throws java.sql.SQLException
     */
    public ResultSetMetaDataFacade metaData() throws SQLException {
        if (metaDataFacade == null) {
            metaDataFacade = ResultSetMetaDataFacade.of(rs());
        }
        return metaDataFacade;
    }

    /**
     *
     * @param index
     * @return
     */
    protected Object uncheckedGet(final int index) {
        try {
            return rs().getObject(index + 1);
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    /**
     *
     */
    // private List<String> stringList = null;
    /**
     *
     * @param <T>
     * @param index
     * @param clazz
     * @return
     */
    protected <T> T uncheckedGetObject(final int index, final Class<T> clazz) {
        try {
            return clazz.cast(rs().getObject(index + 1));
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    /**
     *
     * @param index
     * @param value
     * @return
     */
    protected Object uncheckedSet(final int index, final Object value) {
        try {
            final Object prev = rs().getObject(index + 1);
            rs().updateObject(index + 1, value);
            return prev;
        } catch (final SQLException ex) {
            throw new UncheckedSQLException(ex);
        }
    }

    /**
     *
     * @return
     */
    protected int uncheckedSize() {
        try {
            return columnCount();
        } catch (final SQLException e) {
            throw new UncheckedSQLException(e);
        }
    }

    /**
     *
     * @return 
     * @throws java.sql.SQLException
     */
    @Deprecated
    public ResultSetFacade old_updateRow() throws SQLException {
        rs().updateRow();
        return this;
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    @Deprecated
    public ResultSetFacade old_refreshRow() throws SQLException {
        rs().refreshRow();
        return this;
    }

    /**
     * 
     */
    private final ResultSet rs;

    /**
     * 
     */
    private final ResultSet unmodRS;

    /**
     *
     */
    private List<ResultSetColumnDataChecked> columnData = null;
    
    /**
     *
     */
    private ResultSetMetaDataFacade metaDataFacade = null;
    
    /**
     *
     */
    private List<Object> resultSetAsList = null;
    
    /**
     *
     */
    private IndexedMap<String, Object> resultSetAsMap = null;
    
    /**
     *
     */
    private List<IndexedMap.Entry<String, Object>> resultSetAsMapEntries = null;
    
    private int size = -1;
    
}
