/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.delegated;

import org.joytools.commons.accessors.FixedSizeListMutator;

/**
 *
 * @author AndreaR
 */
@FunctionalInterface
public interface DelegatedFixedSizeListMutator<V> extends DelegatedListAccessor<V>, FixedSizeListMutator<V> {
    
    @Override
    default <Z> DelegatedFixedSizeListMutator<Z> castAs(final Class<Z> clazz) {
        return (DelegatedFixedSizeListMutator<Z>)this;
    }

    @Override
    default boolean isUpdatable() {
        return true;
    }
    
    @Override
    default <U extends V> U set(final int index, final U val) {
        return ((FixedSizeListMutator<V>)delegated()).set(index, val);
    }

    @Override
    default <U extends V> boolean update(final int index, final U val) {
        return ((FixedSizeListMutator<V>)delegated()).update(index, val);
    }

}
