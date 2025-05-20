/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.sql;

import java.sql.SQLException;

import org.joytools.commons.lang.WrappedCheckedException;

/**
 *
 * @author AndreaR
 */
public class UncheckedSQLException extends WrappedCheckedException {

    /**
     * 
     * @param cause 
     */
    public UncheckedSQLException(final SQLException cause) {
        super(cause);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public SQLException getCause() {
        return SQLException.class.cast(super.getCause());
    }

    /**
     * 
     * @return 
     */
    @Override
    public SQLException getUndeclaredThrowable() {
        return SQLException.class.cast(super.getUndeclaredThrowable());
    }
    
    /**
     * 
     * @return 
     */
    public int getErrorCode() {
        return getCause().getErrorCode();
    }

    /**
     * 
     * @return 
     */
    public String getSQLState() {
        return getCause().getSQLState();
    }

}
