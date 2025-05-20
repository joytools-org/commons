/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.mappers;

import io.vavr.control.Option;
import java.util.function.Function;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
@FunctionalInterface
public interface Mapper<K, V> extends Function<K, Option<V>> {
    
    default V applyOrFail(final K k) {
        final Option<V> val = apply(k);
        if (val.isEmpty()) {
            throw new IllegalArgumentException(StringUtils.toString(k));
        }
        return val.get();
    }

    default V applyOrNull(final K k) {
        return apply(k).getOrNull();
    }
    
    default V applyOrElse(final K k, final V def) {
        return apply(k).getOrElse(def);
    }

}
