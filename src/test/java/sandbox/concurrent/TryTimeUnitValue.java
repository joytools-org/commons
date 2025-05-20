/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.concurrent;

import org.joytools.commons.concurrent.TimeValueUnit;
import org.joytools.commons.concurrent.TimeValueUnits;

/**
 *
 * @author AndreaR
 */
public class TryTimeUnitValue {
    
    public static void main(final String... args) {
        // final TimeValueUnit tuv1 = TimeValueUnits.of(10, TimeUnit.DAYS);
        final TimeValueUnit tuv1 = TimeValueUnits.of("1 day");
        System.out.println(tuv1);
        System.out.println(tuv1.duration());
        System.out.println(tuv1.toOption());

        // final TimeValueUnit tuv2 = TimeValueUnits.of(10, TimeUnit.DAYS);
        final TimeValueUnit tuv2 = TimeValueUnits.of("10 days");
        System.out.println(tuv2);
        System.out.println(tuv2.duration());
        System.out.println(tuv2.toOption());
        
        System.out.println(tuv1.equals(tuv2));
    }
    
}
