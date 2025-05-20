/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.control.Option;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class ValueMutators {
    
    public static <K, V> ValueMutator<K, V> of(
            final K label, 
            final Option<V> opt) {
        Objects.requireNonNull(opt, "Option");
        return new ValueMutator<K, V>() {
            @Override
            public K label() {
                return label;
            }

            @Override
            public Option<V> get() {
                return opt;
            }

            @Override
            public V getOrFail() {
                return opt.get();
            }

            @Override
            public V getOrElse(final V other) {
                return opt.getOrElse(other);
            }

            @Override
            public V getOrElse(final Supplier<V> other) {
                return opt.getOrElse(other);
            }

            @Override
            public V getOrNull() {
                return opt.getOrNull();
            }

            @Override
            public <Z> Option<Z> get(final Function<? super V, ? extends Z> function) {
                return opt.map(function);
            }

            @Override
            public boolean isUpdatable() {
                return false;
            }

            @Override
            public boolean isEmpty() {
                return opt.isEmpty();
            }

            @Override
            public int size() {
                return opt.isEmpty() ? 0 : 1;
            }
        };
    }
    
}
