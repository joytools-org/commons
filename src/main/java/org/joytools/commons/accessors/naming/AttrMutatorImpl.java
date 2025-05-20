/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.naming;

import javax.naming.directory.Attribute;
import org.joytools.commons.accessors.ListMutator;

/**
 *
 * @author AndreaR
 */
class AttrMutatorImpl extends NakedAttributeMutator implements ListMutator<Object> {
    
    public AttrMutatorImpl(final Attribute attr) {
        super(attr);
    }
    
}
