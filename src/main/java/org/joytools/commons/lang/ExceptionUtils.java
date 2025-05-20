/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.google.common.reflect.ClassPath;
import io.vavr.collection.Seq;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import org.apache.commons.lang3.concurrent.ConcurrentException;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.concurrent.AtomicSuppliers;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.function.Predicates;
import org.joytools.commons.interop.Java;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class ExceptionUtils extends org.apache.commons.lang3.exception.ExceptionUtils {
    
    /**
     * 
     */
    public ExceptionUtils() {
    }
    
    /**
     * 
     * @param <R>
     * @param t
     * @return
     * @throws Exception 
     */
    @Deprecated
    public static <R> R old_throwUnwrapped(final Throwable t) throws Exception {
        Throwable throwable = t;
        if (throwable instanceof Error err) {
            throw err;
        }
        if (throwable instanceof RuntimeException re) {
            throw unwrapped(re);
        }
        if (throwable instanceof Exception ex) {
            throw unwrapped(ex);
        }
        throw new UnsupportedOperationException("Unknown throable type: " + throwable.getClass().getName());
    }

    /**
     * 
     * @param <R>
     * @param t
     * @return
     * @throws Exception 
     */
    @Deprecated
    public static <R> R old_throwChecked(final Throwable t) throws Exception {
        Objects.requireNonNull(t, "Throwable");
        if (t instanceof Error err) {
            throw err;
        }
        if (t instanceof RuntimeException re) {
            throw re;
        }
        if (t instanceof Exception ex) {
            throw ex;
        }
        throw new IllegalArgumentException("Unknown throwable type: " + t.getClass().getName());
    }

    /**
     * 
     * @param <R>
     * @param t
     * @return
     * @throws Exception 
     */
    @Deprecated
    public static <R> R old_throwCheckedOrInterrupt(final Throwable t) throws Exception {
        Objects.requireNonNull(t, "Throwable");
        if (t instanceof Error err) {
            throw err;
        }
        if (t instanceof RuntimeException re) {
            throw re;
        }
        if (t instanceof InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw ie;
        }
        if (t instanceof Exception ex) {
            throw ex;
        }
        throw new IllegalArgumentException("Unknown throwable type: " + t.getClass().getName());
    }
    
    /**
     * 
     * @param t
     * @return 
     */
    public static Throwable unwrapped(final Throwable t) {
        if (t instanceof Exception ex) {
            return unwrapped(ex);
        }
        return t;
    }

    /**
     * 
     * @param e
     * @return 
     */
    public static Exception unwrapped(final Exception e) {
        final Throwable t = e.getCause();
        if (t != null && t instanceof Exception cause) {
            final Class clazz = e.getClass();
            if (e instanceof UndeclaredThrowableException
                    || clazz.getSimpleName().startsWith("Unchecked")
                    || e instanceof ExecutionException
                    || e instanceof ConcurrentException 
                    || e instanceof InvocationTargetException
                    || clazz.isAssignableFrom(RuntimeException.class)) {
                return unwrapped(cause);
            }
        }
        return e;
    }

    /**
     * 
     * @param cause
     * @return 
     */
    @Deprecated
    private static RuntimeException old_toRuntimeException(final Exception cause) {
        return old_toRuntimeException(cause, null);
    }

    /**
     * 
     * @param msg
     * @param cause
     * @return 
     */
    @Deprecated
    private static RuntimeException old_toRuntimeException(final String msg, 
            final Exception cause) {
        return old_toRuntimeException(cause, msg);
    }

    /**
     * 
     * @param cause
     * @param msg
     * @return 
     */
    @Deprecated
    private static RuntimeException old_toRuntimeException(final Exception cause,
            final String msg) {
        Objects.requireNonNull(cause, "Cause could not be null");
        if (cause instanceof RuntimeException re) {
            return re;
        }
        final String name = cause.getClass().getSimpleName();
        if (!name.endsWith(OLD_EXCEPTION)) {
            return old_newWrappedCheckedException(msg, cause);
        }
        final String newName = StringUtils.substring(name, 0, name.length() - OLD_EXCEPTION.length()) + "RuntimeException";
        for (final Class rtClass : old_runtimeNestedExceptions().get(newName)) {
            if (msg == null) {
                try {
                    final Constructor c = rtClass.getConstructor(cause.getClass());
                    return WrappedCheckedException.class.cast(c.newInstance(cause));
                } catch (final Exception ex) {
                }
            } else {
                try {
                    final Constructor c = rtClass.getConstructor(String.class, cause.getClass());
                    return WrappedCheckedException.class.cast(c.newInstance(msg, cause));
                } catch (final Exception ex) {
                }
            }
        }
        return old_newWrappedCheckedException(msg, cause);
    }
    
    /**
     * 
     * @param msg
     * @param cause 
     * @return  
     */
    @Deprecated
    protected static WrappedCheckedException old_newWrappedCheckedException(final String msg, final Exception cause) {
        if (StringUtils.isEmpty(msg)) {
            return new WrappedCheckedException(cause);
        } else {
            return new WrappedCheckedException(msg, cause);
        }
    }

    /**
     * 
     * @param <T>
     * @param t
     * @param type1
     * @throws T 
     */
    @Deprecated
    public static <T extends Throwable> void old_throwWhenTypeOf(
            final Throwable t,
            final Class<T> type1) throws T {
        if (t == null) {
            return;
        }
        if (type1.isInstance(t)) {
            throw type1.cast(t);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param t
     * @param type1
     * @param type2
     * @throws T1
     * @throws T2 
     */
    @Deprecated
    public static <T1 extends Throwable, T2 extends Throwable> void old_throwWhenTypeOf(
            final Throwable t,
            final Class<T1> type1,
            final Class<T2> type2) throws T1, T2 {
        if (t == null) {
            return;
        }
        if (type1.isInstance(t)) {
            throw type1.cast(t);
        }
        if (type2.isInstance(t)) {
            throw type2.cast(t);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param t
     * @param type1
     * @param type2
     * @param type3
     * @throws T1
     * @throws T2
     * @throws T3 
     */
    @Deprecated
    public static <T1 extends Throwable, T2 extends Throwable, T3 extends Throwable> void old_throwWhenTypeOf(
            final Throwable t,
            final Class<T1> type1,
            final Class<T2> type2,
            final Class<T3> type3) throws T1, T2, T3 {
        if (t == null) {
            return;
        }
        if (type1.isInstance(t)) {
            throw type1.cast(t);
        }
        if (type2.isInstance(t)) {
            throw type2.cast(t);
        }
        if (type3.isInstance(t)) {
            throw type3.cast(t);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param t
     * @param type1
     * @param type2
     * @param type3
     * @param type4
     * @throws T1
     * @throws T2
     * @throws T3
     * @throws T4 
     */
    @Deprecated
    public static <T1 extends Throwable, T2 extends Throwable, T3 extends Throwable, T4 extends Throwable> void old_throwWhenTypeOf(
            final Throwable t,
            final Class<T1> type1,
            final Class<T2> type2,
            final Class<T3> type3,
            final Class<T4> type4) throws T1, T2, T3, T4 {
        if (t == null) {
            return;
        }
        if (type1.isInstance(t)) {
            throw type1.cast(t);
        }
        if (type2.isInstance(t)) {
            throw type2.cast(t);
        }
        if (type3.isInstance(t)) {
            throw type3.cast(t);
        }
        if (type4.isInstance(t)) {
            throw type4.cast(t);
        }
    }

    /**
     * 
     * @param <T>
     * @param chain
     * @param type1
     * @throws T 
     */
    @Deprecated
    public static <T extends Throwable> void old_throwFirstTypeOf(
            final Throwable chain,
            final Class<T> type1) throws T {
        for (final Throwable t : getThrowableList(chain)) {
            ExceptionUtils.old_throwWhenTypeOf(t, type1);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param chain
     * @param type1
     * @param type2
     * @throws T1
     * @throws T2 
     */
    @Deprecated
    public static <T1 extends Throwable, T2 extends Throwable> void old_throwFirstTypeOf(
            final Throwable chain,
            final Class<T1> type1,
            final Class<T2> type2) throws T1, T2 {
        for (final Throwable t : getThrowableList(chain)) {
            old_throwWhenTypeOf(t, type1, type2);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param chain
     * @param type1
     * @param type2
     * @param type3
     * @throws T1
     * @throws T2
     * @throws T3 
     */
    @Deprecated
    public static <T1 extends Throwable, T2 extends Throwable, T3 extends Throwable> void old_throwFirstTypeOf(
            final Throwable chain,
            final Class<T1> type1,
            final Class<T2> type2,
            final Class<T3> type3) throws T1, T2, T3 {
        for (final Throwable t : getThrowableList(chain)) {
            ExceptionUtils.old_throwWhenTypeOf(t, type1, type2, type3);
        }
    }

    /**
     * 
     * @param <T1>
     * @param <T2>
     * @param <T3>
     * @param <T4>
     * @param chain
     * @param type1
     * @param type2
     * @param type3
     * @param type4
     * @throws T1
     * @throws T2
     * @throws T3
     * @throws T4 
     */
    @Deprecated
    public static <T1 extends Throwable, T2 extends Throwable, T3 extends Throwable, T4 extends Throwable> void old_throwFirstTypeOf(
            final Throwable chain,
            final Class<T1> type1,
            final Class<T2> type2,
            final Class<T3> type3,
            final Class<T4> type4) throws T1, T2, T3, T4 {
        for (final Throwable t : getThrowableList(chain)) {
            ExceptionUtils.old_throwWhenTypeOf(t, type1, type2, type3, type4);
        }
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param exceptions
     * @return 
     * @throws Exception 
     */
    public static <T extends Throwable, R> R throwMultipleException(final List<T> exceptions) throws Exception {
        return throwMultipleException(IterableAccessors.ofAll(exceptions));
    }
    
    /**
     * 
     * @param <T>
     * @param <R>
     * @param exceptions
     * @return
     * @throws Exception 
     */
    public static <T extends Throwable, R> R throwMultipleException(final IterableAccessor<T> exceptions) throws Exception {
        if (exceptions.isEmpty()) {
            return (R)null;
        }
        final List<T> list = exceptions.to(Java.arrayList());
        if (exceptions.size() == 1) {
            final Throwable t = list.get(0);
            if (t instanceof Error err) {
                throw err;
            }
            if (t instanceof Exception ex) {
                throw ex;
            }
            // Should never happen
            throw new Exception(t);
        }
        final MultipleException ex = new MultipleException(list);
        ex.initCause(list.get(0));
        throw ex;
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param exceptions
     * @return
     * @throws Exception 
     */
    public static <T extends Throwable, R> R throwMultipleException(final Seq<T> exceptions) throws Exception {
        return throwMultipleException(IterableAccessors.ofAll(exceptions));
    }

    /**
     * 
     * @param <T>
     * @param <R>
     * @param exceptions
     * @return
     * @throws Exception 
     */
    public static <T extends Throwable, R> R throwMultipleException(final T... exceptions) throws Exception {
        return throwMultipleException(IterableAccessors.of(exceptions));
    }

    /**
     * 
     */
    @Deprecated
    private final static String OLD_EXCEPTION = "Exception";
    
    /**
     * 
     * @return 
     */
    @Deprecated
    protected static ListMultimap<String, Class> old_runtimeNestedExceptions() {
        return OLD_RUNTIME_NESTED_EXCEPTIONS.get();
    }
            
    /**
     * 
     */
    @Deprecated
    final static ListMultimap<String, Class> OLD_RUNTIME_NESTED_EXCEPTIONS_EMPTY = 
            Multimaps.unmodifiableListMultimap(Multimaps.newListMultimap(new TreeMap(), ArrayList::new));
    
    /**
     * 
     */
    @Deprecated
    private final static Supplier<ListMultimap<String, Class>> OLD_RUNTIME_NESTED_EXCEPTIONS = AtomicSuppliers.ofChecked(() -> {
        final ListMultimap<String, Class> m = Multimaps.newListMultimap(new TreeMap(), ArrayList::new);
        try {
            IterableAccessors.from(ClassPath.from(Thread.currentThread().getContextClassLoader()).getAllClasses())
                    .map(ExceptionUtils::old_toRuntimeNestedException)
                    .omit(Predicates.isNull())
                    .forEach((c) -> { 
                        m.put(c.getSimpleName(), c); 
                    } );
        } catch (final IOException ex) {
        }
        m.keySet().forEach((i) -> { 
            final List values = m.get(i);
            if (values.size() > 1) {
                System.out.println(i + ": " + values); 
            }
        } );
        return Multimaps.unmodifiableListMultimap(m); 
    } );

    /**
     * 
     * @param info
     * @return 
     */
    @Deprecated
    protected static Class old_toRuntimeNestedException(final ClassPath.ClassInfo info) {
        final String className = info.getName();
        if (!StringUtils.endsWith(className, OLD_EXCEPTION)) {
            return null;
        }
        Class clazz;
        try {
            clazz = Class.forName(className);
        } catch (final Throwable t) {
            return null;
        } 
        if (!WrappedCheckedException.class.isAssignableFrom(clazz)) {
            return null;
        }
        // ClassUtils.hierarchy(clazz)
        //return clazz. .isAssignableFrom(RuntimeException.class);
        return clazz;
    }
    
    public static void main(final String... args) {
        final List<Exception> tr = Arrays.asList(
                new UndeclaredThrowableException(new IOException()),
                new IOException(),
                new UncheckedIOException(new IOException()),
                new RuntimeException(new IllegalArgumentException()),
                new IllegalArgumentException(new IllegalArgumentException()),
                new IllegalStateException(new IllegalArgumentException())
        );
        for (final Exception t : tr) {
            System.out.println(t);
            System.out.println("  Unwrapped ==> " + unwrapped(t));
        }
    }
    
}
