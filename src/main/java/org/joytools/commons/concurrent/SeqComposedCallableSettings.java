/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import io.vavr.collection.Seq;
import java.util.concurrent.Callable;

/**
 *
 * @author AndreaR
 * @param <V>
 */
@FunctionalInterface
public interface SeqComposedCallableSettings<V> extends AbstractComposedCallableSettings<Integer, V> {
    
    public Seq<Callable<V>> tasks();
    
}
