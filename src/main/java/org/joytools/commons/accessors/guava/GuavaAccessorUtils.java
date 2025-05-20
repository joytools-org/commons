/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.guava;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class GuavaAccessorUtils {

    /**
     *
     * @param <V>
     * @param <M>
     * @param map
     * @param key
     * @return
     */
    public static <V, M extends Multimap<String, V>> Collection<V> getIgnoreCase(final M map, final String key) {
        final Set<String> foundKeys = new LinkedHashSet();
        for (final String e : map.keySet()) {
            if (StringUtils.equalsIgnoreCase(key, e)) {
                foundKeys.add(e);
            }
        }
        final Iterator<String> i = foundKeys.iterator();
        if (!i.hasNext()) {
            return Collections.EMPTY_LIST;
        }
        final Collection<V> value = map.get(i.next());
        if (i.hasNext()) {
            throw new IllegalArgumentException("Multiple keys found with different case: " + foundKeys);
        }
        return value;
    }
    
}
