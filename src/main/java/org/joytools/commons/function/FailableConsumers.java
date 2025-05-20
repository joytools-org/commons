/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.function;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.function.FailableConsumer;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.lang.MultipleException;
import org.joytools.commons.util.AbstractFailableOpenableConsumer;
import org.joytools.commons.util.FailableOpenable;

/**
 *
 * @author AndreaR
 */
public class FailableConsumers {
    
    /**
     * 
     * @param <T>
     * @param <R>
     * @param <E>
     * @param processor
     * @return 
     */
    public static <T, R, E extends Exception> FailableConsumer<T, E> of(final FailableProcessor<T, R, E> processor) {
        Objects.requireNonNull(processor, "Processor");
        if (processor instanceof FailableOpenable op) {
            if (!op.isOpenInvoked()) {
                return new AbstractFailableOpenableConsumer<T, E>() {
                    @Override
                    protected boolean isLazyOpen() {
                        return false;
                    }

                    @Override
                    protected void init() throws E {
                        dispose(op);
                    }

                    @Override
                    protected void consume(final T item) throws E {
                        processor.process(item);
                    }
                };
            }
        }
        return (x) -> {
            processor.process(x);
        };
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param executable
     * @return 
     */
    public static <T, E extends Throwable> FailableConsumer<T, E> lenient(final FailableConsumer<T, E> executable) {
        return lenient(executable, true);
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param executable
     * @param lenient
     * @return 
     */
    public static <T, E extends Throwable> FailableConsumer<T, E> lenient(final FailableConsumer<T, E> executable, boolean lenient) {
        Objects.requireNonNull(executable, "Effect");
        return (final T input) -> {
            if (lenient) {
                try {
                    executable.accept(input);
                } catch (final Exception e) {
                }
            } else {
                executable.accept(input);
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @param <E>
     * @param <C>
     * @param executable
     * @param exceptions
     * @return 
     */
    public static <T, E extends Exception, C extends Collection<? super Exception>> FailableConsumer<T, E> lenient(final FailableConsumer<T, E> executable, final C exceptions) {
        Objects.requireNonNull(executable, "Effect");
        return (final T input) -> {
            if (exceptions != null) {
                try {
                    executable.accept(input);
                } catch (final Exception e) {
                    exceptions.add(e);
                }
            } else {
                executable.accept(input);
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @param <E>
     * @param keepGoing
     * @param executables
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, Exception> forIterable(
            final boolean keepGoing,
            final Iterable<FailableConsumer<T, E>> executables) {
        Objects.requireNonNull(executables, "FailableConsumers");
        return x -> {
            final List<Throwable> exceptions = new ArrayList();
            for (final FailableConsumer<T, E> exec : executables) {
                if (keepGoing) {
                    try {
                        exec.accept(x);
                    } catch (final Exception e) {
                        if (e instanceof MultipleException me) {
                            CollectionUtils.addAll(exceptions, me);
                        } else {
                            exceptions.add(e);
                        }
                    }
                } else {
                    exec.accept(x);
                }
            }
            ExceptionUtils.throwMultipleException(exceptions);
        };
    }
    
    /**
     * 
     * @param <T>
     * @param <E>
     * @param keepGoing
     * @param executables
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, Exception> forArray(
            final boolean keepGoing, 
            final FailableConsumer<T, E>... executables) {
        Objects.requireNonNull(executables, "FailableConsumers");
        return x -> {
            final List<Throwable> exceptions = new ArrayList();
            for (final FailableConsumer<T, E> exec : executables) {
                if (keepGoing) {
                    try {
                        exec.accept(x);
                    } catch (final Exception e) {
                        if (e instanceof MultipleException me) {
                            CollectionUtils.addAll(exceptions, me);
                        } else {
                            exceptions.add(e);
                        }
                    }
                } else {
                    exec.accept(x);
                }
            }
            ExceptionUtils.throwMultipleException(exceptions);
        };
    }
    
    /**
     * 
     * @param <T>
     * @param <E>
     * @param executables
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, Exception> sequential(
            final Iterable<FailableConsumer<T, E>> executables) {
        return forIterable(false, executables);
    }
    
    /**
     * 
     * @param <T>
     * @param <E>
     * @param executables
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, Exception> sequential(
            final FailableConsumer<T, E>... executables) {
        return forArray(false, executables);
    }
        
    /**
     * 
     * @param <T>
     * @param <E>
     * @param condition
     * @param ifTrue
     * @param ifFalse
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, E> iif(
            final Predicate<T> condition,
            final FailableConsumer<T, E> ifTrue, 
            final FailableConsumer<T, E> ifFalse) {
        Objects.requireNonNull(condition, "Condition");
        Objects.requireNonNull(ifTrue);
        Objects.requireNonNull(ifFalse);
        return x -> {
            if (condition.test(x)) {
                ifTrue.accept(x);
            } else {
                ifFalse.accept(x);
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param condition
     * @param ifTrue
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, E> iif(
            final Predicate<T> condition,
            final FailableConsumer<T, E> ifTrue) {
        return FailableConsumers.iif(condition, ifTrue, x -> {});
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param executables
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, Exception> keepGoing(
            final Iterable<FailableConsumer<T, E>> executables) {
        return forIterable(true, executables);
    }
    
    /**
     * 
     * @param <T>
     * @param <E>
     * @param executables
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, Exception> keepGoing(
            final FailableConsumer<T, E>... executables) {
        return forArray(true, executables);
    }
    
    /**
     * 
     * @param <T>
     * @param <E>
     * @param consumer
     * @param exceptions
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, E> catchAndContinue(
            final FailableConsumer<T, E> consumer,
            final List<? super Exception> exceptions) {
        Objects.requireNonNull(consumer, "Consumer");
        return x -> {
            try {
                consumer.accept(x);
            } catch(final Exception e) {
                exceptions.add(e);
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param <E>
     * @param consumer
     * @param exceptions
     * @return 
     */
    public static <T, E extends Exception> FailableConsumer<T, E> catchAndRethrow(
            final FailableConsumer<T, E> consumer,
            final List<? super Exception> exceptions) {
        Objects.requireNonNull(consumer, "Consumer");
        return x -> {
            try {
                consumer.accept(x);
            } catch(final Exception e) {
                exceptions.add(e);
                throw e;
            }
        };
    }

}
