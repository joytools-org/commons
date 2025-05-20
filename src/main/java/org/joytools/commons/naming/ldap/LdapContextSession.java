/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import org.joytools.commons.util.FailableAutoCloseableContainer;

/**
 * 
 * @author AndreaR
 */
public abstract class LdapContextSession extends FailableAutoCloseableContainer<NamingException> {
    
    /**
     * 
     * @return 
     * @throws javax.naming.NamingException 
     */
    public abstract LdapContext ldapContext() throws NamingException;
       
}
