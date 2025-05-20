/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.concurrent;

import java.util.concurrent.Callable;
import org.joytools.commons.concurrent.Callables;

/**
 *
 * @author AndreaR
 */
public class TryComposedCallable {
    
    public static void main(final String... args) throws Exception {
        final Callable<Boolean> c1 = () -> { 
            System.out.println("c1");
            return true;
        };
        final Callable<Boolean> c2 = () -> { 
            System.out.println("c2");
            throw new UnsupportedOperationException();
        };
        final Callable<Boolean> c3 = () -> { 
            System.out.println("c3");
            return true;
        };
        Callables.sequential(c1, c2, c3)
                .build()
                .call();
        // ComposedCallable.forSequential(c1, c2, c3).call();
    }
}
