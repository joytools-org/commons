/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.vavr;

import io.vavr.collection.Map;
import io.vavr.collection.Seq;
import java.util.Objects;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.MapAccessor;

/**
 *
 * @author AndreaR
 */
public class VavrAccessors {
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param map
     * @return 
     */
    public static <K, V> MapAccessor<K, V> of(final io.vavr.collection.Map<K, V> map) {
        return new VavrMapAccessor<>(map);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param seq
     * @return 
     */
    public static <K, V> ListAccessor<V> of(final io.vavr.collection.Seq<V> seq) {
        return new VavrSeqListAccessor<>(seq);
    }
    
}
