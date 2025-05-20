/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import static com.google.common.base.Preconditions.checkState;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public abstract class AbstractFailableOpenable<E extends Exception> extends FailableAutoCloseableContainer<E> implements FailableOpenable<E> {

    /**
     * 
     */
    protected AbstractFailableOpenable() {
    }
    
    /**
     * 
     * @throws E
     */
    @Override
    public final void open() throws E {
        openInvoked = true;
        openSucceeded = false;
        init();
        openSucceeded = true;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public boolean isOpenInvoked() {
        return openInvoked;
    }

    /**
     * 
     * @return 
     */
    private boolean lazyOpen() {
        if (isOpenInvoked()) {
            checkOpen();
            return false;
        }
        try {
            open();
            return true;
        // } catch (final RuntimeException rt) {
        //    throw rt;
        } catch (final Exception e) {
            throw new IllegalStateException(String.format("Initialization of %s failed", getClass().getName()), e);
        }
    }

    /**
     * 
     * @return
     * @throws E 
     */
    private boolean checkedLazyOpen() throws E {
        if (isOpenInvoked()) {
            checkOpen();
            return false;
        }
        open();
        return true;

        // return lazyOpen();
        /*
        if (isOpenInvoked()) {
            checkOpen();
            return false;
        }
        open();
        return true;
        */
    }

    /**
     * 
     * @return 
     */    
    protected abstract boolean isLazyOpen();
    
    /**
     * 
     */
    protected void makeOpen() {
        if (isLazyOpen()) {
            lazyOpen();
        } else {
            checkOpen();
        }
    }

    /**
     * 
     */
    protected void checkedMakeOpen() throws E {
        if (isLazyOpen()) {
            checkedLazyOpen();
        } else {
            checkOpen();
        }
    }
    
    /**
     * 
     * @throws E
     */
    @Override
    public void close() throws E {
        try {
            checkState(openInvoked, "Close has been invoked with no initialization");
        } finally {
            try {
                super.close();
            } finally {
                openInvoked = false;
                openSucceeded = false;
            }
        }
    }

    /**
     * 
     * @return 
     */
    protected boolean isOpen() {
        return openInvoked && openSucceeded;
    }
    
    /**
     * 
     */
    private void checkOpen() {
        checkState(openInvoked, "Initialization of %s has not been invoked", getClass().getName());
        checkState(openSucceeded, "Initialization of %s failed", getClass().getName());
    }
    
    /**
     * 
     * @return 
     */
    protected boolean isInitialized() {
        return openInvoked && openSucceeded;
    }
    
    /**
     * 
     * @throws E 
     */
    abstract protected void init() throws E;
    
    /**
     * 
     */
    private boolean openInvoked = false;
    
    /**
     * 
     */
    private boolean openSucceeded = false;
    
}
