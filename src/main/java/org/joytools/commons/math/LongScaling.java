/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.math;

import io.vavr.Function1;
import java.util.function.LongFunction;

/**
 *
 * @author AndreaR
 */
public class LongScaling extends AbstractScaling<Long> {
    
    /**
     * 
     * @param a1
     * @param b1
     * @param a2
     * @param b2 
     */
    protected LongScaling(
            final long a1,
            final long b1,
            final long a2,
            final long b2) {
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
    public static LongScaling of(final long a1, final long b1, final long a2, final long b2) {
        return new LongScaling(a1, b1, a2, b2);
    }
    
    /**
     * 
     * @param value
     * @return 
     */
    public long scale(final long value) {
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
    public long unscale(final long value) {
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
    public LongFunction<Long> toLongFunction() {
        return x -> scale(x);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public Function1<Long, Long> toFunction() {
        return x -> scale(x);
    }
    
    @Override
    public Long fromStart() {
        return a1;
    }

    @Override
    public Long fromStop() {
        return a1 + fromRange;
    }

    @Override
    public Long fromRange() {
        return fromRange;
    }

    @Override
    public Long toStart() {
        return a2;
    }

    @Override
    public Long toStop() {
        return a2 + toRange;
    }

    @Override
    public Long toRange() {
        return toRange;
    }

    private final long a1;
    
    private final long a2;

    private final long fromRange;

    private final long toRange;
    
}
