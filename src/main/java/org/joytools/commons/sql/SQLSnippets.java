/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.sql;

import org.apache.commons.text.TextStringBuilder;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.text.PropertyStrings;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class SQLSnippets {
    
    /**
     * 
     * @param md
     * @return 
     */
    public static String toDDL(final ResultSetColumnMetaData md) {
        final TextStringBuilder sb = new TextStringBuilder(md.getTypeName());
        if (StringUtils.containsIgnoreCase(sb, "char")) {
            sb.append(withParentheses(String.valueOf(md.getDisplaySize())));
        }
        return sb.toString();
    }

    /**
     * 
     * @param a
     * @param b
     * @param colNames
     * @return 
     */
    public static String colDiffCondition(final CharSequence a, final CharSequence b, final CharSequence... colNames) {
        return SQLSnippets.colDiffCondition(a, b, IterableAccessors.of(colNames));
    }

    /**
     * 
     * @param a
     * @param b
     * @param colName
     * @return 
     */
    static String colDiffCondition0(final CharSequence a, final CharSequence b, final CharSequence colName) {
        final String s = PropertyStrings.of(
            "((${a}.${c} IS NULL AND ${b}.${c} IS NOT NULL) OR " +
            "(${a}.${c} IS NOT NULL AND ${b}.${c} IS NULL) OR " +
            "(${a}.${c} <> ${b}.${c}))",
            "${a}", a,
            "${b}", b,
            "${c}", colName).resolve();
        return StringUtils.replaceRepeatedly(StringUtils.replaceChars(s, "\n\r", "  "), "OR  ", "OR ").trim();
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static String exprDiffCondition(final CharSequence a, final CharSequence b) {
        final String s = PropertyStrings.of(
            "(((${a}) IS NULL AND (${b}) IS NOT NULL) OR " +
            "((${a}) IS NOT NULL AND (${b}) IS NULL) OR " +
            "((${a}) <> (${b})))",                                            
            "${a}", a,
            "${b}", b).resolve();
        return StringUtils.replaceRepeatedly(StringUtils.replaceChars(s, "\n\r", "  "), "OR  ", "OR ").trim();
    }

    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    public static String exprEqualsCondition(final CharSequence a, final CharSequence b) {
        final String s = PropertyStrings.of(
            "(((${a}) IS NULL AND (${b}) IS NULL) OR ((${a}) = (${b})))",
            "${a}", a,
            "${b}", b).resolve();
        return StringUtils.replaceRepeatedly(StringUtils.replaceChars(s, "\n\r", "  "), "OR  ", "OR ").trim();
    }

    /**
     * 
     * @param a
     * @param b
     * @param colNames
     * @return 
     */
    public static String colDiffCondition(final CharSequence a, final CharSequence b, final Iterable<? extends CharSequence> colNames) {
        final IterableAccessor<CharSequence> v = IterableAccessors.ofAll(colNames).map(c -> colDiffCondition0(a, b, c));
        return or(v);
    }

    /**
     * 
     * @param terms
     * @return 
     */
    public static String and(final CharSequence... terms) {
        return concatTerms0(IterableAccessors.of(terms), " AND ");
    }

    /**
     * 
     * @param terms
     * @return 
     */
    public static String and(final Iterable<? extends CharSequence> terms) {
        return concatTerms0(terms, " AND ");
    }
    
    /**
     * 
     * @param terms
     * @return 
     */
    public static String or(final CharSequence... terms) {
        return concatTerms0(IterableAccessors.of(terms), " OR ");
    }

    /**
     * 
     * @param terms
     * @return 
     */
    public static String or(final Iterable<? extends CharSequence> terms) {
        return concatTerms0(terms, " OR ");
    }

    /**
     * 
     * @param terms
     * @param op
     * @return 
     */
    static String concatTerms0(final Iterable<? extends CharSequence> terms, final CharSequence op) {
        if (terms == null) {
            return StringUtils.EMPTY;
        }
        final StringBuilder sb = new StringBuilder();
        for (final CharSequence s : terms) {
            final String t = StringUtils.trimToNull(s);
            if (t != null) {
                if (sb.length() > 0) {
                    sb.append(op);
                }
                sb.append(withParentheses(s));
            }
        }
        return withParentheses(sb);
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
        if (s.startsWith("(") 
                && s.endsWith(")")
                && !StringUtils.containsAny(StringUtils.slice(s, 1, -1), '(', ')')) {
            return s;
        }
        return "(" + s + ")";
    }
    
    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        /* System.out.println(withParentheses(""));
        System.out.println(withParentheses("a = 1"));
        System.out.println(withParentheses("(a = 1)"));
        System.out.println(withParentheses("(a = 1) AND (b = 2)")); */
        System.out.println(and());
        System.out.println(and("a = 1"));
        System.out.println(and("a = 1", "b = 2"));
        System.out.println(or());
        System.out.println(or("a = 1"));
        System.out.println(or("(a = 1)", "b = 2")); 
    }

}
