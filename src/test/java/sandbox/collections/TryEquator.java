/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.collections;

import org.apache.commons.collections4.Equator;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryEquator {

    public static void main(final String... args) {
        comp("", "");
        comp("a", null);
        comp(null, "a");
        comp("a", "a");
        comp("a", "A");
        comp("a", "b");
    }
    
    static void comp(final String a, final String b) {
        System.out.println(StringUtils.repeat("*", 50));
        System.out.println("a: " + a);
        System.out.println("b: " + b);
        for (final CaseSensitivity cs : CaseSensitivity.values()) {
            System.out.println(cs);
            final Equator<String> eq = cs.equator();
            System.out.println(" eq: " + eq.equate(a, b));
            System.out.println("  a: " + eq.hash(a));
            System.out.println("  b: " + eq.hash(b));
        }
    }
    
}
