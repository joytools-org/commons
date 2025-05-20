/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import java.util.Objects;
import javax.naming.InvalidNameException;
import javax.naming.ldap.LdapName;
import org.apache.commons.lang3.StringUtils;
import java.util.function.Predicate;

/**
 *
 * @author AndreaR
 */
public class DistinguishedNamePredicates {
    
    public DistinguishedNamePredicates() {
    }
    
    /**
     * 
     * @param <S>
     * @param container
     * @return 
     * @throws javax.naming.InvalidNameException 
     */
    public static <S extends CharSequence> Predicate<S> inContainerSubTree(final S container) throws InvalidNameException {
        final LdapName containerName = LdapNames.of(Objects.toString(container));
        final String containerString = StringUtils.lowerCase(containerName.toString());
        final String commaContainerString = "," + StringUtils.lowerCase(containerName.toString());
        final Predicate<S> p = (final S dn) -> {
            final String lowerCaseDN = StringUtils.lowerCase(Objects.toString(dn));
            if (lowerCaseDN == null) {
                return false;
            }
            if (lowerCaseDN.endsWith(commaContainerString) || lowerCaseDN.equals(containerString)) {
                return true;
            }
            return false;
        };
        return p;
    }

}
