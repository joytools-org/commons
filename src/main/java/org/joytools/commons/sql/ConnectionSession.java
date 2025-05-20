/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.Connection;
import java.sql.SQLException;
import org.joytools.commons.util.FailableAutoCloseableContainer;

/**
 *
 * @author AndreaR
 */
public abstract class ConnectionSession extends FailableAutoCloseableContainer<SQLException> {
    
    /**
     * 
     * @return
     * @throws SQLException 
     */
    public abstract Connection connection() throws SQLException;
    
}
