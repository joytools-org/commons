/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sandbox.lang;

import org.apache.commons.text.StringTokenizer;
import org.apache.commons.text.matcher.StringMatcher;
import org.apache.commons.text.matcher.StringMatcherFactory;

/**
 *
 * @author AndreaR
 */
public class TryStringTokenizer {
    
    public static void main(final String... args) {
        final StringMatcher delimiters = StringMatcherFactory.INSTANCE.charSetMatcher(":,; \t\n\r{}");
        final StringMatcher quote = StringMatcherFactory.INSTANCE.noneMatcher();
        final StringTokenizer tok = new StringTokenizer();
    }
    
}
