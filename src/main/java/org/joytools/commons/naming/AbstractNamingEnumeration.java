/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.naming;

import org.joytools.commons.collections.AbstractFailableIterator;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public abstract class AbstractNamingEnumeration<T> extends AbstractFailableIterator<T, NamingException> implements NamingEnumeration<T> {
      
    @Override
    public boolean hasMoreElements() {
        try {
            return hasMore();
        } catch (final NamingException ne) {
            throw new UncheckedNamingException(ne);
        }
    }
    
    @Override
    public T nextElement() {
        try {
            return next();
        } catch (final NamingException ne) {
            throw new UncheckedNamingException(ne);
        }
    }

    @Override
    public boolean hasMore() throws NamingException {
        return hasNext();
    }
    
}
