/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.naming.ldap;

import java.util.Arrays;
import java.util.List;
import org.joytools.commons.collections.IterableAccessors;
// import org.joytools.commons.collections.StreamIterables;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class LdapSearchFilters {

    /**
     * 
     * @return 
     */
    public static String existingObjectClassFilter() {
        return "(objectClass=*)";
    }
    
    /**
     *
     * @param terms
     * @return
     */
    public static String or(final Iterable<? extends CharSequence> terms) {
        final String s = concatTerms0(terms);
        if (s.isEmpty()) {
            return s;
        }
        return "(|" + s + ")";
    }

    /**
     *
     * @param <S>
     * @param terms
     * @return
     */
    public static <S extends CharSequence> String or(final S... terms) {
        return or(IterableAccessors.of(terms));
    }

    /**
     *
     * @param <S>
     * @param term
     * @return
     */
    public static <S extends CharSequence> String not(final S term) {
        if (StringUtils.isEmpty(term)) {
            return StringUtils.EMPTY;
        }
        return "(!" + withParentheses(term) + ")";
    }

    /**
     *
     * @param terms
     * @return
     */
    public static String and(final Iterable<? extends CharSequence> terms) {
        final String s = concatTerms0(terms);
        if (s.isEmpty()) {
            return s;
        }
        return "(&" + s + ")";
    }

    /**
     *
     * @param <S>
     * @param terms
     * @return
     */
    public static <S extends CharSequence> String and(final S... terms) {
        return and(IterableAccessors.of(terms));
    }

    /**
     *
     * @param terms
     * @param op
     * @return
     */
    static String concatTerms0(final Iterable<? extends CharSequence> terms) {
        if (terms == null) {
            return StringUtils.EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        for (final CharSequence s : terms) {
            final String t = StringUtils.trimToNull(s);
            if (t != null) {
                sb.append(withParentheses(s));
            }
        }
        return sb.toString();
    }

    /**
     *
     * @param cs
     * @return
     */
    public static String withParentheses(final CharSequence cs) {
        final String s = StringUtils.trim(StringUtils.toString(cs));
        if (StringUtils.isEmpty(s) || s.length() <= 3) {
            return s;
        }
        if (s.startsWith("(") && s.endsWith(")") && !StringUtils.containsAny(StringUtils.slice(s, 1, -1), '(', ')')) {
            return s;
        }
        return "(" + s + ")";
    }
    
    /**
     * 
     * @param args 
     */
    public final static void main(final String... args) {
        final List<String> filters = Arrays.asList("(objectClass=*)",
                "",
                "!(employeeID=LWB2BUSER)");
        System.out.println(and(filters));
        System.out.println(or(filters));
    }

}
