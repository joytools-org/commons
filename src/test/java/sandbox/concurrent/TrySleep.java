/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.joytools.commons.concurrent.ExecutorServiceShutdown;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.concurrent.TimeValueUnit;
import org.joytools.commons.concurrent.TimeValueUnits;

/**
 *
 * @author AndreaR
 */
public class TrySleep {
    
    private final static int THREAD_COUNT = 20;
    
    private final static TimeValueUnit TIMEOUT = TimeValueUnits.of(60, TimeUnit.SECONDS);
    
    public static void main(final String... args) throws Exception {
        final ExecutorService es = Executors.newCachedThreadPool();
        List<Future<Integer>> l;
        
        System.out.println("Running Sleep");
        l = es.invokeAll(newPseudoSleep());
        l.forEach((f) -> {
            try {
                f.get();
            } catch (final Exception e) {
                ExceptionUtils.wrapAndThrow(e);
            } 
        });
        
        System.out.println("Running Pseudo Sleep");
        l = es.invokeAll(newPseudoSleep());
        l.forEach((f) -> {
            try {
                f.get();
            } catch (final Exception e) {
                ExceptionUtils.wrapAndThrow(e);
            } 
        });

        ExecutorServiceShutdown.of(es).execute();
        
    }
    
    static List<Callable<Integer>> newSleep() {
        final List<Callable<Integer>> l = new ArrayList();
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int result = i;
            l.add(() -> {
                Thread.sleep(TIMEOUT.toMillis());
                return result;
            });
        }
        return l;
    }
    
    static List<Callable<Integer>> newPseudoSleep() {
        final List<Callable<Integer>> l = new ArrayList();
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int result = i;
            l.add(() -> {
                sleep(TIMEOUT);
                return result;
            });
        }
        return l;
    }
    
    static void sleep(final TimeValueUnit timeout) throws InterruptedException {
        final long expiresNano = System.nanoTime() + timeout.toNanos();
        while (System.nanoTime() < expiresNano) {
            if (Thread.currentThread().isInterrupted()) {
                throw new InterruptedException();
            }
        }
    }

}
