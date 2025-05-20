/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import com.google.common.collect.AbstractIterator;
import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import java.util.Iterator;
import java.util.Objects;
import javax.naming.ldap.Rdn;
import javax.naming.ldap.LdapName;
import org.joytools.commons.lang.StringUtils;


/**
 *
 * @author AndreaR
 */
public class LdapNames {
    
    /**
     * 
     */
    public LdapNames() {
    }
    
    /**
     * 
     * @param dn
     * @return 
     */
    public static LdapName of(final CharSequence dn) {
        final String s = StringUtils.trimToEmpty(dn);
        if (s.isEmpty()) {
            return null;
        }
        return LdapFunctions.toLdapName().apply(dn);
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public static boolean isEmpty(final LdapName name) {
        return name == null || name.isEmpty();
    }
    
    /**
     * CN=Andrea Rombaldi,OU=Users,DC=LUXGROUP,DC=net will return 
     * CN=Andrea Rombaldi
     *
     * @param name
     * @return
     */
    public static Rdn getRdn(final LdapName name) {
        if (isEmpty(name)) {
            return null;
        }
        return name.getRdn(name.size() - 1);
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    static Iterator<Rdn> toIterator(final LdapName name) {
        return new AbstractIterator<Rdn>() {
            int index = 0;
            @Override
            protected Rdn computeNext() {
                if (index < name.size()) {
                    return name.getRdn(index++);
                }
                return endOfData();
            }
        };
    }

    /**
     * 
     * @param name
     * @return 
     */
    @Deprecated
    public static Vector<Rdn> toRdnVectorV1(final LdapName name) {
        Objects.requireNonNull(name, "Ldap Name");
        return Vector.ofAll(name.getRdns());
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public static Rdn getCNOrNull(final LdapName name) {
        return getCNOrNull(getRdn(name));
    }

    /**
     * 
     * @param r
     * @return 
     */
    public static Rdn getCNOrNull(final Rdn r) {
        if (r == null || !"cn".equalsIgnoreCase(r.getType())) {
            return null;
        }
        return r;
    }

    /**
     * 
     * @param r
     * @return 
     */
    public static Option<Rdn> getCN(final Rdn r) {
        if (r == null || !"cn".equalsIgnoreCase(r.getType())) {
            return None();
        }
        return Some(r);
    }

    /**
     * 
     * @param r
     * @return 
     */
    public static Object getValue(final Rdn r) {
        return r == null ? null : r.getValue();
    }
    
    /**
     * 
     * @param r
     * @return 
     */
    public static String getStringValue(final Rdn r) {
        final Object o = r == null ? null : r.getValue();
        return o == null ? null : o.toString();
    }
    
    /**
     * 
     * @param r
     * @return 
     */
    public static String getStringValueToNull(final Rdn r) {
        final String s = getStringValue(r);
        if (s == null || s.length() == 0) {
            return null;
        }
        return s;
    }
 
    /**
     * 
     * @param r
     * @return 
     */
    public static String getStringValueToEmpty(final Rdn r) {
        final String s = getStringValue(r);
        if (s == null || s.length() == 0) {
            return StringUtils.EMPTY;
        }
        return s;
    }

    /**
     * CN=Andrea Rombaldi,OU=Users,DC=LUXGROUP,DC=net will return
     * OU=Users,DC=LUXGROUP,DC=net
     *
     * @param name
     * @return
     */
    public static LdapName containerOf(final LdapName name) {
        if (isEmpty(name)) {
            return null;
        }
        return (LdapName)name.getPrefix(name.size() - 1);
    }
    
}
