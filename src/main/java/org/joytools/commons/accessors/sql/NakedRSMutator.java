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
class NakedRSMutator extends NakedRSGenericMutator<Object> {

    /**
     * 
     * @param rs
     * @throws SQLException 
     */
    protected NakedRSMutator(final ResultSet rs) throws SQLException {
        super(rs);
    }
    
    /**
     * 
     * @param index
     * @return
     * @throws SQLException 
     */
    @Override
    protected Object get0(int index) throws SQLException {
        return rs.getObject(index + 1);
    }

    /**
     * 
     * @param index
     * @param value
     * @throws SQLException 
     */
    @Override
    protected void set0(int index, final Object value) throws SQLException {
        rs.updateObject(index + 1, value);
    }

}
