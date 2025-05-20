/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import java.util.Map;
import org.apache.commons.collections4.Equator;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public interface EquatorMap<K, V> extends Map<K, V> {
    
    public Equator<K> keyEquator();

    public Equator<V> valueEquator();
    
}
