/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import org.apache.commons.collections4.Equator;
import org.apache.commons.collections4.functors.DefaultEquator;
import org.apache.commons.collections4.map.AbstractLinkedMap;

/**
 *
 * @author AndreaR
 * @param <K>
 * @param <V>
 */
public class EquatorLinkedHashMap<K, V> extends AbstractLinkedMap<K, V> implements EquatorMap<K, V> {

    public EquatorLinkedHashMap(final int initialCapacity, final float loadFactor, final int threshold,
            final Equator<? super K> keyEq, final Equator<V> valueEq) {
        super(initialCapacity, loadFactor, threshold);
        this.keyEquator = keyEq == null ? DefaultEquator.defaultEquator() : keyEq;
        this.valueEquator = valueEq == null ? DefaultEquator.defaultEquator() : valueEq;
    }
    
    public EquatorLinkedHashMap(final Equator<? super K> keyEq, final Equator<V> valueEq) {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR, DEFAULT_THRESHOLD, keyEq, valueEq);
    }

    public EquatorLinkedHashMap(final Equator<? super K> keyEq) {
        this(keyEq, null);
    }

    public EquatorLinkedHashMap() {
        this(null, null);
    }

    @Override
    protected int hash(final Object key) {
        return keyEquator.hash(key);
    }

    @Override
    protected boolean isEqualKey(final Object k1, final Object k2) {
        return keyEquator.equate(k1, k2);
    }

    @Override
    protected boolean isEqualValue(final Object k1, final Object k2) {
        return valueEquator.equate(k1, k2);
    }
    
    private final Equator keyEquator;
        
    private final Equator valueEquator;

    @Override
    public Equator<K> keyEquator() {
        return keyEquator;
    }

    @Override
    public Equator<V> valueEquator() {
        return valueEquator;
    }
    
}
