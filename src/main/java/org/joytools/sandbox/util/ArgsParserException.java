/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.sandbox.util;

/**
 * 
 * @author AndreaR
 */
public class ArgsParserException extends Exception {

    /**
     *
     */
    public ArgsParserException() { }

    /**
     *
     * @param message
     */
    public ArgsParserException(final String message) {
        super(message);
    }

    /**
     * 
     * @param cause 
     */
    public ArgsParserException(final Throwable cause) { 
        super(cause);
    }

    /**
     * 
     * @param message
     * @param cause 
     */
    public ArgsParserException(final String message,
            final Throwable cause) {
        super(message, cause);
    }

}