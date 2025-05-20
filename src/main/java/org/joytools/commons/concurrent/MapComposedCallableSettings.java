/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.concurrent;

import io.vavr.collection.Map;
import java.util.concurrent.Callable;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface MapComposedCallableSettings<K, V> extends AbstractComposedCallableSettings<K, V> {
    
    public Map<K, Callable<V>> tasks();
    
}
