/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import java.util.Objects;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.FixedSizeMutatorMethods;
import org.joytools.commons.accessors.FixedSizeMutators;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.vavr.Traversables;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
abstract class NakedArrayMutator<V> extends NakedMultiValueAccessor<Void, V> {

    protected NakedArrayMutator(final V[] array) {
        this.array = Objects.requireNonNull(array, "Array");
    }
    
    /**
     * 
     * @return 
     */
    final protected V[] array;
       
    /**
     * 
     * @param index
     * @return 
     */
    public boolean contains(final int index) {
        return AccessorMethods.contains(array, index);
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    public V getOrFail(final int index) {
        return AccessorMethods.getOrFail(array, index);
    }

    /**
     * 
     * @param index
     * @return 
     */
    public Option<V> get(final int index) {
        return AccessorMethods.get(array, index);
    }

    /**
     * 
     * @param index
     * @return 
     */
    public V getOrNull(final int index) {
        return AccessorMethods.getOrNull(array, index);
    }
    
    /**
     * 
     * @param index
     * @param defValue
     * @return 
     */
    public V getOrElse(final int index, final V defValue) {
        return AccessorMethods.getOrElse(array, index, defValue);
    }

    /**
     * 
     * @return 
     */
    public boolean isUpdatable() {
        return true;
    }

    /**
     * 
     * @param <U>
     * @param index
     * @param val 
     */
    public <U extends V> U set(final int index, final U val) {
        return FixedSizeMutatorMethods.set(array, index, val);
    }

    /**
     * 
     * @param <U>
     * @param index
     * @param val
     * @return 
     */
    public <U extends V> boolean update(final int index, final U val) {
        return FixedSizeMutatorMethods.update(array, index, val);
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<Tuple2<Integer, V>> entriesByIndex() {
        final Traversable<Tuple2<Integer, V>> t = Traversables.of(array)
                .zipWithIndex((v, i) -> Tuple(i, v));
        return IterableAccessors.ofAll(t);
    }
    
    /**
     * 
     * @return 
     */
    public IterableAccessor<V> values() {
        if (values == null) {
            values = IterableAccessors.of(array);
        }
        return values;
    }

    /**
     * 
     * @return 
     */
    public int size() {
        if (size < 0) {
            size = array.length;
        }
        return size;
    }
    
    /**
     * 
     * @return 
     */
    public final ListAccessor.Reader<V> read() {
        return read0();
    }
    
    /**
     * 
     */
    private IterableAccessor<V> values = null;

    /**
     * 
     */
    private int size = -1;

}
