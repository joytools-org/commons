/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.concurrent;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.function.FailableSupplier;

/**
 *
 * @author AndreaR
 */
class Commons {
    
    /**
     * 
     * @return 
     */
    static FailableSupplier<String, Exception> failingInitializer() {
        return () -> {
            try {
                System.out.println("*** INITIALIZATION START ***");
                Thread.sleep(Duration.ofSeconds(2).toMillis());
                System.out.println("*** INITIALIZATION FAILURE ***");
                throw new UnsupportedOperationException("Fake exception");
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            }
        };
    }
    
    /**
     * 
     * @return 
     */
    static List<Callable<Boolean>> newCallables() {
        return newCallables(false);
    }

    /**
     * 
     * @return 
     */
    static List<Callable<Boolean>> newFailingCallables() {
        return newCallables(true);
    }
    
    /**
     * 
     * @param failing
     * @return 
     */
    static List<Callable<Boolean>> newCallables(final boolean failing) {
        final List<Callable<Boolean>> callables = new ArrayList();
        for (int i = 0; i < 10; i++) {
            final int ii = i;
            callables.add(() -> { 
                long millis = 0;
                switch (ii % 3) {
                    case 0:
                        millis = TimeUnit.SECONDS.toMillis(2);
                        break;
                    case 1:
                        millis = TimeUnit.SECONDS.toMillis(4);
                        break;
                    default:
                        if (failing) {
                            System.out.println("#" + ii + ": Exception!");
                            throw new IllegalArgumentException("Invalid number: " + ii);
                        } else {
                            millis = TimeUnit.SECONDS.toMillis(6);
                            break;
                        }
                }
                System.out.println("#" + ii + ": " + millis + "ms Started");
                try {
                    Thread.sleep(millis);
                } catch (final InterruptedException e) {
                    System.out.println("#" + ii + ": " + millis + "ms Interrupted!");
                    throw e;
                }
                System.out.println("#" + ii + ": " + millis + "ms Done!");
                return true;
            });
        }
        return callables;
    }

}
