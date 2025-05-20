/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import java.io.Closeable;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;
import org.apache.commons.lang3.concurrent.AtomicInitializer;
import org.apache.commons.lang3.concurrent.AtomicSafeInitializer;
import org.apache.commons.lang3.concurrent.BackgroundInitializer;
import org.apache.commons.lang3.function.FailableConsumer;
import org.apache.commons.lang3.function.FailableSupplier;
import org.apache.commons.lang3.reflect.MethodUtils;
import static org.joytools.commons.concurrent.AtomicUtils.defaultCloser;
import org.joytools.commons.lang.ObjectUtils;

/**
 *
 * @author AndreaR
 */
public class AtomicInitializers {
    
    /**
     * 
     * @param <T>
     * @param supplier
     * @return 
     */
    public static <T> AtomicInitializer<T> of(final Supplier<T> supplier) {
        Objects.requireNonNull(supplier, "Supplier");
        return AtomicInitializer.<T>builder()
                .setInitializer(supplier::get)
                .setCloser(defaultCloser())
                .get();
    }
    
    /**
     * 
     * @param <T>
     * @param failable
     * @return 
     */
    public static <T> AtomicInitializer<T> ofFailable(final FailableSupplier<T, ? extends Exception> failable) {
        Objects.requireNonNull(failable, "FailableSupplier");
        return AtomicInitializer.<T>builder()
                .setInitializer(failable)
                .setCloser(defaultCloser())
                .get();
    }

    /**
     * 
     * @param <T>
     * @param callable
     * @return 
     */
    public static <T> AtomicInitializer<T> ofChecked(final Callable<T> callable) {
        Objects.requireNonNull(callable, "Supplier");
        return AtomicInitializer.<T>builder()
                .setInitializer(callable::call)
                .setCloser(defaultCloser())
                .get();
    }
    
    
}
