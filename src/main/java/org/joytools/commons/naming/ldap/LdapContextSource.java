/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

import java.util.Objects;
import javax.naming.NamingException;
import javax.naming.ldap.LdapContext;
import org.joytools.commons.function.FailableAutoCloseables;

/**
 *
 * @author AndreaR
 */
public abstract class LdapContextSource {

    /**
     * 
     * @return 
     * @throws javax.naming.NamingException 
     */
    public abstract LdapContextSession newSession() throws NamingException;
    
    /**
     * 
     * @param ldapCtxBuilder
     * @return 
     */
    public static LdapContextSource of(final LdapContextBuilder ldapCtxBuilder) {
        Objects.requireNonNull(ldapCtxBuilder, "Ldap Context Builder");
        return new LdapContextSource() {
            @Override
            public LdapContextSession newSession() {
                return new LdapContextSession() {
                    @Override
                    public LdapContext ldapContext() throws NamingException {
                        if (m_ldapCtx == null) {
                            m_ldapCtx = ldapCtxBuilder.build();
                            /*
                            final FailableAutoCloseable<NamingException> ac = new FailableAutoCloseable<NamingException>() {
                                @Override
                                public void close() throws NamingException {
                                    m_ldapCtx.close();
                                }
                            };
                            final FailableAutoCloseable<NamingException> ac1 = FailableAutoCloseable.of(m_ldapCtx::close);*/
                            dispose(m_ldapCtx, FailableAutoCloseables.of(m_ldapCtx::close));
                        }
                        return m_ldapCtx;
                    }
                    private LdapContext m_ldapCtx = null;
                };
            }
        };
    }
    
    /**
     * 
     * @param ldapCtx
     * @return 
     */
    public static LdapContextSource of(final LdapContext ldapCtx) {
        Objects.requireNonNull(ldapCtx, "Ldap Context");
        return new LdapContextSource() {
            @Override
            public LdapContextSession newSession() {
                return new LdapContextSession() {
                    @Override
                    public LdapContext ldapContext() throws NamingException {
                        return ldapCtx;
                    }
                };
            }
        };
    }

}
