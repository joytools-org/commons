/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import org.joytools.commons.lang.MultipleException;

/**
 *
 * @author AndreaR
 */
public class TryMultipleExceptionFlatten {
    
    public static void main(final String... args) {
        final MultipleException me = Commons.buildMultipleException(2);
        System.out.println("*** ORIGINAL ***");
        Commons.dump(me);
        System.out.println("*** FLATTEN ***");
        Commons.dump(me.flatten());
        for (int i = 0; i <= 2; i++) {
            System.out.println("*** FLATTEN " + i + " ***");
            Commons.dump(me.flatten(i));
        }

    }
    
}
