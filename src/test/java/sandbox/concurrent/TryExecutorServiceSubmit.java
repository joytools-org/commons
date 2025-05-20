/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.joytools.commons.concurrent.ExecutorServiceSubmit;
import org.joytools.commons.concurrent.ExecutorServiceShutdown;
import org.joytools.commons.concurrent.ComposedFutureCompletion;

/**
 *
 * @author AndreaR
 */
public class TryExecutorServiceSubmit {
    
    public static void main(final String... args) throws TimeoutException, InterruptedException {
        final ExecutorService es = Executors.newFixedThreadPool(8);
        ComposedFutureCompletion<Boolean> sr;
        try {
            System.out.println("ExecutorService.invoke()");
            sr = ExecutorServiceSubmit.<Boolean>of(es)
                    .set(Commons.newFailingCallables())
                    .submit();
            sr.waitForCompletion(10, TimeUnit.SECONDS);
        } finally {
            System.out.println("ExecutorService.shutdown()");
            ExecutorServiceShutdown.of(es)
                    .timeout1(10, TimeUnit.SECONDS)
                    .timeout2(10, TimeUnit.SECONDS)
                    .execute();
        }
        System.out.println("waitForCompletion()");
        /*
        sr.futures().forEach(f -> {
            System.out.println("********************************");
            System.out.println("isDone: " + f.isDone());
            System.out.println("isCancelled: " + f.isCancelled());
        });
        */
        sr.result().entriesSeq().forEach(System.out::println);
    }
    
}
