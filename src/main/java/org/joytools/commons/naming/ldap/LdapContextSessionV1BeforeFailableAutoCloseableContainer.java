/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import org.joytools.commons.lang.ExceptionUtils;
import org.joytools.commons.util.DefaultAutoCloseableContainer;

public abstract class LdapContextSessionV1BeforeFailableAutoCloseableContainer extends DefaultAutoCloseableContainer {
    
    /**
     * 
     * @return 
     * @throws javax.naming.NamingException 
     */
    public abstract LdapContext ldapContext() throws NamingException;
    
    /**
     * 
     * @throws javax.naming.NamingException
     */
    @Override
    public void close() throws NamingException {
        try {
            super.close();
        } catch (final NamingException sqle) {
            throw sqle;
        } catch (final Exception e) {
            ExceptionUtils.wrapAndThrow(e);
        }
    }
    
}
