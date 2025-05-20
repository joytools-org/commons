/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import java.util.Arrays;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TryShrink {
    
    public static void main(final String... args) {
        final String s = "Questa é una stringa di prova!";
        for (final int i : Arrays.asList(0, -1, 2, 5)) {
            System.out.println(i + ": '" + StringUtils.strip(s, i, i / 2) + "'");
        }
    }
    
}
