/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Traversable;
import io.vavr.control.Option;
import java.util.List;
import org.joytools.commons.accessors.AccessorMethods;
import org.joytools.commons.accessors.FixedSizeMutatorMethods;
import org.joytools.commons.accessors.ListAccessor;
import org.joytools.commons.accessors.MutatorMethods;
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
abstract class NakedJavaListMutator<V> extends NakedMultiValueAccessor<Void, V>  {

    /**
     * 
     * @return 
     */
    abstract protected List<V> list();
       
    /**
     * 
     * @param index
     * @return 
     */
    public V getOrFail(final int index) {
        return AccessorMethods.getOrFail(list(), index);
    }

    /**
     * 
     * @param index
     * @return 
     */
    public Option<V> get(final int index) {
        return AccessorMethods.get(list(), index);
    }

    /**
     * 
     * @param index
     * @return 
     */
    public V getOrNull(final int index) {
        return AccessorMethods.getOrNull(list(), index);
    }

    /**
     * 
     * @param index
     * @param defValue
     * @return 
     */
    public V getOrElse(final int index, final V defValue) {
        return AccessorMethods.getOrElse(list(), index, defValue);
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
     * @param value 
     * @return  
     */
    public <U extends V> U add(final U value) {
        return MutatorMethods.add(list(), value);
    }

    /**
     * 
     * @param <U>
     * @param index
     * @param value 
     * @return  
     */
    public <U extends V> U insert(final int index, final U value) {
        return MutatorMethods.insert(list(), index, value);
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param val 
     * @return  
     */
    public <U extends V> U put(final int key, final U val) {
        return MutatorMethods.put(list(), key, val);
    }

    /**
     * 
     * @param <U>
     * @param index
     * @param val
     * @return 
     */
    public <U extends V> U set(final int index, final U val) {
        return FixedSizeMutatorMethods.set(list(), index, val);
    }

    /**
     * 
     * @param <U>
     * @param index
     * @param val
     * @return 
     */
    public <U extends V> boolean update(final int index, final U val) {
        return FixedSizeMutatorMethods.update(list(), index, val);
    }

    /**
     * 
     * @param index 
     */
    public boolean delete(final int index) {
        return MutatorMethods.delete(list(), index);
    }
    
    /**
     * 
     * @param index 
     */
    public void remove(final int index) {
        MutatorMethods.remove(list(), index);
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<Tuple2<Integer, V>> entriesByIndex() {
        final Traversable<Tuple2<Integer, V>> t = Traversables.ofAll(list())
                .zipWithIndex((v, i) -> Tuple(i, v));
        return IterableAccessors.ofAll(t);
    }

    /**
     * 
     * @return 
     */
    public IterableAccessor<V> values() {
        return IterableAccessors.ofAll(list());
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
     * @return 
     */
    public int size() {
        return list().size();
    }
    
}
