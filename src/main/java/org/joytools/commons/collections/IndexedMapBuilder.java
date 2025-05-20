/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
public class IndexedMapBuilder {
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> IndexedMap<K, V> create() {
        return newHashed();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param data
     * @return 
     */
    public static <K, V> TypedBuilder<K, V> copyOf(final Map<K, V> data) {
        return new TypedBuilder() {
            @Override
            public Builder<K, V> hashed() {
                return () -> newHashedCopyOf(data);
            }

            @Override
            public Builder<K, V> caseSensitivity(final CaseSensitivity cs) {
                return () -> newSortedCopyOf(cs, data);
            }
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> TypedBuilder<K, V> empty() {
        return new TypedBuilder() { 
            @Override
            public Builder<K, V> hashed() {
                return () -> newHashed();
            }

            @Override
            public Builder<K, V> caseSensitivity(final CaseSensitivity cs) {
                return () -> newSorted(cs);
            }
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param keys
     * @param value
     * @return 
     */
    public static <K, V> TypedBuilder<K, V> withData(final Iterable<K> keys, final V value) {
        return new TypedBuilder() {
            @Override
            public Builder<K, V> hashed() {
                return () -> newHashedWithData(keys, value);
            }

            @Override
            public Builder<K, V> caseSensitivity(final CaseSensitivity cs) {
                return () -> newSortedWithData(cs, keys, value);
            }
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param cs
     * @return 
     */
    protected static <K, V> IndexedMap<K, V> newSorted(final CaseSensitivity cs) {
        Objects.requireNonNull(cs, "Case Sensitivity");
        return new IndexedMapImpl(cs.newLinkedMap());
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param cs
     * @param keys
     * @param value
     * @return 
     */
    protected static <K, V> IndexedMap<K, V> newSortedWithData(final CaseSensitivity cs, 
            final Iterable<K> keys, final V value) {
        return populate(newSorted(cs), keys, value);
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param cs
     * @param data
     * @return 
     */
    protected static <K, V> IndexedMap<K, V> newSortedCopyOf(final CaseSensitivity cs,
            final Map<K, V> data) {
        return populate(newSorted(cs), data);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> Builder<K, V> ignoreCase() {
        return () -> newSorted(CaseSensitivity.INSENSITIVE);
    }
    
    /**
     * 
     * @param <K>
     * @param <V> 
     */
    @FunctionalInterface
    public static interface Builder<K, V> {
        
        public IndexedMap<K, V> create();
        
    }
    
    public static abstract class TypedBuilder<K, V> implements Builder<K, V> {
        
        @Override
        public IndexedMap<K, V> create() {
            return hashed().create();
        }
        
        public abstract Builder<K, V> hashed();

        public abstract Builder<K, V> caseSensitivity(final CaseSensitivity cs);

        public final Builder<K, V> ignoreCase() {
            return caseSensitivity(CaseSensitivity.INSENSITIVE);
        }
        
        public final Builder<K, V> ignoreCase(final boolean flag) {
            return caseSensitivity(flag ? CaseSensitivity.INSENSITIVE : CaseSensitivity.SENSITIVE);
        }
        
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    protected static <K, V> IndexedMap<K, V> newHashed() {
        return new IndexedMapImpl(new HashMap());
    }
    
    /**
     * 
     * @param <K>
     * @param <V>
     * @param data
     * @return 
     */
    protected static <K, V> IndexedMap<K, V> newHashedCopyOf(final Map<K, V> data) {
        return populate(newHashed(), data);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param keys
     * @param value
     * @return 
     */
    protected static <K, V> IndexedMap<K, V> newHashedWithData(final Iterable<K> keys, final V value) {
        return populate(newHashed(), keys, value);
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param result
     * @param data
     * @return 
     */
    protected static <K, V> IndexedMap<K, V> populate(
            final IndexedMap<K, V> result, 
            final Map<K, V> data) {
        if (data != null) {
            result.putAll(data);
        }
        return result;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param result
     * @param keys
     * @param value
     * @return 
     */
    protected static <K, V> IndexedMap<K, V> populate(
            final IndexedMap<K, V> result, 
            final Iterable<K> keys,
            final V value) {
        keys.forEach((k) -> { result.put(k, value); });
        return result;
    }

}
