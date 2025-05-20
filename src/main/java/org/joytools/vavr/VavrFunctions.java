/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.vavr;

import io.vavr.Function1;
import io.vavr.Function2;
import io.vavr.Tuple2;
import io.vavr.collection.TreeMap;
import io.vavr.collection.TreeSet;
import io.vavr.control.Either;
import io.vavr.control.Option;
import java.util.Objects;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class VavrFunctions {
 
            
    /**
     * 
     * @param <V>
     * @return 
     */
    public final static <V> Function2<V, V, V> mergeSameValues() {
        return (Function2<V, V, V>)((Function2)MERGE_SAME_VALUES);
    }
 
    /**
     * 
     * @param <V>
     * @return 
     */
    public final static <V> Function2<V, V, V> mergeNoDuplicates() {
        return (Function2<V, V, V>)((Function2)MERGE_NO_DUPLICATES);
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public final static <V> Function2<V, V, V> mergeKeepFirst() {
        return (Function2<V, V, V>)((Function2)MERGE_KEEP_FIRST);
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public final static <V> Function2<V, V, V> mergeKeepLast() {
        return (Function2<V, V, V>)((Function2)MERGE_KEEP_LAST);
    }

    /**
     * 
     */
    final static Function2<?, ?, ?> MERGE_SAME_VALUES = (a, b) -> {
        if (Objects.equals(a, b)) {
            return a;
        }
        throw new IllegalArgumentException("Duplicated values: " + a + ", " + b);
    };

    /**
     * 
     */
    final static Function2<?, ?, ?> MERGE_NO_DUPLICATES = (a, b) -> {
        throw new IllegalArgumentException("Duplicated values: " + a + ", " + b);
    };

    /**
     * 
     */
    final static Function2<?, ?, ?> MERGE_KEEP_FIRST = (a, b) -> {
        return a;
    };

    /**
     * 
     */
    final static Function2<?, ?, ?> MERGE_KEEP_LAST = (a, b) -> {
        return b;
    };

}
