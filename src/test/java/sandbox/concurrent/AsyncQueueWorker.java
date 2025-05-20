/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.concurrent;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import org.joytools.commons.function.FailableProcessor;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.concurrent.TimeValueUnit;

/**
 *
 * @author AndreaR
 */
public class AsyncQueueWorker<E> {
    
    /**
     * 
     * @param config 
     */
    AsyncQueueWorker(final Config<E> config) {
        this.config = Objects.requireNonNull(config, "Configuration");
        this.queue = Objects.requireNonNull(config.queue(), "Configuration Queue");
        this.parentThread = Thread.currentThread();
    }

    /**
     * 
     * @param <K> 
     */
    public interface Config<K> {
        
        public BlockingQueue<K> queue();
        
        public TimeValueUnit timeout();
        
        public FailableProcessor<K, Boolean, Exception> processor();
        
    }
    
    /**
     * 
     * @return 
     */
    public AsyncQueueWorker start() {
        if (thread != null) {
            throw new IllegalStateException("Already started");
        }
        thread = new Thread(new Worker());
        thread.start();
        return this;
    }
    
    /**
     * 
     */
    class Worker implements Runnable {

        /**
         * 
         */
        @Override
        public void run() {
            try {
                execute();
            } catch (final Exception e) {
                ExceptionUtils.wrapAndThrow(e);
            }
        }
            
        /**
         * 
         * @throws Exception 
         */
        protected void execute() throws Exception {
            Long exitTimeMillis = null;
            System.out.println("[Background] Started");
            while (true) {
                if (!parentThread.isAlive()) {
                    final long currentTimeMillis = System.currentTimeMillis();
                    if (exitTimeMillis == null) {
                        final TimeValueUnit timeout = config.timeout();
                        final boolean hasTimeout = timeout != null && timeout.isPresent();
                        exitTimeMillis = hasTimeout ? 
                                Long.MAX_VALUE : 
                                currentTimeMillis + timeout.toMillis();
                        System.out.println("[Background] Parent thread finished, " + (hasTimeout ? "additional timeout is " + timeout : "no timeout defined"));
                    } else {
                        if (currentTimeMillis >= exitTimeMillis) {
                            System.out.println("[Background] Quitting for timeout");
                            break;
                        }
                    }
                    if (queue.isEmpty()) {
                        System.out.println("[Background] Quitting for completion");
                        break;
                    }
                } 
                try {
                    final E o = queue.peek();
                    if (o == null) {
                        Thread.sleep(1000);
                    } else {
                        try {
                            if (process(o)) {
                                queue.remove();
                            }
                        } catch (final Exception e) {
                            System.out.println("[Background] Exception found " + e);
                        }
                    }
                } catch (final InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
            System.out.println("[Background] Finished");
        }

        /**
         * 
         * @param item
         * @return
         * @throws Exception 
         */
        protected boolean process(final E item) throws Exception {
            final FailableProcessor<E, Boolean, Exception> p = config.processor();
            if (p != null) {
                return p.process(item);
            }
            return true;
        }

    }
    
    /**
     * 
     * @param item
     * @return 
     */
    public boolean offer(final E item) {
        return queue.offer(item);
    }
    
    /**
     * 
     */
    private final Config<E> config;

    /**
     * 
     */
    private final Thread parentThread;
    
    /**
     * 
     */
    private Thread thread = null;

    /**
     * 
     */
    private final BlockingQueue<E> queue;
    
}
