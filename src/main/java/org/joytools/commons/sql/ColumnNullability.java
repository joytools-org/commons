/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.ResultSetMetaData;
import java.util.HashSet;
import java.util.Set;
import org.joytools.commons.collections.IterableAccessors;
// import org.joytools.commons.collections.StreamIterable;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.lang.CaseSpaceNormalizer;
import org.joytools.commons.lang.EnumLookup;


/**
 *
 * @author AndreaR
 */
public enum ColumnNullability {
    
    NOT_NULLABLE(ResultSetMetaData.columnNoNulls, "NO_NULL", "NO_NULLS"),
    NULLABLE(ResultSetMetaData.columnNullable, "NULL"),    
    UNKNOWN(ResultSetMetaData.columnNullableUnknown, "NULLABLE_UNKNOWN");
    
    /**
     * 
     * @param value
     * @param names 
     */
    ColumnNullability(final int value,
            final String... names) {
        this.value = value;
        this.names = names;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public static ColumnNullability of(final String name) {
        return LOOKUP.getExisting(name);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static ColumnNullability of(final int value) {
        for (final ColumnNullability t : values()) {
            if (t.value == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("No nullability found with value: " + value);
    }

    /**
     * 
     * @return 
     */
    public int value() {
        return value;
    }

    /**
     * 
     */
    private final static EnumLookup<String, ColumnNullability> LOOKUP = new EnumLookup<String, ColumnNullability>(
        values(),
        CaseSpaceNormalizer.UPPERCASE_TRIM.castAsString()) {
            
        @Override
        protected Iterable<String> keysOf(final ColumnNullability item) {
            final Set<String> keys = new HashSet();
            for (final String n : IterableAccessors.from(item.name()).append(item.names)) {
                keys.addAll(expand(n));
            }
            return keys;
        }
        
    };

    /**
     * 
     */
    private final int value;
    
    /**
     * 
     */
    private final String[] names;
    
}

