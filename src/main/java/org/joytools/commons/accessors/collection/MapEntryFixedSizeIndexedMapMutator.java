/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.collection;

import java.util.Map;
import org.joytools.commons.accessors.FixedSizeIndexedMapMutator;

/**
 *
 * @author AndreaR
 */
class MapEntryFixedSizeIndexedMapMutator<K, V> extends NakedJavaMapEntryMutator<K, V> implements FixedSizeIndexedMapMutator<K, V> {
     
    public MapEntryFixedSizeIndexedMapMutator(final Map.Entry<K, V> entry) {
        super(entry);
    }
}
