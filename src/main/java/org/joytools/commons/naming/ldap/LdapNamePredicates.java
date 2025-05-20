/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.naming.ldap;

import java.util.function.Function;
import java.util.function.Predicate;
import javax.naming.ldap.LdapName;
import javax.naming.ldap.Rdn;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.lang.StringPredicates;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class LdapNamePredicates {
    
    private final static Function<Rdn, String> RDN_TO_VALUE = r -> StringUtils.toString(r.getValue());
       
    /**
     * 
     * @param filter
     * @return 
     */
    public static Predicate<LdapName> anyRdnValue(final Predicate<CharSequence> filter) {
        return name -> IterableAccessors.from(name.getRdns()).map(RDN_TO_VALUE).findFirstOrNull(filter) != null;
    }

    /**
     * 
     * @param pattern
     * @return 
     */
    public static Predicate<LdapName> rdnValueEquals(final CharSequence pattern) {
        return anyRdnValue(StringPredicates.equals(pattern, true));
    }

    /**
     * 
     * @param pattern
     * @return 
     */
    public static Predicate<LdapName> rdnValueStartsWith(final CharSequence pattern) {
        return anyRdnValue(StringPredicates.startsWith(pattern, true));
    }

    /**
     * 
     * @param pattern
     * @return 
     */
    public static Predicate<LdapName> rdnValueEndsWith(final CharSequence pattern) {
        return anyRdnValue(StringPredicates.endsWith(pattern, true));
    }

    /**
     * 
     * @param pattern
     * @return 
     */
    public static Predicate<LdapName> rdnValueLike(final CharSequence pattern) {
        return anyRdnValue(StringPredicates.like(pattern, true));
    }
    
    /**
     * 
     * @param pattern
     * @return 
     */
    public static Predicate<LdapName> rdnValueContains(final CharSequence pattern) {
        return anyRdnValue(StringPredicates.contains(pattern, true));
    }

}
