/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;

/**
 *
 * @author AndreaR
 */
public class WrappedCheckedException extends UndeclaredThrowableException {

    /**
     *
     * @param <T>
     * @param clazz
     * @return
     */
    public static <T extends Throwable> boolean isUnchecked(final Class<T> clazz) {
        Objects.requireNonNull(clazz);
        if (!Throwable.class.isAssignableFrom(clazz)) {
            throw new IllegalArgumentException(clazz.getName() + " is not a Throwable");
        }
        return RuntimeException.class.isAssignableFrom(clazz) || Error.class.isAssignableFrom(clazz);
    }
    
    /**
     * 
     * @param cause 
     */
    protected WrappedCheckedException(final Throwable cause) {
        super(checkCause(cause));
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String getMessage() {
        // return "A checked exception of type " + getCause().getClass().getName() + " has been wrapped: " + getCause().getMessage();
        return getCause().getMessage();
    }
    
    @Override
    public String getLocalizedMessage() {
        // return "A checked exception of type " + getCause().getClass().getName() + " has been wrapped: " + getCause().getMessage();
        return getCause().getLocalizedMessage();
    }

    /**
     * 
     * @param cause
     * @return 
     */
    protected static Throwable checkCause(final Throwable cause) {
        if (ExceptionUtils.isUnchecked(cause)) {
            throw new IllegalArgumentException(WrappedCheckedException.class.getName()  + " is meant to wrap checked exeptions only. " + 
                    cause.getClass().getName() + " is not a checked exception.");
        }
        return cause;
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    protected static Class assertCheckedException(final Class clazz) {
        if (isUnchecked(clazz)) {
            throw new IllegalArgumentException("throwCauseIf() could be used only with checked Exceptions. " + clazz.getName() + " is not a checked Exception.");
        }
        return clazz;
    }

    /**
     * 
     * @param clazz
     * @return 
     */
    protected static IllegalArgumentException newUnsupportedCauseException(final Class clazz) {
        return new IllegalArgumentException(WrappedCheckedException.class.getName() + " is meant to wrap checked exeptions only. " + 
                    clazz.getName() + " is not a checked exception.");
    }
    
    /**
     * 
     * @param msg
     * @param cause 
     */
    public WrappedCheckedException(final String msg, 
            final Exception cause) {
        super(cause, msg);
    }

    /**
     * 
     * @param cause
     * @param msg 
     */
    public WrappedCheckedException(final Exception cause,
            final String msg) {
        super(cause, msg);
    }
    
    /**
     * 
     * @param <T>
     * @param clazz
     * @return 
     * @throws T 
     */
    public <T extends Throwable> T throwCauseOfType(final Class<T> clazz) throws T {
        assertCheckedException(clazz);
        if (clazz.isAssignableFrom(getCause().getClass())) {
            throw clazz.cast(getCause());
        }
        return null;
    }
    
    /**
     * 
     * @return 
     */
    public IllegalStateException unexpectedCondition() {
        return unexpectedCondition(null);
    }

    private final static String DEFAULT_MESSAGE = "The program should never arrive here. " +
            "Please check if the WrappedCheckedException mechanism has been implemented propertly.";
    
    /**
     * 
     * @param message
     * @return 
     */
    public IllegalStateException unexpectedCondition(
            final CharSequence message) {
        final String s = message == null ? DEFAULT_MESSAGE : message.toString();
        return new IllegalStateException(s, getCause());
    }
    
}
