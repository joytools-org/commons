/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.collections;

import static com.google.common.base.Preconditions.checkNotNull;
import java.util.Map;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public class StringMapBuilder<V> {

    protected StringMapBuilder() {
    }
    
    public static <T> StringMapBuilder<T> of() {
        return new StringMapBuilder<>();
    }
    
    public static <T> StringMapBuilder<T> of(final Class<T> clazz) {
        return new StringMapBuilder<>();
    }

    public StringMapBuilder<V> caseSensitivity(final CaseSensitivity cs) {
        this.cs = checkNotNull(cs, "Case sensitivity");
        return this;
    }

    public StringMapBuilder<V> ignoreCase() {
        this.cs = CaseSensitivity.INSENSITIVE;
        return this;
    }
    
    public StringMapBuilder<V> caseInsensitive() {
        this.cs = CaseSensitivity.INSENSITIVE;
        return this;
    }

    public StringMapBuilder<V> caseSensitive() {
        this.cs = CaseSensitivity.SENSITIVE;
        return this;
    }

    public StringMap<V> create() {
        return new StringHashMap(cs);
    }

    public StringMap<V> create(final Map<String, V> initialData) {
        return StringMaps.copyInto(create(), initialData);
    }

    public StringMap<V> createLinked() {
        return new StringLinkedHashMap(cs);
    }
    
    public StringMap<V> createLinked(final Map<String, V> initialData) {
        return StringMaps.copyInto(createLinked(), initialData);
    }

    private CaseSensitivity cs = CaseSensitivity.UNKNOWN;

    // Default value from AbstractHashedMap
    private int capacity = 16;

    // Default value from AbstractHashedMap
    private float loadFactor = 0.75f;

    // Default value from AbstractHashedMap
    private int threshold = 12;
    
}
