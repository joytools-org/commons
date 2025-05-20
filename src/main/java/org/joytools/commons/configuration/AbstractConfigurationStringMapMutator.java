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
public abstract class AbstractConfigurationStringMapMutator extends AbstractConfigurationMapMutator<String> {

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public String getOrFail(final String key) {
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
    public Option<String> get(final String key) {
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
    public String getOrElse(final String key, final String value) {
        return config().getString(nonNullKey(key), value);
    }

    /**
     * 
     * @param key
     * @return 
     */
    @Override
    public String getOrNull(final String key) {
        return config().getString(nonNullKey(key), null);
    }

    /**
     * 
     * @return 
     */
    @Override
    public IterableAccessor<String> values() {
        return IterableAccessors.from(config().getKeys())
                .map(k -> config().getString(k));
    }
    
}
