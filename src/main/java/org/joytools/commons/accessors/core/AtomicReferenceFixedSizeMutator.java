/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;
import org.joytools.commons.accessors.impl.NakedSingletonMutator;
import org.joytools.commons.accessors.IndexedMapAccessor;

/**
 *
 * @author AndreaR
 */
class AtomicReferenceFixedSizeMutator<K, V> extends NakedSingletonMutator<K, V> implements FixedSizeIndexedMapMutator<K, V> {

    private final AtomicReference<V> ref;
    
    protected AtomicReferenceFixedSizeMutator(final K key, final AtomicReference<V> ref) {
        super(key);
        this.ref = Objects.requireNonNull(ref, "AtomicReference");
    }
    
    @Override
    protected V get0() {
        return ref.get();
    }

    @Override
    protected void set0(final V value) {
        ref.set(value);
    }

}
