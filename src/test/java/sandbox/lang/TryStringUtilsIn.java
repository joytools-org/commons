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
public class TryStringUtilsIn {
    
    public void main(final String... args) {
        final Iterable<String> strings = Arrays.asList("ciao", "CIAO", "prova");
        final boolean flag = StringUtils.in("CIAO", strings, false);
        System.out.println(flag);
    }
    
}
