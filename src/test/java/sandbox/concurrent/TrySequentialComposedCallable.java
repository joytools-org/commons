/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.concurrent;

import java.util.concurrent.Callable;
import org.joytools.commons.concurrent.BasicComposedResult;
import org.joytools.commons.concurrent.Callables;

/**
 *
 * @author AndreaR
 */
public class TrySequentialComposedCallable {
    
    public static void main(final String... args) throws Exception {
        // final Callable<SeqComposedResult<Boolean>> c = Callables.sequential(Commons.newCallables()).build();
        final Callable<BasicComposedResult<Boolean>> c = Callables.sequential(Commons.newCallables(true))
                .shutdownOnCompletion()
                .build();
        c.call();
    }
    
}
