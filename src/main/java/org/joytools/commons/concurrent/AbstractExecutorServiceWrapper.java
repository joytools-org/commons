/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractExecutorServiceWrapper {
    
    protected AbstractExecutorServiceWrapper(final ExecutorService es) {
        this.es = checkNotNull(es, "Executor Service");
    }
    
    protected final ExecutorService executorService() {
        return es;
    }
    
    private final ExecutorService es;
    
}
