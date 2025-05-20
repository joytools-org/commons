/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import org.joytools.commons.collections.IndexedMap;
import org.joytools.commons.collections.IndexedMapBuilder;
import org.joytools.commons.json.JsonUtils;

/**
 *
 * @author AndreaR
 */
@Deprecated
class CurrentRowReaderV1 {

    /**
     * 
     * @param rs 
     */
    private CurrentRowReaderV1(final ResultSet rs) {
        this.rs = rs;
    }
    
    /**
     * 
     * @param rs
     * @return 
     */
    public static CurrentRowReaderV1 of(final ResultSet rs) {
        return new CurrentRowReaderV1(rs);
    }
    
    /**
     * 
     * @return 
     * @throws java.sql.SQLException 
     */
    protected CurrentRowReaderV1 lazyInit() throws SQLException {
        if (columnNames != null) {
            return this;
        }
        final ResultSetMetaData md = rs.getMetaData();
        final int count = md.getColumnCount();
        columnNames = new ArrayList(count);
        for (int index = 0; index < count; index++) {
            columnNames.add(md.getColumnName(index + 1));
        }
        columnNames = Collections.unmodifiableList(columnNames);
        return this;
    }
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public List<String> columnNames() throws SQLException {
        lazyInit();
        return columnNames;
    }
    
    /**
     * 
     * @param <M>
     * @param map
     * @return
     * @throws SQLException 
     */
    public <M extends Map<String, Object>> M copyInto(final M map) throws SQLException {
        lazyInit();
        final int count = columnNames.size();
        for (int index = 0; index < count; index++) {
            map.put(columnNames.get(index), rs.getObject(index + 1));
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
    public <L extends List<Object>> L copyInto(final L list) throws SQLException {
        lazyInit();
        final int count = columnNames.size();
        while (list.size() < count) {
            list.add(null);
        }
        for (int index = 0; index < count; index++) {
            list.set(index, rs.getObject(index + 1));
        }
        return list;
    }
    
    /**
     * 
     * @param array
     * @return
     * @throws SQLException 
     */
    public Object[] copyInto(final Object[] array) throws SQLException {
        lazyInit();
        final int count = columnNames.size();
        for (int index = 0; index < count; index++) {
            array[index] = rs.getObject(index + 1);
        }
        return array;
    }

    /**
     * 
     * @return 
     */
    public JsonObject toJsonObject() throws SQLException {
        lazyInit();
        final JsonObjectBuilder builder = Json.createObjectBuilder();
        final int count = columnNames.size();
        for (int index = 0; index < count; index++) {
            JsonUtils.add(builder, columnNames.get(index), rs.getObject(index + 1));
        }
        return builder.build();
    }

    /**
     * 
     * @return
     * @throws SQLException 
     */
    public IndexedMap<String, Object> toIndexexMap() throws SQLException {
        lazyInit();
        final IndexedMap<String, Object> m = IndexedMapBuilder.create();
        return copyInto(m);
    }

    /**
     * 
     */
    private final ResultSet rs;
    
    /**
     * 
     */
    private List<String> columnNames = null;
    
}
