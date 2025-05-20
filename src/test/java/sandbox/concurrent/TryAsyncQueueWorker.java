/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.joytools.commons.function.FailableProcessor;
import org.joytools.commons.concurrent.TimeValueUnit;
import org.joytools.commons.concurrent.TimeValueUnits;

/**
 *
 * @author AndreaR
 */
public class TryAsyncQueueWorker {
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(final String... args) throws Exception {
        System.out.println("[Application] Started");

        final BlockingQueue<String> queue = new LinkedBlockingQueue();
        final AsyncQueueWorker.Config<String> c = new AsyncQueueWorker.Config<String>() {
            @Override
            public BlockingQueue<String> queue() {
                return queue;
            }

            @Override
            public TimeValueUnit timeout() {
                return TimeValueUnits.of(20, TimeUnit.SECONDS);
            }

            @Override
            public FailableProcessor<String, Boolean, Exception> processor() {
                return (p) -> {
                    return process(p);
                };
            }
        };
        final AsyncQueueWorker<String> w = new AsyncQueueWorker(c).start();

        for (int i = 0; i < 10; i++) {
            Thread.sleep((int)(Math.random() * 500));
            System.out.println("[Application] Queued " + i);
            w.offer(String.valueOf(i));
            Thread.sleep((int)(Math.random() * 500));
        }
        System.out.println("[Application] Finished");
        
    }

    /**
     * 
     * @param p
     * @return
     * @throws Exception 
     */
    static boolean process(final String item) throws Exception {
        System.out.println("[Background] Received " + item);
        for (int i = 0; i < 5; i++) {
            System.out.println("[Background] " + item + " Iteration " + i);
            Thread.sleep(800);
        }
        return true;
    }
    
}
