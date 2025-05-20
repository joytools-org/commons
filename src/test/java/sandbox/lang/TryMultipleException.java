/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import java.util.ArrayList;
import java.util.List;
import org.joytools.commons.lang.ExceptionUtils;

/**
 *
 * @author AndreaR
 */
public class TryMultipleException {
    
    public static void main(final String... args) throws Exception {
        final List<Throwable> exceptions = new ArrayList();
        for (int i = 0; i < 15; i++) {
            exceptions.add(new IllegalStateException("Fake exception number " + String.valueOf(i)));
        }
        ExceptionUtils.throwMultipleException(exceptions);
    }
    
}
