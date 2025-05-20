/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.lang;

import java.util.Objects;
import java.util.function.Consumer;

/**
 *
 * @author AndreaR
 */
public class AutoCloseables {
    
    /**
     * 
     * @param autoCloseable
     * @return 
     */
    public static AutoCloseable lenient(final AutoCloseable autoCloseable) {
        return lenient(autoCloseable, null);
    };
    
    /**
     * 
     * @param autoCloseable
     * @param c
     * @return 
     */
    public static AutoCloseable lenient(final AutoCloseable autoCloseable,
            final Consumer<? super Exception> c) {
        Objects.requireNonNull(autoCloseable, "AutoCloseable");
        return () -> { 
            try {
                autoCloseable.close();
            } catch (final Exception e) {
                if (c != null) {
                    c.accept(e);
                }
            }
        };
    };

}
