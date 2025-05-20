/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.concurrent.Callables;
import org.joytools.commons.concurrent.TimeValueUnits;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class TryInterruptibleIteration {
    
    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(final String... args) throws Exception {
        final Callable<String> c = () -> {
            final AtomicLong timeout = new AtomicLong();
            final IterableAccessor<String> itr = IterableAccessors.from("uno", "due", "tre", "quattro")
                    /* .accept(e -> {
                        System.out.println("*** " + e + " ***");
                    }) */
                    .accept(e -> {
                        final long time = System.currentTimeMillis();
                        if (time > timeout.get()) {
                            System.out.println(e);
                            timeout.set(time + 2000);
                        }
                    })
                    .cycling();
            itr.interruptible().forEach();
            return EMPTY;
        };       
        Callables.parallel(c)
                .maxThreads(1)
                .timeout(TimeValueUnits.of("8 seconds"))
                .build()
                .call();
    }
    
}
