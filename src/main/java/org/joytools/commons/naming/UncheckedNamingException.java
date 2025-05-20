/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming;

import javax.naming.NamingException;
import org.joytools.commons.lang.WrappedCheckedException;

/**
 *
 * @author AndreaR
 */
public class UncheckedNamingException extends WrappedCheckedException {
    
    /**
     * 
     * @param cause 
     */
    public UncheckedNamingException(final NamingException cause) {
        super(cause);
    }

    @Override
    public NamingException getCause() {
        return NamingException.class.cast(super.getCause());
    }

    @Override
    public NamingException getUndeclaredThrowable() {
        return NamingException.class.cast(super.getUndeclaredThrowable());
    }
    
    /**
     * 
     * @param message
     * @param cause 
     */ /*
    public NamingRuntimeException(final String message, 
            final Exception cause) {
        super(message, cause);
    } */
    
}
