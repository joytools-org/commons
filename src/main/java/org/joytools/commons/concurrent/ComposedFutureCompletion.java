/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkState;
import com.google.common.collect.ImmutableList;
import static io.vavr.API.Left;
import static io.vavr.API.Right;
import io.vavr.control.Either;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Predicate;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.lang.ExceptionUtils;

/**
 * 
 * @param <R> 
 */
public class ComposedFutureCompletion<R> {

    /**
     * 
     * @param futures 
     */
    protected ComposedFutureCompletion(final Iterable<? extends Future<R>> futures) {
        this.futures = ImmutableList.copyOf(futures);
    }
    
    /**
     * 
     * @param <Z>
     * @param futures
     * @return 
     */
    public static <Z> ComposedFutureCompletion<Z> of(final Iterable<? extends Future<Z>> futures) {
        return new ComposedFutureCompletion(futures);
    }

    /**
     * 
     * @return 
     */
    public ImmutableList<Future<R>> futures() {
        return futures;
    }

    /**
     * 
     * @return 
     */
    public boolean isEmpty() {
        return futures().isEmpty();
    }

    /**
     * 
     * @return
     * @throws InterruptedException 
     */
    public ComposedFutureCompletion<R> waitForCompletion() throws InterruptedException {
        return waitForCompletion(TimeValueUnits.of());
    }

    /**
     * 
     * @param value
     * @param unit
     * @return
     * @throws InterruptedException 
     */
    public ComposedFutureCompletion<R> waitForCompletion(final long value, final TimeUnit unit) throws InterruptedException {
        return waitForCompletion(TimeValueUnits.of(value, unit));
    }

    /**
     * 
     * @param timeout
     * @return
     * @throws InterruptedException 
     */
    public ComposedFutureCompletion<R> waitForCompletion(final TimeValueUnit timeout) throws InterruptedException {
        if (result != null) {
            return this;
        }
        final RemainingTime t = timeout == null ? Timeouts.none() : Timeouts.fromNow(timeout);
        return waitForCompletion0(t);
    }
    
    /**
     * 
     * @param timeout
     * @return
     * @throws InterruptedException 
     */
    public ComposedFutureCompletion<R> waitForCompletion(final RemainingMillis timeout) throws InterruptedException {
        return waitForCompletion0(timeout);
    }

    /**
     * 
     * @param timeout
     * @return
     * @throws InterruptedException 
     */
    protected ComposedFutureCompletion<R> waitForCompletion0(final RemainingMillis timeout) throws InterruptedException {
        final RemainingMillis t = timeout == null ? Timeouts.none() : timeout;
        final int count = futures.size();
        final List<Either<Exception, R>> entries = new ArrayList();
        while (entries.size() < count) {
            entries.add(null);
        }
        final BitSet retries = new BitSet(count);
        boolean retry = true;
        while (retry) {
            retry = false;
            for (int index = 0; index < count; index++) {
                final Future<R> f = futures.get(index);
                Either<Exception, R> entry = entries.get(index);
                if (entry == null || retries.get(index)) {
                    retries.set(index, false);
                    try {
                        final R result = f.get(t.remainingMillis(), TimeUnit.MILLISECONDS);
                        entry = Right(result);
                    } catch (final TimeoutException ex) {
                        entry = Left(ex);
                        retry = true;
                        retries.set(index, true);
                        if (t.remainingMillis() <= 0) {
                            f.cancel(true);
                        }
                    } catch (final CancellationException ex) {
                        entry = Left(ex);
                    } catch (final ExecutionException ex1) {
                        final Throwable ex2 = ex1.getCause();
                        final Exception ex = ex2 != null && ex2 instanceof Exception ? Exception.class.cast(ex2) : ex1;
                        entry = Left(ex);
                    }
                    entries.set(index, entry);
                }
            }
        }
        result = ComposedResults.forEntries(entries);
        return this;
    }

    /**
     * 
     * @return 
     */
    public BasicComposedResult<R> result() {
        checkCompletionState();
        return result;
    }
    
    /**
     * 
     * @return 
     * @throws Exception 
     */
    public ComposedFutureCompletion<R> throwFailures() throws Exception {
        checkCompletionState();
        ExceptionUtils.throwMultipleException(result.failedSeq());
        return this;
    }

    /**
     * 
     * @param condition
     * @return 
     * @throws Exception 
     */
    public ComposedFutureCompletion<R> throwFailures(final Predicate<BasicComposedResult<R>> condition) throws Exception {
        checkCompletionState();
        if (condition == null ||
                condition.test(result)) {
            ExceptionUtils.throwMultipleException(result.failedSeq());
        }
        return this;
    }

    /**
     * 
     * @return 
     */
    public boolean isDone() {
        return pendingCount() == 0;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isPending() {
        return pendingCount() > 0;
    }

    /**
     * 
     * @return 
     */
    public int pendingCount() {
        return IterableAccessors.from(futures())
                .filter(f -> !f.isDone())
                .size();
    }
    
    /**
     * 
     */
    protected void checkCompletionState() {
        if (result != null) {
            return;
        }
        final int pending = pendingCount();
        checkState(pending == 0, "%s future(s) still to be completed", pending);
        throw new IllegalStateException("waitForCompletion must be invoked");
    }

    private final ImmutableList<Future<R>> futures;

    private BasicComposedResult<R> result = null;
    
}
