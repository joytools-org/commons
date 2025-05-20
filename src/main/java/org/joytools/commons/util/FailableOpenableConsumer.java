/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import org.apache.commons.lang3.function.FailableConsumer;

/**
 *
 * @author AndreaR
 * @param <T>
 * @param <E>
 */
public interface FailableOpenableConsumer<T, E extends Exception> extends FailableOpenable<E>, FailableConsumer<T, E> {
    
}
