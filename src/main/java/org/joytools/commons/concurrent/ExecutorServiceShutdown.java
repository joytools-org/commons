/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author AndreaR
 */
public final class ExecutorServiceShutdown extends AbstractExecutorServiceWrapper implements AutoCloseable {
    
    /**
     * 
     * @param executorService 
     */
    protected ExecutorServiceShutdown(final ExecutorService executorService) {
        super(executorService);
    }
    
    /**
     * 
     * @param executorService
     * @return 
     */
    public static ExecutorServiceShutdown of(final ExecutorService executorService) {
        return new ExecutorServiceShutdown(executorService);
    }
    
    /**
     * 
     * @param executorService
     * @param settings
     * @return 
     */
    public static ExecutorServiceShutdown of(final ExecutorService executorService,
            final Settings settings) {
        final ExecutorServiceShutdown ess = new ExecutorServiceShutdown(executorService);
        checkNotNull(settings, "Settings");
        if (settings.lenient()) {
            ess.lenient();
        }
        ess.timeout1(settings.timeout1());
        ess.timeout2(settings.timeout2());
        return ess;
    }

    /**
     * 
     */
    public interface Settings {
    
        default TimeValueUnit timeout1() {
            return TimeValueUnits.of(15, TimeUnit.SECONDS);
        }
        
        default TimeValueUnit timeout2() {
            return TimeValueUnits.of(15, TimeUnit.SECONDS);
        }
        
        default boolean lenient() {
            return false;
        }

    }
    
    /**
     * 
     * @param executorService
     * @return 
     */
    @Deprecated
    public static ExecutorServiceShutdown old_lenientOf(final ExecutorService executorService) {
        return new ExecutorServiceShutdown(executorService).lenient();
    }

    /**
     * 
     * @throws java.util.concurrent.TimeoutException
     */
    @Override
    public void close() throws TimeoutException {
        execute();
    }
    
    /**
     * 
     * @return 
     */
    public ExecutorServiceShutdownThread toThread() {
        return new ExecutorServiceShutdownThread(this);
    }
    
    /**
     * 
     * @return 
     * @throws java.util.concurrent.TimeoutException 
     */
    public boolean execute() throws TimeoutException {
        final ExecutorService es = executorService();
        if (es.isShutdown()) {
            return es.isTerminated();
        }
        es.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!es.awaitTermination(timeout1.value(), timeout1.unit())) {
                es.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (!es.awaitTermination(timeout2.value(), timeout2.unit()) && !lenient) {
                    throw new TimeoutException("Task(s) did not terminate within the given timeout");
                }  /*else {
                    if (lenient) {
                        return false;
                    }
                    throw new TimeoutException("Executor Service did not terminate within the given timeout");
                } */
            }
            return es.isTerminated();
        } catch (final InterruptedException ie) {
            // Preserve interrupt status
            Thread.currentThread().interrupt();
            // (Re-)Cancel if current thread also interrupted
            es.shutdownNow();
        }
        return false;
    }    

    /**
     * 
     * @param timeout
     * @return 
     */
    public ExecutorServiceShutdown timeout1(final TimeValueUnit timeout) {
        this.timeout1 = checkNotNull(timeout, "Timeout");
        return this;
    }

    /**
     * 
     * @param duration
     * @return 
     */
    public ExecutorServiceShutdown timeout1(final Duration duration) {
        this.timeout1 = TimeValueUnits.of(checkNotNull(duration, "Duration"));
        return this;
    }

    /**
     * 
     * @param value
     * @param unit
     * @return 
     */
    public ExecutorServiceShutdown timeout1(final long value,
            final TimeUnit unit) {
        this.timeout1 = TimeValueUnits.of(value, unit);
        return this;
    }
    
    /**
     * 
     * @param timeout
     * @return 
     */
    public ExecutorServiceShutdown timeout2(final TimeValueUnit timeout) {
        this.timeout2 = checkNotNull(timeout, "Timeout");
        return this;
    }

    /**
     * 
     * @return 
     */
    public ExecutorServiceShutdown lenient() {
        this.lenient = true;
        return this;
    }
    
    /**
     * 
     * @param flag
     * @return 
     */
    public ExecutorServiceShutdown lenient(final boolean flag) {
        this.lenient = flag;
        return this;
    }

    /**
     * 
     * @param value
     * @param unit
     * @return 
     */
    public ExecutorServiceShutdown timeout2(final long value,
            final TimeUnit unit) {
        this.timeout2 = TimeValueUnits.of(value, unit);
        return this;
    }

    /**
     * 
     * @param duration
     * @return 
     */
    public ExecutorServiceShutdown timeout2(final Duration duration) {
        this.timeout2 = TimeValueUnits.of(checkNotNull(duration, "Duration"));
        return this;
    }

    /**
     * 
     */
    private TimeValueUnit timeout1 = TimeValueUnits.of(30, TimeUnit.SECONDS);
    
    /**
     * 
     */
    private TimeValueUnit timeout2 = TimeValueUnits.of(30, TimeUnit.SECONDS);

    /**
     * 
     */
    private boolean lenient = false;
            
}
