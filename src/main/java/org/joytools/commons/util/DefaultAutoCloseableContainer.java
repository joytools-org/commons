/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import org.joytools.commons.function.FailableAutoCloseables;


/**
 *
 * @author AndreaR
 */
public abstract class DefaultAutoCloseableContainer extends FailableAutoCloseableContainer<Exception> {

    /**
     * 
     */
    protected DefaultAutoCloseableContainer() {
    }
    
    /**
     * 
     * @param <T>
     * @param item
     * @return 
     * @throws java.lang.Exception 
     */
    protected <T extends AutoCloseable> T dispose(final T item) throws Exception {
        dispose(FailableAutoCloseables.of(item::close));
        return item;
    }
        
}
