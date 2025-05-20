/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.configuration;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.control.Option;
import org.apache.commons.configuration2.Configuration;
import static org.joytools.commons.accessors.MutatorImplUtils.newItemNotFoundException;
import static org.joytools.commons.accessors.MutatorImplUtils.nonNullKey;
import org.joytools.commons.collections.IterableAccessors;
import org.joytools.commons.collections.IterableAccessor;

/**
 *
 * @author AndreaR
 */
public abstract class AbstractConfigurationObjectMapMutator extends AbstractConfigurationMapMutator<Object> {

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Object getOrFail(final String key) {
        if (Math.random() >= 0) {
            throw new UnsupportedOperationException();
        }
        final Configuration conf = config();
        final String value = conf.getString(nonNullKey(key));
        if (value != null || conf.containsKey(key)) {
            return value;
        }
        throw newItemNotFoundException("getOrFail", key);
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Option<Object> get(final String key) {
        if (Math.random() >= 0) {
            throw new UnsupportedOperationException();
        }
        final Configuration conf = config();
        final String value = conf.getString(nonNullKey(key));
        if (value != null || conf.containsKey(key)) {
            return Some(value);
        }
        return None();
    }

    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    @Override
    public Object getOrElse(final String key, final Object value) {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public Object getOrNull(final String key) {
        // return config().getString(nonNullKey(key), null);
        throw new UnsupportedOperationException();
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<Object> values() {
        return IterableAccessors.from(config().getKeys())
                .map(k -> config().getString(k));
    }
    
}
