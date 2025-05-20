/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors;

import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public interface IndexedStringMapAccessor<V> extends IndexedMapAccessor<String, V>, StringMapAccessor<V>, CaseSensitivitySupport {
    
    /**
     * 
     * @param <Z>
     * @param type
     * @return 
     */
    @Override
    default <Z> IndexedStringMapAccessor<Z> castAs(final Class<Z> type) {
        return (IndexedStringMapAccessor<Z>)this;
    }

}
