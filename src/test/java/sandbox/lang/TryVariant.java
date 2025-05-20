/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import org.joytools.commons.util.ComboVariant;
import org.joytools.commons.util.ComboVariants;

/**
 *
 * @author AndreaR
 */
public class TryVariant {
 
    public static void main(final String... args) {
        final Object i = "2019-01-01";
        final ComboVariant v = ComboVariants.of(i);
        System.out.println(v.getSQLTimestamp());
        System.out.println(v.getSQLTimestamp());
    }
    
}
