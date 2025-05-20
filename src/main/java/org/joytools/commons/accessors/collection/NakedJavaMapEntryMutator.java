/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Map;
import java.util.Objects;
import org.joytools.commons.accessors.impl.NakedSingletonMutator;

/**
 *
 * @author AndreaR
 */
abstract class NakedJavaMapEntryMutator<K, V> extends NakedSingletonMutator<K, V> {

    protected NakedJavaMapEntryMutator(final Map.Entry<K, V> entry) {
        super(Objects.requireNonNull(entry, "Map.Entry").getKey());
        this.entry = entry;
    }
    
    @Override
    protected V get0() {
        return entry.getValue();
    }

    @Override
    protected void set0(final V value) {
        entry.setValue(value);
    }

    protected final Map.Entry<K, V> entry;
    
}
