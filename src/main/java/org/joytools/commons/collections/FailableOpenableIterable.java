/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import org.joytools.commons.util.FailableOpenable;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <E>
 */
public interface FailableOpenableIterable<T, E extends Exception> extends FailableOpenable<E>, Iterable<T> {
    
}
