/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import org.joytools.commons.collections.IterableAccessors;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.lang.CaseSpaceNormalizer;
import org.joytools.commons.lang.EnumLookup;


/**
 *
 * @author AndreaR
 */
public enum ResultSetType {
    
    FORWARD_ONLY(ResultSet.TYPE_FORWARD_ONLY),
    SCROLL_INSENSITIVE(ResultSet.TYPE_SCROLL_INSENSITIVE),
    SCROLL_SENSITIVE(ResultSet.TYPE_SCROLL_SENSITIVE);
    
    /**
     * 
     * @param value
     * @param names 
     */
    ResultSetType(final int value,
            final String... names) {
        this.value = value;
        this.names = names;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public static ResultSetType of(final String name) {
        return LOOKUP.getExisting(name);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static ResultSetType of(final int value) {
        for (final ResultSetType t : values()) {
            if (t.value == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("No ResultSet type found with value: " + value);
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
    private final static EnumLookup<String, ResultSetType> LOOKUP = new EnumLookup<String, ResultSetType>(
        values(),
        CaseSpaceNormalizer.UPPERCASE_TRIM.castAsString()) {
            
        @Override
        protected Iterable<String> keysOf(final ResultSetType item) {
            final Set<String> keys = new HashSet();
            for (final String n : IterableAccessors.from(item.name()).append(item.names)) {
                keys.addAll(expand(n, "TYPE_" + n));
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
