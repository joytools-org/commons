/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import java.util.Objects;
import org.joytools.commons.accessors.core.CoreStringAccessors;
import org.joytools.commons.accessors.delegated.DelegatedStringMapAccessor;
import org.joytools.commons.accessors.collection.CollectionStringAccessors;
import org.joytools.commons.accessors.reflect.RecordAccessor;
import org.joytools.commons.accessors.reflect.RecordAccessors;
import org.joytools.commons.accessors.vavr.VavrStringAccessors;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class StringAccessors {

    /**
     *
     * @param <V>
     * @param map
     * @return
     */
    public static <V> StringMapAccessor<V> of(final io.vavr.collection.Map<String, V> map) {
        return VavrStringAccessors.of(map);
    }

    /**
     *
     * @param <V>
     * @param map
     * @return
     */
    public static <V> StringMapAccessor<V> of(final java.util.Map<String, V> map) {
        return CollectionStringAccessors.of(map);
    }
    
    /**
     * 
     * @param <V>
     * @param map
     * @param cs
     * @return 
     */
    public static <V> StringMapAccessor<V> of(final java.util.Map.Entry<String, V> map,
            final CaseSensitivity cs) {
        return CollectionStringAccessors.of(map, cs);
    }

    /**
     *
     * @param <V>
     * @param acc
     * @return
     */
    public static <V> StringMapAccessor<V> of(final MapAccessor<String, V> acc) {
        Objects.requireNonNull(acc, "Accessor");
        if (acc instanceof StringMapAccessor) {
            return StringMapAccessor.class.cast(acc);
        }
        return (DelegatedStringMapAccessor<V>) () -> acc;
    }
    
    /**
     * 
     * @param <R>
     * @param record
     * @return 
     */
    public static <R extends Record> RecordAccessor<R> of(final R record) {
        return RecordAccessors.of(record);
    }
    
    /**
     * 
     * @param <R>
     * @param record
     * @param cs
     * @return 
     */
    public static <R extends Record> RecordAccessor<R> of(final R record, final CaseSensitivity cs) {
        return RecordAccessors.of(record, cs);
    }

    /**
     * 
     * @param <R>
     * @param record
     * @return 
     */
    public static <R extends Record> RecordAccessor<R> ofIgnoreCase(final R record) {
        return RecordAccessors.ofIgnoreCase(record);
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> IndexedStringMapAccessor<V> empty() {
        return CoreStringAccessors.empty();
    }
    
}
