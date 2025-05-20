/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkArgument;
import io.vavr.collection.Seq;
import java.util.function.Function;
import java.util.function.Predicate;
import org.joytools.commons.lang.MultipleException;

/**
 *
 * @author AndreaR
 */
@Deprecated
class OldThrowPolicies {
    
    public static <V> Predicate<BasicComposedResult<V>> isAnyFailed() {
        return (Predicate<BasicComposedResult<V>>)((Predicate)IS_ANY_FAILED);
    }

    public static <V> Predicate<BasicComposedResult<V>> isAtLeastFailed(final int count) {
        checkArgument(count > 0, "Count must be positive");
        return r -> r.failedSeq().size() >= count;
    }

    public static <V> Predicate<BasicComposedResult<V>> isAllFailed() {
        return (Predicate<BasicComposedResult<V>>)((Predicate)IS_ALL_FAILED);
    }

    public static <V> Function<BasicComposedResult<V>, Exception> firstException() {
        return (Function<BasicComposedResult<V>, Exception>)((Function)FIRST_EXCEPTION);
    }

    public static <V> Function<BasicComposedResult<V>, Exception> allExceptions() {
        return (Function<BasicComposedResult<V>, Exception>)((Function)ALL_EXCEPTIONS);
    }

    private final static Predicate<BasicComposedResult<?>> IS_ANY_FAILED = r -> r.isAnyFailed();

    private final static Predicate<BasicComposedResult<?>> IS_ALL_FAILED = r -> r.isAllFailed();

    private final static Function<BasicComposedResult<?>, Exception> FIRST_EXCEPTION = r -> r.failedSeq().get();

    private final static Function<BasicComposedResult<?>, Exception> ALL_EXCEPTIONS = r -> {
        final Seq<Exception> failed = r.failedSeq();
        if (failed.size() == 1) {
            return failed.get(0);
        }
        return new MultipleException(failed.asJava());
    };

}
