/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import io.vavr.Tuple2;
import java.util.Objects;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public class MapModifications<K, V> {
    
    /**
     * 
     * @param m 
     */
    protected MapModifications(final MapMutator<K, V> m) {
        this.dest = Objects.requireNonNull(m, "MapMutator");
    }
    
    /**
     * 
     * @param <T>
     * @param <R>
     * @param m
     * @return 
     */
    public static <T, R> MapModifications<T, R> of(final MapMutator<T, R> m) {
        return new MapModifications(m);
    }
    
    /**
     * 
     * @param <T>
     * @param <R>
     * @param m
     * @return 
     */
    public static <T, R> MapModifications<T, R> of(final java.util.Map<T, R> m) {
        return new MapModifications(Mutators.of(m));
    }
    
    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    public MapModifications<K, V> put(final K key, final V value) {
        dest.put(key, value);
        return this;
    }

    /**
     * 
     * @param other
     * @return 
     */
    public MapModifications<K, V> putAll(final MapMutator<K, V> other) {
        return putAll(other.entriesByKey());
    }

    /**
     * 
     * @param other
     * @return 
     */
    public MapModifications<K, V> putAll(final Iterable<Tuple2<K, V>> other) {
        if (other != null) {
            for (final Tuple2<K, V> e : other) {
                dest.put(e._1(), e._2());
            }
        }
        return this;
    }

    /**
     * 
     * @param other
     * @return 
     */
    public MapModifications<K, V> putAll(final java.util.Map<K, V> other) {
        return putAll(Accessors.of(other).entriesByKey());
    }

    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    public MapModifications<K, V> insert(final K key, final V value) {
        dest.insert(key, value);
        return this;
    }

    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    public MapModifications<K, V> remove(final K key, final V value) {
        dest.remove(key);
        return this;
    }

    /**
     * 
     */
    private final MapMutator<K, V> dest;
    
}
