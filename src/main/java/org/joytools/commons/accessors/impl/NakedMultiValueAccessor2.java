/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.impl;

import io.vavr.control.Option;
import java.util.function.Supplier;
import org.joytools.commons.accessors.Accessor;
import org.joytools.commons.accessors.IndexedMapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullKey;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public abstract class NakedMultiValueAccessor2<K, V> {
    
    /**
     * 
     * @param methodName
     * @return 
     */
    protected UnsupportedOperationException unsupported(final String methodName) {
        return new UnsupportedOperationException(methodName);
    }
    
    /**
     * 
     * @param methodName
     * @param cause
     * @return 
     */
    protected UnsupportedOperationException unsupported(final String methodName, final Exception cause) {
        return new UnsupportedOperationException(methodName, cause);
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public final String toString() {
        if (this instanceof Accessor acc) {
            return AccessorImplUtils.toString(acc);
        }
        return super.toString();
    }    
    
    /**
     * 
     * @return 
     */
    protected IndexedMapAccessor.Reader<K, V> read0() {
        if (reader == null) {
            reader = new IndexedMapAccessorReaderImpl(Accessor.class.cast(this));
        }            
        return reader;
    }
    
    /**
     * 
     * @param a
     * @param b
     * @return 
     */
    protected boolean equals(final K a, final K b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (this instanceof CaseSensitivitySupport css) {
            CaseSensitivity cs = css.caseSensitivity();
            if (cs.isUnknown()) {
                cs = CaseSensitivity.SENSITIVE;
            }
            return cs.equals(CharSequence.class.cast(a), CharSequence.class.cast(b));
        }
        return a.equals(b);
    }
    
    public final boolean contains(final K key) {
        return contains0(nonNullKey(key));
    }
    
    public final Option<V> get(final K key) {
        return get0(nonNullKey(key));
    }

    public final V getOrElse(final K key, final V defValue) {
        return getOrElse0(nonNullKey(key), defValue);
    }

    public final V getOrNull(final K key) {
        return getOrElse0(nonNullKey(key), (V)null);
    }

    abstract protected boolean contains0(final K key);

    abstract protected Option<V> get0(final K key);

    abstract protected V getOrElse0(final K key, final V defValue);

    abstract protected V getOrElse0(final K key, final Supplier<V> supplier);

    /**
     * 
     */
    private IndexedMapAccessor.Reader<K, V> reader;
    
    
}
