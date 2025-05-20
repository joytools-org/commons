/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.function.Function;

/**
 *
 * @author AndreaR
 */
public class AccessorFunctions {

    public static <K, V> Function<java.util.Map.Entry<K, V>, Tuple2<K, V>> mapEntryToTuple() {
        return (Function<java.util.Map.Entry<K, V>, Tuple2<K, V>>)(Function)MAPENTRY_2_TUPLE;
    }
    
    private final static Function<java.util.Map.Entry<Object, Object>, Tuple2<Object, Object>> MAPENTRY_2_TUPLE = Tuple::fromEntry;
            
}
