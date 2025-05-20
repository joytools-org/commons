/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author AndreaR
 * @param <E>
 */
@Deprecated
public class BlockingQueueProxyV1BeforeGuavaForwarding<E> extends AbstractBlockingQueueProxyV1<E> {
    
    /**
     * 
     */
    public BlockingQueueProxyV1BeforeGuavaForwarding() {
    }
    
    /**
     * 
     * @param queue 
     */
    public BlockingQueueProxyV1BeforeGuavaForwarding(final BlockingQueue<E> queue) {
        this.queue = queue;
    }
    
    /**
     * 
     * @param queue 
     */
    public void setQueue(final BlockingQueue<E> queue) {
        this.queue = queue;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    protected BlockingQueue<E> queue() {
        if (queue == null) {
            throw new IllegalStateException("No Blocking Queue set");
        }
        return queue;
    }
    
    /**
     * 
     */
    private BlockingQueue<E> queue = null;
    
}
