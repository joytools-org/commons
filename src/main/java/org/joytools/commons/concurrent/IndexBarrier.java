/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkArgument;
import org.apache.commons.lang3.function.FailableCallable;
import org.apache.commons.lang3.function.FailableConsumer;

/**
 *
 * @author AndreaR
 */
public class IndexBarrier {
    
    private volatile int allowedIndex = 0;

    /**
     * 
     * @param index
     * @throws InterruptedException 
     */
    public void await(final int index) throws InterruptedException {
        checkArgument(index >= 0, "Index must be not negative");
        while (true) {
            synchronized(this) {
                if (index < allowedIndex) {
                    throw new IllegalStateException(String.format("Index %s is lower than the current %s and will never be reached", index, allowedIndex));
                }
                if (allowedIndex == index) {
                    return;
                }
                wait();
            }
        }
    }
    
    /**
     * 
     */
    public void increment() {
        synchronized(this) {
            allowedIndex++;
            notifyAll();
        }
    }
    
}
