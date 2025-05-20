/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import io.vavr.control.Option;

/**
 *
 * @author AndreaR
 * @param <E>
 */
public interface TypeSafeVariant<E> {
    
    public void set(final E value);
 
    public E get();
    
    public Option<Class> type();
    
}
