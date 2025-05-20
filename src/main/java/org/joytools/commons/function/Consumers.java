/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.function;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.profile.Counter;

/**
 *
 * @author AndreaR
 */
public class Consumers {
    
    /**
     * 
     * @param <T>
     * @param test
     * @param ifTrue
     * @return 
     */
    public static <T> Consumer<T> iif(final Predicate<? super T> test, final Consumer<T> ifTrue) {
        return iif(test, ifTrue, null);
    }

    /**
     * 
     * @param <T>
     * @param test
     * @param ifTrue
     * @param ifFalse
     * @return 
     */
    public static <T> Consumer<T> iif(final Predicate<? super T> test, final Consumer<T> ifTrue, final Consumer<T> ifFalse) {
        return x -> {
            if (test.test(x)) {
                if (ifTrue != null) {
                    ifTrue.accept(x);
                }
            } else {
                if (ifFalse != null) {
                    ifFalse.accept(x);
                }
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param consumer
     * @return 
     */
    public static <T> Consumer<T> lenient(final Consumer<T> consumer) {
        return lenient(consumer, true);
    }

    /**
     * 
     * @param <T>
     * @param consumer
     * @param lenient
     * @return 
     */
    public static <T> Consumer<T> lenient(final Consumer<T> consumer, boolean lenient) {
        Objects.requireNonNull(consumer, "Consumer");
        return (final T input) -> {
            if (lenient) {
                try {
                    consumer.accept(input);
                } catch (final Exception e) {
                }
            } else {
                consumer.accept(input);
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @param <C>
     * @param consumer
     * @param exceptions
     * @return 
     */
    public static <T, C extends Collection<? super Exception>> Consumer<T> lenient(final Consumer<T> consumer, final C exceptions) {
        Objects.requireNonNull(consumer, "Consumer");
        return (final T input) -> {
            if (exceptions != null) {
                try {
                    consumer.accept(input);
                } catch (final Exception e) {
                    exceptions.add(e);
                }
            } else {
                consumer.accept(input);
            }
        };
    }

    /**
     * 
     * @param <P>
     * @param threshold
     * @param p
     * @return 
     */
    public static <P> Consumer<P> threshold(final int threshold, final Consumer<P> p) {
        return threshold(threshold, null, 0, p);
    }

    /**
     * 
     * @param <P>
     * @param timeUnit
     * @param timeValue
     * @param p
     * @return 
     */
    public static <P> Consumer<P> threshold(final TimeUnit timeUnit, final int timeValue, final Consumer<P> p) {
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
    private static <P> Consumer<P> threshold(final int threshold, final TimeUnit timeUnit, final int timeValue, final Consumer<P> p) {
        final long timeoutMillis = (timeUnit == null || timeValue <= 0) ? 0 : TimeUnit.MILLISECONDS.convert(timeValue, timeUnit);
        final Counter c = new Counter(threshold, (int) timeoutMillis);
        return (final P payload) -> {
            if (c.increment() && p != null) {
                p.accept(payload);
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    public static <T> Consumer<T> none() {
        return (Consumer<T>)NONE;
    }

    private final static Consumer<Object> NONE = x -> { };

}
