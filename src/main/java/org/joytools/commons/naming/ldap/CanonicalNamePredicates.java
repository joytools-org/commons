/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import io.vavr.collection.HashSet;
import io.vavr.collection.Set;
import java.util.function.Predicate;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class CanonicalNamePredicates {
    
    /**
     * 
     */
    public CanonicalNamePredicates() {
    }
    
    private final static Set<String> RDN_ITEMS = HashSet.of(
        "CN=", "OU=", "O=", "DC=");
    
    /**
     * 
     * @param <S>
     * @param canName
     * @return 
     */
    public static <S extends CharSequence> S checkCanonicalName(final S canName) {
        if (StringUtils.containsAnyIgnoreCase(canName, RDN_ITEMS)) {
            throw new IllegalArgumentException(canName + " is not a canonicalName");
        }
        return canName;
    }
    
    /**
     * 
     * @param <S>
     * @param ou
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> inOrganizationUnit(final S ou) {
        return inOrganizationUnit(ou, false);
    }
    
    /**
     * 
     * @param <S>
     * @param ou
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> inRelativeOrganizationUnit(final S ou) {
        return inOrganizationUnit(ou, true);
    }

    /**
     * 
     * @param <S>
     * @param ou
     * @param relative
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> inOrganizationUnit(final S ou,
            final boolean relative) {
        if (StringUtils.isEmpty(ou)) {
            throw new IllegalArgumentException("No OU specified");
        }
        checkCanonicalName(ou);
        final String ouString = StringUtils.lowerCase(StringUtils.trimToNull(ou.toString()));
        if (StringUtils.isEmpty(StringUtils.strip(ouString, " /"))) {
            throw new IllegalArgumentException("Invalid OU name: " + ou);
        }
        final String searchFor = ouString; // relative ? relativeOUString : absoluteOUString;
        final Predicate<S> p = (final S canonicalName) -> {
            final String canonicalNameString = canonicalName == null ? null :
                    StringUtils.lowerCase(canonicalName.toString());
            if (canonicalNameString == null) {
                return false;
            }
            final int index = canonicalNameString.indexOf(searchFor);
            if (index < 0 || !relative && index > 0) {
                return false;
            }
            final boolean startSlash = searchFor.startsWith("/");
            final boolean endSlash = searchFor.endsWith("/");
            if (startSlash && endSlash) {
                return true;
            }
            if (!startSlash && index > 0 && canonicalNameString.charAt(index - 1) != '/') {
                return false;
            }
            if (!endSlash) {
                final String remaining =
                        StringUtils.trimToEmpty(canonicalNameString.substring(index + searchFor.length()));
                if (remaining.length() > 0 && remaining.charAt(0) != '/') {
                    return false;
                }
            }
            return true;
        };
        return p;
    }
    
    /**
     * 
     * @param <S>
     * @param likeExpression
     * @return 
     */
    public static <S extends CharSequence> Predicate<S> rdnLike(final S likeExpression) {
        final String likeExprString = StringUtils.lowerCase(likeExpression == null ? null : likeExpression.toString());
        final Predicate<String> p = StringPredicates.like(likeExprString, false);
        return (final S canonicalName) -> {
            final String str = canonicalName == null ? null : StringUtils.lowerCase(canonicalName.toString());
            if (str == null) {
                return false;
            }
            checkCanonicalName(canonicalName);
            final String[] items = StringUtils.splitPreserveAllTokens(str, '/');
            for (final String item : items) {
                if (p.test(item)) {
                    return true;
                }
            }
            return false;
        };
    }
    
}
