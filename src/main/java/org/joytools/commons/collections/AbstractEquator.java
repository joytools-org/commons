/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import org.apache.commons.collections4.Equator;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractEquator<T> implements Equator<T> {

    @Override
    public int hash(final T o) {
        throw new UnsupportedOperationException("hashCode"); 
    }
    
}
