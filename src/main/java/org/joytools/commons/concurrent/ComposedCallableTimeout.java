/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.util.concurrent.TimeoutException;

/**
 *
 * @author AndreaR
 */
public class ComposedCallableTimeout extends TimeoutException {
    
    public ComposedCallableTimeout() {
        partialResult = null;
    }
    
    public ComposedCallableTimeout(final String msg) {
        super(msg);
        partialResult = null;
    }
    
    public ComposedCallableTimeout(final ComposedResult r) {
        partialResult = r;
    }
    
    public ComposedCallableTimeout(final String msg, final ComposedResult r) {
        super(msg);
        partialResult = r;
    }

    public ComposedResult partialResult() {
        return partialResult;
    }
    
    private final ComposedResult partialResult;
    
}
