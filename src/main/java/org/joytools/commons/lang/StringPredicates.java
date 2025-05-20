/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.function.Predicate;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.interop.Vavr;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class StringPredicates {
 
    /**
     * 
     * @param cs
     * @return 
     */
    public static boolean isLikePattern(final CharSequence cs) {
        return StringUtils.containsAny(cs, "*?_%");
    }    

    /**
     * 
     * @param likeExpr
     * @param ignoreCase
     * @return 
     */
    public static Pattern likeToPattern(final CharSequence likeExpr,
            final boolean ignoreCase) {
        int len;
        if (likeExpr == null || (len = likeExpr.length()) == 0) {
            throw new IllegalArgumentException("Like expression is null");
        }
        final StringBuilder sb = new StringBuilder(len * 2);
        for (int i = 0; i < len; i++) {
            char c = likeExpr.charAt(i);
            switch (c) {
                case '*':
                    c = '%';
                    break;
                case '?':
                    c = '_';
                    break;
            }
            switch (c) {
                case '%':
                    sb.append(".*?");
                    break;
                case '_':
                    sb.append('.');
                    break;
                default:
                    if ("[](){}.*+?$^|#\\".indexOf(c) != -1) {
                        sb.append("\\");
                    }
                    sb.append(c);
                    break;
            }
        }
        // System.out.println("==> Regex: " + sb);
        int flags = Pattern.DOTALL;
        if (ignoreCase) {
            flags |= Pattern.CASE_INSENSITIVE;
        }
        return Pattern.compile(sb.toString(), flags);
    }

    /**
     * 
     * @param <S>
     * @param <C>
     * @param set
     * @param makeLowerCase
     * @return 
     */ /*
    public static <S extends CharSequence, C extends Collection<String>> Predicate<S> in(
            final C set,
            final boolean makeLowerCase) {
        return (final S input) -> in(set, input == null ? null : input.toString(), makeLowerCase);
    }     */
    
    /**
     * 
     * @param <S>
     * @param set
     * @param makeLowerCase
     * @return 
     */ 
    @Deprecated
    public static <S extends CharSequence> Predicate<S> old_in(
            final Iterable<String> set,
            final boolean makeLowerCase) {
        final IterableAccessor<String> itr = IterableAccessors.from(set);
        return (final S input) -> old_in0(itr, input == null ? null : input.toString(), makeLowerCase);
    }     

    /**
     * 
     * @param <S>
     * @param elements
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isAnyOf(
            final Iterable<String> elements) {
        final IterableAccessor<String> itr = IterableAccessors.from(elements);
        return (final S input) -> isAnyOf(itr, input == null ? null : input.toString());
    }     

    /**
     * 
     * @param <S>
     * @param elements
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isAnyOf(
            final CharSequence... elements) {
        return isAnyOf(IterableAccessors.sorted(false, elements));
    }     
    
    /**
     * 
     * @param <S>
     * @param elements
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isAnyOfIgnoreCase(
            final CharSequence... elements) {
        return isAnyOf(IterableAccessors.sortedIgnoreCase(elements));
    }     

    /**
     * 
     * @param <S>
     * @param elements
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isAnyOfIgnoreCase(
            final Iterable<String> elements) {
        if (CaseSensitivity.from(elements) == CaseSensitivity.INSENSITIVE) {
            return isAnyOf(elements);
        }
        // return isAnyOf(Iterations.from(elements).to(TreeSet(CaseSensitivity.INSENSITIVE.comparator()));
        return isAnyOf(IterableAccessors.from(elements).to(Vavr.treeSetIgnoreCase()));
    }     

    /**
     * 
     * @param <S>
     * @param elements
     * @param ignoreCase
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isAnyOf(
            final Iterable<String> elements,
            final boolean ignoreCase) {
        return ignoreCase ? isAnyOfIgnoreCase(elements) : isAnyOf(elements);
    }     

    /**
     * 
     * @param <I>
     * @param itr
     * @param input
     * @param makeLowerCase
     * @return 
     */ /*
    static <I extends Iterable<String>> boolean in(
            final I itr,
            final CharSequence input,
            final boolean makeLowerCase) {
        String s;
        if (itr == null
                || input == null
                || (s = input.toString()) == null) {
            return false;
        }
        final Iteration<String> coll = Iterations.from(itr);
        if (makeLowerCase) {
            s = s.toLowerCase();
        }
        return coll.contains(s);
    }     */ 
    @Deprecated
    static boolean old_in0(
            final IterableAccessor<String> itr,
            final CharSequence input,
            final boolean makeLowerCase) {
        String s;
        if (itr == null
                || input == null
                || (s = input.toString()) == null) {
            return false;
        }
        // final Iteration<String> coll = Iterations.from(itr);
        if (makeLowerCase) {
            s = s.toLowerCase();
        }
        return itr.contains(s);
    }      

    /**
     * 
     * @param itr
     * @param input
     * @param makeLowerCase
     * @return 
     */
    static boolean isAnyOf(
            final IterableAccessor<String> itr,
            final CharSequence input) {
        String s;
        if (itr == null
                || input == null
                || (s = input.toString()) == null) {
            return false;
        }
        /*
        if (makeLowerCase) {
            s = s.toLowerCase();
        } */
        return itr.contains(s);
    }      

    /**
     * 
     * @param <S>
     * @param searchSequence
     * @param ignoreCase
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> contains(
            final S searchSequence,
            final boolean ignoreCase) {
        return (final S input) -> {
            if (ignoreCase) {
                if (StringUtils.containsIgnoreCase(input, searchSequence)) {
                    return true;
                }
            } else {
                if (StringUtils.contains(input, searchSequence)) { 
                    return true;
                }
            }
            return false;
        };
    }    

    /**
     * 
     * @param <S>
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> distinct() {
        return distinct(CaseSensitivity.SENSITIVE);
    }    

    /**
     * 
     * @param <S>
     * @param ignoreCase
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> distinct(final boolean ignoreCase) {
        return distinct(CaseSensitivity.forIgnoreCase(ignoreCase));
    }    

    /**
     * 
     * @param <S>
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> distinctIgnoreCase() {
        return distinct(CaseSensitivity.INSENSITIVE);
    }    

    /**
     * 
     * @param <S>
     * @param cs
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> distinct(final CaseSensitivity cs) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        final Set<String> unique = cs.newSet();
        return s -> {
            return unique.add(StringUtils.toString(s));
        };
    }    
    
    /**
     * 
     * @param <S>
     * @param searchSequence
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> contains(
            final S searchSequence) {
        return contains(searchSequence, false);
    }    
    
    /**
     * 
     * @param <S>
     * @param searchSequence
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> containsIgnoreCase(
            final S searchSequence) {
        return contains(searchSequence, true);
    }    

    /**
     * 
     * @param <S>
     * @param endsWith
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> endsWith(
            final S endsWith) {
        return endsWith(endsWith, false);
    }

    /**
     * 
     * @param <S>
     * @param endsWith
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> endsWithIgnoreCase(
            final S endsWith) {
        return endsWith(endsWith, true);
    }

    /**
     *
     * @param <S>
     * @param endsWith CharSequence
     * @param ignoreCase boolean
     * @return Predicate
     */
    public static <S extends CharSequence> Predicate<S> endsWith(
            final S endsWith, 
            final boolean ignoreCase) {
        return (final S input) -> {
            if (ignoreCase) {
                return StringUtils.endsWithIgnoreCase(input, endsWith);
            } else {
                return StringUtils.endsWith(input, endsWith);
            }
        };
    }
    
    /**
     * 
     * @param <S>
     * @param literal
     * @param ignoreCase
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> equals(
            final S literal,
            final boolean ignoreCase) {
        return (final S cs) -> {
            if (ignoreCase) {
                return StringUtils.equalsIgnoreCase(literal, cs);
            } else {
                return StringUtils.equals(literal, cs);
            }
        };
    }

    /**
     * 
     * @param <S>
     * @param literal
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> equals(
            final S literal) {
        return equals(literal, false);
    }
    
    /**
     * 
     * @param <S>
     * @param literal
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> equalsIgnoreCase(
            final S literal) {
        return equals(literal, true);
    }

    /**
     * 
     * @param pattern
     * @param flags
     * @return 
     */
    static Pattern newPattern(
            final CharSequence pattern,
            final int flags) {
        Objects.requireNonNull(pattern, "Pattern");
        return Pattern.compile(pattern.toString(), flags);
    } 

    /**
     * 
     * @param pattern
     * @param flags
     * @return 
     */
    static Pattern newIgnoreCasePattern(
            final CharSequence pattern,
            final int flags) {
        return newPattern(pattern, flags | Pattern.CASE_INSENSITIVE);
    } 

    /**
     * 
     * @param <S>
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isBlank() {
        return (Predicate<S>)IS_BLANK;
    }
    
    /**
     * 
     * @param <S>
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isNotBlank() {
        return (Predicate<S>)IS_NOT_BLANK;
    }

    /**
     * 
     */
    private final static Predicate<CharSequence> IS_BLANK = StringUtils::isBlank;

    /**
     * 
     */
    private final static Predicate<CharSequence> IS_NOT_BLANK = StringUtils::isNotBlank;

    /**
     * 
     * @param <S>
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isEmpty() {
        return (Predicate<S>)IS_EMPTY;
    }

    /**
     * 
     * @param <S>
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isNotEmpty() {
        return (Predicate<S>)IS_NOT_EMPTY;
    }

    /**
     * 
     * @param <S>
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isNumeric() {
        return (Predicate<S>)IS_NUMERIC;
    }

    /**
     * 
     * @param <S>
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> isNotNumeric() {
        return (Predicate<S>)IS_NOT_NUMERIC;
    }

    /**
     * 
     */
    private final static Predicate<CharSequence> IS_EMPTY = StringUtils::isEmpty;

    /**
     * 
     */
    private final static Predicate<CharSequence> IS_NOT_EMPTY = StringUtils::isNotEmpty;

    /**
     * 
     */
    private final static Predicate<CharSequence> IS_NUMERIC = StringUtils::isNumeric;

    /**
     * 
     */
    private final static Predicate<CharSequence> IS_NOT_NUMERIC = IS_NUMERIC.negate();

    /**
     * 
     * @param <S>
     * @param pattern
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> matches(
            final Pattern pattern) {
        Objects.requireNonNull(pattern, "Pattern");
        return (final S cs) -> cs != null && pattern.matcher(cs).matches();
    }

    /**
     * 
     * @param <S>
     * @param pattern
     * @param flags
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> matches(
            final CharSequence pattern,
            final int flags) {
        return matches(newPattern(pattern, flags));
        // Objects.requireNonNull(pattern, "Pattern");
        // return (final S cs) -> cs != null && pattern.matcher(cs).matches();
    }

    /**
     * 
     * @param <S>
     * @param likeExpr
     * @param ignoreCase
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> like(
            final S likeExpr,
            final boolean ignoreCase) {
        if (isLikePattern(likeExpr)) {
            return matches(likeToPattern(likeExpr, ignoreCase));
        } else {
            return equals(likeExpr, ignoreCase);
        }
    }    

    /**
     * 
     * @param <S>
     * @param likeExpr
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> like(
            final S likeExpr) {
        return like(likeExpr, false);
    }    

    /**
     * 
     * @param <S>
     * @param likeExpr
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> likeIgnoreCase(
            final S likeExpr) {
        return like(likeExpr, true);
    }    

    /**
     * 
     * @param <S>
     * @param startsWith
     * @param ignoreCase
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> startsWith(
            final S startsWith, 
            final boolean ignoreCase) {
        return (final S input) -> {
            if (ignoreCase) {
                return StringUtils.startsWithIgnoreCase(input, startsWith);
            } else {
                return StringUtils.startsWith(input, startsWith);
            }
        };
    }

    /**
     * 
     * @param <S>
     * @param startsWith
     * @param ignoreCase
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> startsWithAny(
            final S startsWith, 
            final boolean ignoreCase) {
        return (final S input) -> {
            if (ignoreCase) {
                return StringUtils.startsWithAnyIgnoreCase(input, startsWith);
            } else {
                return StringUtils.startsWithAny(input, startsWith);
            }
        };
    }

    /**
     * 
     * @param <S>
     * @param startsWith
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> startsWith(
            final S startsWith) {
        return startsWith(startsWith, false);
    }

    /**
     * 
     * @param <S>
     * @param startsWith
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> startsWithAny(
            final S... startsWith) {
        return (final S input) -> {
            return StringUtils.startsWithAny(input, startsWith);
        };
    }

    /**
     * 
     * @param <S>
     * @param startsWith
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> startsWithIgnoreCase(
            final S startsWith) {
        return startsWith(startsWith, true);
    }
    
    /**
     * 
     * @param <S>
     * @param startsWith
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> startsWithAnyIgnoreCase(
            final S... startsWith) {
        return (final S input) -> {
            return StringUtils.startsWithAnyIgnoreCase(input, startsWith);
        };
    }

    /**
     * 
     * @param args 
     */
    public static void main(final String... args) {
        final Predicate<String> p = StringPredicates.isAnyOfIgnoreCase("uno", "DUE", "tre", "quattro", "cinque", "sei");
        final boolean b = p.test("due");
        System.out.println(b);
    }
    
}
