/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.sql;

import org.joytools.commons.accessors.IndexedStringMapAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface ResultSetAccessor<V> extends IndexedStringMapAccessor<V> {
       
    @Override
    default <Z> ResultSetAccessor<Z> castAs(final Class<Z> type) {
        return (ResultSetAccessor<Z>)this;
    }

    /**
     * 
     * @return 
     */
    public ResultSetMetaDataAccessor metaData();
            
}
