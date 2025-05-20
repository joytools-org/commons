/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.naming;

import javax.naming.directory.Attribute;
import org.joytools.commons.accessors.ListAccessor;

/**
 *
 * @author AndreaR
 */
class AttrAccessorImpl extends NakedAttributeMutator implements ListAccessor<Object> {
    
    public AttrAccessorImpl(final Attribute attr) {
        super(attr);
    }
    
}
