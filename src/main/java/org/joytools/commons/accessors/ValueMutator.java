/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.control.Option;
import java.util.function.Function;
import java.util.function.Supplier;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullFunction;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullSupplier;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public interface ValueMutator<K, V> extends Mutator<V> {
    
    /**
     * 
     * @return 
     */
    default K label() {
        return null;
    }
    
    /**
     * 
     * @return 
     */
    default Option<V> get() {
        if (isEmpty()) {
            return None();
        }
        return Some(ValueMutator.this.getOrFail());
    }

    /**
     * 
     * @param <Z>
     * @param function
     * @return 
     */
    default <Z> Option<Z> get(final Function<? super V, ? extends Z> function) {
        if (isEmpty()) {
            return None();
        }
        return Some(nonNullFunction(function).apply(ValueMutator.this.getOrFail()));
    }

    /**
     * 
     * @return 
     */
    public V getOrFail();
    
    /**
     * 
     * @param <Z>
     * @param function
     * @return 
     */
    default <Z> Z getOrFail(final Function<? super V, ? extends Z> function) {
        final V v = ValueMutator.this.getOrFail();
        return nonNullFunction(function).apply(v);
    }

    /**
     * 
     * @return 
     */
    default V getOrNull() {
        if (isEmpty()) {
            return null;
        }
        return ValueMutator.this.getOrFail();
    }

    /**
     * 
     * @param defValue
     * @return 
     */
    default V getOrElse(final V defValue) {
        if (isEmpty()) {
            return null;
        }
        return ValueMutator.this.getOrFail();
    }

    /**
     * 
     * @param supplier
     * @return 
     */
    default V getOrElse(final Supplier<V> supplier) {
        nonNullSupplier(supplier);
        if (isEmpty()) {
            return null;
        }
        return ValueMutator.this.getOrFail();
    }
    
}
