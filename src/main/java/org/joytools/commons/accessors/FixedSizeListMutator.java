/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public interface FixedSizeListMutator<V> extends ListAccessor<V>, Mutator<V> {
    
    @Override
    default <Z> FixedSizeListMutator<Z> castAs(final Class<Z> type) {
        return (FixedSizeListMutator<Z>)this;
    }
    
    /**
     * 
     * @param <U>
     * @param index
     * @param value 
     * @return  
     */
    public <U extends V> U set(final int index, final U value);
    
    /**
     * 
     * @param <U>
     * @param index
     * @param value
     * @return 
     */
    public <U extends V> boolean update(final int index, final U value);

}
