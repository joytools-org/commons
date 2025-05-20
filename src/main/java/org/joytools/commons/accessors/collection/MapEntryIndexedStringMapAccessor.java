/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Map;
import org.joytools.commons.accessors.IndexedStringMapAccessor;

/**
 *
 * @author AndreaR
 */
abstract class MapEntryIndexedStringMapAccessor<V> extends MapEntryIndexedMapAccessor<String, V> implements IndexedStringMapAccessor<V> {
 
    public MapEntryIndexedStringMapAccessor(final Map.Entry<String, V> entry) {
        super(entry);
    }
    
}
