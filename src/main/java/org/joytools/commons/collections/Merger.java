/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import io.vavr.Function3;
import java.util.function.BinaryOperator;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public abstract class Merger<K, V> implements Function3<K, V, V, V> {
    
    public BinaryOperator<V> asBinaryOperator() {
        return binOp;
    }
    
    private final BinaryOperator<V> binOp = (v1, v2) -> apply(null, v1, v2);
    
}
