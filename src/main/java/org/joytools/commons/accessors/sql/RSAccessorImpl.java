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
class RSAccessorImpl extends NakedRSMutator implements ResultSetAccessor<Object> {
    
    /**
     * 
     * @param rs
     * @throws SQLException 
     */
    protected RSAccessorImpl(final ResultSet rs) throws SQLException {
        super(rs);
    }
    
}
