/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkState;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 */
abstract class AbstractComposedResultImpl<K, V> implements ComposedResult<K, V> {

    /**
     * 
     * @return 
     */
    protected final void checkNoFailures() {
        final int failures = failedCount();
        checkState(failures == 0, "Values not available since there are %s errors.", failures);
    }
    
    @Override
    public final int succeededCount() {
        return succeededSeq().size();
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final double succeededRatio() {
        final double size = size();
        if (size == 0) {
            return 1;
        }
        return (double)succeededCount() / size;
    }

    @Override
    public final int failedCount() {
        return failedSeq().size();
    }

    /**
     * 
     * @return 
     */
    @Override
    public final double failedRatio() {
        final double size = size();
        if (size == 0) {
            return 0;
        }
        return (double)failedCount() / size;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final boolean isNotEmpty() {
        return size() > 0;
    }

    /**
     * 
     * @return 
     */
    @Override
    public final boolean isAnySucceeded() {
        return succeededCount() > 0;
    }

    /**
     * 
     * @return 
     */
    @Override
    public final boolean isAllSucceeded() {
        return succeededCount() > 0 && failedCount() == 0;
    }

    /**
     * 
     * @return 
     */
    @Override
    public final boolean isAllSucceededOrEmpty() {
        return isAllSucceeded() || isEmpty();
    }

    /**
     * 
     * @return 
     */
    @Override
    public final boolean isAnySucceededOrEmpty() {
        return isAnySucceeded() || isEmpty();
    }

    /**
     * 
     * @return 
     */
    @Override
    public final boolean isAllFailed() {
        if (isEmpty()) {
            return false;
        }
        return succeededCount() == 0;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final boolean isAnyFailed() {
        return failedCount() > 0;
    }
    
    @Override
    public final <T> T throwExceptions() throws Exception {
        return ExceptionUtils.throwMultipleException(failedSeq());
    }
    
}
