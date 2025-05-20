/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import io.vavr.collection.Traversable;

/**
 *
 * @author AndreaR
 * @param <T>
 */
public interface DelegatedTraversable<T> extends Traversable<T>, DelegatedValue<T> {
    
}
