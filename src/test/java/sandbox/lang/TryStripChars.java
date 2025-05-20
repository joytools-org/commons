/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.lang;

import com.google.common.base.CharMatcher;
import org.joytools.commons.lang.CharFilter;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TryStripChars {
    
    public static void main(final String... args) {
        final String s1 = "123";
        final CharMatcher f = CharFilter.inRange('A', 'Z').negate();
        final String s2 = StringUtils.removeChars(s1, f);
        System.out.println("'" + s2 + "'");
        // final String s2 = StringUtils.stripChars(s1, f);
    }
    
}
