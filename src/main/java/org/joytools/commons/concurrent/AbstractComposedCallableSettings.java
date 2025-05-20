/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 */
abstract interface AbstractComposedCallableSettings<K, V> {
    
    default Integer maxThreads() {
        return null;
    }

    default TimeValueUnit timeout() {
        return null;
    }
    
    default Predicate<ComposedResult<K, V>> shutdownPolicy() {
        return ShutdownPolicies.onCompletion();
    }

    default TimeValueUnit shutdownTimeout() {
        return null;
    }
    
    default boolean completionOrder() {
        return false;
    }

    default Predicate<ComposedResult<K, V>> throwPolicy() {
        return ShutdownPolicies.onFailure();
    }

    default Function<ComposedResult<K, V>, Exception> throwPolicyMapper() {
        return null;
    }

}
