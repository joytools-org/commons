/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Map;
import org.joytools.commons.accessors.FixedSizeIndexedStringMapMutator;

/**
 *
 * @author AndreaR
 */
abstract class MapEntryFixedSizeIndexedStringMapMutator<V> extends MapEntryFixedSizeIndexedMapMutator<String, V> implements FixedSizeIndexedStringMapMutator<V> {

    public MapEntryFixedSizeIndexedStringMapMutator(final Map.Entry<String, V> entry) {
        super(entry);
    }
    
}
