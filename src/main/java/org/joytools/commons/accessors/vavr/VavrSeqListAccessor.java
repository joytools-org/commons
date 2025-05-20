/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.vavr;

import static io.vavr.API.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import java.util.Objects;
import org.joytools.commons.accessors.ListAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
class VavrSeqListAccessor<V> extends NakedMultiValueAccessor<Void, V> implements ListAccessor<V> {

    protected VavrSeqListAccessor(final Seq<V> seq) {
        this.seq = Objects.requireNonNull(seq, "Seq");
    }
    
    @Override
    public boolean isEmpty() {
        return seq.isEmpty();
    }
    
    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public V getOrFail(final int index) {
        try {
            return seq.get(index);
        } catch (final IndexOutOfBoundsException ioe) {
            throw newItemNotFoundException("getOrFail", index);
        }
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public V getOrNull(final int index) {
        try {
            return seq.get(index);
        } catch (final IndexOutOfBoundsException ioe) {
            return null;
        }
    }

    /**
     * 
     * @param index
     * @param defVal
     * @return 
     */
    @Override
    public V getOrElse(final int index, final V defVal) {
        try {
            return seq.get(index);
        } catch (final IndexOutOfBoundsException ioe) {
            return defVal;
        }
    }

    /**
     * 
     * @param index
     * @return 
     */
    @Override
    public Option<V> get(final int index) {
        return seq.isDefinedAt(index) ? Option.some(seq.get(index)) : Option.none();  
        /* if (index >= 0 && index < seq.size()) {
            return Some(seq.get(index));
        }
        return None(); */
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<V> values() {
        return IterableAccessors.ofAll(seq);
        /*
        return new AbstractList<V>() {
            @Override
            public V get(final int index) {
                return seq.get(index);
            }

            @Override
            public int size() {
                return seq.size();
            }
        };
        */
    }

    @Override
    public IterableAccessor<Tuple2<Integer, V>> entriesByIndex() {
        return IterableAccessors.ofAll(seq.zipWithIndex((v, i) -> Tuple(i, v)));
    }

    @Override
    public int size() {
        return seq.size();
    }
    
    @Override
    public ListAccessor.Reader<V> read() {
        return read0();
    }          
    
    private final Seq<V> seq;

}
