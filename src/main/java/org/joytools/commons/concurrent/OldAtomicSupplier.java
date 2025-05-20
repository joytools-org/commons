/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static io.vavr.API.Left;
import static io.vavr.API.Right;
import io.vavr.control.Either;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.apache.commons.lang3.concurrent.ConcurrentException;

/**
 * <p>
 * A specialized implementation of the {@code ConcurrentInitializer} interface
 * based on an {@link AtomicReference} variable.
 * </p>
 * <p>
 * This class maintains a member field of type {@code AtomicReference}. It
 * implements the following algorithm to create and initialize an object in its
 * {@link #getEither()} method:
 * </p>
 * <ul>
 * <li>First it is checked whether the {@code AtomicReference} variable contains
 * already a value. If this is the case, the value is directly returned.</li>
 * <li>Otherwise the {@link #buildEither()} method is called. This method must be
 * defined in concrete subclasses to actually create the managed object.</li>
 * <li>After the object was created by {@link #buildEither()} it is checked
 * whether the {@code AtomicReference} variable is still undefined. This has to
 * be done because in the meantime another thread may have initialized the
 * object. If the reference is still empty, the newly created object is stored
 * in it and returned by this method.</li>
 * <li>Otherwise the value stored in the {@code AtomicReference} is returned.</li>
 * </ul>
 * <p>
 * Because atomic variables are used this class does not need any
 * synchronization. So there is no danger of deadlock, and access to the managed
 * object is efficient. However, if multiple threads access the {@code
 * AtomicInitializer} object before it has been initialized almost at the same
 * time, it can happen that {@link #buildEither()} is called multiple times. The
 * algorithm outlined above guarantees that {@link #getEither()} always returns the
 * same object though.
 * </p>
 * <p>
 * Compared with the {@link LazyInitializer} class, this class can be more
 * efficient because it does not need synchronization. The drawback is that the
 * {@link #buildEither()} method can be called multiple times which may be
 * problematic if the creation of the managed object is expensive. As a rule of
 * thumb this initializer implementation is preferable if there are not too many
 * threads involved and the probability that multiple threads access an
 * uninitialized object is small. If there is high parallelism,
 * {@link LazyInitializer} is more appropriate.
 * </p>
 *
 * @since 3.0
 * @param <T> the type of the object managed by this initializer class
 */
abstract class OldAtomicSupplier<T> implements Supplier<T> { // , Callable<T> {

    private final AtomicReference<OldAtomicSupplier<T>> factory = new AtomicReference<>();

    /** Holds the reference to the managed object. */
    private final AtomicReference<Either<Exception, T>> reference = new AtomicReference<>();
    
    /**
     * 
     * @return
     * @throws ConcurrentException 
     */ /*
    public T get() throws ConcurrentException {
        final Either<Exception, T> result = getEither();
        if (result.isRight()) {
            return result.get();
        }
        final Exception ex = result.getLeft();
        if (ex instanceof ConcurrentException) {
            throw ConcurrentException.class.cast(ex);
        }
        if (ex instanceof Exception) {
            throw new ConcurrentException(Exception.class.cast(ex));
        }
        throw new ConcurrentException(new Exception(ex));
    } */

    /**
     * Returns the object managed by this initializer. The object is created if
     * it is not available yet and stored internally. This method always returns
     * the same object.
     *
     * @return the object created by this {@code AtomicInitializer}
     */
    // @Override
    public final Either<Exception, T> getEither() {
        Either<Exception, T> result;

        while ((result = reference.get()) == null) {
            if (factory.compareAndSet(null, this)) {
                reference.set(buildEither());
            }
        }

        return result;
    }

    /**
     * 
     * @return 
     */
    @Override
    public final T get() {
        final Either<Exception, T> result = getEither();
        if (result.isRight()) {
            return result.get();
        }
        throw new IllegalStateException("Initialization failed", result.getLeft());
    }

    /**
     * 
     * @return
     * @throws Exception 
     */ /*
    public final T getOrFail() throws Exception {
        final Either<Exception, T> result = getEither();
        if (result.isRight()) {
            return result.get();
        }
        throw result.getLeft();
    } */

    /**
     * 
     * @return
     * @throws Exception 
     */
    // @Override 
    /* public final T call() throws Exception {
        return getOrFail();
    } */

    /**
     * 
     * @return 
     */
    /* public final Supplier<T> unchecked() {
        return unchecked;
    } */
            
    /**
     * 
     */ /*
    private final Supplier<T> unchecked = () -> {
        final Either<Exception, T> result = getEither();
        return result.isRight() ? result.get() : ExceptionUtils.wrapAndThrow(result.getLeft());
    }; */
    
    /**
     * 
     * @return 
     */
    final protected Either<Exception, T> buildEither() {
        try {
            return Right(Objects.requireNonNull(initialize(), "Initializer returned a null object"));
        } catch (final Exception e) {
            return Left(e);
        }
    }

    /**
     * Creates and initializes the object managed by this {@code
     * AtomicInitializer}. This method is called by {@link #getEither()} when the
     * managed object is not available yet. An implementation can focus on the
     * creation of the object. No synchronization is needed, as this is already
     * handled by {@code get()}. As stated by the class comment, it is possible
     * that this method is called multiple times.
     *
     * @return the managed data object
     * @throws ConcurrentException if an error occurs during object creation
     */
    protected abstract T initialize() throws Exception;
    
}
