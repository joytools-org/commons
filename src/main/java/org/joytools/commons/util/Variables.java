/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.joytools.commons.util;

import java.util.Objects;
import java.util.function.Supplier;
import org.joytools.commons.lang.StringUtils;

/**
 *
 * @author AndreaR
 */
public class Variables {
    
    /**
     * 
     * @param name
     * @return 
     */
    public static NamedVariableBuilder named(final String name) {
        return new NamedVariableBuilder() {
            @Override
            public <T> ResettableVariable<T> forClass(final Class<T> clazz) {
                return forClass0(name, clazz);
            }

            @Override
            public <T> ResettableVariable<T> withDefault(final T initValue) {
                return withDefault0(name, initValue);
            }

            @Override
            public <T> ResettableVariable<T> withDefault(final Supplier<T> initValue) {
                return withDefault0(name, initValue);
            }

            @Override
            public <X> Variable<X> of(final X initValue) {
                return of0(name, initValue);
            }
        };
    }
    
    /**
     * 
     * @param <T>
     * @param initValue
     * @return 
     */
    public static <T> Variable<T> withDefault(final T initValue) {
        return withDefault0(null, initValue);
    }
    
    /**
     * 
     * @param <T>
     * @param initValue
     * @return 
     */
    public static <T> Variable<T> withDefault(final Supplier<T> initValue) {
        return withDefault0(null, initValue);
    }

    /**
     * 
     * @param <T>
     * @param name
     * @param defaultValue
     * @return 
     */
    private static <T> ResettableVariable<T> withDefault0(final String name,
            final T defaultValue) {
        final ResettableVariableImpl<T> v = new ResettableVariableImpl<T>() {
            @Override
            public String name() {
                return name;
            }

            @Override
            protected T defaultValue() {
                return defaultValue;
            }
        };
        v.reset();
        return v;
    }

    /**
     * 
     * @param <T>
     * @param name
     * @param defaultValue
     * @return 
     */
    private static <T> ResettableVariable<T> withDefault0(final String name,
            final Supplier<T> defaultValue) {
        Objects.requireNonNull(defaultValue, "Default value");
        final ResettableVariableImpl<T> v = new ResettableVariableImpl<T>() {
            @Override
            public String name() {
                return name;
            }

            @Override
            protected T defaultValue() {
                return defaultValue.get();
            }
        };
        v.reset();
        return v;
    }

    /**
     * 
     * @param <T>
     * @param clazz
     * @return 
     */
    public static <T> Variable<T> forClass(final Class<T> clazz) {
        return forClass0(null, clazz);
    }

    /**
     * 
     * @param <T>
     * @param value
     * @return 
     */
    public static <T> Variable<T> of(final T value) {
        return of0(null, value);
    }

    /**
     * 
     * @param <T>
     * @param value
     * @return 
     */
    private static <T> Variable<T> of0(final String name,
            final T value) {
        final Variable<T> v = new AlwaysSetVariable() {
            public String name() {
                return name;
            }
        };
        v.set(value);
        return v;
    }

    /**
     * 
     * @param <T>
     * @param clazz
     * @return 
     */
    private static <T> ResettableVariable<T> forClass0(final String name,
            final Class<T> clazz) {
        final ResettableVariable<T> v = new ResettableVariableImpl<T>() {
            @Override
            public String name() {
                return name;
            }

            @Override
            protected T defaultValue() {
                return null;
            }
        };
        v.reset();
        return v;
    }

    public static interface NamedVariableBuilder {
    
        public <X> ResettableVariable<X> forClass(final Class<X> clazz);
        
        public <X> ResettableVariable<X> withDefault(final X initValue);

        public <X> ResettableVariable<X> withDefault(final Supplier<X> initValue);

        public <X> Variable<X> of(final X initValue);
        
    }
    
    /**
     * 
     * @param <Z> 
     */
    static abstract class BaseVariable<Z> implements Variable<Z> {
    
        /**
         * 
         */
        protected BaseVariable() {
        }
        
        /**
         * 
         * @return 
         */
        @Override
        public String toString() {
            final String name = StringUtils.toNullableString(name());
            final String valueStr = Objects.toString(get());
            if (name == null) {
                return valueStr;
            }
            return name + "=" + valueStr;
        }

        /**
         * 
         * @param other
         * @return 
         */
        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (other == null) {
                return false;
            }
            if (!(other instanceof Variable)) {
                return false;
            }
            return Objects.equals(get(), Variable.class.cast(other));
        }
        
        /**
         * 
         * @return 
         */
        @Override
        public int hashCode() {
            return Objects.hash(get());
        }
        
    }

    /**
     * 
     * @param <Z> 
     */
    static abstract class ResettableVariableImpl<Z> extends BaseVariable<Z> implements ResettableVariable<Z> {
    
        /**
         * 
         */
        protected ResettableVariableImpl() {
        }
        
        public void reset() {
            isSet = false;
            value = defaultValue();
        }
        
        protected abstract Z defaultValue();
        
        /**
         * 
         * @param newValue
         * @return 
         */
        @Override
        public Z set(final Z newValue) {
            isSet = true;
            final Z prev = value;
            value = newValue;
            return prev;
        }
        
        /**
         * 
         * @return 
         */
        public Z get() {
            return value;
        }
        
        /**
         * 
         * @return 
         */
        public boolean isSet() {
            return isSet;
        }
        
        /**
         * 
         */
        private Z value = null;
        
        /**
         * 
         */
        private boolean isSet = false;
        
    }

    static class AlwaysSetVariable<Z> extends BaseVariable<Z> {
    
        /**
         * 
         */
        protected AlwaysSetVariable() {
        }
        
        /**
         * 
         * @param newValue
         * @return 
         */
        @Override
        public Z set(final Z newValue) {
            final Z prev = value;
            value = newValue;
            return prev;
        }
        
        /**
         * 
         * @return 
         */
        @Override
        public Z get() {
            return value;
        }
        
        /**
         * 
         */
        private Z value = null;
        
    }
    
}
