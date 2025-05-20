/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author AndreaR
 */
public class ResultSetMutators {
    
    /**
     * 
     * @param rs
     * @return 
     * @throws java.sql.SQLException 
     */
    public static ResultSetMutator<Object> of(final ResultSet rs) throws SQLException {
        return new RSMutatorImpl(rs);
    }
   
    /**
     * 
     * @param rs
     * @return 
     */ /*
    public static ResultSetColumnDataMutatorChecked CheckedColDataMutator(final ResultSet rs) {
        Objects.requireNonNull(rs, "ResultSet");
        return new AbstractResultSetColumnDataMutatorChecked() {
            @Override
            protected ResultSet resultSet() {
                return rs;
            }

            @Override
            public ResultSetMetaDataAccessor metaData() {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
    } */
    
}
