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
public class ResultSetColumnDataAccessors {
    
    /**
     * 
     * @param rs
     * @return
     * @throws SQLException 
     */
    public static ResultSetColumnDataAccessor of(final ResultSet rs) throws SQLException {
        return new RSColumnDataAccessorImpl(rs);
    }
    
}
