/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import static io.vavr.API.Tuple;
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
import java.util.function.Supplier;
import org.joytools.commons.lang.ObjectUtils;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public abstract class BasicComposedCallable<V> extends AbstractComposedCallable implements Callable<BasicComposedResult<V>> {
    
    private final static TimeValueUnit SHUTDOWN_TIMEOUT1 = TimeValueUnits.zeroMillis();
    
    private final static TimeValueUnit SHUTDOWN_DEFAULT_TIMEOUT2 = TimeValueUnits.ofSeconds(5);

    abstract protected BasicComposedCallableSettings<V> settings();
    
    @Override
    public BasicComposedResult<V> call() throws Exception {
        final BasicComposedCallableSettings<V> settings = Objects.requireNonNull(settings(), "Settings");
        Objects.requireNonNull(settings.tasks(), "Tasks");
        Objects.requireNonNull(settings.shutdownPolicy(), "Shutdown Policy");

        final Integer maxThreadSettings = settings.maxThreads();
        
        if (maxThreadSettings != null && maxThreadSettings == 0) {
            /* final Supplier<ExecutorService> ess = () -> MoreExecutors.newDirectExecutorService();
            return doExecutorCall(ess); */
            return doCurrentThreadCall();
        } else {
            final Supplier<ExecutorService> ess = () ->
                    maxThreadSettings == null || maxThreadSettings < 0 ? 
                            Executors.newCachedThreadPool() : 
                            Executors.newFixedThreadPool(maxThreadSettings);
            return doExecutorCall(ess);
        }
    }
    
    /**
     * 
     * @return
     * @throws Exception 
     */
    protected BasicComposedResult<V> doCurrentThreadCall() throws Exception {
        final BasicComposedCallableSettings<V> settings = settings();
        final Seq<Callable<V>> tasks = settings.tasks();
        final Predicate<ComposedResult<Integer, V>> shutdownPolicy = settings.shutdownPolicy();
        
        IndexedSeq<Future<V>> sourceOrderFutures = Vector.fill(tasks.size(), (Future<V>)null);
        // IndexedSeq<Either<Exception, E>> completionOrderEntries = Vector.empty();
        BasicComposedResult<V> completionOrderResult = ComposedResults.basic.empty();
        // final int nThreads = maxThreadSettings != null && maxThreadSettings > 0 ? maxThreadSettings : tasks.size();
        final TimeValueUnit timeout = settings.timeout();
        // final TimeValueUnit delay = null;
        if (tasks.isEmpty()) {
            return completionOrderResult;
        }
        final RemainingTime remainingTime = timeout == null ? Timeouts.none() : Timeouts.fromNow(timeout);
        final Seq<Tuple2<Integer, Callable<V>>> callables = tasks
                .zipWithIndex()
                .map(t -> t.swap());
        for (final Tuple2<Integer, Callable<V>> c : callables) {
            /* if (c._1() > 0 && delay != null) {
                final long delayMillis = Math.min(delay.toMillis(), remainingTime.remainingMillis());
                System.out.println(tracePrefix()+ "Delaying for " + delayMillis + " milliseconds");
                Thread.sleep(delayMillis);
            } */
            if (remainingTime.isAfter()) {
                throw new ComposedCallableTimeout("Task did not complete in the expected timeout: " + timeout);
            }
            trace("Running " + c._1());
            final Tuple2<Integer, Future<V>> f = Tuple(c._1, toFuture(c._2()));
            sourceOrderFutures = sourceOrderFutures.update(f._1(), f._2());
            
            trace("Completed " + c._1());

            completionOrderResult = completionOrderResult.append(ComposedResults.newEntryFromCompletedFuture(f._2()).get());
            
            trace("Taken #" + c._1());
            trace("  Key: " + f._1());
            try {
                trace("  Future: " + f._2().get());
            } catch (final ExecutionException ee) {
                trace("  Future: " + ee.getCause());
            }
            if (shutdownPolicy.test(completionOrderResult)) {
                break;
            }
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
        return ComposedResults.forFutures(sourceOrderFutures);
    }
    
    /**
     * 
     * @param ess
     * @return
     * @throws Exception 
     */
    protected BasicComposedResult<V> doExecutorCall(final Supplier<ExecutorService> ess) throws Exception {
        final BasicComposedCallableSettings<V> settings = settings();
        final Seq<Callable<V>> tasks = settings.tasks();
        final Predicate<ComposedResult<Integer, V>> shutdownPolicy = settings.shutdownPolicy();

        IndexedSeq<Future<V>> sourceOrderFutures = Vector.fill(tasks.size(), (Future<V>)null);
        // IndexedSeq<Either<Exception, E>> completionOrderEntries = Vector.empty();
        BasicComposedResult<V> completionOrderResult = ComposedResults.basic.empty();
        // final int nThreads = maxThreadSettings != null && maxThreadSettings > 0 ? maxThreadSettings : tasks.size();
        final TimeValueUnit timeout = settings.timeout();
        // final TimeValueUnit delay = null;
        if (tasks.isEmpty()) {
            return completionOrderResult;
        }
        final ExecutorService es = ess.get();
        try {
            final RemainingTime remainingTime = timeout == null ? Timeouts.none() : Timeouts.fromNow(timeout);
            final KeyedCompletionService<Integer, V> kcs = KeyedCompletionService.<Integer, V>on(es);
            final Seq<Tuple2<Integer, Callable<V>>> callables = tasks
                    .zipWithIndex()
                    .map(t -> t.swap());
            for (final Tuple2<Integer, Callable<V>> c : callables) {
                /* if (c._1() > 0 && delay != null) {
                    final long delayMillis = Math.min(delay.toMillis(), remainingTime.remainingMillis());
                    System.out.println(tracePrefix()+ "Delaying for " + delayMillis + " milliseconds");
                    Thread.sleep(delayMillis);
                } */
                if (remainingTime.isAfter()) {
                    throw new ComposedCallableTimeout("Task did not complete in the expected timeout: " + timeout);
                }
                trace("Submitting " + c._1());
                final Tuple2<Integer, Future<V>> f = kcs.submit(c._1, c._2());
                sourceOrderFutures = sourceOrderFutures.update(f._1(), f._2());
                trace("Submitted " + c._1());
            }
            final AtomicInteger atomicIndex = new AtomicInteger(0);
            while (kcs.isNotEmpty()) {
                final int index = atomicIndex.getAndIncrement();
                trace("Polling #" + index);
                Tuple2<Integer, Future<V>> f = null;
                try {
                    f = kcs.take(remainingTime.remainingMillis(), TimeUnit.MILLISECONDS);
                } catch (final TimeoutException te) {
                    throw new ComposedCallableTimeout("Task did not complete in the expected timeout: " + timeout, completionOrderResult);
                }
                // final Seq<Either<Exception, E>> newEntries = completionOrderResult.entriesSeq().append(ComposedResults.newEntryFromCompletedFuture(f._2()).get());
                // completionOrderResult = () -> newEntries;
                completionOrderResult = completionOrderResult.append(ComposedResults.newEntryFromCompletedFuture(f._2()).get());
                trace("Taken #" + index);
                trace("  Key: " + f._1());
                try {
                    trace("  Future: " + f._2().get());
                } catch (final ExecutionException ee) {
                    trace("  Future: " + ee.getCause());
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
        return ComposedResults.forFutures(sourceOrderFutures);
    }

}
