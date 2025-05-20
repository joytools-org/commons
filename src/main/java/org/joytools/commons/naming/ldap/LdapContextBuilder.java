/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author AndreaR
 */
@FunctionalInterface
public interface LdapContextBuilder {
    
    /**
     * 
     * @return
     * @throws NamingException 
     */
    public LdapContext build() throws NamingException;
    
}
