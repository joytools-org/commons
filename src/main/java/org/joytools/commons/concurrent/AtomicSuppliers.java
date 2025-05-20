/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.util.concurrent.Callable;
import java.util.function.Supplier;
import org.apache.commons.lang3.function.FailableSupplier;
import static org.joytools.commons.concurrent.AtomicUtils.toEither;
import static org.joytools.commons.concurrent.AtomicUtils.toSupplier;

/**
 *
 * @author AndreaR
 */
public class AtomicSuppliers {
    
    /**
     * 
     * @param <T>
     * @param supplier
     * @return 
     */
    public static <T> Supplier<T> of(final Supplier<T> supplier) {
        return toSupplier(AtomicInitializers.of(toEither(supplier)));
    }
    
    /**
     * 
     * @param <T>
     * @param callable
     * @return 
     */
    public static <T> Supplier<T> ofChecked(final Callable<T> callable) {
        return toSupplier(AtomicInitializers.ofChecked(toEither(callable)));
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param failableSupplier
     * @return 
     */
    public static <T, E extends Exception> Supplier<T> ofFailable(final FailableSupplier<T, E> failableSupplier) {
        return toSupplier(AtomicInitializers.ofFailable(toEither(failableSupplier)));
    }


    

}

