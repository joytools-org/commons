/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.accessors.vavr;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Option;
import org.joytools.commons.accessors.MutatorImplUtils;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullKey;
import org.joytools.commons.accessors.StringMapAccessor;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
abstract class VavrTupleMapAccessor<V> implements StringMapAccessor<V> {

    private final static Seq<String> KEYS = Vector.of("1", "2", "3", "4", "5", "6", "7", "8");
            
    @Override
    public final CaseSensitivity caseSensitivity() {
        return CaseSensitivity.INSENSITIVE;
    }

    @Override
    public final Option<V> get(final String key) {
        final int index = toIndex(key);
        if (index < 0) {
            return None();
        }
        return Some(get0(index));
    }
    
    @Override
    public final V getOrNull(final String key) {
        final int index = toIndex(key);
        if (index < 0) {
            return null;
        }
        return get0(index);
    }

    @Override
    public final V getOrElse(final String key, final V def) {
        final int index = toIndex(key);
        if (index < 0) {
            return def;
        }
        return get0(index);
    }

    @Override
    public final V getOrFail(final String key) {
        final int index = toIndex(key);
        if (index < 0) {
            throw MutatorImplUtils.newItemNotFoundException("getOrFail", key);
        }
        return get0(index);
    }

    protected int toIndex(final String key) {
        try {
            final int index = Integer.parseInt(nonNullKey(key));
            if (index >= 1 && index <= size()) {
                return index;
            }
            return -1;
        } catch (final NumberFormatException e) {
            return -1;
        }
    }
    
    abstract protected V get0(final int index);
    
    @Override
    public final boolean contains(final String key) {
        return toIndex(key) > 0;
    }

    @Override
    public final IterableAccessor<String> keys() {
        return IterableAccessors.ofAll(KEYS.subSequence(0, size()));
    }
    
}
