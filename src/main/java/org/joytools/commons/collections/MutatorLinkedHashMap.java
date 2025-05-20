/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.collections;

import java.util.LinkedHashMap;
import java.util.Map;
import org.joytools.commons.accessors.MapMutator;
import org.joytools.commons.accessors.MapMutatorSupport;
import org.joytools.commons.accessors.collection.CollectionStringMutators;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 */
public class MutatorLinkedHashMap<V> extends LinkedHashMap<String, V> implements MapMutatorSupport<String, V>, CaseSensitivitySupport {
    
    /**
     * 
     */
    public MutatorLinkedHashMap() {
    }
    
    /**
     * 
     * @param map 
     */
    public MutatorLinkedHashMap(final Map<String, V> map) {
        super(map);
    }

    /**
     * 
     * @param initialCapacity 
     */
    public MutatorLinkedHashMap(final int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * 
     * @param initialCapacity
     * @param loadFactor 
     */
    public MutatorLinkedHashMap(final int initialCapacity, final float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * 
     * @param initialCapacity
     * @param loadFactor
     * @param accessOrder 
     */
    public MutatorLinkedHashMap(final int initialCapacity, final float loadFactor, final boolean accessOrder) {
        super(initialCapacity, loadFactor, accessOrder);
    }

    private MapMutator<String, V> mapAcc;
    
    @Override
    public MapMutator<String, V> mapMutator() {
        if (mapAcc == null) {
            /* mapAcc = new AbstractJdkStringMapMutator<V>() {
                @Override
                protected Map<String, V> map() {
                    return MutatorLinkedHashMap.this;
                }

                @Override
                public CaseSensitivity caseSensitivity() {
                    return CaseSensitivity.SENSITIVE;
                }
            }; */
            mapAcc = CollectionStringMutators.of(this);
        }
        return mapAcc;
    }

    @Override
    public CaseSensitivity caseSensitivity() {
        return CaseSensitivity.SENSITIVE;
    }

}
