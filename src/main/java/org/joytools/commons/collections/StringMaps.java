/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.joytools.commons.lang.StringUtils;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.util.CaseSensitivitySupport;

/**
 *
 * @author AndreaR
 */
public class StringMaps {
   
    public static CaseSensitivity caseSensitivityOf(final java.util.Map<?, ?> map) {
        checkNotNull(map, "Map");
        if (map instanceof CaseSensitivitySupport css) {
            return css.caseSensitivity();
        }
        if (map instanceof org.joytools.commons.collections.EquatorMap em) {
            final CaseSensitivity cs = CaseSensitivity.from(em.keyEquator());
            if (!cs.isUnknown()) {
                return cs;
            }
        }
        if (map instanceof org.apache.commons.collections4.map.CaseInsensitiveMap) {
            return CaseSensitivity.INSENSITIVE;
        }
        if (map instanceof java.util.HashMap
                || map instanceof java.util.Hashtable
                || map instanceof org.joytools.commons.collections.IndexedHashMap
                || map instanceof java.util.concurrent.ConcurrentHashMap) {
            return CaseSensitivity.SENSITIVE;
        }
        if (map instanceof java.util.SortedMap sm) {
            final CaseSensitivity cs = CaseSensitivity.from(sm.comparator());
            if (!cs.isUnknown()) {
                return cs;
            }
        }
        return CaseSensitivity.UNKNOWN;
    }
    
    /**
     * 
     * @param map
     * @return 
     */
    public static CaseSensitivity caseSensitivityOf(final io.vavr.collection.Map<?, ?> map) {
        checkNotNull(map, "Map");
        if (map instanceof CaseSensitivitySupport css) {
            return css.caseSensitivity();
        }
        if (map instanceof io.vavr.collection.Ordered ord) {
            final CaseSensitivity cs = CaseSensitivity.from(ord.comparator());
            if (!cs.isUnknown()) {
                return cs;
            }
        }
        return CaseSensitivity.UNKNOWN;
    }

    /**
     * 
     * @param <M>
     * @param <K>
     * @param <V>
     * @param target
     * @param source
     * @return 
     */
    public static <M extends java.util.Map<String, V>, K, V> M copyInto(
            final M target, 
            final java.util.Map<K, V> source) {
        checkNotNull(target, "Destination");
        checkNotNull(source, "Source");
        final CaseSensitivity toCS = caseSensitivityOf(target);
        final boolean noCheck = !toCS.isUnknown() && toCS.isCaseSensitive();
        final List<String> duplicates = new ArrayList();
        for (final java.util.Map.Entry<K, V> e : source.entrySet()) {
            final String key = StringUtils.toString(e.getKey());
            final V value = e.getValue();
            if (!noCheck && target.containsKey(key)) {
                duplicates.add(key);
            } else {
                target.put(key, value);
            }
        }
        checkArgument(duplicates.isEmpty(), 
                "Duplicated key(s) in destination map: %s. Check case sensitivity and retry.", 
                duplicates);
        return target;
    }

}
