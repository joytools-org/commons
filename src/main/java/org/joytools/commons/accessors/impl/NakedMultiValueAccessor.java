/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.impl;

import org.joytools.commons.accessors.Accessor;
import org.joytools.commons.accessors.IndexedMapAccessor;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public abstract class NakedMultiValueAccessor<K, V> extends NakedAccessor<V> {
    
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
    
    public boolean isUpdatable() {
        return true;
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
    
    /**
     * 
     */
    private IndexedMapAccessor.Reader<K, V> reader;
    
    
}
