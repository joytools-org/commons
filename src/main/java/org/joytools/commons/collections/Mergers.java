/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import static io.vavr.API.Tuple;
import io.vavr.Function3;
import io.vavr.Tuple4;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import org.joytools.commons.lang.ObjectUtils;

/**
 *
 * @author AndreaR
 */
public class Mergers {
 
    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> Merger<K, V> mergeNone() {
        return (Merger<K, V>)MERGE_NONE;
    }

    /**
     * 
     * @param <V>
     * @return 
     */
    public static <V> BinaryOperator<V> mergeNoneBinaryOperator() {
        final Merger<Object, V> f = mergeNone();
        return f.asBinaryOperator();
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> Function3<K, V, V, V> mergeEquals() {
        return (Merger<K, V>)MERGE_EQUALS;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param merged
     * @return 
     */
    public static <K, V> Function3<K, V, V, V> mergeEquals(final Consumer<Tuple4<K, V, V, V>> merged) {
        Objects.requireNonNull(merged, "Consumer");
        return (k, v1, v2) -> {
            if (ObjectUtils.equals(v1, v2)) {
                merged.accept(Tuple(k, v1, v2, v1));
                return v1;
            }
            return throwDuplicates(k, v1, v2);
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> Function3<K, V, V, V> mergeFirst() {
        return (Merger<K, V>)MERGE_FIRST;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param merged
     * @return 
     */
    public static <K, V> Function3<K, V, V, V> mergeFirst(final Consumer<Tuple4<K, V, V, V>> merged) {
        Objects.requireNonNull(merged, "Consumer");
        return (k, v1, v2) -> {
            merged.accept(Tuple(k, v1, v2, v1));
            return v1;
        };
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @return 
     */
    public static <K, V> Function3<K, V, V, V> mergeLast() {
        return (Merger<K, V>)MERGE_LAST;
    }

    /**
     * 
     * @param <K>
     * @param <V>
     * @param merged
     * @return 
     */
    public static <K, V> Function3<K, V, V, V> mergeLast(final Consumer<Tuple4<K, V, V, V>> merged) {
        Objects.requireNonNull(merged, "Consumer");
        return (k, v1, v2) -> {
            merged.accept(Tuple(k, v1, v2, v2));
            return v2;
        };
    }
    
    /**
     * 
     */
    private final static Merger<Object, Object> MERGE_NONE = new Merger() {
        @Override
        public Object apply(Object k, Object v1, Object v2) {
            return throwDuplicates(k, v1, v2);
        }
    };

    /**
     * 
     */
    private final static Merger<Object, Object> MERGE_EQUALS = new Merger() {
        @Override
        public Object apply(Object k, Object v1, Object v2) {
            if (ObjectUtils.equals(v1, v2)) {
                return v1;
            }
            return throwDuplicates(k, v1, v2);
        }
    };

    /**
     * 
     */
    private final static Merger<Object, Object> MERGE_FIRST = new Merger() {
        @Override
        public Object apply(Object k, Object v1, Object v2) {
            return v1;
        }
    };

    /**
     * 
     */
    private final static Merger<Object, Object> MERGE_LAST = new Merger() {
        @Override
        public Object apply(Object k, Object v1, Object v2) {
            return v2;
        }
    };

    private final static Merger<Object, Comparable<Object>> MERGE_MIN = new Merger<Object, Comparable<Object>>() {
        @Override
        public Comparable<Object> apply(Object k, Comparable<Object> v1, Comparable<Object> v2) {
            if (v1 == v2) {
                return v1;
            }
            if (v1 == null) {
                return v1;
            }
            if (v2 == null) {
                return v2;
            }
            return v1.compareTo(v2) <= 0 ? v1: v2;
        }
    };

    /**
     * 
     */
    private final static Merger<Object, Comparable<Object>> MERGE_MAX = new Merger<Object, Comparable<Object>>() {
        @Override
        public Comparable<Object> apply(Object k, Comparable<Object> v1, Comparable<Object> v2) {
            if (v1 == v2) {
                return v1;
            }
            if (v1 == null) {
                return v2;
            }
            if (v2 == null) {
                return v1;
            }
            return v2.compareTo(v1) >= 0 ? v2: v1;
        }
    };

    /**
     * 
     * @param <R>
     * @param <K>
     * @param <V>
     * @param key
     * @param first
     * @param last
     * @return 
     */
    public static <R, K, V> R throwDuplicates(final K key, final V first, final V last) {
        final StringBuilder sb = new StringBuilder();
        if (key != null) {
            sb.append("Multiple entries with the same key: ").append(key)
                    .append(", first: ").append(first)
                    .append(", last: ").append(last);
        } else {
            sb.append("Multiple entries with the same key: ").append(key)
                    .append(", first: ").append(first)
                    .append(", last: ").append(last);
        }
        throw new IllegalArgumentException(sb.toString());
    }

}
