/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import org.apache.commons.lang3.concurrent.BackgroundInitializer;
import org.apache.commons.lang3.function.FailableSupplier;
import static org.joytools.commons.concurrent.AtomicUtils.defaultCloser;

/**
 *
 * @author AndreaR
 */
public class BackgroundInitializers {
    
    /**
     * 
     * @param <T>
     * @param supplier
     * @return 
     */
    public static <T> BackgroundInitializer<T> of(final Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "Supplier");
        final var init = BackgroundInitializer.<T>builder()
                .setInitializer(supplier::get)
                .setCloser(defaultCloser())
                .get();
        init.start();
        return init;
    }
    
    /**
     * 
     * @param <T>
     * @param failable
     * @return 
     */
    public static <T> BackgroundInitializer<T> ofFailable(final FailableSupplier<T, ? extends Exception> failable) {
        Objects.requireNonNull(failable, "FailableSupplier");
        final var init = BackgroundInitializer.<T>builder()
                .setInitializer(failable)
                .setCloser(defaultCloser())
                .get();
        init.start();
        return init;
    }

    /**
     * 
     * @param <T>
     * @param callable
     * @return 
     */
    public static <T> BackgroundInitializer<T> ofChecked(final Callable<T> callable) {
        Objects.requireNonNull(callable, "Supplier");
        final var init = BackgroundInitializer.<T>builder()
                .setInitializer(callable::call)
                .setCloser(defaultCloser())
                .get();
        init.start();
        return init;
    }
    
}
