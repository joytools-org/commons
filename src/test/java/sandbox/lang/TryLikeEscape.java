/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sandbox.lang;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class TryLikeEscape {
 
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final String likeExpr = "_OP_*";
        // final Pattern pt = StringPredicates.likeToPattern(likeExpr, true);
        final Pattern pt = Pattern.compile("%OP%.*?", Pattern.CASE_INSENSITIVE);
        System.out.println("Pattern: " + pt.pattern());
        final Predicate<String> p1 = StringPredicates.like(likeExpr);
        for (final String s : Arrays.asList("_OP_CIAO", "COPPA")) {
            System.out.println(s + ": " + p1.test(s));
        }
        final Predicate<String> p2 = pt.asPredicate();
        for (final String s : Arrays.asList("_OP_CIAO", "COPPA", "%OP%CIAO")) {
            System.out.println(s + ": " + p2.test(s));
        }
    }
    
}
