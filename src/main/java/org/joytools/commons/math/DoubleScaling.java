/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.math;

import io.vavr.Function1;
import java.util.function.DoubleFunction;

/**
 *
 * @author AndreaR
 */
public class DoubleScaling extends AbstractScaling<Double> {
    
    /**
     * 
     * @param a1
     * @param b1
     * @param a2
     * @param b2 
     */
    protected DoubleScaling(
            final double a1,
            final double b1,
            final double a2,
            final double b2) {
        this.a1 = a1;
        this.b1 = b1;
        this.a2 = a2;
        this.b2 = b2;
        fromRange = b1 - a1;
        toRange = b2 - a2;
        // checkFromRange(fromRange);
        // checkToRange(toRange);
    }
    
    /**
     * 
     * @param a1
     * @param b1
     * @param a2
     * @param b2
     * @return 
     */
    public static DoubleScaling of(final double a1, final double b1, final double a2, final double b2) {
        return new DoubleScaling(a1, b1, a2, b2);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public double scale(final double value) {
        if (value == a1) {
            return a2;
        }
        if (value == b1) {
            return b2;
        }
        return (value - a1) * toRange / fromRange + a2;
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public double unscale(final double value) {
        if (value == a2) {
            return a1;
        }
        if (value == a2 + toRange) {
            return a1 + fromRange;
        }
        return (value - a2) * fromRange / toRange + a1;
    }

    /**
     * 
     * @return 
     */
    public DoubleFunction<Double> toDoubleFunction() {
        return x -> scale(x);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Function1<Double, Double> toFunction() {
        return x -> scale(x);
    }

    @Override
    public Double fromStart() {
        return a1;
    }

    @Override
    public Double fromStop() {
        return b1;
    }

    @Override
    public Double toStart() {
        return a2;
    }

    @Override
    public Double toStop() {
        return b2;
    }

    @Override
    public Double fromRange() {
        return fromRange;
    }

    @Override
    public Double toRange() {
        return toRange;
    }

    private final double a1;
    
    private final double b1;

    private final double a2;

    private final double b2;
    
    private final double fromRange;

    private final double toRange;
    
}
