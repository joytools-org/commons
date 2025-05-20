/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import com.google.common.util.concurrent.MoreExecutors;
import io.vavr.Tuple2;
import io.vavr.collection.IndexedSeq;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import org.joytools.commons.lang.ObjectUtils;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public abstract class SeqComposedCallableV1BeforeCurrentThread<V> implements Callable<SeqComposedResult<V>> {
    
    private final static TimeValueUnit SHUTDOWN_TIMEOUT1 = TimeValueUnits.zeroMillis();
    
    private final static TimeValueUnit SHUTDOWN_DEFAULT_TIMEOUT2 = TimeValueUnits.ofSeconds(5);

    abstract protected SeqComposedCallableSettings<V> settings();
    
    @Override
    public SeqComposedResult<V> call() throws Exception {
        final SeqComposedCallableSettings<V> settings = Objects.requireNonNull(settings(), "Settings");
        final Seq<Callable<V>> tasks = Objects.requireNonNull(settings.tasks(), "Tasks");
        final Predicate<ComposedResult<Integer, V>> shutdownPolicy = Objects.requireNonNull(settings.shutdownPolicy(), "Shutdown Policy");

        IndexedSeq<Future<V>> sourceOrderFutures = Vector.fill(tasks.size(), (Future<V>)null);
        // IndexedSeq<Either<Exception, E>> completionOrderEntries = Vector.empty();
        SeqComposedResult<V> completionOrderResult = ComposedResults.seq.empty();
        final Integer maxThreadSettings = settings.maxThreads();
        // final int nThreads = maxThreadSettings != null && maxThreadSettings > 0 ? maxThreadSettings : tasks.size();
        final int nThreads = maxThreadSettings == null || maxThreadSettings < 0 ? tasks.size() : maxThreadSettings;
        final TimeValueUnit timeout = settings.timeout();
        final TimeValueUnit delay = null;
        if (tasks.isEmpty()) {
            return completionOrderResult;
        }
        final ExecutorService es = nThreads == 0 ? MoreExecutors.newDirectExecutorService() : Executors.newFixedThreadPool(nThreads);
        try {
            final RemainingTime remainingTime = timeout == null ? Timeouts.none() : Timeouts.fromNow(timeout);
            final KeyedCompletionService<Integer, V> kcs = KeyedCompletionService.<Integer, V>on(es);
            final Seq<Tuple2<Integer, Callable<V>>> callables = tasks
                    .zipWithIndex()
                    .map(t -> t.swap());
            for (final Tuple2<Integer, Callable<V>> c : callables) {
                if (c._1() > 0 && delay != null) {
                    final long delayMillis = Math.min(delay.toMillis(), remainingTime.remainingMillis());
                    System.out.println(tracePrefix()+ "Delaying for " + delayMillis + " milliseconds");
                    Thread.sleep(delayMillis);
                }
                if (remainingTime.isAfter()) {
                    throw new TimeoutException(); 
                }
                System.out.println(tracePrefix() + "Submitting " + c._1());
                final Tuple2<Integer, Future<V>> f = kcs.submit(c._1, c._2());
                sourceOrderFutures = sourceOrderFutures.update(f._1(), f._2());
                System.out.println(tracePrefix() + "Submitted " + c._1());
            }
            final AtomicInteger atomicIndex = new AtomicInteger(0);
            while (kcs.isNotEmpty()) {
                final int index = atomicIndex.getAndIncrement();
                System.out.println(tracePrefix() + "Polling #" + index);
                Tuple2<Integer, Future<V>> f = null;
                try {
                    f = kcs.take(remainingTime.remainingMillis(), TimeUnit.MILLISECONDS);
                } catch (final TimeoutException te) {
                    throw new ComposedCallableTimeout("Task did not complete in the expected timeout: " + timeout, completionOrderResult);
                }
                // final Seq<Either<Exception, E>> newEntries = completionOrderResult.entriesSeq().append(ComposedResults.newEntryFromCompletedFuture(f._2()).get());
                // completionOrderResult = () -> newEntries;
                completionOrderResult = completionOrderResult.append(ComposedResults.newEntryFromCompletedFuture(f._2()).get());
                System.out.println(tracePrefix() + "Taken #" + index);
                System.out.println(tracePrefix() + "  Key: " + f._1());
                try {
                    System.out.println(tracePrefix() + "  Future: " + f._2().get());
                } catch (final ExecutionException ee) {
                    System.out.println(tracePrefix() + "  Future: " + ee.getCause());
                }
                if (shutdownPolicy.test(completionOrderResult)) {
                    break;
                }
            }
        } finally {
            final TimeValueUnit shutdownTimeout = ObjectUtils.coalesce(settings.shutdownTimeout(), SHUTDOWN_DEFAULT_TIMEOUT2);
            final boolean lenient = true; // shutdownTimeout.toMillis() == 0;
            ExecutorServiceShutdown.of(es)
                    .timeout1(SHUTDOWN_TIMEOUT1)
                    .timeout2(shutdownTimeout)
                    .lenient(lenient)
                    .execute();
        }
        completionOrderResult.throwIf(settings.throwPolicy(), settings.throwPolicyMapper());
        if (settings.completionOrder()) {
            final int missingCount = tasks.size() - completionOrderResult.entriesSeq().size();
            /* final Seq<Either<Exception, E>> newEntries = completionOrderResult
                    .entriesSeq()
                    .appendAll(io.vavr.collection.Iterator.fill(missingCount, 
                            ComposedResults.newEntryFromFuture(newTimeoutFuture())));
            completionOrderResult = () -> newEntries; */
            final Iterable<Either<Exception, V>> newEntries = io.vavr.collection.Iterator.fill(missingCount, 
                            ComposedResults.newEntryFromFuture(newTimeoutFuture()));
            completionOrderResult = completionOrderResult.appendAll(newEntries);
            return completionOrderResult;
        }
        return ComposedResults.ofSeqFutures(sourceOrderFutures);
    }
    
    /**
     * 
     * @param <T>
     * @return 
     */
    static <T> Future<T> newTimeoutFuture() {
        return new Future<T>() {
            @Override
            public boolean cancel(final boolean mayInterruptIfRunning) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean isCancelled() {
                return true;
            }

            @Override
            public boolean isDone() {
                return true;
            }

            @Override
            public T get() throws InterruptedException, ExecutionException {
                throw new UnsupportedOperationException();
            }

            @Override
            public T get(final long timeout, final TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                throw new TimeoutException();
            }
        };
    }

    /**
     * 
     * @return 
     */
    static String tracePrefix() {
        final String s = new java.sql.Timestamp(System.currentTimeMillis()).toString();
        return StringUtils.rightPad(s, 23, "0") + " ";
    }
    
}
