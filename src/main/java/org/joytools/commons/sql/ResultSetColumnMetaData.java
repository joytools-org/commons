/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.util.Objects;

/**
 *
 * @author AndreaR
 */
public interface ResultSetColumnMetaData {
 
    public int getIndex();
    
    public String getCatalogName();
    
    public String getClassName();
    
    public int getDisplaySize();

    public String getLabel();

    public String getName();    

    public Type getType();    

    public String getTypeName();    
    
    public int getPrecision();    

    public int getScale();    

    public String getTableName();    

    public String getSchemaName();    

    public boolean isAutoIncrement();
    
    public boolean isCaseSensitive();
    
    public boolean isCurrency();
    
    public boolean isDefinitelyWritable();
    
    public ColumnNullability isNullable();
    
    public boolean isReadOnly();
    
    public boolean isSearchable();
    
    public boolean isSigned();
    
    public boolean isWritable();
    
    default boolean sameTypeOf(final ResultSetColumnMetaData other) {
        return Objects.equals(this.getCatalogName(), other.getCatalogName())
                && Objects.equals(this.getClassName(), other.getClassName())
                && Objects.equals(this.getDisplaySize(), other.getDisplaySize())
                && Objects.equals(this.getLabel(), other.getLabel())
                && Objects.equals(this.getName(), other.getName())
                && Objects.equals(this.getType(), other.getType())
                && Objects.equals(this.getTypeName(), other.getTypeName())
                && Objects.equals(this.getPrecision(), other.getPrecision())
                && Objects.equals(this.getScale(), other.getScale())
                && Objects.equals(this.getTableName(), other.getTableName())
                && Objects.equals(this.getSchemaName(), other.getSchemaName())
                && Objects.equals(this.isAutoIncrement(), other.isAutoIncrement())
                && Objects.equals(this.isCaseSensitive(), other.isCaseSensitive())
                && Objects.equals(this.isCurrency(), other.isCurrency())
                && Objects.equals(this.isDefinitelyWritable(), other.isDefinitelyWritable())
                && Objects.equals(this.isNullable(), other.isNullable())
                && Objects.equals(this.isReadOnly(), other.isReadOnly())
                && Objects.equals(this.isSearchable(), other.isSearchable())
                && Objects.equals(this.isSigned(), other.isSigned())
                && Objects.equals(this.isWritable(), other.isWritable());
    }
    
}
