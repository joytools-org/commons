/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.naming;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import org.apache.commons.lang3.function.FailableSupplier;

/**
 *
 * @author AndreaR
 */
@FunctionalInterface
public interface NamingEnumerationSupplier<E> extends FailableSupplier<NamingEnumeration<E>, NamingException> {
 
    @Override
    public NamingEnumeration<E> get() throws NamingException;
    
}
