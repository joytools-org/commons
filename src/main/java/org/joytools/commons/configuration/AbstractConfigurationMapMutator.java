/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.configuration;

import io.vavr.Tuple2;
import org.apache.commons.configuration2.Configuration;
import org.joytools.commons.accessors.impl.AccessorImplUtils;
import org.joytools.commons.accessors.MapAccessor;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemAlreadyExistsException;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullKey;
import org.joytools.commons.accessors.impl.NakedMultiValueAccessor;
import org.joytools.commons.accessors.StringMapMutator;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.util.CaseSensitivity;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 * @param <V>
 */
public abstract class AbstractConfigurationMapMutator<V> extends NakedMultiValueAccessor<String, V> implements StringMapMutator<V> {

    @Override
    public CaseSensitivity caseSensitivity() {
        return CaseSensitivity.SENSITIVE;
    }
    
    /**
     * 
     * @return 
     */
    protected abstract Configuration config();
    
    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public final boolean contains(final String key) {
        return config().containsKey(nonNullKey(key));
    }

    /**
     * 
     * @return 
     */
    @Override
    public boolean isUpdatable() {
        return true;
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param val 
     */
    @Override
    public final <U extends V> U put(final String key, final U val) {
        config().setProperty(nonNullKey(key), val);
        return val;
    }

    /**
     * 
     * @param <U>
     * @param key
     * @param val
     * @return 
     */
    @Override
    public final <U extends V> U insert(final String key, final U val) {
        if (contains(nonNullKey(key))) {
            throw newItemAlreadyExistsException("insert", key);
        }
        config().setProperty(key, val);
        return val;
    }

    /**
     * 
     * @param key
     * @param val
     * @return 
     */
    @Override
    public final <U extends V> U set(final String key, final U val) {
        if (!contains(nonNullKey(key))) {
            throw newItemNotFoundException("set", key);
        }
        config().setProperty(key, val);
        return val;
    }
    
    @Override
    public final <U extends V> boolean update(final String key, final U val) {
        if (!contains(nonNullKey(key))) {
            return false;
        }
        config().setProperty(key, val);
        return true;
    }

    /**
     * 
     * @param key 
     * @return  
     */
    @Override
    public final boolean delete(final String key) {
        if (contains(key)) {
            config().clearProperty(key);
            return true;
        }
        return false;
    }
    
    @Override
    public final void remove(final String key) {
        if (!contains(key)) {
            throw newItemNotFoundException("remove", key);
        }
        config().clearProperty(key);
    }

    /**
     * 
     * @return 
     */
    @Override
    public final IterableAccessor<String> keys() {
        return IterableAccessors.from(config().getKeys());
    }
    
    @Override
    public final IterableAccessor<Tuple2<String, V>> entriesByKey() {
        return AccessorImplUtils.defaultEntriesByKey(this);
    }

    /**
     * 
     * @return 
     */
    @Override
    public final boolean isEmpty() {
        return config().isEmpty();
    }

    /**
     * 
     * @return 
     */
    @Override
    public final int size() {
        return config().size();
    }
    
    @Override
    public MapAccessor.Reader<String, V> read() {
        return read0();
    }
    
}
