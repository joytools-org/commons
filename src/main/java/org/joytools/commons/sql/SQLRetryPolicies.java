/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import org.apache.commons.lang3.function.FailableCallable;
import org.joytools.commons.concurrent.DefaultRetryPolicy;

/**
 *
 * @author AndreaR
 */
public class SQLRetryPolicies {

    /**
     * 
     */
    private final static Predicate<Exception> LOCK_EXCEPTION_FILTER = (final Exception ex) -> SQLUtils.isLockException(ex);
    
    /**
     * 
     */
    private final static Predicate<Exception> DUPLICATE_KEY_EXCEPTION_FILTER = (final Exception ex) -> SQLUtils.isDuplicateKeyException(ex);

    /**
     * 
     */
    private final static DefaultRetryPolicy LOCK_RETRY_POLICY = new DefaultRetryPolicy(
            LOCK_EXCEPTION_FILTER, 
            5, 
            DefaultRetryPolicy.DELAY_POLICY_5_SECONDS_EXPONENTIAL);

    /**
     * 
     */
    private final static DefaultRetryPolicy DUPLICATE_KEY_RETRY_POLICY = new DefaultRetryPolicy(
            DUPLICATE_KEY_EXCEPTION_FILTER, 
            5, 
            DefaultRetryPolicy.DELAY_POLICY_NONE);

    /**
     * 
     * @param <V>
     * @param t
     * @return 
     */
    public static <V> FailableCallable<V, SQLException> lockRetryable(final Callable<V> t) {
        final Callable<V> callable = (Callable<V>)LOCK_RETRY_POLICY.apply(t);
        return () -> {
            try {
                return callable.call();
            } catch (final Exception ex) {
                return SQLUtils.throwSQLException(ex);
            }
        };
    }
    
    /**
     * 
     * @param <V>
     * @param t
     * @return
     * @throws SQLException 
     */
    public static <V> V lockRetry(final Callable<V> t) throws SQLException {
        return lockRetryable(t).call();
    }

    /**
     * 
     * @param <V>
     * @param t
     * @return 
     */
    public static <V> FailableCallable<V, SQLException> duplicateKeyRetryable(final Callable<V> t) {
        final Callable<V> callable = (Callable<V>)DUPLICATE_KEY_RETRY_POLICY.apply(t);
        return () -> {
            try {
                return callable.call();
            } catch (final Exception ex) {
                return SQLUtils.throwSQLException(ex);
            }
        };
    }
    
    /**
     * 
     * @param <V>
     * @param t
     * @return
     * @throws SQLException 
     */
    public static <V> V duplicateKeyRetry(final Callable<V> t) throws SQLException {
        return duplicateKeyRetryable(t).call();
    }

}
