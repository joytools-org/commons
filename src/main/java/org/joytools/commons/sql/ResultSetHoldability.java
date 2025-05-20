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
public enum ResultSetHoldability {
    
    HOLD_CURSORS_OVER_COMMIT(ResultSet.HOLD_CURSORS_OVER_COMMIT),
    CLOSE_CURSORS_AT_COMMIT(ResultSet.CLOSE_CURSORS_AT_COMMIT);
    
    /**
     * 
     * @param value
     * @param names 
     */
    ResultSetHoldability(final int value,
            final String... names) {
        this.value = value;
        this.names = names;
    }

    /**
     * 
     * @param name
     * @return 
     */
    public static ResultSetHoldability of(final String name) {
        return LOOKUP.getExisting(name);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public static ResultSetHoldability of(final int value) {
        for (final ResultSetHoldability t : values()) {
            if (t.value == value) {
                return t;
            }
        }
        throw new IllegalArgumentException("No ResultSet holdability found with value: " + value);
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
    private final static EnumLookup<String, ResultSetHoldability> LOOKUP = new EnumLookup<String, ResultSetHoldability>(
        values(),
        CaseSpaceNormalizer.UPPERCASE_TRIM.castAsString()) {
            
        @Override
        protected Iterable<String> keysOf(final ResultSetHoldability item) {
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
