/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.ldap;

/**
 *
 * @author AndreaR
 */
public class LdapSearchOptions {
    
    LdapSearchOptions() {
    }
    
    /**
     * 
     * @return 
     */
    public static LdapSearchOptions empty() {
        return new LdapSearchOptions();
    }

    /**
     * 
     * @return 
     */
    public static LdapSearchOptions of() {
        return new LdapSearchOptions();
    }
    
    /**
     * 
     * @param pageSize
     * @return 
     */
    public static LdapSearchOptions of(final int pageSize) {
        return new LdapSearchOptions().pageSize(pageSize);
    }

    /**
     * 
     * @param pageSize
     * @return 
     */
    public LdapSearchOptions pageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }
    
    /**
     * 
     * @param fetchLimit
     * @return 
     */
    public LdapSearchOptions fetchLimit(final long fetchLimit) {
        this.fetchLimit = fetchLimit;
        return this;
    }

    /**
     * 
     * @return 
     */
    public int pageSize() {
        return pageSize;
    }
    
    /**
     * 
     * @return 
     */
    public long fetchLimit() {
        return fetchLimit;
    }

    private long fetchLimit = -1;
    
    private int pageSize = 0;
        
}
