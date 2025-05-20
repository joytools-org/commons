/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.vavr;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import static io.vavr.API.Tuple;
import io.vavr.Tuple1;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.Tuple4;
import io.vavr.Tuple5;
import io.vavr.Tuple6;
import io.vavr.Tuple7;
import io.vavr.Tuple8;
import io.vavr.control.Option;
import java.util.Objects;
import org.joytools.commons.accessors.MapAccessor;
import org.joytools.commons.accessors.MutatorImplUtils;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullKey;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
class VavrTupleStringMapAccessor<V> extends NakedMultiValueAccessor<String, V> implements StringMapAccessor<V> {

    private final static io.vavr.collection.Seq<String> KEYS = io.vavr.collection.Array.of("1", "2", "3", "4", "5", "6", "7", "8");
            
    /**
     * 
     * @param data 
     */
    protected VavrTupleStringMapAccessor(final V[] data) {
        this.data = Objects.requireNonNull(data);
        if (data.length < 1 || data.length > 8) {
            throw new IllegalArgumentException("Invalid tuple size: " + data.length);
        }
    }
    
    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> VavrTupleStringMapAccessor<V> of(final Tuple1<? extends V> tuple) {
        Objects.requireNonNull(tuple, "Tuple1");
        return of0(tuple._1);
    }
    
    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> VavrTupleStringMapAccessor<V> of(final Tuple2<? extends V, ? extends V> tuple) {
        Objects.requireNonNull(tuple, "Tuple2");
        return of0(tuple._1, tuple._2);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> VavrTupleStringMapAccessor<V> of(final Tuple3<? extends V, ? extends V, ? extends V> tuple) {
        Objects.requireNonNull(tuple, "Tuple3");
        return of0(tuple._1, tuple._2, tuple._3);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> VavrTupleStringMapAccessor<V> of(final Tuple4<? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        Objects.requireNonNull(tuple, "Tuple4");
        return of0(tuple._1, tuple._2, tuple._3, tuple._4);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> VavrTupleStringMapAccessor<V> of(final Tuple5<? extends V, ? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        Objects.requireNonNull(tuple, "Tuple5");
        return of0(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> VavrTupleStringMapAccessor<V> of(final Tuple6<? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        Objects.requireNonNull(tuple, "Tuple6");
        return of0(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5, tuple._6);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> VavrTupleStringMapAccessor<V> of(final Tuple7<? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        Objects.requireNonNull(tuple, "Tuple7");
        return of0(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5, tuple._6, tuple._7);
    }

    /**
     * 
     * @param <V>
     * @param tuple
     * @return 
     */
    public static <V> VavrTupleStringMapAccessor<V> of(final Tuple8<? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V, ? extends V> tuple) {
        Objects.requireNonNull(tuple, "Tuple8");
        return of0(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5, tuple._6, tuple._7, tuple._8);
    }

    /**
     * 
     * @param <V>
     * @param data
     * @return 
     */
    static <V> VavrTupleStringMapAccessor<V> of0(final V... data) {
        return new VavrTupleStringMapAccessor(data);
    }

    /**
     * 
     * @return 
     */
    @Override
    public final CaseSensitivity caseSensitivity() {
        return CaseSensitivity.INSENSITIVE;
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public final Option<V> get(final String key) {
        final int index = toIndex(key);
        if (index < 0) {
            return None();
        }
        return Some(get0(index));
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public final V getOrNull(final String key) {
        final int index = toIndex(key);
        if (index < 0) {
            return null;
        }
        return get0(index);
    }

    /**
     * 
     * @param key
     * @param def
     * @return 
     */
    @Override
    public final V getOrElse(final String key, final V def) {
        final int index = toIndex(key);
        if (index < 0) {
            return def;
        }
        return get0(index);
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public final V getOrFail(final String key) {
        final int index = toIndex(key);
        if (index < 0) {
            throw MutatorImplUtils.newItemNotFoundException("getOrFail", key);
        }
        return get0(index);
    }

    /**
     * 
     * @param key
     * @return 
     */
    protected int toIndex(final String key) {
        try {
            final int index = Integer.parseInt(nonNullKey(key));
            if (index >= 1 && index <= size()) {
                return index;
            }
            return -1;
        } catch (final NumberFormatException e) {
            return -1;
        }
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    protected V get0(final int index) {
        return data[index - 1];
    }
    
    @Override
    public boolean isEmpty() {
        return false;
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public int size() {
        return data.length;
    }
    
    /**
     * 
     */
    private final V[] data;
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public final boolean contains(final String key) {
        return toIndex(key) > 0;
    }

    @Override
    public final IterableAccessor<String> keys() {
        return IterableAccessors.ofAll(KEYS.subSequence(0, size()));
    }
    
    @Override
    public final IterableAccessor<V> values() {
        return IterableAccessors.of(data);
    }
    
    @Override
    public final IterableAccessor<Tuple2<String, V>> entriesByKey() {
        final int s = data.length;
        final Tuple2<String, V>[] entries = new Tuple2[s];
        for (int i = 0; i < s; i++) {
            entries[i] = Tuple(KEYS.get(i), data[i]);
        }
        return IterableAccessors.of(entries);
    }

    @Override
    public MapAccessor.Reader<String, V> read() {
        return read0();
    }
    
}
