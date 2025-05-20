/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.joytools.commons.configuration;

import static com.google.common.base.Preconditions.checkArgument;
import io.vavr.control.Option;
import java.util.Objects;
import org.apache.commons.configuration2.Configuration;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import org.joytools.commons.util.Variable;

/**
 *
 * @author AndreaR
 */
public class ConfigurationVariables {
 
    /**
     * 
     * @param config
     * @param name
     * @return 
     */
    public static Variable<String> of(final Configuration config,
            final String name) {
        Objects.requireNonNull(config, "Configuration");
        checkArgument(isNotEmpty(name), "Variable name is empty");
        return new Variable<String>() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public String set(final String newValue) {
                config.setProperty(name, newValue);
                return newValue;
            }

            @Override
            public String get() {
                return ConfigurationUtils.getConfiguredString(config, name);
            }

            @Override
            public boolean isSet() {
                return config.containsKey(name);
            }
            
            @Override
            public Option<String> toOption() {
                return ConfigurationUtils.getOptionString(config, name);
            }

            /*
            @Override
            public Value<String> toValue() {
                return ConfigurationUtils.getValueString(config, name);
            } */
        };
    }
    
}
