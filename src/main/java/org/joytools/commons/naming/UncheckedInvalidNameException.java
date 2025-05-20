/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming;

import javax.naming.InvalidNameException;

/**
 *
 * @author AndreaR
 */
public class UncheckedInvalidNameException extends UncheckedNamingException {
    
    /**
     * 
     * @param cause 
     */
    public UncheckedInvalidNameException(final InvalidNameException cause) {
        super(cause);
    }
    
    @Override
    public InvalidNameException getCause() {
        return InvalidNameException.class.cast(super.getCause());
    }

    @Override
    public InvalidNameException getUndeclaredThrowable() {
        return InvalidNameException.class.cast(super.getUndeclaredThrowable());
    }

    /**
     * 
     * @param message
     * @param cause 
     */ /*
    public InvalidNameRuntimeException(final String message,
            final InvalidNameException cause) {
        super(message, cause);
    } */

}
