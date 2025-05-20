/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.lang;

import java.util.Set;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class TryEqualsAny {
    
    public static void main(final String... args) {
        final Set<String> s = CaseSensitivity.SENSITIVE.newSortedSet();
        s.add("A");
        s.add("B");
        s.add("C");
        final boolean b = StringUtils.equalsAnyIgnoreCase("a", s);
        System.out.println(b);
    }
}
