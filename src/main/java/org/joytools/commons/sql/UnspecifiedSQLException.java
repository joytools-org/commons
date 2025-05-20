/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.SQLException;

/**
 *
 * @author AndreaR
 */
public class UnspecifiedSQLException extends SQLException {
 
    /**
     * 
     */
    public UnspecifiedSQLException() {
    }
    
    /**
     * 
     * @param cause 
     */
    public UnspecifiedSQLException(final Throwable cause) {
        super(cause);
    }
    
}
