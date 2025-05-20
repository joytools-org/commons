/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.text;

import io.vavr.Value;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import org.apache.commons.text.lookup.StringLookup;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class StringLookups {

    /**
     * 
     * @param resolver
     * @param propName
     * @return 
     */
    static String toLookupString(final String propName, final Function<String, ?> resolver) {
        if (resolver == null || StringUtils.isEmpty(propName)) {
            return null;
        }
        final Object value = resolver.apply(propName);
        return toLookupString(value);
    }
    
    /**
     * 
     * @param obj
     * @return 
     */
    static String toLookupString(final Object obj) {
        Object ret = obj;
        while (true) {
            if (ret == null) {
                return null;
            }
            if (ret instanceof CharSequence cs) {
                return cs.toString();
            }
            /* if (ret instanceof 0ption) {
                final Option v = Option.class.cast(ret);
                if (v.isEmpty()) {
                    return null;
                }
                ret = v.get();
                if (ret == null) {
                    return org.apache.commons.lang3.StringUtils.EMPTY;
                } 
            } else 
            if (ret instanceof Either) {
                final Either e = Either.class.cast(ret);
                if (e.isEmpty()) {
                    return null;
                }
                ret = e.get();
                if (ret == null) {
                    return org.apache.commons.lang3.StringUtils.EMPTY;
                } 
            } else */
            if (ret instanceof Value v) {
                if (v.isSingleValued()) {
                    if (v.isEmpty()) {
                        return null;
                    } else {
                        ret = v.get();
                        if (ret == null) {
                            return org.apache.commons.lang3.StringUtils.EMPTY;
                        }
                    }
                } else {
                    ret = ret.toString();
                }
            } else 
            if (ret instanceof Optional v) {
                if (!v.isPresent()) {
                    return null;
                }
                ret = v.get();
            } else {
                return ret.toString();
            }
        }
    };

    /**
     * 
     * @param <T>
     * @param f
     * @return 
     */
    static <T> StringLookup ofFunction(final Function<String, T> f) {
        Objects.requireNonNull(f, "Function");
        return s -> {
            final T res = f.apply(s);
            return toLookupString(res);
        };
    }

    /**
     * 
     * @param <T>
     * @param func
     * @return 
     */
    public static <T> StringLookup ofOption(final Function<String, Option<T>> func) {
        Objects.requireNonNull(func, "Function");
        return s -> {
            final Option<T> opt = func.apply(s);
            if (opt.isEmpty()) {
                return null;
            }
            final Object obj = Objects.requireNonNull(opt.get(), "Option contains a null value");
            return StringUtils.toString(obj);
        };
    }
    
    /**
     * 
     * @param <T>
     * @param func
     * @return 
     */
    public static <T> StringLookup ofOptional(final Function<String, Optional<T>> func) {
        Objects.requireNonNull(func, "Function");
        return s -> {
            final Optional<T> opt = func.apply(s);
            if (opt.isPresent()) {
                return StringUtils.toString(opt.get());
            }
            return null;
        };
    }

    /*
    public static void main(final String... args) {
        final Either<Integer, String> e1 = Left(1);
        final Either<Integer, String> e2 = Right("Ciao");
        final Option<String> o1 = None();
        final Option<String> o2 = Some("Ciao");
        final Option<String> o3 = Some(null);
        final Optional<String> op1 = Optional.empty();
        final Optional<String> op2 = Optional.of("Ciao");
        final Vector<String> v1 = Vector.empty();
        final Vector<String> v2 = Vector.of("Ciao");
        final Vector<String> v3 = Vector.of("Ciao1", "Ciao2");
        for (final Object o : List.of(null, "Ciao", 1, e1, e2, o1, o2, o3, op1, op2, v1, v2, v3)) {
            System.out.println(o);
            System.out.println("  " + toLookupString(o));
        }
    } */
    
}
