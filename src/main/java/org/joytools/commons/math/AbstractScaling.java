/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.math;

import io.vavr.Function1;

/**
 *
 * @author AndreaR
 */
abstract class AbstractScaling<N> {
    
    /**
     * 
     */
    protected AbstractScaling() {
    }
    
    /**
     * 
     * @param distance 
     */
    protected static void checkFromRange(final Number distance) {
        if (distance.intValue() == 0) {
            throw new IllegalArgumentException("From range is 0");
        }
    }
    
    /**
     * 
     * @param distance 
     */
    protected static void checkToRange(final Number distance) {
        if (distance.intValue() == 0) {
            throw new IllegalArgumentException("To range is 0");
        }
    }

    /**
     * 
     * @return 
     */
    public abstract N fromStart();
    
    /**
     * 
     * @return 
     */
    public abstract N fromStop();

    /**
     * 
     * @return 
     */
    public abstract N fromRange();

    /**
     * 
     * @return 
     */
    public abstract N toStart();

    /**
     * 
     * @return 
     */
    public abstract N toStop();

    /**
     * 
     * @return 
     */
    public abstract N toRange();

    /**
     * 
     * @return 
     */
    public abstract Function1<N, N> toFunction();
    
}
