/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Function;
import org.apache.commons.collections4.Equator;
import org.apache.commons.collections4.functors.DefaultEquator;

/**
 *
 * @author AndreaR
 */
public class Equators {
    
    /**
     * 
     * @param <T>
     * @param <R>
     * @param equator
     * @param function
     * @return 
     */
    public static <T> Equator<T> of(final Function<T, T> function) {
        return of(function, null);
    }
    
    /**
     * 
     * @param <T>
     * @param <R>
     * @param equator
     * @param function
     * @return 
     */
    public static <T, R> Equator<T> of(
            final Function<T, T> function,
            final Equator<T> equator) {
        final Equator<T> eq = equator == null ? DefaultEquator.defaultEquator() : equator;
        if (function == null) {
            return eq;
        }
        return new AbstractEquator<T>() {
            @Override
            public boolean equate(final T o1, final T o2) {
                final T r1 = function.apply(o1);
                final T r2 = function.apply(o2);
                return eq.equate(r1, r2);
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @param predicate
     * @return 
     */
    public static <T> Equator<T> of(final BiPredicate<T, T> predicate) {
        if (predicate == null) {
            return DefaultEquator.defaultEquator();
        }
        return new AbstractEquator<T>() {
            @Override
            public boolean equate(final T o1, final T o2) {
                return predicate.test(o1, o2);
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param comp
     * @return 
     */
    public static <T> Equator<T> of(final Comparator<T> comp) {
        if (comp == null) {
            return DefaultEquator.defaultEquator();
        }
        return new AbstractEquator<T>() {
            @Override
            public boolean equate(final T o1, final T o2) {
                return comp.compare(o1, o2) == 0;
            }
        };
    }

    /**
     * 
     * @return 
     */
    public static Equator<String> caseSensitive() {
        return CASE_SENSITIVE_EQUATOR;
    }
    
    /**
     * 
     * @param function
     * @return 
     */
    public static Equator<String> caseSensitive(final Function<String, String> function) {
        return of(function, CASE_SENSITIVE_EQUATOR);
    }

    /**
     * 
     * @return 
     */
    public static Equator<String> caseInsensitive() {
        return CASE_INSENSITIVE_EQUATOR;
    }

    /**
     * 
     * @param function
     * @return 
     */
    public static Equator<String> caseInsensitive(final Function<String, String> function) {
        return of(function, CASE_INSENSITIVE_EQUATOR);
    }

    /**
     * 
     * @return 
     */
    public static Equator<String> ignoreCase() {
        return CASE_INSENSITIVE_EQUATOR;
    }

    /**
     * 
     * @param function
     * @return 
     */
    public static Equator<String> ignoreCase(final Function<String, String> function) {
        return of(function, CASE_INSENSITIVE_EQUATOR);
    }

    /**
     * 
     */
    final private static Equator<String> CASE_SENSITIVE_EQUATOR = DefaultEquator.INSTANCE;
    
    /**
     * 
     */
    final private static Equator<String> CASE_INSENSITIVE_EQUATOR = new Equator<String>() {
        @Override
        public boolean equate(final String o1, final String o2) {
            if (o1 == o2) {
                return true;
            }
            return o1 != null && o1.equalsIgnoreCase(o2);
        }

        @Override
        public int hash(final String o) {
            final String s = o == null ? null : o.toString();
            final String lower = s == null ? null : s.toLowerCase();
            return Objects.hashCode(lower);
        }
    };

    /**
     * 
     * @param equator
     * @return 
     */
    public static Equator<String> nonNullEquator(final Equator<String> equator) {
        return equator == null ? DefaultEquator.defaultEquator() : equator;
    }
    
}
