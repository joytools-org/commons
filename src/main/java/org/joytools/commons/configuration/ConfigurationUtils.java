/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.configuration;

import static io.vavr.API.None;
import static io.vavr.API.Some;
import io.vavr.control.Option;
import java.util.Objects;
import org.apache.commons.configuration2.Configuration;
import org.joytools.commons.accessors.MapMutator;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class ConfigurationUtils {
    
    /**
     * 
     * @param config
     * @return 
     */
    public static MapMutator<String, String> Mutator(final Configuration config) {
        return toStringMutator(config);
    }

    /**
     * 
     * @param config
     * @return 
     */
    public static MapMutator<String, String> toStringMutator(final Configuration config) {
        Objects.requireNonNull(config, "Configuration");
        return new AbstractConfigurationStringMapMutator() {
            @Override
            protected Configuration config() {
                return config;
            }
        };
    }

    /**
     * 
     * @param <T>
     * @param object
     * @param name
     * @return 
     */
    public static <T> String buildObjectKey(final T object, final String name) {
        return buildClassKey(Objects.requireNonNull(object, "Object").getClass(), name);
    }
    
    /**
     * 
     * @param <T>
     * @param clazz
     * @param name
     * @return 
     */
    public static <T> String buildClassKey(final Class clazz, final String name) {
        final String baseName = Objects.requireNonNull(clazz, "Class").getName();
        return baseName + "." + checkKeyName(name);
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    static String checkKeyName(final String name) {
        if (StringUtils.isEmpty(name)) {
            throw new IllegalArgumentException("Key name is empty");
        }
        return name;
    }

    /**
     * 
     * @param config
     * @param key
     * @return 
     */
    public static String checkExistingKey(
            final Configuration config, 
            final String key) {
        if (!nonNullConfig(config).containsKey(key)) {
            throw new IllegalStateException("Property not configured: " + key);
        }
        return key;
    }
    
    /**
     * 
     * @param config
     * @return 
     */
    static Configuration nonNullConfig(final Configuration config) {
        return Objects.requireNonNull(config, "Configuration");
    }

    /**
     * 
     * @param config
     * @param key
     * @return 
     */
    public static String getConfiguredString(
            final Configuration config,
            final String key) {
        return nonNullConfig(config).getString(checkExistingKey(config, key));
    }
    
    /**
     * 
     * @param config
     * @param key
     * @return 
     */
    public static Option<String> getOptionString(
            final Configuration config,
            final String key) {
        final String s = nonNullConfig(config).getString(key);
        if (s != null || config.containsKey(key)) {
            return Some(s);
        }
        return None();
    }

    /**
     * 
     * @param config
     * @param key
     * @return 
     */
    public static int getConfiguredInt(
            final Configuration config,
            final String key) {
        return nonNullConfig(config).getInt(checkExistingKey(config, key));
    }
    
    /**
     * 
     * @param key
     * @return 
     */
    public static Integer getConfiguredInteger(
            final Configuration config,
            final String key) {
        return nonNullConfig(config).getInteger(checkExistingKey(config, key), 0);
    }
    
    /**
     * 
     * @param config
     * @param key
     * @return 
     */
    public static boolean getConfiguredBoolean(
            final Configuration config,
            final String key) {
        return nonNullConfig(config).getBoolean(checkExistingKey(config, key));
    }
    
    /**
     * 
     * @param <T>
     * @param config
     * @param clazz
     * @param key
     * @return 
     */
    public static <T> T getConfiguredObject(
            final Configuration config,
            final Class<T> clazz,
            final String key) {
        return nonNullConfig(config).get(clazz, checkExistingKey(config, key));
    }

    /**
     * 
     * @param args
     * @throws Exception 
     */
    public static void main(final String... args) throws Exception {
        System.out.println(buildClassKey(String.class, "propName"));
        System.out.println(buildObjectKey(new Object(), "propName"));
    }

}
