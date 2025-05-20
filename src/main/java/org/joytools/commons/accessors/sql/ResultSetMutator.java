/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.sql;

import org.joytools.commons.accessors.FixedSizeIndexedStringMapMutator;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface ResultSetMutator<V> extends ResultSetAccessor<V>, FixedSizeIndexedStringMapMutator<V> {
       
    @Override
    default <Z> ResultSetMutator<Z> castAs(final Class<Z> type) {
        return (ResultSetMutator<Z>)this;
    }

    /**
     * 
     * @return 
     */
    @Override
    public ResultSetMetaDataAccessor metaData();
            
}
