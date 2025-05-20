/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.sql;

import org.joytools.commons.sql.ResultSetColumnMetaData;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.accessors.IndexedStringMapAccessor;

/**
 *
 * @author AndreaR
 */
// public interface ResultSetMetaDataMutator extends FixedSizeIndexedMapMutator<String, ResultSetColumnMetaData> {
public interface ResultSetMetaDataAccessor extends IndexedStringMapAccessor<ResultSetColumnMetaData> {
    
    @Override
    public CaseSensitivity caseSensitivity();
    
    public int indexOf(final String name);
    
}

