/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.mappers;

import io.vavr.Value;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 *
 * @author AndreaR
 */
class MappingsBuilder<I, O, R extends O, V extends Value<R>> {
    
    public MappingsBuilder() {
    }
    
    public <Z extends V> MappingsBuilder<I, O, R, V> add(final Function<? super I, V> mapping) {
        checkAdd();
        final Function<I, V> m = (Function<I, V>)Objects.requireNonNull(mapping, "Mapping");
        list.add(m);
        return this;
    }
    
    public <Z extends V> MappingsBuilder<I, O, R, V> add(final Z defValue) {
        Objects.requireNonNull(defValue, "Default Value");
        final Function<I, V> f = (i) -> defValue;
        add(f);
        this.hasDefValue = true;
        return this;
    }

    public <Z extends V> MappingsBuilder<I, O, R, V> add(final Supplier<Z> defValue) {
        Objects.requireNonNull(defValue, "Default Value Supplier");
        final Function<I, V> f = (i) -> defValue.get();
        add(f);
        this.hasDefValue = true;
        return this;
    }

    protected void checkAdd() {
        if (seq != null) {
            throw new IllegalStateException("Unable to add mapping as mappings have already been built");
        }
        if (hasDefValue) {
            throw new IllegalStateException("Unable to add mapping because a default value has been set");
        }
    }
    
    public boolean isModifiable() {
        return seq == null && !hasDefValue;
    }
    
    public Seq<Function<I, V>> build() {
        if (seq == null) {
            seq = Vector.ofAll(list);
            list.clear();
        }
        return seq;
    }

    private final List<Function<I, V>> list = new ArrayList<>();

    private boolean hasDefValue = false;
    
    private Seq<Function<I, V>> seq = null;

}
