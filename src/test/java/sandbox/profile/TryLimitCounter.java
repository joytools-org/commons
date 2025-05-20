/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.profile;

import org.joytools.commons.profile.LimitCounter;

/**
 *
 * @author AndreaR
 */
public class TryLimitCounter {
    
    public static void main(final String... args) throws Exception {
        final LimitCounter l = new LimitCounter().limit(5);
        for (int i = 0; i < 100; i++) {
            if (l.increment()) {
                System.out.println(l);
            }
        }
        System.out.println(l);
    }
    
}
