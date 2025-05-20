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
public class ShutdownPolicies {
    
    public static <K, V> Predicate<ComposedResult<K, V>> onSuccess() {
        return (Predicate<ComposedResult<K, V>>)((Predicate)ON_SUCCESS);
    }
    
    public static <K, V> Predicate<ComposedResult<K, V>> onSuccess(final int count) {
        checkArgument(count > 0, "Count must be positive");
        return r -> r.succeededSeq().size() >= count;
    }

    public static <K, V> Predicate<ComposedResult<K, V>> onFailure() {
        return (Predicate<ComposedResult<K, V>>)((Predicate)ON_FAILURE);
    }

    public static <K, V> Predicate<ComposedResult<K, V>> onFailure(final int count) {
        checkArgument(count > 0, "Count must be positive");
        return r -> r.failedSeq().size() >= count;
    }

    public static <K, V> Predicate<ComposedResult<K, V>> onCompletion() {
        return Predicates.alwaysFalse();
    }
    
    public static <K, V> Predicate<ComposedResult<K, V>> onCompletion(final int count) {
        checkArgument(count > 0, "Count must be positive");
        return r -> r.entriesSeq().size() >= count;
    }

    private final static Predicate<ComposedResult<?, ?>> ON_SUCCESS = r -> r.isAnySucceeded();

    private final static Predicate<ComposedResult<?, ?>> ON_FAILURE = r -> r.isAnyFailed();

}
