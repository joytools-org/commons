/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming.directory;

import javax.naming.directory.SearchControls;

/**
 *
 * @author AndreaR
 */
public enum SearchScope {
    
    OBJECT(SearchControls.OBJECT_SCOPE),
    ONELEVEL(SearchControls.ONELEVEL_SCOPE),
    SUBTREE(SearchControls.SUBTREE_SCOPE);
    
    SearchScope(final int value) {
        this.value = value;
    }
    
    /**
     * 
     * @return 
     */
    public int value() {
        return value;
    }
    
    private int value;

}
