/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.function;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailablePredicate;
import org.joytools.commons.profile.Counter;

/**
 *
 * @author AndreaR
 */
public class Processors {

    /**
     * 
     * @param <P>
     * @param <E>
     * @param pred
     * @param cons
     * @return 
     */
    public static <P, E extends Exception> FailableProcessor<P, Boolean, E> of(final Predicate<P> pred, final Consumer<P> cons) {
        Objects.requireNonNull(pred, "Predicate");
        Objects.requireNonNull(cons, "Consumer");
        return p -> {
            if (pred.test(p)) {
                cons.accept(p);
                return true;
            }
            return false;
        };
    }

    /**
     * 
     * @param <P>
     * @param <E>
     * @param pred
     * @param cons
     * @return 
     */
    public static <P, E extends Exception> FailableProcessor<P, Boolean, E> ofFailable(final FailablePredicate<P, E> pred, 
            final FailableConsumer<P, E> cons) {
        Objects.requireNonNull(pred, "Predicate");
        Objects.requireNonNull(cons, "Consumer");
        return p -> {
            if (pred.test(p)) {
                cons.accept(p);
                return true;
            }
            return true;
        };
    }

    /**
     * 
     * @param <P>
     * @param <E>
     * @param threshold
     * @param p
     * @return 
     */
    public static <P, E extends Exception> FailableProcessor<P, Boolean, E> threshold(final int threshold, final FailableProcessor<P, Boolean, E> p) {
        return threshold(threshold, null, 0, p);
    }

    /**
     * 
     * @param <P>
     * @param <E>
     * @param timeUnit
     * @param timeValue
     * @param p
     * @return 
     */
    public static <P, E extends Exception> FailableProcessor<P, Boolean, E> threshold(final TimeUnit timeUnit, final int timeValue, final FailableProcessor<P, Boolean, E> p) {
        return threshold(0, timeUnit, timeValue, p);
    }

    /**
     *
     * @param <P>
     * @param <R>
     * @param threshold
     * @param timeout
     * @param p
     * @return
     */
    private static <P, E extends Exception> FailableProcessor<P, Boolean, E> threshold(final int threshold, final TimeUnit timeUnit, final int timeValue, final FailableProcessor<P, Boolean, E> p) {
        final long timeoutMillis = (timeUnit == null || timeValue <= 0) ? 0 : TimeUnit.MILLISECONDS.convert(timeValue, timeUnit);
        final Counter c = new Counter(threshold, (int) timeoutMillis);
        return (final P payload) -> {
            if (c.increment() && p != null) {
                return p.process(payload);
            }
            return true;
        };
    }
    
}
