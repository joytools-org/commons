/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.lang;

import io.vavr.control.Option;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;

import java.util.function.Function;
import java.util.function.Supplier;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.Mutators;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.concurrent.AtomicSuppliers;
import org.joytools.commons.function.Predicates;
import static org.joytools.commons.function.Predicates.isNotNull;


/**
 *
 * @author andrear
 * @param <K>
 * @param <V>
 */
public abstract class EnumLookup<K, V> {

    /**
     * 
     */
    public EnumLookup() {
    }

    /**
     * 
     * @param keyFunc 
     */
    public EnumLookup(final Function<K, K> keyFunc) {
        this.keyFunc = keyFunc;
    }

    /**
     * 
     * @param values
     */
    public EnumLookup(final V[] values) {
        if (values == null) {
            throw new NullPointerException("values");
        }
        this.values = Arrays.asList(values);
    }
    
    /**
     * 
     * @param values 
     */
    public EnumLookup(final Iterable<V> values) {
        if (values == null) {
            throw new NullPointerException("values");
        }
        this.values = values;
    }

    /**
     * 
     * @param values
     * @param keyFunc 
     */
    public EnumLookup(final V[] values,
            final Function<K, K> keyFunc) {
        if (values == null) {
            throw new NullPointerException("values");
        }
        this.values = Arrays.asList(values);
        this.keyFunc = keyFunc;
    }

    /**
     * 
     * @param values
     * @param keyFunc 
     */
    public EnumLookup(final Iterable<V> values,
            final Function<K, K> keyFunc) {
        if (values == null) {
            throw new NullPointerException("values");
        }
        this.values = values;
        this.keyFunc = keyFunc;
    }

    /**
     * 
     * @param item 
     */
    public void add(final V item) {
        if (values != null) {
            throw new IllegalStateException("add() may be used only when values not specified in costructor");
        }
        put(map(), item);
        attached.add(item);
        if (comparable && !(item instanceof Comparable)) {
            comparable = false;
        }
    }
    
    /**
     * 
     * @param names
     * @return 
     */
    protected static Set<String> expand(final String... names) {
        final Set<String> result = new HashSet<>();
        // for (final String n : StreamIterables.of(names).omitNull()) {
        for (final String n : IterableAccessors.from(names).filter(isNotNull())) {
            result.addAll(doExpand(n));
        }
        return result;
    }

    /**
     * 
     * @param names
     * @return 
     */
    protected static Set<String> expand(final Iterable<String> names) {
        final Set<String> result = new HashSet<>();
        for (final String n : IterableAccessors.from(names).omit(Predicates.isNull())) {
            result.addAll(doExpand(n));
        }
        return result;
    }

    /**
     * 
     * @param name
     * @return 
     */
    protected static List<String> doExpand(final String name) {
        return Arrays.asList(
                name,
                StringUtils.replace(name, "_", " "),
                StringUtils.replace(name, "_", ""));
    }
    
    /**
     * 
     * @param map
     * @param item 
     */
    protected void put(final Map<K, V> map,
            final V item) {
        for (final K key : keysOf(item)) {
            final K k = keyFunc == null ? key : keyFunc.apply(key);
            final V prev = map.put(k, item);
            if (prev != null && prev != item) {
                throw new IllegalArgumentException("Duplicated key: " + k);
            }
            if (comparable && !(item instanceof Comparable)) {
                comparable = false;
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    protected Iterable<V> iterableValues() {
        return values != null ? values : attached;
    }
    
    /**
     * 
     * @return 
     */
    public Collection<V> values() {
        final Collection<V> result = comparable ? new TreeSet<>() : new ArrayList<>();
        for (final V item : iterableValues()) {
            result.add(item);
        }
        return result;
    }
    
    /**
     * 
     * @return 
     */
    protected Map<K, V> map() {
        return atomicMap.get();
    }
    
    /**
     * 
     */
    private Iterable<V> values = null;

    /**
     * 
     */
    private final Supplier<Map<K, V>> atomicMap = AtomicSuppliers.of(() -> {
        final Map<K, V> map = new HashMap<>();
        if (values != null) {
            for (final V item : values) {
                put(map, item);
            }
        }
        return map;
    });

    /**
     * 
     * @param key
     * @return 
     */
    public V get(final K key) {
        final K k = keyFunc == null ? key : keyFunc.apply(key);
        return map().get(k);
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    public Option<V> getOption(final K key) {
        final K k = keyFunc == null ? key : keyFunc.apply(key);
        return AccessorMethods.get(map(), k);
    }

    /**
     * 
     * @param key
     * @return 
     */
    public Optional<V> getOptional(final K key) {
        return getOption(key).toJavaOptional();
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    public V getExisting(final K key) {
        final V result = get(key);
        if (result == null) {
            throw new NoSuchElementException("No enum found with name or alias: " + key);
        }
        return result;
    }

    /**
     * 
     * @param item 
     * @return  
     */
    abstract protected Iterable<K> keysOf(final V item);
    
    /**
     * 
     */
    private boolean comparable = true;
    
        
    /**
     * 
     */
    private final List<V> attached = new ArrayList<>();

    /**
     * 
     */
    private Function<K, K> keyFunc = null;
    
}
