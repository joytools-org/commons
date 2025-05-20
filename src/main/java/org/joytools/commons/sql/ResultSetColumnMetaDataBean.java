/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Objects;
import org.joytools.commons.beans.ArrayBean;

/**
 *
 * @author AndreaR
 */
public class ResultSetColumnMetaDataBean extends ArrayBean implements ResultSetColumnMetaData {

    private final int index;
    
    private final ResultSetMetaData md;
    
    private UncheckedSQLException pending = null;
    
    private final int baseIndex;

    /**
     * 
     * @param md
     * @param index 
     */
    ResultSetColumnMetaDataBean(final ResultSetMetaData md, final int index) {
        this.md = Objects.requireNonNull(md, "ResultSetMetaData");
        this.index = index;
        this.baseIndex = super.size();
    }
    
    /**
     * 
     * @param md
     * @param index
     * @return 
     */
    public static ResultSetColumnMetaDataBean of(final ResultSetMetaData md, final int index) {
        return new ResultSetColumnMetaDataBean(md, index);
    }
    
    /**
     * 
     * @param values 
     */
    @Override
    protected void initValues(final Object[] values) {
        super.initValues(values);
        try {
            values[baseIndex +  0] = index;   
            values[baseIndex +  1] = md.getCatalogName(index);   
            values[baseIndex +  2] = md.getColumnClassName(index);   
            values[baseIndex +  3] = md.getColumnDisplaySize(index);   
            values[baseIndex +  4] = md.getColumnLabel(index);
            values[baseIndex +  5] = md.getColumnName(index);
            values[baseIndex +  6] = Type.of(md.getColumnType(index));
            values[baseIndex +  7] = md.getColumnTypeName(index);
            values[baseIndex +  8] = md.getPrecision(index);
            values[baseIndex +  9] = md.getScale(index);
            values[baseIndex + 10] = md.getSchemaName(index);
            values[baseIndex + 11] = md.getTableName(index);
            values[baseIndex + 12] = md.isAutoIncrement(index);
            values[baseIndex + 13] = md.isCaseSensitive(index);
            values[baseIndex + 14] = md.isCurrency(index);
            values[baseIndex + 15] = md.isDefinitelyWritable(index);
            values[baseIndex + 16] = ColumnNullability.of(md.isNullable(index));
            values[baseIndex + 17] = md.isReadOnly(index);
            values[baseIndex + 18] = md.isSearchable(index);
            values[baseIndex + 19] = md.isSigned(index);
            values[baseIndex + 20] = md.isWritable(index);
        } catch (final SQLException e) {
            pending = new UncheckedSQLException(e);
            throw pending;
        }
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int getIndex() {
        return getInteger(baseIndex + 0);
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getCatalogName() {
        return getString(baseIndex + 1);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getClassName() {
        return getString(baseIndex + 2);
    }

    /**
     * 
     * @return 
     */
    @Override
    public int getDisplaySize() {
        return getInteger(baseIndex + 3);
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getLabel() {
        return getString(baseIndex + 4);
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getName() {
        return getString(baseIndex + 5);
    }

    /**
     * 
     * @return 
     */
    @Override
    public Type getType() {
        return get(baseIndex + 6, Type.class);
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getTypeName() {
        return getString(baseIndex + 7);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int getPrecision() {
        return getInteger(baseIndex + 8);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int getScale() {
        return getInteger(baseIndex + 9);
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getSchemaName() {
        return getString(baseIndex + 10);
    }

    /**
     * 
     * @return 
     */
    @Override
    public String getTableName() {
        return getString(baseIndex + 11);
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isAutoIncrement() {
        return getBoolean(baseIndex + 12);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean isCaseSensitive() {
        return getBoolean(baseIndex + 13);
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isCurrency() {
        return getBoolean(baseIndex + 14);
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isDefinitelyWritable() {
        return getBoolean(baseIndex + 15);
    }

    /**
     * 
     * @return 
     */
    @Override
    public ColumnNullability isNullable() {
        return get(baseIndex + 16, ColumnNullability.class);
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isReadOnly() {
        return getBoolean(baseIndex + 17);
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isSearchable() {
        return getBoolean(baseIndex + 18);
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isSigned() {
        return getBoolean(baseIndex + 19);
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isWritable() {
        return getBoolean(baseIndex + 20);
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    protected Object get(final int index) {
        if (pending != null) {
            throw pending;
        }
        return super.get(index);
    }

    /**
     * 
     * @param <T>
     * @param index
     * @param value
     * @return 
     */
    @Override
    protected <T> T set(final int index,
            final T value) {
        throw new UnsupportedOperationException("Read Only");
    }
    
    /**
     * 
     * @return 
     */
    @Override
    protected int size() {
        return baseIndex + 21;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    protected boolean hasMutableValues() {
        return false;
    }
    
}
