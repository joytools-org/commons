/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import org.apache.commons.lang3.function.FailableSupplier;

/**
 *
 * @author AndreaR
 */
class OldAtomicSuppliers {
    
    /**
     * 
     * @param <E>
     * @param supplier
     * @return 
     */
    public static <E> Supplier<E> of(final Supplier<E> supplier) {
        Objects.requireNonNull(supplier, "Supplier");
        return new OldAtomicSupplier<E>() {
            @Override
            protected E initialize() throws Exception {
                return supplier.get();
            }            
        };
    }

    /**
     * 
     * @param <E>
     * @param callable
     * @return 
     */
    public static <E> Supplier<E> ofChecked(final Callable<E> callable) {
        Objects.requireNonNull(callable, "Callable");
        return new OldAtomicSupplier<E>() {
            @Override
            protected E initialize() throws Exception {
                return callable.call();
            }            
        };
    }
        
    /**
     * 
     * @param <E>
     * @param <T>
     * @param supplier
     * @return 
     */
    public static <E, T extends Exception> Supplier<E> ofFailable(final FailableSupplier<E, T> supplier) {
        Objects.requireNonNull(supplier, "Supplier");
        return new OldAtomicSupplier<E>() {
            @Override
            protected E initialize() throws Exception {
                return supplier.get();
            }            
        };
    }

}
