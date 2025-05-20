/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import org.joytools.commons.function.FailableProcessor;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <R>
 * @param <E>
 */
public interface FailableOpenableProcessor<T, R, E extends Exception> extends FailableOpenable<E>, FailableProcessor<T, R, E> {
    
}
