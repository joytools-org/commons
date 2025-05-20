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
class RSMutatorImpl extends NakedRSMutator implements ResultSetMutator<Object> {
    
    protected RSMutatorImpl(final ResultSet rs) throws SQLException {
        super(rs);
    }
    
}
