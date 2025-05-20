/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.util;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public abstract class FailableOpenableContainer<E extends Exception> extends FailableAutoCloseableContainer<E> implements FailableOpenable<E> {
    
    @Override
    public void open() throws E {
        inner.open();
    }

    @Override
    public boolean isOpenInvoked() {
        return inner.isOpenInvoked();
    }

    @Override
    public void close() throws E {
        try {
            inner.close();
        } finally {
            super.close();
        }
    }

    abstract protected void init();
    
    abstract protected boolean isLazyOpen();
    
    protected void checkedMakeOpen() throws E {
        inner.checkedMakeOpen();
    }
    
    protected void makeOpen() {
        inner.makeOpen();
    }
    
    protected boolean isInitialized() {
        return inner.isInitialized();
    }
    
    public boolean isOpen() {
        return inner.isOpen();
    }
    
    protected class InnerOpenable extends AbstractFailableOpenable<E> {
        @Override
        protected void init() throws Exception {
            FailableOpenableContainer.this.init();
        }

        @Override
        protected boolean isLazyOpen() {
            return FailableOpenableContainer.this.isLazyOpen();
        }
    };
    
    private final InnerOpenable inner = new InnerOpenable();

    @Override
    protected Disposer<E> disposer() {
        makeOpen();
        return super.disposer();
    }
    
}
