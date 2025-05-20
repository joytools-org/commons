/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.lang3.function.FailableConsumer;

import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class ContextUtils {

    /**
     * 
     */
    public ContextUtils() {
    }
    
    /**
     * 
     * @param <T>
     * @param name
     * @param targetClass
     * @return 
     * @throws javax.naming.NamingException 
     */
    public static <T> T typedLookup(final String name,
            final Class<T> targetClass) throws NamingException {
        final Context ctx = new InitialContext();
        try {
            return typedLookup(ctx, name, targetClass);
        } finally {
            ctx.close();
        }
    }
    
    /**
     * 
     * @param <T>
     * @param ctx
     * @param targetClass
     * @param name
     * @return
     * @throws NamingException 
     */
    public static <T> T typedLookup(final Context ctx,
            final String name,
            final Class<T> targetClass) throws NamingException {
        if (ctx == null) {
            throw new IllegalArgumentException("No Context specified");
        }
        if (targetClass == null) {
            throw new IllegalArgumentException("No target class specified");
        }
        final String targetClassName = targetClass.getCanonicalName();
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("No " + targetClassName + " name specified");
        }
        final Object obj = ctx.lookup(name);
        if (obj == null) {
            throw new NamingException("No " + targetClassName + " found for name " + name);
        }
        try {
            return targetClass.cast(obj);
        } catch (final ClassCastException cce) {
            final String msg = String.format(
                    "%s expected for name %s but a %s has been found", 
                    targetClassName, name, obj.getClass().getCanonicalName());
            throw new ClassCastException(msg);
        }
    }
    
    /**
     * 
     * @param ctx
     * @param name
     * @return 
     * @throws javax.naming.NamingException
     */
    public static DataSource lookupDataSource(final Context ctx,
            final String name) throws NamingException {
        return typedLookup(ctx, name, DataSource.class);
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws NamingException 
     */
    public static DataSource lookupDataSource(
            final String name) throws NamingException {
        return ContextUtils.typedLookup(name, DataSource.class);
    }

    /**
     * 
     * @param ctx
     * @param name
     * @return
     * @throws NamingException 
     * @throws java.sql.SQLException 
     */
    public static Connection getConnection(final Context ctx,
            final String name) throws NamingException, SQLException {
        return lookupDataSource(ctx, name).getConnection();
    }

    /**
     * 
     * @param name
     * @return
     * @throws NamingException
     * @throws SQLException 
     */
    public static Connection getConnection(
            final String name) throws NamingException, SQLException {
        return lookupDataSource(name).getConnection();
    }

    /**
     * 
     * @return 
     */
    public static FailableConsumer<Context, NamingException> closer() {
        return CONTEXT_CLOSER;
    }
    
    private final static FailableConsumer<Context, NamingException> CONTEXT_CLOSER = (final Context ctx) -> ctx.close();
    
}
