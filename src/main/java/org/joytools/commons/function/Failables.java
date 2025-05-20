/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.function;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Objects;
import java.util.function.Consumer;
import org.joytools.commons.lang.ExceptionUtils;

/**
 * This class provides utility functions, and classes for working with the {@code java.util.function} package, or more
 * generally, with Java 8 lambdas. More specifically, it attempts to address the fact that lambdas are supposed not to
 * throw Exceptions, at least not checked Exceptions, AKA instances of {@link Exception}. This enforces the use of
 * constructs like:
 *
 * <pre>
 * Consumer&lt;java.lang.reflect.Method&gt; consumer = m -&gt; {
 *     try {
 *         m.invoke(o, args);
 *     } catch (Throwable t) {
 *         throw Failable.rethrow(t);
 *     }
 * };
 * </pre>
 *
 * <p>
 * By replacing a {@link java.util.function.Consumer Consumer&lt;O&gt;} with a {@link FailableConsumer
 * FailableConsumer&lt;O,? extends Throwable&gt;}, this can be written like follows:
 * </p>
 *
 * <pre>
 * Functions.accept((m) -&gt; m.invoke(o, args));
 * </pre>
 *
 * <p>
 * Obviously, the second version is much more concise and the spirit of Lambda expressions is met better than the second
 * version.
 * </p>
 *
 * @since 3.11
 */
public abstract class Failables {

    /**
     * 
     */
    @Deprecated
    private final static Failable apache = new Failable() {
        @Override
        protected Consumer<Throwable> rethrowConsumer() {
            return APACHE_CONSUMER;
        }
    };
            
    /**
     * 
     */
    public final static Failable wrapped = new Failable() {
        @Override
        protected Consumer<Throwable> rethrowConsumer() {
            return WRAPPED_CONSUMER;
        }
    };

    /**
     * 
     */
    public final static Failable checked = new Failable() {
        @Override
        protected Consumer<Throwable> rethrowConsumer() {
            return CHECKED_CONSUMER;
        }
    };

    /**
     * 
     * @param rethrowConsumer
     * @return 
     */
    public static Failable on(final Consumer<Throwable> rethrowConsumer) {
        Objects.requireNonNull(rethrowConsumer, "Rethrow Consumer");
        return new Failable() {
            @Override
            protected Consumer<Throwable> rethrowConsumer() {
                return rethrowConsumer;
            }
        };
    }
    
    /**
     * 
     * @param throwable
     * @return 
     */
    @Deprecated
    static Throwable apacheRethrow(final Throwable throwable) {
        Objects.requireNonNull(throwable, "throwable");
        ExceptionUtils.throwUnchecked(throwable);
        if (throwable instanceof IOException) {
            throw new UncheckedIOException((IOException) throwable);
        }
        throw new UndeclaredThrowableException(throwable);
    }

    /**
     * 
     * @param throwable
     * @return 
     */
    static Throwable checkedRethrow(final Throwable throwable) {
        throw ExceptionUtils.asRuntimeException(Objects.requireNonNull(throwable, "throwable"));
    }

    /**
     * 
     * @param throwable
     * @return 
     */
    static Throwable wrappedRethrow(final Throwable throwable) {
        return ExceptionUtils.wrapAndThrow(Objects.requireNonNull(throwable, "throwable"));
    }

    @Deprecated
    private final static Consumer<Throwable> APACHE_CONSUMER = Failables::apacheRethrow;

    private final static Consumer<Throwable> WRAPPED_CONSUMER = Failables::wrappedRethrow;

    private final static Consumer<Throwable> CHECKED_CONSUMER = Failables::checkedRethrow;
    
}
