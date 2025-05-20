/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import java.util.Objects;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public class MutatorUtils {
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param accessor
     * @return 
     */
    public static <K, V> MapAccessor<K, V> castAsMapAccessor(final Accessor<V> accessor) {
        return (MapAccessor<K, V>)accessor;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param accessor
     * @return 
     */
    public static <K, V> MapMutator<K, V> castAsMapMutator(final Mutator<V> accessor) {
        return (MapMutator<K, V>)accessor;
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param accessor
     * @return 
     */
    public static <K, V> FixedSizeListMutator<V> castAsFixedListMutator(final Mutator<V> accessor) {
        return (FixedSizeListMutator<V>)accessor;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param accessor
     * @return 
     */
    public static <K, V> ListMutator<V> castAsListMutator(final Mutator<V> accessor) {
        return (ListMutator<V>)accessor;
    }

    /**
     * \
     * @param <K>
     * @param <V>
     * @param accessor
     * @return 
     */
    public static <K, V> IndexedMapMutator<K, V> castAsIndexedMapMutator(final Mutator<V> accessor) {
        return (IndexedMapMutator<K, V>)accessor;
    }

    /**
     * 
     * @param <V>
     * @param <A>
     * @param fa
     * @return 
     */
    public static <V, A extends Accessor<V>> A nonNullAccessor(final A fa) {
        return Objects.requireNonNull(fa, "Accessor");
    }   
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    public static <K, V> IterableAccessor<Tuple2<K, V>> entries(final MapAccessor<K, V> acc) {
        return nonNullAccessor(acc)
                .keys()
                .map(k -> Tuple(k, acc.getOrFail(k)));
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param acc
     * @return 
     */
    public static <K, V> IterableAccessor<Tuple2<Integer, V>> entries(final ListAccessor<V> acc) {
        return nonNullAccessor(acc)
                .indexes()
                .map(i -> Tuple(i, acc.getOrFail(i)));
    }
    
}
