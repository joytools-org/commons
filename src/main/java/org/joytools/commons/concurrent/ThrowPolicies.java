/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkArgument;
import io.vavr.collection.Seq;
import java.util.function.Function;
import java.util.function.Predicate;
import org.joytools.commons.function.Predicates;
import org.joytools.commons.lang.MultipleException;

/**
 *
 * @author AndreaR
 */
public class ThrowPolicies {
    
    public static <K, V> Predicate<ComposedResult<K, V>> alwaysFalse() {
        return Predicates.alwaysFalse(); 
    }

    public static <K, V> Predicate<ComposedResult<K, V>> isAnyFailed() {
        return (Predicate<ComposedResult<K, V>>)((Predicate)IS_ANY_FAILED);
    }

    public static <K, V> Predicate<ComposedResult<K, V>> isAtLeastFailed(final int count) {
        checkArgument(count > 0, "Count must be positive");
        return r -> r.failedSeq().size() >= count;
    }

    public static <K, V> Predicate<ComposedResult<K, V>> isAllFailed() {
        return (Predicate<ComposedResult<K, V>>)((Predicate)IS_ALL_FAILED);
    }

    public static <K, V> Function<ComposedResult<K, V>, Exception> firstException() {
        return (Function<ComposedResult<K, V>, Exception>)((Function)FIRST_EXCEPTION);
    }

    public static <K, V> Function<ComposedResult<K, V>, Exception> allExceptions() {
        return (Function<ComposedResult<K, V>, Exception>)((Function)ALL_EXCEPTIONS);
    }

    private final static Predicate<ComposedResult<?, ?>> IS_ANY_FAILED = r -> r.isAnyFailed();

    private final static Predicate<ComposedResult<?, ?>> IS_ALL_FAILED = r -> r.isAllFailed();

    private final static Function<ComposedResult<?, ?>, Exception> FIRST_EXCEPTION = r -> r.failedSeq().get();

    private final static Function<ComposedResult<?, ?>, Exception> ALL_EXCEPTIONS = r -> {
        final Seq<Exception> failed = r.failedSeq();
        if (failed.size() == 1) {
            return failed.get(0);
        }
        return new MultipleException(failed.asJava());
    };

}
