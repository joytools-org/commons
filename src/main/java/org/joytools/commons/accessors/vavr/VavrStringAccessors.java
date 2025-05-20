/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.vavr;

import io.vavr.Tuple1;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.Tuple4;
import io.vavr.Tuple5;
import io.vavr.Tuple6;
import io.vavr.Tuple7;
import io.vavr.Tuple8;
import io.vavr.collection.Map;
import java.util.Objects;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class VavrStringAccessors {
    
    /**
     * 
     * @param <V>
     * @param map
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Map<String, V> map) {
        Objects.requireNonNull(map, "Map");
        final CaseSensitivity cs = CaseSensitivity.fromMap(map);
        return new VavrStringMapAccessor<V>(map) {
            @Override
            public CaseSensitivity caseSensitivity() {
                return cs;
            }
        };
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Tuple1<? extends V> tuple) {
        return VavrTupleStringMapAccessor.of(tuple);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Tuple2<? extends V, ? extends V> tuple) {
        return VavrTupleStringMapAccessor.of(tuple);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Tuple3<? extends V, ? extends V, ? extends V> tuple) {
        return VavrTupleStringMapAccessor.of(tuple);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Tuple4<? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        return VavrTupleStringMapAccessor.of(tuple);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Tuple5<? extends V, ? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        return VavrTupleStringMapAccessor.of(tuple);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Tuple6<? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        return VavrTupleStringMapAccessor.of(tuple);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Tuple7<? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        return VavrTupleStringMapAccessor.of(tuple);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final Tuple8<? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        return VavrTupleStringMapAccessor.of(tuple);
    }

}
