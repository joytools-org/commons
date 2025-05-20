/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.util.Objects;
import java.util.function.Supplier;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.apache.commons.lang3.function.FailableSupplier;

/**
 *
 * @author AndreaR
 */
public class ConcurrentInitializers {
    
    /**
     * 
     * @param <T>
     * @param initializer
     * @return 
     */
    public static <T> Supplier<T> asSupplier(final FailableSupplier<T, ConcurrentException> initializer) {
        Objects.requireNonNull(initializer, "Initializer");
        return () -> {
            try {
                return initializer.get();
            } catch (final ConcurrentException ex) {
                throw new IllegalStateException(String.format("Initialization failed: %s", ex.getCause().getMessage(), ex.getCause()));
            }
        };
    }
    
}
