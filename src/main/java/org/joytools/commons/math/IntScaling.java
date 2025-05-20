/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.math;

import io.vavr.Function1;
import java.util.function.IntFunction;

/**
 *
 * @author AndreaR
 */
public class IntScaling extends AbstractScaling<Integer> {

    /**
     * 
     * @param a1
     * @param b1
     * @param a2
     * @param b2 
     */
    protected IntScaling(
            final int a1,
            final int b1,
            final int a2,
            final int b2) {
        this.a1 = a1;
        this.a2 = a2;
        fromRange = b1 - a1;
        toRange = b2 - a2;
        checkFromRange(fromRange);
        checkToRange(toRange);
    }
    
    /**
     * 
     * @param a1
     * @param b1
     * @param a2
     * @param b2
     * @return 
     */
    public static IntScaling of(final int a1, final int b1, final int a2, final int b2) {
        return new IntScaling(a1, b1, a2, b2);
    }

    /**
     * 
     * @param value
     * @return 
     */
    public int scale(final int value) {
        if (value == a1) {
            return a2;
        }
        if (value == a1 + fromRange) {
            return a2 + toRange;
        }
        return (value - a1) * toRange / fromRange + a2;
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public int unscale(final int value) {
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
    public IntFunction<Integer> toDoubleFunction() {
        return x -> scale(x);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Function1<Integer, Integer> toFunction() {
        return x -> scale(x);
    }
 
    private final int a1;
    
    private final int a2;

    private final int fromRange;

    private final int toRange;

    @Override
    public Integer fromStart() {
        return a1;
    }

    @Override
    public Integer fromStop() {
        return a1 + fromRange;
    }

    @Override
    public Integer fromRange() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Integer toStart() {
        return a2;
    }

    @Override
    public Integer toStop() {
        return a2 + toRange;
    }

    @Override
    public Integer toRange() {
        return toRange;
    }
    
}
