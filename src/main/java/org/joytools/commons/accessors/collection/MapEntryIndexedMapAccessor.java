/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Map;
import org.joytools.commons.accessors.IndexedMapAccessor;

/**
 *
 * @author AndreaR
 */
class MapEntryIndexedMapAccessor<K, V> extends NakedJavaMapEntryMutator<K, V> implements IndexedMapAccessor<K, V> {
     
    public MapEntryIndexedMapAccessor(final Map.Entry<K, V> entry) {
        super(entry);
    }
    
}
