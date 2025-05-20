/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkArgument;
import java.util.function.Predicate;
import org.joytools.commons.function.Predicates;

/**
 *
 * @author AndreaR
 */
@Deprecated
class OldShutdownPolicies {
    
    public static <V> Predicate<BasicComposedResult<V>> onSuccess() {
        return (Predicate<BasicComposedResult<V>>)((Predicate)ON_SUCCESS);
    }
    
    public static <V> Predicate<BasicComposedResult<V>> onSuccess(final int count) {
        checkArgument(count > 0, "Count must be positive");
        return r -> r.succeededSeq().size() >= count;
    }

    public static <V> Predicate<BasicComposedResult<V>> onFailure() {
        return (Predicate<BasicComposedResult<V>>)((Predicate)ON_FAILURE);
    }

    public static <V> Predicate<BasicComposedResult<V>> onFailure(final int count) {
        checkArgument(count > 0, "Count must be positive");
        return r -> r.failedSeq().size() >= count;
    }

    public static <V> Predicate<BasicComposedResult<V>> onCompletion() {
        return Predicates.alwaysFalse();
    }
    
    public static <V> Predicate<BasicComposedResult<V>> onCompletion(final int count) {
        checkArgument(count > 0, "Count must be positive");
        return r -> r.entriesSeq().size() >= count;
    }

    private final static Predicate<BasicComposedResult<?>> ON_SUCCESS = r -> r.isAnySucceeded();

    private final static Predicate<BasicComposedResult<?>> ON_FAILURE = r -> r.isAnyFailed();

}
