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
public interface ListMutator<V> extends FixedSizeListMutator<V> {
    
    @Override
    default <Z> ListMutator<Z> castAs(final Class<Z> type) {
        return (ListMutator<Z>)this;
    }

    /**
     * 
     * @param <U>
     * @param value 
     * @return  
     */
    public <U extends V> U add(final U value);

    /**
     * 
     * @param <U>
     * @param index
     * @param value 
     * @return  
     */
    public <U extends V> U insert(final int index, final U value);

    /**
     * 
     * @param <U>
     * @param index
     * @param value 
     * @return  
     */
    public <U extends V> U put(final int index, final U value);

    /**
     * 
     * @param index 
     * @return  
     */
    public boolean delete(final int index);
    
    /**
     * 
     * @param index 
     */
    public void remove(final int index);

}
