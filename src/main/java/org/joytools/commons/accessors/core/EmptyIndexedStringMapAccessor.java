/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.core;

import org.joytools.commons.accessors.IndexedStringMapAccessor;
import org.joytools.commons.util.CaseSensitivity;

/**
 *
 * @author AndreaR
 */
abstract class EmptyIndexedStringMapAccessor<V> extends AbstractEmptyIndexedMapAccessor<String, V> implements IndexedStringMapAccessor<V> {

    private final static EmptyIndexedStringMapAccessor<Object> INSTANCE = new EmptyIndexedStringMapAccessor() {
        @Override
        public CaseSensitivity caseSensitivity() {
            return CaseSensitivity.SENSITIVE;
        }
    };
    
    private final static EmptyIndexedStringMapAccessor<Object> INSTANCE_IGNORE_CASE = new EmptyIndexedStringMapAccessor() {
        @Override
        public CaseSensitivity caseSensitivity() {
            return CaseSensitivity.INSENSITIVE;
        }
    };

    public static <T> EmptyIndexedStringMapAccessor<T> instance() {
        return (EmptyIndexedStringMapAccessor<T>)INSTANCE;
    }

    public static <T> EmptyIndexedStringMapAccessor<T> instance(final CaseSensitivity cs) {
        if (cs != null && cs.isIgnoreCase()) {
            return (EmptyIndexedStringMapAccessor<T>)INSTANCE_IGNORE_CASE;
        }
        return (EmptyIndexedStringMapAccessor<T>)INSTANCE;
    }

    public static <T> EmptyIndexedStringMapAccessor<T> instanceIgnoreCase() {
        return (EmptyIndexedStringMapAccessor<T>)INSTANCE_IGNORE_CASE;
    }

}
