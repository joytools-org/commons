/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.naming;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.joytools.commons.accessors.StringMapAccessor;

/**
 *
 * @author AndreaR
 */
class AttrsAccessorImpl extends NakedAttributesMutator implements StringMapAccessor<Object> {
    
    public AttrsAccessorImpl(final Attributes attr) {
        super(attr);
    }

    @Override
    protected Object get0(final Attribute attr) {
        return NamingAccessors.getObjectOrListAccessor(attr);
    }
    
}
