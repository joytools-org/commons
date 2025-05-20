/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TryStringSplit {
    
    public static void main(final String... args) {
        final String s = "Ciao Andrea!";
        System.out.println(s);
        
        final String[] r1 = StringUtils.splitAt(s, 3);
        System.out.println("split()");
        dump(r1);
        
        final String[] r2 = StringUtils.splitAll(s, 1, 4, -1);
        System.out.println("splitAll()");
        dump(r2);
    }
    
    static void dump(final String[] str) {
        int i = 0;
        for (final String s : str) {
            System.out.println("" + i + ": " + s);
        }
    }
}
