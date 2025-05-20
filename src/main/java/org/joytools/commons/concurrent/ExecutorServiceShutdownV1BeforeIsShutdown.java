/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 *
 * @author AndreaR
 */
public final class ExecutorServiceShutdownV1BeforeIsShutdown extends AbstractExecutorServiceWrapper implements AutoCloseable {
    
    /**
     * 
     * @param executorService 
     */
    protected ExecutorServiceShutdownV1BeforeIsShutdown(final ExecutorService executorService) {
        super(executorService);
    }
    
    /**
     * 
     * @param executorService
     * @return 
     */
    public static ExecutorServiceShutdownV1BeforeIsShutdown of(final ExecutorService executorService) {
        return new ExecutorServiceShutdownV1BeforeIsShutdown(executorService);
    }
    
    /**
     * 
     * @param executorService
     * @param settings
     * @return 
     */
    public static ExecutorServiceShutdownV1BeforeIsShutdown of(final ExecutorService executorService,
            final Settings settings) {
        final ExecutorServiceShutdownV1BeforeIsShutdown ess = new ExecutorServiceShutdownV1BeforeIsShutdown(executorService);
        checkNotNull(settings, "Settings");
        if (settings.lenient()) {
            ess.lenient();
        }
        ess.timeout1(settings.timeOut1());
        ess.timeout2(settings.timeOut2());
        return ess;
    }

    /**
     * 
     */
    public interface Settings {
    
        default TimeValueUnit timeOut1() {
            return TimeValueUnits.of(60, TimeUnit.SECONDS);
        }
        
        default TimeValueUnit timeOut2() {
            return TimeValueUnits.of(60, TimeUnit.SECONDS);
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
    public static ExecutorServiceShutdownV1BeforeIsShutdown lenientOf(final ExecutorService executorService) {
        return new ExecutorServiceShutdownV1BeforeIsShutdown(executorService).lenient();
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
     * @throws java.util.concurrent.TimeoutException 
     */
    public boolean execute() throws TimeoutException {
        if (called) {
            throw new IllegalStateException("Shutdown has already been issued for this ExecutorService");
        }
        called = true;
        final ExecutorService es = executorService();
        es.shutdown(); // Disable new tasks from being submitted
        try {
            // Wait a while for existing tasks to terminate
            if (!es.awaitTermination(timeout1.value(), timeout1.unit())) {
                es.shutdownNow(); // Cancel currently executing tasks
                // Wait a while for tasks to respond to being cancelled
                if (es.awaitTermination(timeout2.value(), timeout2.unit())) {
                    if (lenient) {
                        return false;
                    }
                    throw new TimeoutException("Task(s) did not terminate within the given timeout");
                } else {
                    if (lenient) {
                        return false;
                    }
                    throw new TimeoutException("Executor Service did not terminate within the given timeout");
                }
            }
            return es.isTerminated();
        } catch (final InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            es.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
        return false;
    }    

    /**
     * 
     * @param timeout
     * @return 
     */
    public ExecutorServiceShutdownV1BeforeIsShutdown timeout1(final TimeValueUnit timeout) {
        this.timeout1 = checkNotNull(timeout, "Timeout");
        return this;
    }

    /**
     * 
     * @param value
     * @param unit
     * @return 
     */
    public ExecutorServiceShutdownV1BeforeIsShutdown timeout1(final long value,
            final TimeUnit unit) {
        this.timeout1 = TimeValueUnits.of(value, unit);
        return this;
    }
    
    /**
     * 
     * @param timeout
     * @return 
     */
    public ExecutorServiceShutdownV1BeforeIsShutdown timeout2(final TimeValueUnit timeout) {
        this.timeout2 = checkNotNull(timeout, "Timeout");
        return this;
    }

    /**
     * 
     * @return 
     */
    public ExecutorServiceShutdownV1BeforeIsShutdown lenient() {
        this.lenient = true;
        return this;
    }
    
    /**
     * 
     * @param value
     * @param unit
     * @return 
     */
    public ExecutorServiceShutdownV1BeforeIsShutdown timeout2(final long value,
            final TimeUnit unit) {
        this.timeout2 = TimeValueUnits.of(value, unit);
        return this;
    }

    /**
     * 
     */
    private TimeValueUnit timeout1 = TimeValueUnits.of();
    
    /**
     * 
     */
    private TimeValueUnit timeout2 = TimeValueUnits.of(60, TimeUnit.SECONDS);

    /**
     * 
     */
    private boolean called = false;
    
    /**
     * 
     */
    private boolean lenient = false;
            
}
